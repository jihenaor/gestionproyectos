package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;
import java.util.List;

public record P002ACronograma(
        @NotBlank String codigoProyecto,
        @NotEmpty List<CronogramaActividad> actividades,
        String porcentajeTotal
) {
        public record CronogramaActividad(
                @NotBlank String tipoActividad,
                @Size(max = 500) String descripcionActividad,
                @Pattern(regexp = "^\\d{1,3}\\.\\d{1,2}$") String porcentajeProyectado,
                @Pattern(regexp = "^\\d{8}$") String fechaInicio,
                @Pattern(regexp = "^\\d{8}$") String fechaTerminacion,
                String unidadMedida,
                Long cantidadProgramada
        ) {}
}