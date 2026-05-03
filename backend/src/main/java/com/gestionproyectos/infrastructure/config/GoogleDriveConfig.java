package com.gestionproyectos.infrastructure.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "google.drive.enabled", havingValue = "true", matchIfMissing = false)
public class GoogleDriveConfig {

    @Value("${google.drive.service-account-key-path:#{null}}")
    private String credentialsPath;

    @Value("${google.drive.application-name:GestionProyectos}")
    private String applicationName;

    @Value("${google.drive.folder-id:}")
    private String rootFolderId;

    @Bean(name = "googleDriveRootFolderId")
    public String googleDriveRootFolderId() {
        if (rootFolderId == null || rootFolderId.isBlank()) {
            String msg = "rootFolderId no configurado. Configure google.drive.folder-id en application.yml o la variable de entorno GOOGLE_DRIVE_FOLDER_ID.";
            log.error(msg);
            throw new IllegalStateException(msg);
        }
        log.info("Root Folder ID (Google Drive API): {}", rootFolderId);
        return rootFolderId.trim();
    }

    @Bean
    public Drive googleDriveClient() throws GeneralSecurityException, IOException {
        log.info("Inicializando servicio de Google Drive");
        log.info("Application Name: {}", applicationName);

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        final GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleCredentials credentials = getCredentials();

        Drive driveService = new Drive.Builder(
                httpTransport,
                jsonFactory,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(applicationName)
                .build();

        log.info("Servicio de Google Drive inicializado correctamente");

        return driveService;
    }

    private GoogleCredentials getCredentials() throws IOException {
        if (credentialsPath == null || credentialsPath.isBlank()) {
            String errorMsg = "No se ha configurado la ruta de credenciales de Google Drive. " +
                    "Configure la propiedad 'google.drive.service-account-key-path'";
            log.error(errorMsg);
            throw new IllegalStateException(errorMsg);
        }

        log.info("Cargando credenciales desde: {}", credentialsPath);

        try {
            InputStream credentialsStream;

            if (credentialsPath.startsWith("classpath:")) {
                String resourcePath = credentialsPath.substring("classpath:".length());
                credentialsStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

                if (credentialsStream == null) {
                    String errorMsg = "No se encontró el archivo de credenciales en classpath: " + resourcePath;
                    log.error(errorMsg);
                    throw new IOException(errorMsg);
                }

                log.info("Credenciales cargadas desde classpath: {}", resourcePath);
            } else {
                credentialsStream = new FileInputStream(credentialsPath);
                log.info("Credenciales cargadas desde archivo: {}", credentialsPath);
            }

            return GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(Collections.singletonList(DriveScopes.DRIVE));

        } catch (IOException e) {
            String errorMsg = "Error al cargar credenciales de Google Drive desde: " + credentialsPath;
            log.error(errorMsg, e);
            throw new IOException(errorMsg, e);
        }
    }
}