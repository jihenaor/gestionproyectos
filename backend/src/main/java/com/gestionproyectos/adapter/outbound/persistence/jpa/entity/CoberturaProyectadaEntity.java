package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_COBERTURA_PROYECTADA")
@Data
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
    private LocalDateTime fechaCreacion;
}