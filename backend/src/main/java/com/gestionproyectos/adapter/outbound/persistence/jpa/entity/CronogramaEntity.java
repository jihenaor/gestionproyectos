package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_CRONOGRAMA")
@Data
public class CronogramaEntity {

    @Id
    @Column(name = "ID_CRONOGRAMA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "TIPO_ACTIVIDAD", length = 5, nullable = false)
    private String tipoActividad;

    @Column(name = "DESCRIPCION_ACTIVIDAD", length = 500)
    private String descripcionActividad;

    @Column(name = "PORCENTAJE_PROYECTADO", length = 5, nullable = false)
    private String porcentajeProyectado;

    @Column(name = "FECHA_INICIO", length = 8, nullable = false)
    private String fechaInicio;

    @Column(name = "FECHA_TERMINACION", length = 8, nullable = false)
    private String fechaTerminacion;

    @Column(name = "UNIDAD_MEDIDA", length = 50)
    private String unidadMedida;

    @Column(name = "CANTIDAD_PROGRAMADA")
    private Long cantidadProgramada;

    @Column(name = "VALOR_PROGRAMADO")
    private Long valorProgramado;

    @Column(name = "ORDEN_EJECUCION")
    private Integer ordenEjecucion;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}