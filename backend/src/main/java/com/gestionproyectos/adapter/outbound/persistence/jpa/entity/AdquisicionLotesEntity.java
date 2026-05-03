package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "ADQUISICION_LOTES")
public class AdquisicionLotesEntity {

    @Id
    @Column(name = "ID_LOTE", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "AREA_LOTE", length = 10)
    private String areaLote;

    @Column(name = "VALOR_LOTE", nullable = false)
    private Long valorLote;

    @Column(name = "VALOR_OTROS_COSTOS")
    private Long valorOtrosCostos;

    @Column(name = "FEC_AVALUO", length = 8)
    private String fecAvaluo;

    @Column(name = "PERITO", length = 50)
    private String perito;

    @Column(name = "NUM_REGISTRO_AVALUADOR", length = 20)
    private String numRegistroAvaliador;

    @Column(name = "VALOR_AVALUO")
    private Long valorAvaluo;

    @Column(name = "FEC_CERT_TRADICION_LIBERTAD", length = 8)
    private String fecCertTradicionLibertad;

    @Column(name = "SERVICIOS_PUBLICOS")
    private Integer serviciosPublicos;

    public AdquisicionLotesEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public String getAreaLote() { return areaLote; }
    public void setAreaLote(String areaLote) { this.areaLote = areaLote; }

    public Long getValorLote() { return valorLote; }
    public void setValorLote(Long valorLote) { this.valorLote = valorLote; }

    public Long getValorOtrosCostos() { return valorOtrosCostos; }
    public void setValorOtrosCostos(Long valorOtrosCostos) { this.valorOtrosCostos = valorOtrosCostos; }

    public String getFecAvaluo() { return fecAvaluo; }
    public void setFecAvaluo(String fecAvaluo) { this.fecAvaluo = fecAvaluo; }

    public String getPerito() { return perito; }
    public void setPerito(String perito) { this.perito = perito; }

    public String getNumRegistroAvaliador() { return numRegistroAvaliador; }
    public void setNumRegistroAvaliador(String numRegistroAvaliador) { this.numRegistroAvaliador = numRegistroAvaliador; }

    public Long getValorAvaluo() { return valorAvaluo; }
    public void setValorAvaluo(Long valorAvaluo) { this.valorAvaluo = valorAvaluo; }

    public String getFecCertTradicionLibertad() { return fecCertTradicionLibertad; }
    public void setFecCertTradicionLibertad(String fecCertTradicionLibertad) { this.fecCertTradicionLibertad = fecCertTradicionLibertad; }

    public Integer getServiciosPublicos() { return serviciosPublicos; }
    public void setServiciosPublicos(Integer serviciosPublicos) { this.serviciosPublicos = serviciosPublicos; }
}