package com.gestionproyectos.application.service.reporte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class XmlGeneratorServiceTest {

    private XmlGeneratorService xmlGenerator;

    @BeforeEach
    void setUp() {
        xmlGenerator = new XmlGeneratorService();
    }

    @Test
    void generarP001A_debeGenerarXmlValido() {
        Map<String, Object> datos = Map.of(
                "codigo", "CCF001-01-00001",
                "nombre", "Proyecto Test",
                "modalidadInversion", "INF",
                "valorTotal", "1000000000",
                "valorAprobado", "800000000",
                "fechaAprobacion", LocalDate.of(2025, 3, 15),
                "numActa", "123",
                "fechaConsejo", LocalDate.of(2025, 3, 10),
                "numConsejeros", "7"
        );

        String xml = xmlGenerator.generarP001A(datos);

        assertThat(xml).contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        assertThat(xml).contains("<COD_PROYECTO>CCF001-01-00001</COD_PROYECTO>");
        assertThat(xml).contains("<NOMBRE_PROYECTO>Proyecto Test</NOMBRE_PROYECTO>");
        assertThat(xml).contains("<VALOR_TOTAL_PROYECTO>1000000000</VALOR_TOTAL_PROYECTO>");
    }

    @Test
    void generarP001A_debeEscaparCaracteresEspeciales() {
        Map<String, Object> datos = Map.of(
                "codigo", "CCF001-01-00001",
                "nombre", "Proyecto <Test> & \"Validación\"",
                "modalidadInversion", "INF",
                "valorTotal", "1000000000",
                "valorAprobado", "800000000",
                "fechaAprobacion", LocalDate.now(),
                "numActa", "123",
                "fechaConsejo", LocalDate.now(),
                "numConsejeros", "7"
        );

        String xml = xmlGenerator.generarP001A(datos);

        assertThat(xml).contains("&lt;Test&gt;");
        assertThat(xml).contains("&amp;");
        assertThat(xml).contains("&quot;Validación&quot;");
    }

    @Test
    void generarP002A_debeGenerarXmlConDatosCorrectos() {
        Map<String, Object> cronograma = Map.of(
                "tipoActividad", "0501",
                "descripcion", "Actividad de prueba",
                "porcentaje", "25.50",
                "fechaInicio", LocalDate.of(2025, 4, 1),
                "fechaTerminacion", LocalDate.of(2025, 12, 31)
        );

        String xml = xmlGenerator.generarP002A("CCF001-01-00001", cronograma);

        assertThat(xml).contains("<COD_PROYECTO>CCF001-01-00001</COD_PROYECTO>");
        assertThat(xml).contains("<TIPO_ACTIVIDAD>0501</TIPO_ACTIVIDAD>");
        assertThat(xml).contains("<PORCENTAJE_PROYECTADO>25.50</PORCENTAJE_PROYECTADO>");
    }

    @Test
    void generarTodosLosXml_debeGenerarTodosLosXmls() {
        Map<String, Object> datosP001A = Map.of(
                "codigo", "CCF001-01-00001",
                "nombre", "Proyecto Test",
                "modalidadInversion", "INF",
                "valorTotal", "1000000000",
                "valorAprobado", "800000000",
                "fechaAprobacion", LocalDate.now(),
                "numActa", "123",
                "fechaConsejo", LocalDate.now(),
                "numConsejeros", "7"
        );

        Map<String, Map<String, Object>> estructuras = Map.of(
                "P-001A", datosP001A
        );

        var xmls = xmlGenerator.generarTodosLosXml("CCF001-01-00001", estructuras);

        assertThat(xmls).containsKey("P-001A");
        assertThat(xmls.get("P-001A")).contains("CCF001-01-00001");
    }
}