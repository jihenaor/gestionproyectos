package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "FONDOS_CREDITO")
public class FondosCreditoEntity {

    @Id
    @Column(name = "ID_FONDO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "MODALIDAD_CREDITO", nullable = false)
    private Integer modalidadCredito;

    @Column(name = "COD_CATEGORIA", nullable = false)
    private Integer codCategoria;

    @Column(name = "TASA_INTERES_MINIMA", length = 5, nullable = false)
    private String tasaInteresMinima;

    @Column(name = "TASA_INTERES_MAXIMA", length = 5, nullable = false)
    private String tasaInteresMaxima;

    @Column(name = "CANT_CREDITOS", nullable = false)
    private Integer cantCreditos;

    @Column(name = "VAL_MONTO_CREDITOS", nullable = false)
    private Long valMontoCreditos;

    @Column(name = "PLAZO_CREDITO", nullable = false)
    private Integer plazoCredito;

    @Column(name = "PORCENTAJE_SUBSIDIO", length = 5, nullable = false)
    private String porcentajeSubsidio;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public FondosCreditoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public Integer getModalidadCredito() { return modalidadCredito; }
    public void setModalidadCredito(Integer modalidadCredito) { this.modalidadCredito = modalidadCredito; }

    public Integer getCodCategoria() { return codCategoria; }
    public void setCodCategoria(Integer codCategoria) { this.codCategoria = codCategoria; }

    public String getTasaInteresMinima() { return tasaInteresMinima; }
    public void setTasaInteresMinima(String tasaInteresMinima) { this.tasaInteresMinima = tasaInteresMinima; }

    public String getTasaInteresMaxima() { return tasaInteresMaxima; }
    public void setTasaInteresMaxima(String tasaInteresMaxima) { this.tasaInteresMaxima = tasaInteresMaxima; }

    public Integer getCantCreditos() { return cantCreditos; }
    public void setCantCreditos(Integer cantCreditos) { this.cantCreditos = cantCreditos; }

    public Long getValMontoCreditos() { return valMontoCreditos; }
    public void setValMontoCreditos(Long valMontoCreditos) { this.valMontoCreditos = valMontoCreditos; }

    public Integer getPlazoCredito() { return plazoCredito; }
    public void setPlazoCredito(Integer plazoCredito) { this.plazoCredito = plazoCredito; }

    public String getPorcentajeSubsidio() { return porcentajeSubsidio; }
    public void setPorcentajeSubsidio(String porcentajeSubsidio) { this.porcentajeSubsidio = porcentajeSubsidio; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}