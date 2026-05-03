package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.*;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.*;
import com.gestionproyectos.application.dto.estructura.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/estructuras")
@Tag(name = "Estructuras", description = "Gestión de estructuras P-001A a P-055A")
public class EstructuraController {

    private final GpProyectoRepository gpProyectoRepository;
    private final GpDatosGeneralesRepository gpDatosGeneralesRepository;
    private final CronogramaRepository cronogramaRepository;
    private final LocalizacionRepository localizacionRepository;

    public EstructuraController(
            GpProyectoRepository gpProyectoRepository,
            GpDatosGeneralesRepository gpDatosGeneralesRepository,
            CronogramaRepository cronogramaRepository,
            LocalizacionRepository localizacionRepository
    ) {
        this.gpProyectoRepository = gpProyectoRepository;
        this.gpDatosGeneralesRepository = gpDatosGeneralesRepository;
        this.cronogramaRepository = cronogramaRepository;
        this.localizacionRepository = localizacionRepository;
    }

    private Optional<UUID> getIdProyectoPorCodigo(String codigoProyecto) {
        return gpProyectoRepository.findByCodigoProyecto(codigoProyecto).map(GpProyectoEntity::getId);
    }

    @PostMapping("/P-001A")
    @Operation(summary = "Guardar Datos Generales (P-001A)")
    public ResponseEntity<P001ADatosGenerales> guardarP001A(@Valid @RequestBody P001ADatosGenerales datos) {
        Optional<GpProyectoEntity> gpOpt = gpProyectoRepository.findByCodigoProyecto(datos.codigoProyecto());
        if (gpOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        GpProyectoEntity gp = gpOpt.get();

        Optional<GpDatosGeneralesEntity> dgOpt = gpDatosGeneralesRepository.findByIdProyecto(gp.getId());
        GpDatosGeneralesEntity dg;
        if (dgOpt.isPresent()) {
            dg = dgOpt.get();
            dg.setObjetivosEspecificos(datos.objetivos());
            dg.setDescripcionObjetivo(datos.descripcionObjetivo());
            dg.setTiempoRecuperacion(datos.tiempoRecuperacion());
            dg.setTasaDescuento(datos.tasaDescuento());
            dg.setNumeroBeneficiarios(datos.numeroBeneficiarios());
            dg.setFecModificacion(LocalDateTime.now());
        } else {
            dg = new GpDatosGeneralesEntity();
            dg.setId(UUID.randomUUID());
            dg.setIdProyecto(gp.getId());
            dg.setObjetivosEspecificos(datos.objetivos());
            dg.setDescripcionObjetivo(datos.descripcionObjetivo());
            dg.setTiempoRecuperacion(datos.tiempoRecuperacion());
            dg.setTasaDescuento(datos.tasaDescuento());
            dg.setNumeroBeneficiarios(datos.numeroBeneficiarios());
            dg.setFecCreacion(LocalDateTime.now());
            dg.setFecModificacion(LocalDateTime.now());
        }
        gpDatosGeneralesRepository.save(dg);
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/P-001A/{codigoProyecto}")
    @Operation(summary = "Obtener Datos Generales (P-001A)")
    public ResponseEntity<P001ADatosGenerales> obtenerP001A(@PathVariable String codigoProyecto) {
        Optional<GpProyectoEntity> gpOpt = gpProyectoRepository.findByCodigoProyecto(codigoProyecto);
        if (gpOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<GpDatosGeneralesEntity> dgOpt = gpDatosGeneralesRepository.findByIdProyecto(gpOpt.get().getId());
        if (dgOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        GpDatosGeneralesEntity dg = dgOpt.get();
        GpProyectoEntity gp = gpOpt.get();
        P001ADatosGenerales response = new P001ADatosGenerales(
                gp.getCodigoProyecto(),
                gp.getDescripcionProyecto() != null ? gp.getDescripcionProyecto().substring(0, Math.min(200, gp.getDescripcionProyecto().length())) : "",
                gp.getModDeInversion() != null ? String.valueOf(gp.getModDeInversion()) : "1",
                gp.getValTotalProyecto(),
                gp.getValorAprVigencia(),
                gp.getJustificacion(),
                dg.getObjetivosEspecificos(),
                gp.getResolucionAEI(),
                gp.getNumActaAei() != null ? gp.getNumActaAei().toString() : null,
                gp.getFechaAprAei(),
                gp.getNumConsejeros(),
                dg.getTiempoRecuperacion(),
                dg.getTasaDescuento(),
                dg.getNumeroBeneficiarios(),
                dg.getDescripcionObjetivo()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/P-002A")
    @Operation(summary = "Guardar Cronograma (P-002A)")
    public ResponseEntity<P002ACronograma> guardarP002A(@Valid @RequestBody P002ACronograma datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cronogramaRepository.deleteByIdProyecto(idOpt.get());
        for (P002ACronograma.CronogramaActividad act : datos.actividades()) {
            CronogramaEntity entity = new CronogramaEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setTipoActividad(act.tipoActividad());
            entity.setDescripcionActividad(act.descripcionActividad());
            entity.setPorcentajeProyectado(act.porcentajeProyectado());
            entity.setFechaInicio(act.fechaInicio());
            entity.setFechaTerminacion(act.fechaTerminacion());
            entity.setUnidadMedida(act.unidadMedida());
            entity.setCantidadProgramada(act.cantidadProgramada());
            entity.setFechaCreacion(LocalDateTime.now());
            cronogramaRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-003A")
    @Operation(summary = "Guardar Localización (P-003A)")
    public ResponseEntity<P003ALocalizacion> guardarP003A(@Valid @RequestBody P003ALocalizacion datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<LocalizacionEntity> existente = localizacionRepository.findByIdProyecto(idOpt.get());
        LocalizacionEntity entity = existente.orElse(new LocalizacionEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setDepartamento(datos.departamento());
        entity.setMunicipio(datos.municipio());
        entity.setDireccion(datos.direccion());
        entity.setBarrio(datos.barrio());
        entity.setTelefono(datos.telefono());
        entity.setContacto(datos.contacto());
        entity.setFechaInicioOperacion(datos.fechaInicioOperacion());
        entity.setFechaCreacion(LocalDateTime.now());
        localizacionRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-004C")
    @Operation(summary = "Guardar Fuentes de Recursos (P-004C)")
    public ResponseEntity<P004CEstructuraFuenteRecursos> guardarP004C(@Valid @RequestBody P004CEstructuraFuenteRecursos datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-011A")
    @Operation(summary = "Guardar Cobertura Proyectada (P-011A)")
    public ResponseEntity<P011ACoberturaProyectada> guardarP011A(@Valid @RequestBody P011ACoberturaProyectada datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-011B")
    @Operation(summary = "Guardar Cobertura Ejecutada (P-011B)")
    public ResponseEntity<P011BCoberturaEjecutada> guardarP011B(@Valid @RequestBody P011BCoberturaEjecutada datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-012A")
    @Operation(summary = "Guardar Seguimiento (P-012A)")
    public ResponseEntity<P012ASeguimientoProyecto> guardarP012A(@Valid @RequestBody P012ASeguimientoProyecto datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-013A")
    @Operation(summary = "Guardar Infraestructura (P-013A)")
    public ResponseEntity<P013AAspectosInfraestructura> guardarP013A(@Valid @RequestBody P013AAspectosInfraestructura datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-023A")
    @Operation(summary = "Guardar Fondos de Crédito (P-023A)")
    public ResponseEntity<P023AAspectosFondosCredito> guardarP023A(@Valid @RequestBody P023AAspectosFondosCredito datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-024A")
    @Operation(summary = "Guardar Cartera por Edades (P-024A)")
    public ResponseEntity<P024ACarteraPorEdades> guardarP024A(@Valid @RequestBody P024ACarteraPorEdades datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-026A")
    @Operation(summary = "Guardar Arrendamiento (P-026A)")
    public ResponseEntity<P026AArrendamientoBienes> guardarP026A(@Valid @RequestBody P026AArrendamientoBienes datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-031A")
    @Operation(summary = "Guardar Comodato (P-031A)")
    public ResponseEntity<P031AComodatoBienes> guardarP031A(@Valid @RequestBody P031AComodatoBienes datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-034A")
    @Operation(summary = "Guardar Compra Bienes (P-034A)")
    public ResponseEntity<P034ACompraBienes> guardarP034A(@Valid @RequestBody P034ACompraBienes datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-040A")
    @Operation(summary = "Guardar Permuta (P-040A)")
    public ResponseEntity<P040APermutaBienes> guardarP040A(@Valid @RequestBody P040APermutaBienes datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-050A")
    @Operation(summary = "Guardar Negociación Acciones (P-050A)")
    public ResponseEntity<P050ANegociacionAcciones> guardarP050A(@Valid @RequestBody P050ANegociacionAcciones datos) {
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-055A")
    @Operation(summary = "Guardar Capitalizaciones (P-055A)")
    public ResponseEntity<P055ACapitalizaciones> guardarP055A(@Valid @RequestBody P055ACapitalizaciones datos) {
        return ResponseEntity.ok(datos);
    }
}