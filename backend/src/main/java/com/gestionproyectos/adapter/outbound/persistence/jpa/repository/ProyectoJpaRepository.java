package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.ProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProyectoJpaRepository extends JpaRepository<ProyectoEntity, UUID> {

    Optional<ProyectoEntity> findByCodigoProyecto(String codigoProyecto);

    boolean existsByCodigoProyecto(String codigoProyecto);

    List<ProyectoEntity> findByEstado(String estado);
}
