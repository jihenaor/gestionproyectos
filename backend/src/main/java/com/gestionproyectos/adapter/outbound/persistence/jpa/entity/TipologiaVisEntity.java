package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_TIPOLOGIA_VIS")
@Data
public class TipologiaVisEntity {

    @Id
    @Column(name = "ID_TIPOLOGIA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "COD_TIPOLOGIA", nullable = false)
    private Integer codTipologia;

    @Column(name = "AREA_UNIDAD_CONSTRUCCION", length = 5)
    private String areaUnidadConstruccion;

    @Column(name = "VALOR_VENTA", nullable = false)
    private Long valorVenta;

    @Column(name = "NUMERO_UNIDADES", nullable = false)
    private Integer numeroUnidades;
}