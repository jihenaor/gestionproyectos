package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.CarteraEdadesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CarteraEdadesRepository extends JpaRepository<CarteraEdadesEntity, UUID> {
    List<CarteraEdadesEntity> findByIdProyecto(UUID idProyecto);
    void deleteByIdProyecto(UUID idProyecto);
}