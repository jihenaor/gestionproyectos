package com.gestionproyectos.domain.port.out;

import java.util.Map;

public interface ZipGenerationPort {

    byte[] generarZip(Map<String, String> archivosXml);
}