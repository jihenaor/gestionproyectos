package com.gestionproyectos.application.service;

import com.gestionproyectos.adapter.outbound.storage.LocalFileStorageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DocumentoService {

    private final LocalFileStorageAdapter storageAdapter;
    private final Map<String, DocumentMetadata> documents = new ConcurrentHashMap<>();

    public DocumentoService(LocalFileStorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }

    public DocumentMetadata subirDocumento(String proyectoId, String tipoDocumento, MultipartFile file) throws IOException {
        String filePath = storageAdapter.storeFile(file, proyectoId, tipoDocumento);
        String documentId = UUID.randomUUID().toString();

        DocumentMetadata metadata = new DocumentMetadata(
                documentId,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                proyectoId,
                tipoDocumento,
                filePath
        );

        documents.put(documentId, metadata);
        return metadata;
    }

    public DocumentMetadata obtenerDocumento(String documentoId) {
        return documents.get(documentoId);
    }

    public List<DocumentMetadata> listarPorProyecto(String proyectoId) {
        return documents.values().stream()
                .filter(doc -> doc.proyectoId().equals(proyectoId))
                .toList();
    }

    public void eliminarDocumento(String documentoId) throws IOException {
        DocumentMetadata doc = documents.get(documentoId);
        if (doc != null) {
            storageAdapter.deleteFile(doc.filePath());
            documents.remove(documentoId);
        }
    }

    public record DocumentMetadata(
            String id,
            String nombreOriginal,
            String tipoContenido,
            long tamanho,
            String proyectoId,
            String tipoDocumento,
            String filePath
    ) {}
}