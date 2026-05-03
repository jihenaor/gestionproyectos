package com.gestionproyectos.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record ProyectoFOVIS(
        ProyectoId id,
        String codigo,
        Dinero valorTotal,
        ModalidadFovis modalidadFovis,
        String nombre,
        String descripcion,
        EstadoProyecto estado,
        LocalDate fechaCreacion,
        LocalDateTime ultimaActualizacion,
        Set<String> estructurasCompletadas
) {
    public double porcentajeCompletado(Set<String> requeridas) {
        if (requeridas.isEmpty()) return 0.0;
        long count = estructurasCompletadas.stream()
                .filter(requeridas::contains)
                .count();
        return (count * 100.0) / requeridas.size();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ProyectoId id;
        private String codigo;
        private Dinero valorTotal;
        private ModalidadFovis modalidadFovis;
        private String nombre;
        private String descripcion;
        private EstadoProyecto estado;
        private LocalDate fechaCreacion;
        private LocalDateTime ultimaActualizacion;
        private Set<String> estructurasCompletadas;

        public Builder id(ProyectoId id) { this.id = id; return this; }
        public Builder codigo(String codigo) { this.codigo = codigo; return this; }
        public Builder valorTotal(Dinero valor) { this.valorTotal = valor; return this; }
        public Builder modalidadFovis(ModalidadFovis modalidad) { this.modalidadFovis = modalidad; return this; }
        public Builder nombre(String nombre) { this.nombre = nombre; return this; }
        public Builder descripcion(String desc) { this.descripcion = desc; return this; }
        public Builder estado(EstadoProyecto estado) { this.estado = estado; return this; }
        public Builder fechaCreacion(LocalDate fecha) { this.fechaCreacion = fecha; return this; }
        public Builder ultimaActualizacion(LocalDateTime fecha) { this.ultimaActualizacion = fecha; return this; }
        public Builder estructurasCompletadas(Set<String> estructuras) { this.estructurasCompletadas = estructuras; return this; }

        public ProyectoFOVIS build() {
            return new ProyectoFOVIS(
                    id, codigo, valorTotal, modalidadFovis, nombre, descripcion,
                    estado, fechaCreacion, ultimaActualizacion, estructurasCompletadas
            );
        }
    }
}