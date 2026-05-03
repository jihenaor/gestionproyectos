package com.gestionproyectos.domain.repository;

import com.gestionproyectos.domain.model.Proyecto;
import com.gestionproyectos.domain.model.CodigoProyecto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProyectoRepository {
    Optional<Proyecto> findById(UUID id);
    Optional<Proyecto> findByCodigo(CodigoProyecto codigo);
    List<Proyecto> findAll(int page, int size);
    long count();
    Proyecto save(Proyecto proyecto);
    void delete(UUID id);
    boolean existsByCodigo(CodigoProyecto codigo);
    List<Proyecto> findByEstado(String estado);
}