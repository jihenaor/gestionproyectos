package com.gestionproyectos.application.service.mapper;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.P001AEntity;
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

    public P001ADatosGenerales toP001ADTO(P001AEntity entity) {
        if (entity == null) return null;

        return new P001ADatosGenerales(
                entity.getCodigoProyecto(),
                entity.getNombreProyecto(),
                entity.getModalidadInversion(),
                entity.getValorTotalProyecto(),
                entity.getValorAprobadoVigencia(),
                entity.getJustificacion(),
                entity.getObjetivos(),
                entity.getResolucionAEI(),
                entity.getNumActa(),
                entity.getFechaConsejo(),
                entity.getNumConsejeros(),
                entity.getTiempoRecuperacion(),
                entity.getTasaDescuento(),
                entity.getNumeroBeneficiarios(),
                entity.getDescripcionObjetivo()
        );
    }

    public P001AEntity toP001AEntity(P001ADatosGenerales dto) {
        if (dto == null) return null;

        P001AEntity entity = new P001AEntity();
        entity.setId(UUID.randomUUID());
        entity.setCodigoProyecto(dto.codigoProyecto());
        entity.setNombreProyecto(dto.nombreProyecto());
        entity.setModalidadInversion(dto.modalidadInversion());
        entity.setValorTotalProyecto(dto.valorTotalProyecto());
        entity.setValorAprobadoVigencia(dto.valorAprobadoVigencia());
        entity.setJustificacion(dto.justificacion());
        entity.setObjetivos(dto.objetivos());
        entity.setResolucionAEI(dto.resolucionAEI());
        entity.setNumActa(dto.numActa());
        entity.setFechaConsejo(dto.fechaConsejo());
        entity.setNumConsejeros(dto.numConsejeros());
        entity.setTiempoRecuperacion(dto.tiempoRecuperacion());
        entity.setTasaDescuento(dto.tasaDescuento());
        entity.setNumeroBeneficiarios(dto.numeroBeneficiarios());
        entity.setDescripcionObjetivo(dto.descripcionObjetivo());
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setFechaActualizacion(LocalDateTime.now());
        return entity;
    }

    public P001AEntity toP001AEntityUpdate(P001AEntity entity, P001ADatosGenerales dto) {
        entity.setNombreProyecto(dto.nombreProyecto());
        entity.setModalidadInversion(dto.modalidadInversion());
        entity.setValorTotalProyecto(dto.valorTotalProyecto());
        entity.setValorAprobadoVigencia(dto.valorAprobadoVigencia());
        entity.setJustificacion(dto.justificacion());
        entity.setObjetivos(dto.objetivos());
        entity.setResolucionAEI(dto.resolucionAEI());
        entity.setNumActa(dto.numActa());
        entity.setFechaConsejo(dto.fechaConsejo());
        entity.setNumConsejeros(dto.numConsejeros());
        entity.setTiempoRecuperacion(dto.tiempoRecuperacion());
        entity.setTasaDescuento(dto.tasaDescuento());
        entity.setNumeroBeneficiarios(dto.numeroBeneficiarios());
        entity.setDescripcionObjetivo(dto.descripcionObjetivo());
        entity.setFechaActualizacion(LocalDateTime.now());
        return entity;
    }

    private ModalidadInversion modalidadFromString(String codigo) {
        try {
            return ModalidadInversion.valueOf(codigo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ModalidadInversion.OTRO;
        }
    }
}