package com.gestionproyectos.application.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Command para crear un nuevo proyecto.
 * Usa Java record para DTO inmutable.
 */
public record CrearProyectoCommand(
        @NotBlank(message = "Código es obligatorio")
        @Pattern(regexp = "^CCF\\d{3}-\\d{2}-\\d{5}$", message = "Formato código inválido")
        String codigo,

        @NotBlank(message = "Nombre es obligatorio")
        @Size(min = 10, max = 200, message = "Nombre debe tener entre 10 y 200 caracteres")
        String nombre,

        @NotBlank(message = "Modalidad de inversión es obligatoria")
        String modalidadInversion,

        @NotNull(message = "Valor total es obligatorio")
        @Positive(message = "Valor total debe ser positivo")
        Long valorTotal,

        @NotNull(message = "Valor aprobado es obligatorio")
        Long valorAprobado,

        @NotBlank(message = "Justificación es obligatoria")
        @Size(min = 100, max = 4000, message = "Justificación debe tener entre 100 y 4000 caracteres")
        String justificacion,

        Integer resolucionAEI,
        Integer tiempoRecuperacion,
        String tasaDescuento
) {}