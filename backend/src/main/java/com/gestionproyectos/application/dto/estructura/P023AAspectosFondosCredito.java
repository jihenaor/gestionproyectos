package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P023AAspectosFondosCredito(
        @NotBlank String codigoProyecto,
        @NotEmpty List<FondoCreditoItem> items
) {
        public record FondoCreditoItem(
                @NotNull Integer modalidadCredito,
                @NotBlank String codigoCategoria,
                @NotBlank @Pattern(regexp = "^\\d{1,2}\\.\\d{1,2}$") String tasaInteresMinima,
                @NotBlank @Pattern(regexp = "^\\d{1,2}\\.\\d{1,2}$") String tasaInteresMaxima,
                @NotNull @Min(0) Integer cantCreditos,
                @NotNull @Min(0) Long valMontoCreditos,
                @NotNull @Min(1) @Max(120) Integer plazoCredito,
                @NotBlank @Pattern(regexp = "^\\d{1,2}\\.\\d{1,2}$") String porcentajeSubsidio
        ) {}
}