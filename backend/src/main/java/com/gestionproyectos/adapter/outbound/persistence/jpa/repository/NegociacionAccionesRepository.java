package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.NegociacionAccionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface NegociacionAccionesRepository extends JpaRepository<NegociacionAccionesEntity, UUID> {
    Optional<NegociacionAccionesEntity> findByIdProyecto(UUID idProyecto);
}