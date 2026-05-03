package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_PROYECTOS")
@Data
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

    @Column(name = "VALOR_APR_VIGENCIA")
    private Long valorAprVigencia;

    @Column(name = "DESCRIPCION_PROYECTO", length = 4000)
    private String descripcionProyecto;

    @Column(name = "OBJETIVO_PROYECTO", length = 4000)
    private String objetivoProyecto;

    @Column(name = "JUSTIFICACION", length = 4000)
    private String justificacion;

    @Column(name = "RESOLUCION_AEI")
    private Integer resolucionAEI;

    @Column(name = "NUM_ACTA_AEI")
    private Long numActaAei;

    @Column(name = "FECHA_APR_AEI", length = 8)
    private String fechaAprAei;

    @Column(name = "NUM_CONSEJEROS")
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
}