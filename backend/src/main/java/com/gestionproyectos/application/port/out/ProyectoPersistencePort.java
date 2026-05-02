package com.gestionproyectos.application.port.out;

import com.gestionproyectos.domain.model.Proyecto;
import com.gestionproyectos.domain.model.CodigoProyecto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto outbound - Puerto de persistencia para proyectos.
 */
public interface ProyectoPersistencePort {

    Optional<Proyecto> findById(UUID id);

    Optional<Proyecto> findByCodigo(CodigoProyecto codigo);

    List<Proyecto> findAll(int page, int size);

    List<Proyecto> findByEstado(String estado);

    long count();

    boolean existsByCodigo(CodigoProyecto codigo);

    Proyecto save(Proyecto proyecto);

    void delete(UUID id);
}