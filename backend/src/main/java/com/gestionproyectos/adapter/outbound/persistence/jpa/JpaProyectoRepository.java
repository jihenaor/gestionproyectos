package com.gestionproyectos.adapter.outbound.persistence.jpa;

import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.ProyectoJpaRepository;
import com.gestionproyectos.application.service.mapper.ProyectoMapper;
import com.gestionproyectos.domain.model.CodigoProyecto;
import com.gestionproyectos.domain.model.Proyecto;
import com.gestionproyectos.domain.repository.ProyectoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaProyectoRepository implements ProyectoRepository {

    private final ProyectoJpaRepository jpa;
    private final ProyectoMapper mapper;

    public JpaProyectoRepository(ProyectoJpaRepository jpa, ProyectoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Optional<Proyecto> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Proyecto> findByCodigo(CodigoProyecto codigo) {
        return jpa.findByCodigoProyecto(codigo.value()).map(mapper::toDomain);
    }

    @Override
    public List<Proyecto> findAll(int page, int size) {
        return jpa.findAll(PageRequest.of(page, size)).getContent().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return jpa.count();
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        return jpa.findById(proyecto.id())
                .map(entity -> {
                    mapper.mergeProyectoIntoEntity(entity, proyecto);
                    return mapper.toDomain(jpa.save(entity));
                })
                .orElseGet(() -> mapper.toDomain(jpa.save(mapper.toEntity(proyecto))));
    }

    @Override
    public void delete(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existsByCodigo(CodigoProyecto codigo) {
        return jpa.existsByCodigoProyecto(codigo.value());
    }

    @Override
    public List<Proyecto> findByEstado(String estado) {
        return jpa.findByEstado(estado).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
