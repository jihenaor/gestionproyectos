package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_CARTERA_EDADES")
@Data
public class CarteraEdadesEntity {

    @Id
    @Column(name = "ID_CARTERA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "RAN_EDAD", length = 2, nullable = false)
    private String ranEdad;

    @Column(name = "EDAD_CARTERA", nullable = false)
    private Integer edadCartera;

    @Column(name = "MODALIDAD_CREDITO", nullable = false)
    private Integer modalidadCredito;

    @Column(name = "COD_CATEGORIA", nullable = false)
    private Integer codCategoria;

    @Column(name = "CANT_CREDITOS", nullable = false)
    private Integer cantCreditos;

    @Column(name = "VALOR_TOTAL_MONTO_CARTERA", nullable = false)
    private Long valorTotalMontoCartera;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}