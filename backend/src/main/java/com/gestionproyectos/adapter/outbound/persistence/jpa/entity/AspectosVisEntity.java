package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_ASPECTOS_VIS")
@Data
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
}