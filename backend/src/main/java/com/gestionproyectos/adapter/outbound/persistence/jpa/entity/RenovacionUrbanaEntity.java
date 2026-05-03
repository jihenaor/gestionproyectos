package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_RENOVACION_URBANA")
@Data
public class RenovacionUrbanaEntity {

    @Id
    @Column(name = "ID_RENOVACION", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "NUM_SOLUCIONES_VIV", nullable = false)
    private Integer numSolucionesViv;
}