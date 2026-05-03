package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "FV_ADQUISICION_LOTES")
@Data
public class AdquisicionLotesEntity {

    @Id
    @Column(name = "ID_LOTE", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO_FOVIS", length = 36, nullable = false)
    private UUID idProyectoFovis;

    @Column(name = "AREA_LOTE", length = 10)
    private String areaLote;

    @Column(name = "VALOR_LOTE", nullable = false)
    private Long valorLote;

    @Column(name = "VALOR_OTROS_COSTOS")
    private Long valorOtrosCostos;

    @Column(name = "FEC_AVALUO", length = 8)
    private String fecAvaluo;

    @Column(name = "PERITO", length = 50)
    private String perito;

    @Column(name = "NUM_REGISTRO_AVALUADOR", length = 20)
    private String numRegistroAvaliador;

    @Column(name = "VALOR_AVALUO")
    private Long valorAvaluo;

    @Column(name = "FEC_CERT_TRADICION_LIBERTAD", length = 8)
    private String fecCertTradicionLibertad;

    @Column(name = "SERVICIOS_PUBLICOS")
    private Integer serviciosPublicos;
}