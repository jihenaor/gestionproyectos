package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.adapter.inbound.rest.controller.dto.LoginRequest;
import com.gestionproyectos.adapter.inbound.rest.controller.dto.TokenResponse;
import com.gestionproyectos.application.service.auth.JwtTokenGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Gestión de autenticación y tokens JWT")
public class AuthController {

    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthController(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        log.info("Intento de login para usuario: {}", request.username());

        if (!validarCredenciales(request.username(), request.password())) {
            log.warn("Credenciales inválidas para usuario: {}", request.username());
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Credenciales inválidas", "message", "Usuario o contraseña incorrectos"));
        }

        Set<String> roles = obtenerRoles(request.username());
        String token = jwtTokenGenerator.generateToken(request.username(), roles);

        log.info("Login exitoso para usuario: {}", request.username());

        return ResponseEntity.ok(new TokenResponse(token, jwtTokenGenerator.getExpirationSeconds()));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada exitosamente"));
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar token JWT")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(Map.of("valid", false, "error", "Token no proporcionado"));
        }

        return ResponseEntity.ok(Map.of("valid", true));
    }

    private boolean validarCredenciales(String username, String password) {
        return username != null && password != null &&
               username.length() >= 3 && password.length() >= 4;
    }

    private Set<String> obtenerRoles(String username) {
        return Set.of("USER");
    }
}