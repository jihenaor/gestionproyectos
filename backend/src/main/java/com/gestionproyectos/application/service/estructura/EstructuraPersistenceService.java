package com.gestionproyectos.application.service.estructura;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.GpProyectoEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.GpDatosGeneralesEntity;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.GpProyectoRepository;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.GpDatosGeneralesRepository;
import com.gestionproyectos.application.dto.estructura.P001ADatosGenerales;
import com.gestionproyectos.application.service.mapper.ProyectoMapper;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EstructuraPersistenceService {

    private final GpProyectoRepository gpProyectoRepository;
    private final GpDatosGeneralesRepository gpDatosGeneralesRepository;
    private final ProyectoMapper mapper;

    public EstructuraPersistenceService(
            GpProyectoRepository gpProyectoRepository,
            GpDatosGeneralesRepository gpDatosGeneralesRepository,
            ProyectoMapper mapper) {
        this.gpProyectoRepository = gpProyectoRepository;
        this.gpDatosGeneralesRepository = gpDatosGeneralesRepository;
        this.mapper = mapper;
    }

    public P001ADatosGenerales guardarP001A(P001ADatosGenerales datos) {
        Optional<GpProyectoEntity> gpOpt = gpProyectoRepository.findByCodigoProyecto(datos.codigoProyecto());

        GpProyectoEntity gp;
        if (gpOpt.isPresent()) {
            gp = gpOpt.get();
            mapper.mergeGpProyectoFromP001ADto(gp, datos);
            gp = gpProyectoRepository.save(gp);

            Optional<GpDatosGeneralesEntity> dgOpt = gpDatosGeneralesRepository.findByIdProyecto(gp.getId());
            if (dgOpt.isPresent()) {
                GpDatosGeneralesEntity dg = dgOpt.get();
                mapper.mergeGpDatosGeneralesFromDto(dg, datos);
                gpDatosGeneralesRepository.save(dg);
                return mapper.toP001ADTO(gp, dg);
            }
            GpDatosGeneralesEntity creada = mapper.newGpDatosGeneralesFromDto(datos, gp.getId());
            gpDatosGeneralesRepository.save(creada);
            return mapper.toP001ADTO(gp, creada);
        }

        UUID idProyecto = UUID.randomUUID();
        gp = mapper.newGpProyectoFromP001ADto(datos, idProyecto);
        gp = gpProyectoRepository.save(gp);

        GpDatosGeneralesEntity dg = mapper.newGpDatosGeneralesFromDto(datos, gp.getId());
        gpDatosGeneralesRepository.save(dg);

        return mapper.toP001ADTO(gp, dg);
    }

    @Transactional(readOnly = true)
    public Optional<P001ADatosGenerales> obtenerP001A(String codigoProyecto) {
        return gpProyectoRepository
                .findByCodigoProyecto(codigoProyecto)
                .map(gp -> mapper.toP001ADTO(gp, gpDatosGeneralesRepository.findByIdProyecto(gp.getId()).orElse(null)));
    }

    public boolean existeP001A(String codigoProyecto) {
        return gpDatosGeneralesRepository.existsByCodigoProyecto(codigoProyecto);
    }
}