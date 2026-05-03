package com.gestionproyectos.application.service;

public class ProyectoYaExisteException extends RuntimeException {
    public ProyectoYaExisteException(String message) {
        super(message);
    }
}