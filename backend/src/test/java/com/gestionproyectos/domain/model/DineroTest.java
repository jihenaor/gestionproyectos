package com.gestionproyectos.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DineroTest {

    @Test
    void of_conValorPositivo_debeCrearDinero() {
        var dinero = Dinero.of(1000000L);
        assertThat(dinero.valor()).isEqualTo(1000000L);
    }

    @Test
    void of_conValorCero_debeCrearDinero() {
        var dinero = Dinero.of(0L);
        assertThat(dinero.valor()).isEqualTo(0L);
    }

    @Test
    void of_conValorNegativo_debeLanzarExcepcion() {
        assertThatThrownBy(() -> Dinero.of(-100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no puede ser negativo");
    }

    @Test
    void cero_debeRetornarInstanciaConValorCero() {
        var dinero = Dinero.cero();
        assertThat(dinero.valor()).isEqualTo(0L);
    }
}