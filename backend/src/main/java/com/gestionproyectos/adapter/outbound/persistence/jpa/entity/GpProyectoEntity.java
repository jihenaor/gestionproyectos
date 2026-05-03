package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "GP_PROYECTOS")
public class GpProyectoEntity {

    @Id
    @Column(name = "ID_PROYECTO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "COD_PROYECTO", length = 15, nullable = false)
    private String codigoProyecto;

    @Column(name = "MOD_PROYECTO", nullable = false)
    private Integer modProyecto;

    @Column(name = "MOD_DE_INVERSION", nullable = false)
    private Integer modDeInversion;

    @Column(name = "VAL_TOTAL_PROYECTO", nullable = false)
    private Long valTotalProyecto;

    @Column(name = "VALOR_APR_VIGENCIA", nullable = false)
    private Long valorAprVigencia;

    @Column(name = "DESCRIPCION_PROYECTO", length = 4000, nullable = false)
    private String descripcionProyecto;

    @Column(name = "OBJETIVO_PROYECTO", length = 4000, nullable = false)
    private String objetivoProyecto;

    @Column(name = "JUSTIFICACION", length = 4000, nullable = false)
    private String justificacion;

    @Column(name = "RESOLUCION_AEI", nullable = false)
    private Integer resolucionAEI;

    @Column(name = "NUM_ACTA_AEI")
    private Long numActaAei;

    @Column(name = "FECHA_APR_AEI", length = 8)
    private String fechaAprAei;

    @Column(name = "NUM_CONSEJEROS", nullable = false)
    private Integer numConsejeros;

    @Column(name = "TMP_RECUPERACION")
    private Integer tmpRecuperacion;

    @Column(name = "ESTUDIO_MERCADO", length = 4000)
    private String estudioMercado;

    @Column(name = "EVALUACION_SOCIAL", length = 4000)
    private String evaluacionSocial;

    @Column(name = "EVALUACION_FINANCIERA", length = 4000)
    private String evaluacionFinanciera;

    @Column(name = "NUM_PERSONAS_REFERENCE")
    private Long numPersonasReference;

    @Column(name = "NUM_POBLACION_AFECTADA")
    private Long numPoblacionAfectada;

    @Column(name = "ESTADO_PROYECTO", length = 20)
    private String estadoProyecto;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fecCreacion;

    @Column(name = "FEC_MODIFICACION")
    private LocalDateTime fecModificacion;

    @Column(name = "USUARIO_CREACION", length = 50)
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION", length = 50)
    private String usuarioModificacion;

    public GpProyectoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigoProyecto() { return codigoProyecto; }
    public void setCodigoProyecto(String codigoProyecto) { this.codigoProyecto = codigoProyecto; }

    public Integer getModProyecto() { return modProyecto; }
    public void setModProyecto(Integer modProyecto) { this.modProyecto = modProyecto; }

    public Integer getModDeInversion() { return modDeInversion; }
    public void setModDeInversion(Integer modDeInversion) { this.modDeInversion = modDeInversion; }

    public Long getValTotalProyecto() { return valTotalProyecto; }
    public void setValTotalProyecto(Long valTotalProyecto) { this.valTotalProyecto = valTotalProyecto; }

    public Long getValorAprVigencia() { return valorAprVigencia; }
    public void setValorAprVigencia(Long valorAprVigencia) { this.valorAprVigencia = valorAprVigencia; }

    public String getDescripcionProyecto() { return descripcionProyecto; }
    public void setDescripcionProyecto(String descripcionProyecto) { this.descripcionProyecto = descripcionProyecto; }

    public String getObjetivoProyecto() { return objetivoProyecto; }
    public void setObjetivoProyecto(String objetivoProyecto) { this.objetivoProyecto = objetivoProyecto; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public Integer getResolucionAEI() { return resolucionAEI; }
    public void setResolucionAEI(Integer resolucionAEI) { this.resolucionAEI = resolucionAEI; }

    public Long getNumActaAei() { return numActaAei; }
    public void setNumActaAei(Long numActaAei) { this.numActaAei = numActaAei; }

    public String getFechaAprAei() { return fechaAprAei; }
    public void setFechaAprAei(String fechaAprAei) { this.fechaAprAei = fechaAprAei; }

    public Integer getNumConsejeros() { return numConsejeros; }
    public void setNumConsejeros(Integer numConsejeros) { this.numConsejeros = numConsejeros; }

    public Integer getTmpRecuperacion() { return tmpRecuperacion; }
    public void setTmpRecuperacion(Integer tmpRecuperacion) { this.tmpRecuperacion = tmpRecuperacion; }

    public String getEstudioMercado() { return estudioMercado; }
    public void setEstudioMercado(String estudioMercado) { this.estudioMercado = estudioMercado; }

    public String getEvaluacionSocial() { return evaluacionSocial; }
    public void setEvaluacionSocial(String evaluacionSocial) { this.evaluacionSocial = evaluacionSocial; }

    public String getEvaluacionFinanciera() { return evaluacionFinanciera; }
    public void setEvaluacionFinanciera(String evaluacionFinanciera) { this.evaluacionFinanciera = evaluacionFinanciera; }

    public Long getNumPersonasReference() { return numPersonasReference; }
    public void setNumPersonasReference(Long numPersonasReference) { this.numPersonasReference = numPersonasReference; }

    public Long getNumPoblacionAfectada() { return numPoblacionAfectada; }
    public void setNumPoblacionAfectada(Long numPoblacionAfectada) { this.numPoblacionAfectada = numPoblacionAfectada; }

    public String getEstadoProyecto() { return estadoProyecto; }
    public void setEstadoProyecto(String estadoProyecto) { this.estadoProyecto = estadoProyecto; }

    public LocalDateTime getFecCreacion() { return fecCreacion; }
    public void setFecCreacion(LocalDateTime fecCreacion) { this.fecCreacion = fecCreacion; }

    public LocalDateTime getFecModificacion() { return fecModificacion; }
    public void setFecModificacion(LocalDateTime fecModificacion) { this.fecModificacion = fecModificacion; }

    public String getUsuarioCreacion() { return usuarioCreacion; }
    public void setUsuarioCreacion(String usuarioCreacion) { this.usuarioCreacion = usuarioCreacion; }

    public String getUsuarioModificacion() { return usuarioModificacion; }
    public void setUsuarioModificacion(String usuarioModificacion) { this.usuarioModificacion = usuarioModificacion; }
}