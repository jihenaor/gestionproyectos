package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "PROYECTO")
public class ProyectoEntity {

    @Id
    @Column(name = "ID_PROYECTO")
    private UUID id;

    @Column(name = "COD_PROYECTO", length = 15, nullable = false, unique = true)
    private String codigoProyecto;

    @Column(name = "NOMBRE", length = 200, nullable = false)
    private String nombre;

    @Column(name = "MODALIDAD_INVERSION", length = 3, nullable = false)
    private String modalidadInversion;

    @Column(name = "VALOR_TOTAL", nullable = false)
    private Long valorTotal;

    @Column(name = "VALOR_APROBADO", nullable = false)
    private Long valorAprobado;

    @Column(name = "JUSTIFICACION", length = 4000)
    private String justificacion;

    @Column(name = "ESTADO", length = 20, nullable = false)
    private String estado;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "ULTIMA_ACTUALIZACION", nullable = false)
    private LocalDateTime ultimaActualizacion;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROYECTO_ESTRUCTURAS",
                     joinColumns = @JoinColumn(name = "ID_PROYECTO"))
    @Column(name = "NOMBRE_ESTRUCTURA")
    private Set<String> estructurasCompletadas = new HashSet<>();

    @Version
    @Column(name = "VERSION")
    private Long version;

    public ProyectoEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigoProyecto() { return codigoProyecto; }
    public void setCodigoProyecto(String codigo) { this.codigoProyecto = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getModalidadInversion() { return modalidadInversion; }
    public void setModalidadInversion(String modalidad) { this.modalidadInversion = modalidad; }

    public Long getValorTotal() { return valorTotal; }
    public void setValorTotal(Long valorTotal) { this.valorTotal = valorTotal; }

    public Long getValorAprobado() { return valorAprobado; }
    public void setValorAprobado(Long valorAprobado) { this.valorAprobado = valorAprobado; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getUltimaActualizacion() { return ultimaActualizacion; }
    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) { this.ultimaActualizacion = ultimaActualizacion; }

    public Set<String> getEstructurasCompletadas() { return estructurasCompletadas; }
    public void setEstructurasCompletadas(Set<String> estructuras) { this.estructurasCompletadas = estructuras; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}