package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_PERMUTA")
@Data
public class PermutaEntity {

    @Id
    @Column(name = "ID_PERMUTA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "FEC_CERT_TRADICION_LIBERTAD", length = 8, nullable = false)
    private String fecCertTradicionLibertad;

    @Column(name = "FEC_AVALUO_RECIBE", length = 8, nullable = false)
    private String fecAvaluoRecibe;

    @Column(name = "FEC_AVALUO_ENTREGA", length = 8, nullable = false)
    private String fecAvaluoEntrega;

    @Column(name = "AVALUADOR_RECIBE", length = 50, nullable = false)
    private String avaluadorRecibe;

    @Column(name = "AVALUADOR_ENTREGA", length = 50, nullable = false)
    private String avaluadorEntrega;

    @Column(name = "VAL_AVALUO_RECIBE", nullable = false)
    private Long valAvaluoRecibe;

    @Column(name = "VAL_AVALUO_ENTREGA", nullable = false)
    private Long valAvaluoEntrega;

    @Column(name = "DESTINACION_INMUEBLE", length = 50, nullable = false)
    private String destinacionInmueble;

    @Column(name = "USO_AUTORIZADO", length = 50, nullable = false)
    private String usoAutorizado;

    @Column(name = "VAL_LIBROS", nullable = false)
    private Long valLibros;

    @Column(name = "UTILIDAD_PERDIDA", nullable = false)
    private Long utilidadPerdida;

    @Column(name = "ORIGEN_RECURSOS", length = 30, nullable = false)
    private String origenRecursos;

    @Column(name = "DESTINACION_RECURSOS", length = 80, nullable = false)
    private String destinacionRecursos;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}