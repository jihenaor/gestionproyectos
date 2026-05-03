package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "CREDITOS_HIPOTECARIOS")
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

    public CreditosHipotecariosEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public Long getValorIndividualCreditos() { return valorIndividualCreditos; }
    public void setValorIndividualCreditos(Long valorIndividualCreditos) { this.valorIndividualCreditos = valorIndividualCreditos; }

    public String getSistemaAmortizacion() { return sistemaAmortizacion; }
    public void setSistemaAmortizacion(String sistemaAmortizacion) { this.sistemaAmortizacion = sistemaAmortizacion; }

    public String getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(String tasaInteres) { this.tasaInteres = tasaInteres; }

    public String getPuntosAdicionales() { return puntosAdicionales; }
    public void setPuntosAdicionales(String puntosAdicionales) { this.puntosAdicionales = puntosAdicionales; }

    public Integer getPlazoFinanciacion() { return plazoFinanciacion; }
    public void setPlazoFinanciacion(Integer plazoFinanciacion) { this.plazoFinanciacion = plazoFinanciacion; }

    public String getRequisitosGarantias() { return requisitosGarantias; }
    public void setRequisitosGarantias(String requisitosGarantias) { this.requisitosGarantias = requisitosGarantias; }

    public String getEstrategiasRecuperacion() { return estrategiasRecuperacion; }
    public void setEstrategiasRecuperacion(String estrategiasRecuperacion) { this.estrategiasRecuperacion = estrategiasRecuperacion; }

    public String getProcesosAdministrativos() { return procesosAdministrativos; }
    public void setProcesosAdministrativos(String procesosAdministrativos) { this.procesosAdministrativos = procesosAdministrativos; }
}