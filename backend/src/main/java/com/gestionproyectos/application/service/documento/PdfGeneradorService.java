package com.gestionproyectos.application.service.documento;

import com.gestionproyectos.application.dto.estructura.*;
import com.gestionproyectos.domain.model.Proyecto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfGeneradorService {

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarP001A(Proyecto proyecto, P001ADatosGenerales datos) {
        Map<String, Object> contexto = new HashMap<>();
        contexto.put("titulo", "P-001A - Datos Generales del Proyecto");
        contexto.put("codigoProyecto", datos.codigoProyecto());
        contexto.put("nombreProyecto", datos.nombreProyecto());
        contexto.put("modalidadInversion", formatearModalidad(datos.modalidadInversion()));
        contexto.put("valorTotalProyecto", formatearMoneda(datos.valorTotalProyecto()));
        contexto.put("valorAprobadoVigencia", formatearMoneda(datos.valorAprobadoVigencia()));
        contexto.put("justificacion", stripHtml(datos.justificacion()));
        contexto.put("objetivos", stripHtml(datos.objetivos()));
        contexto.put("resolucionAEI", datos.resolucionAEI() == 1 ? "Sí" : "No");
        contexto.put("numActa", datos.numActa());
        contexto.put("fechaConsejo", datos.fechaConsejo());
        contexto.put("numConsejeros", datos.numConsejeros());
        contexto.put("tiempoRecuperacion", datos.tiempoRecuperacion() + " meses");
        contexto.put("tasaDescuento", datos.tasaDescuento() + "%");
        contexto.put("numeroBeneficiarios", datos.numeroBeneficiarios());
        contexto.put("descripcionObjetivo", datos.descripcionObjetivo());
        contexto.put("fechaGeneracion", LocalDateTime.now().format(FECHA_FORMATTER));

        return generarPdfBytes(contexto);
    }

    public byte[] generarP002A(Proyecto proyecto, P002ACronograma cronograma) {
        Map<String, Object> contexto = new HashMap<>();
        contexto.put("titulo", "P-002A - Cronograma del Proyecto");
        contexto.put("codigoProyecto", cronograma.codigoProyecto());
        contexto.put("actividades", cronograma.actividades());
        contexto.put("porcentajeTotal", cronograma.porcentajeTotal());
        contexto.put("numActividades", cronograma.actividades().size());
        contexto.put("fechaGeneracion", LocalDateTime.now().format(FECHA_FORMATTER));

        return generarPdfBytes(contexto);
    }

    public byte[] generarP003A(Proyecto proyecto, P003ALocalizacion localizacion) {
        Map<String, Object> contexto = new HashMap<>();
        contexto.put("titulo", "P-003A - Localización del Proyecto");
        contexto.put("codigoProyecto", localizacion.codigoProyecto());
        contexto.put("departamento", localizacion.departamento());
        contexto.put("municipio", localizacion.municipio());
        contexto.put("direccion", localizacion.direccion());
        contexto.put("barrio", localizacion.barrio());
        contexto.put("telefono", localizacion.telefono());
        contexto.put("contacto", localizacion.contacto());
        contexto.put("fechaInicioOperacion", localizacion.fechaInicioOperacion());
        contexto.put("latitud", localizacion.latitude());
        contexto.put("longitud", localizacion.longitude());
        contexto.put("fechaGeneracion", LocalDateTime.now().format(FECHA_FORMATTER));

        return generarPdfBytes(contexto);
    }

    public byte[] generarP004C(Proyecto proyecto, P004CEstructuraFuenteRecursos fuentes) {
        Map<String, Object> contexto = new HashMap<>();
        contexto.put("titulo", "P-004C - Estructura Fuentes de Recursos");
        contexto.put("codigoProyecto", fuentes.codigoProyecto());
        contexto.put("fuentes", fuentes.fuentes());
        contexto.put("totalFuentes", fuentes.fuentes().stream()
                .mapToLong(P004CEstructuraFuenteRecursos.FuenteRecurso::valor)
                .sum());
        contexto.put("numFuentes", fuentes.fuentes().size());
        contexto.put("fechaGeneracion", LocalDateTime.now().format(FECHA_FORMATTER));

        return generarPdfBytes(contexto);
    }

    public byte[] generarInformeCompleto(Proyecto proyecto, Map<String, Object> estructuras) {
        Map<String, Object> contexto = new HashMap<>();
        contexto.put("titulo", "Informe Completo del Proyecto");
        contexto.put("codigoProyecto", proyecto.codigo().value());
        contexto.put("nombreProyecto", proyecto.nombre());
        contexto.put("estado", proyecto.estado().descripcion());
        contexto.put("estructuras", estructuras);
        contexto.put("fechaGeneracion", LocalDateTime.now().format(FECHA_FORMATTER));

        return generarPdfBytes(contexto);
    }

    private byte[] generarPdfBytes(Map<String, Object> contexto) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 40px; }");
        html.append("h1 { color: #333; border-bottom: 2px solid #0066cc; padding-bottom: 10px; }");
        html.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        html.append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
        html.append("th { background-color: #0066cc; color: white; }");
        html.append(".header { background-color: #f5f5f5; padding: 15px; margin-bottom: 20px; }");
        html.append(".footer { margin-top: 30px; font-size: 0.8em; color: #666; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        html.append("<div class='header'>");
        html.append("<h1>").append(contexto.get("titulo")).append("</h1>");
        html.append("<p><strong>Código:</strong> ").append(contexto.get("codigoProyecto")).append("</p>");
        if (contexto.containsKey("nombreProyecto")) {
            html.append("<p><strong>Nombre:</strong> ").append(contexto.get("nombreProyecto")).append("</p>");
        }
        html.append("</div>");

        for (Map.Entry<String, Object> entry : contexto.entrySet()) {
            String key = entry.getKey();
            if (!key.equals("titulo") && !key.equals("codigoProyecto") &&
                !key.equals("nombreProyecto") && !key.equals("fechaGeneracion") &&
                !key.equals("actividades") && !key.equals("fuentes") && !key.equals("estructuras")) {

                html.append("<p><strong>").append(formatearLabel(key)).append(":</strong> ");
                html.append(entry.getValue()).append("</p>");
            }
        }

        if (contexto.containsKey("actividades")) {
            @SuppressWarnings("unchecked")
            var actividades = (java.util.List<?>) contexto.get("actividades");
            html.append("<h2>Actividades del Cronograma</h2>");
            html.append("<table>");
            html.append("<tr><th>Tipo</th><th>Descripción</th><th>%</th><th>Inicio</th><th>Fin</th></tr>");
            for (var act : actividades) {
                if (act instanceof P002ACronograma.CronogramaActividad) {
                    var a = (P002ACronograma.CronogramaActividad) act;
                    html.append("<tr>");
                    html.append("<td>").append(a.tipoActividad()).append("</td>");
                    html.append("<td>").append(a.descripcionActividad()).append("</td>");
                    html.append("<td>").append(a.porcentajeProyectado()).append("</td>");
                    html.append("<td>").append(a.fechaInicio()).append("</td>");
                    html.append("<td>").append(a.fechaTerminacion()).append("</td>");
                    html.append("</tr>");
                }
            }
            html.append("</table>");
        }

        if (contexto.containsKey("fuentes")) {
            @SuppressWarnings("unchecked")
            var fuentes = (java.util.List<?>) contexto.get("fuentes");
            html.append("<h2>Fuentes de Recursos</h2>");
            html.append("<table>");
            html.append("<tr><th>Código</th><th>Nombre</th><th>Valor</th><th>%</th></tr>");
            for (var f : fuentes) {
                if (f instanceof P004CEstructuraFuenteRecursos.FuenteRecurso) {
                    var fuente = (P004CEstructuraFuenteRecursos.FuenteRecurso) f;
                    html.append("<tr>");
                    html.append("<td>").append(fuente.codigoFuente()).append("</td>");
                    html.append("<td>").append(fuente.nombreFuente()).append("</td>");
                    html.append("<td>").append(formatearMoneda(fuente.valor())).append("</td>");
                    html.append("<td>").append(fuente.porcentaje()).append("</td>");
                    html.append("</tr>");
                }
            }
            html.append("</table>");
        }

        html.append("<div class='footer'>");
        html.append("<p>Generado: ").append(contexto.get("fechaGeneracion")).append("</p>");
        html.append("<p>Sistema de Gestión de Proyectos - Caja de Compensación Familiar</p>");
        html.append("</div>");

        html.append("</body>");
        html.append("</html>");

        return html.toString().getBytes();
    }

    private String formatearModalidad(String modalidad) {
        return switch (modalidad) {
            case "INF" -> "01 - Infraestructura";
            case "FON" -> "02 - Fondo de Crédito";
            case "EDU" -> "03 - Educación";
            case "REC" -> "04 - Recreación";
            case "OTR" -> "05 - Otros";
            default -> modalidad;
        };
    }

    private String formatearMoneda(Long valor) {
        if (valor == null) return "$0";
        return String.format("$%,d", valor);
    }

    private String stripHtml(String html) {
        if (html == null) return "";
        return html.replaceAll("<[^>]*>", "").trim();
    }

    private String formatearLabel(String key) {
        return key.replaceAll("([a-z])([A-Z])", "$1 $2")
                  .replaceAll("_", " ")
                  .toUpperCase();
    }
}