package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.*;
import com.gestionproyectos.application.port.in.ProyectoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Adaptador inbound - REST Controller.
 */
@RestController
@RequestMapping("/v1/proyectos")
@Tag(name = "Proyectos", description = "Gestión de proyectos de inversión")
public class ProyectoController {

    private final ProyectoUseCase proyectoUseCase;

    public ProyectoController(ProyectoUseCase proyectoUseCase) {
        this.proyectoUseCase = proyectoUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar todos los proyectos")
    public ResponseEntity<PaginatedResponse<ProyectoResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(proyectoUseCase.listarProyectos(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID")
    public ResponseEntity<ProyectoResponse> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(proyectoUseCase.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo proyecto")
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody CrearProyectoCommand command) {
        var response = proyectoUseCase.crear(command);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proyecto")
    public ResponseEntity<ProyectoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CrearProyectoCommand command
    ) {
        return ResponseEntity.ok(proyectoUseCase.actualizar(id, command));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proyecto")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        proyectoUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar proyectos por estado")
    public ResponseEntity<List<ProyectoResponse>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(proyectoUseCase.buscarPorEstado(estado));
    }
}