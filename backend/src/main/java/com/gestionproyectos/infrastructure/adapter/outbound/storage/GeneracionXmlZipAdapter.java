package com.gestionproyectos.infrastructure.adapter.outbound.storage;

import com.gestionproyectos.domain.port.out.ZipGenerationPort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class GeneracionXmlZipAdapter implements ZipGenerationPort {

    @Override
    public byte[] generarZip(Map<String, String> archivosXml) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (Map.Entry<String, String> entry : archivosXml.entrySet()) {
                String nombreArchivo = entry.getKey();
                String contenidoXml = entry.getValue();

                ZipEntry zipEntry = new ZipEntry(nombreArchivo);
                zos.putNextEntry(zipEntry);
                zos.write(contenidoXml.getBytes(StandardCharsets.UTF_8));
                zos.closeEntry();
            }

            zos.finish();
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generando ZIP: " + e.getMessage(), e);
        }
    }
}