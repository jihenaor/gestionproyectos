package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.adapter.outbound.persistence.jpa.entity.*;
import com.gestionproyectos.adapter.outbound.persistence.jpa.repository.*;
import com.gestionproyectos.application.dto.estructura.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
    private final FuenteRecursosRepository fuenteRecursosRepository;
    private final CoberturaProyectadaRepository coberturaProyectadaRepository;
    private final CoberturaEjecutadaRepository coberturaEjecutadaRepository;
    private final SeguimientoRepository seguimientoRepository;
    private final InfraestructuraRepository infraestructuraRepository;
    private final FondosCreditoRepository fondosCreditoRepository;
    private final CarteraEdadesRepository carteraEdadesRepository;
    private final ArrendamientoRepository arrendamientoRepository;
    private final ComodatoRepository comodatoRepository;
    private final CompraInmueblesRepository compraInmueblesRepository;
    private final PermutaRepository permutaRepository;
    private final NegociacionAccionesRepository negociacionAccionesRepository;
    private final CapitalizacionesRepository capitalizacionesRepository;

    public EstructuraController(
            GpProyectoRepository gpProyectoRepository,
            GpDatosGeneralesRepository gpDatosGeneralesRepository,
            CronogramaRepository cronogramaRepository,
            LocalizacionRepository localizacionRepository,
            FuenteRecursosRepository fuenteRecursosRepository,
            CoberturaProyectadaRepository coberturaProyectadaRepository,
            CoberturaEjecutadaRepository coberturaEjecutadaRepository,
            SeguimientoRepository seguimientoRepository,
            InfraestructuraRepository infraestructuraRepository,
            FondosCreditoRepository fondosCreditoRepository,
            CarteraEdadesRepository carteraEdadesRepository,
            ArrendamientoRepository arrendamientoRepository,
            ComodatoRepository comodatoRepository,
            CompraInmueblesRepository compraInmueblesRepository,
            PermutaRepository permutaRepository,
            NegociacionAccionesRepository negociacionAccionesRepository,
            CapitalizacionesRepository capitalizacionesRepository
    ) {
        this.gpProyectoRepository = gpProyectoRepository;
        this.gpDatosGeneralesRepository = gpDatosGeneralesRepository;
        this.cronogramaRepository = cronogramaRepository;
        this.localizacionRepository = localizacionRepository;
        this.fuenteRecursosRepository = fuenteRecursosRepository;
        this.coberturaProyectadaRepository = coberturaProyectadaRepository;
        this.coberturaEjecutadaRepository = coberturaEjecutadaRepository;
        this.seguimientoRepository = seguimientoRepository;
        this.infraestructuraRepository = infraestructuraRepository;
        this.fondosCreditoRepository = fondosCreditoRepository;
        this.carteraEdadesRepository = carteraEdadesRepository;
        this.arrendamientoRepository = arrendamientoRepository;
        this.comodatoRepository = comodatoRepository;
        this.compraInmueblesRepository = compraInmueblesRepository;
        this.permutaRepository = permutaRepository;
        this.negociacionAccionesRepository = negociacionAccionesRepository;
        this.capitalizacionesRepository = capitalizacionesRepository;
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

    @GetMapping("/P-002A/{codigoProyecto}")
    @Operation(summary = "Obtener Cronograma (P-002A)")
    public ResponseEntity<P002ACronograma> obtenerP002A(@PathVariable String codigoProyecto) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(codigoProyecto);
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CronogramaEntity> actividades = cronogramaRepository.findByIdProyecto(idOpt.get());
        List<P002ACronograma.CronogramaActividad> items = actividades.stream()
                .map(a -> new P002ACronograma.CronogramaActividad(
                        a.getTipoActividad(),
                        a.getDescripcionActividad(),
                        a.getPorcentajeProyectado(),
                        a.getFechaInicio(),
                        a.getFechaTerminacion(),
                        a.getUnidadMedida(),
                        a.getCantidadProgramada()))
                .toList();
        return ResponseEntity.ok(new P002ACronograma(codigoProyecto, items, null));
    }

    @GetMapping("/P-003A/{codigoProyecto}")
    @Operation(summary = "Obtener Localización (P-003A)")
    public ResponseEntity<P003ALocalizacion> obtenerP003A(@PathVariable String codigoProyecto) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(codigoProyecto);
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<LocalizacionEntity> locOpt = localizacionRepository.findByIdProyecto(idOpt.get());
        if (locOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LocalizacionEntity loc = locOpt.get();
        String coordenadas = null;
        if (loc.getLatitud() != null && loc.getLongitud() != null) {
            coordenadas = loc.getLatitud() + "," + loc.getLongitud();
        }
        P003ALocalizacion response = new P003ALocalizacion(
                codigoProyecto,
                loc.getDepartamento(),
                loc.getMunicipio(),
                loc.getDireccion(),
                loc.getBarrio(),
                loc.getTelefono(),
                loc.getContacto(),
                loc.getFechaInicioOperacion(),
                loc.getLatitud() != null ? loc.getLatitud().doubleValue() : null,
                loc.getLongitud() != null ? loc.getLongitud().doubleValue() : null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/proyecto/{codigoProyecto}")
    @Operation(summary = "Obtener estructuras completadas por proyecto")
    public ResponseEntity<java.util.Map<String, Boolean>> obtenerEstructurasPorProyecto(@PathVariable String codigoProyecto) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(codigoProyecto);
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        java.util.Map<String, Boolean> result = new java.util.HashMap<>();
        result.put("P-001A", gpDatosGeneralesRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-002A", !cronogramaRepository.findByIdProyecto(idOpt.get()).isEmpty());
        result.put("P-003A", localizacionRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-004C", !fuenteRecursosRepository.findByIdProyecto(idOpt.get()).isEmpty());
        result.put("P-011A", !coberturaProyectadaRepository.findByIdProyecto(idOpt.get()).isEmpty());
        result.put("P-012A", !seguimientoRepository.findByIdProyecto(idOpt.get()).isEmpty());
        result.put("P-013A", infraestructuraRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-023A", !fondosCreditoRepository.findByIdProyecto(idOpt.get()).isEmpty());
        result.put("P-024A", !carteraEdadesRepository.findByIdProyecto(idOpt.get()).isEmpty());
        result.put("P-026A", arrendamientoRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-031A", comodatoRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-034A", compraInmueblesRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-040A", permutaRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-050A", negociacionAccionesRepository.findByIdProyecto(idOpt.get()).isPresent());
        result.put("P-055A", capitalizacionesRepository.findByIdProyecto(idOpt.get()).isPresent());
        return ResponseEntity.ok(result);
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
        if (datos.latitude() != null) {
            entity.setLatitud(BigDecimal.valueOf(datos.latitude()));
        }
        if (datos.longitude() != null) {
            entity.setLongitud(BigDecimal.valueOf(datos.longitude()));
        }
        entity.setFechaCreacion(LocalDateTime.now());
        localizacionRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-004C")
    @Operation(summary = "Guardar Fuentes de Recursos (P-004C)")
    public ResponseEntity<P004CEstructuraFuenteRecursos> guardarP004C(@Valid @RequestBody P004CEstructuraFuenteRecursos datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        fuenteRecursosRepository.deleteByIdProyecto(idOpt.get());
        for (P004CEstructuraFuenteRecursos.FuenteRecurso fuente : datos.fuentes()) {
            FuenteRecursosEntity entity = new FuenteRecursosEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setCodFuente(fuente.codigoFuente());
            entity.setDescFuente(fuente.nombreFuente());
            entity.setValorAsignado(fuente.valor());
            entity.setPorcentaje(fuente.porcentaje());
            entity.setTipoRecurso(fuente.tipoRecurso());
            entity.setCentroCosto(fuente.centroCosto());
            entity.setFechaCreacion(LocalDateTime.now());
            fuenteRecursosRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-011A")
    @Operation(summary = "Guardar Cobertura Proyectada (P-011A)")
    public ResponseEntity<P011ACoberturaProyectada> guardarP011A(@Valid @RequestBody P011ACoberturaProyectada datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        coberturaProyectadaRepository.deleteByIdProyecto(idOpt.get());
        for (P011ACoberturaProyectada.CoberturaProyectadaItem item : datos.coberturas()) {
            CoberturaProyectadaEntity entity = new CoberturaProyectadaEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setCodCategoria(Integer.parseInt(item.codigoCategoria()));
            entity.setCantidadBeneficiarios(item.cantidadBeneficiarios());
            entity.setValorPerCapita(item.valorPerCapita());
            entity.setAnio1(item.anio1());
            entity.setAnio2(item.anio2());
            entity.setAnio3(item.anio3());
            entity.setAnio4(item.anio4());
            entity.setAnio5(item.anio5());
            entity.setFechaCreacion(LocalDateTime.now());
            coberturaProyectadaRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-011B")
    @Operation(summary = "Guardar Cobertura Ejecutada (P-011B)")
    public ResponseEntity<P011BCoberturaEjecutada> guardarP011B(@Valid @RequestBody P011BCoberturaEjecutada datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        coberturaEjecutadaRepository.deleteByIdProyecto(idOpt.get());
        for (P011BCoberturaEjecutada.CoberturaEjecutadaItem item : datos.coberturas()) {
            CoberturaEjecutadaEntity entity = new CoberturaEjecutadaEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setCodCategoria(Integer.parseInt(item.codigoCategoria()));
            entity.setCantidadBeneficiarios(item.cantidadBeneficiarios());
            entity.setValorPerCapita(item.valorPerCapita());
            entity.setAnio1Ejecutado(item.anio1Ejecutado());
            entity.setAnio2Ejecutado(item.anio2Ejecutado());
            entity.setAnio3Ejecutado(item.anio3Ejecutado());
            entity.setAnio4Ejecutado(item.anio4Ejecutado());
            entity.setAnio5Ejecutado(item.anio5Ejecutado());
            entity.setObservaciones(datos.observaciones());
            entity.setFechaCreacion(LocalDateTime.now());
            coberturaEjecutadaRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-012A")
    @Operation(summary = "Guardar Seguimiento (P-012A)")
    public ResponseEntity<P012ASeguimientoProyecto> guardarP012A(@Valid @RequestBody P012ASeguimientoProyecto datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SeguimientoEntity> existing = seguimientoRepository.findByIdProyectoAndPeriodoReporte(idOpt.get(), datos.periodoReporte());
        seguimientoRepository.deleteAll(existing);
        for (P012ASeguimientoProyecto.SeguimientoItem item : datos.seguimientos()) {
            SeguimientoEntity entity = new SeguimientoEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setPeriodoReporte(datos.periodoReporte());
            entity.setTipoActividad(item.tipoActividad());
            entity.setDescripcionActividad(item.descripcionActividad());
            entity.setPorcentajeEjecutado(item.porcentajeEjecutado());
            entity.setValorPlaneado(item.valorPlaneado());
            entity.setValorEjecutado(item.valorEjecutado());
            entity.setCostoActual(item.costoActual());
            entity.setValorPagado(item.valorPagado());
            entity.setValorGanado(item.valorGanado());
            entity.setCantidadEjecucionFisica(item.cantidadEjecucionFisica());
            entity.setFechaInicio(item.fechaInicio());
            entity.setFechaTerminacion(item.fechaTerminacion());
            entity.setObservaciones(item.observaciones());
            entity.setFechaCreacion(LocalDateTime.now());
            seguimientoRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-013A")
    @Operation(summary = "Guardar Infraestructura (P-013A)")
    public ResponseEntity<P013AAspectosInfraestructura> guardarP013A(@Valid @RequestBody P013AAspectosInfraestructura datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<InfraestructuraEntity> existente = infraestructuraRepository.findByIdProyecto(idOpt.get());
        InfraestructuraEntity entity = existente.orElse(new InfraestructuraEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setInterventoriaSupervision(datos.interventoriaSupervision());
        entity.setValorTotalInterventoria(datos.valorTotalInterventoria());
        entity.setLicenciaConstruccion(datos.licenciaConstruccion());
        entity.setEntCompetente(datos.entidadCompetente());
        entity.setNumRadicadoSolicitudLicencia(datos.numRadicadoLicencia());
        entity.setFechaRadicacionSolicitudLicencia(datos.fechaRadicacionLicencia());
        entity.setNumeroLicencia(datos.numeroLicencia());
        entity.setFechaLicencia(datos.fechaLicencia());
        entity.setVigenciaLicencia(datos.vigenciaLicencia());
        entity.setServiciosPublicos(datos.serviciosPublicos());
        entity.setFechaRadicacionAaa(datos.fechaRadicacionAAA());
        entity.setNumRadicadoSolicitudAaa(datos.numRadicadoAAA());
        entity.setFechaExpedicionAaa(datos.fechaExpedicionAAA());
        entity.setNumDisponibilidadAaa(datos.numDisponibilidadAAA());
        entity.setVigenciaAaa(datos.vigenciaAAA());
        entity.setFechaRadicacionEea(datos.fechaRadicacionEEA());
        entity.setNumRadicadoSolicitudEea(datos.numRadicadoEEA());
        entity.setFechaExpedicionEea(datos.fechaExpedicionEEA());
        entity.setNumDisponibilidadEea(datos.numDisponibilidadEEA());
        entity.setVigenciaEea(datos.vigenciaEEA());
        entity.setFechaRadicacionGna(datos.fechaRadicacionGNA());
        entity.setNumRadicadoSolicitudGna(datos.numRadicadoGNA());
        entity.setFechaExpedicionGna(datos.fechaExpedicionGNA());
        entity.setNumDisponibilidadGna(datos.numDisponibilidadGNA());
        entity.setVigenciaGna(datos.vigenciaGNA());
        entity.setProyeccionGeneracionEmpleo(datos.proyeccionGeneracionEmpleo());
        entity.setFechaCreacion(LocalDateTime.now());
        infraestructuraRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-023A")
    @Operation(summary = "Guardar Fondos de Crédito (P-023A)")
    public ResponseEntity<P023AAspectosFondosCredito> guardarP023A(@Valid @RequestBody P023AAspectosFondosCredito datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        fondosCreditoRepository.deleteByIdProyecto(idOpt.get());
        for (P023AAspectosFondosCredito.FondoCreditoItem item : datos.items()) {
            FondosCreditoEntity entity = new FondosCreditoEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setModalidadCredito(item.modalidadCredito());
            entity.setCodCategoria(Integer.parseInt(item.codigoCategoria()));
            entity.setTasaInteresMinima(item.tasaInteresMinima());
            entity.setTasaInteresMaxima(item.tasaInteresMaxima());
            entity.setCantCreditos(item.cantCreditos());
            entity.setValMontoCreditos(item.valMontoCreditos());
            entity.setPlazoCredito(item.plazoCredito());
            entity.setPorcentajeSubsidio(item.porcentajeSubsidio());
            entity.setFechaCreacion(LocalDateTime.now());
            fondosCreditoRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-024A")
    @Operation(summary = "Guardar Cartera por Edades (P-024A)")
    public ResponseEntity<P024ACarteraPorEdades> guardarP024A(@Valid @RequestBody P024ACarteraPorEdades datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carteraEdadesRepository.deleteByIdProyecto(idOpt.get());
        for (P024ACarteraPorEdades.CarteraItem item : datos.items()) {
            CarteraEdadesEntity entity = new CarteraEdadesEntity();
            entity.setId(UUID.randomUUID());
            entity.setIdProyecto(idOpt.get());
            entity.setRanEdad(item.rangoEdad());
            entity.setEdadCartera(item.edadCartera());
            entity.setModalidadCredito(item.modalidadCredito());
            entity.setCodCategoria(Integer.parseInt(item.codigoCategoria()));
            entity.setCantCreditos(item.cantCreditos());
            entity.setValorTotalMontoCartera(item.valorTotalMontoCartera());
            entity.setFechaCreacion(LocalDateTime.now());
            carteraEdadesRepository.save(entity);
        }
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-026A")
    @Operation(summary = "Guardar Arrendamiento (P-026A)")
    public ResponseEntity<P026AArrendamientoBienes> guardarP026A(@Valid @RequestBody P026AArrendamientoBienes datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<ArrendamientoEntity> existente = arrendamientoRepository.findByIdProyecto(idOpt.get());
        ArrendamientoEntity entity = existente.orElse(new ArrendamientoEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setFecCertTradicionLibertad(datos.fechaCertTradicionLibertad());
        entity.setFecAvaluo(datos.fechaAvaluo());
        entity.setPerito(datos.perito());
        entity.setValorAvaluo(datos.valorAvaluo());
        entity.setValCanonMensual(datos.valorCanonMensual());
        entity.setTmpContrato(datos.tiempoContrato());
        entity.setDestinacionInmueble(datos.destinacionInmueble());
        entity.setUsoAutorizado(datos.usoAutorizado());
        entity.setFechaCreacion(LocalDateTime.now());
        arrendamientoRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-031A")
    @Operation(summary = "Guardar Comodato (P-031A)")
    public ResponseEntity<P031AComodatoBienes> guardarP031A(@Valid @RequestBody P031AComodatoBienes datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<ComodatoEntity> existente = comodatoRepository.findByIdProyecto(idOpt.get());
        ComodatoEntity entity = existente.orElse(new ComodatoEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setFecCertTradicionLibertad(datos.fechaCertTradicionLibertad());
        entity.setDestinacionInmueble(datos.destinacionInmueble());
        entity.setUsoAutorizado(datos.usoAutorizado());
        entity.setFechaCreacion(LocalDateTime.now());
        comodatoRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-034A")
    @Operation(summary = "Guardar Compra Bienes (P-034A)")
    public ResponseEntity<P034ACompraBienes> guardarP034A(@Valid @RequestBody P034ACompraBienes datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<CompraInmueblesEntity> existente = compraInmueblesRepository.findByIdProyecto(idOpt.get());
        CompraInmueblesEntity entity = existente.orElse(new CompraInmueblesEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setFecCertTradicionLibertad(datos.fechaCertTradicionLibertad());
        entity.setFecAvaluo(datos.fechaAvaluo());
        entity.setPerito(datos.perito());
        entity.setValorAvaluo(datos.valorAvaluo());
        entity.setDestinacionInmueble(datos.destinacionInmueble());
        entity.setUsoAutorizado(datos.usoAutorizado());
        entity.setFechaCreacion(LocalDateTime.now());
        compraInmueblesRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-040A")
    @Operation(summary = "Guardar Permuta (P-040A)")
    public ResponseEntity<P040APermutaBienes> guardarP040A(@Valid @RequestBody P040APermutaBienes datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<PermutaEntity> existente = permutaRepository.findByIdProyecto(idOpt.get());
        PermutaEntity entity = existente.orElse(new PermutaEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setFecCertTradicionLibertad(datos.fechaCertTradicionLibertad());
        entity.setFecAvaluoRecibe(datos.fechaAvaluoRecibe());
        entity.setFecAvaluoEntrega(datos.fechaAvaluoEntrega());
        entity.setAvaluadorRecibe(datos.avaluadorRecibe());
        entity.setAvaluadorEntrega(datos.avaluadorEntrega());
        entity.setValAvaluoRecibe(datos.valorAvaluoRecibe());
        entity.setValAvaluoEntrega(datos.valorAvaluoEntrega());
        entity.setDestinacionInmueble(datos.destinacionInmueble());
        entity.setUsoAutorizado(datos.usoAutorizado());
        entity.setValLibros(datos.valorEnLibros());
        entity.setUtilidadPerdida(datos.utilidadPerdida());
        entity.setOrigenRecursos(datos.origenRecursos());
        entity.setDestinacionRecursos(datos.destinacion());
        entity.setFechaCreacion(LocalDateTime.now());
        permutaRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-050A")
    @Operation(summary = "Guardar Negociación Acciones (P-050A)")
    public ResponseEntity<P050ANegociacionAcciones> guardarP050A(@Valid @RequestBody P050ANegociacionAcciones datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<NegociacionAccionesEntity> existente = negociacionAccionesRepository.findByIdProyecto(idOpt.get());
        NegociacionAccionesEntity entity = existente.orElse(new NegociacionAccionesEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setNumAccionesCuotas(datos.numAccionesCuotas());
        entity.setValorAccionesCuotas(datos.valorAccionesCuotas());
        entity.setPorcentajeParticipacion(datos.porcentajeParticipacion());
        entity.setValorNominalAcciones(datos.valorNominalAcciones());
        entity.setValorMercadoAcciones(datos.valorMercadoAcciones());
        entity.setFechaCreacion(LocalDateTime.now());
        negociacionAccionesRepository.save(entity);
        return ResponseEntity.ok(datos);
    }

    @PostMapping("/P-055A")
    @Operation(summary = "Guardar Capitalizaciones (P-055A)")
    public ResponseEntity<P055ACapitalizaciones> guardarP055A(@Valid @RequestBody P055ACapitalizaciones datos) {
        Optional<UUID> idOpt = getIdProyectoPorCodigo(datos.codigoProyecto());
        if (idOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<CapitalizacionesEntity> existente = capitalizacionesRepository.findByIdProyecto(idOpt.get());
        CapitalizacionesEntity entity = existente.orElse(new CapitalizacionesEntity());
        entity.setId(entity.getId() != null ? entity.getId() : UUID.randomUUID());
        entity.setIdProyecto(idOpt.get());
        entity.setNumAccionesCuotas(datos.numAccionesCuotas());
        entity.setValorAccionesCuotas(datos.valorAccionesCuotas());
        entity.setPorcentajeParticipacion(datos.porcentajeParticipacion());
        entity.setValorNominalAcciones(datos.valorNominalAcciones());
        entity.setValorMercadoAcciones(datos.valorMercadoAcciones());
        entity.setFechaCreacion(LocalDateTime.now());
        capitalizacionesRepository.save(entity);
        return ResponseEntity.ok(datos);
    }
}