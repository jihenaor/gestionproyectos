package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "GP_LOCALIZACION")
@Data
public class LocalizacionEntity {

    @Id
    @Column(name = "ID_LOCALIZACION", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "DEPARTAMENTO", length = 100, nullable = false)
    private String departamento;

    @Column(name = "MUNICIPIO", length = 100, nullable = false)
    private String municipio;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @Column(name = "BARRIO", length = 100)
    private String barrio;

    @Column(name = "TELEFONO", length = 30)
    private String telefono;

    @Column(name = "CONTACTO", length = 50)
    private String contacto;

    @Column(name = "FECHA_INICIO_OPERACION", length = 8)
    private String fechaInicioOperacion;

    @Column(name = "LATITUD")
    private BigDecimal latitud;

    @Column(name = "LONGITUD")
    private BigDecimal longitud;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fechaCreacion;
}