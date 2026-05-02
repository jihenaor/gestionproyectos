package com.gestionproyectos.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class EstadoProyectoTest {

    @ParameterizedTest
    @CsvSource({
            "BORRADOR, Borrador",
            "EN_EJECUCION, En Ejecución",
            "COMPLETADO, Completado",
            "CANCELADO, Cancelado"
    })
    void desdeString_conEstadoValido_debeCrearEstadoCorrecto(String input, String expectedDescripcion) {
        var estado = EstadoProyecto.desdeString(input);
        assertThat(estado.descripcion()).isEqualTo(expectedDescripcion);
    }

    @Test
    void borrador_puedeTransicionarAEnEjecucion() {
        var borrador = new EstadoProyecto.Borrador();
        var enEjecucion = new EstadoProyecto.EnEjecucion();
        assertThat(borrador.puedeTransicionarA(enEjecucion)).isTrue();
    }

    @Test
    void borrador_noPuedeTransicionarACompletado() {
        var borrador = new EstadoProyecto.Borrador();
        var completado = new EstadoProyecto.Completado();
        assertThat(borrador.puedeTransicionarA(completado)).isFalse();
    }

    @Test
    void completado_noPuedeTransicionarANingunEstado() {
        var completado = new EstadoProyecto.Completado();
        assertThat(completado.puedeTransicionarA(new EstadoProyecto.Borrador())).isFalse();
        assertThat(completado.puedeTransicionarA(new EstadoProyecto.EnEjecucion())).isFalse();
        assertThat(completado.puedeTransicionarA(new EstadoProyecto.Cancelado())).isFalse();
    }

    @Test
    void enEjecucion_puedeTransicionarACompletadoOCancelado() {
        var enEjecucion = new EstadoProyecto.EnEjecucion();
        assertThat(enEjecucion.puedeTransicionarA(new EstadoProyecto.Completado())).isTrue();
        assertThat(enEjecucion.puedeTransicionarA(new EstadoProyecto.Cancelado())).isTrue();
    }
}