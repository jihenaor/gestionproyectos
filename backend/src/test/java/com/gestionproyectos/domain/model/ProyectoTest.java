package com.gestionproyectos.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ProyectoTest {

    @Test
    void crearProyecto_debeCrearConEstadoBorrador() {
        var proyecto = Proyecto.crear(
                "Proyecto Test",
                CodigoProyecto.of("CCF001-01-00001"),
                ModalidadInversion.INF,
                Dinero.of(1000000000L),
                Dinero.of(800000000L),
                "Justificación del proyecto test"
        );

        assertThat(proyecto.id()).isNotNull();
        assertThat(proyecto.nombre()).isEqualTo("Proyecto Test");
        assertThat(proyecto.estado()).isInstanceOf(EstadoProyecto.Borrador.class);
        assertThat(proyecto.fechaCreacion()).isEqualTo(LocalDate.now());
        assertThat(proyecto.estructurasCompletadas()).isEmpty();
    }

    @Test
    void porcentajeCompletado_debeCalcularCorrectamente() {
        var proyecto = Proyecto.crear(
                "Proyecto Test",
                CodigoProyecto.of("CCF001-01-00001"),
                ModalidadInversion.INF,
                Dinero.of(1000000000L),
                Dinero.of(800000000L),
                "Justificación"
        );

        Set<String> requeridas = Set.of("P-001A", "P-002A", "P-003A", "P-004C");
        assertThat(proyecto.porcentajeCompletado(requeridas)).isEqualTo(0.0);
    }

    @Test
    void toBuilder_debePermitirModificarProyecto() {
        var original = Proyecto.crear(
                "Proyecto Original",
                CodigoProyecto.of("CCF001-01-00001"),
                ModalidadInversion.INF,
                Dinero.of(1000000000L),
                Dinero.of(800000000L),
                "Justificación"
        );

        var modificado = original.toBuilder()
                .nombre("Proyecto Modificado")
                .build();

        assertThat(modificado.nombre()).isEqualTo("Proyecto Modificado");
        assertThat(modificado.codigo()).isEqualTo(original.codigo());
        assertThat(modificado.id()).isEqualTo(original.id());
    }
}