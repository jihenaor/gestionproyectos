package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_SEGUIMIENTO")
@Data
public class SeguimientoEntity {

    @Id
    @Column(name = "ID_SEGUIMIENTO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "PERIODO_REPORTE", length = 8, nullable = false)
    private String periodoReporte;

    @Column(name = "TIPO_ACTIVIDAD", length = 5, nullable = false)
    private String tipoActividad;

    @Column(name = "DESCRIPCION_ACTIVIDAD", length = 500)
    private String descripcionActividad;

    @Column(name = "PORCENTAJE_EJECUTADO", length = 5)
    private String porcentajeEjecutado;

    @Column(name = "VALOR_PLANEADO")
    private Long valorPlaneado;

    @Column(name = "VALOR_EJECUTADO")
    private Long valorEjecutado;

    @Column(name = "COSTO_ACTUAL")
    private Long costoActual;

    @Column(name = "VALOR_PAGADO")
    private Long valorPagado;

    @Column(name = "VALOR_GANADO")
    private Long valorGanado;

    @Column(name = "CANTIDAD_EJECUCION_FISICA")
    private Long cantidadEjecucionFisica;

    @Column(name = "FECHA_INICIO", length = 8)
    private String fechaInicio;

    @Column(name = "FECHA_TERMINACION", length = 8)
    private String fechaTerminacion;

    @Column(name = "OBSERVACIONES", length = 2000)
    private String observaciones;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}