package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "DATOS_GENERALES")
public class DatosGeneralesEntity {

    @Id
    @Column(name = "ID", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "COD_PROYECTO", length = 15, nullable = false)
    private String codigoProyecto;

    @Column(name = "NOMBRE_PROYECTO", length = 200, nullable = false)
    private String nombreProyecto;

    @Column(name = "MODALIDAD_INVERSION", length = 3, nullable = false)
    private String modalidadInversion;

    @Column(name = "VALOR_TOTAL_PROYECTO", nullable = false)
    private Long valorTotalProyecto;

    @Column(name = "VALOR_APROBADO_VIGENCIA", nullable = false)
    private Long valorAprobadoVigencia;

    @Column(name = "JUSTIFICACION", length = 4000)
    private String justificacion;

    @Column(name = "OBJETIVOS", length = 2000)
    private String objetivos;

    @Column(name = "RESOLUCION_AEI")
    private Integer resolucionAEI;

    @Column(name = "NUM_ACTA", length = 20)
    private String numActa;

    @Column(name = "FECHA_CONSEJO", length = 8)
    private String fechaConsejo;

    @Column(name = "NUM_CONSEJEROS")
    private Integer numConsejeros;

    @Column(name = "TIEMPO_RECUPERACION")
    private Integer tiempoRecuperacion;

    @Column(name = "TASA_DESCUENTO", length = 10)
    private String tasaDescuento;

    @Column(name = "NUMERO_BENEFICIARIOS")
    private Long numeroBeneficiarios;

    @Column(name = "DESCRIPCION_OBJETIVO", length = 1000)
    private String descripcionObjetivo;

    @Column(name = "FECHA_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private java.time.LocalDateTime fechaActualizacion;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public DatosGeneralesEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigoProyecto() { return codigoProyecto; }
    public void setCodigoProyecto(String codigo) { this.codigoProyecto = codigo; }

    public String getNombreProyecto() { return nombreProyecto; }
    public void setNombreProyecto(String nombre) { this.nombreProyecto = nombre; }

    public String getModalidadInversion() { return modalidadInversion; }
    public void setModalidadInversion(String modalidad) { this.modalidadInversion = modalidad; }

    public Long getValorTotalProyecto() { return valorTotalProyecto; }
    public void setValorTotalProyecto(Long valor) { this.valorTotalProyecto = valor; }

    public Long getValorAprobadoVigencia() { return valorAprobadoVigencia; }
    public void setValorAprobadoVigencia(Long valor) { this.valorAprobadoVigencia = valor; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public String getObjetivos() { return objetivos; }
    public void setObjetivos(String objetivos) { this.objetivos = objetivos; }

    public Integer getResolucionAEI() { return resolucionAEI; }
    public void setResolucionAEI(Integer resolucionAEI) { this.resolucionAEI = resolucionAEI; }

    public String getNumActa() { return numActa; }
    public void setNumActa(String numActa) { this.numActa = numActa; }

    public String getFechaConsejo() { return fechaConsejo; }
    public void setFechaConsejo(String fecha) { this.fechaConsejo = fecha; }

    public Integer getNumConsejeros() { return numConsejeros; }
    public void setNumConsejeros(Integer num) { this.numConsejeros = num; }

    public Integer getTiempoRecuperacion() { return tiempoRecuperacion; }
    public void setTiempoRecuperacion(Integer tiempo) { this.tiempoRecuperacion = tiempo; }

    public String getTasaDescuento() { return tasaDescuento; }
    public void setTasaDescuento(String tasa) { this.tasaDescuento = tasa; }

    public Long getNumeroBeneficiarios() { return numeroBeneficiarios; }
    public void setNumeroBeneficiarios(Long numero) { this.numeroBeneficiarios = numero; }

    public String getDescripcionObjetivo() { return descripcionObjetivo; }
    public void setDescripcionObjetivo(String desc) { this.descripcionObjetivo = desc; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fecha) { this.fechaCreacion = fecha; }

    public java.time.LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(java.time.LocalDateTime fecha) { this.fechaActualizacion = fecha; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}