package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "ARRENDAMIENTO")
public class ArrendamientoEntity {

    @Id
    @Column(name = "ID_ARRENDAMIENTO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "FEC_CERT_TRADICION_LIBERTAD", length = 8, nullable = false)
    private String fecCertTradicionLibertad;

    @Column(name = "FEC_AVALUO", length = 8, nullable = false)
    private String fecAvaluo;

    @Column(name = "PERITO", length = 50, nullable = false)
    private String perito;

    @Column(name = "VALOR_AVALUO", nullable = false)
    private Long valorAvaluo;

    @Column(name = "VAL_CANON_MENSUAL", nullable = false)
    private Long valCanonMensual;

    @Column(name = "TMP_CONTRATO", nullable = false)
    private Integer tmpContrato;

    @Column(name = "DESTINACION_INMUEBLE", length = 50, nullable = false)
    private String destinacionInmueble;

    @Column(name = "USO_AUTORIZADO", length = 50, nullable = false)
    private String usoAutorizado;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public ArrendamientoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getFecCertTradicionLibertad() { return fecCertTradicionLibertad; }
    public void setFecCertTradicionLibertad(String fecCertTradicionLibertad) { this.fecCertTradicionLibertad = fecCertTradicionLibertad; }

    public String getFecAvaluo() { return fecAvaluo; }
    public void setFecAvaluo(String fecAvaluo) { this.fecAvaluo = fecAvaluo; }

    public String getPerito() { return perito; }
    public void setPerito(String perito) { this.perito = perito; }

    public Long getValorAvaluo() { return valorAvaluo; }
    public void setValorAvaluo(Long valorAvaluo) { this.valorAvaluo = valorAvaluo; }

    public Long getValCanonMensual() { return valCanonMensual; }
    public void setValCanonMensual(Long valCanonMensual) { this.valCanonMensual = valCanonMensual; }

    public Integer getTmpContrato() { return tmpContrato; }
    public void setTmpContrato(Integer tmpContrato) { this.tmpContrato = tmpContrato; }

    public String getDestinacionInmueble() { return destinacionInmueble; }
    public void setDestinacionInmueble(String destinacionInmueble) { this.destinacionInmueble = destinacionInmueble; }

    public String getUsoAutorizado() { return usoAutorizado; }
    public void setUsoAutorizado(String usoAutorizado) { this.usoAutorizado = usoAutorizado; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}