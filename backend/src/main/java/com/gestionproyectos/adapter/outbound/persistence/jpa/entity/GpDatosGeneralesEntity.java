package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "GP_DATOS_GENERALES")
public class GpDatosGeneralesEntity {

    @Id
    @Column(name = "ID_DATO_GENERAL", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "ID_PROYECTO", length = 36, nullable = false)
    private UUID idProyecto;

    @Column(name = "OBJETIVOS_ESPECIFICOS", length = 4000)
    private String objetivosEspecificos;

    @Column(name = "DESCRIPCION_OBJETIVO", length = 2000)
    private String descripcionObjetivo;

    @Column(name = "TIEMPO_RECUPERACION")
    private Integer tiempoRecuperacion;

    @Column(name = "TASA_DESCUENTO", length = 5)
    private String tasaDescuento;

    @Column(name = "NUMERO_BENEFICIARIOS")
    private Long numeroBeneficiarios;

    @Column(name = "FEC_CREACION")
    private LocalDateTime fecCreacion;

    @Column(name = "FEC_MODIFICACION")
    private LocalDateTime fecModificacion;

    public GpDatosGeneralesEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getObjetivosEspecificos() { return objetivosEspecificos; }
    public void setObjetivosEspecificos(String objetivosEspecificos) { this.objetivosEspecificos = objetivosEspecificos; }

    public String getDescripcionObjetivo() { return descripcionObjetivo; }
    public void setDescripcionObjetivo(String descripcionObjetivo) { this.descripcionObjetivo = descripcionObjetivo; }

    public Integer getTiempoRecuperacion() { return tiempoRecuperacion; }
    public void setTiempoRecuperacion(Integer tiempoRecuperacion) { this.tiempoRecuperacion = tiempoRecuperacion; }

    public String getTasaDescuento() { return tasaDescuento; }
    public void setTasaDescuento(String tasaDescuento) { this.tasaDescuento = tasaDescuento; }

    public Long getNumeroBeneficiarios() { return numeroBeneficiarios; }
    public void setNumeroBeneficiarios(Long numeroBeneficiarios) { this.numeroBeneficiarios = numeroBeneficiarios; }

    public LocalDateTime getFecCreacion() { return fecCreacion; }
    public void setFecCreacion(LocalDateTime fecCreacion) { this.fecCreacion = fecCreacion; }

    public LocalDateTime getFecModificacion() { return fecModificacion; }
    public void setFecModificacion(LocalDateTime fecModificacion) { this.fecModificacion = fecModificacion; }
}