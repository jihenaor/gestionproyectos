package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_COBERTURA_PROYECTADA")
@Data
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
}