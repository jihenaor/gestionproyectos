package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.PaginatedResponse;
import com.gestionproyectos.application.dto.ProyectoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/fovis")
@Tag(name = "FOVIS", description = "Gestión de proyectos FOVIS")
public class FovisController {

    @GetMapping
    @Operation(summary = "Listar proyectos FOVIS")
    public ResponseEntity<PaginatedResponse<ProyectoResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(new PaginatedResponse<>(
                List.of(), page, size, 0, 0, false, false
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto FOVIS por ID")
    public ResponseEntity<ProyectoResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(new ProyectoResponse(
                id, "01-01-00001", "Proyecto FOVIS Demo",
                "Obras Asociadas VIS", 1000000000L, 1000000000L,
                "Descripción del proyecto FOVIS", "Borrador",
                java.time.LocalDate.now(), java.time.LocalDateTime.now(),
                java.util.Set.of(), 0.0
        ));
    }
}