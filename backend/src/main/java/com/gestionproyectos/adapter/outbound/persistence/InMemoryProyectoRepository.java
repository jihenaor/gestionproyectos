package com.gestionproyectos.adapter.outbound.persistence;

import com.gestionproyectos.application.port.out.ProyectoPersistencePort;
import com.gestionproyectos.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryProyectoRepository implements ProyectoPersistencePort {

    private final Map<UUID, Proyecto> database = new ConcurrentHashMap<>();
    private final Map<String, UUID> codigoToUuid = new ConcurrentHashMap<>();

    @Override
    public Optional<Proyecto> findById(UUID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Proyecto> findByCodigo(CodigoProyecto codigo) {
        UUID uuid = codigoToUuid.get(codigo.value());
        if (uuid == null) return Optional.empty();
        return Optional.ofNullable(database.get(uuid));
    }

    @Override
    public List<Proyecto> findAll(int page, int size) {
        return database.values().stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @Override
    public List<Proyecto> findByEstado(String estado) {
        return database.values().stream()
                .filter(p -> p.estado().descripcion().equalsIgnoreCase(estado))
                .toList();
    }

    @Override
    public long count() {
        return database.size();
    }

    @Override
    public boolean existsByCodigo(CodigoProyecto codigo) {
        return codigoToUuid.containsKey(codigo.value());
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        codigoToUuid.values().removeIf(uuid -> uuid.equals(proyecto.id()));

        database.put(proyecto.id(), proyecto);
        codigoToUuid.put(proyecto.codigo().value(), proyecto.id());
        return proyecto;
    }

    @Override
    public void delete(UUID id) {
        Proyecto proyecto = database.get(id);
        if (proyecto != null) {
            codigoToUuid.remove(proyecto.codigo().value());
            database.remove(id);
        }
    }
}