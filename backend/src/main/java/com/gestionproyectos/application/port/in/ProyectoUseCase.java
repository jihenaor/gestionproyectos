package com.gestionproyectos.application.port.in;

import com.gestionproyectos.application.dto.CrearProyectoCommand;
import com.gestionproyectos.application.dto.ProyectoResponse;
import com.gestionproyectos.application.dto.PaginatedResponse;

import java.util.List;
import java.util.UUID;

/**
 * Puerto inbound - Caso de uso para gestión de proyectos.
 */
public interface ProyectoUseCase {

    PaginatedResponse<ProyectoResponse> listarProyectos(int page, int size);

    ProyectoResponse obtenerPorId(UUID id);

    ProyectoResponse crear(CrearProyectoCommand command);

    ProyectoResponse actualizar(UUID id, CrearProyectoCommand command);

    void eliminar(UUID id);

    List<ProyectoResponse> buscarPorEstado(String estado);
}