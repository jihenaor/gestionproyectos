package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_SEGUIMIENTO")
@Data
public class SeguimientoFovisEntity {

    @Id
    @Column(name = "ID_SEGUIMIENTO", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "ANO_EJECUCION", nullable = false)
    private Integer anoEjecucion;

    @Column(name = "MES_EJECUCION", nullable = false)
    private Integer mesEjecucion;

    @Column(name = "TIPO_ACTIVIDAD", length = 5, nullable = false)
    private String tipoActividad;

    @Column(name = "VALOR_EJECUTADO")
    private Long valorEjecutado;

    @Column(name = "CANTIDAD_CREDITOS_ASIGNADOS")
    private Integer cantidadCreditosAsignados;

    @Column(name = "VALOR_CREDITOS_ASIGNADOS")
    private Long valorCreditosAsignados;

    @Column(name = "FECHA_COMPRA_LOTE", length = 8)
    private String fechaCompraLote;

    @Column(name = "VALOR_TOTAL_COMPRA_LOTE")
    private Long valorTotalCompraLote;

    @Column(name = "VALOR_OTROS_COSTOS")
    private Long valorOtrosCostos;

    @Column(name = "COMENTARIOS", length = 4000)
    private String comentarios;
}