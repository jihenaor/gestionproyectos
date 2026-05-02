package com.gestionproyectos.domain.model;

import java.util.Objects;

public record ProyectoId(String value) {

    public ProyectoId {
        Objects.requireNonNull(value, "El ID no puede ser null");
    }

    public static ProyectoId of(String value) {
        return new ProyectoId(value);
    }

    public static ProyectoId generate() {
        return new ProyectoId(java.util.UUID.randomUUID().toString());
    }
}