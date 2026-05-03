package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.service.generacionxml.GeneracionXmlInformesSuperintendenciaService;
import com.gestionproyectos.domain.port.out.ZipGenerationPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/generacion-xml-informes")
@Tag(name = "Generación XML Informes Superintendencia", description = "Generación de archivos XML para reportes de Superintendencia")
public class GeneracionXmlInformesSuperintendenciaController {

    private static final String CODIGO_CAJA = "CCF044";

    private final GeneracionXmlInformesSuperintendenciaService generacionXmlService;
    private final ZipGenerationPort zipGenerationPort;

    public GeneracionXmlInformesSuperintendenciaController(
            GeneracionXmlInformesSuperintendenciaService generacionXmlService,
            ZipGenerationPort zipGenerationPort) {
        this.generacionXmlService = generacionXmlService;
        this.zipGenerationPort = zipGenerationPort;
    }

    @GetMapping("/xml/{proyectoId}/{estructura}")
    @Operation(summary = "Generar XML de una estructura específica")
    public ResponseEntity<String> generarXmlEstructura(
            @PathVariable String proyectoId,
            @PathVariable String estructura,
            @RequestParam(required = false, defaultValue = "502026") String codigoPeriodo
    ) {
        String xml = generacionXmlService.generarXmlPlantilla(estructura, proyectoId, codigoPeriodo);
        String nombreArchivo = generarNombreArchivo(estructura, codigoPeriodo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                .contentType(MediaType.APPLICATION_XML)
                .body(xml);
    }

    @GetMapping("/xml/proyecto/{proyectoId}")
    @Operation(summary = "Generar ZIP con todos los XMLs de un proyecto")
    public ResponseEntity<byte[]> generarZipProyecto(
            @PathVariable String proyectoId,
            @RequestParam(required = false, defaultValue = "502026") String codigoPeriodo
    ) {
        Map<String, String> archivosXml = generacionXmlService.generarTodosLosXmlsParaProyecto(
                proyectoId, codigoPeriodo);

        byte[] zipContent = zipGenerationPort.generarZip(archivosXml);

        String nombreZip = String.format("proyecto_%s_%s.zip", proyectoId, codigoPeriodo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreZip)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(zipContent);
    }

    @GetMapping("/estructuras")
    @Operation(summary = "Listar todas las estructuras XML disponibles")
    public ResponseEntity<List<Map<String, String>>> getEstructurasDisponibles() {
        List<Map<String, String>> estructuras = generacionXmlService.getEstructurasDisponibles();
        return ResponseEntity.ok(estructuras);
    }

    private String generarNombreArchivo(String estructura, String codigoPeriodo) {
        String numeralFormato = estructura.replace("P-", "").replace("-", "");
        return String.format("%s_%s_%s.xml", CODIGO_CAJA, numeralFormato, codigoPeriodo);
    }
}