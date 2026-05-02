package com.gestionproyectos.domain.model;

import java.util.Objects;

public record CodigoProyecto(String value) {

    private static final String PATTERN = "^CCF\\d{3}-\\d{2}-\\d{5}$";

    public CodigoProyecto {
        Objects.requireNonNull(value, "El código no puede ser null");
        if (!value.matches(PATTERN)) {
            throw new IllegalArgumentException(
                "Código proyecto inválido: " + value + ". Formato esperado: CCFXXX-XX-XXXXX");
        }
    }

    public static CodigoProyecto of(String value) {
        return new CodigoProyecto(value.toUpperCase());
    }
}