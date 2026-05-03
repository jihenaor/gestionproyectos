package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_INFRAESTRUCTURA")
@Data
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
    private LocalDateTime fechaCreacion;
}