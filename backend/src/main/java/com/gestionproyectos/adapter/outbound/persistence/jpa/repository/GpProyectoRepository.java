package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.GpProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface GpProyectoRepository extends JpaRepository<GpProyectoEntity, UUID> {
    Optional<GpProyectoEntity> findByCodigoProyecto(String codigoProyecto);
}