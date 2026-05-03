package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "RENOVACION_URBANA")
public class RenovacionUrbanaEntity {

    @Id
    @Column(name = "ID_RENOVACION", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "NUM_SOLUCIONES_VIV", nullable = false)
    private Integer numSolucionesViv;

    public RenovacionUrbanaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyectoFovis() { return idProyectoFovis; }
    public void setIdProyectoFovis(UUID idProyectoFovis) { this.idProyectoFovis = idProyectoFovis; }

    public Integer getNumSolucionesViv() { return numSolucionesViv; }
    public void setNumSolucionesViv(Integer numSolucionesViv) { this.numSolucionesViv = numSolucionesViv; }
}