package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "COBERTURA_FOVIS")
public class CoberturaFovisEntity {

    @Id
    @Column(name = "ID_COBERTURA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "COD_CATEGORIA", nullable = false)
    private Integer codCategoria;

    @Column(name = "POBLACION_OBJETIVO", nullable = false)
    private Long poblacionObjetivo;

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

    public CoberturaFovisEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public Integer getCodCategoria() { return codCategoria; }
    public void setCodCategoria(Integer codCategoria) { this.codCategoria = codCategoria; }

    public Long getPoblacionObjetivo() { return poblacionObjetivo; }
    public void setPoblacionObjetivo(Long poblacionObjetivo) { this.poblacionObjetivo = poblacionObjetivo; }

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
}