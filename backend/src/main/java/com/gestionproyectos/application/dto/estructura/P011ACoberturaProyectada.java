package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P011ACoberturaProyectada(
        @NotBlank String codigoProyecto,
        @NotEmpty List<CoberturaProyectadaItem> coberturas
) {
        public record CoberturaProyectadaItem(
                @NotBlank String codigoCategoria,
                @NotNull @Min(0) Integer cantidadBeneficiarios,
                @NotNull @Min(0) Long valorPerCapita,
                @Min(0) Integer anio1,
                @Min(0) Integer anio2,
                @Min(0) Integer anio3,
                @Min(0) Integer anio4,
                @Min(0) Integer anio5
        ) {}
}