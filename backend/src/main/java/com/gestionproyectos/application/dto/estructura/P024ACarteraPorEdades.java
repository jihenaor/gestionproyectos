package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P024ACarteraPorEdades(
        @NotBlank String codigoProyecto,
        @NotEmpty List<CarteraItem> items
) {
        public record CarteraItem(
                @NotBlank String rangoEdad,
                @NotNull Integer edadCartera,
                @NotNull Integer modalidadCredito,
                @NotBlank String codigoCategoria,
                @NotNull @Min(0) Integer cantCreditos,
                @NotNull @Min(0) Long valorTotalMontoCartera
        ) {}
}