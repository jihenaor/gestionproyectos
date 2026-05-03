package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.PermutaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PermutaRepository extends JpaRepository<PermutaEntity, UUID> {
    Optional<PermutaEntity> findByIdProyecto(UUID idProyecto);
}