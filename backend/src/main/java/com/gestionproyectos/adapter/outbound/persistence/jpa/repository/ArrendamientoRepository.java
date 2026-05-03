package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.ArrendamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ArrendamientoRepository extends JpaRepository<ArrendamientoEntity, UUID> {
    Optional<ArrendamientoEntity> findByIdProyecto(UUID idProyecto);
}