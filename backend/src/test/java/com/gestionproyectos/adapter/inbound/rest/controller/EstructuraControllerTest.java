package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.estructura.*;
import com.gestionproyectos.application.service.estructura.EstructuraPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class EstructuraControllerTest {

    @Mock
    private EstructuraPersistenceService persistenceService;

    @InjectMocks
    private EstructuraController controller;

    @Test
    void guardarP001ARetornaOkConDatosGuardados() {
        P001ADatosGenerales datos = new P001ADatosGenerales(
                "CCF123-01-00001",
                "Proyecto de Infraestructura Deportivo",
                "INF",
                500000000L,
                500000000L,
                "<p>Justificación del proyecto...</p>",
                "<ul><li>Objetivo 1</li></ul>",
                2,
                "001",
                "20250115",
                7,
                24,
                "12.50",
                1500L,
                "Descripción adicional"
        );

        P001ADatosGenerales savedData = new P001ADatosGenerales(
                datos.codigoProyecto(),
                datos.nombreProyecto(),
                datos.modalidadInversion(),
                datos.valorTotalProyecto(),
                datos.valorAprobadoVigencia(),
                datos.justificacion(),
                datos.objetivos(),
                datos.resolucionAEI(),
                datos.numActa(),
                datos.fechaConsejo(),
                datos.numConsejeros(),
                datos.tiempoRecuperacion(),
                datos.tasaDescuento(),
                datos.numeroBeneficiarios(),
                datos.descripcionObjetivo()
        );

        when(persistenceService.guardarP001A(any(P001ADatosGenerales.class))).thenReturn(savedData);

        ResponseEntity<Map<String, Object>> response = controller.guardarP001A(datos);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("P-001A", response.getBody().get("estructura"));
        assertEquals("CCF123-01-00001", response.getBody().get("codigoProyecto"));
        assertEquals("guardado", response.getBody().get("estado"));
        assertEquals("Proyecto de Infraestructura Deportivo", response.getBody().get("nombreProyecto"));

        verify(persistenceService).guardarP001A(datos);
    }

    @Test
    void obtenerP001AExisteDevuelveDatos() {
        P001ADatosGenerales datos = new P001ADatosGenerales(
                "CCF123-01-00001",
                "Test Proyecto",
                "INF",
                100000000L,
                100000000L,
                "Test justificacion",
                "Test objetivos",
                2,
                "001",
                "20250115",
                7,
                0,
                "10.00",
                100L,
                ""
        );

        when(persistenceService.obtenerP001A("CCF123-01-00001")).thenReturn(Optional.of(datos));

        ResponseEntity<P001ADatosGenerales> response = controller.obtenerP001A("CCF123-01-00001");

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CCF123-01-00001", response.getBody().codigoProyecto());
        assertEquals("Test Proyecto", response.getBody().nombreProyecto());
        assertEquals("INF", response.getBody().modalidadInversion());
    }

    @Test
    void obtenerP001ANoExisteDevuelve404() {
        when(persistenceService.obtenerP001A("CCF999-99-99999")).thenReturn(Optional.empty());

        ResponseEntity<P001ADatosGenerales> response = controller.obtenerP001A("CCF999-99-99999");

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void guardarP002ARetornaOk() {
        P002ACronograma cronograma = new P002ACronograma(
                "CCF123-01-00001",
                List.of(
                        new P002ACronograma.CronogramaActividad(
                                "01", "Construcción", "25.50", "20250115", "20250630", "M2", 500L
                        )
                ),
                "25.50"
        );

        ResponseEntity<Map<String, Object>> response = controller.guardarP002A(cronograma);

        assertEquals(OK, response.getStatusCode());
        assertEquals("P-002A", response.getBody().get("estructura"));
        assertEquals(1, response.getBody().get("numActividades"));
    }

    @Test
    void guardarP003ARetornaOk() {
        P003ALocalizacion localizacion = new P003ALocalizacion(
                "CCF123-01-00001",
                "Antioquia",
                "Medellín",
                "Calle 10 # 20-30",
                "El Poblado",
                "6042500000",
                "Juan Pérez",
                "20250301",
                6.247197,
                -75.565145
        );

        ResponseEntity<Map<String, Object>> response = controller.guardarP003A(localizacion);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Antioquia", response.getBody().get("departamento"));
        assertEquals("Medellín", response.getBody().get("municipio"));
    }

    @Test
    void guardarP004CCalculaTotalCorrectamente() {
        P004CEstructuraFuenteRecursos fuentes = new P004CEstructuraFuenteRecursos(
                "CCF123-01-00001",
                List.of(
                        new P004CEstructuraFuenteRecursos.FuenteRecurso("01", "CCF", 300000000L, "60.00", "Propio", "CC-001"),
                        new P004CEstructuraFuenteRecursos.FuenteRecurso("02", "Terceros", 200000000L, "40.00", "Externo", "CC-002")
                )
        );

        ResponseEntity<Map<String, Object>> response = controller.guardarP004C(fuentes);

        assertEquals(OK, response.getStatusCode());
        assertEquals(500000000L, response.getBody().get("valorTotal"));
    }

    @Test
    void guardarP011ACalculaBeneficiariosCorrectamente() {
        P011ACoberturaProyectada cobertura = new P011ACoberturaProyectada(
                "CCF123-01-00001",
                List.of(
                        new P011ACoberturaProyectada.CoberturaProyectadaItem("01", 500, 500000L, 100, 100, 100, 100, 100),
                        new P011ACoberturaProyectada.CoberturaProyectadaItem("02", 300, 600000L, 50, 50, 50, 50, 50)
                )
        );

        ResponseEntity<Map<String, Object>> response = controller.guardarP011A(cobertura);

        assertEquals(OK, response.getStatusCode());
        assertEquals(800, response.getBody().get("totalBeneficiarios"));
    }

    @Test
    void obtenerEstructurasPorProyectoDevuelveMapa() {
        ResponseEntity<Map<String, Object>> response = controller.obtenerEstructurasPorProyecto("CCF123-01-00001");

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CCF123-01-00001", response.getBody().get("codigoProyecto"));
        assertTrue(response.getBody().get("estructuras") instanceof Map);
    }

    @Test
    void obtenerEstructuraNoExistenteDevuelve404() {
        ResponseEntity<Map<String, Object>> response = controller.obtenerEstructura("CCF999-99-99999", "P-001A");

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void guardarP013ARetornaOk() {
        P013AAspectosInfraestructura aspectos = new P013AAspectosInfraestructura(
                "CCF123-01-00001",
                1, 25000000L, 1, "Curaduría No. 1", "2025-00123", "20250115",
                "2025-000456", "20250301", "12 meses",
                1, "20250120", "2025-000789", "20250215", "AAA-2025-001", 24,
                "20250122", "2025-000111", "20250220", "EEA-2025-002", 12,
                "20250125", "2025-000222", "20250225", "GNA-2025-003", 6,
                25
        );

        ResponseEntity<Map<String, Object>> response = controller.guardarP013A(aspectos);

        assertEquals(OK, response.getStatusCode());
        assertEquals("P-013A", response.getBody().get("estructura"));
        assertEquals(1, response.getBody().get("interventoria"));
        assertEquals(25, response.getBody().get("empleosProyectados"));
    }
}