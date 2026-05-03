package com.gestionproyectos.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "LOCALIZACION")
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
    private java.math.BigDecimal latitud;

    @Column(name = "LONGITUD")
    private java.math.BigDecimal longitud;

    @Column(name = "FEC_CREACION")
    private java.time.LocalDateTime fechaCreacion;

    public LocalizacionEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdProyecto() { return idProyecto; }
    public void setIdProyecto(UUID idProyecto) { this.idProyecto = idProyecto; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getFechaInicioOperacion() { return fechaInicioOperacion; }
    public void setFechaInicioOperacion(String fechaInicioOperacion) { this.fechaInicioOperacion = fechaInicioOperacion; }

    public java.math.BigDecimal getLatitud() { return latitud; }
    public void setLatitud(java.math.BigDecimal latitud) { this.latitud = latitud; }

    public java.math.BigDecimal getLongitud() { return longitud; }
    public void setLongitud(java.math.BigDecimal longitud) { this.longitud = longitud; }

    public java.time.LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}