package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.ComodatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ComodatoRepository extends JpaRepository<ComodatoEntity, UUID> {
    Optional<ComodatoEntity> findByIdProyecto(UUID idProyecto);
}