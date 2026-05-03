package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "COBERTURA_EJECUTADA")
public class CoberturaEjecutadaEntity {

    @Id
    @Column(name = "ID_COBERTURA_EJEC", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "COD_CATEGORIA", nullable = false)
    private Integer codCategoria;

    @Column(name = "CANTIDAD_BENEFICIARIOS", nullable = false)
    private Integer cantidadBeneficiarios;

    @Column(name = "VALOR_PER_CAPITA")
    private Long valorPerCapita;

    @Column(name = "ANIO1_EJECUTADO")
    private Integer anio1Ejecutado;

    @Column(name = "ANIO2_EJECUTADO")
    private Integer anio2Ejecutado;

    @Column(name = "ANIO3_EJECUTADO")
    private Integer anio3Ejecutado;

    @Column(name = "ANIO4_EJECUTADO")
    private Integer anio4Ejecutado;

    @Column(name = "ANIO5_EJECUTADO")
    private Integer anio5Ejecutado;

    @Column(name = "OBSERVACIONES", length = 2000)
    private String observaciones;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public CoberturaEjecutadaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public Integer getCodCategoria() { return codCategoria; }
    public void setCodCategoria(Integer codCategoria) { this.codCategoria = codCategoria; }

    public Integer getCantidadBeneficiarios() { return cantidadBeneficiarios; }
    public void setCantidadBeneficiarios(Integer cantidadBeneficiarios) { this.cantidadBeneficiarios = cantidadBeneficiarios; }

    public Long getValorPerCapita() { return valorPerCapita; }
    public void setValorPerCapita(Long valorPerCapita) { this.valorPerCapita = valorPerCapita; }

    public Integer getAnio1Ejecutado() { return anio1Ejecutado; }
    public void setAnio1Ejecutado(Integer anio1Ejecutado) { this.anio1Ejecutado = anio1Ejecutado; }

    public Integer getAnio2Ejecutado() { return anio2Ejecutado; }
    public void setAnio2Ejecutado(Integer anio2Ejecutado) { this.anio2Ejecutado = anio2Ejecutado; }

    public Integer getAnio3Ejecutado() { return anio3Ejecutado; }
    public void setAnio3Ejecutado(Integer anio3Ejecutado) { this.anio3Ejecutado = anio3Ejecutado; }

    public Integer getAnio4Ejecutado() { return anio4Ejecutado; }
    public void setAnio4Ejecutado(Integer anio4Ejecutado) { this.anio4Ejecutado = anio4Ejecutado; }

    public Integer getAnio5Ejecutado() { return anio5Ejecutado; }
    public void setAnio5Ejecutado(Integer anio5Ejecutado) { this.anio5Ejecutado = anio5Ejecutado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}