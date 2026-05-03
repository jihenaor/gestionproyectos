package com.gestionproyectos.domain.model;

public sealed interface EstadoProyecto
    permits EstadoProyecto.Borrador,
            EstadoProyecto.EnEjecucion,
            EstadoProyecto.Completado,
            EstadoProyecto.Cancelado {

    String descripcion();
    boolean puedeTransicionarA(EstadoProyecto nuevoEstado);

    record Borrador() implements EstadoProyecto {
        public String descripcion() { return "Borrador"; }
        public boolean puedeTransicionarA(EstadoProyecto nuevo) {
            return nuevo instanceof EnEjecucion;
        }
    }

    record EnEjecucion() implements EstadoProyecto {
        public String descripcion() { return "En Ejecución"; }
        public boolean puedeTransicionarA(EstadoProyecto nuevo) {
            return nuevo instanceof Completado || nuevo instanceof Cancelado;
        }
    }

    record Completado() implements EstadoProyecto {
        public String descripcion() { return "Completado"; }
        public boolean puedeTransicionarA(EstadoProyecto nuevo) { return false; }
    }

    record Cancelado() implements EstadoProyecto {
        public String descripcion() { return "Cancelado"; }
        public boolean puedeTransicionarA(EstadoProyecto nuevo) { return false; }
    }

    static EstadoProyecto desdeString(String estado) {
        return switch (estado.toUpperCase()) {
            case "BORRADOR" -> new Borrador();
            case "EN_EJECUCION", "EN EJECUCION" -> new EnEjecucion();
            case "COMPLETADO" -> new Completado();
            case "CANCELADO" -> new Cancelado();
            default -> throw new IllegalArgumentException("Estado desconocido: " + estado);
        };
    }
}