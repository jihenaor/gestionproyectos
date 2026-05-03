package com.gestionproyectos.domain.model;

public enum ModalidadFovis {
    OBRAS_ASOCIADAS_VIS(1, "Obras Asociadas a VIS"),
    CREDITOS_HIPOTECARIOS_MICROCREDITOS(2, "Créditos Hipotecarios y Microcréditos para VIS"),
    FINANCIACION_OFERENTES(3, "Financiación Oferentes de Programas y Proyectos VIS"),
    ADQUISICION_LOTES(4, "Adquisición de Lotes para Proyectos VIS"),
    PROGRAMAS_INTEGRALES(5, "Programas Integrales de Renovación y Redensificación Urbana"),
    MICROCREDITO(6, "Microcrédito");

    private final int codigo;
    private final String descripcion;

    ModalidadFovis(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }

    public static ModalidadFovis desdeCodigo(int codigo) {
        for (ModalidadFovis m : values()) {
            if (m.codigo == codigo) return m;
        }
        throw new IllegalArgumentException("Modalidad FOVIS desconocida: " + codigo);
    }
}