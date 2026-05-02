package com.gestionproyectos.application.service.estructura;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.P001AEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.P001ARepository;
import com.gestionproyectos.application.dto.estructura.P001ADatosGenerales;
import com.gestionproyectos.application.service.mapper.ProyectoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EstructuraPersistenceService {

    private final P001ARepository p001ARepository;
    private final ProyectoMapper mapper;

    public EstructuraPersistenceService(P001ARepository p001ARepository, ProyectoMapper mapper) {
        this.p001ARepository = p001ARepository;
        this.mapper = mapper;
    }

    public P001ADatosGenerales guardarP001A(P001ADatosGenerales datos) {
        Optional<P001AEntity> existente = p001ARepository.findByCodigoProyecto(datos.codigoProyecto());

        P001AEntity entity;
        if (existente.isPresent()) {
            entity = mapper.toP001AEntityUpdate(existente.get(), datos);
        } else {
            entity = mapper.toP001AEntity(datos);
        }

        P001AEntity guardado = p001ARepository.save(entity);
        return mapper.toP001ADTO(guardado);
    }

    @Transactional(readOnly = true)
    public Optional<P001ADatosGenerales> obtenerP001A(String codigoProyecto) {
        return p001ARepository.findByCodigoProyecto(codigoProyecto)
                .map(mapper::toP001ADTO);
    }

    public boolean existeP001A(String codigoProyecto) {
        return p001ARepository.existsByCodigoProyecto(codigoProyecto);
    }
}