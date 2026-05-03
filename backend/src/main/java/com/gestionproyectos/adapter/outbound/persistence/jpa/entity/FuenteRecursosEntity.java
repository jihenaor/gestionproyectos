package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "FUENTE_RECURSOS")
public class FuenteRecursosEntity {

    @Id
    @Column(name = "ID_FUENTE", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "COD_FUENTE", length = 10, nullable = false)
    private String codFuente;

    @Column(name = "DESC_FUENTE", length = 100, nullable = false)
    private String descFuente;

    @Column(name = "VALOR_ASIGNADO", nullable = false)
    private Long valorAsignado;

    @Column(name = "PORCENTAJE", length = 5)
    private String porcentaje;

    @Column(name = "TIPO_RECURSO", length = 50)
    private String tipoRecurso;

    @Column(name = "CENTRO_COSTO", length = 50)
    private String centroCosto;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public FuenteRecursosEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getCodFuente() { return codFuente; }
    public void setCodFuente(String codFuente) { this.codFuente = codFuente; }

    public String getDescFuente() { return descFuente; }
    public void setDescFuente(String descFuente) { this.descFuente = descFuente; }

    public Long getValorAsignado() { return valorAsignado; }
    public void setValorAsignado(Long valorAsignado) { this.valorAsignado = valorAsignado; }

    public String getPorcentaje() { return porcentaje; }
    public void setPorcentaje(String porcentaje) { this.porcentaje = porcentaje; }

    public String getTipoRecurso() { return tipoRecurso; }
    public void setTipoRecurso(String tipoRecurso) { this.tipoRecurso = tipoRecurso; }

    public String getCentroCosto() { return centroCosto; }
    public void setCentroCosto(String centroCosto) { this.centroCosto = centroCosto; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}