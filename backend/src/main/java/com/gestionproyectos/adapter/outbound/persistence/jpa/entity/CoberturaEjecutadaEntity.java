package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_COBERTURA_EJECUTADA")
@Data
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
    private LocalDateTime fechaCreacion;
}