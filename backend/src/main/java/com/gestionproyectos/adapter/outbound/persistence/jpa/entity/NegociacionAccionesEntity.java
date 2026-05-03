package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_NEGOCIACION_ACCIONES")
@Data
public class NegociacionAccionesEntity {

    @Id
    @Column(name = "ID_NEGOCIACION", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "NUM_ACCIONES_CUOTAS", nullable = false)
    private Integer numAccionesCuotas;

    @Column(name = "VALOR_ACCIONES_CUOTAS", nullable = false)
    private Long valorAccionesCuotas;

    @Column(name = "PORCENTAJE_PARTICIPACION", length = 5, nullable = false)
    private String porcentajeParticipacion;

    @Column(name = "VALOR_NOMINAL_ACCIONES", nullable = false)
    private Long valorNominalAcciones;

    @Column(name = "VALOR_MERCADO_ACCIONES", nullable = false)
    private Long valorMercadoAcciones;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}