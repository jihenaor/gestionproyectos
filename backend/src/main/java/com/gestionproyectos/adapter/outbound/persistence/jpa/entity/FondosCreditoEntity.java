package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_FONDOS_CREDITO")
@Data
public class FondosCreditoEntity {

    @Id
    @Column(name = "ID_FONDO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "MODALIDAD_CREDITO", nullable = false)
    private Integer modalidadCredito;

    @Column(name = "COD_CATEGORIA", nullable = false)
    private Integer codCategoria;

    @Column(name = "TASA_INTERES_MINIMA", length = 5, nullable = false)
    private String tasaInteresMinima;

    @Column(name = "TASA_INTERES_MAXIMA", length = 5, nullable = false)
    private String tasaInteresMaxima;

    @Column(name = "CANT_CREDITOS", nullable = false)
    private Integer cantCreditos;

    @Column(name = "VAL_MONTO_CREDITOS", nullable = false)
    private Long valMontoCreditos;

    @Column(name = "PLAZO_CREDITO", nullable = false)
    private Integer plazoCredito;

    @Column(name = "PORCENTAJE_SUBSIDIO", length = 5, nullable = false)
    private String porcentajeSubsidio;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}