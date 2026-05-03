package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.SeguimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SeguimientoRepository extends JpaRepository<SeguimientoEntity, UUID> {
    List<SeguimientoEntity> findByIdProyecto(UUID idProyecto);
    List<SeguimientoEntity> findByIdProyectoAndPeriodoReporte(UUID idProyecto, String periodoReporte);
}