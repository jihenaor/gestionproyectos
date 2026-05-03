package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "CRONOGRAMA")
public class CronogramaEntity {

    @Id
    @Column(name = "ID_CRONOGRAMA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "TIPO_ACTIVIDAD", length = 5, nullable = false)
    private String tipoActividad;

    @Column(name = "DESCRIPCION_ACTIVIDAD", length = 500)
    private String descripcionActividad;

    @Column(name = "PORCENTAJE_PROYECTADO", length = 5, nullable = false)
    private String porcentajeProyectado;

    @Column(name = "FECHA_INICIO", length = 8, nullable = false)
    private String fechaInicio;

    @Column(name = "FECHA_TERMINACION", length = 8, nullable = false)
    private String fechaTerminacion;

    @Column(name = "UNIDAD_MEDIDA", length = 50)
    private String unidadMedida;

    @Column(name = "CANTIDAD_PROGRAMADA")
    private Long cantidadProgramada;

    @Column(name = "VALOR_PROGRAMADO")
    private Long valorProgramado;

    @Column(name = "ORDEN_EJECUCION")
    private Integer ordenEjecucion;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public CronogramaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getTipoActividad() { return tipoActividad; }
    public void setTipoActividad(String tipoActividad) { this.tipoActividad = tipoActividad; }

    public String getDescripcionActividad() { return descripcionActividad; }
    public void setDescripcionActividad(String descripcionActividad) { this.descripcionActividad = descripcionActividad; }

    public String getPorcentajeProyectado() { return porcentajeProyectado; }
    public void setPorcentajeProyectado(String porcentajeProyectado) { this.porcentajeProyectado = porcentajeProyectado; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaTerminacion() { return fechaTerminacion; }
    public void setFechaTerminacion(String fechaTerminacion) { this.fechaTerminacion = fechaTerminacion; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public Long getCantidadProgramada() { return cantidadProgramada; }
    public void setCantidadProgramada(Long cantidadProgramada) { this.cantidadProgramada = cantidadProgramada; }

    public Long getValorProgramado() { return valorProgramado; }
    public void setValorProgramado(Long valorProgramado) { this.valorProgramado = valorProgramado; }

    public Integer getOrdenEjecucion() { return ordenEjecucion; }
    public void setOrdenEjecucion(Integer ordenEjecucion) { this.ordenEjecucion = ordenEjecucion; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}