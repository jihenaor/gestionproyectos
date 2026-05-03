package com.gestionproyectos.application.dto.estructura;

import jakarta.validation.constraints.*;

public record P013AAspectosInfraestructura(
        @NotBlank String codigoProyecto,
        @NotNull Integer interventoriaSupervision,
        @NotNull @Min(0) Long valorTotalInterventoria,
        @NotNull Integer licenciaConstruccion,
        @Size(max = 250) String entidadCompetente,
        @Size(max = 30) String numRadicadoLicencia,
        @Pattern(regexp = "^\\d{8}$") String fechaRadicacionLicencia,
        @Size(max = 250) String numeroLicencia,
        String fechaLicencia,
        String vigenciaLicencia,
        @NotNull Integer serviciosPublicos,
        @Pattern(regexp = "^\\d{8}$") String fechaRadicacionAAA,
        @Size(max = 30) String numRadicadoAAA,
        @Pattern(regexp = "^\\d{8}$") String fechaExpedicionAAA,
        @Size(max = 30) String numDisponibilidadAAA,
        Integer vigenciaAAA,
        @Pattern(regexp = "^\\d{8}$") String fechaRadicacionEEA,
        @Size(max = 30) String numRadicadoEEA,
        @Pattern(regexp = "^\\d{8}$") String fechaExpedicionEEA,
        @Size(max = 30) String numDisponibilidadEEA,
        Integer vigenciaEEA,
        @Pattern(regexp = "^\\d{8}$") String fechaRadicacionGNA,
        @Size(max = 30) String numRadicadoGNA,
        @Pattern(regexp = "^\\d{8}$") String fechaExpedicionGNA,
        @Size(max = 30) String numDisponibilidadGNA,
        Integer vigenciaGNA,
        @NotNull @Min(0) Integer proyeccionGeneracionEmpleo
) {}