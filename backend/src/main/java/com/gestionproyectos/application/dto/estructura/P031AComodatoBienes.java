package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P031AComodatoBienes(
        @NotBlank String codigoProyecto,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String fechaCertTradicionLibertad,
        @NotBlank @Size(max = 50) String destinacionInmueble,
        @NotBlank @Size(max = 50) String usoAutorizado
) {}