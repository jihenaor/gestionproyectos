package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.CoberturaProyectadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CoberturaProyectadaRepository extends JpaRepository<CoberturaProyectadaEntity, UUID> {
    List<CoberturaProyectadaEntity> findByIdProyecto(UUID idProyecto);
    void deleteByIdProyecto(UUID idProyecto);
}