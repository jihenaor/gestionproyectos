package com.gestionproyectos.adapter.inbound.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/creditos")
@Tag(name = "Créditos", description = "Gestión de créditos y cartera")
public class CreditoController {

    @GetMapping("/cartera")
    @Operation(summary = "Obtener cartera por edades")
    public ResponseEntity<Void> obtenerCarteraPorEdades() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/seguimiento")
    @Operation(summary = "Crear registro de seguimiento de crédito")
    public ResponseEntity<Void> crearSeguimiento(@RequestBody Object datos) {
        return ResponseEntity.ok().build();
    }
}