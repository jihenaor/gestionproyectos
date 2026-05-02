package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P001ADatosGenerales(
        @NotBlank @Size(max = 15) String codigoProyecto,
        @NotBlank @Size(max = 200) String nombreProyecto,
        @NotBlank String modalidadInversion,
        @NotNull @Min(1) Long valorTotalProyecto,
        @NotNull @Min(0) Long valorAprobadoVigencia,
        @NotBlank @Size(min = 100, max = 4000) String justificacion,
        @NotBlank String objetivos,
        @NotNull Integer resolucionAEI,
        String numActa,
        String fechaConsejo,
        Integer numConsejeros,
        Integer tiempoRecuperacion,
        String tasaDescuento,
        @Min(0) Long numeroBeneficiarios,
        String descripcionObjetivo
) {}