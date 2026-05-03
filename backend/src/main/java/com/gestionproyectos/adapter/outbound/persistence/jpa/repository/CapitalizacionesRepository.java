package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.CapitalizacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CapitalizacionesRepository extends JpaRepository<CapitalizacionesEntity, UUID> {
    Optional<CapitalizacionesEntity> findByIdProyecto(UUID idProyecto);
}