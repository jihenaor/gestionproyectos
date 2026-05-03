package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.LocalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface LocalizacionRepository extends JpaRepository<LocalizacionEntity, UUID> {
    Optional<LocalizacionEntity> findByIdProyecto(UUID idProyecto);
}