package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "SEGUIMIENTO")
public class SeguimientoEntity {

    @Id
    @Column(name = "ID_SEGUIMIENTO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "PERIODO_REPORTE", length = 8, nullable = false)
    private String periodoReporte;

    @Column(name = "TIPO_ACTIVIDAD", length = 5, nullable = false)
    private String tipoActividad;

    @Column(name = "DESCRIPCION_ACTIVIDAD", length = 500)
    private String descripcionActividad;

    @Column(name = "PORCENTAJE_EJECUTADO", length = 5)
    private String porcentajeEjecutado;

    @Column(name = "VALOR_PLANEADO")
    private Long valorPlaneado;

    @Column(name = "VALOR_EJECUTADO")
    private Long valorEjecutado;

    @Column(name = "COSTO_ACTUAL")
    private Long costoActual;

    @Column(name = "VALOR_PAGADO")
    private Long valorPagado;

    @Column(name = "VALOR_GANADO")
    private Long valorGanado;

    @Column(name = "CANTIDAD_EJECUCION_FISICA")
    private Long cantidadEjecucionFisica;

    @Column(name = "FECHA_INICIO", length = 8)
    private String fechaInicio;

    @Column(name = "FECHA_TERMINACION", length = 8)
    private String fechaTerminacion;

    @Column(name = "OBSERVACIONES", length = 2000)
    private String observaciones;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public SeguimientoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getPeriodoReporte() { return periodoReporte; }
    public void setPeriodoReporte(String periodoReporte) { this.periodoReporte = periodoReporte; }

    public String getTipoActividad() { return tipoActividad; }
    public void setTipoActividad(String tipoActividad) { this.tipoActividad = tipoActividad; }

    public String getDescripcionActividad() { return descripcionActividad; }
    public void setDescripcionActividad(String descripcionActividad) { this.descripcionActividad = descripcionActividad; }

    public String getPorcentajeEjecutado() { return porcentajeEjecutado; }
    public void setPorcentajeEjecutado(String porcentajeEjecutado) { this.porcentajeEjecutado = porcentajeEjecutado; }

    public Long getValorPlaneado() { return valorPlaneado; }
    public void setValorPlaneado(Long valorPlaneado) { this.valorPlaneado = valorPlaneado; }

    public Long getValorEjecutado() { return valorEjecutado; }
    public void setValorEjecutado(Long valorEjecutado) { this.valorEjecutado = valorEjecutado; }

    public Long getCostoActual() { return costoActual; }
    public void setCostoActual(Long costoActual) { this.costoActual = costoActual; }

    public Long getValorPagado() { return valorPagado; }
    public void setValorPagado(Long valorPagado) { this.valorPagado = valorPagado; }

    public Long getValorGanado() { return valorGanado; }
    public void setValorGanado(Long valorGanado) { this.valorGanado = valorGanado; }

    public Long getCantidadEjecucionFisica() { return cantidadEjecucionFisica; }
    public void setCantidadEjecucionFisica(Long cantidadEjecucionFisica) { this.cantidadEjecucionFisica = cantidadEjecucionFisica; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaTerminacion() { return fechaTerminacion; }
    public void setFechaTerminacion(String fechaTerminacion) { this.fechaTerminacion = fechaTerminacion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}