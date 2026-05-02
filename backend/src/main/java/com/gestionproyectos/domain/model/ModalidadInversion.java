package com.gestionproyectos.domain.model;

public enum ModalidadInversion {
    INF("Infraestructura"),
    FON("Fondo de Crédito"),
    EDU("Educación"),
    REC("Recreación"),
    OTRO("Otro");

    private final String descripcion;

    ModalidadInversion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String descripcion() { return descripcion; }
}