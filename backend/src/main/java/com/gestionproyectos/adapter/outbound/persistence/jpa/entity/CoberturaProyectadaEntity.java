package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "COBERTURA_PROYECTADA")
public class CoberturaProyectadaEntity {

    @Id
    @Column(name = "ID_COBERTURA", length = 36)
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

    @Column(name = "ANIO1")
    private Integer anio1;

    @Column(name = "ANIO2")
    private Integer anio2;

    @Column(name = "ANIO3")
    private Integer anio3;

    @Column(name = "ANIO4")
    private Integer anio4;

    @Column(name = "ANIO5")
    private Integer anio5;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public CoberturaProyectadaEntity() {}

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

    public Integer getAnio1() { return anio1; }
    public void setAnio1(Integer anio1) { this.anio1 = anio1; }

    public Integer getAnio2() { return anio2; }
    public void setAnio2(Integer anio2) { this.anio2 = anio2; }

    public Integer getAnio3() { return anio3; }
    public void setAnio3(Integer anio3) { this.anio3 = anio3; }

    public Integer getAnio4() { return anio4; }
    public void setAnio4(Integer anio4) { this.anio4 = anio4; }

    public Integer getAnio5() { return anio5; }
    public void setAnio5(Integer anio5) { this.anio5 = anio5; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}