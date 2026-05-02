package com.gestionproyectos.application.service.documento;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.P001AEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.P001ARepository;
import com.gestionproyectos.application.service.googledrive.GoogleDriveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@ConditionalOnProperty(name = "google.drive.enabled", havingValue = "true", matchIfMissing = false)
@Transactional
public class PdfDocumentoService {

    private final GoogleDriveService googleDriveService;
    private final P001ARepository p001ARepository;

    public PdfDocumentoService(GoogleDriveService googleDriveService, P001ARepository p001ARepository) {
        this.googleDriveService = googleDriveService;
        this.p001ARepository = p001ARepository;
    }

    public record SubidaArchivoResult(
            String fileId,
            String fileName,
            String webViewLink,
            String drivePath
    ) {}

    public SubidaArchivoResult generarYSubirP001A(String codigoProyecto) {
        log.info("Generando y subiendo P-001A para proyecto: {}", codigoProyecto);

        Optional<P001AEntity> optEntity = p001ARepository.findByCodigoProyecto(codigoProyecto);
        if (optEntity.isEmpty()) {
            throw new IllegalArgumentException("No se encontró P-001A para el proyecto: " + codigoProyecto);
        }

        P001AEntity entity = optEntity.get();
        byte[] htmlContent = generarHtmlP001A(entity);
        byte[] pdfContent = convertirHtmlAPdf(htmlContent);

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = String.format("P001A_%s_%s.html", codigoProyecto, fecha);

        String folderPath = "Proyectos/" + codigoProyecto;
        String fileId = googleDriveService.uploadPdf(pdfContent, fileName, folderPath);

        var fileInfo = googleDriveService.getFileInfo(fileId);

        log.info("Archivo subido exitosamente. ID: {}, Nombre: {}", fileId, fileName);

        return new SubidaArchivoResult(
                fileId,
                fileName,
                fileInfo.map(GoogleDriveService.DriveFile::webViewLink).orElse(""),
                folderPath
        );
    }

    private byte[] generarHtmlP001A(P001AEntity entity) {
        StringBuilder html = new StringBuilder();
        html.append("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset='UTF-8'>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    h1 { color: #003366; border-bottom: 2px solid #003366; padding-bottom: 10px; }
                    table { width: 100%; border-collapse: collapse; margin: 20px 0; }
                    th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
                    th { background-color: #003366; color: white; }
                    .header { background-color: #f5f5f5; padding: 15px; margin-bottom: 20px; }
                    .section { margin: 20px 0; }
                    .label { font-weight: bold; color: #003366; }
                </style>
            </head>
            <body>
            """);

        html.append("<div class='header'>");
        html.append("<h1>P-001A: Datos Generales del Proyecto</h1>");
        html.append("<p><strong>Código:</strong> ").append(entity.getCodigoProyecto()).append("</p>");
        html.append("<p><strong>Nombre:</strong> ").append(entity.getNombreProyecto()).append("</p>");
        html.append("<p><strong>Modalidad:</strong> ").append(formatearModalidad(entity.getModalidadInversion())).append("</p>");
        html.append("</div>");

        html.append("<table>");
        html.append("<tr><th colspan='2'>Información Financiera</th></tr>");
        html.append("<tr><td class='label'>Valor Total del Proyecto</td><td>").append(formatCurrency(entity.getValorTotalProyecto())).append("</td></tr>");
        html.append("<tr><td class='label'>Valor Aprobado Vigencia</td><td>").append(formatCurrency(entity.getValorAprobadoVigencia())).append("</td></tr>");
        html.append("<tr><td class='label'>Resolución AEI</td><td>").append(entity.getResolucionAEI() == 1 ? "Sí" : "No").append("</td></tr>");
        html.append("<tr><td class='label'>Número de Acta</td><td>").append(nullSafe(entity.getNumActa())).append("</td></tr>");
        html.append("<tr><td class='label'>Número de Consejeros</td><td>").append(entity.getNumConsejeros()).append("</td></tr>");
        html.append("</table>");

        if (entity.getJustificacion() != null && !entity.getJustificacion().isEmpty()) {
            html.append("<div class='section'>");
            html.append("<h2>Justificación del Proyecto</h2>");
            html.append("<p>").append(stripHtml(entity.getJustificacion())).append("</p>");
            html.append("</div>");
        }

        if (entity.getObjetivos() != null && !entity.getObjetivos().isEmpty()) {
            html.append("<div class='section'>");
            html.append("<h2>Objetivos</h2>");
            html.append("<p>").append(stripHtml(entity.getObjetivos())).append("</p>");
            html.append("</div>");
        }

        html.append("<div class='footer' style='margin-top:30px; font-size:0.8em; color:#666;'>");
        html.append("<p>Generado: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("</p>");
        html.append("<p>Sistema de Gestión de Proyectos - Caja de Compensación Familiar</p>");
        html.append("</div>");

        html.append("</body></html>");

        return html.toString().getBytes();
    }

    private byte[] convertirHtmlAPdf(byte[] htmlContent) {
        return htmlContent;
    }

    private String formatearModalidad(String modalidad) {
        if (modalidad == null) return "";
        return switch (modalidad.toUpperCase()) {
            case "INF" -> "01 - Infraestructura";
            case "FON" -> "02 - Fondo de Crédito";
            case "EDU" -> "03 - Educación";
            case "REC" -> "04 - Recreación";
            case "OTR", "OTRO" -> "05 - Otros";
            default -> modalidad;
        };
    }

    private String formatCurrency(Long valor) {
        if (valor == null) return "$0";
        return String.format("$%,d", valor);
    }

    private String nullSafe(String value) {
        return value != null ? value : "";
    }

    private String stripHtml(String html) {
        if (html == null) return "";
        return html.replaceAll("<[^>]*>", "").replaceAll("\\s+", " ").trim();
    }
}