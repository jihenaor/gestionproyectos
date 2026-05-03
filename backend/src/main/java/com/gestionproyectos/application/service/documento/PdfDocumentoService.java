package com.gestionproyectos.application.service.documento;

import com.gestionproyectos.application.dto.estructura.P001ADatosGenerales;
import com.gestionproyectos.application.service.estructura.EstructuraPersistenceService;
import com.gestionproyectos.application.service.googledrive.GoogleDriveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@ConditionalOnProperty(name = "google.drive.enabled", havingValue = "true", matchIfMissing = false)
@Transactional
public class PdfDocumentoService {

    private final GoogleDriveService googleDriveService;
    private final EstructuraPersistenceService estructuraPersistenceService;

    public PdfDocumentoService(
            GoogleDriveService googleDriveService, EstructuraPersistenceService estructuraPersistenceService) {
        this.googleDriveService = googleDriveService;
        this.estructuraPersistenceService = estructuraPersistenceService;
    }

    public record SubidaArchivoResult(
            String fileId,
            String fileName,
            String webViewLink,
            String drivePath
    ) {}

    public SubidaArchivoResult generarYSubirP001A(String codigoProyecto) {
        log.info("Generando y subiendo P-001A para proyecto: {}", codigoProyecto);

        P001ADatosGenerales datos =
                estructuraPersistenceService
                        .obtenerP001A(codigoProyecto)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "No se encontró P-001A para el proyecto: " + codigoProyecto));

        byte[] htmlContent = generarHtmlP001A(datos);
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
                folderPath);
    }

    private byte[] generarHtmlP001A(P001ADatosGenerales dto) {
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
        html.append("<p><strong>Código:</strong> ").append(dto.codigoProyecto()).append("</p>");
        html.append("<p><strong>Nombre:</strong> ").append(nullSafe(dto.nombreProyecto())).append("</p>");
        html.append("<p><strong>Modalidad:</strong> ").append(formatearModalidad(dto.modalidadInversion())).append("</p>");
        html.append("</div>");

        html.append("<table>");
        html.append("<tr><th colspan='2'>Información Financiera</th></tr>");
        html.append("<tr><td class='label'>Valor Total del Proyecto</td><td>").append(formatCurrency(dto.valorTotalProyecto())).append("</td></tr>");
        html.append("<tr><td class='label'>Valor Aprobado Vigencia</td><td>").append(formatCurrency(dto.valorAprobadoVigencia())).append("</td></tr>");
        html.append("<tr><td class='label'>Resolución AEI</td><td>").append(dto.resolucionAEI() == 1 ? "Sí" : "No").append("</td></tr>");
        html.append("<tr><td class='label'>Número de Acta</td><td>").append(nullSafe(dto.numActa())).append("</td></tr>");
        html.append("<tr><td class='label'>Número de Consejeros</td><td>").append(dto.numConsejeros() != null ? dto.numConsejeros() : "").append("</td></tr>");
        html.append("</table>");

        if (dto.justificacion() != null && !dto.justificacion().isEmpty()) {
            html.append("<div class='section'>");
            html.append("<h2>Justificación del Proyecto</h2>");
            html.append("<p>").append(stripHtml(dto.justificacion())).append("</p>");
            html.append("</div>");
        }

        if (dto.objetivos() != null && !dto.objetivos().isEmpty()) {
            html.append("<div class='section'>");
            html.append("<h2>Objetivos</h2>");
            html.append("<p>").append(stripHtml(dto.objetivos())).append("</p>");
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
        if (modalidad.matches("\\d+")) {
            return switch (modalidad) {
                case "1", "01", "001" -> "01 - Infraestructura";
                case "2", "02", "002" -> "02 - Fondo de Crédito";
                case "3", "03", "003" -> "03 - Educación";
                case "4", "04", "004" -> "04 - Recreación / Crédito (num.)";
                case "5", "05", "005" -> "05 - Otros";
                default -> modalidad;
            };
        }
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
