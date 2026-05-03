package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P011BCoberturaEjecutada(
        @NotBlank String codigoProyecto,
        @NotEmpty List<CoberturaEjecutadaItem> coberturas,
        String observaciones
) {
        public record CoberturaEjecutadaItem(
                @NotBlank String codigoCategoria,
                @NotNull @Min(0) Integer cantidadBeneficiarios,
                @NotNull @Min(0) Long valorPerCapita,
                @NotNull @Min(0) Integer anio1Ejecutado,
                @NotNull @Min(0) Integer anio2Ejecutado,
                @NotNull @Min(0) Integer anio3Ejecutado,
                @NotNull @Min(0) Integer anio4Ejecutado,
                @NotNull @Min(0) Integer anio5Ejecutado
        ) {}
}