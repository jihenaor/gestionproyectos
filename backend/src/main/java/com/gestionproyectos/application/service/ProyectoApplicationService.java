package com.gestionproyectos.application.service;

import com.gestionproyectos.application.dto.*;
import com.gestionproyectos.application.port.in.ProyectoUseCase;
import com.gestionproyectos.domain.model.*;
import com.gestionproyectos.domain.repository.ProyectoRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ProyectoApplicationService implements ProyectoUseCase {

    private static final Set<String> ESTRUCTURAS_REQUERIDAS = Set.of(
            "P-001A", "P-002A", "P-003A", "P-004C", "P-005A",
            "P-011A", "P-013A"
    );

    private final ProyectoRepository repository;

    public ProyectoApplicationService(ProyectoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaginatedResponse<ProyectoResponse> listarProyectos(int page, int size) {
        var proyectos = repository.findAll(page, size);
        var total = repository.count();
        var responses = proyectos.stream()
                .map(p -> ProyectoResponse.from(p, ESTRUCTURAS_REQUERIDAS))
                .toList();
        return PaginatedResponse.of(responses, page, size, total);
    }

    @Override
    public ProyectoResponse obtenerPorId(UUID id) {
        var proyecto = repository.findById(id)
                .orElseThrow(() -> new ProyectoNotFoundException("Proyecto no encontrado: " + id));
        return ProyectoResponse.from(proyecto, ESTRUCTURAS_REQUERIDAS);
    }

    @Override
    public ProyectoResponse crear(CrearProyectoCommand command) {
        var codigo = CodigoProyecto.of(command.codigo());

        if (repository.existsByCodigo(codigo)) {
            throw new ProyectoYaExisteException("Ya existe un proyecto con código: " + command.codigo());
        }

        var modalidad = ModalidadInversion.valueOf(command.modalidadInversion().toUpperCase());

        var proyecto = Proyecto.crear(
                command.nombre(),
                codigo,
                modalidad,
                Dinero.of(command.valorTotal()),
                Dinero.of(command.valorAprobado()),
                command.justificacion()
        );

        var saved = repository.save(proyecto);
        return ProyectoResponse.from(saved, ESTRUCTURAS_REQUERIDAS);
    }

    @Override
    public ProyectoResponse actualizar(UUID id, CrearProyectoCommand command) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ProyectoNotFoundException("Proyecto no encontrado: " + id));

        var updated = existing.toBuilder()
                .nombre(command.nombre())
                .modalidadInversion(ModalidadInversion.valueOf(command.modalidadInversion().toUpperCase()))
                .valorTotal(Dinero.of(command.valorTotal()))
                .valorAprobado(Dinero.of(command.valorAprobado()))
                .justificacion(command.justificacion())
                .build();

        var saved = repository.save(updated);
        return ProyectoResponse.from(saved, ESTRUCTURAS_REQUERIDAS);
    }

    @Override
    public void eliminar(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new ProyectoNotFoundException("Proyecto no encontrado: " + id);
        }
        repository.delete(id);
    }

    @Override
    public List<ProyectoResponse> buscarPorEstado(String estado) {
        return repository.findByEstado(estado).stream()
                .map(p -> ProyectoResponse.from(p, ESTRUCTURAS_REQUERIDAS))
                .toList();
    }
}