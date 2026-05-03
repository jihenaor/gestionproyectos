package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.CoberturaEjecutadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CoberturaEjecutadaRepository extends JpaRepository<CoberturaEjecutadaEntity, UUID> {
    List<CoberturaEjecutadaEntity> findByIdProyecto(UUID idProyecto);
    void deleteByIdProyecto(UUID idProyecto);
}