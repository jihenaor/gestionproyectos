package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.PaginatedResponse;
import com.gestionproyectos.application.dto.ProyectoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1/fovis")
@Tag(name = "FOVIS", description = "Gestión de proyectos FOVIS")
public class FovisController {

    private static final Set<String> ESTRUCTURAS_FOVIS_REQUERIDAS = Set.of(
            "P-001F", "P-002F", "P-011F", "P-012F", "P-014F"
    );

    @GetMapping("/proyectos")
    @Operation(summary = "Listar proyectos FOVIS")
    public ResponseEntity<PaginatedResponse<ProyectoResponse>> listarFovis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(PaginatedResponse.of(java.util.Collections.emptyList(), 0, 20, 0));
    }

    @GetMapping("/proyectos/{id}")
    @Operation(summary = "Obtener proyecto FOVIS por ID")
    public ResponseEntity<ProyectoResponse> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.notFound().build();
    }
}