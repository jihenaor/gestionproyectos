package com.gestionproyectos.adapter.outbound.persistence.jpa.repository;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.GpDatosGeneralesEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GpDatosGeneralesRepository extends JpaRepository<GpDatosGeneralesEntity, UUID> {

    Optional<GpDatosGeneralesEntity> findByIdProyecto(UUID idProyecto);

    @Query(
            """
            SELECT d FROM GpDatosGeneralesEntity d
            JOIN GpProyectoEntity p ON d.idProyecto = p.id
            WHERE p.codigoProyecto = :codigo
            """)
    Optional<GpDatosGeneralesEntity> findByCodigoProyecto(@Param("codigo") String codigo);

    @Query(
            """
            SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
            FROM GpDatosGeneralesEntity d
            JOIN GpProyectoEntity p ON d.idProyecto = p.id
            WHERE p.codigoProyecto = :codigo
            """)
    boolean existsByCodigoProyecto(@Param("codigo") String codigo);
}