package com.gestionproyectos.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CodigoProyectoTest {

    @ParameterizedTest
    @ValueSource(strings = {"CCF001-01-00001", "CCF123-02-12345", "ccf001-01-00001"})
    void of_conCodigoValido_debeCrearProyecto(String codigo) {
        var resultado = CodigoProyecto.of(codigo);
        assertThat(resultado.value()).isEqualTo(codigo.toUpperCase());
    }

    @Test
    void of_conCodigoInvalido_debeLanzarExcepcion() {
        assertThatThrownBy(() -> CodigoProyecto.of("INVALIDO"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Formato esperado: CCFXXX-XX-XXXXX");
    }

    @Test
    void of_conNull_debeLanzarExcepcion() {
        assertThatThrownBy(() -> CodigoProyecto.of(null))
                .isInstanceOf(NullPointerException.class);
    }
}