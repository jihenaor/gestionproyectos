package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_DATOS_GENERALES")
@Data
public class GpDatosGeneralesEntity {

    @Id
    @Column(name = "ID_DATO_GENERAL", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "OBJETIVOS_ESPECIFICOS", length = 4000)
    private String objetivosEspecificos;

    @Column(name = "DESCRIPCION_OBJETIVO", length = 2000)
    private String descripcionObjetivo;

    @Column(name = "TIEMPO_RECUPERACION")
    private Integer tiempoRecuperacion;

    @Column(name = "TASA_DESCUENTO", length = 5)
    private String tasaDescuento;

    @Column(name = "NUMERO_BENEFICIARIOS")
    private Long numeroBeneficiarios;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fecCreacion;

    @Column(name = "FEC_MODIFICACION")
    private LocalDateTime fecModificacion;
}