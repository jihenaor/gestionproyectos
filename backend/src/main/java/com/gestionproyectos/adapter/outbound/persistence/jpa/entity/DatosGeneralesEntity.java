package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "DATOS_GENERALES")
@Data
public class DatosGeneralesEntity {

    @Id
    @Column(name = "ID", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "COD_PROYECTO", length = 15, nullable = false)
    private String codigoProyecto;

    @Column(name = "NOMBRE_PROYECTO", length = 200, nullable = false)
    private String nombreProyecto;

    @Column(name = "MODALIDAD_INVERSION", length = 3, nullable = false)
    private String modalidadInversion;

    @Column(name = "VALOR_TOTAL_PROYECTO", nullable = false)
    private Long valorTotalProyecto;

    @Column(name = "VALOR_APROBADO_VIGENCIA", nullable = false)
    private Long valorAprobadoVigencia;

    @Column(name = "JUSTIFICACION", length = 4000)
    private String justificacion;

    @Column(name = "OBJETIVOS", length = 2000)
    private String objetivos;

    @Column(name = "RESOLUCION_AEI")
    private Integer resolucionAEI;

    @Column(name = "NUM_ACTA", length = 20)
    private String numActa;

    @Column(name = "FECHA_CONSEJO", length = 8)
    private String fechaConsejo;

    @Column(name = "NUM_CONSEJEROS")
    private Integer numConsejeros;

    @Column(name = "TIEMPO_RECUPERACION")
    private Integer tiempoRecuperacion;

    @Column(name = "TASA_DESCUENTO", length = 10)
    private String tasaDescuento;

    @Column(name = "NUMERO_BENEFICIARIOS")
    private Long numeroBeneficiarios;

    @Column(name = "DESCRIPCION_OBJETIVO", length = 1000)
    private String descripcionObjetivo;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @Version
    @Column(name = "VERSION")
    private Long version;
}