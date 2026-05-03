package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "PROYECTOS_FOVIS")
public class ProyectoFovisEntity {

    @Id
    @Column(name = "ID_PROYECTO_FOVIS", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "COD_PROYECTO", length = 15, nullable = false, unique = true)
    private String codigoProyecto;

    @Column(name = "VAL_TOTAL_PROYECTO", nullable = false)
    private Long valorTotalProyecto;

    @Column(name = "MODALIDAD_FOVIS", nullable = false)
    private Integer modalidadFovis;

    @Column(name = "DESCRIPCION_PROYECTO", length = 4000, nullable = false)
    private String descripcionProyecto;

    @Column(name = "OBJETIVO_PROYECTO", length = 4000, nullable = false)
    private String objetivoProyecto;

    @Column(name = "JUSTIFICACION", length = 4000, nullable = false)
    private String justificacion;

    @Column(name = "APROBACION", nullable = false)
    private Integer aprobacion;

    @Column(name = "NUM_ACTA_AEI")
    private Long numActaAei;

    @Column(name = "FECHA_APR_AEI", length = 8)
    private String fechaAprobAei;

    @Column(name = "NUM_CONSEJEROS", nullable = false)
    private Integer numConsejeros;

    @Column(name = "TMP_REINTEGRO")
    private Integer tmpReintegro;

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

    @Column(name = "FECHA_DESEMBOLSO", length = 8)
    private String fechaDesembolso;

    @Column(name = "FEC_REINTEGRO", length = 8)
    private String fecReintegro;

    @Column(name = "ESTADO_PROYECTO", length = 20, nullable = false)
    private String estadoProyecto;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FEC_MODIFICACION")
    private LocalDateTime fechaModificacion;

    @Column(name = "USUARIO_CREACION", length = 50)
    private String usuarioCreacion;

    @Column(name = "USUARIO_MODIFICACION", length = 50)
    private String usuarioModificacion;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public ProyectoFovisEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigoProyecto() { return codigoProyecto; }
    public void setCodigoProyecto(String codigoProyecto) { this.codigoProyecto = codigoProyecto; }

    public Long getValorTotalProyecto() { return valorTotalProyecto; }
    public void setValorTotalProyecto(Long valorTotalProyecto) { this.valorTotalProyecto = valorTotalProyecto; }

    public Integer getModalidadFovis() { return modalidadFovis; }
    public void setModalidadFovis(Integer modalidadFovis) { this.modalidadFovis = modalidadFovis; }

    public String getDescripcionProyecto() { return descripcionProyecto; }
    public void setDescripcionProyecto(String descripcionProyecto) { this.descripcionProyecto = descripcionProyecto; }

    public String getObjetivoProyecto() { return objetivoProyecto; }
    public void setObjetivoProyecto(String objetivoProyecto) { this.objetivoProyecto = objetivoProyecto; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public Integer getAprobacion() { return aprobacion; }
    public void setAprobacion(Integer aprobacion) { this.aprobacion = aprobacion; }

    public Long getNumActaAei() { return numActaAei; }
    public void setNumActaAei(Long numActaAei) { this.numActaAei = numActaAei; }

    public String getFechaAprobAei() { return fechaAprobAei; }
    public void setFechaAprobAei(String fechaAprobAei) { this.fechaAprobAei = fechaAprobAei; }

    public Integer getNumConsejeros() { return numConsejeros; }
    public void setNumConsejeros(Integer numConsejeros) { this.numConsejeros = numConsejeros; }

    public Integer getTmpReintegro() { return tmpReintegro; }
    public void setTmpReintegro(Integer tmpReintegro) { this.tmpReintegro = tmpReintegro; }

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

    public String getFechaDesembolso() { return fechaDesembolso; }
    public void setFechaDesembolso(String fechaDesembolso) { this.fechaDesembolso = fechaDesembolso; }

    public String getFecReintegro() { return fecReintegro; }
    public void setFecReintegro(String fecReintegro) { this.fecReintegro = fecReintegro; }

    public String getEstadoProyecto() { return estadoProyecto; }
    public void setEstadoProyecto(String estadoProyecto) { this.estadoProyecto = estadoProyecto; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public String getUsuarioCreacion() { return usuarioCreacion; }
    public void setUsuarioCreacion(String usuarioCreacion) { this.usuarioCreacion = usuarioCreacion; }

    public String getUsuarioModificacion() { return usuarioModificacion; }
    public void setUsuarioModificacion(String usuarioModificacion) { this.usuarioModificacion = usuarioModificacion; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}