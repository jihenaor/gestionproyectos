package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P003ALocalizacion(
        @NotBlank String codigoProyecto,
        @NotBlank @Size(max = 50) String departamento,
        @NotBlank @Size(max = 50) String municipio,
        @Size(max = 100) String direccion,
        @Size(max = 50) String barrio,
        @Size(max = 30) String telefono,
        @Size(max = 50) String contacto,
        @Pattern(regexp = "^\\d{8}$") String fechaInicioOperacion,
        Double latitude,
        Double longitude
) {}