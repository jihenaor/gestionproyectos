package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P034ACompraBienes(
        @NotBlank String codigoProyecto,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String fechaCertTradicionLibertad,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String fechaAvaluo,
        @NotBlank @Size(max = 50) String perito,
        @NotNull @Min(0) Long valorAvaluo,
        @NotBlank @Size(max = 50) String destinacionInmueble,
        @NotBlank @Size(max = 50) String usoAutorizado
) {}