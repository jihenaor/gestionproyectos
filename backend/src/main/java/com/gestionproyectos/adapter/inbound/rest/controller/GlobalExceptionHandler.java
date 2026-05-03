package com.gestionproyectos.adapter.inbound.rest.controller;

import com.gestionproyectos.application.service.ProyectoNotFoundException;
import com.gestionproyectos.application.service.ProyectoYaExisteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Adaptador inbound - Manejo global de excepciones.
 * Los 500 se registran siempre en log; el cuerpo JSON puede incluir detalle según {@code app.error.expose-details}.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final boolean exposeDetails;

    public GlobalExceptionHandler(@Value("${app.error.expose-details:false}") boolean exposeDetails) {
        this.exposeDetails = exposeDetails;
    }

    @ExceptionHandler(ProyectoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ProyectoNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(ProyectoYaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(ProyectoYaExisteException ex) {
        log.warn("Conflicto: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Validación fallida: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(HttpStatus.BAD_REQUEST, "Validación fallida: " + errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento ilegal: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        log.error("Error interno no manejado [{}]: {}", ex.getClass().getName(), ex.getMessage(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.put("message", "Error interno del sistema");
        if (exposeDetails) {
            body.put("exception", ex.getClass().getName());
            body.put("detail", ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private Map<String, Object> errorResponse(HttpStatus status, String message) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }
}
