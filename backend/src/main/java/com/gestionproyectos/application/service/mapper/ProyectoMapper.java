package com.gestionproyectos.application.service.mapper;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.GpProyectoEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.GpDatosGeneralesEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.ProyectoEntity;
import com.gestionproyectos.application.dto.estructura.P001ADatosGenerales;
import com.gestionproyectos.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProyectoMapper {

    public Proyecto toDomain(ProyectoEntity entity) {
        if (entity == null) return null;

        return Proyecto.builder()
                .id(entity.getId())
                .codigo(CodigoProyecto.of(entity.getCodigoProyecto()))
                .nombre(entity.getNombre())
                .modalidadInversion(modalidadFromString(entity.getModalidadInversion()))
                .valorTotal(Dinero.of(entity.getValorTotal()))
                .valorAprobado(Dinero.of(entity.getValorAprobado()))
                .justificacion(entity.getJustificacion())
                .estado(EstadoProyecto.desdeString(entity.getEstado()))
                .fechaCreacion(entity.getFechaCreacion())
                .ultimaActualizacion(entity.getUltimaActualizacion())
                .estructurasCompletadas(entity.getEstructurasCompletadas())
                .build();
    }

    public ProyectoEntity toEntity(Proyecto dominio) {
        if (dominio == null) return null;

        ProyectoEntity entity = new ProyectoEntity();
        entity.setId(dominio.id() != null ? dominio.id() : UUID.randomUUID());
        entity.setCodigoProyecto(dominio.codigo().value());
        entity.setNombre(dominio.nombre());
        entity.setModalidadInversion(dominio.modalidadInversion().name());
        entity.setValorTotal(dominio.valorTotal().valor());
        entity.setValorAprobado(dominio.valorAprobado().valor());
        entity.setJustificacion(dominio.justificacion());
        entity.setEstado(dominio.estado().descripcion());
        entity.setFechaCreacion(dominio.fechaCreacion() != null ? dominio.fechaCreacion() : LocalDate.now());
        entity.setUltimaActualizacion(dominio.ultimaActualizacion() != null ? dominio.ultimaActualizacion() : LocalDateTime.now());
        entity.setEstructurasCompletadas(dominio.estructurasCompletadas());
        return entity;
    }

    /** Actualiza una entidad gestionada por JPA (conserva id y @Version). */
    public void mergeProyectoIntoEntity(ProyectoEntity entity, Proyecto dominio) {
        entity.setNombre(dominio.nombre());
        entity.setModalidadInversion(dominio.modalidadInversion().name());
        entity.setValorTotal(dominio.valorTotal().valor());
        entity.setValorAprobado(dominio.valorAprobado().valor());
        entity.setJustificacion(dominio.justificacion());
        entity.setEstado(dominio.estado().descripcion());
        entity.setUltimaActualizacion(LocalDateTime.now());
        entity.setEstructurasCompletadas(dominio.estructurasCompletadas());
    }

    /**
     * P-001A DTO a partir de {@code GP_PROYECTOS} + fila opcional {@code GP_DATOS_GENERALES}.
     */
    public P001ADatosGenerales toP001ADTO(GpProyectoEntity gp, GpDatosGeneralesEntity dg) {
        if (gp == null) return null;

        Integer tiempo =
                dg != null && dg.getTiempoRecuperacion() != null
                        ? dg.getTiempoRecuperacion()
                        : gp.getTmpRecuperacion();

        String objetivos =
                dg != null && dg.getObjetivosEspecificos() != null && !dg.getObjetivosEspecificos().isBlank()
                        ? dg.getObjetivosEspecificos()
                        : gp.getObjetivoProyecto();

        Long numBenef =
                dg != null && dg.getNumeroBeneficiarios() != null
                        ? dg.getNumeroBeneficiarios()
                        : gp.getNumPoblacionAfectada();

        return new P001ADatosGenerales(
                gp.getCodigoProyecto(),
                trunc(gp.getDescripcionProyecto(), 200),
                modalidadFromModDeInversion(gp.getModDeInversion()),
                gp.getValTotalProyecto(),
                gp.getValorAprVigencia(),
                gp.getJustificacion(),
                objetivos,
                gp.getResolucionAEI(),
                gp.getNumActaAei() != null ? gp.getNumActaAei().toString() : null,
                gp.getFechaAprAei(),
                gp.getNumConsejeros(),
                tiempo,
                dg != null ? dg.getTasaDescuento() : null,
                numBenef,
                dg != null ? dg.getDescripcionObjetivo() : null);
    }

    public GpProyectoEntity newGpProyectoFromP001ADto(P001ADatosGenerales dto, UUID idProyecto) {
        LocalDateTime now = LocalDateTime.now();
        GpProyectoEntity gp = new GpProyectoEntity();
        gp.setId(idProyecto);
        gp.setCodigoProyecto(dto.codigoProyecto());
        gp.setModProyecto(1);
        gp.setModDeInversion(modalidadToModDeInversion(dto.modalidadInversion()));
        gp.setValTotalProyecto(dto.valorTotalProyecto());
        gp.setValorAprVigencia(dto.valorAprobadoVigencia());
        gp.setDescripcionProyecto(trunc(dto.nombreProyecto(), 4000));
        gp.setObjetivoProyecto(trunc(dto.objetivos(), 4000));
        gp.setJustificacion(trunc(dto.justificacion(), 4000));
        gp.setResolucionAEI(dto.resolucionAEI());
        gp.setNumActaAei(parseNumActaLong(dto.numActa()));
        gp.setFechaAprAei(trunc(dto.fechaConsejo(), 8));
        gp.setNumConsejeros(dto.numConsejeros() != null ? dto.numConsejeros() : 0);
        gp.setTmpRecuperacion(dto.tiempoRecuperacion());
        gp.setEstadoProyecto("BORRADOR");
        gp.setFecCreacion(now);
        gp.setFecModificacion(now);
        return gp;
    }

    public void mergeGpProyectoFromP001ADto(GpProyectoEntity gp, P001ADatosGenerales dto) {
        gp.setDescripcionProyecto(trunc(dto.nombreProyecto(), 4000));
        gp.setModDeInversion(modalidadToModDeInversion(dto.modalidadInversion()));
        gp.setValTotalProyecto(dto.valorTotalProyecto());
        gp.setValorAprVigencia(dto.valorAprobadoVigencia());
        gp.setObjetivoProyecto(trunc(dto.objetivos(), 4000));
        gp.setJustificacion(trunc(dto.justificacion(), 4000));
        gp.setResolucionAEI(dto.resolucionAEI());
        gp.setNumActaAei(parseNumActaLong(dto.numActa()));
        gp.setFechaAprAei(trunc(dto.fechaConsejo(), 8));
        gp.setNumConsejeros(dto.numConsejeros() != null ? dto.numConsejeros() : 0);
        gp.setTmpRecuperacion(dto.tiempoRecuperacion());
        gp.setFecModificacion(LocalDateTime.now());
    }

    public GpDatosGeneralesEntity newGpDatosGeneralesFromDto(P001ADatosGenerales dto, UUID idProyecto) {
        LocalDateTime now = LocalDateTime.now();
        GpDatosGeneralesEntity dg = new GpDatosGeneralesEntity();
        dg.setId(UUID.randomUUID());
        dg.setIdProyecto(idProyecto);
        mergeGpDatosGeneralesFieldsFromDto(dg, dto);
        dg.setFecCreacion(now);
        dg.setFecModificacion(now);
        return dg;
    }

    public void mergeGpDatosGeneralesFromDto(GpDatosGeneralesEntity dg, P001ADatosGenerales dto) {
        mergeGpDatosGeneralesFieldsFromDto(dg, dto);
        dg.setFecModificacion(LocalDateTime.now());
    }

    private void mergeGpDatosGeneralesFieldsFromDto(GpDatosGeneralesEntity dg, P001ADatosGenerales dto) {
        dg.setObjetivosEspecificos(trunc(dto.objetivos(), 4000));
        dg.setDescripcionObjetivo(trunc(dto.descripcionObjetivo(), 2000));
        dg.setTiempoRecuperacion(dto.tiempoRecuperacion());
        dg.setTasaDescuento(trunc(dto.tasaDescuento(), 5));
        dg.setNumeroBeneficiarios(dto.numeroBeneficiarios());
    }

    private static String trunc(String s, int max) {
        if (s == null) return null;
        return s.length() <= max ? s : s.substring(0, max);
    }

    private static Long parseNumActaLong(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return Long.parseLong(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Código modalidad para API: numérico {@code 001}… o alias legado {@code INF}, {@code FON}, … */
    private static String modalidadFromModDeInversion(Integer modDeInversion) {
        if (modDeInversion == null) return "000";
        return String.format("%03d", modDeInversion);
    }

    private static int modalidadToModDeInversion(String modalidad) {
        if (modalidad == null || modalidad.isBlank()) return 0;
        String m = modalidad.trim();
        if (m.matches("\\d+")) {
            return Integer.parseInt(m);
        }
        return switch (m.toUpperCase()) {
            case "INF" -> 1;
            case "FON" -> 2;
            case "EDU" -> 3;
            case "REC" -> 4;
            case "OTR", "OTRO" -> 5;
            default -> 0;
        };
    }

    private ModalidadInversion modalidadFromString(String codigo) {
        try {
            return ModalidadInversion.valueOf(codigo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ModalidadInversion.OTRO;
        }
    }
}
