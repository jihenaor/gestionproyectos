package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P012ASeguimientoProyecto(
        @NotBlank String codigoProyecto,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String periodoReporte,
        @NotEmpty List<SeguimientoItem> seguimientos
) {
        public record SeguimientoItem(
                @NotBlank String tipoActividad,
                @Size(max = 500) String descripcionActividad,
                @Pattern(regexp = "^\\d{1,3}\\.\\d{1,2}$") String porcentajeEjecutado,
                @NotNull @Min(0) Long valorPlaneado,
                @NotNull @Min(0) Long valorEjecutado,
                @NotNull @Min(0) Long costoActual,
                @NotNull @Min(0) Long valorPagado,
                @NotNull @Min(0) Long valorGanado,
                @NotNull @Min(0) Long cantidadEjecucionFisica,
                @Pattern(regexp = "^\\d{8}$") String fechaInicio,
                @Pattern(regexp = "^\\d{8}$") String fechaTerminacion,
                String observaciones
        ) {}
}