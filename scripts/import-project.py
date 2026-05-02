#!/usr/bin/env python3
"""
Script para importar datos de proyecto desde XML al backend
Uso: python import-project.py <archivo.xml> [api_base_url]

Ejemplo:
    python import-project.py CCF044P-001A022026.xml http://localhost:8080/api
"""

import sys
import xml.etree.ElementTree as ET
import requests
import json
from datetime import datetime

def parse_xml_date(date_str):
    """Convertir fecha de AAAAMMDD a ISO 8601"""
    if date_str and len(date_str) == 8 and date_str.isdigit():
        return f"{date_str[:4]}-{date_str[4:6]}-{date_str[6:8]}"
    return date_str

def main():
    if len(sys.argv) < 2:
        print("Uso: python import-project.py <archivo.xml> [api_base_url]")
        sys.exit(1)

    xml_path = sys.argv[1]
    api_base = sys.argv[2] if len(sys.argv) > 2 else "http://localhost:8080/api"

    # Parsear XML
    try:
        tree = ET.parse(xml_path)
        root = tree.getroot()
    except Exception as e:
        print(f"ERROR: No se pudo parsear el archivo XML: {e}")
        sys.exit(1)

    # Buscar nodo del proyecto
    ns = {'p': ''}  # namespace vacío para este XML
    proyecto = root.find('.//T_PROYECTOS_NUEVOS')

    if proyecto is None:
        print("ERROR: No se encontró el nodo T_PROYECTOS_NUEVOS")
        sys.exit(1)

    # Extraer campos
    def get_text(element, tag, default=''):
        child = proyecto.find(tag)
        return child.text.strip() if child is not None and child.text else default

    codigo_proyecto = get_text(proyecto, 'COD_PROYECTO')
    nombre_proyecto = get_text(proyecto, 'DESCRIPCION_PROYECTO')
    modalidad = get_text(proyecto, 'MOD_PROYECTO')
    valor_total = get_text(proyecto, 'VAL_TOTAL_PROYECTO')
    valor_aprobado = get_text(proyecto, 'VALOR_APR_VIGENCIA')
    justificacion = get_text(proyecto, 'JUSTIFICACION')
    objetivos = get_text(proyecto, 'OBJETIVO_PROYECTO')
    resolucion_aei = get_text(proyecto, 'RESOLUCION_AEI')
    num_acta = get_text(proyecto, 'NUM_ACTA_AEI')
    fecha_aei = get_text(proyecto, 'FECHA_APR_AEI')
    num_consejeros = get_text(proyecto, 'NUM_CONSEJEROS')
    tmp_recuperacion = get_text(proyecto, 'TMP_RECUPERACION')
    num_beneficiarios = get_text(proyecto, 'NUM_PERSONAS_REFERENCIA')

    # Campos adicionales
    estudio_mercado = get_text(proyecto, 'ESTUDIO_MERCADO')
    evaluacion_social = get_text(proyecto, 'EVALUACION_SOCIAL')
    evaluacion_financiera = get_text(proyecto, 'EVALUACION_FINANCIERA')

    # Construir payload
    payload = {
        "codigoProyecto": codigo_proyecto,
        "nombreProyecto": nombre_proyecto,
        "modalidadInversion": modalidad,
        "valorTotalProyecto": int(valor_total) if valor_total else 0,
        "valorAprobadoVigencia": int(valor_aprobado) if valor_aprobado else 0,
        "justificacion": justificacion,
        "objetivos": objetivos,
        "resolucionAEI": int(resolucion_aei) if resolucion_aei else 0,
        "numActa": num_acta,
        "fechaConsejo": parse_xml_date(fecha_aei),
        "numConsejeros": int(num_consejeros) if num_consejeros else 1,
        "tiempoRecuperacion": int(tmp_recuperacion) if tmp_recuperacion else 1,
        "tasaDescuento": None,
        "numeroBeneficiarios": int(num_beneficiarios) if num_beneficiarios else 0,
        "descripcionObjetivo": f"Estudio Mercado: {estudio_mercado}\n\nEvaluacion Social: {evaluacion_social}\n\nEvaluacion Financiera: {evaluacion_financiera}"
    }

    print("=== Datos del Proyecto ===")
    print(f"Codigo: {codigo_proyecto}")
    print(f"Nombre: {nombre_proyecto[:80]}...")
    print(f"Modalidad: {modalidad}")
    print(f"Valor Total: {valor_total}")
    print(f"Valor Aprobado: {valor_aprobado}")
    print("========================")

    # Enviar a la API
    url = f"{api_base}/v1/estructuras/P-001A"
    print(f"\nEnviando a: {url}")

    try:
        response = requests.post(url, json=payload, timeout=30)
        print(f"\nStatus: {response.status_code}")
        print(f"Respuesta: {response.text}")

        if response.status_code in [200, 201]:
            print("\n✓ Importacion exitosa!")
        else:
            print(f"\n✗ Error: {response.status_code}")
    except requests.exceptions.RequestException as e:
        print(f"\n✗ Error de conexion: {e}")

if __name__ == "__main__":
    main()