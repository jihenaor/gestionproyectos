package com.gestionproyectos.application.service.generacionxml;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneracionXmlInformesSuperintendenciaService {

    private static final String NAMESPACE = "http://www.supersubsidio.gov.co/schema";
    private static final String CODIGO_CAJA = "CCF044";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Map<String, String> ESTRUCTURAS_XML = new LinkedHashMap<>();
    static {
        ESTRUCTURAS_XML.put("P-001A", "PROYECTOS_NUEVOS");
        ESTRUCTURAS_XML.put("P-002A", "CRONOGRAMA_INICIAL_PROYECTO");
        ESTRUCTURAS_XML.put("P-003A", "LOCALIZACION_PROYECTO");
        ESTRUCTURAS_XML.put("P-004C", "ESTRUCTURA_FUENTE_RECURSOS");
        ESTRUCTURAS_XML.put("P-005A", "FICHA_TECNICA_PROYECTOS");
        ESTRUCTURAS_XML.put("P-011A", "COBERTURA_PROYECTADA");
        ESTRUCTURAS_XML.put("P-011B", "COBERTURA_EJECUTADA");
        ESTRUCTURAS_XML.put("P-012A", "SEGUIMIENTO_PROYECTO");
        ESTRUCTURAS_XML.put("P-013A", "ASPECTOS_INFRAESTRUCTURA");
        ESTRUCTURAS_XML.put("P-023A", "FONDOS_CREDITO");
        ESTRUCTURAS_XML.put("P-024A", "CARTERA_POR_EDADES");
        ESTRUCTURAS_XML.put("P-026A", "ARRENDAMIENTO_INMUEBLES");
        ESTRUCTURAS_XML.put("P-031A", "COMODATO_INMUEBLES");
        ESTRUCTURAS_XML.put("P-034A", "COMPRA_INMUEBLES");
        ESTRUCTURAS_XML.put("P-040A", "PERMUTA_INMUEBLES");
        ESTRUCTURAS_XML.put("P-050A", "NEGOCIACION_ACCIONES");
        ESTRUCTURAS_XML.put("P-055A", "CAPITALIZACIONES");
    }

    public String generarXmlPlantilla(String tipoEstructura, String codigoProyecto, String codigoPeriodo) {
        StringBuilder xml = new StringBuilder();
        String rootName = tipoEstructura.replace("P-", "").replace("-", "");
        String nombreArchivo = String.format("%s_%s_%s", CODIGO_CAJA, rootName, codigoPeriodo);

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<").append(nombreArchivo);
        xml.append(" xmlns=\"").append(NAMESPACE).append("\">\n");
        xml.append("  <REGISTRO>\n");
        xml.append("    <COD_PROYECTO>").append(escapeXml(codigoProyecto)).append("</COD_PROYECTO>\n");
        xml.append("    <CODIGO_CAJA>").append(escapeXml(CODIGO_CAJA)).append("</CODIGO_CAJA>\n");
        xml.append("    <CODIGO_FORMATO>").append(escapeXml(tipoEstructura)).append("</CODIGO_FORMATO>\n");
        xml.append("    <PERIODO_REPORTE>").append(escapeXml(codigoPeriodo)).append("</PERIODO_REPORTE>\n");
        xml.append("    <FECHA_REPORTE>").append(escapeXml(LocalDate.now().format(DATE_FORMATTER))).append("</FECHA_REPORTE>\n");

        agregarCamposPorEstructura(xml, tipoEstructura);

        xml.append("  </REGISTRO>\n");
        xml.append("</").append(nombreArchivo).append(">");

        return xml.toString();
    }

    private void agregarCamposPorEstructura(StringBuilder xml, String tipoEstructura) {
        switch (tipoEstructura) {
            case "P-001A" -> {
                xml.append("    <NOMBRE_PROYECTO></NOMBRE_PROYECTO>\n");
                xml.append("    <MODALIDAD_INVERSION></MODALIDAD_INVERSION>\n");
                xml.append("    <VALOR_TOTAL></VALOR_TOTAL>\n");
                xml.append("    <VALOR_APROBADO></VALOR_APROBADO>\n");
                xml.append("    <JUSTIFICACION></JUSTIFICACION>\n");
            }
            case "P-002A" -> {
                xml.append("    <FECHA_INICIO></FECHA_INICIO>\n");
                xml.append("    <FECHA_FIN></FECHA_FIN>\n");
                xml.append("    <ACTIVIDADES></ACTIVIDADES>\n");
            }
            case "P-011A" -> {
                xml.append("    <ANO_1></ANO_1>\n");
                xml.append("    <ANO_2></ANO_2>\n");
                xml.append("    <ANO_3></ANO_3>\n");
                xml.append("    <ANO_4></ANO_4>\n");
                xml.append("    <ANO_5></ANO_5>\n");
            }
            case "P-012A" -> {
                xml.append("    <PERIODO></PERIODO>\n");
                xml.append("    <AVANCE_FISICO></AVANCE_FISICO>\n");
                xml.append("    <AVANCE_FINANCIERO></AVANCE_FINANCIERO>\n");
            }
            default -> {
                xml.append("    <DATO_PLACEHOLDER></DATO_PLACEHOLDER>\n");
            }
        }
    }

    public Map<String, String> generarTodosLosXmlsParaProyecto(String codigoProyecto, String codigoPeriodo) {
        Map<String, String> xmls = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : ESTRUCTURAS_XML.entrySet()) {
            String estructura = entry.getKey();
            String xml = generarXmlPlantilla(estructura, codigoProyecto, codigoPeriodo);
            String nombreArchivo = estructura.replace("P-", "").replace("-", "");
            String nombreCompleto = String.format("%s_%s_%s.xml", CODIGO_CAJA, nombreArchivo, codigoPeriodo);
            xmls.put(nombreCompleto, xml);
        }

        return xmls;
    }

    public List<Map<String, String>> getEstructurasDisponibles() {
        List<Map<String, String>> estructuras = new ArrayList<>();
        for (Map.Entry<String, String> entry : ESTRUCTURAS_XML.entrySet()) {
            Map<String, String> estructura = new LinkedHashMap<>();
            estructura.put("codigo", entry.getKey());
            estructura.put("nombreXml", entry.getValue());
            estructura.put("nombreArchivo", generarNombreArchivoBase(entry.getKey()));
            estructuras.add(estructura);
        }
        return estructuras;
    }

    private String generarNombreArchivoBase(String estructura) {
        String numeral = estructura.replace("P-", "").replace("-", "");
        return String.format("%s_%s_PERC.xml", CODIGO_CAJA, numeral);
    }

    private String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}