package com.gestionproyectos.application.service.googledrive;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConditionalOnProperty(name = "google.drive.enabled", havingValue = "true", matchIfMissing = false)
public class GoogleDriveService {

    private final Drive driveService;
    private final String rootFolderId;

    public GoogleDriveService(Drive driveService,
                              @Qualifier("googleDriveRootFolderId") String rootFolderId) {
        this.driveService = driveService;
        this.rootFolderId = rootFolderId;
    }

    public record DriveFile(
            String id,
            String name,
            String mimeType,
            Long size,
            String webViewLink,
            String webContentLink
    ) {}

    public String uploadFile(String fileName, InputStream fileContent, String mimeType, String folderPath) {
        try {
            log.info("Subiendo archivo a Google Drive. Archivo: {}, Tipo MIME: {}", fileName, mimeType);

            String targetFolderId = resolveFolderId(folderPath);

            File fileMetadata = new File();
            fileMetadata.setName(fileName);
            fileMetadata.setParents(Collections.singletonList(targetFolderId));

            InputStreamContent mediaContent = new InputStreamContent(
                    mimeType != null && !mimeType.isBlank() ? mimeType : "application/octet-stream",
                    fileContent
            );

            File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                    .setFields("id, name, mimeType, size, webViewLink")
                    .setSupportsAllDrives(true)
                    .execute();

            log.info("Archivo subido exitosamente. ID: {}, Nombre: {}", uploadedFile.getId(), uploadedFile.getName());

            return uploadedFile.getId();

        } catch (IOException e) {
            log.error("Error al subir archivo a Google Drive: {}", e.getMessage(), e);
            throw new RuntimeException("Error al subir archivo a Google Drive: " + e.getMessage(), e);
        }
    }

    public String uploadPdf(byte[] pdfContent, String fileName, String folderPath) {
        return uploadFile(fileName, new java.io.ByteArrayInputStream(pdfContent), "application/pdf", folderPath);
    }

    public Optional<DriveFile> getFileInfo(String fileId) {
        try {
            File file = driveService.files().get(fileId)
                    .setFields("id, name, mimeType, size, webViewLink, webContentLink")
                    .setSupportsAllDrives(true)
                    .execute();

            return Optional.of(new DriveFile(
                    file.getId(),
                    file.getName(),
                    file.getMimeType(),
                    file.getSize(),
                    file.getWebViewLink(),
                    file.getWebContentLink()
            ));

        } catch (IOException e) {
            log.error("Error al obtener información del archivo: {}", fileId, e);
            return Optional.empty();
        }
    }

    public List<DriveFile> listFilesInFolder(String folderId) {
        try {
            String query = "'%s' in parents and trashed = false".formatted(folderId);

            log.debug("Listando archivos en carpeta: {}", folderId);

            var result = driveService.files().list()
                    .setQ(query)
                    .setFields("files(id, name, mimeType, size, webViewLink)")
                    .setPageSize(100)
                    .setSupportsAllDrives(true)
                    .setIncludeItemsFromAllDrives(true)
                    .setSpaces("drive")
                    .execute();

            log.info("Archivos encontrados: {}", result.getFiles().size());

            return result.getFiles().stream()
                    .map(file -> new DriveFile(
                            file.getId(),
                            file.getName(),
                            file.getMimeType(),
                            file.getSize(),
                            file.getWebViewLink(),
                            null
                    ))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Error al listar archivos en carpeta: {}", folderId, e);
            throw new RuntimeException("Error al listar archivos de Google Drive", e);
        }
    }

    public Optional<String> findFolderIdByName(String folderName, String parentId) {
        try {
            String query = "name = '%s' and '%s' in parents and mimeType = 'application/vnd.google-apps.folder' and trashed = false"
                    .formatted(folderName, parentId);

            var result = driveService.files().list()
                    .setQ(query)
                    .setFields("files(id, name)")
                    .setPageSize(10)
                    .setSupportsAllDrives(true)
                    .setIncludeItemsFromAllDrives(true)
                    .setSpaces("drive")
                    .execute();

            if (result.getFiles().isEmpty()) {
                log.warn("No se encontró carpeta: {} en carpeta: {}", folderName, parentId);
                return Optional.empty();
            }

            return Optional.of(result.getFiles().get(0).getId());

        } catch (IOException e) {
            log.error("Error al buscar carpeta: {}", folderName, e);
            throw new RuntimeException("Error al buscar carpeta en Google Drive", e);
        }
    }

    public Optional<String> findFileIdByName(String fileName, String folderId) {
        try {
            String query = "name = '%s' and '%s' in parents and trashed = false"
                    .formatted(fileName, folderId);

            var result = driveService.files().list()
                    .setQ(query)
                    .setFields("files(id, name)")
                    .setPageSize(10)
                    .setSupportsAllDrives(true)
                    .setIncludeItemsFromAllDrives(true)
                    .setSpaces("drive")
                    .execute();

            if (result.getFiles().isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(result.getFiles().get(0).getId());

        } catch (IOException e) {
            log.error("Error al buscar archivo: {}", fileName, e);
            return Optional.empty();
        }
    }

    public byte[] downloadFileAsBytes(String fileId) {
        try (InputStream inputStream = driveService.files().get(fileId)
                .setSupportsAllDrives(true)
                .executeMediaAsInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            inputStream.transferTo(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("Error al descargar archivo: {}", fileId, e);
            throw new RuntimeException("Error al descargar archivo de Google Drive", e);
        }
    }

    public String downloadFileAsBase64(String fileId) {
        byte[] bytes = downloadFileAsBytes(fileId);
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }

    public void deleteFile(String fileId) {
        try {
            log.info("Eliminando archivo en Google Drive. File ID: {}", fileId);
            driveService.files().delete(fileId)
                    .setSupportsAllDrives(true)
                    .execute();
            log.info("Archivo eliminado: {}", fileId);
        } catch (IOException e) {
            log.error("Error al eliminar archivo: {}", fileId, e);
            throw new RuntimeException("Error al eliminar archivo de Google Drive", e);
        }
    }

    public void shareFileWithDomain(String fileId, String domain) {
        try {
            Permission permission = new Permission();
            permission.setType("domain");
            permission.setRole("reader");
            permission.setDomain(domain);

            driveService.permissions().create(fileId, permission)
                    .setSupportsAllDrives(true)
                    .setSendNotificationEmail(false)
                    .execute();

            log.info("Archivo {} compartido con dominio: {}", fileId, domain);
        } catch (IOException e) {
            log.warn("No se pudo compartir archivo {} con dominio {}: {}", fileId, domain, e.getMessage());
        }
    }

    public String createFolder(String folderName, String parentId) {
        try {
            File folderMetadata = new File();
            folderMetadata.setName(folderName);
            folderMetadata.setMimeType("application/vnd.google-apps.folder");
            folderMetadata.setParents(Collections.singletonList(parentId));

            File createdFolder = driveService.files().create(folderMetadata)
                    .setFields("id, name")
                    .setSupportsAllDrives(true)
                    .execute();

            log.info("Carpeta creada: {} con ID: {}", folderName, createdFolder.getId());
            return createdFolder.getId();

        } catch (IOException e) {
            log.error("Error al crear carpeta: {}", folderName, e);
            throw new RuntimeException("Error al crear carpeta en Google Drive", e);
        }
    }

    private String resolveFolderId(String folderPath) {
        if (folderPath == null || folderPath.isEmpty()) {
            return rootFolderId;
        }

        String[] folders = folderPath.split("/");
        String currentParentId = rootFolderId;

        for (String folderName : folders) {
            if (folderName.trim().isEmpty()) {
                continue;
            }

            Optional<String> folderId = findFolderIdByName(folderName.trim(), currentParentId);
            if (folderId.isEmpty()) {
                log.warn("Carpeta no encontrada: {}, creando nueva...", folderName);
                currentParentId = createFolder(folderName.trim(), currentParentId);
            } else {
                currentParentId = folderId.get();
            }
        }

        return currentParentId;
    }

    public String getRootFolderId() {
        return rootFolderId;
    }
}