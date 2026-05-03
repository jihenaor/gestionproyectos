package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "CRONOGRAMA_FOVIS")
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

    public CronogramaFovisEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public Integer getAnoEjecucion() { return anoEjecucion; }
    public void setAnoEjecucion(Integer anoEjecucion) { this.anoEjecucion = anoEjecucion; }

    public Integer getMesEjecucion() { return mesEjecucion; }
    public void setMesEjecucion(Integer mesEjecucion) { this.mesEjecucion = mesEjecucion; }

    public Integer getPorcentajeProyectado() { return porcentajeProyectado; }
    public void setPorcentajeProyectado(Integer porcentajeProyectado) { this.porcentajeProyectado = porcentajeProyectado; }

    public Integer getCantidadCreditosProyectados() { return cantidadCreditosProyectados; }
    public void setCantidadCreditosProyectados(Integer cantidadCreditosProyectados) { this.cantidadCreditosProyectados = cantidadCreditosProyectados; }

    public Long getValorCreditosProyectados() { return valorCreditosProyectados; }
    public void setValorCreditosProyectados(Long valorCreditosProyectados) { this.valorCreditosProyectados = valorCreditosProyectados; }
}