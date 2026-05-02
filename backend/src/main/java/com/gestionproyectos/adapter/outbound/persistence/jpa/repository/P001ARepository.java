package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.P001AEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface P001ARepository extends JpaRepository<P001AEntity, UUID> {

    Optional<P001AEntity> findByCodigoProyecto(String codigoProyecto);

    boolean existsByCodigoProyecto(String codigoProyecto);
}