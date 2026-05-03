package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "TIPOLOGIA_VIS")
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

    public TipologiaVisEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public Integer getCodTipologia() { return codTipologia; }
    public void setCodTipologia(Integer codTipologia) { this.codTipologia = codTipologia; }

    public String getAreaUnidadConstruccion() { return areaUnidadConstruccion; }
    public void setAreaUnidadConstruccion(String areaUnidadConstruccion) { this.areaUnidadConstruccion = areaUnidadConstruccion; }

    public Long getValorVenta() { return valorVenta; }
    public void setValorVenta(Long valorVenta) { this.valorVenta = valorVenta; }

    public Integer getNumeroUnidades() { return numeroUnidades; }
    public void setNumeroUnidades(Integer numeroUnidades) { this.numeroUnidades = numeroUnidades; }
}