package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/inmuebles")
@Tag(name = "Bienes Inmuebles", description = "Gestión de bienes inmuebles")
public class BienInmuebleController {

    @GetMapping
    @Operation(summary = "Listar bienes inmuebles")
    public ResponseEntity<PaginatedResponse<Map<String, Object>>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(new PaginatedResponse<>(
                List.of(), page, size, 0, 0, false, false
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener bien inmueble por ID")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "id", id,
                "tipo", "arrendamiento"
        ));
    }

    @PostMapping("/arrendamiento")
    @Operation(summary = "Crear registro de arrendamiento")
    public ResponseEntity<Map<String, Object>> crearArrendamiento(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok(Map.of("id", "generated-id"));
    }

    @PostMapping("/compra")
    @Operation(summary = "Crear registro de compra")
    public ResponseEntity<Map<String, Object>> crearCompra(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok(Map.of("id", "generated-id"));
    }

    @PostMapping("/comodato")
    @Operation(summary = "Crear registro de comodato")
    public ResponseEntity<Map<String, Object>> crearComodato(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok(Map.of("id", "generated-id"));
    }

    @PostMapping("/permuta")
    @Operation(summary = "Crear registro de permuta")
    public ResponseEntity<Map<String, Object>> crearPermuta(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok(Map.of("id", "generated-id"));
    }
}