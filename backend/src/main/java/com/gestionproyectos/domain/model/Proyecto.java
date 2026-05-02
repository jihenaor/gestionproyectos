package com.gestionproyectos.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record Proyecto(
        UUID id,
        CodigoProyecto codigo,
        String nombre,
        ModalidadInversion modalidadInversion,
        Dinero valorTotal,
        Dinero valorAprobado,
        String justificacion,
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

    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .codigo(this.codigo)
                .nombre(this.nombre)
                .modalidadInversion(this.modalidadInversion)
                .valorTotal(this.valorTotal)
                .valorAprobado(this.valorAprobado)
                .justificacion(this.justificacion)
                .estado(this.estado)
                .fechaCreacion(this.fechaCreacion)
                .ultimaActualizacion(this.ultimaActualizacion)
                .estructurasCompletadas(this.estructurasCompletadas);
    }

    public static Proyecto crear(
            String nombre,
            CodigoProyecto codigo,
            ModalidadInversion modalidad,
            Dinero valorTotal,
            Dinero valorAprobado,
            String justificacion
    ) {
        return new Proyecto(
                UUID.randomUUID(),
                codigo,
                nombre,
                modalidad,
                valorTotal,
                valorAprobado,
                justificacion,
                new EstadoProyecto.Borrador(),
                LocalDate.now(),
                LocalDateTime.now(),
                Set.of()
        );
    }

    public static class Builder {
        private UUID id;
        private CodigoProyecto codigo;
        private String nombre;
        private ModalidadInversion modalidadInversion;
        private Dinero valorTotal;
        private Dinero valorAprobado;
        private String justificacion;
        private EstadoProyecto estado;
        private LocalDate fechaCreacion;
        private LocalDateTime ultimaActualizacion;
        private Set<String> estructurasCompletadas;

        public Builder id(UUID id) { this.id = id; return this; }
        public Builder codigo(CodigoProyecto codigo) { this.codigo = codigo; return this; }
        public Builder nombre(String nombre) { this.nombre = nombre; return this; }
        public Builder modalidadInversion(ModalidadInversion modalidad) { this.modalidadInversion = modalidad; return this; }
        public Builder valorTotal(Dinero valor) { this.valorTotal = valor; return this; }
        public Builder valorAprobado(Dinero valor) { this.valorAprobado = valor; return this; }
        public Builder justificacion(String text) { this.justificacion = text; return this; }
        public Builder estado(EstadoProyecto estado) { this.estado = estado; return this; }
        public Builder fechaCreacion(LocalDate fecha) { this.fechaCreacion = fecha; return this; }
        public Builder ultimaActualizacion(LocalDateTime fecha) { this.ultimaActualizacion = fecha; return this; }
        public Builder estructurasCompletadas(Set<String> estructuras) { this.estructurasCompletadas = estructuras; return this; }

        public Builder copy(Proyecto proyecto) {
            return this.id(proyecto.id)
                    .codigo(proyecto.codigo)
                    .nombre(proyecto.nombre)
                    .modalidadInversion(proyecto.modalidadInversion)
                    .valorTotal(proyecto.valorTotal)
                    .valorAprobado(proyecto.valorAprobado)
                    .justificacion(proyecto.justificacion)
                    .estado(proyecto.estado)
                    .fechaCreacion(proyecto.fechaCreacion)
                    .ultimaActualizacion(proyecto.ultimaActualizacion)
                    .estructurasCompletadas(proyecto.estructurasCompletadas);
        }

        public Proyecto build() {
            return new Proyecto(
                    id != null ? id : UUID.randomUUID(),
                    codigo, nombre, modalidadInversion,
                    valorTotal, valorAprobado, justificacion,
                    estado != null ? estado : new EstadoProyecto.Borrador(),
                    fechaCreacion != null ? fechaCreacion : LocalDate.now(),
                    ultimaActualizacion != null ? ultimaActualizacion : LocalDateTime.now(),
                    estructurasCompletadas != null ? estructurasCompletadas : Set.of()
            );
        }
    }
}