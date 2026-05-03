package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "INFRAESTRUCTURA")
public class InfraestructuraEntity {

    @Id
    @Column(name = "ID_INFRAESTRUCTURA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "INTERVENTORIA_SUPERVISION", nullable = false)
    private Integer interventoriaSupervision;

    @Column(name = "VALOR_TOTAL_INTERVENTORIA")
    private Long valorTotalInterventoria;

    @Column(name = "LICENCIA_CONSTRUCCION", nullable = false)
    private Integer licenciaConstruccion;

    @Column(name = "ENT_COMPETENTE", length = 250)
    private String entCompetente;

    @Column(name = "NUM_RADICADO_SOLICITUD_LICENCIA", length = 30)
    private String numRadicadoSolicitudLicencia;

    @Column(name = "FECHA_RADICACION_SOLICITUD_LICENCIA", length = 8)
    private String fechaRadicacionSolicitudLicencia;

    @Column(name = "NUMERO_LICENCIA", length = 250)
    private String numeroLicencia;

    @Column(name = "FECHA_LICENCIA", length = 250)
    private String fechaLicencia;

    @Column(name = "VIGENCIA_LICENCIA", length = 50)
    private String vigenciaLicencia;

    @Column(name = "SERVICIOS_PUBLICOS", nullable = false)
    private Integer serviciosPublicos;

    @Column(name = "FECHA_RADICACION_AAA", length = 8)
    private String fechaRadicacionAaa;

    @Column(name = "NUM_RADICADO_SOLICITUD_AAA", length = 30)
    private String numRadicadoSolicitudAaa;

    @Column(name = "FECHA_EXPEDICION_AAA", length = 8)
    private String fechaExpedicionAaa;

    @Column(name = "NUM_DISPONIBILIDAD_AAA", length = 30)
    private String numDisponibilidadAaa;

    @Column(name = "VIGENCIA_AAA")
    private Integer vigenciaAaa;

    @Column(name = "FECHA_RADICACION_EEA", length = 8)
    private String fechaRadicacionEea;

    @Column(name = "NUM_RADICADO_SOLICITUD_EEA", length = 30)
    private String numRadicadoSolicitudEea;

    @Column(name = "FECHA_EXPEDICION_EEA", length = 8)
    private String fechaExpedicionEea;

    @Column(name = "NUM_DISPONIBILIDAD_EEA", length = 30)
    private String numDisponibilidadEea;

    @Column(name = "VIGENCIA_EEA")
    private Integer vigenciaEea;

    @Column(name = "FECHA_RADICACION_GNA", length = 8)
    private String fechaRadicacionGna;

    @Column(name = "NUM_RADICADO_SOLICITUD_GNA", length = 30)
    private String numRadicadoSolicitudGna;

    @Column(name = "FECHA_EXPEDICION_GNA", length = 8)
    private String fechaExpedicionGna;

    @Column(name = "NUM_DISPONIBILIDAD_GNA", length = 30)
    private String numDisponibilidadGna;

    @Column(name = "VIGENCIA_GNA")
    private Integer vigenciaGna;

    @Column(name = "PROYECCION_GENERACION_EMPLEO")
    private Integer proyeccionGeneracionEmpleo;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public InfraestructuraEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public Integer getInterventoriaSupervision() { return interventoriaSupervision; }
    public void setInterventoriaSupervision(Integer interventoriaSupervision) { this.interventoriaSupervision = interventoriaSupervision; }

    public Long getValorTotalInterventoria() { return valorTotalInterventoria; }
    public void setValorTotalInterventoria(Long valorTotalInterventoria) { this.valorTotalInterventoria = valorTotalInterventoria; }

    public Integer getLicenciaConstruccion() { return licenciaConstruccion; }
    public void setLicenciaConstruccion(Integer licenciaConstruccion) { this.licenciaConstruccion = licenciaConstruccion; }

    public String getEntCompetente() { return entCompetente; }
    public void setEntCompetente(String entCompetente) { this.entCompetente = entCompetente; }

    public String getNumRadicadoSolicitudLicencia() { return numRadicadoSolicitudLicencia; }
    public void setNumRadicadoSolicitudLicencia(String numRadicadoSolicitudLicencia) { this.numRadicadoSolicitudLicencia = numRadicadoSolicitudLicencia; }

    public String getFechaRadicacionSolicitudLicencia() { return fechaRadicacionSolicitudLicencia; }
    public void setFechaRadicacionSolicitudLicencia(String fechaRadicacionSolicitudLicencia) { this.fechaRadicacionSolicitudLicencia = fechaRadicacionSolicitudLicencia; }

    public String getNumeroLicencia() { return numeroLicencia; }
    public void setNumeroLicencia(String numeroLicencia) { this.numeroLicencia = numeroLicencia; }

    public String getFechaLicencia() { return fechaLicencia; }
    public void setFechaLicencia(String fechaLicencia) { this.fechaLicencia = fechaLicencia; }

    public String getVigenciaLicencia() { return vigenciaLicencia; }
    public void setVigenciaLicencia(String vigenciaLicencia) { this.vigenciaLicencia = vigenciaLicencia; }

    public Integer getServiciosPublicos() { return serviciosPublicos; }
    public void setServiciosPublicos(Integer serviciosPublicos) { this.serviciosPublicos = serviciosPublicos; }

    public String getFechaRadicacionAaa() { return fechaRadicacionAaa; }
    public void setFechaRadicacionAaa(String fechaRadicacionAaa) { this.fechaRadicacionAaa = fechaRadicacionAaa; }

    public String getNumRadicadoSolicitudAaa() { return numRadicadoSolicitudAaa; }
    public void setNumRadicadoSolicitudAaa(String numRadicadoSolicitudAaa) { this.numRadicadoSolicitudAaa = numRadicadoSolicitudAaa; }

    public String getFechaExpedicionAaa() { return fechaExpedicionAaa; }
    public void setFechaExpedicionAaa(String fechaExpedicionAaa) { this.fechaExpedicionAaa = fechaExpedicionAaa; }

    public String getNumDisponibilidadAaa() { return numDisponibilidadAaa; }
    public void setNumDisponibilidadAaa(String numDisponibilidadAaa) { this.numDisponibilidadAaa = numDisponibilidadAaa; }

    public Integer getVigenciaAaa() { return vigenciaAaa; }
    public void setVigenciaAaa(Integer vigenciaAaa) { this.vigenciaAaa = vigenciaAaa; }

    public String getFechaRadicacionEea() { return fechaRadicacionEea; }
    public void setFechaRadicacionEea(String fechaRadicacionEea) { this.fechaRadicacionEea = fechaRadicacionEea; }

    public String getNumRadicadoSolicitudEea() { return numRadicadoSolicitudEea; }
    public void setNumRadicadoSolicitudEea(String numRadicadoSolicitudEea) { this.numRadicadoSolicitudEea = numRadicadoSolicitudEea; }

    public String getFechaExpedicionEea() { return fechaExpedicionEea; }
    public void setFechaExpedicionEea(String fechaExpedicionEea) { this.fechaExpedicionEea = fechaExpedicionEea; }

    public String getNumDisponibilidadEea() { return numDisponibilidadEea; }
    public void setNumDisponibilidadEea(String numDisponibilidadEea) { this.numDisponibilidadEea = numDisponibilidadEea; }

    public Integer getVigenciaEea() { return vigenciaEea; }
    public void setVigenciaEea(Integer vigenciaEea) { this.vigenciaEea = vigenciaEea; }

    public String getFechaRadicacionGna() { return fechaRadicacionGna; }
    public void setFechaRadicacionGna(String fechaRadicacionGna) { this.fechaRadicacionGna = fechaRadicacionGna; }

    public String getNumRadicadoSolicitudGna() { return numRadicadoSolicitudGna; }
    public void setNumRadicadoSolicitudGna(String numRadicadoSolicitudGna) { this.numRadicadoSolicitudGna = numRadicadoSolicitudGna; }

    public String getFechaExpedicionGna() { return fechaExpedicionGna; }
    public void setFechaExpedicionGna(String fechaExpedicionGna) { this.fechaExpedicionGna = fechaExpedicionGna; }

    public String getNumDisponibilidadGna() { return numDisponibilidadGna; }
    public void setNumDisponibilidadGna(String numDisponibilidadGna) { this.numDisponibilidadGna = numDisponibilidadGna; }

    public Integer getVigenciaGna() { return vigenciaGna; }
    public void setVigenciaGna(Integer vigenciaGna) { this.vigenciaGna = vigenciaGna; }

    public Integer getProyeccionGeneracionEmpleo() { return proyeccionGeneracionEmpleo; }
    public void setProyeccionGeneracionEmpleo(Integer proyeccionGeneracionEmpleo) { this.proyeccionGeneracionEmpleo = proyeccionGeneracionEmpleo; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}