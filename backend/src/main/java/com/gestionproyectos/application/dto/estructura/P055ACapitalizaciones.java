package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P055ACapitalizaciones(
        @NotBlank String codigoProyecto,
        @NotNull @Min(0) Integer numAccionesCuotas,
        @NotNull @Min(0) Long valorAccionesCuotas,
        @NotBlank @Pattern(regexp = "^\\d{1,3}\\.\\d{1,2}$") String porcentajeParticipacion,
        @NotNull @Min(0) Long valorNominalAcciones,
        @NotNull @Min(0) Long valorMercadoAcciones
) {}