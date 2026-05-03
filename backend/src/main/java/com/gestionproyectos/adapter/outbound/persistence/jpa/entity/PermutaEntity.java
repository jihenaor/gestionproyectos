package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "PERMUTA")
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
    private java.time.LocalDateTime fechaCreacion;

    public PermutaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getFecCertTradicionLibertad() { return fecCertTradicionLibertad; }
    public void setFecCertTradicionLibertad(String fecCertTradicionLibertad) { this.fecCertTradicionLibertad = fecCertTradicionLibertad; }

    public String getFecAvaluoRecibe() { return fecAvaluoRecibe; }
    public void setFecAvaluoRecibe(String fecAvaluoRecibe) { this.fecAvaluoRecibe = fecAvaluoRecibe; }

    public String getFecAvaluoEntrega() { return fecAvaluoEntrega; }
    public void setFecAvaluoEntrega(String fecAvaluoEntrega) { this.fecAvaluoEntrega = fecAvaluoEntrega; }

    public String getAvaluadorRecibe() { return avaluadorRecibe; }
    public void setAvaluadorRecibe(String avaluadorRecibe) { this.avaluadorRecibe = avaluadorRecibe; }

    public String getAvaluadorEntrega() { return avaluadorEntrega; }
    public void setAvaluadorEntrega(String avaluadorEntrega) { this.avaluadorEntrega = avaluadorEntrega; }

    public Long getValAvaluoRecibe() { return valAvaluoRecibe; }
    public void setValAvaluoRecibe(Long valAvaluoRecibe) { this.valAvaluoRecibe = valAvaluoRecibe; }

    public Long getValAvaluoEntrega() { return valAvaluoEntrega; }
    public void setValAvaluoEntrega(Long valAvaluoEntrega) { this.valAvaluoEntrega = valAvaluoEntrega; }

    public String getDestinacionInmueble() { return destinacionInmueble; }
    public void setDestinacionInmueble(String destinacionInmueble) { this.destinacionInmueble = destinacionInmueble; }

    public String getUsoAutorizado() { return usoAutorizado; }
    public void setUsoAutorizado(String usoAutorizado) { this.usoAutorizado = usoAutorizado; }

    public Long getValLibros() { return valLibros; }
    public void setValLibros(Long valLibros) { this.valLibros = valLibros; }

    public Long getUtilidadPerdida() { return utilidadPerdida; }
    public void setUtilidadPerdida(Long utilidadPerdida) { this.utilidadPerdida = utilidadPerdida; }

    public String getOrigenRecursos() { return origenRecursos; }
    public void setOrigenRecursos(String origenRecursos) { this.origenRecursos = origenRecursos; }

    public String getDestinacionRecursos() { return destinacionRecursos; }
    public void setDestinacionRecursos(String destinacionRecursos) { this.destinacionRecursos = destinacionRecursos; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}