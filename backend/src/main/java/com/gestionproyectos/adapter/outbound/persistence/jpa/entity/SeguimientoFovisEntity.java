package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "SEGUIMIENTO_FOVIS")
public class SeguimientoFovisEntity {

    @Id
    @Column(name = "ID_SEGUIMIENTO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "ANO_EJECUCION", nullable = false)
    private Integer anoEjecucion;

    @Column(name = "MES_EJECUCION", nullable = false)
    private Integer mesEjecucion;

    @Column(name = "TIPO_ACTIVIDAD", length = 5, nullable = false)
    private String tipoActividad;

    @Column(name = "VALOR_EJECUTADO")
    private Long valorEjecutado;

    @Column(name = "CANTIDAD_CREDITOS_ASIGNADOS")
    private Integer cantidadCreditosAsignados;

    @Column(name = "VALOR_CREDITOS_ASIGNADOS")
    private Long valorCreditosAsignados;

    @Column(name = "FECHA_COMPRA_LOTE", length = 8)
    private String fechaCompraLote;

    @Column(name = "VALOR_TOTAL_COMPRA_LOTE")
    private Long valorTotalCompraLote;

    @Column(name = "VALOR_OTROS_COSTOS")
    private Long valorOtrosCostos;

    @Column(name = "COMENTARIOS", length = 4000)
    private String comentarios;

    public SeguimientoFovisEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public Integer getAnoEjecucion() { return anoEjecucion; }
    public void setAnoEjecucion(Integer anoEjecucion) { this.anoEjecucion = anoEjecucion; }

    public Integer getMesEjecucion() { return mesEjecucion; }
    public void setMesEjecucion(Integer mesEjecucion) { this.mesEjecucion = mesEjecucion; }

    public String getTipoActividad() { return tipoActividad; }
    public void setTipoActividad(String tipoActividad) { this.tipoActividad = tipoActividad; }

    public Long getValorEjecutado() { return valorEjecutado; }
    public void setValorEjecutado(Long valorEjecutado) { this.valorEjecutado = valorEjecutado; }

    public Integer getCantidadCreditosAsignados() { return cantidadCreditosAsignados; }
    public void setCantidadCreditosAsignados(Integer cantidadCreditosAsignados) { this.cantidadCreditosAsignados = cantidadCreditosAsignados; }

    public Long getValorCreditosAsignados() { return valorCreditosAsignados; }
    public void setValorCreditosAsignados(Long valorCreditosAsignados) { this.valorCreditosAsignados = valorCreditosAsignados; }

    public String getFechaCompraLote() { return fechaCompraLote; }
    public void setFechaCompraLote(String fechaCompraLote) { this.fechaCompraLote = fechaCompraLote; }

    public Long getValorTotalCompraLote() { return valorTotalCompraLote; }
    public void setValorTotalCompraLote(Long valorTotalCompraLote) { this.valorTotalCompraLote = valorTotalCompraLote; }

    public Long getValorOtrosCostos() { return valorOtrosCostos; }
    public void setValorOtrosCostos(Long valorOtrosCostos) { this.valorOtrosCostos = valorOtrosCostos; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}