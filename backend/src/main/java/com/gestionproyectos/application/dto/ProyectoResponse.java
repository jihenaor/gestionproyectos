package com.gestionproyectos.application.dto;

import com.gestionproyectos.domain.model.Proyecto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record ProyectoResponse(
        UUID id,
        String codigo,
        String nombre,
        String modalidadInversion,
        Long valorTotal,
        Long valorAprobado,
        String justificacion,
        String estado,
        LocalDate fechaCreacion,
        LocalDateTime ultimaActualizacion,
        Set<String> estructurasCompletadas,
        double porcentajeCompletado
) {
    public static ProyectoResponse from(Proyecto proyecto, Set<String> estructurasRequeridas) {
        return new ProyectoResponse(
                proyecto.id(),
                proyecto.codigo().value(),
                proyecto.nombre(),
                proyecto.modalidadInversion().descripcion(),
                proyecto.valorTotal().valor(),
                proyecto.valorAprobado().valor(),
                proyecto.justificacion(),
                proyecto.estado().descripcion(),
                proyecto.fechaCreacion(),
                proyecto.ultimaActualizacion(),
                proyecto.estructurasCompletadas(),
                proyecto.porcentajeCompletado(estructurasRequeridas)
        );
    }
}