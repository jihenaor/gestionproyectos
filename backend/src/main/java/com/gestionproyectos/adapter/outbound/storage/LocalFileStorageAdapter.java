package com.gestionproyectos.adapter.outbound.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class LocalFileStorageAdapter {

    private final String basePath;

    public LocalFileStorageAdapter(@Value("${ccf.storage.base-path:/tmp/gestionproyectos}") String basePath) {
        this.basePath = basePath;
        initDirectory();
    }

    private void initDirectory() {
        try {
            Path path = Paths.get(basePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento", e);
        }
    }

    public String storeFile(MultipartFile file, String projectId, String documentType) throws IOException {
        String folderPath = basePath + "/" + projectId + "/" + documentType;
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String storedFilename = UUID.randomUUID().toString() + extension;
        Path filePath = folder.resolve(storedFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }

    public byte[] retrieveFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    public void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }

    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}