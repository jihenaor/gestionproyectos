package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "ASPECTOS_VIS")
public class AspectosVisEntity {

    @Id
    @Column(name = "ID_ASPECTO_VIS", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "MODALIDAD_SOLUCION_VIVIENDA", length = 50)
    private String modalidadSolucionVivienda;

    @Column(name = "INTERVENTORIA_SUPERVISION")
    private Integer interventoriaSupervision;

    @Column(name = "ENT_COMPETENTE", length = 250)
    private String entCompetente;

    @Column(name = "NUM_RADICADO_LICENCIA", length = 30)
    private String numRadicadoLicencia;

    @Column(name = "FEC_SOLICITUD_LICENCIA", length = 8)
    private String fecSolicitudLicencia;

    @Column(name = "NUMERO_LICENCIA", length = 30)
    private String numeroLicencia;

    @Column(name = "FEC_LICENCIA", length = 8)
    private String fecLicencia;

    @Column(name = "VIGENCIA_LICENCIA")
    private Integer vigenciaLicencia;

    @Column(name = "FEC_RADICACION_AAA", length = 8)
    private String fecRadicacionAaa;

    @Column(name = "NUM_RADICADO_SOLICITUD_AAA", length = 30)
    private String numRadicadoSolicitudAaa;

    @Column(name = "FEC_EXPEDICION_AAA", length = 8)
    private String fecExpedicionAaa;

    @Column(name = "NUM_DISPONIBILIDAD_AAA", length = 30)
    private String numDisponibilidadAaa;

    @Column(name = "VIGENCIA_AAA")
    private Integer vigenciaAaa;

    @Column(name = "FEC_RADICACION_EEA", length = 8)
    private String fecRadicacionEea;

    @Column(name = "NUM_RADICADO_SOLICITUD_EEA", length = 30)
    private String numRadicadoSolicitudEea;

    @Column(name = "FEC_EXPEDICION_EEA", length = 8)
    private String fecExpedicionEea;

    @Column(name = "NUM_DISPONIBILIDAD_EEA", length = 30)
    private String numDisponibilidadEea;

    @Column(name = "VIGENCIA_EEA")
    private Integer vigenciaEea;

    @Column(name = "FEC_RADICACION_GNA", length = 8)
    private String fecRadicacionGna;

    @Column(name = "NUM_RADICADO_SOLICITUD_GNA", length = 30)
    private String numRadicadoSolicitudGna;

    @Column(name = "FEC_EXPEDICION_GNA", length = 8)
    private String fecExpedicionGna;

    @Column(name = "NUM_DISPONIBILIDAD_GNA", length = 30)
    private String numDisponibilidadGna;

    @Column(name = "VIGENCIA_GNA")
    private Integer vigenciaGna;

    public AspectosVisEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public String getModalidadSolucionVivienda() { return modalidadSolucionVivienda; }
    public void setModalidadSolucionVivienda(String modalidadSolucionVivienda) { this.modalidadSolucionVivienda = modalidadSolucionVivienda; }

    public Integer getInterventoriaSupervision() { return interventoriaSupervision; }
    public void setInterventoriaSupervision(Integer interventoriaSupervision) { this.interventoriaSupervision = interventoriaSupervision; }

    public String getEntCompetente() { return entCompetente; }
    public void setEntCompetente(String entCompetente) { this.entCompetente = entCompetente; }

    public String getNumRadicadoLicencia() { return numRadicadoLicencia; }
    public void setNumRadicadoLicencia(String numRadicadoLicencia) { this.numRadicadoLicencia = numRadicadoLicencia; }

    public String getFecSolicitudLicencia() { return fecSolicitudLicencia; }
    public void setFecSolicitudLicencia(String fecSolicitudLicencia) { this.fecSolicitudLicencia = fecSolicitudLicencia; }

    public String getNumeroLicencia() { return numeroLicencia; }
    public void setNumeroLicencia(String numeroLicencia) { this.numeroLicencia = numeroLicencia; }

    public String getFecLicencia() { return fecLicencia; }
    public void setFecLicencia(String fecLicencia) { this.fecLicencia = fecLicencia; }

    public Integer getVigenciaLicencia() { return vigenciaLicencia; }
    public void setVigenciaLicencia(Integer vigenciaLicencia) { this.vigenciaLicencia = vigenciaLicencia; }

    public String getFecRadicacionAaa() { return fecRadicacionAaa; }
    public void setFecRadicacionAaa(String fecRadicacionAaa) { this.fecRadicacionAaa = fecRadicacionAaa; }

    public String getNumRadicadoSolicitudAaa() { return numRadicadoSolicitudAaa; }
    public void setNumRadicadoSolicitudAaa(String numRadicadoSolicitudAaa) { this.numRadicadoSolicitudAaa = numRadicadoSolicitudAaa; }

    public String getFecExpedicionAaa() { return fecExpedicionAaa; }
    public void setFecExpedicionAaa(String fecExpedicionAaa) { this.fecExpedicionAaa = fecExpedicionAaa; }

    public String getNumDisponibilidadAaa() { return numDisponibilidadAaa; }
    public void setNumDisponibilidadAaa(String numDisponibilidadAaa) { this.numDisponibilidadAaa = numDisponibilidadAaa; }

    public Integer getVigenciaAaa() { return vigenciaAaa; }
    public void setVigenciaAaa(Integer vigenciaAaa) { this.vigenciaAaa = vigenciaAaa; }

    public String getFecRadicacionEea() { return fecRadicacionEea; }
    public void setFecRadicacionEea(String fecRadicacionEea) { this.fecRadicacionEea = fecRadicacionEea; }

    public String getNumRadicadoSolicitudEea() { return numRadicadoSolicitudEea; }
    public void setNumRadicadoSolicitudEea(String numRadicadoSolicitudEea) { this.numRadicadoSolicitudEea = numRadicadoSolicitudEea; }

    public String getFecExpedicionEea() { return fecExpedicionEea; }
    public void setFecExpedicionEea(String fecExpedicionEea) { this.fecExpedicionEea = fecExpedicionEea; }

    public String getNumDisponibilidadEea() { return numDisponibilidadEea; }
    public void setNumDisponibilidadEea(String numDisponibilidadEea) { this.numDisponibilidadEea = numDisponibilidadEea; }

    public Integer getVigenciaEea() { return vigenciaEea; }
    public void setVigenciaEea(Integer vigenciaEea) { this.vigenciaEea = vigenciaEea; }

    public String getFecRadicacionGna() { return fecRadicacionGna; }
    public void setFecRadicacionGna(String fecRadicacionGna) { this.fecRadicacionGna = fecRadicacionGna; }

    public String getNumRadicadoSolicitudGna() { return numRadicadoSolicitudGna; }
    public void setNumRadicadoSolicitudGna(String numRadicadoSolicitudGna) { this.numRadicadoSolicitudGna = numRadicadoSolicitudGna; }

    public String getFecExpedicionGna() { return fecExpedicionGna; }
    public void setFecExpedicionGna(String fecExpedicionGna) { this.fecExpedicionGna = fecExpedicionGna; }

    public String getNumDisponibilidadGna() { return numDisponibilidadGna; }
    public void setNumDisponibilidadGna(String numDisponibilidadGna) { this.numDisponibilidadGna = numDisponibilidadGna; }

    public Integer getVigenciaGna() { return vigenciaGna; }
    public void setVigenciaGna(Integer vigenciaGna) { this.vigenciaGna = vigenciaGna; }
}