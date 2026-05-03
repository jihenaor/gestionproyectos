package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "FV_PROYECTOS")
@Data
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
}