package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/creditos")
@Tag(name = "Créditos", description = "Gestión de créditos y cartera")
public class CreditoController {

    @GetMapping
    @Operation(summary = "Listar créditos")
    public ResponseEntity<PaginatedResponse<Map<String, Object>>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(new PaginatedResponse<>(
                List.of(), page, size, 0, 0, false, false
        ));
    }

    @GetMapping("/cartera/{proyectoId}")
    @Operation(summary = "Obtener cartera por edades de un proyecto")
    public ResponseEntity<List<Map<String, Object>>> obtenerCarteraPorEdades(@PathVariable String proyectoId) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    @Operation(summary = "Crear crédito")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Map<String, Object> credito) {
        return ResponseEntity.ok(Map.of(
                "id", "generated-id",
                "estado", "creado"
        ));
    }
}