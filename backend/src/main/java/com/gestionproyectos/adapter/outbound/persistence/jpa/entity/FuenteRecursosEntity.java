package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_FUENTE_RECURSOS")
@Data
public class FuenteRecursosEntity {

    @Id
    @Column(name = "ID_FUENTE", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "COD_FUENTE", length = 10, nullable = false)
    private String codFuente;

    @Column(name = "DESC_FUENTE", length = 100, nullable = false)
    private String descFuente;

    @Column(name = "VALOR_ASIGNADO", nullable = false)
    private Long valorAsignado;

    @Column(name = "PORCENTAJE", length = 5)
    private String porcentaje;

    @Column(name = "TIPO_RECURSO", length = 50)
    private String tipoRecurso;

    @Column(name = "CENTRO_COSTO", length = 50)
    private String centroCosto;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}