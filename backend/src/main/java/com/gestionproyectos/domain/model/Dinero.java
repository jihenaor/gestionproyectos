package com.gestionproyectos.domain.model;

import java.util.Objects;

public record Dinero(Long valor) {

    public Dinero {
        Objects.requireNonNull(valor, "El valor no puede ser null");
        if (valor < 0) {
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
    }

    public static Dinero of(Long valor) {
        return new Dinero(valor);
    }

    public static Dinero cero() {
        return new Dinero(0L);
    }
}