"""Genera sql/seed_pruebas_CCF044_P001A022026.sql desde el XML de Banco de Proyectos."""
import xml.etree.ElementTree as ET
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
XML = ROOT / "_data_BancodeProyectos-508723940_Files_CCF044P-001A022026.xml"
OUT = Path(__file__).resolve().parent / "seed_pruebas_CCF044_P001A022026.sql"


def esc(s: str) -> str:
    return s.replace("'", "''")


def norm(s: str) -> str:
    """Una sola línea SQL: quita saltos de línea y colapsa espacios."""
    return " ".join(s.split())


def q(s: str | None) -> str:
    if s is None:
        return "NULL"
    return "'" + esc(s) + "'"


def main() -> None:
    tree = ET.parse(XML)
    row = tree.find(".//T_PROYECTOS_NUEVOS")
    if row is None:
        raise SystemExit("No T_PROYECTOS_NUEVOS in XML")

    def txt(tag: str) -> str:
        el = row.find(tag)
        return (el.text or "").strip() if el is not None else ""

    cols = {child.tag: (child.text or "").strip() for child in row}

    # UUID fijos para pruebas reproducibles (RFC4122 forma válida)
    pid = "ccf04404-0002-8000-8000-000000000001"
    dgid = "ccf04404-0002-8000-8000-000000000002"

    num_acta = cols.get("NUM_ACTA_AEI", "")
    try:
        num_acta_i = int(num_acta) if num_acta else None
    except ValueError:
        num_acta_i = None

    tmp = cols.get("TMP_RECUPERACION", "")
    try:
        tmp_i = int(tmp) if tmp else None
    except ValueError:
        tmp_i = None

    fecha = cols.get("FECHA_APR_AEI", "") or None

    lines: list[str] = [
        "-- Datos de prueba (script aparte; no forma parte de CREAR_TABLAS.sql).",
        "-- Fuente XML: _data_BancodeProyectos-508723940_Files_CCF044P-001A022026.xml",
        "-- Legado: BDSUPER.GP_PROYECTOS, GP_DATOS_GENERALES. JPA: BDSUPER.PROYECTO (GET /api/v1/proyectos).",
        "-- Idempotente: borra por COD_PROYECTO antes de insertar.",
        "",
        "DELETE FROM BDSUPER.GP_DATOS_GENERALES",
        "WHERE ID_PROYECTO IN (SELECT ID_PROYECTO FROM BDSUPER.GP_PROYECTOS WHERE COD_PROYECTO = 'CCF044-04-00028');",
        "",
        "DELETE FROM BDSUPER.GP_PROYECTOS WHERE COD_PROYECTO = 'CCF044-04-00028';",
        "",
    ]

    desc = norm(txt("DESCRIPCION_PROYECTO"))[:4000]
    obj = norm(txt("OBJETIVO_PROYECTO"))[:4000]
    just = norm(txt("JUSTIFICACION"))[:4000]
    est_m = norm(txt("ESTUDIO_MERCADO"))[:4000]
    ev_s = norm(txt("EVALUACION_SOCIAL"))[:4000]
    ev_f = norm(txt("EVALUACION_FINANCIERA"))[:4000]

    na_sql = str(num_acta_i) if num_acta_i is not None else "NULL"
    tr_sql = str(tmp_i) if tmp_i is not None else "NULL"
    fecha_sql = q(fecha) if fecha else "NULL"

    ins = f"""INSERT INTO BDSUPER.GP_PROYECTOS (
    ID_PROYECTO, COD_PROYECTO, MOD_PROYECTO, MOD_DE_INVERSION,
    VAL_TOTAL_PROYECTO, VALOR_APR_VIGENCIA,
    DESCRIPCION_PROYECTO, OBJETIVO_PROYECTO, JUSTIFICACION,
    RESOLUCION_AEI, NUM_ACTA_AEI, FECHA_APR_AEI, NUM_CONSEJEROS, TMP_RECUPERACION,
    ESTUDIO_MERCADO, EVALUACION_SOCIAL, EVALUACION_FINANCIERA,
    NUM_PERSONAS_REFERENCE, NUM_POBLACION_AFECTADA,
    ESTADO_PROYECTO, USUARIO_CREACION, USUARIO_MODIFICACION
) VALUES (
    {q(pid)}, {q(cols['COD_PROYECTO'])}, {int(cols['MOD_PROYECTO'])}, {int(cols['MOD_DE_INVERSION'])},
    {int(cols['VAL_TOTAL_PROYECTO'])}, {int(cols['VALOR_APR_VIGENCIA'])},
    {q(desc)}, {q(obj)}, {q(just)},
    {int(cols['RESOLUCION_AEI'])}, {na_sql}, {fecha_sql}, {int(cols['NUM_CONSEJEROS'])}, {tr_sql},
    {q(est_m)}, {q(ev_s)}, {q(ev_f)},
    {int(cols['NUM_PERSONAS_REFERENCIA'])}, {int(cols['NUM_POBLACION_AFECTADA'])},
    'BORRADOR', 'SEED_XML', 'SEED_XML'
);"""

    desc_obj = obj[:2000]

    ins2 = f"""INSERT INTO BDSUPER.GP_DATOS_GENERALES (
    ID_DATo_GENERAL, ID_PROYECTO,
    OBJETIVOS_ESPECIFICOS, DESCRIPCION_OBJETIVO,
    TIEMPO_RECUPERACION, TASA_DESCUENTO, NUMERO_BENEFICIARIOS
) VALUES (
    {q(dgid)}, {q(pid)},
    {q(obj)},
    {q(desc_obj)},
    {tr_sql},
    NULL,
    {int(cols['NUM_POBLACION_AFECTADA'])}
);"""

    # typo fix ID_DATo_GENERAL -> ID_DATO_GENERAL
    ins2 = ins2.replace("ID_DATo_GENERAL", "ID_DATO_GENERAL")

    mod_inv = str(int(cols["MOD_DE_INVERSION"])).zfill(3)[:3]
    nombre_jpa = esc(norm(txt("DESCRIPCION_PROYECTO"))[:200])
    just_jpa = norm(txt("JUSTIFICACION"))[:4000]

    ins_proyecto = f"""INSERT INTO BDSUPER.PROYECTO (
    ID_PROYECTO, COD_PROYECTO, NOMBRE, MODALIDAD_INVERSION,
    VALOR_TOTAL, VALOR_APROBADO, JUSTIFICACION, ESTADO,
    FECHA_CREACION, ULTIMA_ACTUALIZACION, VERSION
) VALUES (
    {q(pid)}, {q(cols['COD_PROYECTO'])}, {q(nombre_jpa)}, {q(mod_inv)},
    {int(cols['VAL_TOTAL_PROYECTO'])}, {int(cols['VALOR_APR_VIGENCIA'])},
    {q(just_jpa)},
    'BORRADOR',
    CURRENT_DATE,
    CURRENT_TIMESTAMP,
    0
);"""

    lines.extend(
        [
            ins,
            "",
            ins2,
            "",
            "-- PROYECTO (JPA): listado REST /api/v1/proyectos",
            "DELETE FROM BDSUPER.PROYECTO_ESTRUCTURAS WHERE ID_PROYECTO = 'ccf04404-0002-8000-8000-000000000001';",
            "",
            f"DELETE FROM BDSUPER.PROYECTO WHERE COD_PROYECTO = {q(cols['COD_PROYECTO'])};",
            "",
            ins_proyecto,
            "",
        ]
    )
    OUT.write_text("\n".join(lines), encoding="utf-8")
    print(f"OK -> {OUT}")


if __name__ == "__main__":
    main()
