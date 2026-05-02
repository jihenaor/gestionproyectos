package com.gestionproyectos.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Tras un fallo de arranque, si el mensaje indica rechazo IBM i, deja una pista explícita en el log
 * (el mensaje genérico de Hibernate no explica que falta el perfil en el AS400).
 */
@Slf4j
@Component
public class As400ConnectionFailureHints implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        Throwable t = event.getException();
        while (t != null) {
            String msg = t.getMessage();
            if (msg != null) {
                if (msg.contains("User ID is not known")) {
                    log.error(
                            "IBM i / JT400: 'User ID is not known' significa que el PERFIL DE USUARIO no existe "
                                    + "en ese sistema IBM i (o el nombre no coincide). No es un fallo de Spring. "
                                    + "Revise en el AS400 con DSPUSRPRF el nombre exacto del perfil; "
                                    + "ajuste SPRING_DATASOURCE_USERNAME (o AS400_USERNAME) o cree el perfil con CRTUSRPRF. "
                                    + "El servidor suele mostrar el id en mayúsculas (p. ej. SALJORGE).");
                    return;
                }
                if (msg.contains("Password is incorrect")) {
                    log.error(
                            "IBM i / JT400: contraseña incorrecta para el usuario indicado. "
                                    + "Revise SPRING_DATASOURCE_PASSWORD; si contiene $, en PowerShell use comillas simples "
                                    + "o defínala en variables de entorno del sistema.");
                    return;
                }
            }
            t = t.getCause();
        }
    }
}
