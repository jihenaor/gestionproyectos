package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "PROYECTO")
@Data
public class ProyectoEntity {

    @Id
    @Column(name = "ID_PROYECTO", length = 36)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(name = "COD_PROYECTO", length = 15, nullable = false, unique = true)
    private String codigoProyecto;

    @Column(name = "NOMBRE", length = 200, nullable = false)
    private String nombre;

    @Column(name = "MODALIDAD_INVERSION", length = 3, nullable = false)
    private String modalidadInversion;

    @Column(name = "VALOR_TOTAL", nullable = false)
    private Long valorTotal;

    @Column(name = "VALOR_APROBADO", nullable = false)
    private Long valorAprobado;

    @Column(name = "JUSTIFICACION", length = 4000)
    private String justificacion;

    @Column(name = "ESTADO", length = 20, nullable = false)
    private String estado;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "ULTIMA_ACTUALIZACION", nullable = false)
    private LocalDateTime ultimaActualizacion;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROYECTO_ESTRUCTURAS",
                     joinColumns = @JoinColumn(name = "ID_PROYECTO"))
    @Column(name = "NOMBRE_ESTRUCTURA")
    private Set<String> estructurasCompletadas = new HashSet<>();

    @Version
    @Column(name = "VERSION")
    private Long version;
}