package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.CronogramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CronogramaRepository extends JpaRepository<CronogramaEntity, UUID> {
    List<CronogramaEntity> findByIdProyecto(UUID idProyecto);
    void deleteByIdProyecto(UUID idProyecto);
}