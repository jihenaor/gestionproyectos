package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "CARTERA_EDADES")
public class CarteraEdadesEntity {

    @Id
    @Column(name = "ID_CARTERA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "RAN_EDAD", length = 2, nullable = false)
    private String ranEdad;

    @Column(name = "EDAD_CARTERA", nullable = false)
    private Integer edadCartera;

    @Column(name = "MODALIDAD_CREDITO", nullable = false)
    private Integer modalidadCredito;

    @Column(name = "COD_CATEGORIA", nullable = false)
    private Integer codCategoria;

    @Column(name = "CANT_CREDITOS", nullable = false)
    private Integer cantCreditos;

    @Column(name = "VALOR_TOTAL_MONTO_CARTERA", nullable = false)
    private Long valorTotalMontoCartera;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public CarteraEdadesEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getRanEdad() { return ranEdad; }
    public void setRanEdad(String ranEdad) { this.ranEdad = ranEdad; }

    public Integer getEdadCartera() { return edadCartera; }
    public void setEdadCartera(Integer edadCartera) { this.edadCartera = edadCartera; }

    public Integer getModalidadCredito() { return modalidadCredito; }
    public void setModalidadCredito(Integer modalidadCredito) { this.modalidadCredito = modalidadCredito; }

    public Integer getCodCategoria() { return codCategoria; }
    public void setCodCategoria(Integer codCategoria) { this.codCategoria = codCategoria; }

    public Integer getCantCreditos() { return cantCreditos; }
    public void setCantCreditos(Integer cantCreditos) { this.cantCreditos = cantCreditos; }

    public Long getValorTotalMontoCartera() { return valorTotalMontoCartera; }
    public void setValorTotalMontoCartera(Long valorTotalMontoCartera) { this.valorTotalMontoCartera = valorTotalMontoCartera; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}