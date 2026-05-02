package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.dto.estructura.*;
import com.gestionproyectos.application.service.estructura.EstructuraPersistenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/v1/estructuras")
@Tag(name = "Estructuras", description = "Gestión de estructuras P-001A a P-056A")
public class EstructuraController {

    private final Map<String, Map<String, Object>> estructurasAlmacenadas = new HashMap<>();
    private final EstructuraPersistenceService persistenceService;

    public EstructuraController(EstructuraPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @PostMapping("/P-001A")
    @Operation(summary = "Guardar P-001A - Datos Generales del Proyecto")
    public ResponseEntity<Map<String, Object>> guardarP001A(@Valid @RequestBody P001ADatosGenerales datos) {
        P001ADatosGenerales guardado = persistenceService.guardarP001A(datos);

        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-001A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("fecha", LocalDateTime.now().toString());
        result.put("nombreProyecto", guardado.nombreProyecto());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-002A")
    @Operation(summary = "Guardar P-002A - Cronograma del Proyecto")
    public ResponseEntity<Map<String, Object>> guardarP002A(@Valid @RequestBody P002ACronograma cronograma) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-002A");
        result.put("codigoProyecto", cronograma.codigoProyecto());
        result.put("estado", "guardado");
        result.put("numActividades", cronograma.actividades().size());
        result.put("fecha", LocalDateTime.now().toString());

        Map<String, Object> estructuraData = new HashMap<>();
        estructuraData.put("actividades", cronograma.actividades());
        estructuraData.put("porcentajeTotal", cronograma.porcentajeTotal());

        estructurasAlmacenadas.put(cronograma.codigoProyecto() + "-P-002A", estructuraData);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-003A")
    @Operation(summary = "Guardar P-003A - Localización del Proyecto")
    public ResponseEntity<Map<String, Object>> guardarP003A(@Valid @RequestBody P003ALocalizacion localizacion) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-003A");
        result.put("codigoProyecto", localizacion.codigoProyecto());
        result.put("estado", "guardado");
        result.put("departamento", localizacion.departamento());
        result.put("municipio", localizacion.municipio());

        estructurasAlmacenadas.put(localizacion.codigoProyecto() + "-P-003A", new HashMap<>(Map.of(
                "departamento", localizacion.departamento(),
                "municipio", localizacion.municipio(),
                "direccion", localizacion.direccion() != null ? localizacion.direccion() : ""
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-004C")
    @Operation(summary = "Guardar P-004C - Estructura Fuente de Recursos")
    public ResponseEntity<Map<String, Object>> guardarP004C(@Valid @RequestBody P004CEstructuraFuenteRecursos fuentes) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-004C");
        result.put("codigoProyecto", fuentes.codigoProyecto());
        result.put("estado", "guardado");
        result.put("numFuentes", fuentes.fuentes().size());

        long totalValor = fuentes.fuentes().stream()
                .mapToLong(P004CEstructuraFuenteRecursos.FuenteRecurso::valor)
                .sum();
        result.put("valorTotal", totalValor);

        estructurasAlmacenadas.put(fuentes.codigoProyecto() + "-P-004C", new HashMap<>(Map.of(
                "fuentes", fuentes.fuentes(),
                "total", totalValor
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-011A")
    @Operation(summary = "Guardar P-011A - Cobertura Proyectada")
    public ResponseEntity<Map<String, Object>> guardarP011A(@Valid @RequestBody P011ACoberturaProyectada cobertura) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-011A");
        result.put("codigoProyecto", cobertura.codigoProyecto());
        result.put("estado", "guardado");
        result.put("numCoberturas", cobertura.coberturas().size());

        int totalBeneficiarios = cobertura.coberturas().stream()
                .mapToInt(P011ACoberturaProyectada.CoberturaProyectadaItem::cantidadBeneficiarios)
                .sum();
        result.put("totalBeneficiarios", totalBeneficiarios);

        estructurasAlmacenadas.put(cobertura.codigoProyecto() + "-P-011A", new HashMap<>(Map.of(
                "coberturas", cobertura.coberturas()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-011B")
    @Operation(summary = "Guardar P-011B - Cobertura Ejecutada")
    public ResponseEntity<Map<String, Object>> guardarP011B(@Valid @RequestBody P011BCoberturaEjecutada cobertura) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-011B");
        result.put("codigoProyecto", cobertura.codigoProyecto());
        result.put("estado", "guardado");

        estructurasAlmacenadas.put(cobertura.codigoProyecto() + "-P-011B", new HashMap<>(Map.of(
                "coberturas", cobertura.coberturas(),
                "observaciones", cobertura.observaciones() != null ? cobertura.observaciones() : ""
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-012A")
    @Operation(summary = "Guardar P-012A - Seguimiento del Proyecto")
    public ResponseEntity<Map<String, Object>> guardarP012A(@Valid @RequestBody P012ASeguimientoProyecto seguimiento) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-012A");
        result.put("codigoProyecto", seguimiento.codigoProyecto());
        result.put("periodoReporte", seguimiento.periodoReporte());
        result.put("estado", "guardado");
        result.put("numItems", seguimiento.seguimientos().size());

        estructurasAlmacenadas.put(seguimiento.codigoProyecto() + "-P-012A", new HashMap<>(Map.of(
                "periodoReporte", seguimiento.periodoReporte(),
                "seguimientos", seguimiento.seguimientos()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-013A")
    @Operation(summary = "Guardar P-013A - Aspectos Específicos Infraestructura")
    public ResponseEntity<Map<String, Object>> guardarP013A(@Valid @RequestBody P013AAspectosInfraestructura aspectos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-013A");
        result.put("codigoProyecto", aspectos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("interventoria", aspectos.interventoriaSupervision());
        result.put("empleosProyectados", aspectos.proyeccionGeneracionEmpleo());

        estructurasAlmacenadas.put(aspectos.codigoProyecto() + "-P-013A", new HashMap<>(Map.of(
                "interventoria", aspectos.interventoriaSupervision(),
                "valorInterventoria", aspectos.valorTotalInterventoria(),
                "licencia", aspectos.licenciaConstruccion(),
                "servicios", aspectos.serviciosPublicos(),
                "empleos", aspectos.proyeccionGeneracionEmpleo()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-023A")
    @Operation(summary = "Guardar P-023A - Aspectos Específicos Fondos de Crédito")
    public ResponseEntity<Map<String, Object>> guardarP023A(@Valid @RequestBody P023AAspectosFondosCredito datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-023A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("numItems", datos.items().size());

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-023A", new HashMap<>(Map.of(
                "items", datos.items()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-024A")
    @Operation(summary = "Guardar P-024A - Cartera por Edades")
    public ResponseEntity<Map<String, Object>> guardarP024A(@Valid @RequestBody P024ACarteraPorEdades datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-024A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("numItems", datos.items().size());

        long totalCartera = datos.items().stream()
                .mapToLong(P024ACarteraPorEdades.CarteraItem::valorTotalMontoCartera)
                .sum();
        result.put("totalCartera", totalCartera);

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-024A", new HashMap<>(Map.of(
                "items", datos.items(),
                "total", totalCartera
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-026A")
    @Operation(summary = "Guardar P-026A - Arrendamiento Bienes Inmuebles")
    public ResponseEntity<Map<String, Object>> guardarP026A(@Valid @RequestBody P026AArrendamientoBienes datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-026A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("valorCanon", datos.valorCanonMensual());

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-026A", new HashMap<>(Map.of(
                "tipo", "arrendamiento",
                "valorAvaluo", datos.valorAvaluo(),
                "canon", datos.valorCanonMensual(),
                "tiempo", datos.tiempoContrato()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-031A")
    @Operation(summary = "Guardar P-031A - Comodato Bienes Inmuebles")
    public ResponseEntity<Map<String, Object>> guardarP031A(@Valid @RequestBody P031AComodatoBienes datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-031A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-031A", new HashMap<>(Map.of(
                "tipo", "comodato",
                "destinacion", datos.destinacionInmueble()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-034A")
    @Operation(summary = "Guardar P-034A - Compra Bienes Inmuebles")
    public ResponseEntity<Map<String, Object>> guardarP034A(@Valid @RequestBody P034ACompraBienes datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-034A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("valorAvaluo", datos.valorAvaluo());

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-034A", new HashMap<>(Map.of(
                "tipo", "compra",
                "valorAvaluo", datos.valorAvaluo(),
                "perito", datos.perito()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-040A")
    @Operation(summary = "Guardar P-040A - Permuta Bienes Inmuebles")
    public ResponseEntity<Map<String, Object>> guardarP040A(@Valid @RequestBody P040APermutaBienes datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-040A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");
        result.put("valorRecibe", datos.valorAvaluoRecibe());
        result.put("valorEntrega", datos.valorAvaluoEntrega());

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-040A", new HashMap<>(Map.of(
                "tipo", "permuta",
                "valorRecibe", datos.valorAvaluoRecibe(),
                "valorEntrega", datos.valorAvaluoEntrega()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-050A")
    @Operation(summary = "Guardar P-050A - Negociación Acciones/Cuotas")
    public ResponseEntity<Map<String, Object>> guardarP050A(@Valid @RequestBody P050ANegociacionAcciones datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-050A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-050A", new HashMap<>(Map.of(
                "tipo", "negociacion",
                "numAcciones", datos.numAccionesCuotas(),
                "valor", datos.valorAccionesCuotas()
        )));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/P-055A")
    @Operation(summary = "Guardar P-055A - Capitalizaciones")
    public ResponseEntity<Map<String, Object>> guardarP055A(@Valid @RequestBody P055ACapitalizaciones datos) {
        Map<String, Object> result = new HashMap<>();
        result.put("estructura", "P-055A");
        result.put("codigoProyecto", datos.codigoProyecto());
        result.put("estado", "guardado");

        estructurasAlmacenadas.put(datos.codigoProyecto() + "-P-055A", new HashMap<>(Map.of(
                "tipo", "capitalizacion",
                "numAcciones", datos.numAccionesCuotas(),
                "valor", datos.valorAccionesCuotas()
        )));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/proyecto/{codigoProyecto}")
    @Operation(summary = "Obtener todas las estructuras de un proyecto")
    public ResponseEntity<Map<String, Object>> obtenerEstructurasPorProyecto(@PathVariable String codigoProyecto) {
        Map<String, Object> result = new HashMap<>();
        result.put("codigoProyecto", codigoProyecto);

        Map<String, Object> estructuras = new HashMap<>();
        for (String key : estructurasAlmacenadas.keySet()) {
            if (key.startsWith(codigoProyecto + "-")) {
                String estructura = key.substring(codigoProyecto.length() + 1);
                estructuras.put(estructura, estructurasAlmacenadas.get(key));
            }
        }

        result.put("estructuras", estructuras);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{codigoProyecto}/{estructura}")
    @Operation(summary = "Obtener una estructura específica de un proyecto")
    public ResponseEntity<Map<String, Object>> obtenerEstructura(
            @PathVariable String codigoProyecto,
            @PathVariable String estructura) {
        String key = codigoProyecto + "-" + estructura;
        Map<String, Object> data = estructurasAlmacenadas.get(key);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("estructura", estructura);
        result.put("codigoProyecto", codigoProyecto);
        result.put("data", data);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/P-001A/{codigoProyecto}")
    @Operation(summary = "Obtener datos P-001A de un proyecto")
    public ResponseEntity<P001ADatosGenerales> obtenerP001A(@PathVariable String codigoProyecto) {
        return persistenceService.obtenerP001A(codigoProyecto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/P-002A/{codigoProyecto}")
    @Operation(summary = "Obtener datos P-002A de un proyecto")
    public ResponseEntity<P002ACronograma> obtenerP002A(@PathVariable String codigoProyecto) {
        String key = codigoProyecto + "-P-002A";
        Map<String, Object> data = estructurasAlmacenadas.get(key);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        @SuppressWarnings("unchecked")
        List<P002ACronograma.CronogramaActividad> actividades =
            (List<P002ACronograma.CronogramaActividad>) data.getOrDefault("actividades", List.of());

        P002ACronograma cronograma = new P002ACronograma(
                codigoProyecto,
                actividades,
                (String) data.getOrDefault("porcentajeTotal", "0")
        );

        return ResponseEntity.ok(cronograma);
    }

    @GetMapping("/P-003A/{codigoProyecto}")
    @Operation(summary = "Obtener datos P-003A de un proyecto")
    public ResponseEntity<P003ALocalizacion> obtenerP003A(@PathVariable String codigoProyecto) {
        String key = codigoProyecto + "-P-003A";
        Map<String, Object> data = estructurasAlmacenadas.get(key);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        P003ALocalizacion localizacion = new P003ALocalizacion(
                codigoProyecto,
                (String) data.getOrDefault("departamento", ""),
                (String) data.getOrDefault("municipio", ""),
                (String) data.getOrDefault("direccion", ""),
                (String) data.getOrDefault("barrio", ""),
                (String) data.getOrDefault("telefono", ""),
                (String) data.getOrDefault("contacto", ""),
                (String) data.getOrDefault("fechaInicioOperacion", ""),
                data.get("latitude") != null ? ((Number) data.get("latitude")).doubleValue() : null,
                data.get("longitude") != null ? ((Number) data.get("longitude")).doubleValue() : null
        );

        return ResponseEntity.ok(localizacion);
    }

    @GetMapping("/P-004C/{codigoProyecto}")
    @Operation(summary = "Obtener datos P-004C de un proyecto")
    public ResponseEntity<P004CEstructuraFuenteRecursos> obtenerP004C(@PathVariable String codigoProyecto) {
        String key = codigoProyecto + "-P-004C";
        Map<String, Object> data = estructurasAlmacenadas.get(key);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        @SuppressWarnings("unchecked")
        List<P004CEstructuraFuenteRecursos.FuenteRecurso> fuentes =
            (List<P004CEstructuraFuenteRecursos.FuenteRecurso>) data.getOrDefault("fuentes", List.of());

        P004CEstructuraFuenteRecursos fuentesRecursos = new P004CEstructuraFuenteRecursos(
                codigoProyecto,
                fuentes
        );

        return ResponseEntity.ok(fuentesRecursos);
    }

    @GetMapping("/P-011A/{codigoProyecto}")
    @Operation(summary = "Obtener datos P-011A de un proyecto")
    public ResponseEntity<P011ACoberturaProyectada> obtenerP011A(@PathVariable String codigoProyecto) {
        String key = codigoProyecto + "-P-011A";
        Map<String, Object> data = estructurasAlmacenadas.get(key);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        @SuppressWarnings("unchecked")
        List<P011ACoberturaProyectada.CoberturaProyectadaItem> coberturas =
            (List<P011ACoberturaProyectada.CoberturaProyectadaItem>) data.getOrDefault("coberturas", List.of());

        P011ACoberturaProyectada cobertura = new P011ACoberturaProyectada(
                codigoProyecto,
                coberturas
        );

        return ResponseEntity.ok(cobertura);
    }

    @GetMapping("/P-013A/{codigoProyecto}")
    @Operation(summary = "Obtener datos P-013A de un proyecto")
    public ResponseEntity<P013AAspectosInfraestructura> obtenerP013A(@PathVariable String codigoProyecto) {
        String key = codigoProyecto + "-P-013A";
        Map<String, Object> data = estructurasAlmacenadas.get(key);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        P013AAspectosInfraestructura aspectos = new P013AAspectosInfraestructura(
                codigoProyecto,
                (Integer) data.getOrDefault("interventoria", 0),
                ((Number) data.getOrDefault("valorInterventoria", 0)).longValue(),
                (Integer) data.getOrDefault("licencia", 0),
                (String) data.getOrDefault("entidadCompetente", ""),
                (String) data.getOrDefault("numRadicadoLicencia", ""),
                (String) data.getOrDefault("fechaRadicacionLicencia", ""),
                (String) data.getOrDefault("numeroLicencia", ""),
                (String) data.getOrDefault("fechaLicencia", ""),
                (String) data.getOrDefault("vigenciaLicencia", ""),
                (Integer) data.getOrDefault("servicios", 0),
                (String) data.getOrDefault("fechaRadicacionAAA", ""),
                (String) data.getOrDefault("numRadicadoAAA", ""),
                (String) data.getOrDefault("fechaExpedicionAAA", ""),
                (String) data.getOrDefault("numDisponibilidadAAA", ""),
                (Integer) data.getOrDefault("vigenciaAAA", 0),
                (String) data.getOrDefault("fechaRadicacionEEA", ""),
                (String) data.getOrDefault("numRadicadoEEA", ""),
                (String) data.getOrDefault("fechaExpedicionEEA", ""),
                (String) data.getOrDefault("numDisponibilidadEEA", ""),
                (Integer) data.getOrDefault("vigenciaEEA", 0),
                (String) data.getOrDefault("fechaRadicacionGNA", ""),
                (String) data.getOrDefault("numRadicadoGNA", ""),
                (String) data.getOrDefault("fechaExpedicionGNA", ""),
                (String) data.getOrDefault("numDisponibilidadGNA", ""),
                (Integer) data.getOrDefault("vigenciaGNA", 0),
                (Integer) data.getOrDefault("empleos", 0)
        );

        return ResponseEntity.ok(aspectos);
    }
}