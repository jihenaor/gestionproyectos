package com.gestionproyectos.adapter.inbound.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/bienes-inmuebles")
@Tag(name = "Bienes Inmuebles", description = "Gestión de bienes inmuebles")
public class BienInmuebleController {

    @PostMapping("/arrendamiento")
    @Operation(summary = "Crear registro de arrendamiento")
    public ResponseEntity<Void> crearArrendamiento(@RequestBody Object datos) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comodato")
    @Operation(summary = "Crear registro de comodato")
    public ResponseEntity<Void> crearComodato(@RequestBody Object datos) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/compra")
    @Operation(summary = "Crear registro de compra")
    public ResponseEntity<Void> crearCompra(@RequestBody Object datos) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/permuta")
    @Operation(summary = "Crear registro de permuta")
    public ResponseEntity<Void> crearPermuta(@RequestBody Object datos) {
        return ResponseEntity.ok().build();
    }
}