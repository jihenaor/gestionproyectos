package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "CAPITALIZACIONES")
public class CapitalizacionesEntity {

    @Id
    @Column(name = "ID_CAPITALIZACION", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "NUM_ACCIONES_CUOTAS", nullable = false)
    private Integer numAccionesCuotas;

    @Column(name = "VALOR_ACCIONES_CUOTAS", nullable = false)
    private Long valorAccionesCuotas;

    @Column(name = "PORCENTAJE_PARTICIPACION", length = 5, nullable = false)
    private String porcentajeParticipacion;

    @Column(name = "VALOR_NOMINAL_ACCIONES", nullable = false)
    private Long valorNominalAcciones;

    @Column(name = "VALOR_MERCADO_ACCIONES", nullable = false)
    private Long valorMercadoAcciones;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public CapitalizacionesEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public Integer getNumAccionesCuotas() { return numAccionesCuotas; }
    public void setNumAccionesCuotas(Integer numAccionesCuotas) { this.numAccionesCuotas = numAccionesCuotas; }

    public Long getValorAccionesCuotas() { return valorAccionesCuotas; }
    public void setValorAccionesCuotas(Long valorAccionesCuotas) { this.valorAccionesCuotas = valorAccionesCuotas; }

    public String getPorcentajeParticipacion() { return porcentajeParticipacion; }
    public void setPorcentajeParticipacion(String porcentajeParticipacion) { this.porcentajeParticipacion = porcentajeParticipacion; }

    public Long getValorNominalAcciones() { return valorNominalAcciones; }
    public void setValorNominalAcciones(Long valorNominalAcciones) { this.valorNominalAcciones = valorNominalAcciones; }

    public Long getValorMercadoAcciones() { return valorMercadoAcciones; }
    public void setValorMercadoAcciones(Long valorMercadoAcciones) { this.valorMercadoAcciones = valorMercadoAcciones; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}