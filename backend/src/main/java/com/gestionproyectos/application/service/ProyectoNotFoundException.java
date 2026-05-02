package com.gestionproyectos.application.service;

public class ProyectoNotFoundException extends RuntimeException {
    public ProyectoNotFoundException(String message) {
        super(message);
    }
}