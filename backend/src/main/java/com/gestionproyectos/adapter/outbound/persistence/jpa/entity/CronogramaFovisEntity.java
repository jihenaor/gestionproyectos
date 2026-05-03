package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_CRONOGRAMA")
@Data
public class CronogramaFovisEntity {

    @Id
    @Column(name = "ID_CRONOGRAMA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "ANO_EJECUCION", nullable = false)
    private Integer anoEjecucion;

    @Column(name = "MES_EJECUCION", nullable = false)
    private Integer mesEjecucion;

    @Column(name = "PORCENTAJE_PROYECTADO")
    private Integer porcentajeProyectado;

    @Column(name = "CANTIDAD_CREDITOS_PROYECTADOS")
    private Integer cantidadCreditosProyectados;

    @Column(name = "VALOR_CREDITOS_PROYECTADOS")
    private Long valorCreditosProyectados;
}