package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P004CEstructuraFuenteRecursos(
        @NotBlank String codigoProyecto,
        @NotEmpty List<FuenteRecurso> fuentes
) {
        public record FuenteRecurso(
                @NotBlank String codigoFuente,
                @NotBlank String nombreFuente,
                @NotNull @Min(0) Long valor,
                @Pattern(regexp = "^\\d{1,3}\\.\\d{1,2}$") String porcentaje,
                String tipoRecurso,
                String centroCosto
        ) {}
}