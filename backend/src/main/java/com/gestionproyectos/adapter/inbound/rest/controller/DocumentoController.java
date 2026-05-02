package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/documentos")
@Tag(name = "Documentos", description = "Gestión de documentos PDF")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/upload/{proyectoId}/{tipoDocumento}")
    @Operation(summary = "Subir documento PDF")
    public ResponseEntity<Map<String, Object>> subirDocumento(
            @PathVariable String proyectoId,
            @PathVariable String tipoDocumento,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            var metadata = documentoService.subirDocumento(proyectoId, tipoDocumento, file);
            return ResponseEntity.ok(Map.of(
                    "id", metadata.id(),
                    "nombre", metadata.nombreOriginal(),
                    "tipo", metadata.tipoDocumento(),
                    "proyectoId", metadata.proyectoId(),
                    "tamanho", metadata.tamanho(),
                    "estado", "uploaded"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/proyecto/{proyectoId}")
    @Operation(summary = "Listar documentos de un proyecto")
    public ResponseEntity<List<Map<String, Object>>> listarPorProyecto(@PathVariable String proyectoId) {
        var documentos = documentoService.listarPorProyecto(proyectoId);
        List<Map<String, Object>> resultado = documentos.stream()
                .map(doc -> Map.<String, Object>of(
                        "id", doc.id(),
                        "nombre", doc.nombreOriginal(),
                        "tipo", doc.tipoDocumento(),
                        "proyectoId", doc.proyectoId(),
                        "tamanho", doc.tamanho()
                ))
                .toList();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{documentoId}")
    @Operation(summary = "Obtener documento por ID")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable String documentoId) {
        var metadata = documentoService.obtenerDocumento(documentoId);
        if (metadata == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
                "id", metadata.id(),
                "nombre", metadata.nombreOriginal(),
                "tipo", metadata.tipoDocumento(),
                "proyectoId", metadata.proyectoId(),
                "tamanho", metadata.tamanho()
        ));
    }

    @DeleteMapping("/{documentoId}")
    @Operation(summary = "Eliminar documento")
    public ResponseEntity<Void> eliminar(@PathVariable String documentoId) {
        try {
            documentoService.eliminarDocumento(documentoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}