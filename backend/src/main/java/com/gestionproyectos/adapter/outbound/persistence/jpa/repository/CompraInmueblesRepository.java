package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.CompraInmueblesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CompraInmueblesRepository extends JpaRepository<CompraInmueblesEntity, UUID> {
    Optional<CompraInmueblesEntity> findByIdProyecto(UUID idProyecto);
}