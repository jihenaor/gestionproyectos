package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_CREDITOS_HIPOTECARIOS")
@Data
public class CreditosHipotecariosEntity {

    @Id
    @Column(name = "ID_CREDITO_HIP", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "VLR_INDIVIDUAL_CREDITOS", nullable = false)
    private Long valorIndividualCreditos;

    @Column(name = "SISTEMA_AMORTIZACION", length = 200)
    private String sistemaAmortizacion;

    @Column(name = "TAS_INTERES", length = 5, nullable = false)
    private String tasaInteres;

    @Column(name = "PUNTOS_ADICIONALES", length = 5)
    private String puntosAdicionales;

    @Column(name = "PLAZO_FINANCIACION", nullable = false)
    private Integer plazoFinanciacion;

    @Column(name = "REQUISITOS_GARANTIAS", length = 4000)
    private String requisitosGarantias;

    @Column(name = "ESTRATEGIAS_RECUPERACION", length = 4000)
    private String estrategiasRecuperacion;

    @Column(name = "PROCESOS_ADMINISTRATIVOS", length = 4000)
    private String procesosAdministrativos;
}