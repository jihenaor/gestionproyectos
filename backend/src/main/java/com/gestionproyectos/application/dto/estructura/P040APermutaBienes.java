package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P040APermutaBienes(
        @NotBlank String codigoProyecto,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String fechaCertTradicionLibertad,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String fechaAvaluoRecibe,
        @NotBlank @Pattern(regexp = "^\\d{8}$") String fechaAvaluoEntrega,
        @NotBlank @Size(max = 50) String avaluadorRecibe,
        @NotBlank @Size(max = 50) String avaluadorEntrega,
        @NotNull @Min(0) Long valorAvaluoRecibe,
        @NotNull @Min(0) Long valorAvaluoEntrega,
        @NotBlank @Size(max = 50) String destinacionInmueble,
        @NotBlank @Size(max = 50) String usoAutorizado,
        @NotNull Long valorEnLibros,
        @NotNull Long utilidadPerdida,
        @NotBlank @Size(max = 30) String origenRecursos,
        @NotBlank @Size(max = 80) String destinacion
) {}