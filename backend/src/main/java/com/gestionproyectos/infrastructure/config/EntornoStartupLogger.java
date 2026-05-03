package com.gestionproyectos.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Deja en log (WARN/ERROR) qué perfiles y qué JDBC se usan, para detectar
 * arranques en "producción" que siguen con H2 por olvidar {@code SPRING_PROFILES_ACTIVE=as400}.
 */
@Slf4j
@Component
public class EntornoStartupLogger implements ApplicationRunner {

    private final Environment environment;

    public EntornoStartupLogger(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        String[] active = environment.getActiveProfiles();
        String perfilStr = active.length > 0 ? String.join(", ", active) : "(ninguno explícito; revisar spring.profiles.active/default)";
        String jdbcUrl = environment.getProperty("spring.datasource.url", "");
        String driver = environment.getProperty("spring.datasource.driver-class-name", "");

        log.info("Arranque — perfiles activos: [{}] | driver: {} | jdbc (inicio): {}",
                perfilStr,
                driver.isEmpty() ? "?" : driver,
                jdbcResumen(jdbcUrl));

        List<String> perfiles = Arrays.asList(active);
        boolean tieneLocal = perfiles.contains("local");
        boolean tieneAs400 = perfiles.contains("as400");

        if (tieneLocal && tieneAs400) {
            log.error(
                    "CONFIGURACIÓN INVÁLIDA: están activos los perfiles 'local' y 'as400' a la vez. "
                            + "En IBM i use SOLO as400 (p. ej. SPRING_PROFILES_ACTIVE=as400), sin 'local', "
                            + "o seguirá mezclando reglas y puede usarse H2.");
        }

        if (tieneAs400 && jdbcUrl.toLowerCase().contains("jdbc:h2")) {
            log.error(
                    "CONFIGURACIÓN INVÁLIDA: perfil 'as400' activo pero spring.datasource.url apunta a H2. "
                            + "Revise el orden de perfiles y application-as400.yml.");
        }

        if (!tieneAs400 && jdbcUrl.toLowerCase().contains("jdbc:h2")) {
            log.warn(
                    "MODO DESARROLLO (H2): no está el perfil 'as400'. "
                            + "Si este proceso debe conectarse a IBM i, defina SPRING_PROFILES_ACTIVE=as400 "
                            + "(solo ese perfil) y reinicie; de lo contrario los datos serán en memoria H2.");
        }

        if (tieneAs400) {
            String jdbcUser = environment.getProperty("spring.datasource.username", "");
            log.warn(
                    "IBM i JDBC: usuario configurado (spring.datasource.username)='{}'. "
                            + "Si al arrancar aparece 'User ID is not known', ese perfil no existe en ESE servidor: "
                            + "use DSPUSRPRF en IBM i o ajuste SPRING_DATASOURCE_USERNAME a un perfil existente.",
                    jdbcUser);
        }
    }

    private static String jdbcResumen(String url) {
        if (url == null || url.isBlank()) {
            return "(sin URL)";
        }
        int max = 120;
        String u = url.replaceAll("(?i)(password|pwd)=[^;&]+", "$1=***");
        return u.length() <= max ? u : u.substring(0, max) + "...";
    }
}
