package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_COMPRA_INMUEBLES")
@Data
public class CompraInmueblesEntity {

    @Id
    @Column(name = "ID_COMPRA", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "FEC_CERT_TRADICION_LIBERTAD", length = 8, nullable = false)
    private String fecCertTradicionLibertad;

    @Column(name = "FEC_AVALUO", length = 8, nullable = false)
    private String fecAvaluo;

    @Column(name = "PERITO", length = 50, nullable = false)
    private String perito;

    @Column(name = "VALOR_AVALUO", nullable = false)
    private Long valorAvaluo;

    @Column(name = "DESTINACION_INMUEBLE", length = 50, nullable = false)
    private String destinacionInmueble;

    @Column(name = "USO_AUTORIZADO", length = 50, nullable = false)
    private String usoAutorizado;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}