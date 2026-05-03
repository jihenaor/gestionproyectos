package com.gestionproyectos.infrastructure.config;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.ProyectoEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.ProyectoJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Sembrado solo en desarrollo H2: {@code local} y nunca si {@code as400} está activo
 * (evita insertar en H2 cuando alguien pone {@code local,as400} o el perfil por defecto equivocado).
 * <p>
 * El REST {@code GET /v1/proyectos} lee la entidad JPA {@code PROYECTO} (esquema BDSUPER en local / AS400_LIBRARY en IBM i).
 */
@Slf4j
@Component
@Profile("local & !as400")
public class LocalSampleProyectosRunner implements ApplicationRunner {

    private final ProyectoJpaRepository proyectoJpaRepository;

    public LocalSampleProyectosRunner(ProyectoJpaRepository proyectoJpaRepository) {
        this.proyectoJpaRepository = proyectoJpaRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (proyectoJpaRepository.count() > 0) {
            return;
        }
        var e = new ProyectoEntity();
        e.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        // Debe cumplir CodigoProyecto: CCF + 3 dígitos + - + 2 dígitos + - + 5 dígitos
        e.setCodigoProyecto("CCF999-99-00001");
        e.setNombre("Proyecto demostración (H2 — perfil local)");
        e.setModalidadInversion("INF");
        e.setValorTotal(1_000_000L);
        e.setValorAprobado(1_000_000L);
        e.setJustificacion(
                "Sembrado automático al arrancar con SPRING_PROFILES_ACTIVE=local. "
                        + "Para ver datos de IBM i use el perfil as400 y la tabla JPA PROYECTO.");
        e.setEstado("BORRADOR");
        e.setFechaCreacion(LocalDate.now());
        e.setUltimaActualizacion(LocalDateTime.now());
        proyectoJpaRepository.save(e);
        log.info(
                "H2 local: insertado 1 proyecto de ejemplo en PROYECTO (esquema BDSUPER). "
                        + "Si esperabas filas de GP_PROYECTOS (scripts SQL), son tablas distintas al modelo JPA.");
    }
}
