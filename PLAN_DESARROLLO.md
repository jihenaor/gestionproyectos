# PLAN DE DESARROLLO: SISTEMA DE GESTIÓN DE PROYECTOS
## Anexo Técnico Circular Externa 2025-00008
### Versión: Angular 21 + Java 21

---

## TABLA DE CONTENIDO

1. [Resumen Ejecutivo](#1-resumen-ejecutivo)
2. [Alcance del Sistema](#2-alcance-del-sistema)
3. [Arquitectura del Sistema](#3-arquitectura-del-sistema)
4. [Estructuras de Datos - Proyectos Generales (P-001A a P-056A)](#4-estructuras-de-datos---proyectos-generales-p-001a-a-p-056a)
5. [Estructuras de Datos - Proyectos FOVIS (P-001F a P-050F)](#5-estructuras-de-datos---proyectos-fovis-p-001f-a-p-050f)
6. [Módulos y Funcionalidades](#6-módulos-y-funcionalidades)
7. [Plan de Desarrollo por Fases](#7-plan-de-desarrollo-por-fases)
8. [Especificación de Campos por Estructura](#8-especificación-de-campos-por-estructura)
9. [Validaciones y Reglas de Negocio](#9-validaciones-y-reglas-de-negocio)
10. [Tablas de Referencia](#10-tablas-de-referencia)
11. [Estructuras XML de Reporte (XSD Schemas)](#11-estructuras-xml-de-reporte-xsd-schemas)
12. [Integración Google Drive](#12-integración-google-drive)
13. [Consideraciones Específicas DB2/AS400](#13-consideraciones-específicas-db2as400)
14. [Anexo: Modelo de Datos Conceptual](#anexo-modelo-de-datos-conceptual)
15. [Checklist de Implementación](#checklist-de-implementación)

---

## 1. RESUMEN EJECUTIVO

### 1.1 Objetivo del Proyecto
Desarrollar una aplicación web para la gestión integral de proyectos de inversión de Cajas de Compensación Familiar, cumplimiento del Anexo Técnico de la Circular Externa 2025-00008.

### 1.2 Tecnologia
- **Frontend:** Angular 21 (TypeScript, Signals, Standalone Components)
- **Backend:** Java 21 (Spring Boot 3.x, Gradle) - Arquitectura Monolítica
- **Base de Datos:** IBM DB2/AS400 (iSeries)
- **Almacenamiento PDFs:** Google Drive API
- **Autenticación:** Endpoint externo (SSO corporativo)

### 1.3 Principales Módulos
1. Gestión de Proyectos de Inversión (No FOVIS)
2. Gestión de Proyectos FOVIS
3. Módulo de Reportes y Cargue de Estructuras
4. Administración de Fondos de Crédito
5. Gestión de Bienes Inmuebles
6. Cartera por Edades
7. Captura de Documentos PDF

---

## 2. ALCANCE DEL SISTEMA

### 2.1 Proyectos Cubiertos

#### 2.1.1 Proyectos Generales (No FOVIS)

> **NOTA IMPORTANTE:** Según el Anexo Técnico de la Circular Externa 2025-00008, las modificaciones a los proyectos de inversión (P-001A, P-002A, P-004C, P-011A, P-013A, P-023A, P-026A, P-031A, P-034A, P-040A, P-005A) se deben radicar dentro de los 45 días siguientes a su aprobación por parte del Consejo Directivo (Artículo 2.2.7.5.3.3 del Decreto 1072 de 2015).

| Código | Nombre | Periodicidad | Archivo PDF Asociado | Obligatoriedad |
|--------|--------|--------------|---------------------|----------------|
| P-001A | Estructuras Generales Proyectos Nuevos | Único por proyecto (nuevo proyecto, una vez al mes y se realiza una única vez por cada proyecto) | - | Todas las CCF |
| P-002A | Cronograma Inicial del Proyecto | Único por proyecto | - | Todas las CCF |
| P-003A | Localización del Proyecto | Único por proyecto | - | Todas las CCF |
| P-004C | Estructura Fuente de Recursos del Proyecto - Centro de Costos | Por proyecto (una fuente por registro) | - | Todas las CCF |
| P-005A | Ficha Técnica Proyectos de Inversión | Periódica | P-005A.pdf | Todas las CCF |
| P-011A | Cobertura Proyectada | Por proyecto (proyección 5 años) | - | Todas las CCF |
| P-011B | Cobertura Ejecutada | ANUAL (una vez culminado el proyecto y por 5 años, si no aplica digitar "no aplica") | - | Todas las CCF |
| P-012A | Seguimiento del Proyecto | Trimestral (proyectos >12 meses) o mensual (proyectos <12 meses) | - | Todas las CCF |
| P-013A | Aspectos Específicos Proyectos de Infraestructura | Por proyecto (cada vez que nuevo proyecto) | - | Todas las CCF |
| P-014A | Soportes Aspectos Específicos de Infraestructura | Periódica | P-014A.pdf | Todas las CCF |
| P-023A | Aspectos Específicos - Fondos de Crédito | Por proyecto | - | Todas las CCF |
| P-024A | Cartera por Edades | Periódica | - | Todas las CCF |
| P-025A | Soporte Reglamento Actualizado del Fondo | Periódica | P-025A.pdf | Todas las CCF |
| P-026A | Arrendamiento Bienes Inmuebles | Por proyecto | - | Todas las CCF |
| P-027A | Soportes Arrendamiento Bienes Inmuebles | Mensual | P-027A.pdf | Todas las CCF |
| P-031A | Comodato Bienes Inmuebles | Por proyecto | - | Todas las CCF |
| P-032A | Soportes Comodato Bienes Inmuebles | Mensual | P-032A.pdf | Todas las CCF |
| P-034A | Compra Bienes Inmuebles | Por proyecto | - | Todas las CCF |
| P-035A | Soportes Compra Bienes Inmuebles | Mensual | P-035A.pdf | Todas las CCF |
| P-040A | Permuta Bienes Inmuebles | Por proyecto | - | Todas las CCF |
| P-041A | Soportes Permuta Bienes Inmuebles | Mensual | P-041A.pdf | Todas las CCF |
| P-050A | Negociación Acciones/Cuotas/Partes de Interés Social | Por proyecto | - | Todas las CCF |
| P-051A | Soportes Negociación Acciones/Cuotas/Partes de Interés Social | Periódica | P-051A.pdf | Todas las CCF |
| P-055A | Capitalizaciones | Por proyecto | - | Todas las CCF |
| P-056A | Soportes Capitalizaciones | Periódica | P-056A.pdf | Todas las CCF |

**Día límite de reporte:** El último día del mes hasta las 5 pm
**Firma digital requerida:** Director Administrativo

#### 2.1.2 Proyectos FOVIS

> **NOTA IMPORTANTE:** Los proyectos FOVIS (Fondo de Vivienda de Interés Social) tienen reglas especiales de condicionalidad según la MODALIDAD_FOVIS (Tabla 210). El campo MODALIDAD_FOVIS en P-001F determina qué campos son obligatorios en otras estructuras.

| Código | Nombre | Periodicidad | Archivo PDF Asociado | Condicionalidad |
|--------|--------|--------------|---------------------|-----------------|
| P-001F | Estructuras Generales Proyectos FOVIS Nuevos | Nuevo proyecto (cada vez que nuevo proyecto, una vez al mes, una única vez por cada proyecto) | - | Estructura principal FOVIS |
| P-002F | Cronograma Inicial del Proyecto FOVIS | Único por proyecto | - | **Condicional según MODALIDAD_FOVIS:** Si código 1,3,5,6 → PORCENTAJE_PROYECTADO. Si código 2 → CANTIDAD_CREDITOS y VALOR_CREDITOS |
| P-005F | Soportes FOVIS Ficha Técnica | Periódica | P-005F.pdf | Documentos: Estudio de Mercado, Evaluación Social, Evaluación Financiera, Justificación, Petición Formal, Acta Consejo, Certificación Revisor Fiscal |
| P-011F | Estructura Cobertura Proyectada FOVIS | Por proyecto | - | Proyección 5 años, por categoría (Tabla 8: códigos 1-4) |
| P-012F | Seguimiento del Proyecto FOVIS | Trimestral | - | **Condicional según MODALIDAD_FOVIS:** Si código 4 → fecha_compra_lote, valor_total_compra_lote, valor_otros_costos |
| P-014F | Aspectos Específicos - Adquisición y Desarrollo VIS / Compra Derechos Fiduciarios | Por proyecto (cada vez que nuevo proyecto) | - |Licencias y servicios públicos (AAA, EEA, GNA) |
| P-015F | Soportes Adquisición VIS / Compra Derechos Fiduciarios | Periódica | P-015F.pdf | Cronograma obra, Planos, Presupuesto, Licencia, Servicios públicos, Flujo de caja |
| P-022F | Tipología Aspectos Específicos VIS | Por proyecto | - | Tipología (Tabla 208), área m2, valor venta, número unidades |
| P-023F | Créditos Hipotecarios y Microcréditos para VIS | Por proyecto | - | Para Modalidad FOVIS código 2 |
| P-024F | Soportes Créditos Hipotecarios y Microcréditos VIS | Periódica | P-024F.pdf | Proyección plan anual, Requisitos, Estrategias recuperación, Procesos administrativos |
| P-028F | Financiación Oferentes de Programas y Proyectos VIS | Por proyecto | - | Para Modalidad FOVIS código 3 |
| P-029F | Tipología Oferentes VIS | Por proyecto | - | Tipología (Tabla 208), unidades, área, valor venta |
| P-030F | Soportes Financiación Oferentes VIS | Periódica | P-030F.pdf | Requisitos, Estrategias, Plan anual, Licencia, Servicios, Cronograma, Presupuesto |
| P-039F | Adquisición de Lotes para Proyectos VIS | Por proyecto | - | Para Modalidad FOVIS código 4 |
| P-040F | Soportes Adquisición de Lotes VIS | Periódica | P-040F.pdf | Avalúo comercial, Estudio de títulos, Libertad y tradición, Otros costos, Servicios públicos |
| P-049F | Programas Integrales de Renovación y Redensificación Urbana | Por proyecto | - | Para Modalidad FOVIS código 5 |
| P-050F | Soportes Renovación y Redensificación Urbana | Periódica | P-050F.pdf | Cronograma.pdf, Presupuesto.pdf, Flujo de caja.pdf |

**Día límite de reporte:** El último día del mes hasta las 5 pm
**Firma digital requerida:** Director Administrativo
**Obligatoriedad:** Todas las CCF

---

### 2.2 REGLAS DE PRESENTACIÓN AL USUARIO (Frontend)

El sistema debe mostrar al usuario de manera clara y precisa qué información se está solicitando, siguiendo estas reglas de visualización:

#### 2.2.1 Principios de UX/UI para Reporte de Estructuras

| Principio | Implementación | Descripción |
|-----------|----------------|-------------|
| **Claridad** | Cada campo debe tener label descriptivo + tooltip de ayuda | El usuario debe saber exactamente qué ingresar |
| **Formato visible** | Mostrar formato requerido junto al campo | Ej: "Fecha (AAAAMMDD): ___" |
| **Ejemplos** | Placeholder con ejemplo válido | Ej: "Código proyecto (ej: CCF001-01-00001)" |
| **Obligatoriedad** | Indicador visual claro (asterisco rojo) | Los campos obligatorios deben ser evidentes |
| **Condicionalidad** | Mostrar/ocultar campos según selección previa | Si el usuario selecciona "En trámite" en servicios públicos, mostrar campos de radicación |
| **Longitud** | Contador de caracteres remaining | El usuario debe saber cuánto puede escribir |
| **Validación en tiempo real** | Feedback inmediato al digitar | Mostrar errores antes de enviar |

#### 2.2.2 Componente de Campo con Tooltip de Ayuda

Cada campo del formulario debe incluir un tooltip que muestre:
1. **Descripción del campo** (qué debe digitar)
2. **Ejemplo de formato válido**
3. **Reglas de validación específicas**
4. **Referencia a tabla de ser necesario**

```html
<!-- Ejemplo de implementación Angular -->
<app-form-field
  [label]="'Código del Proyecto'"
  [tooltip]="'Código asignado al proyecto. Formato: Código Caja (2-3 dígitos) + \"-\" + Modalidad (Tabla 32) + \"-\" + Consecutivo (5 dígitos). Ejemplo: CCF001-01-00001'">
  <input
    formControlName="codigoProyecto"
    placeholder="CCF001-01-00001"
    maxlength="15" />
  <span helper>Formato: CCFXXX-XX-XXXXX - Longitud máxima: 15 caracteres</span>
</app-form-field>
```

#### 2.2.3 Estructura de Ayuda por Sección

El sistema debe mostrar help panels que incluyan:

```
┌─────────────────────────────────────────────────────────────────┐
│  P-001A: ESTRUCTURAS GENERALES PROYECTOS DE INVERSIÓN          │
├─────────────────────────────────────────────────────────────────┤
│  📋 Instrucciones:                                              │
│  Este formato se diligencia una única vez por proyecto, al      │
│  momento de la creación. Todos los campos son obligatorios      │
│  a menos que se indique lo contrario.                           │
│                                                                 │
│  ⏰ Día límite: El último día del mes hasta las 5 pm            │
│  👔 Firma: Director Administrativo                              │
│                                                                 │
│  📌 Estructuras relacionadas:                                   │
│  • P-002A (Cronograma) - obligatorio                            │
│  • P-004C (Fuentes) - obligatorio                               │
│  • P-011A (Cobertura) - obligatorio                             │
│  • P-005A (PDF Ficha Técnica) - obligatorio                     │
└─────────────────────────────────────────────────────────────────┘
```

#### 2.2.4 Reglas de Condicionalidad Visibles

Cuando un campo dependa de otro, el sistema debe:

1. **Mostrar la regla al usuario:**
   ```
   ┌────────────────────────────────────────────────────────────┐
   │  Servicios Públicos: [Dropdown: 1=N.A, 2=En trámite, 3=Expedida] │
   │                                                            │
   │  ⚠️ Si selecciona "En trámite" (código 2), debe diligenciar:
   │     • Número de radicado de solicitud                      │
   │     • Fecha de radicación (AAAAMMDD)                       │
   │                                                            │
   │  ⚠️ Si selecciona "Expedida" (código 3), debe diligenciar:
   │     • Número de licencia                                   │
   │     • Fecha de la licencia (AAAAMMDD)                      │
   │     • Vigencia de la licencia (años)                       │
   └────────────────────────────────────────────────────────────┘
   ```

2. **Campos condicionalmente obligatorios:**
   - Campos que solo se muestran según condición
   - Validación que solo se aplica según condición
   - Mensaje explicativo de por qué el campo aparece

#### 2.2.5 Especificación de Formatos Numéricos

El frontend debe mostrar claramente los formatos requeridos:

| Tipo de Dato | Formato Requerido | Visualización en UI |
|--------------|-------------------|---------------------|
| **Fecha** | AAAAMMDD (8 dígitos, sin separadores) | "Fecha: ___ (formato: AAAAMMDD, ej: 20250315)" |
| **Numérico sin decimales** | Sin puntos, sin comas, sin separadores | "Valor total (sin puntos ni comas): ___ " |
| **Decimal (tasas)** | Dos decimales separados por punto | "Tasa de interés (ej: 10.25): ___ " |
| **Porcentaje** | Sin símbolo %, número entero 0-100 | "Porcentaje (0-100): ___ " |
| **Código proyecto general** | CCFXXX-XX-XXXXX | "Código (ej: CCF001-01-00001): ___ " |
| **Código proyecto FOVIS** | XX-XX-XXXXX | "Código (ej: 01-01-00001): ___ " |

#### 2.2.6 Validación de Campos Obligatorios

```
┌─────────────────────────────────────────────────────────────────┐
│  CAMPOS OBLIGATORIOS (marcados con *)                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  * Código del Proyecto (formato: CCFXXX-XX-XXXXX)              │
│  * Modalidad del Proyecto (Tabla 32)                           │
│  * Modalidad de Inversión (Tabla 32)                            │
│  * Valor total del proyecto (sin decimales ni separadores)      │
│  * Valor aprobado para la vigencia (sin decimales ni separadores)│
│  * Descripción del Proyecto (mínimo 100 caracteres)            │
│  * Objetivo del Proyecto (mínimo 3 objetivos específicos)       │
│  * Justificación (mínimo 100 caracteres)                        │
│  * ¿Cuenta con resolución AEI? (1=Si, 2=No)                     │
│  ...                                                            │
└─────────────────────────────────────────────────────────────────┘
```

#### 2.2.7 Checklist de Documentos PDF

Para estructuras que requieren PDF, mostrar checklist visual:

```
┌─────────────────────────────────────────────────────────────────┐
│  P-005A: FICHA TÉCNICA PROYECTOS DE INVERSIÓN (PDF)            │
├─────────────────────────────────────────────────────────────────┤
│  📎 Documentos requeridos (en este orden):                      │
│                                                                 │
│  ☑️ 1. Solicitud del Representante Legal                        │
│  ☑️ 2. Soporte Tiempo de Recuperación (si aplica)               │
│  ☑️ 3. Soporte Estudio de Mercado (si aplica)                   │
│  ☑️ 4. Soporte Evaluación Social (si aplica)                    │
│  ☑️ 5. Soporte Evaluación Financiera                            │
│  ☑️ 6. Justificación del Proyecto                               │
│  ☑️ 7. Acta de Aprobación - Consejo Directivo                   │
│  ☑️ 8. Certificado del Revisor Fiscal                           │
│                                                                 │
│  📄 Formato: PDF | Tamaño máximo: 50MB                          │
│  📅 Certificado de libertad: vigencia máx 30 días               │
└─────────────────────────────────────────────────────────────────┘
```

#### 2.2.8 Validación de Códigos de Referencia

Cuando un campo requiera código de tabla de referencia:

```
┌─────────────────────────────────────────────────────────────────┐
│  Modalidad de Crédito: [Dropdown]                               │
├─────────────────────────────────────────────────────────────────┤
│  Tabla 66: MODALIDAD DE CRÉDITO SOCIAL                          │
│                                                                 │
│  Opciones disponibles:                                          │
│  ┌─────┬────────────────────────────────┐                      │
│  │ 1   │ Libre inversión                │                      │
│  │ 2   │ Consumo                        │                      │
│  │ 3   │ Créditos Educativos            │                      │
│  │ 4   │ Créditos de Salud              │                      │
│  │ 5   │ Créditos de vivienda           │                      │
│  │ 6   │ Fomento, Emprendimiento        │                      │
│  │ 7   │ Otros                          │                      │
│  │ 8   │ Crédito de mercadeo            │                      │
│  │ 9   │ Recreación y turismo           │                      │
│  └─────┴────────────────────────────────┘                      │
│                                                                 │
│  [ selected: 1 - Libre inversión ]                              │
└─────────────────────────────────────────────────────────────────┘
```

#### 2.2.9 Wizard/Stepper para Flujo de Creación de Proyecto

El sistema debe guiar al usuario paso a paso:

```
┌─────────────────────────────────────────────────────────────────┐
│  CREACIÓN DE PROYECTO - Paso 1 de 5                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ●━━●━━○━━○━━○                                                 │
│  1     2     3     4     5                                      │
│  │                                                      │        │
│  ▼                                                      ▼        │
│  Datos    Cronograma   Fuentes   Cobertura   Documentos         │
│  Generales                                   PDF                 │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │  P-001A: Datos Generales del Proyecto                      ││
│  │                                                             ││
│  │  * Código del Proyecto: [CCF001-01-00001     ] (auto)      ││
│  │  * Modalidad: [Dropdown Tabla 32     ]                      ││
│  │  ...                                                        ││
│  └─────────────────────────────────────────────────────────────┘│
│                                                                 │
│  [ Anterior ]  [ Siguiente > ]                                  │
└─────────────────────────────────────────────────────────────────┘
```

#### 2.2.10 Resumen de Estructuras Pendientes por Proyecto

El dashboard debe mostrar qué falta por cada proyecto:

```
┌─────────────────────────────────────────────────────────────────┐
│  PROYECTO: CCF001-01-00001 - Construcción Centro Recreativo    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Estructuras cargadas:                                          │
│  ☑️ P-001A - Datos Generales          (25/03/2025)              │
│  ☑️ P-002A - Cronograma                (25/03/2025)              │
│  ☐ P-003A - Localización               ❌ Pendiente             │
│  ☐ P-004C - Fuentes y Centros          ❌ Pendiente             │
│  ☐ P-005A - Ficha Técnica (PDF)        ❌ Pendiente             │
│  ☐ P-011A - Cobertura Proyectada       ❌ Pendiente             │
│  ○ P-011B - Cobertura Ejecutada        (no aplica aún)          │
│  ☐ P-012A - Seguimiento                ❌ Pendiente (trimestral)│
│  ☐ P-013A - Aspectos Infraestructura   ❌ Pendiente             │
│  ...                                                            │
│                                                                 │
│  ⚠️ 45 días para radicar modificaciones después de aprobación   │
└─────────────────────────────────────────────────────────────────┘
```

#### 2.2.11 Paleta de Colores para Estados

| Estado | Color | Uso |
|--------|-------|-----|
| Pendiente | `#F59E0B` (Amarillo/Naranja) | Campos no diligenciados |
| Completado | `#10B981` (Verde) | Campos válidos |
| Error/Inválido | `#DC2626` (Rojo) | Validación fallida |
| Condicional | `#3B82F6` (Azul) | Campos que aparecen según condición |
| Información | `#6B7280` (Gris) | Texto de ayuda |

---

## 3. ARQUITECTURA DEL SISTEMA

### 3.1 Arquitectura General - BACKEND MONOLÍTICO (Hexagonal + DDD)

```
                                    ┌─────────────────────────┐
                                    │      CLIENTE ANGULAR    │
                                    │          21             │
                                    └────────────┬────────────┘
                                                 │ REST API
                                                 ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                    BACKEND SPRING BOOT (Java 21)                          │
│                           PUERTO: 8080                                    │
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                     APPLICATION LAYER (CASOS DE USO)                 │ │
│  │  ┌────────────────┐  ┌────────────────┐  ┌────────────────────────┐│ │
│  │  │CrearProyecto   │  │  Seguimiento   │  │  GenerarReporte        ││ │
│  │  │UseCase         │  │  FOVISUseCase  │  │  UseCase               ││ │
│  │  └────────────────┘  └────────────────┘  └────────────────────────┘│ │
│  │  ┌────────────────┐  ┌────────────────┐  ┌────────────────────────┐│ │
│  │  │CargarCredito   │  │ GestionarInmue │  │  ValidarCumplimiento   ││ │
│  │  │UseCase         │  │ bleUseCase     │  │  UseCase               ││ │
│  │  └────────────────┘  └────────────────┘  └────────────────────────┘│ │
│  └─────────────────────────────────────────────────────────────────────┘ │
│           │                                                             │
│           ▼                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                         DOMAIN LAYER (DDD)                           │ │
│  │                                                                     │ │
│  │  ┌───────────────────────┐     ┌───────────────────────┐            │ │
│  │  │   ProyectoAggregate   │     │   ProyectoFOVIS       │            │ │
│  │  │   ┌─────────────────┐ │     │   Aggregate           │            │ │
│  │  │   │ Proyecto        │ │     │   ┌─────────────────┐ │            │ │
│  │  │   │ (Root Entity)   │ │     │   │ ProyectoFOVIS   │ │            │ │
│  │  │   └─────────────────┘ │     │   │ (Root Entity)   │ │            │ │
│  │  │   ┌─────────────────┐ │     │   └─────────────────┘ │            │ │
│  │  │   │ Cronograma      │ │     │   ┌─────────────────┐ │            │ │
│  │  │   │ (Entity)        │ │     │   │ Seguimiento     │ │            │ │
│  │  │   └─────────────────┘ │     │   │ (Entity)        │ │            │ │
│  │  └───────────────────────┘     └───────────────────────┘            │ │
│  │                                                                     │ │
│  │  ┌───────────────────────┐     ┌───────────────────────┐            │ │
│  │  │   CreditoAggregate    │     │   BienInmueble        │            │ │
│  │  │   ┌─────────────────┐ │     │   Aggregate           │            │ │
│  │  │   │ Credito         │ │     │   ┌─────────────────┐ │            │ │
│  │  │   │ (Root Entity)   │ │     │   │ Arrendamiento   │ │            │ │
│  │  │   └─────────────────┘ │     │   │ (Entity)        │ │            │ │
│  │  │   ┌─────────────────┐ │     │   └─────────────────┘ │            │ │
│  │  │   │ TasaInteres     │ │     │                       │            │ │
│  │  │   │ (Value Object)  │ │     │                       │            │ │
│  │  │   └─────────────────┘ │     │                       │            │ │
│  │  └───────────────────────┘     └───────────────────────┘            │ │
│  │                                                                     │ │
│  │  ┌───────────────────────────────────────────────────────────────┐  │ │
│  │  │                      DOMAIN SERVICES                          │  │ │
│  │  │  ProyectoDomainService, CreditoDomainService,                │  │ │
│  │  │  FechaDomainService, ValidacionDominioService                │  │ │
│  │  └───────────────────────────────────────────────────────────────┘  │ │
│  │                                                                     │ │
│  │  ┌───────────────────────────────────────────────────────────────┐  │ │
│  │  │                    REPOSITORY PORTS (Interfaces)              │  │ │
│  │  │  ProyectoRepository, CreditoRepository,                      │  │ │
│  │  │  BienInmuebleRepository, DocumentoRepository                 │  │ │
│  │  └───────────────────────────────────────────────────────────────┘  │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
│           │                                                             │
│           ▼                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐ │
│  │                    INFRASTRUCTURE LAYER (Adapters)                  │ │
│  │                                                                     │ │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │ │
│  │  │ JPA/DB2      │  │ Google Drive │  │  Auth        │              │ │
│  │  │ Adapter      │  │ Adapter      │  │  External    │              │ │
│  │  └──────────────┘  └──────────────┘  └──────────────┘              │ │
│  └─────────────────────────────────────────────────────────────────────┘ │
│           │                                                             │
└───────────┼─────────────────────────────────────────────────────────────┘
            │
            ▼
    ┌───────────────────┐        ┌───────────────────┐
    │     DB2/AS400     │        │   Google Drive    │
    │    (iSeries)      │        │   (Almacenamiento │
    │                   │        │    de PDFs)       │
    └───────────────────┘        └───────────────────┘
```

### 3.2 Estructura de Paquetes Java (Hexagonal + DDD)

```
com.ccf.gestionproyectos
│
├── [Application Layer - Casos de Uso]
├── application
│   ├── config/
│   ├── ports/
│   │   ├── inbound/            # Interfaces para Use Cases
│   │   │   ├── CrearProyectoPort.java
│   │   │   ├── ConsultarProyectoPort.java
│   │   │   ├── GestionarCreditoPort.java
│   │   │   └── GenerarXmlPort.java
│   │   └── outbound/           # Interfaces para infraestructura
│   │       ├── ProyectoPersistencePort.java
│   │       ├── DocumentoStoragePort.java
│   │       └── AuthServicePort.java
│   ├── usecase/
│   │   ├── proyecto/
│   │   │   ├── CrearProyectoUseCase.java
│   │   │   ├── ConsultarProyectosUseCase.java
│   │   │   ├── ActualizarProyectoUseCase.java
│   │   │   └── EliminarProyectoUseCase.java
│   │   ├── fovis/
│   │   │   ├── CrearProyectoFovisUseCase.java
│   │   │   └── ActualizarSeguimientoFovisUseCase.java
│   │   ├── credito/
│   │   │   ├── GestionarCreditoUseCase.java
│   │   │   └── ConsultarCarteraUseCase.java
│   │   ├── bieninmueble/
│   │   │   └── GestionarInmuebleUseCase.java
│   │   ├── documento/
│   │   │   └── CargarDocumentoUseCase.java
│   │   └── reporte/
│   │       └── GenerarReporteUseCase.java
│   └── dto/
│       ├── inbound/
│       └── outbound/
│
├── [Domain Layer - Núcleo DDD]
├── domain
│   ├── model/
│   │   ├── proyecto/           # Aggregate: Proyecto
│   │   │   ├── Proyecto.java              # Root Entity
│   │   │   ├── Cronograma.java            # Entity
│   │   │   ├── ValorTotal.java            # Value Object
│   │   │   └── CodigoProyecto.java        # Value Object
│   │   ├── fovis/
│   │   │   ├── ProyectoFOVIS.java         # Root Entity
│   │   │   ├── CoberturaProyectada.java   # Entity
│   │   │   └── Seguimiento.java           # Entity
│   │   ├── credito/
│   │   │   ├── Credito.java               # Root Entity
│   │   │   ├── CarteraPorEdades.java      # Entity
│   │   │   ├── TasaInteres.java           # Value Object
│   │   │   └── Plazo.java                 # Value Object
│   │   ├── bieninmueble/
│   │   │   ├── Arrendamiento.java         # Root Entity
│   │   │   ├── CompraInmueble.java        # Root Entity
│   │   │   ├── Comodato.java              # Root Entity
│   │   │   ├── Permuta.java               # Root Entity
│   │   │   └── Avaluo.java                # Value Object
│   │   └── documento/
│   │       ├── DocumentoProyecto.java     # Root Entity
│   │       └── TipoDocumento.java         # Value Object
│   │
│   ├── service/               # Domain Services
│   │   ├── ProyectoDomainService.java
│   │   ├── CreditoDomainService.java
│   │   ├── FechaDomainService.java
│   │   └── ValidacionDominioService.java
│   │
│   ├── repository/            # Repository Ports (Interfaces)
│   │   ├── ProyectoRepository.java
│   │   ├── ProyectoFOVISRepository.java
│   │   ├── CreditoRepository.java
│   │   ├── BienInmuebleRepository.java
│   │   └── DocumentoRepository.java
│   │
│   └── exception/
│       ├── DomainException.java
│       ├── ProyectoNotFoundException.java
│       └── ValidacionNegocioException.java
│
├── [Infrastructure Layer - Adaptadores]
├── infrastructure
│   ├── adapter/
│   │   ├── persistence/
│   │   │   └── jpa/
│   │   │       ├── proyecto/
│   │   │       │   ├── ProyectoJpaRepository.java
│   │   │       │   └── ProyectoJpaAdapter.java
│   │   │       ├── fovis/
│   │   │       ├── credito/
│   │   │       └── bieninmueble/
│   │   │
│   │   ├── storage/
│   │   │   └── googledrive/
│   │   │       └── GoogleDriveAdapter.java
│   │   │
│   │   └── auth/
│   │       └── ExternalAuthAdapter.java
│   │
│   └── config/
│       ├── Db2Config.java
│       ├── GoogleDriveConfig.java
│       └── SecurityConfig.java
│
├── [Adapters Layer - Controladores REST]
├── adapters
│   └── rest/
│       ├── ProyectoController.java
│       ├── FovisController.java
│       ├── CreditoController.java
│       ├── BienInmuebleController.java
│       ├── DocumentoController.java
│       └── ReporteController.java
│
└── GestionProyectosApplication.java
```

### 3.3 application.yml - Configuración

```yaml
spring:
  application:
    name: gestion-proyectos-ccf

server:
  port: 8080

# DB2/AS400 Configuration
spring:
  datasource:
    url: jdbc:as400://${DB2_HOST:localhost}:${DB2_PORT:446}/${DB2_BIBLIOTECA:CCFPROY}
    username: ${DB2_USER}
    password: ${DB2_PASSWORD}
    driver-class-name: com.ibm.as400.access.AS400JDBCDriver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5

  jpa:
    database-platform: org.hibernate.dialect.DB2AS400Dialect
    open-in-view: false
    properties:
      hibernate:
        format_sql: true
        default_schema: CCFPROY

# Google Drive Configuration
ccf:
  google-drive:
    credentials-path: ${GOOGLE_CREDENTIALS_PATH:/config/google-credentials.json}
    folder-id: ${GOOGLE_DRIVE_FOLDER_ID:}

# External Auth Service
ccf:
  auth:
    base-url: ${AUTH_SERVICE_URL:http://auth-server:8080}
    token-validation-path: /api/v1/auth/validar

# File Upload
spring:
  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 50MB

# Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

### 3.4 Security - Autenticación Externa

```java
// External Auth Adapter
@Component
@RequiredArgsConstructor
public class ExternalAuthAdapter implements AuthServicePort {
    
    private final RestTemplate restTemplate;
    private final String authServiceUrl;
    
    @Override
    public boolean validarToken(String token) {
        try {
            ResponseEntity<TokenValidationResponse> response = restTemplate.exchange(
                authServiceUrl + "/api/v1/auth/validar",
                HttpMethod.POST,
                new HttpEntity<>(token),
                TokenValidationResponse.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public UsuarioInfo obtenerUsuario(String token) {
        // Llamar al servicio de autenticación externo
    }
}

// Security Config
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthFilter(authAdapter), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

### 3.5 Build.gradle

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.0'
    id 'io.spring.dependency-management' version '1.1.6'
    id 'io.freefair.lombok' version '8.11'
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'

    // Spring Data JPA
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    // Spring Security
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // IBM AS400 JDBC Driver
    implementation 'com.ibm.as400:jt400:12.0.0'

    // Google Drive API
    implementation 'com.google.api-client:google-api-client:2.2.0'
    implementation 'com.google.apis:google-api-services-drive:v3-rev20240521-2.0.0'

    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // MapStruct
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'

    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.mockito:mockito-core'
}

test {
    useJUnitPlatform()
}
```

---
```

### 3.2.1 build.gradle - Configuración Gradle con Arquitectura Hexagonal

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.0'
    id 'io.spring.dependency-management' version '1.1.6'
    id 'io.freefair.lombok' version '8.11'
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    languageVersion = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Core
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'

    // Spring Data JPA
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    // Spring Security (LDAP)
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.security:spring-security-ldap'

    // IBM AS400 JDBC Driver
    implementation 'com.ibm.as400:jt400:12.0.0'

    // Hexagonal Architecture Support
    implementation 'org.springframework.context:org.springframework.context'

    // Domain Events
    implementation 'com.fasterxml.jackson.core:jackson-databind'

    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // MapStruct (Entity <-> Domain Mapper)
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'

    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.mockito:mockito-core'
    testImplementation 'org.assertj:assertj-core'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

test {
    useJUnitPlatform()
    filter {
        includeTestsMatching '*Test'
    }
}

sourceSets {
    main {
        java {
            srcDirs = [
                'src/main/java',
                'src/generated/java'
            ]
        }
    }
}

tasks.withType(JavaCompile).configureEach {
    options.compilerArgs.add('-parameters')
}

bootJar {
    archiveFileName = 'gestion-proyectos.jar'
}
```

### 3.2.2 application.yml - Configuración de Capas

```yaml
spring:
  application:
    name: gestion-proyectos-ccf

  datasource:
    url: jdbc:as400://${AS400_HOST}:${AS400_PORT}/${AS400_BIBLIOTECA}
    username: ${AS400_USER}
    password: ${AS400_PASSWORD}
    driver-class-name: com.ibm.as400.access.AS400JDBCDriver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5

  jpa:
    database-platform: org.hibernate.dialect.DB2AS400Dialect
    open-in-view: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          time-zone: America/Bogota
        default_schema: ${AS400_BIBLIOTECA}

  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 50MB

ccf:
  hexagonal:
    base-package: com.ccf.gestionproyectos
  storage:
    pdf-path: /data/pdfs
  validation:
    max-decimal-length: 5
```

### 3.2.3 Mapeo de Capas a Paquetes

```
┌─────────────────────────────────────────────────────────────┐
│                    package-info.java                        │
│                                                             │
│  @io.github.reservation.system.annotation.PackageInfo       │
│  Layer: DOMAIN                                              │
│  sealed: true                                               │
│                                                             │
│  - model/      → Entidades y VOs del dominio                │
│  - service/    → Lógica de negocio pura                     │
│  - repository/ → Interfaces (ports)                         │
│  - event/      → Eventos del dominio                        │
│  - exception/  → Excepciones del dominio                    │
└─────────────────────────────────────────────────────────────┘
```

### 3.2.4 Reglas de Dependencias (Layers)

```
┌──────────────────────────────────────────────────────────────┐
│                     DEPENDENCY RULES                          │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│   adapters (REST)  ──────►  application (UseCases)           │
│         │                          │                         │
│         │                          ▼                         │
│         │                   domain (Model)                   │
│         │                          ▲                         │
│         │                          │                         │
│         ▼                          │                         │
│   infrastructure               infrastructure                │
│   (JPA, DB2, Files)         (solo implementa ports)          │
│                                                              │
│   ■ domain NUNCA depende de application                      │
│   ■ domain NUNCA depende de infrastructure                   │
│   ■ application solo depende de domain y ports               │
│   ■ infrastructure implementa los ports del domain           │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

### 3.2.5 Patrones DDD Implementados

| Patrón DDD | Implementación |
|------------|----------------|
| **Aggregate** | `Proyecto`, `ProyectoFOVIS`, `Credito`, `BienInmueble` |
| **Value Object** | `CodigoProyecto`, `TasaInteres`, `ValorTotal`, `Fecha` |
| **Entity** | `Cronograma`, `Seguimiento`, `CarteraPorEdades` |
| **Domain Service** | `ProyectoDomainService`, `ValidacionDominioService` |
| **Repository (Port)** | `ProyectoRepository`, `DocumentoRepository` |
| **Factory** | `ProyectoFactory`, `CreditoFactory` |
| **Domain Event** | `ProyectoCreadoEvent`, `ProyectoAprobadoEvent` |
| **Application Service** | `CrearProyectoUseCase`, `ActualizarProyectoUseCase` |
| **Inbound Port** | `CrearProyectoPort` (interface) |
| **Outbound Port** | `ProyectoPersistencePort` (interface) |
| **Adapter** | `ProyectoJpaAdapter`, `PdfStorageAdapter` |

### 3.2.6 Estructura de Paquetes Java - ARQUITECTURA HEXAGONAL + DDD

```
com.ccf.gestionproyectos
│
├── [Application Layer - Casos de Uso]
├── application
│   ├── config/                 # Configuración deApplication
│   ├── ports/                  # Definición de Ports (interfaces)
│   │   ├── inbound/            # Puertos de entrada (Use Cases)
│   │   │   ├── proyecto/       # Puertos para gestión de proyectos
│   │   │   ├── fovis/          # Puertos para gestión FOVIS
│   │   │   ├── credito/        # Puertos para gestión de créditos
│   │   │   └── documento/      # Puertos para gestión de documentos
│   │   └── outbound/           # Puertos de salida (repositorios externos)
│   │       ├── persistence/    # Puertos para persistencia
│   │       ├── storage/        # Puertos para almacenamiento archivos
│   │       └── notification/   # Puertos para notificaciones
│   ├── usecase/                # Implementaciones de Use Cases
│   │   ├── proyecto/           # Crear, Actualizar, Consultar, etc.
│   │   ├── fovis/              # Casos de uso FOVIS
│   │   ├── credito/            # Casos de uso Créditos
│   │   ├── bieninmueble/       # Casos de uso Bienes Inmuebles
│   │   └── report/
│   └── dto/                    # DTOs de aplicación
│       ├── inbound/            # DTOs de entrada (requests)
│       └── outbound/           # DTOs de salida (responses)
│
├── [Domain Layer - Núcleo DDD]
├── domain
│   ├── model/                  # Modelo de Dominio
│   │   ├── proyecto/           # Aggregate: Proyecto
│   │   │   ├── Proyecto.java              # Root Entity
│   │   │   ├── Cronograma.java            # Entity
│   │   │   ├── Objetivo.java              # Value Object
│   │   │   ├── ValorTotal.java            # Value Object
│   │   │   ├── CodigoProyecto.java        # Value Object
│   │   │   └── estado/                    # Estados del proyecto
│   │   ├── fovis/               # Aggregate: ProyectoFOVIS
│   │   │   ├── ProyectoFOVIS.java         # Root Entity
│   │   │   ├── CoberturaProyectada.java   # Entity
│   │   │   ├── Seguimiento.java           # Entity
│   │   │   ├── ModalidadFOVIS.java        # Value Object
│   │   │   └── Conclusiones.java          # Value Object
│   │   ├── credito/             # Aggregate: Credito
│   │   │   ├── Credito.java               # Root Entity
│   │   │   ├── CarteraPorEdades.java      # Entity
│   │   │   ├── TasaInteres.java           # Value Object
│   │   │   ├── Plazo.java                 # Value Object
│   │   │   ├── MontoCredito.java          # Value Object
│   │   │   └── Categoria.java             # Value Object
│   │   ├── bieninmueble/        # Aggregate: BienInmueble
│   │   │   ├── Arrendamiento.java         # Root Entity
│   │   │   ├── Comodato.java              # Root Entity
│   │   │   ├── CompraInmueble.java        # Root Entity
│   │   │   ├── Permuta.java               # Root Entity
│   │   │   ├── Avaluo.java                # Value Object
│   │   │   ├── Destinacion.java           # Value Object
│   │   │   └── UsoSuelo.java              # Value Object
│   │   ├── acciones/            # Aggregate: NegociacionAcciones
│   │   │   ├── NegociacionAcciones.java   # Root Entity
│   │   │   ├── ValorNominal.java          # Value Object
│   │   │   └── PorcentajeParticipacion.java # Value Object
│   │   └── documento/           # Aggregate: Documento
│   │       ├── DocumentoProyecto.java     # Root Entity
│   │       ├── TipoDocumento.java         # Value Object
│   │       └── MetadatosDocumento.java    # Value Object
│   │
│   ├── service/                 # Domain Services
│   │   ├── ProyectoDomainService.java     # Lógica de dominio proyectos
│   │   ├── CreditoDomainService.java      # Lógica de dominio créditos
│   │   ├── CarteraDomainService.java      # Lógica de dominio cartera
│   │   ├── ValidacionDominioService.java  # Validaciones de negocio
│   │   └── FechaDomainService.java        # Manejo de fechas AAAAMMDD
│   │
│   ├── repository/              # Interfaces de Repositorio (Ports)
│   │   ├── ProyectoRepository.java        # Port para persistencia proyectos
│   │   ├── ProyectoFOVISRepository.java   # Port para persistencia FOVIS
│   │   ├── CreditoRepository.java         # Port para persistencia créditos
│   │   ├── BienInmuebleRepository.java    # Port para persistencia inmuebles
│   │   └── DocumentoRepository.java       # Port para persistencia documentos
│   │
│   ├── event/                   # Domain Events
│   │   ├── ProyectoCreadoEvent.java
│   │   ├── ProyectoAprobadoEvent.java
│   │   ├── SeguimientoActualizadoEvent.java
│   │   └── DomainEventPublisher.java
│   │
│   └── exception/               # Excepciones de Dominio
│       ├── DomainException.java
│       ├── ProyectoNotFoundException.java
│       ├── CodigoProyectoInvalidoException.java
│       ├── ValidacionNegocioException.java
│       └── FechaInvalidaException.java
│
├── [Infrastructure Layer - Adaptadores]
├── infrastructure
│   ├── adapter/
│   │   ├── persistence/         # Adaptadores de Persistencia (JPA/DB2)
│   │   │   ├── jpa/
│   │   │   │   ├── proyecto/
│   │   │   │   │   ├── ProyectoJpaRepository.java
│   │   │   │   │   ├── ProyectoJpaAdapter.java
│   │   │   │   │   └── mapper/
│   │   │   │   │       └── ProyectoMapper.java
│   │   │   │   ├── fovis/
│   │   │   │   └── credito/
│   │   │   └── db2/             # Implementación DB2/AS400 específica
│   │   │       └── DB2Projectsupport.java
│   │   │
│   │   ├── storage/             # Adaptadores de Almacenamiento
│   │   │   └── filestore/
│   │   │       ├── PdfStorageAdapter.java
│   │   │       └── FileSystemAdapter.java
│   │   │
│   │   └── security/            # Adaptadores de Seguridad
│   │       └── ldap/
│   │           └── LdapAuthenticationAdapter.java
│   │
│   ├── configuration/
│   │   ├── db2/                 # Configuración DB2/AS400
│   │   │   ├── DataSourceConfig.java
│   │   │   └── HibernateConfig.java
│   │   ├── hexagonal/           # Configuración de arquitectura
│   │   │   └── DependencyInjectionConfig.java
│   │   └── security/
│   │       └── SecurityConfig.java
│   │
│   └── mapper/                  # Mappers de infraestructura
│       └── ModeloJpaMapper.java
│
├── [Adapters Layer - Controladores]
├── adapters
│   ├── rest/                    # REST API Controllers
│   │   ├── proyecto/
│   │   │   ├── ProyectoController.java
│   │   │   └── dto/
│   │   │       ├── CrearProyectoRequest.java
│   │   │       └── ProyectoResponse.java
│   │   ├── fovis/
│   │   │   ├── ProyectoFOVISController.java
│   │   │   └── dto/
│   │   ├── credito/
│   │   ├── bieninmueble/
│   │   └── documento/
│   │       └── DocumentoController.java
│   │
│   └── scheduler/               # Tareas programadas
│       └── ReporteScheduler.java
│
└── shared/
    ├── constants/               # Constantes compartidas
    │   ├── TablasReferencia.java
    │   └── EstadosProyecto.java
    ├── mapper/                  # Mappers compartidos
    └── util/                    # Utilidades
        └── FechaUtil.java
```

### 3.3 Convenciones DDD - Nombres de Clases

| Tipo DDD | Sufijo/Patrón | Ejemplo |
|----------|---------------|---------|
| Root Entity | Nombre del Aggregate | `Proyecto`, `ProyectoFOVIS` |
| Entity | Nombre del objeto | `Cronograma`, `Seguimiento` |
| Value Object | Nombre descriptivo | `CodigoProyecto`, `TasaInteres` |
| Domain Service | `DomainService` | `ProyectoDomainService` |
| Repository Interface | `Repository` | `ProyectoRepository` |
| Use Case | `UseCase` | `CrearProyectoUseCase` |
| Port (Inbound) | Interface + UseCase | `CrearProyectoPort` |
| Port (Outbound) | Interface + Repository | `ProyectoPersistencePort` |
| Adapter | `Adapter` | `ProyectoJpaAdapter` |

### 3.3 Estructura Angular con Atomic Design CSS

```
src/app/
├── core/                         # Core (auth, guards, interceptors)
├── shared/                       # Shared (components, pipes, directives)
│   ├── components/               # Componentes compartidos
│   │   ├── atoms/                # Atomic: botones, inputs, labels, iconos
│   │   │   ├── button/
│   │   │   ├── input-text/
│   │   │   ├── input-number/
│   │   │   ├── input-date/
│   │   │   ├── select/
│   │   │   ├── checkbox/
│   │   │   ├── radio/
│   │   │   ├── label/
│   │   │   ├── badge/
│   │   │   ├── spinner/
│   │   │   └── toast/
│   │   ├── molecules/            # Atomic: grupos de átomos
│   │   │   ├── form-field/       # label + input + error
│   │   │   ├── search-box/       # input + button + icon
│   │   │   ├── card-header/      # título + badge + actions
│   │   │   ├── table-row/        # celdas + acciones
│   │   │   ├── alert-message/    # icono + texto + close
│   │   │   └── pagination/       # prev + pages + next
│   │   └── organisms/            # Atomic: componentes complejos
│   │       ├── data-table/       # tabla completa con paginación
│   │       ├── form-section/     # sección de formulario
│   │       ├── modal-dialog/     # modal reutilizable
│   │       ├── file-upload/      # dropzone + progress
│   │       ├── project-card/     # tarjeta de proyecto
│   │       ├── documento-list/   # lista de documentos
│   │       └── xml-generator/    # generador de XML
│   │
│   ├── styles/                   # ESTILOS ATOMIC DESIGN
│   │   ├── atoms/
│   │   │   ├── _buttons.scss
│   │   │   ├── _inputs.scss
│   │   │   ├── _labels.scss
│   │   │   ├── _badges.scss
│   │   │   └── _utilities.scss
│   │   ├── molecules/
│   │   │   ├── _form-fields.scss
│   │   │   ├── _cards.scss
│   │   │   ├── _tables.scss
│   │   │   └── _alerts.scss
│   │   ├── organisms/
│   │   │   ├── _data-table.scss
│   │   │   ├── _modals.scss
│   │   │   └── _forms.scss
│   │   ├── abstracts/
│   │   │   ├── _variables.scss   # colores, breakpoints, spacing
│   │   │   ├── _mixins.scss      # mixins reutilizables
│   │   │   ├── _animations.scss  # transiciones, keyframes
│   │   │   └── _typography.scss  # escalas tipográficas
│   │   └── themes/
│   │       ├── _light.scss       # tema claro
│   │       └── _dark.scss        # tema oscuro (futuro)
│   │
│   └── pipes/                    # Pipes compartidos
│       ├── date-format/
│       ├── currency-format/
│       └── xml-validate/
│
├── features/
│   ├── proyectos/                # Módulo proyectos generales
│   │   ├── components/
│   │   │   ├── lista/            # Lista de proyectos
│   │   │   ├── crear/            # Formulario crear
│   │   │   ├── editar/           # Formulario editar
│   │   │   └── detalle/          # Detalle del proyecto
│   │   ├── pages/                # Páginas del módulo
│   │   └── styles/               # Estilos específicos del módulo
│   ├── fovis/                    # Módulo FOVIS
│   │   ├── components/
│   │   ├── pages/
│   │   └── styles/
│   ├── reportes/                 # Módulo reportes
│   └── admin/                    # Módulo administración
│
├── models/                       # Modelos TypeScript
├── services/                     # Servicios Angular
└── layouts/                      # Layouts principales
    ├── main-layout/              # Layout con sidebar
    └── auth-layout/              # Layout para autenticación
```

### 3.3.1 Atomic Design CSS - Reglas de Implementación

| Nivel Atomic | Descripción | Ejemplos |
|--------------|-------------|----------|
| **Atoms (Átomos)** | Elementos HTML mínimos, no tienen hijos | `.btn`, `.input`, `.label`, `.badge` |
| **Molecules (Moléculas)** | Grupo de átomos funcionando juntos | `.form-field`, `.search-box`, `.card` |
| **Organisms (Organismos)** | Componentes complejos con lógica | `.data-table`, `.modal`, `.file-upload` |
| **Templates (Plantillas)** | Estructuras de página布局 | `.form-layout`, `.list-layout` |
| **Pages (Páginas)** | Instancias concretas con datos reales | `ProyectoListPage`, `ProyectoDetailPage` |

### 3.3.2 Ejemplo de Clases CSS Atómicas

```scss
// atoms/_buttons.scss
.atomic-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-sm) var(--spacing-md);
  border: none;
  border-radius: var(--radius-md);
  font-family: var(--font-family);
  font-size: var(--font-size-sm);
  cursor: pointer;
  transition: background-color 0.2s ease;

  &--primary { background-color: var(--color-primary); color: white; }
  &--secondary { background-color: var(--color-secondary); color: white; }
  &--outline { background: transparent; border: 1px solid var(--color-primary); }
  &--sm { padding: var(--spacing-xs) var(--spacing-sm); font-size: var(--font-size-xs); }
  &--lg { padding: var(--spacing-md) var(--spacing-lg); font-size: var(--font-size-md); }
  &:disabled { opacity: 0.5; cursor: not-allowed; }
}

// molecules/_form-fields.scss
.atomic-form-field {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xs);

  &__label {
    @extend .atomic-label;
    font-weight: var(--font-weight-medium);
  }

  &__input {
    @extend .atomic-input;
    border: 1px solid var(--color-border);
    &:focus { border-color: var(--color-primary); outline: none; }
  }

  &__error {
    @extend .atomic-label--error;
    font-size: var(--font-size-xs);
  }
}

// organisms/_data-table.scss
.atomic-data-table {
  width: 100%;
  border-collapse: collapse;

  &__header { background-color: var(--color-bg-secondary); }
  &__cell { padding: var(--spacing-sm); border-bottom: 1px solid var(--color-border); }
  &__row:hover { background-color: var(--color-bg-hover); }
}
```

### 3.3.3 Variables CSS Globales

```scss
// abstracts/_variables.scss
:root {
  // Colores principales
  --color-primary: #1E40AF;
  --color-primary-light: #3B82F6;
  --color-secondary: #059669;
  --color-error: #DC2626;
  --color-warning: #F59E0B;
  --color-success: #10B981;

  // Escala de grises
  --color-gray-50: #F9FAFB;
  --color-gray-100: #F3F4F6;
  --color-gray-200: #E5E7EB;
  --color-gray-300: #D1D5DB;
  --color-gray-400: #9CA3AF;
  --color-gray-500: #6B7280;
  --color-gray-600: #4B5563;
  --color-gray-700: #374151;
  --color-gray-800: #1F2937;
  --color-gray-900: #111827;

  // Spacing
  --spacing-xs: 0.25rem;
  --spacing-sm: 0.5rem;
  --spacing-md: 1rem;
  --spacing-lg: 1.5rem;
  --spacing-xl: 2rem;
  --spacing-2xl: 3rem;

  // Border radius
  --radius-sm: 0.25rem;
  --radius-md: 0.375rem;
  --radius-lg: 0.5rem;
  --radius-xl: 0.75rem;

  // Breakpoints
  --breakpoint-sm: 640px;
  --breakpoint-md: 768px;
  --breakpoint-lg: 1024px;
  --breakpoint-xl: 1280px;

  // Sombras
  --shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.05);
  --shadow-md: 0 4px 6px -1px rgb(0 0 0 / 0.1);
  --shadow-lg: 0 10px 15px -3px rgb(0 0 0 / 0.1);
}
```

### 3.3.4 Componentes Angular con Estilos Atómicos

```typescript
// Ejemplo: Button component usando atomic classes
@Component({
  selector: 'app-button',
  standalone: true,
  template: `
    <button
      [class]="'atomic-btn atomic-btn--' + variant + ' atomic-btn--' + size"
      [type]="type"
      [disabled]="disabled">
      <ng-content></ng-content>
    </button>
  `,
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent {
  @Input() variant: 'primary' | 'secondary' | 'outline' | 'ghost' = 'primary';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() type: 'button' | 'submit' = 'button';
  @Input() disabled = false;
}

// Ejemplo: Form field component
@Component({
  selector: 'app-form-field',
  standalone: true,
  template: `
    <div class="atomic-form-field">
      <label class="atomic-form-field__label">{{ label }}</label>
      <input
        class="atomic-form-field__input"
        [type]="type"
        [placeholder]="placeholder"
        [formControl]="control" />
      @if (control.hasError('required') && control.touched) {
        <span class="atomic-form-field__error">Este campo es requerido</span>
      }
    </div>
  `,
  styleUrls: ['./form-field.component.scss']
})
export class FormFieldComponent {
  @Input() label = '';
  @Input() type = 'text';
  @Input() placeholder = '';
  @ControlContainer(FormControl) control!: FormControl;
}
```

### 3.3.5 No Inline CSS - Regla Obligatoria

```
⚠️  PROHIBIDO USAR inline styles (style="...") en componentes Angular

✔️  USAR clases atómicas: class="atomic-btn atomic-btn--primary"
✔️  USAR @if, @for en templates
✔️  USAR styleUrls para estilos específicos del componente
✔️  CREAR componentes atomic para elementos reutilizables
```

---

## 4. ESTRUCTURAS DE DATOS - PROYECTOS GENERALES (P-001A a P-056A)

### 4.0 Instrucciones Generales - Proyectos de Inversión

**Formatos de fecha:** Todos los campos de fecha deben digitarse en formato AAAAMMDD (8 dígitos). Ejemplo: 15 de marzo de 2025 → 20250315

**Valores numéricos:** Los campos numéricos deben digitarse sin decimales, sin separadores de miles, sin puntos ni comas. Ejemplo: un millón quinientos mil pesos → 1500000

**Campos condicionales:** Algunos campos son conditionally obligatorios según el valor de otros campos. La columna "Reglas de Validación" indica estas dependencias.

**Códigos de referencia:** Los campos que requieren códigos (modalidad, categoría, etc.) deben consultar las Tablas de Referencia (Sección 10). Usar el código numérico, no la descripción.

**Longitudes máximas:** No exceder la longitud indicada. Los campos de texto (string) truncarán automáticamente si se excede la longitud.

### 4.1 P-001A - ESTRUCTURAS GENERALES PROYECTOS DE INVERSIÓN

**Instrucciones de llenado:** Este formato se diligencia una única vez por proyecto, al momento de la creación. Todos los campos son obligatorios a menos que se indique lo contrario.

| Campo | Descripción | Qué debe digitar el usuario | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|----------------------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código único del proyecto | Código compuesto: Código Caja (2 dígitos) + '-' + Modalidad (2 dígitos según Tabla 32) + '-' + Consecutivo (5 dígitos). Ejemplo: 001-01-00001 | Alfanumérico | 15 | Sí | Formato: CCFXX-XX-XXXXX. Debe ser único en el sistema. No usar caracteres especiales (#, -, /). Primero crear el proyecto y luego usar este código en los demás formularios. |
| MOD_PROYECTO | Modalidad del proyecto | Código numérico de la modalidad según Tabla 32. Ejemplos: 1=Construcción, 2=Adquisición, 3=Mejoramiento | Numérico | 1 | Sí | Valores válidos según Tabla 32 de Supersubsidio. Si el código no existe, consultar tabla de referencia. |
| MOD_DE_INVERSION | Modalidad de inversión | Código numérico según clasificación interna. Consultar Tabla de Modalidades de Inversión | Numérico | 1 | Sí | Debe ser un valor permitido según políticas de la caja. |
| VAL_TOTAL_PROYECTO | Valor total del proyecto | Valor en pesos COP, sin decimales ni separadores de miles. Ejemplo: 1500000000 para mil quinientos millones | Numérico | 18 | Sí | Solo números enteros. No usar puntos, comas ni espacios. Valor debe ser mayor a 0. Debe coincidir con la sumatoria de todas las asignaciones presupuestales. |
| VALOR_APR_VIGENCIA | Valor aprobado para la vigencia | Valor aprobado en el presupuesto del año. Mismo formato que VAL_TOTAL_PROYECTO | Numérico | 18 | Sí | No puede exceder VAL_TOTAL_PROYECTO. Debe ser mayor a 0. |
| DESCRIPCION_PROYECTO | Descripción detallada del proyecto | Texto narrativo que explique qué se va a hacer, dónde, cómo y para qué. Mínimo 100 caracteres | Texto | 4000 | Sí | Descripción clara y completa. Incluir: ubicación geográfica, actividades principales, beneficiarios esperados. |
| OBJETIVO_PROYECTO | Objetivo general y específicos | Minimum 3 objetivos específicos siguiendo metodología SMART: Specific, Measurable, Achievable, Relevant, Time-bound | Texto | 4000 | Sí | Cada objetivo en párrafo separado. Incluir meta cuantificable y plazo de cumplimiento. |
| JUSTIFICACION | Justificación del proyecto | Identificación clara del problema o necesidad que justifica la inversión. Incluir datos de respaldo | Texto | 4000 | Sí | Justificación técnica, social y financiera. Soportar con datos (estadísticas, estudios previos). |
| RESOLUCION_AEI | ¿Cuenta con resolución AEI? | 1=Sí, tiene resolución AEI; 2=No, no requiere AEI | Numérico | 1 | Sí | Si es 1, los campos NUM_ACTA_AEI, FECHA_APR_AEI y NUM_CONSEJEROS deben estar diligenciados. Si es 2, NUM_CONSEJEROS debe ser 1. |
| NUM_ACTA_AEI | Número de acta o resolución | Número entero del documento de aprobación. Si no aplica, escribir 0 | Numérico | 10 | Sí | Si RESOLUCION_AEI=1, debe ser mayor a 0. |
| FECHA_APR_AEI | Fecha de aprobación | Fecha en formato AAAAMMDD. Ejemplo: 20250315 para 15 de marzo de 2025 | Texto | 8 | Sí | Debe ser fecha válida. Si RESOLUCION_AEI=2, escribir 00000000. |
| NUM_CONSEJEROS | Número de consejeros | Cantidad de consejeros que participaron en la aprobación. Mínimo 7 para aprobación ordinaria, 1 si es resolución AEI | Numérico | 2 | Sí | Si RESOLUCION_AEI=1, valor >=7. Si RESOLUCION_AEI=2, valor =1. |
| TMP_RECUPERACION | Tiempo de recuperación | Número de meses estimado para recuperar la inversión. Valor entre 1 y 999 | Numérico | 3 | No | Si no aplica (proyecto social sin recuperación), escribir 0. |
| ESTUDIO_MERCADO | Conclusiones estudio de mercado | Resumen de max 4000 caracteres con los hallazgos del estudio de mercado | Texto | 4000 | Sí | Incluir: demanda identificada, competencia, precios de mercado, viabilidad comercial. |
| EVALUACION_SOCIAL | Conclusiones evaluación social | Análisis de impacto social del proyecto | Texto | 4000 | Sí | Incluir: beneficio para afiliados, impacto en calidad de vida, relevancia social. |
| EVALUACION_FINANCIERA | Conclusiones evaluación financiera | Análisis de viabilidad financiera: flujo de caja, VPN, TIR, punto de equilibrio | Texto | 4000 | Sí | Incluir: ingresos esperados, egresos, margen neto, período de recuperación. |
| NUM_PERSONAS_REFERENCIA | Población de referencia | Número de personas que tienen la necesidad/requisitos para el proyecto | Numérico | 10 | Sí | Valor mayor a 0. Fuente: estudio de mercado o diagnóstico previo. |
| NUM_POBLACION_AFECTADA | Población efectivamente afectada | Número de personas que se beneficiarán directamente | Numérico | 10 | Sí | Valor mayor a 0. No puede exceder NUM_PERSONAS_REFERENCIA. |

### 4.2 P-002A - CRONOGRAMA INICIAL DEL PROYECTO

**Instrucciones de llenado:** El cronograma inicial del proyecto es la estructura que muestra ordenadamente las tareas e hitos que forman el proyecto, el inicio y fin de cada una de sus actividades y la proyección mensual de ejecución presupuestal y física de las mismas. Se requiere la fecha de inicio de las actividades (Tabla 115).

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código FK a P-001A | Alfanumérico | 15 | Sí | Debe existir en P-001A. Formato: CCFXXX-XX-XXXXX |
| TIPO_ACTIVIDAD | Tipo de actividad según Tabla 115 | Alfanumérico | 5 | Sí | Códigos: 05-01 a 05-09 (Fases), 06-01 a 06-04, 07-01 a 07-02, 04-01 a 04-18, 09-01 a 09-03, 10-01 a 10-02 |
| ANO_PROYECCION | Año de proyección (vigencia) | Numérico | 4 | Sí | Se reportan tantos años como tiempo total de ejecución tenga la actividad |
| MES_PROYECCION | Mes de proyección (Tabla 68) | Numérico | 2 | Sí | Valores 1-12. Se reportan tantos meses como tiempo total de ejecución |
| VALOR_PROGRAMACION_PAGOS | Monto proyectado por tipo de actividad que se espera pagar cada mes hasta concluirla. Si no hay pagos, $0 | Numérico | 18 | Sí | Sin decimales ni separadores. Valor >= 0 |
| VALOR_PLANEADO | Monto proyectado por tipo de actividad que se espera ejecutar físicamente cada mes. Solo para proyectos de infraestructura | Numérico | 18 | No | Sin decimales ni separadores. Si no aplica, escribir 0 |
| CANTIDAD_PROGRAMACION_ACTIVIDAD | Cantidad según modalidad: créditos (modalidad fondo crédito), acciones/cuotas (negociación o capitalizaciones) | Numérico | 10 | No | Si no hay cantidad, escribir 0. Depende del tipo de actividad |
| FECHA_INICIO_ACTIVIDAD | Fecha inicio de la actividad (debe coincidir con mes de proyección). Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. AAAA: Año, MM: Mes, DD: Día |
| FECHA_TERMINACION_ACTIVIDAD | Fecha terminación de la actividad (debe coincidir con mes de proyección). Formato AAAAMMDD | Texto | 8 | Sí | Debe ser mayor que FECHA_INICIO_ACTIVIDAD |

### 4.3 P-003A - LOCALIZACIÓN DEL PROYECTO

**Instrucciones de llenado:** Ubicación geográfica de cada uno de los sitios donde se va a ejecutar el proyecto.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| COD_INFRAESTRUCTURA | Código asignado por la CCF a la infraestructura. Formato: Código Caja + '-' + Tipo Infraestructura (Tabla 10) + '-' + Número consecutivo | Alfanumérico | 30 | Sí | Si el servicio se presta en infraestructura ocasional, la CCF debe crear un código. Este código debe reportarse en estructura 2-004 INFRAESTRUCTURA |

### 4.4 P-004C - ESTRUCTURA FUENTE DE RECURSOS DEL PROYECTO - CENTRO DE COSTOS

**Instrucciones de llenado:** Se debe reportar un registro por cada fuente de recursos. Los centros de costos deben coincidir con el reporte efectuado en la estructura "Relación de proyectos de inversión que conforman el límite máximo".

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| FUENTE | Código de la fuente de inversión y/o recurso (Tabla 23) | Numérico | 2 | Sí | Códigos: 1=FOVIS, 2=FOSYGA, 3=Promoción y prevención, 4=Fondos educación Ley 115, 5=FOSFEC, 6=FONIÑEZ, 7=Saldo Obras, 8=Excedentes 55%, 9=Remanentes, 10=Crédito, etc. |
| VALOR_APROBADO_FUENTE | Monto aprobado del proyecto por fuente de recursos. Sin decimales ni separadores | Numérico | 18 | Sí | Validar que la sumatoria de todos los registros sea igual al VAL_TOTAL_PROYECTO de P-001A |
| CENTRO_COSTOS | Código centro de costo al cual se aplica el monto (Tabla 20) | Numérico | 2 | Sí | Códigos: 1=Administración, 2=Mercadeo, 3=Salud EPS-S, 4=Salud IPS, 5=Salud EPS-C, 6=Medicina Prepagada, 7=Salud y nutrición, 8=Educación Formal, 9=Educación para el trabajo, 10=Biblioteca, 11=Cultura, 12=Vivienda, 13=Recreación, 14=Crédito Social, 15=Fomento, 16=Convenios, 17=Fondos de Ley |

### 4.5 P-005A - FICHA TÉCNICA (PDF)

**Documentos requeridos en el PDF:**
- Solicitud del Representante Legal
- Soporte Tiempo de Recuperación (si aplica según P-001A)
- Soporte Estudio de Mercado (si aplica según P-001A)
- Soporte Evaluación Social (si aplica según P-001A)
- Soporte Evaluación Financiera
- Justificación del Proyecto
- Acta de Aprobación - Consejo Directivo
- Certificado del Revisor Fiscal

**Fuentes de recursos válidas para financiar proyectos:**
- Recursos de ley (FOVIS – unidad de caja o plan anual-, FONIÑEZ, FOSFEC, Ley 115 de 1994)
- Saldo para obras y programas de beneficio social
- Excedentes del 55%
- Recursos de remanentes de ejercicios anteriores
- Recursos del crédito
- Recursos originados en fuentes diferentes a los aportes parafiscales
- Donaciones
- Aportes de capital a terceros
- Recursos provenientes de convenios de cooperación
- Otras fuentes establecidas en la norma vigente

### 4.6 P-011A - COBERTURA PROYECTADA

**Instrucciones de llenado:** Este archivo reporta la proyección de la población, por categorías, que se beneficiará del proyecto, cuantificando la cobertura actual y proyectada por categoría. Se debe proyectar la cobertura para los primeros cinco (5) años de operación del proyecto.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| CENTRO_COSTOS | Código centro de costo (Tabla 20) | Numérico | 2 | Sí | Debe existir en P-004C para este proyecto |
| ANO | Año de proyección (Tabla 207) | Numérico | 2 | Sí | Códigos: 0, 1, 2, 3, 4, 5 (año 0 = sin ejecución del proyecto) |
| CATEGORIA | Código categoría del afiliado (Tabla 8, solo códigos 1-4) | Numérico | 2 | Sí | 1=A, 2=B, 3=C, 4=D. Solo estos códigos apply |
| NUM_PERSONAS_BENEFICIARIAS_ACTUAL | Número de personas que efectivamente utilizan el servicio (último reporte). Sin separador de miles | Numérico | 10 | Sí | Valor >= 0 |
| NUM_PERSONAS_PROYECTADAS_BENEFICIARIAS | Número de personas proyectadas que utilizarán el servicio durante el año. Sin separador de miles. Si no implica ampliación, escribir 0 | Numérico | 10 | Sí | Valor >= 0 |

### 4.7 P-011B - ESTRUCTURA COBERTURA EJECUTADA

**Instrucciones de llenado:** Este archivo se debe reportar ANUALMENTE la cobertura por categorías, una vez culminado el proyecto de inversión, y por un tiempo de cinco (5) años, y se haya iniciado la prestación del servicio.

> **⚠️ IMPORTANTE:** Si la estructura no aplica, no se requiere realizar el reporte en SIMON. Digitar "no aplica" cuando no corresponda.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| CENTRO_COSTOS | Código centro de costo (Tabla 20) | Numérico | 2 | Sí | - |
| ANO | Año de proyección (Tabla 207) | Numérico | 2 | Sí | Códigos: 0, 1, 2, 3, 4, 5 |
| CATEGORIA | Código categoría del afiliado (Tabla 8, solo códigos 1-4) | Numérico | 2 | Sí | 1=A, 2=B, 3=C, 4=D |
| NUM_PERSONAS_BENEFICIARIAS_ATENDIDAS | Número de personas que efectivamente utilizan el servicio. Sin separador de miles | Numérico | 10 | Sí | Valor >= 0 |

### 4.8 P-012A - SEGUIMIENTO DEL PROYECTO

**Instrucciones de llenado:** En esta estructura se debe reportar el avance de ejecución trimestral de los proyectos cuya ejecución sea superior a 12 meses (ajustando el primer reporte a los trimestres anuales: marzo-junio-septiembre-diciembre) y realizar el reporte durante el mes siguiente, vencido el trimestre. Para proyectos cuya ejecución sea inferior a 12 meses, los reportes deben realizarse mensualmente.

> **Nota:** Si durante el periodo de reporte no se ejecutaron actividades o pagos, se debe reportar en cero (0).

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX |
| ANO_EJECUCION | Vigencia (Año) en el que se ejecuta la actividad | Numérico | 4 | Sí | Año de ejecución |
| MES_EJECUCION | Mes de ejecución (Tabla 68) o trimestre (3, 6, 9, 12) para proyectos >12 meses | Numérico | 2 | Sí | Mensual para <12 meses, trimestral para >12 meses |
| TIPO_ACTIVIDAD | Tipo de actividad necesaria para la ejecución (Tabla 115) | Numérico | 5 | Sí | - |
| COSTO_ACTUAL | Costo real generado por el trabajo ejecutado físicamente al momento del reporte. Si no hay costo, $0. No es el valor total del proyecto | Numérico | 18 | Sí | Sin decimales ni separadores |
| VALOR_PAGADO | Monto real pagado cada mes o trimestre por tipo de actividad. Solo para proyectos de infraestructura, sino incluir 0 | Numérico | 18 | Sí | Sin decimales ni separadores |
| VALOR_GANADO | Trabajo ejecutado a la fecha expresado en términos del presupuesto autorizado. Solo para proyectos de infraestructura, sino incluir 0 | Numérico | 18 | Sí | Sin decimales ni separadores |
| CANTIDAD_EJECUCION_FISICA_ACTIVIDAD | Cantidad según modalidad: créditos desembolsados, acciones/cuotas negociadas, etc. Si no hay cantidad, $0 | Numérico | 10 | Sí | Depende del tipo de actividad |
| FECHA_INICIO | Fecha de inicio de ejecución. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida |
| FECHA_TERMINACION | Fecha de terminación del tipo de actividad. Formato AAAAMMDD | Texto | 8 | Sí | Debe ser mayor que FECHA_INICIO |

### 4.9 P-013A - ASPECTOS ESPECÍFICOS PROYECTOS DE INFRAESTRUCTURA

**Instrucciones de llenado:** Este archivo reporta la información sobre los aspectos específicos de los proyectos de la modalidad de infraestructura que serán ejecutados por la Caja de Compensación Familiar.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX |
| INTERVENTORIA_SUPERVISION | Código que indica si la Interventoría/Supervisión se realiza de manera directa o contratada (Tabla 203) | Numérico | 1 | Sí | 1=Interna, 2=Externa |
| VALOR_TOTAL_INTERVENTORIA_SUPERVISION | Valor de Interventoría/Supervisión. Sin decimales ni separadores | Numérico | 18 | Sí | Valor >= 0 |
| LICENCIA_CONSTRUCCION_URBANISMO | Código que indica la necesidad de tramitar o no licencia de construcción y su estado (Tabla 204) | Numérico | 1 | Sí | 1=N.A., 2=En trámite, 3=Expedida |
| ENT_COMPETENTE | Entidad competente para expedir la licencia (Curaduría, Secretaría de Planeación, Alcaldía, otro). Solo si LICENCIA = 2 o 3 | Texto | 250 | Condicional | Obligatorio si LICENCIA = 2 o 3 |
| NUM_RADICADO_SOLICITUD_LICENCIA | Consecutivo que asigna la entidad competente al radicar la solicitud. Solo si LICENCIA = 2 | Alfanumérico | 30 | Condicional | Obligatorio si LICENCIA = 2 |
| FECHA_RADICACION_SOLICITUD_LICENCIA | Fecha de radicación de la solicitud. Formato AAAAMMDD. Solo si LICENCIA = 2 | Texto | 8 | Condicional | Obligatorio si LICENCIA = 2 |
| NUMERO_LICENCIA | Consecutivo que asigna la entidad competente al expedir la licencia. Solo si LICENCIA = 3. Si aplica más de una, separar por comas | Alfanumérico | 250 | Condicional | Obligatorio si LICENCIA = 3 |
| FECHA_LICENCIA | Fecha de expedición de la licencia. Formato AAAAMMDD. Solo si LICENCIA = 3. Si aplica más de una, separar por comas | Alfanumérico | 250 | Condicional | Obligatorio si LICENCIA = 3 |
| VIGENCIA_LICENCIA | Vigencia de la licencia expedida en años. Solo si LICENCIA = 3. Si aplica más de una, separar por comas | Alfanumérico | 50 | Condicional | Obligatorio si LICENCIA = 3 |
| SERVICIOS_PUBLICOS | Código que indica la necesidad de tramitar o no disponibilidad de servicios públicos y su estado (Tabla 204) | Numérico | 1 | Sí | 1=N.A., 2=En trámite, 3=Expedida |
| FECHA_RADICACION_AAA | Fecha radicación solicitud acueducto/alcantarillado. Formato AAAAMMDD. Solo si SERVICIOS = 2 | Texto | 8 | Condicional | Obligatorio si SERVICIOS = 2 |
| NUM_RADICADO_SOLICITUD_AAA | Número radicado acueducto/alcantarillado. Solo si SERVICIOS = 2 | Alfanumérico | 30 | Condicional | Obligatorio si SERVICIOS = 2 |
| FECHA_EXPEDICION_AAA | Fecha expedición disponibilidad acueducto/alcantarillado. Formato AAAAMMDD. Solo si SERVICIOS = 3 | Texto | 8 | Condicional | Obligatorio si SERVICIOS = 3 |
| NUM_DISPONIBILIDAD_AAA | Número disponibilidad acueducto/alcantarillado. Solo si SERVICIOS = 3 | Alfanumérico | 30 | Condicional | Obligatorio si SERVICIOS = 3 |
| VIGENCIA_AAA | Vigencia años disponibilidad acueducto/alcantarillado. Solo si SERVICIOS = 3 | Numérico | 1 | Condicional | Obligatorio si SERVICIOS = 3 |
| FECHA_RADICACION_EEA | Fecha radicación solicitud energía eléctrica. Formato AAAAMMDD. Solo si SERVICIOS = 2 | Texto | 8 | Condicional | Obligatorio si SERVICIOS = 2 |
| NUM_RADICADO_SOLICITUD_EEA | Número radicado energía eléctrica. Solo si SERVICIOS = 2 | Alfanumérico | 30 | Condicional | Obligatorio si SERVICIOS = 2 |
| FECHA_EXPEDICION_EEA | Fecha expedición disponibilidad energía eléctrica. Formato AAAAMMDD. Solo si SERVICIOS = 3 | Texto | 8 | Condicional | Obligatorio si SERVICIOS = 3 |
| NUM_DISPONIBILIDAD_EEA | Número disponibilidad energía eléctrica. Solo si SERVICIOS = 3 | Alfanumérico | 30 | Condicional | Obligatorio si SERVICIOS = 3 |
| VIGENCIA_EEA | Vigencia años disponibilidad energía eléctrica. Solo si SERVICIOS = 3 | Numérico | 1 | Condicional | Obligatorio si SERVICIOS = 3 |
| FECHA_RADICACION_GNA | Fecha radicación solicitud gas natural. Formato AAAAMMDD. Solo si SERVICIOS = 2 | Texto | 8 | Condicional | Obligatorio si SERVICIOS = 2 |
| NUM_RADICADO_SOLICITUD_GNA | Número radicado gas natural. Solo si SERVICIOS = 2 | Alfanumérico | 30 | Condicional | Obligatorio si SERVICIOS = 2 |
| FECHA_EXPEDICION_GNA | Fecha expedición disponibilidad gas natural. Formato AAAAMMDD. Solo si SERVICIOS = 3 | Texto | 8 | Condicional | Obligatorio si SERVICIOS = 3 |
| NUM_DISPONIBILIDAD_GNA | Número disponibilidad gas natural. Solo si SERVICIOS = 3 | Alfanumérico | 30 | Condicional | Obligatorio si SERVICIOS = 3 |
| VIGENCIA_GNA | Vigencia años disponibilidad gas natural. Solo si SERVICIOS = 3 | Numérico | 1 | Condicional | Obligatorio si SERVICIOS = 3 |
| PROYECCION_GENERACION_EMPLEO | Cantidad de empleos directos que va a generar la ejecución del proyecto (nueva obra, ampliación, remodelación) | Numérico | 4 | Sí | Valor >= 0 |

### 4.10 P-014A - SOPORTES INFRAESTRUCTURA (PDF)

**Documentos requeridos en el PDF:**
- Planos Arquitectónicos
- Cronograma de Obra con Fecha de Inicio
- Presupuesto de Obra
- Plan de Manejo Ambiental
- Licencia de Construcción o Radicado
- Disponibilidad Acueducto/Alcantarillado
- Disponibilidad Energía Eléctrica
- Disponibilidad Gas Natural

### 4.11 P-023A - ASPECTOS ESPECÍFICOS FONDOS DE CRÉDITO

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de la modalidad de fondo de crédito. Se debe reportar un registro por cada categoría y modalidad.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| MODALIDAD_CREDITO | Código modalidad (Tabla 66) | Numérico | 1 | Sí | 1=Social, 2=Fondo de Crédito |
| COD_CATEGORIA | Código categoría del afiliado (Tabla 8) | Numérico | 2 | Sí | 1=A, 2=B, 3=C, 4=D, 5=E |
| TASA_INTERES_MINIMA | Tasa de interés nominal anual mínima (ej: 10.25) | Texto | 5 | Sí | Formato: XX.XX. Rango: 0.00 a 99.99 |
| TASA_INTERES_MAXIMA | Tasa de interés nominal anual máxima (ej: 10.25) | Texto | 5 | Sí | Formato: XX.XX. Rango: 0.00 a 99.99. Debe ser >= TASA_INTERES_MINIMA |
| CANT_CREDITOS | Cantidad de créditos proyectados | Numérico | 10 | Sí | Valor >= 0 |
| VAL_MONTO_CREDITOS | Valor total de los créditos en pesos sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| PLAZO_CREDITO | Plazo en meses (Tabla 206) | Numérico | 2 | Sí | Valores: 1-120 meses |
| PORCENTAJE_SUBSIDIO | Porcentaje de subsidy (ej: 10.25). Solo para categorías A y B | Texto | 5 | Sí | Formato: XX.XX. Si no aplica, escribir 0.00 |

### 4.12 P-024A - CARTERA POR EDADES

**Instrucciones de llenado:** Este archivo se debe reportar ANUALMENTE y es información de respaldo del proyecto, sirve como base para la formulación de nuevos proyectos de crédito.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| RAN_EDAD | Código rango edad (Tabla 98, mayores de 18 años) | Numérico | 2 | Sí | Códigos: 01=18-23, 02=24-29, 03=30-35, 04=36-41, 05=42-47, 06=48-53, 07=54-59, 08=60-65, 09=66-71, 10=72-77, 11=78-83, 12=>=84 |
| EDAD_CARTERA | Código rango edad del crédito (Tabla 205) | Numérico | 1 | Sí | 1=Mora 0-30 días, 2=Mora 31-60 días, 3=Mora 61-90 días, 4=Mora >90 días |
| MODALIDAD_CREDITO | Código modalidad (Tabla 66) | Numérico | 1 | Sí | 1=Social, 2=Fondo de Crédito |
| COD_CATEGORIA | Código categoría del afiliado (Tabla 8) | Numérico | 2 | Sí | 1=A, 2=B, 3=C, 4=D, 5=E, 6=No aplicable |
| CANT_CREDITOS | Total créditos activos por rango de edad, modalidad y categoría | Numérico | 10 | Sí | Valor >= 0 |
| VALOR_TOTAL_MONTO_CARTERA | Valor total de la cartera pesos sin decimales | Numérico | 18 | Sí | Sin separadores de miles |

### 4.13 P-025A - CONTRATO DE ARRENDAMIENTO / COMODATO / COMPRA / PERMUTA (PDF)

**Instrucciones de llenado:** Este archivo reporta la información contractual correspondiente a los proyectos de inversión en bienes inmuebles (arrendamiento, comodato, compra, permuta).

> **⚠️ IMPORTANTE:** Si el proyecto no involucra ninguna de estas modalidades, reportar "no aplica" en SIMON.

**Documentos requeridos en el PDF:**
- Contrato de arrendamiento, comodato, compra o permuta (según corresponda)
- Si es permuta: adicionalmente做梦梦见头发剪短了 proyecto de contrato de permuta

### 4.13 P-026A - ARRENDAMIENTO BIENES INMUEBLES

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de inversión en arrendamiento de bienes inmuebles.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| FEC_CERT_TRADICION_LIBERTAD | Fecha certificado de tradición y libertad. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Vigencia <= 30 días al momento del reporte |
| FEC_AVALUO | Fecha del avalúo comercial. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Debe ser <= FEC_CERT_TRADICION_LIBERTAD |
| PERITO | Nombre del avaluador responsable | Texto | 50 | Sí | Nombre completo |
| VALOR_AVALUO | Valor del canon mensual según avalúo sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| VAL_CANON_MENSUAL | Valor del canon mensual a pagar sin decimales | Numérico | 18 | Sí | Sin separadores de miles. Debe ser <= VALOR_AVALUO |
| TMP_CONTRATO | Tiempo de contrato en meses | Numérico | 3 | Sí | Valor entre 1 y 360 |
| DESTINACION_INMUEBLE | Destinación del inmueble (educación, salud, etc.) | Texto | 50 | Sí | - |
| USO_AUTORIZADO | Uso de suelo autorizado según certificado | Texto | 50 | Sí | - |

### 4.14 P-027A - SOPORTES ARRENDAMIENTO (PDF)

**Documentos requeridos:**
- Certificado de Libertad y Tradición (vigencia <=30 días)
- Avalúo Comercial + Certificación RAA
- Certificado de Uso de Suelo
- Determinación Técnica Fundamentada en Avalúo

### 4.15 P-031A - COMODATO BIENES INMUEBLES

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de inversión en comodato de bienes inmuebles.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| FEC_CERT_TRADICION_LIBERTAD | Fecha certificado de tradición y libertad. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Vigencia <= 30 días al momento del reporte |
| DESTINACION_INMUEBLE | Destinación del inmueble (educación, salud, etc.) | Texto | 50 | Sí | - |
| USO_AUTORIZADO | Uso de suelo autorizado según certificado | Texto | 50 | Sí | - |

**Nota:** Según artículo 44 Ley 21 de 1982, no pueden entregar en comodato bienes propios de la Caja de Compensación Familiar.

### 4.16 P-032A - SOPORTES COMODATO (PDF)

**Documentos requeridos:**
- Certificado de Uso de Suelo
- Contrato de Comodato.pdf

**Nota:** Según artículo 44 Ley 21 de 1982, no pueden entregar en comodato bienes propios.

### 4.17 P-034A - COMPRA BIENES INMUEBLES

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de inversión en compra de bienes inmuebles.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| FEC_CERT_TRADICION_LIBERTAD | Fecha certificado de tradición y libertad. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Vigencia <= 30 días al momento del reporte |
| FEC_AVALUO | Fecha del avalúo comercial. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Debe ser <= FEC_CERT_TRADICION_LIBERTAD |
| PERITO | Nombre del avaluador responsable | Texto | 50 | Sí | Nombre completo |
| VALOR_AVALUO | Valor comercial del inmueble según avalúo sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| DESTINACION_INMUEBLE | Destinación del inmueble (educación, salud, etc.) | Texto | 50 | Sí | - |
| USO_AUTORIZADO | Uso de suelo autorizado según certificado | Texto | 50 | Sí | - |

### 4.18 P-035A - SOPORTES COMPRA (PDF)

**Documentos requeridos:**
- Certificado de Libertad y Tradición (vigencia <=30 días)
- Avalúo Comercial + Certificación RAA
- Estudio de Títulos
- Certificado de Uso de Suelo
- Certificado de No Riesgo y Plan de Mitigación (si aplica)

### 4.19 P-040A - PERMUTA BIENES INMUEBLES

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de inversión en permuta de bienes inmuebles. Se deben reportar los datos tanto del inmueble que recibe como del que entrega la Caja.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| FEC_CERT_TRADICION_LIBERTAD | Fecha cert. tradición inmueble que recibe. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Vigencia <= 30 días |
| FEC_AVALUO_RECIBE | Fecha avalúo inmueble que recibe. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida. Debe ser <= FEC_CERT_TRADICION_LIBERTAD |
| FEC_AVALUO_ENTREGA | Fecha avalúo inmueble que entrega. Formato AAAAMMDD | Texto | 8 | Sí | Fecha válida |
| AVALUADOR_RECIBE | Nombre avaluador inmueble que recibe | Texto | 50 | Sí | Nombre completo |
| AVALUADOR_ENTREGA | Nombre avaluador inmueble que entrega | Texto | 50 | Sí | Nombre completo |
| VAL_AVAL_RECIBE | Valor comercial inmueble que recibe sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| VAL_AVAL_ENTREGA | Valor comercial inmueble que entrega sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| DESTINACION_INMUEBLE | Destinación inmueble que recibe | Texto | 50 | Sí | - |
| USO_AUTORIZADO | Uso de suelo inmueble que recibe | Texto | 50 | Sí | - |
| VAL_LIBROS | Valor en libros inmueble que entrega sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| UTILIDAD_PERDIDA | Utilidad o pérdida de negociación (negativo para pérdida) | Numérico | 18 | Sí | Sin separadores de miles. Negativo = pérdida |
| ORIGEN_RECURSOS | Origen de los recursos inmueble que entrega | Texto | 30 | Sí | - |
| DESTINACION | Destinación de recursos o bienes | Texto | 80 | Sí | - |

### 4.20 P-041A - SOPORTES PERMUTA (PDF)

**Documentos requeridos:**
- Certificado de Libertad y Tradición (vigencia <=30 días)
- Avalúo Comercial + Certificación RAA (ambos inmuebles)
- Estudio de Títulos inmueble que recibe
- Certificado de Uso de Suelo inmueble que recibe
- Certificado de No Riesgo y Plan de Mitigación (si aplica)
- Proyecto de Contrato de Permuta

### 4.21 P-050A - NEGOCIACIÓN ACCIONES/CUOTAS/PARTES

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de inversión en negociación de acciones, cuotas o partes sociales.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| NUM_ACCIONES_CUOTAS | Cantidad de acciones/cuotas/partes a negociar | Numérico | 10 | Sí | Valor >= 0 |
| VALOR_ACCIONES_CUOTAS | Valor total a negociar en pesos sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| PORCENTAJE_PARTICIPACION | Porcentaje de participación según Cámara de Comercio (ej: 10.25) | Texto | 5 | Sí | Formato: XX.XX. Rango: 0.00 a 100.00 |
| VALOR_NOMINAL_ACCIONES | Valor nominal certificado por revisor fiscal sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| VALOR_MERCADO_ACCIONES | Valor de mercado certificado por revisor fiscal sin decimales | Numérico | 18 | Sí | Sin separadores de miles |

### 4.22 P-051A - SOPORTES NEGOCIACIÓN (PDF)

**Documentos requeridos:**
- Estados Financieros Comparativos (3 años): Balance, Resultados, Cambios patrimonio, Flujo efectivo, Cambios situación financiera
- Análisis Situación Financiera y de Mercado
- Evaluación Financiera (si nueva sociedad: estudio factibilidad)
- Porcentaje de Participación (Cámara de Comercio)
- Valor Nominal y de Mercado (certificado Revisor Fiscal)
- Plan de Retorno ROI
- Políticas de Distribución de Dividendos

### 4.23 P-055A - CAPITALIZACIONES

**Instrucciones de llenado:** Este archivo reporta la información específica de los proyectos de inversión en capitalización de acciones, cuotas o partes sociales.

| Campo | Descripción | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código del proyecto (previamente reportado en P-001A) | Alfanumérico | 15 | Sí | Formato: CCFXXX-XX-XXXXX. Debe existir en P-001A |
| NUM_ACCIONES_CUOTAS | Cantidad de acciones/cuotas según Cámara de Comercio | Numérico | 10 | Sí | Valor >= 0 |
| VALOR_ACCIONES_CUOTAS | Valor según Cámara de Comercio sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| PORCENTAJE_PARTICIPACION | Porcentaje de participación según Cámara de Comercio (ej: 10.25) | Texto | 5 | Sí | Formato: XX.XX. Rango: 0.00 a 100.00 |
| VALOR_NOMINAL_ACCIONES | Valor nominal certificado por revisor fiscal sin decimales | Numérico | 18 | Sí | Sin separadores de miles |
| VALOR_MERCADO_ACCIONES | Valor de mercado certificado por revisor fiscal sin decimales | Numérico | 18 | Sí | Sin separadores de miles |

### 4.24 P-056A - SOPORTES CAPITALIZACIONES (PDF)

**Documentos requeridos:**
- Estados Financieros Comparativos (3 años)
- Análisis Situación Financiera y de Mercado
- Porcentaje de Participación (Cámara de Comercio)
- Valor Nominal y de Mercado (Revisor Fiscal)
- Plan de Retorno ROI

---

### 4.25 ESPECIFICACIONES DE COMPONENTES ANGULAR PARA PRESENTACIÓN DE CAMPOS

Esta sección define los componentes Angular 21 requeridos para implementar la presentación de campos según las reglas del Anexo Técnico Circular Externa 2025-00008.

#### 4.25.1 Componente `app-form-field` (Atomic Design)

Componente atómico para renderizar campos de formulario con tooltip de ayuda.

```typescript
// form-field.component.ts
import { Component, input, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TooltipDirective } from '@shared/directives/tooltip.directive';

@Component({
  selector: 'app-form-field',
  standalone: true,
  imports: [CommonModule, TooltipDirective],
  template: `
    <div class="form-field" [class.has-error]="hasError()" [class.is-conditional]="isConditional()">
      <label class="form-field__label">
        {{ label() }}
        @if (isRequired()) {
          <span class="form-field__required">*</span>
        }
        @if (isConditional()) {
          <span class="form-field__conditional-badge">Condicional</span>
        }
      </label>

      <div class="form-field__content">
        <ng-content></ng-content>

        <button
          type="button"
          class="form-field__help-btn"
          [attr.aria-label]="'Ayuda para ' + label()"
          (click)="toggleHelp()">
          ?
        </button>
      </div>

      @if (showHelp()) {
        <div class="form-field__help-panel" role="tooltip">
          <p class="form-field__description">{{ description() }}</p>
          <p class="form-field__format">
            <strong>Formato:</strong> {{ formatExample() }}
          </p>
          @if (tableReference()) {
            <p class="form-field__table-ref">
              <strong>Tabla:</strong> {{ tableReference() }}
            </p>
          }
          @if (validationRules().length > 0) {
            <ul class="form-field__rules">
              @for (rule of validationRules(); track rule) {
                <li>{{ rule }}</li>
              }
            </ul>
          }
        </div>
      }

      <span class="form-field__helper">{{ helperText() }}</span>

      @if (hasError()) {
        <span class="form-field__error">{{ errorMessage() }}</span>
      }

      <span class="form-field__char-count">
        {{ currentLength() }}/{{ maxLength() }}
      </span>
    </div>
  `,
  styles: [`
    .form-field {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
      margin-bottom: 1rem;
    }
    .form-field__label {
      font-weight: 500;
      color: var(--color-text-primary);
    }
    .form-field__required {
      color: var(--color-error);
      margin-left: 0.25rem;
    }
    .form-field__conditional-badge {
      font-size: 0.75rem;
      background: var(--color-info);
      color: white;
      padding: 0.125rem 0.5rem;
      border-radius: 9999px;
      margin-left: 0.5rem;
    }
    .form-field__content {
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }
    .form-field__help-btn {
      width: 1.5rem;
      height: 1.5rem;
      border-radius: 50%;
      background: var(--color-info);
      color: white;
      border: none;
      cursor: pointer;
      font-weight: bold;
    }
    .form-field__help-panel {
      background: var(--color-background-secondary);
      border: 1px solid var(--color-border);
      border-radius: 0.5rem;
      padding: 1rem;
      margin-top: 0.5rem;
    }
    .form-field__description { margin: 0 0 0.5rem 0; }
    .form-field__format { margin: 0 0 0.5rem 0; font-family: monospace; }
    .form-field__table-ref { margin: 0 0 0.5rem 0; color: var(--color-info); }
    .form-field__rules { margin: 0; padding-left: 1.5rem; }
    .form-field__helper { font-size: 0.875rem; color: var(--color-text-secondary); }
    .form-field__error { font-size: 0.875rem; color: var(--color-error); }
    .form-field__char-count { font-size: 0.75rem; color: var(--color-text-secondary); text-align: right; }
    .form-field.has-error input { border-color: var(--color-error); }
  `]
})
export class FormFieldComponent {
  label = input.required<string>();
  description = input<string>('');
  formatExample = input<string>('');
  tableReference = input<string>('');
  validationRules = input<string[]>([]);
  helperText = input<string>('');
  isRequired = input<boolean>(false);
  isConditional = input<boolean>(false);
  maxLength = input<number>(0);
  currentLength = input<number>(0);
  hasError = input<boolean>(false);
  errorMessage = input<string>('');

  showHelp = signal(false);

  toggleHelp() {
    this.showHelp.update(v => !v);
  }
}
```

#### 4.25.2 Servicio `ValidationService` - Reglas de Condicionalidad

Servicio para manejar validaciones y campos condicionales basados en el Anexo Técnico.

```typescript
// validation.service.ts
import { Injectable, signal, computed } from '@angular/core';
import { TableReference } from '@shared/models/table-reference.model';

export interface ConditionalRule {
  fieldName: string;
  conditionField: string;
  conditionValue: any;
  requiredIfTrue: boolean;
  visibilityIfTrue: 'show' | 'hide';
  message?: string;
}

export interface FieldValidation {
  fieldName: string;
  type: 'required' | 'format' | 'range' | 'custom';
  rule: RegExp | ((value: any) => boolean);
  errorMessage: string;
}

@Injectable({ providedIn: 'root' })
export class ValidationService {
  // Mapa de campos condicionales por estructura
  private conditionalRules = signal<Map<string, ConditionalRule[]>>(new Map([
    ['P-013A', [
      { fieldName: 'ENT_COMPETENTE', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: [2, 3], requiredIfTrue: true, visibilityIfTrue: 'show', message: 'Obligatorio cuando Licencia=2 o 3' },
      { fieldName: 'NUM_RADICADO_SOLICITUD_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show', message: 'Obligatorio cuando Licencia=2 (En trámite)' },
      { fieldName: 'FECHA_RADICACION_SOLICITUD_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUMERO_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show', message: 'Obligatorio cuando Licencia=3 (Expedida)' },
      { fieldName: 'FECHA_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'VIGENCIA_LICENCIA', conditionField: 'LICENCIA_CONSTRUCCION_URBANISMO', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_RADICACION_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_RADICADO_SOLICITUD_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_EXPEDICION_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_DISPONIBILIDAD_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'VIGENCIA_AAA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_RADICACION_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_RADICADO_SOLICITUD_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_EXPEDICION_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_DISPONIBILIDAD_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'VIGENCIA_EEA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_RADICACION_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_RADICADO_SOLICITUD_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 2, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'FECHA_EXPEDICION_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'NUM_DISPONIBILIDAD_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
      { fieldName: 'VIGENCIA_GNA', conditionField: 'SERVICIOS_PUBLICOS', conditionValue: 3, requiredIfTrue: true, visibilityIfTrue: 'show' },
    ]],
    ['P-002A', [
      { fieldName: 'VALOR_PLANEADO', conditionField: 'MODALIDAD_INVERSION', conditionValue: 'INF', requiredIfTrue: false, visibilityIfTrue: 'show', message: 'Solo para proyectos de infraestructura' },
      { fieldName: 'CANTIDAD_PROGRAMACION_ACTIVIDAD', conditionField: 'TIPO_ACTIVIDAD', conditionValue: ['0401', '0402', '0403'], requiredIfTrue: false, visibilityIfTrue: 'show' },
    ]],
  ]));

  // Tablas de referencia para dropdowns
  private tableReferences = signal<Map<number, TableReference[]>>(new Map([
    [66, [ // MODALIDAD_CREDITO
      { code: '1', description: 'Libre inversión' },
      { code: '2', description: 'Consumo' },
      { code: '3', description: 'Créditos Educativos' },
      { code: '4', description: 'Créditos de Salud' },
      { code: '5', description: 'Créditos de vivienda' },
      { code: '6', description: 'Fomento, Emprendimiento' },
      { code: '7', description: 'Otros' },
      { code: '8', description: 'Crédito de mercadeo' },
      { code: '9', description: 'Recreación y turismo' },
    ]],
    [8, [ // CATEGORIA
      { code: '1', description: 'A' },
      { code: '2', description: 'B' },
      { code: '3', description: 'C' },
      { code: '4', description: 'D' },
      { code: '5', description: 'E' },
    ]],
    [68, [ // MESES
      { code: '1', description: 'Enero' },
      { code: '2', description: 'Febrero' },
      { code: '3', description: 'Marzo' },
      { code: '4', description: 'Abril' },
      { code: '5', description: 'Mayo' },
      { code: '6', description: 'Junio' },
      { code: '7', description: 'Julio' },
      { code: '8', description: 'Agosto' },
      { code: '9', description: 'Septiembre' },
      { code: '10', description: 'Octubre' },
      { code: '11', description: 'Noviembre' },
      { code: '12', description: 'Diciembre' },
    ]],
    [115, [ // TIPO_ACTIVIDAD
      { code: '0501', description: 'Prediseño' },
      { code: '0502', description: 'Diseño' },
      { code: '0503', description: 'Ejecución' },
      { code: '0504', description: 'Interventoría' },
      { code: '0505', description: 'Adquisición de bienes' },
      { code: '0506', description: 'Adecuación de bienes' },
      { code: '0507', description: 'Mantenimiento de bienes' },
      { code: '0508', description: 'Dotación' },
      { code: '0509', description: 'Otros' },
      { code: '0601', description: 'Capacitación' },
      { code: '0602', description: 'Evaluar' },
      { code: '0603', description: 'Certificar' },
      { code: '0604', description: 'Actualizar' },
      { code: '0701', description: 'Fortalecimiento' },
      { code: '0702', description: 'Gestión' },
      { code: '0401', description: 'Desembolso crédito social' },
      { code: '0402', description: 'Desembolso microcrédito' },
      { code: '0403', description: 'Desembolso crédito constructor' },
      { code: '0404', description: 'Pago honorarios' },
      { code: '0405', description: 'Pago nómina' },
      { code: '0406', description: 'Pago proveedores' },
      { code: '0407', description: 'Pago arriendos' },
      { code: '0408', description: 'Pago servicios' },
      { code: '0409', description: 'Pago obras' },
      { code: '0410', description: 'Pago equipos' },
      { code: '0411', description: 'Constitución garantia' },
      { code: '0412', description: 'Incremento capital trabajo' },
      { code: '0413', description: 'Gastos legales' },
      { code: '0414', description: 'Constitución provisión' },
      { code: '0415', description: 'Castigo cartera' },
      { code: '0416', description: 'Recuperación cartera' },
      { code: '0417', description: ' 其他...' },
      { code: '0418', description: 'Seguros' },
      { code: '0901', description: 'Gastos de personal' },
      { code: '0902', description: 'Gastos generales' },
      { code: '0903', description: 'Gastos de viaje' },
      { code: '1001', description: 'Gastos financieros' },
      { code: '1002', description: 'Gastos no operacionales' },
    ]],
    [203, [ // INTERVENTORIA_SUPERVISION
      { code: '1', description: 'Interna' },
      { code: '2', description: 'Externa' },
    ]],
    [204, [ // ESTADO_LICENCIA_SERVICIOS
      { code: '1', description: 'N.A.' },
      { code: '2', description: 'En trámite' },
      { code: '3', description: 'Expedida' },
    ]],
  ]));

  getConditionalRules(structureCode: string): ConditionalRule[] {
    return this.conditionalRules().get(structureCode) || [];
  }

  getTableValues(tableNumber: number): TableReference[] {
    return this.tableReferences().get(tableNumber) || [];
  }

  isFieldVisible(fieldName: string, structureCode: string, formValues: Record<string, any>): boolean {
    const rules = this.getConditionalRules(structureCode);
    const rule = rules.find(r => r.fieldName === fieldName);
    if (!rule) return true;

    const conditionValue = formValues[rule.conditionField];
    if (Array.isArray(rule.conditionValue)) {
      return rule.conditionValue.includes(conditionValue);
    }
    return conditionValue === rule.conditionValue;
  }

  isFieldRequired(fieldName: string, structureCode: string, formValues: Record<string, any>): boolean {
    const rules = this.getConditionalRules(structureCode);
    const rule = rules.find(r => r.fieldName === fieldName);
    if (!rule) return false;

    const conditionValue = formValues[rule.conditionField];
    if (Array.isArray(rule.conditionValue)) {
      return rule.conditionValue.includes(conditionValue);
    }
    return conditionValue === rule.conditionValue;
  }
}
```

#### 4.25.3 Componente `app-table-reference-dropdown`

Componente molecular para dropdowns de tablas de referencia.

```typescript
// table-reference-dropdown.component.ts
import { Component, input, output, computed, inject } from '@angular/core';
import { ValidationService } from '@shared/services/validation.service';

@Component({
  selector: 'app-table-reference-dropdown',
  standalone: true,
  template: `
    <div class="table-dropdown">
      <select
        [id]="fieldId()"
        [name]="fieldName()"
        [ngModel]="value()"
        (ngModelChange)="valueChange.emit($event)"
        [disabled]="disabled()"
        class="table-dropdown__select"
        [class.has-error]="hasError()">
        <option value="">Seleccione...</option>
        @for (option of tableOptions(); track option.code) {
          <option [value]="option.code">{{ option.code }} - {{ option.description }}</option>
        }
      </select>
      @if (showTableInfo()) {
        <span class="table-dropdown__table-ref">Tabla {{ tableNumber() }}</span>
      }
    </div>
  `,
  styles: [`
    .table-dropdown { display: flex; flex-direction: column; gap: 0.25rem; }
    .table-dropdown__select {
      padding: 0.5rem;
      border: 1px solid var(--color-border);
      border-radius: 0.25rem;
      font-size: 1rem;
    }
    .table-dropdown__select.has-error { border-color: var(--color-error); }
    .table-dropdown__table-ref { font-size: 0.75rem; color: var(--color-info); }
  `]
})
export class TableReferenceDropdownComponent {
  private validationService = inject(ValidationService);

  fieldId = input.required<string>();
  fieldName = input.required<string>();
  tableNumber = input.required<number>();
  value = input<string>('');
  disabled = input<boolean>(false);
  hasError = input<boolean>(false);
  showTableInfo = input<boolean>(true);

  valueChange = output<string>();

  tableOptions = computed(() => this.validationService.getTableValues(this.tableNumber()));
}
```

#### 4.25.4 Componente `app-project-status-dashboard`

Componente molecular para mostrar estado de estructuras por proyecto.

```typescript
// project-status-dashboard.component.ts
import { Component, input, computed } from '@angular/core';
import { CommonModule } from '@angular/common';

interface StructureStatus {
  code: string;
  name: string;
  status: 'completed' | 'pending' | 'not-applicable' | 'overdue';
  lastUpdate?: Date;
  dueDate?: Date;
}

@Component({
  selector: 'app-project-status-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard">
      <h3 class="dashboard__title">{{ projectCode() }} - {{ projectName() }}</h3>

      <div class="dashboard__structures">
        @for (structure of structures(); track structure.code) {
          <div
            class="structure-item"
            [class.completed]="structure.status === 'completed'"
            [class.pending]="structure.status === 'pending'"
            [class.overdue]="structure.status === 'overdue'"
            [class.not-applicable]="structure.status === 'not-applicable'">
            <span class="structure-item__icon">
              @switch (structure.status) {
                @case ('completed') { ✓ }
                @case ('pending') { ☐ }
                @case ('overdue') { ⚠️ }
                @case ('not-applicable') { ○ }
              }
            </span>
            <span class="structure-item__code">{{ structure.code }}</span>
            <span class="structure-item__name">{{ structure.name }}</span>
            @if (structure.lastUpdate) {
              <span class="structure-item__date">{{ structure.lastUpdate | date:'dd/MM/yyyy' }}</span>
            }
            @if (structure.status === 'pending' || structure.status === 'overdue') {
              <span class="structure-item__action">
                @if (structure.status === 'overdue') { ❌ Pendiente }
                @else { ❌ Pendiente }
              </span>
            }
          </div>
        }
      </div>

      @if (show45DayWarning()) {
        <div class="dashboard__warning">
          ⚠️ 45 días para radicar modificaciones después de aprobación
        </div>
      }
    </div>
  `,
  styles: [`
    .dashboard {
      background: var(--color-background);
      border: 1px solid var(--color-border);
      border-radius: 0.5rem;
      padding: 1rem;
    }
    .dashboard__title { margin: 0 0 1rem 0; }
    .dashboard__structures { display: flex; flex-direction: column; gap: 0.5rem; }
    .structure-item {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      padding: 0.5rem;
      border-radius: 0.25rem;
    }
    .structure-item.completed { background: var(--color-success-bg); }
    .structure-item.pending { background: var(--color-warning-bg); }
    .structure-item.overdue { background: var(--color-error-bg); }
    .structure-item.not-applicable { background: var(--color-background-secondary); }
    .structure-item__icon { width: 1.5rem; }
    .structure-item__code { font-weight: bold; min-width: 5rem; }
    .structure-item__name { flex: 1; }
    .structure-item__date { font-size: 0.875rem; color: var(--color-text-secondary); }
    .structure-item__action { font-size: 0.875rem; font-weight: bold; }
    .dashboard__warning {
      margin-top: 1rem;
      padding: 0.75rem;
      background: var(--color-warning-bg);
      border: 1px solid var(--color-warning);
      border-radius: 0.25rem;
      text-align: center;
      font-weight: bold;
    }
  `]
})
export class ProjectStatusDashboardComponent {
  projectCode = input.required<string>();
  projectName = input.required<string>();
  structures = input<StructureStatus[]>([]);
  show45DayWarning = input<boolean>(true);

  pendingCount = computed(() => this.structures().filter(s => s.status === 'pending' || s.status === 'overdue').length);
  completedCount = computed(() => this.structures().filter(s => s.status === 'completed').length);
}
```

#### 4.25.5 Componente `app-pdf-checklist`

Componente molecular para checklist de documentos PDF.

```typescript
// pdf-checklist.component.ts
import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface PdfDocument {
  id: string;
  name: string;
  isRequired: boolean;
  conditionMessage?: string;
  isUploaded: boolean;
}

@Component({
  selector: 'app-pdf-checklist',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="pdf-checklist">
      <h4 class="pdf-checklist__title">{{ title() }} (PDF)</h4>

      <ul class="pdf-checklist__documents">
        @for (doc of documents(); track doc.id) {
          <li class="pdf-checklist__item" [class.is-uploaded]="doc.isUploaded">
            <label class="pdf-checklist__label">
              <input
                type="checkbox"
                [checked]="doc.isUploaded"
                (change)="toggleDocument.emit(doc.id)"
                [disabled]="doc.isRequired && !doc.isUploaded" />
              <span class="pdf-checklist__check">
                @if (doc.isUploaded) { ✓ }
              </span>
              <span class="pdf-checklist__name">{{ doc.name }}</span>
              @if (doc.conditionMessage) {
                <span class="pdf-checklist__condition">({{ doc.conditionMessage }})</span>
              }
            </label>
          </li>
        }
      </ul>

      <div class="pdf-checklist__info">
        <span>📄 Formato: PDF</span>
        <span>📅 Tamaño máximo: 50MB</span>
      </div>
    </div>
  `,
  styles: [`
    .pdf-checklist {
      border: 1px solid var(--color-border);
      border-radius: 0.5rem;
      padding: 1rem;
      background: var(--color-background);
    }
    .pdf-checklist__title { margin: 0 0 1rem 0; }
    .pdf-checklist__documents { list-style: none; padding: 0; margin: 0; }
    .pdf-checklist__item { padding: 0.5rem; }
    .pdf-checklist__item.is-uploaded { background: var(--color-success-bg); }
    .pdf-checklist__label { display: flex; align-items: center; gap: 0.5rem; cursor: pointer; }
    .pdf-checklist__check { width: 1.5rem; height: 1.5rem; display: flex; align-items: center; justify-content: center; }
    .pdf-checklist__condition { font-size: 0.875rem; color: var(--color-text-secondary); font-style: italic; }
    .pdf-checklist__info { display: flex; gap: 1rem; margin-top: 1rem; font-size: 0.875rem; color: var(--color-text-secondary); }
  `]
})
export class PdfChecklistComponent {
  title = input.required<string>();
  documents = input<PdfDocument[]>([]);
  toggleDocument = output<string>();
}
```

#### 4.25.6 Directivas de Validación

```typescript
// date-format.directive.ts
import { Directive, forwardRef } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appDateFormat]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => DateFormatDirective), multi: true }
  ]
})
export class DateFormatDirective implements Validator {
  private datePattern = /^\d{8}$/;

  validate(control: AbstractControl): ValidationErrors | null {
    if (!control.value) return null;

    const value = control.value.toString();
    if (!this.datePattern.test(value)) {
      return { dateFormat: { message: 'Formato inválido. Use AAAAMMDD (8 dígitos)' } };
    }

    const year = parseInt(value.substring(0, 4), 10);
    const month = parseInt(value.substring(4, 6), 10);
    const day = parseInt(value.substring(6, 8), 10);

    if (year < 1900 || year > 2100) {
      return { dateFormat: { message: 'Año inválido' } };
    }
    if (month < 1 || month > 12) {
      return { dateFormat: { message: 'Mes inválido (01-12)' } };
    }
    if (day < 1 || day > 31) {
      return { dateFormat: { message: 'Día inválido (01-31)' } };
    }

    return null;
  }
}

// numeric-no-separator.directive.ts
import { Directive, forwardRef } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appNumericNoSeparator]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => NumericNoSeparatorDirective), multi: true }
  ]
})
export class NumericNoSeparatorDirective implements Validator {
  private numericPattern = /^\d+$/;

  validate(control: AbstractControl): ValidationErrors | null {
    if (!control.value) return null;

    const value = control.value.toString();
    if (!this.numericPattern.test(value)) {
      return { numericNoSeparator: { message: 'Solo números sin puntos ni comas' } };
    }

    return null;
  }
}

// decimal-format.directive.ts
import { Directive, forwardRef } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appDecimalFormat]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => DecimalFormatDirective), multi: true }
  ]
})
export class DecimalFormatDirective implements Validator {
  private decimalPattern = /^\d{1,2}\.\d{1,2}$/;

  validate(control: AbstractControl): ValidationErrors | null {
    if (!control.value) return null;

    const value = control.value.toString();
    if (!this.decimalPattern.test(value)) {
      return { decimalFormat: { message: 'Formato inválido. Use XX.XX (ej: 10.25)' } };
    }

    const num = parseFloat(value);
    if (num < 0 || num > 99.99) {
      return { decimalFormat: { message: 'Valor debe estar entre 0.00 y 99.99' } };
    }

    return null;
  }
}
```

#### 4.25.7 Signals para Estado de Formulario

```typescript
// project-form.state.ts
import { Injectable, signal, computed } from '@angular/core';

export interface FormState {
  projectCode: string;
  modality: string;
  investmentModality: string;
  structures: Map<string, boolean>;
}

@Injectable({ providedIn: 'root' })
export class ProjectFormState {
  // Estado del formulario
  private formState = signal<FormState>({
    projectCode: '',
    modality: '',
    investmentModality: '',
    structures: new Map()
  });

  //Selectors
  projectCode = computed(() => this.formState().projectCode);
  modality = computed(() => this.formState().modality);
  investmentModality = computed(() => this.formState().investmentModality);

  isInfrastructureProject = computed(() =>
    this.formState().investmentModality === 'INF'
  );

  //Estructuras
  requiredStructures = computed(() => [
    'P-001A', 'P-002A', 'P-003A', 'P-004C', 'P-005A',
    'P-011A', 'P-013A'
  ]);

  optionalStructures = computed(() => [
    'P-011B', 'P-012A', 'P-023A', 'P-024A',
    'P-026A', 'P-031A', 'P-034A', 'P-040A',
    'P-050A', 'P-055A'
  ]);

  completedStructures = computed(() =>
    Array.from(this.formState().structures.entries())
      .filter(([_, completed]) => completed)
      .map(([code, _]) => code)
  );

  pendingStructures = computed(() => {
    const all = [...this.requiredStructures(), ...this.optionalStructures()];
    return all.filter(code => !this.formState().structures.get(code));
  });

  completionPercentage = computed(() => {
    const total = this.requiredStructures().length;
    const completed = this.completedStructures().length;
    return Math.round((completed / total) * 100);
  });

  // Actions
  updateField(field: keyof FormState, value: any) {
    this.formState.update(state => ({ ...state, [field]: value }));
  }

  markStructureCompleted(code: string) {
    this.formState.update(state => {
      const newStructures = new Map(state.structures);
      newStructures.set(code, true);
      return { ...state, structures: newStructures };
    });
  }

  markStructurePending(code: string) {
    this.formState.update(state => {
      const newStructures = new Map(state.structures);
      newStructures.set(code, false);
      return { ...state, structures: newStructures };
    });
  }
}
```

---

## 5. ESTRUCTURAS DE DATOS - PROYECTOS FOVIS (P-001F a P-050F)

### 5.0 Instrucciones Generales - Proyectos FOVIS

**⚠️ REGLA CRÍTICA - Dependencia de MODALIDAD_FOVIS:**
El campo MODALIDAD_FOVIS (Tabla 210) del proyecto define qué campos son obligatorios en otras estructuras:

| Código MODALIDAD_FOVIS | Descripción | Campos condicionales obligatorios |
|------------------------|-------------|----------------------------------|
| 1, 3, 5, 6 | Obras Asociadas, Incremento Capital, Proyectos Integrales, Microcrédito | P-002F: PORCENTAJE_PROYECTADO |
| 2 | Créditos Hipotecarios y Microcréditos | P-002F: CANTIDAD_CREDITOS_MICROCREDITOS_PROYECTADOS + VALOR_CREDITOS_MICROCREDITOS_PROYECTADOS |
| 4 | Adquisición de Lotes | P-012F: fecha_compra_lote, valor_total_compra_lote, valor_otros_costos |

**Formatos de fecha:** Todos los campos de fecha deben digitarse en formato AAAAMMDD (8 dígitos).

**Valores numéricos:** Sin decimales ni separadores. Si no hay valor, escribir 0.

**Código del proyecto:** Usar el código exacto asignado en P-001F. No abbreviate.

### 5.1 P-001F - ESTRUCTURA GENERALES PROYECTOS FOVIS NUEVOS

**Instrucciones de llenado:** Este formato se diligencia para proyectos nuevos FOVIS. El código del proyecto debe seguir el formato: Código Caja (2 dígitos) + '-' + Código Actividad Tabla 88 (2 dígitos) + '-' + Consecutivo (5 dígitos). Ejemplo: 01-01-00001.

| Campo | Descripción | Qué debe digitar el usuario | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|-------------|----------------------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código único del proyecto FOVIS | Código compuesto: Código Caja (2) + '-' + Actividad Tabla 88 (2) + '-' + Consecutivo (5). Ej: 01-01-00001. **Este código se usa en todos los demás formularios FOVIS** | Alfanumérico | 15 | Sí | Formato: XX-XX-XXXXX. Debe ser único. Se asigna al crear el proyecto. Usar siempre este código en P-002F, P-011F, P-012F, etc. |
| VAL_TOTAL_PROYECTO | Valor total del proyecto | Valor en pesos COP sin decimales ni separadores. Ej: 2500000000 para dos mil quinientos millones | Numérico | 18 | Sí | Solo enteros positivos. Debe ser mayor a 0. Este valor no puede cambiar después del primer reporte. |
| MODALIDAD_FOVIS | Modalidad del proyecto | Código de 1 o 2 dígitos según Tabla 210: 1=Obras Asociadas, 2=Créditos Hipotecarios y Microcréditos, 3=Incremento de Capital, 4=Adquisición de Lotes, 5=Proyectos Integrales, 6=Microcredito | Texto | 2 | Sí | **CRÍTICO:** Este campo determina qué campos son obligatorios en P-002F (cronograma). Si código 1,3,5,6 → usar PORCENTAJE_PROYECTADO. Si código 2 → usar CANTIDAD_CREDITOS y VALOR_CREDITOS. |
| DESCRIPCION_PROYECTO | Descripción detallada | Texto describing el proyecto, sus actividades principales, ubicación y alcances. Mínimo 100 caracteres | Texto | 4000 | Sí | Incluir: qué se va a hacer, dónde (ciudad/barrio/dirección), cómo, y qué resultados se esperan. |
| OBJETIVO_PROYECTO | Objetivos del proyecto | Minimum 3 objetivos específicos usando metodología SMART (Specific, Measurable, Achievable, Relevant, Time-bound). Cada objetivo en párrafo separado | Texto | 4000 | Sí | Incluir al menos: 1 objetivo general y 2 específicos. Cada uno con meta cuantificable y plazo. |
| JUSTIFICACION | Justificación técnica | Identificación clara del problema o necesidad que justifica la inversión, con datos de respaldo | Texto | 4000 | Sí | Soportar con estadísticas, estudios técnicos o diagnósticos previos. Explicar por qué este proyecto es la mejor solución. |
| APROBACION | ¿Cuenta con resolución AEI? | 1=Sí, cuenta con resolución AEI; 2=No, no requiere AEI | Numérico | 1 | Sí | **Regla condicional:** Si APROBACION=1 → NUM_ACTA_AEI>0, NUM_CONSEJEROS>=7. Si APROBACION=2 → NUM_CONSEJEROS=1 |
| NUM_ACTA_AEI | Número de acta o resolución | Número entero del documento de aprobación. Si no aplica, escribir 0 | Numérico | 10 | Sí | Si APROBACION=1 debe ser mayor a 0. |
| FECHA_APR_AEI | Fecha de aprobación | Fecha en formato AAAAMMDD. Ej: 20250515 para 15 de mayo de 2025 | Texto | 8 | Sí | Debe ser fecha válida. Si APROBACION=2 escribir 00000000. |
| NUM_CONSEJEROS | Número de consejeros que aprobaron | Cantidad numérica: >=7 para aprobación ordinaria del consejo, =1 si es resolución AEI | Numérico | 2 | Sí | Validar según valor de APROBACION. Si APROBACION=1 el valor debe ser >=7. |
| TMP_REINTEGRO | Tiempo de reintegro | Número de meses para devolver los recursos al FOVIS | Numérico | 3 | Sí | Valor entre 1 y 999. Este dato se usa para calcular la fecha de reintegro. |
| ESTUDIO_MERCADO | Conclusiones del estudio de mercado | Resumen de max 4000 caracteres con hallazgos del estudio de demanda y oferta | Texto | 4000 | Sí | Incluir: demanda identificada, competencia, precios de mercado, viabilidad comercial. |
| EVALUACION_SOCIAL | Impacto social esperado | Análisis de cómo el proyecto mejora la calidad de vida de los afiliados | Texto | 4000 | Sí | Incluir: beneficiarios directos e indirectos, impacto social, ambiental y comunitario. |
| EVALUACION_FINANCIERA | Análisis de viabilidad financiera | Resumen del análisis financiero: ingresos, egresos, VPN, TIR, flujo de caja | Texto | 4000 | Sí | Incluir: proyección de ingresos, egresos operativos, punto de equilibrio, VPN y TIR calculados. |
| NUM_PERSONAS_REFERENCIA | Población de referencia | Número total de personas que tienen la necesidad o cumplen requisitos previos | Numérico | 10 | Sí | Valor > 0. Fuente: estudio de mercado, diagnóstico social o base de datos de afiliados. |
| NUM_POBLACION_AFECTADA | Población objetivo | Número de personas que se beneficiarán directamente con el proyecto | Numérico | 10 | Sí | Valor > 0. No puede exceder NUM_PERSONAS_REFERENCIA. |
| FECHA_DESEMBOLSO | Fecha estimada de desembolso | Fecha en formato AAAAMMDD cuando se espera recibir los recursos | Texto | 8 | Sí | Debe ser fecha futura o actual. No puede ser anterior a la fecha de aprobación. |
| FEC_REINTEGRO | Fecha estimada de reintegro | Fecha en formato AAAAMMDD calculada: FECHA_DESEMBOLSO + TMP_REINTEGRO meses | Texto | 8 | Sí | Validar que sea posterior a FECHA_DESEMBOLSO. Se calcula automáticamente = FECHA_DESEMBOLSO + (TMP_REINTEGRO * 30) días aproximadamente |

### 5.2 P-002F - CRONOGRAMA INICIAL FOVIS

**Instrucciones de llenado:** El cronograma muestra las tareas e hitos del proyecto mes por mes. Cada fila representa una actividad para un mes específico.

**⚠️ CONDICIONALIDAD CRÍTICA SEGÚN MODALIDAD_FOVIS (de P-001F):**
- Si MODALIDAD_FOVIS = 1, 3, 5 o 6 → Diligenciar SOLO PORCENTAJE_PROYECTADO (escribir 0 en los otros dos)
- Si MODALIDAD_FOVIS = 2 → Diligenciar CANTIDAD_CREDITOS_MICROCREDITOS y VALOR_CREDITOS (escribir 0 en PORCENTAJE)

| Campo | Qué debe digitar el usuario | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|----------------------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código exacto del proyecto FOVIS creado en P-001F. Ej: 01-01-00001. **No cambiar este código nunca** | Alfanumérico | 15 | Sí | Debe existir en P-001F. Si el código no existe, primero crear el proyecto en P-001F. |
| ANO_EJECUCION | Año de ejecución de la actividad (4 dígitos). Ej: 2025 para año 2025 | Numérico | 4 | Sí | Solo el año en que se proyecta la actividad. Debe estar dentro de la vigencia del proyecto. |
| MES_EJECUCION | Mes de ejecución (1-12). Tabla 68: 1=Enero, 2=Febrero, ..., 12=Diciembre | Numérico | 2 | Sí | Valor entre 1 y 12. No usar nombre del mes (escribir "1" no "Enero"). |
| PORCENTAJE_PROYECTADO | Porcentaje de ejecución proyectado para obras asociadas. Ej: 5, 25, 100 (sin el símbolo %) | Numérico | 3 | Condicional | **Obligatorio solo si MODALIDAD_FOVIS=1,3,5,6.** Valor entre 0 y 100. Si no aplica, escribir 0. |
| CANTIDAD_CREDITOS_MICROCREDITOS_PROYECTADOS | Cantidad de créditos hipotecarios/microcréditos VIS que se proyecta asignar en ese mes. Ej: 5, 10, 50 | Numérico | 5 | Condicional | **Obligatorio solo si MODALIDAD_FOVIS=2.** Valor >= 0. Si no aplica, escribir 0. |
| VALOR_CREDITOS_MICROCREDITOS_PROYECTADOS | Valor total en pesos de los créditos proyectados. Ej: 500000000 para quinientos millones. Si no hay créditos, escribir 0 | Numérico | 18 | Condicional | **Obligatorio solo si MODALIDAD_FOVIS=2.** Valor >= 0. Sin decimales ni separadores. |

### 5.3 P-005F - SOPORTES FOVIS FICHA TÉCNICA (PDF)

**Documentos requeridos:**
- Soporte Estudio de Mercado
- Soporte Evaluación Social
- Soporte Evaluación Financiera
- Soporte Justificación del Proyecto
- Soporte Petición Formal
- Soporte Acta de Aprobación - Consejo Directivo
- Soporte Certificación del Revisor Fiscal

### 5.4 P-011F - ESTRUCTURA COBERTURA PROYECTADA

**Instrucciones de llenado:** Este formato define las categorías de cobertura y la población objetivo del proyecto FOVIS. Se puede reportar una o múltiples filas según las categorías aplicables.

| Campo | Qué debe digitar el usuario | Tipo | Longitud | Obligatorio | Reglas de Validación |
|-------|----------------------------|------|----------|-------------|---------------------|
| COD_PROYECTO | Código exacto del proyecto FOVIS creado en P-001F. Ej: 01-01-00001 | Alfanumérico | 15 | Sí | Debe existir en P-001F. Usar el mismo código en todas las filas de cobertura. |
| COD_CATEGORIA | Código de la categoría según Tabla 8: 1=Contribuyente, 2=Dependiente, 3=MIXTO, 4=Todos | Numérico | 2 | Sí | Valores válidos: 1, 2, 3 o 4 según Tabla 8. No usar la descripción, solo el código numérico. |
| POBLACION_OBJETIVO | Número de soluciones de vivienda proyectadas para esta categoría. Ej: 50, 100, 200 | Numérico | 10 | Sí | Valor >= 0. Si no hay soluciones para esta categoría, escribir 0. La sumatoria de todas las categorías no puede exceder el total de soluciones del proyecto. |

### 5.5 P-012F - SEGUIMIENTO DEL PROYECTO (Trimestral)

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| ANO_EJECUCION | Año de ejecución | Numérico | 4 | Sí |
| MES_EJECUCION | Mes de ejecución (Tabla 68) | Numérico | 2 | Sí |
| TIPO_ACTIVIDAD | Código tipo actividad (Tabla 115) | Numérico | 5 | Sí |
| VALOR_EJECUTADO | Porcentaje ejecutado (código 1,3,5,6 Tabla 210) | Numérico | 18 | Condicional |
| CANTIDAD_CREDITOS_ASIGNADOS | Créditos asignados (código 2 Tabla 210) | Numérico | 5 | Condicional |
| VALOR_CREDITOS_ASIGNADOS | Valor créditos asignados (código 2 Tabla 210) | Numérico | 18 | Condicional |
| FECHA_COMPRA_LOTE | Fecha escrituración (código 4 Tabla 210) | Texto | 8 | Condicional |
| VALOR_TOTAL_COMPRA_LOTE | Valor pagado por lote (código 4 Tabla 210) | Numérico | 18 | Condicional |
| VALOR_OTROS_COSTOS | Gastos notariales y otros (código 4 Tabla 210) | Numérico | 18 | Condicional |
| COMENTARIOS | Explicación de la ejecución | Texto | 4000 | Sí |

### 5.6 P-014F - ASPECTOS ESPECÍFICOS ADQUISICIÓN/DESARROLLO VIS

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| MODALIDAD_SOLUCION_VIVIENDA | Modalidad de vivienda del proyecto | Texto | 50 | Sí |
| INTERVENTORIA_SUPERVISION | Código forma (Tabla 203) | Numérico | 1 | Sí |
| ENT_COMPETENTE | Entidad licencia (Curaduría, etc.) | Texto | 50 | Sí |
| NUM_RADICADO_LICENCIA | Número radicado solicitud licencia | Alfanumérico | 30 | Sí |
| FEC_SOLICITUD_LICENCIA | Fecha solicitud AAAAMMDD | Texto | 8 | Sí |
| NUMERO_LICENCIA | Número de licencia expedida | Alfanumérico | 30 | Sí |
| FEC_LICENCIA | Fecha licencia AAAAMMDD | Texto | 8 | Sí |
| VIGENCIA_LICENCIA | Vigencia en años | Texto | 1 | Sí |
| FEC_RADICACION_AAA | Fecha radicación acueducto AAAAMMDD | Texto | 8 | Sí |
| NUM_RADICADO_SOLICITUD_AAA | Número radicado acueducto | Alfanumérico | 30 | Sí |
| FEC_EXPEDICION_AAA | Fecha expedición acueducto AAAAMMDD | Texto | 8 | Sí |
| NUM_DISPONIBILIDAD_AAA | Número disponibilidad acueducto | Alfanumérico | 30 | Sí |
| VG_DISPONIBILIDAD_AAA | Vigencia años acueducto | Texto | 1 | Sí |
| FEC_RADICACION_EEA | Fecha radicación energía AAAAMMDD | Texto | 8 | Sí |
| NUM_RADICADO_SOLICITUD_EEA | Número radicado energía | Alfanumérico | 30 | Sí |
| FEC_EXPEDICION_EEA | Fecha expedición energía AAAAMMDD | Texto | 8 | Sí |
| NUM_DISPONIBILIDAD_EEA | Número disponibilidad energía | Alfanumérico | 30 | Sí |
| VG_DISPONIBILIDAD_EEA | Vigencia años energía | Texto | 1 | Sí |
| FEC_RADICACION_GNA | Fecha radicación gas AAAAMMDD | Texto | 8 | Sí |
| NUM_RADICADO_SOLICITUD_GNA | Número radicado gas | Alfanumérico | 30 | Sí |
| FEC_EXPEDICION_GNA | Fecha expedición gas AAAAMMDD | Texto | 8 | Sí |
| NUM_DISPONIBILIDAD_GNA | Número disponibilidad gas | Alfanumérico | 30 | Sí |
| VG_DISPONIBILIDAD_GNA | Vigencia años gas | Texto | 1 | Sí |

### 5.7 P-015F - SOPORTES ADQUISICIÓN VIS (PDF)

**Documentos requeridos:**
- Soporte Cronograma de Obra con Fecha de Inicio
- Soporte Planos Arquitectónicos
- Soporte Presupuesto de Obra
- Soporte Licencia de Construcción o Radicado
- Soporte Disponibilidad Acueducto/Alcantarillado
- Soporte Disponibilidad Energía Eléctrica
- Soporte Disponibilidad Gas Natural
- Soporte Flujo de Caja Mensualizado

### 5.8 P-022F - TIPOLOGÍA ASPECTOS ESPECÍFICOS VIS

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| COD_TIPOLOGIA | Código tipología (Tabla 208) | Numérico | 1 | Sí |
| AREA_UNIDAD_CONSTRUCCION | Área m2 con dos decimales (ej: 50.25) | Texto | 5 | Sí |
| VALOR_VENTA | Valor de venta pesos | Numérico | 18 | Sí |
| NUMERO_UNIDADES | Número de unidades | Numérico | 4 | Sí |

### 5.9 P-023F - CRÉDITOS HIPOTECARIOS/MICROCRÉDITOS

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| VLR_INDIVIDUAL_CREDITOS | Valor individual créditos | Texto | 18 | Sí |
| SISTEMA_AMORTIZACION | Sistemas de amortización aplicados | Texto | 200 | Sí |
| TAS_INTERES | Tasa interés efectiva anual | Texto | 5 | Sí |
| PUNTOS_ADICIONALES | Puntos adicionales margen intermediación | Texto | 5 | Sí |
| PLAZO_FINANCIACION | Plazo en meses | Numérico | 3 | Sí |
| REQUISITOS_GARANTIAS | Requisitos y garantías | Texto | 4000 | Sí |
| ESTRATEGIAS_RECUPERACION | Estrategias recuperación cartera, siniestralidad | Texto | 4000 | Sí |
| PROCESOS_ADMINISTRATIVOS | Procesos administrativos para subrogar recursos | Texto | 4000 | Sí |

### 5.10 P-024F - SOPORTES CRÉDITOS (PDF)

**Documentos requeridos:**
- Soporte Proyección del Plan Anual de Ejecución
- Soporte Requisitos y Garantías Crédito Hipotecario/Microcrédito
- Soporte Estrategias Recuperación Cartera, Siniestralidad y Cumplimiento Ley 546
- Soporte Procesos Administrativos y Operativos para Subrogar Recursos

### 5.11 P-028F - FINANCIACIÓN OFERENTES VIS

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| REQUISITOS_GARANTIAS | Requisitos para financiación | Texto | 4000 | Sí |
| MODALIDADES_SOLUCION_VIVIENDA | Modalidad de vivienda del proyecto | Texto | 50 | Sí |
| ESTRATEGIAS_RECUPERACION | Estrategias recuperación cartera | Texto | 4000 | Sí |
| ENT_COMPETENTE | Entidad licencia | Texto | 250 | Sí |
| NUM_RADICADO_LICENCIA | Número radicado solicitud licencia | Alfanumérico | 30 | Sí |
| FEC_SOLICITUD_LICENCIA | Fecha solicitud AAAAMMDD | Texto | 8 | Sí |
| NUMERO_LICENCIA | Número licencia | Alfanumérico | 30 | Sí |
| FEC_LICENCIA | Fecha licencia AAAAMMDD | Texto | 8 | Sí |
| VIGENCIA_LICENCIA | Vigencia años licencia | Texto | 1 | Sí |
| FEC_RADICACION_AAA | Fecha radicación acueducto AAAAMMDD | Texto | 8 | Sí |
| NUM_RADICADO_SOLICITUD_AAA | Número radicado acueducto | Alfanumérico | 30 | Sí |
| FEC_EXPEDICION_AAA | Fecha expedición acueducto AAAAMMDD | Texto | 8 | Sí |
| NUM_DISPONIBILIDAD_AAA | Número disponibilidad acueducto | Alfanumérico | 30 | Sí |
| VG_DISPONIBILIDAD_AAA | Vigencia años acueducto | Texto | 1 | Sí |
| FEC_RADICACION_EEA | Fecha radicación energía AAAAMMDD | Texto | 8 | Sí |
| NUM_RADICADO_SOLICITUD_EEA | Número radicado energía | Alfanumérico | 30 | Sí |
| FEC_EXPEDICION_EEA | Fecha expedición energía AAAAMMDD | Texto | 8 | Sí |
| NUM_DISPONIBILIDAD_EEA | Número disponibilidad energía | Alfanumérico | 30 | Sí |
| VG_DISPONIBILIDAD_EEA | Vigencia años energía | Texto | 1 | Sí |
| FEC_RADICACION_GNA | Fecha radicación gas AAAAMMDD | Texto | 8 | Sí |
| NUM_RADICADO_SOLICITUD_GNA | Número radicado gas | Alfanumérico | 30 | Sí |
| FEC_EXPEDICION_GNA | Fecha expedición gas AAAAMMDD | Texto | 8 | Sí |
| NUM_DISPONIBILIDAD_GNA | Número disponibilidad gas | Alfanumérico | 30 | Sí |
| VG_DISPONIBILIDAD_GNA | Vigencia años gas | Texto | 1 | Sí |

### 5.12 P-029F - TIPOLOGÍA OFERENTES VIS

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| COD_TIPOLOGIA | Código tipología (Tabla 208) | Numérico | 1 | Sí |
| NUMERO_UNIDADES | Número de unidades | Numérico | 4 | Sí |
| AREA_UNIDAD_CONSTRUCCION | Área m2 con dos decimales (ej: 50.25) | Texto | 5 | Sí |
| VALOR_VENTA | Valor de venta pesos | Numérico | 18 | Sí |

### 5.13 P-030F - SOPORTES OFERENTES VIS (PDF)

**Documentos requeridos:**
- Soporte Requisitos y Garantías para Otorgamiento Financiación
- Soporte Estrategias Recuperación Cartera
- Proyección del Plan Anual de Ejecución
- Soporte Licencia de Construcción o Radicado
- Soporte Disponibilidad Acueducto/Alcantarillado
- Soporte Disponibilidad Energía Eléctrica
- Soporte Disponibilidad Gas Natural
- Soporte Cronograma de Obra con Fecha de Inicio
- Soporte Presupuesto de Obra

### 5.14 P-039F - ADQUISICIÓN DE LOTES

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| AREA_LOTE | Área en m2 con dos decimales | Texto | 10 | Sí |
| VALOR_LOTE | Valor a pagar por el lote | Numérico | 18 | Sí |
| VALOR_OTROS_COSTOS | Gastos notariales y otros | Texto | 18 | Sí |
| FEC_AVALUO | Fecha avalúo AAAAMMDD | Texto | 8 | Sí |
| PERITO | Nombre del avaluador | Texto | 50 | Sí |
| NUM_REGISTRO_AVALUADOR | Número RAA | Texto | 20 | Sí |
| VALOR_AVALUO | Valor comercial del avalúo | Numérico | 18 | Sí |
| FEC_CERT_TRADICION_LIBERTAD | Fecha certificado AAAAMMDD | Texto | 8 | Sí |
| SERVICIOS_PUBLICOS | Código necesidad trámites (Tabla 204) | Numérico | 1 | Sí |
| FEC_RADICACION_AAA | Fecha radicación acueducto (código 2) | Texto | 8 | Condicional |
| NUM_RADICADO_SOLICITUD_AAA | Número radicado acueducto | Alfanumérico | 30 | Condicional |
| FEC_EXPEDICION_AAA | Fecha expedición acueducto (código 3) | Texto | 8 | Condicional |
| NUM_DISPONIBILIDAD_AAA | Número disponibilidad acueducto | Alfanumérico | 30 | Condicional |
| VG_DISPONIBILIDAD_AAA | Vigencia años acueducto | Texto | 1 | Condicional |
| FEC_RADICACION_EEA | Fecha radicación energía (código 2) | Texto | 8 | Condicional |
| NUM_RADICADO_SOLICITUD_EEA | Número radicado energía | Alfanumérico | 30 | Condicional |
| FEC_EXPEDICION_EEA | Fecha expedición energía (código 3) | Texto | 8 | Condicional |
| NUM_DISPONIBILIDAD_EEA | Número disponibilidad energía | Alfanumérico | 30 | Condicional |
| VG_DISPONIBILIDAD_EEA | Vigencia años energía | Texto | 1 | Condicional |
| FEC_RADICACION_GNA | Fecha radicación gas (código 2) | Texto | 8 | Condicional |
| NUM_RADICADO_SOLICITUD_GNA | Número radicado gas | Alfanumérico | 30 | Condicional |
| FEC_EXPEDICION_GNA | Fecha expedición gas (código 3) | Texto | 8 | Condicional |
| NUM_DISPONIBILIDAD_GNA | Número disponibilidad gas | Alfanumérico | 30 | Condicional |
| VG_DISPONIBILIDAD_GNA | Vigencia años gas | Texto | 1 | Condicional |

### 5.15 P-040F - SOPORTES ADQUISICIÓN LOTES (PDF)

**Documentos requeridos:**
- Soporte Avalúo Comercial + Certificación RAA
- Soporte Definición del Lote
- Soporte Certificado de No Riesgo y Plan de Mitigación (si aplica)
- Soporte Estudio de Títulos
- Soporte Certificado de Libertad y Tradición (vigencia <=30 días)
- Soporte de Otros Costos Asociados
- Soporte Disponibilidad Acueducto/Alcantarillado
- Soporte Disponibilidad Energía Eléctrica
- Soporte Disponibilidad Gas Natural

### 5.16 P-049F - RENOVACIÓN Y REDENSIFICACIÓN URBANA

| Campo | Descripción | Tipo | Longitud | Obligatorio |
|-------|-------------|------|----------|-------------|
| COD_PROYECTO | Código FK a P-001F | Alfanumérico | 15 | Sí |
| NUM_SOLUCIONES_VIV | Número de soluciones del proyecto | Numérico | 10 | Sí |

### 5.17 P-050F - SOPORTES RENOVACIÓN URBANA (PDF)

**Documentos requeridos:**
- Soporte Cronograma.pdf
- Soporte Presupuesto de Obra.pdf
- Soporte Flujo de Caja Mensualizado.pdf

---

## 6. MÓDULOS Y FUNCIONALIDADES

### 6.1 Módulo de Proyectos Generales

| Funcionalidad | Descripción |
|---------------|-------------|
| CRUD Proyectos | Crear, leer, actualizar, eliminar proyectos de inversión |
| Cargue de Estructuras | Cargar datos P-001A a P-056A |
| Validación de Campos | Validar según tipo, longitud y obligatoriedad |
| Cargue de PDFs | Subir documentos P-005A, P-014A, P-027A, etc. |
| Historial de Cambios | Auditoría de modificaciones |
| Reportes | Generar reportes por proyecto, por período |

### 6.2 Módulo FOVIS

| Funcionalidad | Descripción |
|---------------|-------------|
| CRUD Proyectos FOVIS | Gestión de proyectos FOVIS |
| Cargue de Estructuras FOVIS | Cargar datos P-001F a P-050F |
| Seguimiento Trimestral | Reporte trimestral P-012F |
| Cálculo de Cobertura | Proyección por categorías |
| Validaciones FOVIS | Validaciones específicas del fondo |

### 6.3 Módulo de Bienes Inmuebles

| Funcionalidad | Descripción |
|---------------|-------------|
| Gestión Arrendamientos | P-026A, P-027A |
| Gestión Comodatos | P-031A, P-032A |
| Gestión Compras | P-034A, P-035A |
| Gestión Permutas | P-040A, P-041A |
| Control de Vencimientos | Alertas de certificados vigentes |

### 6.4 Módulo de Créditos

| Funcionalidad | Descripción |
|---------------|-------------|
| Fondos de Crédito | P-023A |
| Cartera por Edades | P-024A |
| Hipotecarios/Microcréditos | P-023F |
| Financiación Oferentes | P-028F, P-029F |

### 6.5 Módulo de Reportes

| Funcionalidad | Descripción |
|---------------|-------------|
| Reporte por Proyecto | Resumen de estructura y documentos |
| Reporte por Período | Proyectos reportados en período |
| Reporte de Cumplimiento | Estado de cargue de estructuras |
| Exportación Datos | Exportar a Excel/CSV |

---

## 7. PLAN DE DESARROLLO POR FASES

### FASE 1: CONFIGURACIÓN Y ARQUITECTURA (Semanas 1-4)

**Backend Java 21:**
- [ ] Crear proyecto Spring Boot 3.x con Gradle
- [ ] Configurar estructura Hexagonal + DDD
- [ ] Configurar conexión a DB2/AS400 (jt400 driver)
- [ ] Configurar JPA con dialecto DB2AS400
- [ ] Configurar autenticación contra endpoint externo
- [ ] Crear Value Objects para fechas AAAAMMDD
- [ ] Crear enumeraciones para tablas de referencia

**Frontend Angular 21:**
- [ ] Crear proyecto Angular 21
- [ ] Configurar ESLint, Prettier
- [ ] Configurar SCSS y estructura Atomic Design CSS
- [ ] Crear carpeta shared/styles con átomos, moléculas, organismos
- [ ] Definir variables CSS globales (colores, spacing, breakpoints)
- [ ] Crear componentes atómicos base (button, input, label, badge, etc.)
- [ ] Crear componentes moleculares (form-field, card, table-row, etc.)
- [ ] Configurar interceptors HTTP
- [ ] Crear layout principal con sidebar

**Entregables F1:**
- Backend compilable y ejecutable
- Frontend ejecutable
- Documentación arquitectura

---

### FASE 2: DOMINIO Y CASOS DE USO (Semanas 5-8)

**Backend:**
- [ ] Crear dominio: Proyecto, Cronograma, ValorTotal (VOs)
- [ ] Crear dominio: ProyectoFOVIS, Seguimiento, Cobertura (VOs)
- [ ] Crear dominio: Credito, CarteraPorEdades, TasaInteres (VOs)
- [ ] Crear dominio: Arrendamiento, Compra, Comodato, Permuta (VOs)
- [ ] Crear dominio: DocumentoProyecto (Root Entity)
- [ ] Crear Repository interfaces (Puerto outbound)
- [ ] Implementar Use Cases (Puerto inbound)
- [ ] Implementar adapters JPA/DB2
- [ ] Escribir pruebas unitarias

**Estructuras de Dominio:**
- Proyecto (P-001A, P-002A, P-003A, P-011A, P-011B, P-012A)
- ProyectoFOVIS (P-001F, P-002F, P-011F, P-012F, P-014F, etc.)
- Credito (P-023A, P-024A, P-023F, P-028F, etc.)
- BienInmueble (P-026A, P-031A, P-034A, P-040A, etc.)

---

### FASE 3: CONTROLADORES REST (Semanas 9-11)

**Backend:**
- [ ] Crear ProyectoController (endpoints proyectos)
- [ ] Crear FovisController (endpoints FOVIS)
- [ ] Crear CreditoController (endpoints créditos)
- [ ] Crear BienInmuebleController (endpoints inmuebles)
- [ ] Crear DocumentoController (endpoints PDFs)
- [ ] Crear ReporteController (endpoints XML/excel)
- [ ] Implementar validación de campos
- [ ] Implementar manejo de errores
- [ ] Documentar con OpenAPI/Swagger

**Frontend:**
- [ ] Crear servicios HTTP (proyecto, fovis, credito, inmueble, documento)
- [ ] Crear componentes de lista para cada módulo
- [ ] Crear componentes de formulario para crear/editar
- [ ] Implementar reactive forms con validaciones

---

### FASE 4: GOOGLE DRIVE - PDFs (Semanas 12-14)

**Backend:**
- [ ] Configurar Google Drive API
- [ ] Implementar GoogleDriveAdapter
- [ ] Implementar upload/download de PDFs
- [ ] Implementar validación de archivos PDF
- [ ] Crear estructura de carpetas por proyecto

**Frontend:**
- [ ] Crear componente de upload de PDFs
- [ ] Implementar drag & drop
- [ ] Crear visor de PDFs (iframe Google Drive)
- [ ] Mostrar lista de documentos por proyecto

---

### FASE 5: GENERACIÓN XML Y REPORTES (Semanas 15-18)

**Backend:**
- [ ] Implementar servicio de generación XML
- [ ] Implementar validación contra XSD
- [ ] Crear endpoints para exportar cada estructura
- [ ] Implementar exportación ZIP (todos los XMLs de un proyecto)
- [ ] Implementar reportes Excel/CSV
- [ ] Crear dashboard de cumplimiento

**Frontend:**
- [ ] Crear módulo de reportes
- [ ] Implementar filtros de búsqueda
- [ ] Implementar exportación de XML
- [ ] Implementar visualización de reportes
- [ ] Crear dashboard de cumplimiento

---

### FASE 6: PRUEBAS Y DEPLOYMENT (Semanas 19-22)

**Pruebas:**
- [ ] Pruebas unitarias Backend (JUnit, Mockito) > 80%
- [ ] Pruebas de integración
- [ ] Pruebas unitarias Frontend > 80%
- [ ] Pruebas end-to-end (Cypress)
- [ ] Pruebas de validación de formularios

**Deployment:**
- [ ] Configurar Docker
- [ ] Configurar CI/CD (GitHub Actions)
- [ ] Desplegar a ambiente de pruebas
- [ ] Documentación de usuario
- [ ] Capacitación

---

## 8. ESPECIFICACIÓN DE CAMPOS POR ESTRUCTURA

### 8.1 Convenciones de Tipos de Datos

| Tipo | Formato | Validación |
|------|---------|------------|
| Numérico | Sin decimales ni separadores | Regex: ^[0-9]+$ |
| Decimal | Dos decimales separados por punto | Regex: ^[0-9]+\.[0-9]{2}$ |
| Texto | Cadena de caracteres | Longitud máxima |
| Fecha AAAAMMDD | 8 caracteres sin separadores | Regex: ^[0-9]{8}$ |

### 8.2 Formatos de Decimales

**Tasas de interés y porcentajes:**
- Formato: `XX.XX` (dos enteros, dos decimales, punto separador)
- Longitud máxima: 5 caracteres
- Ejemplos: `10.25`, `100.00`, `0.50`
- Si no hay decimales: completar con `00` (ej: `10.00`)
- Para 100%: reportar solo `100`

**Áreas:**
- Formato: `XX.XX` metros cuadrados
- Longitud máxima: 5 caracteres

### 8.3 Códigos de Proyecto

**Proyectos Generales (No FOVIS):**
```
CCFXXX-modalidad-consecutivo
Ejemplo: CCF001-01-00001
```
- CCFXXX: Código de la Caja (3 caracteres)
- modalidad: Tabla 32 (2 caracteres)
- consecutivo: Asignado por la Caja (5 caracteres)

**Proyectos FOVIS:**
```
XX-XX-XXXXX
Ejemplo: 01-01-00001
```
- XX: Código de la Caja (2 caracteres)
- XX: Actividades promoción de oferta (Tabla 88)
- XXXXX: Número consecutivo (5 caracteres)

### 8.4 Formato de Fechas

**AAAAMMDD** - 8 caracteres sin separadores:
- AAAA: Año (4 dígitos)
- MM: Mes (2 dígitos, 01-12)
- DD: Día (2 dígitos, 01-31)

**Ejemplos:**
- 20250302 = 2 de marzo de 2025
- 20251231 = 31 de diciembre de 2025

---

## 9. VALIDACIONES Y REGLAS DE NEGOCIO

### 9.1 Validaciones Comunes

| Campo | Regla de Validación |
|-------|---------------------|
| Código proyecto | Debe existir previamente en P-001A o P-001F |
| Fecha | Formato AAAAMMDD, fecha válida |
| Numérico | Sin separadores, sin decimales (excepto campos decimales) |
| Longitud | No exceder longitud máxima definida |
| Obligatorio | No puede estar vacío ni null |

### 9.2 Validaciones Específicas

**P-001A / P-001F:**
- número_consejeros >= 7 (o 1 si es resolución AEI)
- fecha_aprobacion <= fecha actual
- valor_total_proyecto > 0

**P-024A Cartera por Edades:**
- rango_edad_afiliado >= 18
- Cantidad de créditos > 0
- Valor total cartera > 0

**P-002F / P-002A Cronograma:**
- Porcentaje proyectado: 0-100
- Mes válido según Tabla 68
- Año válido: >= año actual

**P-012F Seguimiento:**
- Fecha no puede ser futura
- Tipo de actividad debe existir en Tabla 115

**Certificados PDF:**
- Vigencia máximo 30 días antes de aprobación
- Avalúo: fecha elaboración <= 1 año

### 9.3 Reglas de Condicionalidad

| Campo Condicional | Condición | Campos Obligatorios |
|-------------------|-----------|---------------------|
| Servicios públicos código 2 | Radicación trámite | Campos de radicación |
| Servicios públicos código 3 | Expedición | Campos de expedición |
| Modalidad FOVIS código 1,3,5,6 | Infraestructura | Porcentaje ejecutado |
| Modalidad FOVIS código 2 | Créditos | Cantidad y valor créditos |
| Modalidad FOVIS código 4 | Lotes | Fecha compra, valor, otros costos |
| Tiempo recuperación "no aplica" | - | No subir soporte tiempo recuperación |

---

## 10. TABLAS DE REFERENCIA

### 10.1 Tablas Mencionadas en el Documento

| Código | Nombre | Valores |
|--------|--------|---------|
| Tabla 8 | CATEGORÍA | 1-4 (A, B, C, D) |
| Tabla 32 | MODALIDAD | Códigos de modalidad de proyecto |
| Tabla 66 | MODALIDAD DE CRÉDITO SOCIAL | 1-9 |
| Tabla 68 | MES | 1-12 |
| Tabla 71 | SI / NO | 1=Si, 2=No |
| Tabla 88 | ACTIVIDADES PROMOCIÓN DE OFERTA | Códigos FOVIS |
| Tabla 98 | RANGO DE EDAD AJUSTADO | >= 18 |
| Tabla 115 | TIPOS DE ACTIVIDAD PROYECTOS | Códigos de actividad |
| Tabla 204 | LICENCIA CONSTRUCCIÓN Y/O URBANISMO O SERVICIOS PÚBLICOS | 1, 2, 3 |
| Tabla 205 | EDADES DE LA CARTERA | Códigos de edad |
| Tabla 206 | PLAZO | Códigos de plazo |
| Tabla 208 | TIPOLOGÍA SOLUCIONES DE VIVIENDA FOVIS | Códigos de tipología |
| Tabla 210 | MODALIDAD DE FOVIS | 1-6 |
| Tabla 203 | INTERVENTORÍA / SUPERVISIÓN | 1=Directa, 2=Contratada |

### 10.2 Estados de Proyecto

| Estado | Descripción |
|--------|-------------|
| BORRADOR | Proyecto en edición, no enviado |
| ENVIADO | Proyecto enviado, pendiente aprobación |
| APROBADO | Proyecto aprobado por Consejo |
| EN_EJECUCION | Proyecto en fase de ejecución |
| SEGUIMIENTO | Proyecto en seguimiento trimestral |
| FINALIZADO | Proyecto completado |
| MODIFICADO | Proyecto con modificaciones |

---

## 11. ESTRUCTURAS XML DE REPORTE (XSD SCHEMAS)

La aplicación debe generar archivos XML conforme a los XSDs definidos por Supersubsidio para el reporte de información. Cada estructura de datos tiene un XML correspondiente que se debe diligenciar y exportar.

### 11.1 Inventario Completo de Archivos XML

#### PROYECTOS GENERALES (P-001A a P-056A)

| Código | Nombre Archivo XSD | Campos | Descripción |
|--------|-------------------|--------|-------------|
| P-001A | PROYECTOS_NUEVOS_YYYYCXX | 17 | Estructuras Generales Proyectos de Inversión |
| P-002A | CRONOGRAMA_INICIAL_PROYECTO_YYYYCXX | 9 | Cronograma Inicial del Proyecto |
| P-003A | LOCALIZACION_PROYECTO_YYYYCXX | - | Localización del Proyecto |
| P-004C | ESTRUCTURA_FUENTE_RECURSOS_PROYECTO_CENTRO_COSTOS_YYYYCXX | - | Fuente de Recursos y Centro de Costos |
| P-011A | COBERTURA_PROYECTADA_YYYYCXX | - | Cobertura Proyectada |
| P-011B | ESTRUCTURA_COBERTURA_EJECUTADA_YYYYCXX | - | Cobertura Ejecutada |
| P-012A | SEGUIMIENTO_PROYECTO_YYYYCXX | - | Seguimiento del Proyecto |
| P-013A | ASPECTOS_ESPECIFICOS_PROYECTOS_INFRAESTRUCTURA_YYYYCXX | - | Aspectos Específicos Infraestructura |
| P-023A | PROYECTOS_CONSTITUCION_AMPLIACION_FONDOS_CREDITO_YYYYCXX | 9 | Fondos de Crédito |
| P-024A | CARTERA_POR_EDADES_YYYYCXX | 7 | Cartera por Edades |
| P-026A | ARRENDAMIENTO_BIENES_INMUEBLES_YYYYCXX | 9 | Arrendamiento Bienes Inmuebles |
| P-031A | ASPECTOS_ESPECIFICOS_COMODATO_BIENES_INMUEBLES_YYYYCXX | 4 | Comodato Bienes Inmuebles |
| P-034A | COMPRA_BIENES_INMUEBLES_YYYYCXX | 7 | Compra Bienes Inmuebles |
| P-040A | ASPECTOS_ESPECIFICOS_PERMUTA_BIENES_INMUEBLES_YYYYCXX | 14 | Permuta Bienes Inmuebles |
| P-047A | ASPECTOS_ESPECIFICOS_VENTA_BIENES_INMUEBLES_YYYYCXX | 9 | Venta Bienes Inmuebles |
| P-050A | ASPECTOS_NEGOCIACION_YYYYCXX | 6 | Negociación Acciones/Cuotas/Partes |
| P-055A | ESPECIFICAS_CAPITALIZACIONES_YYYYCXX | 6 | Capitalizaciones |

#### PROYECTOS FOVIS (P-001F a P-050F)

| Código | Nombre Archivo XSD | Campos | Descripción |
|--------|-------------------|--------|-------------|
| P-001F | PRINCIPAL_PROYECTOS_FOVIS_YYYYCXX | 17 | Estructura Principal FOVIS |
| P-002F | CRONOGRAMA_INICIAL_PROYECTO_FOVIS_YYYYCXX | 5 | Cronograma FOVIS |
| P-011F | COBERTURA_PROYECTADA_FOVIS_YYYYCXX | 3 | Cobertura Proyectada FOVIS |
| P-012F | SEGUIMIENTO_PROYECTO_FOVIS_YYYYCXX | 11 | Seguimiento Trimestral FOVIS |
| P-014F | ASPECTOS_ESPECIFICOS_PROYECTOS_FOVIS_YYYYCXX | 25 | Aspectos Específicos Adquisición VIS |
| P-022F | TIPOLOGIA_ASPECTOS_PROYECTOS_PROMOCION_OFERTA_FOVIS_YYYYCXX | 5 | Tipología Proyectos FOVIS |
| P-023F | PROYECTOS_FINANCIADOS_FOVIS_YYYYCXX | 9 | Créditos Hipotecarios/Microcréditos |
| P-028F | PROYECTOS_FINANCIADOS_PROMOCION_FOVIS_YYYYCXX | 22 | Financiación Oferentes VIS |
| P-029F | VALOR_VENTA_UNIDAD_FOVIS_YYYYCXX | 5 | Valor Venta Unidad FOVIS |
| P-039F | PROYECTOS_ADQUISICION_LOTES_FOVIS_YYYYCXX | 25 | Adquisición de Lotes |
| P-049F | PROYECTOS_INTEGRALES_RENOVACION_FOVIS_YYYYCXX | 2 | Renovación y Redensificación Urbana |

### 11.2 Especificación Detallada de Campos XML por Estructura

#### P-001A - PROYECTOS_NUEVOS

| Campo XML | Tipo XSD | Longitud | Descripción | Validación |
|-----------|----------|----------|-------------|------------|
| COD_PROYECTO | string | 15 | Código del proyecto | Formato: CCFXXX-Modalidad-Consecutivo |
| MOD_PROYECTO | integer | 1 | Modalidad del proyecto | Tabla 32 |
| MOD_DE_INVERSION | integer | 1 | Modalidad de inversión | - |
| VAL_TOTAL_PROYECTO | integer | 18 | Valor total del proyecto | Sin decimales, sin separadores |
| VALOR_APR_VIGENCIA | integer | 18 | Valor aprobado vigencia | Sin decimales, sin separadores |
| DESCRIPCION_PROYECTO | string | 4000 | Descripción detallada | - |
| OBJETIVO_PROYECTO | string | 4000 | Objetivo del proyecto | - |
| JUSTIFICACION | string | 4000 | Justificación del proyecto | - |
| RESOLUCION_AEI | integer | 1 | Resolución AEI (1=Si, 2=No) | Tabla 71 |
| NUM_ACTA_AEI | integer | 10 | Número de acta o resolución | - |
| FECHA_APR_AEI | string | 8 | Fecha aprobación AAAAMMDD | Formato fecha |
| NUM_CONSEJEROS | integer | 2 | Número de consejeros (>=7) | Mínimo 7 |
| TMP_RECUPERACION | integer | 3 | Tiempo recuperación meses | - |
| ESTUDIO_MERCADO | string | 4000 | Conclusiones estudio mercado | - |
| EVALUACION_SOCIAL | string | 4000 | Conclusiones evaluación social | - |
| EVALUACION_FINANCIERA | string | 4000 | Conclusiones evaluación financiera | - |
| NUM_PERSONAS_REFERENCIA | integer | 10 | Número personas referencia | - |
| NUM_POBLACION_AFECTADA | integer | 10 | Número población afectada | - |

#### P-002A - CRONOGRAMA_INICIAL_PROYECTO

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK a P-001A |
| TIPO_ACTIVIDAD | string | 5 | Tipo de actividad según Tabla 115 |
| VALOR_PROGRAMACION_PAGOS | integer | 18 | Valor programación de pagos |
| PORCENTAJE_AVANCE_FISICO | integer | 3 | Porcentaje avance físico |
| FECHA_INICIAL | string | 8 | Fecha inicial AAAAMMDD |
| FECHA_FINAL | string | 8 | Fecha final AAAAMMDD |
| DURACION_DIAS | integer | 5 | Duración en días |
| OBSERVACIONES | string | 4000 | Observaciones adicionales |

#### P-002F - CRONOGRAMA_INICIAL_PROYECTO_FOVIS

| Campo XML | Tipo XSD | Longitud | Descripción | Condicionalidad |
|-----------|----------|----------|-------------|-----------------|
| COD_PROYECTO | string | 15 | Código proyecto | Obligatorio. Debe existir en P-001F |
| ANO_EJECUCION | integer | 4 | Año de ejecución de la actividad | Obligatorio |
| MES_EJECUCION | integer | 2 | Mes de ejecución (Tabla 68) | Obligatorio (1-12) |
| PORCENTAJE_PROYECTADO | integer | 3 | Porcentaje proyectado de ejecución. Valor entero sin decimales (ej: 1, 10, 99, 100) | Solo si Modalidad FOVIS código 1, 3, 5 o 6 |
| CANTIDAD_CREDITOS_MICROCREDITOS_PROYECTADOS | integer | 5 | Cantidad de créditos hipotecarios y microcréditos VIS proyectados | Solo si Modalidad FOVIS código 2 |
| VALOR_CREDITOS_MICROCREDITOS_PROYECTADOS | integer | 18 | Valor total de créditos hipotecarios y microcréditos VIS proyectados. Sin decimales ni separadores. Si no hay pagos, $0 | Solo si Modalidad FOVIS código 2 |

#### P-023A - PROYECTOS_CONSTITUCION_AMPLIACION_FONDOS_CREDITO

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| MODALIDAD_CREDITO | integer | 1 | Modalidad crédito (Tabla 66) |
| COD_CATEGORIA | integer | 2 | Categoría (Tabla 8) |
| TASA_INTERES_MINIMA | string | 5 | Tasa mínima (ej: 10.25) |
| TASA_INTERES_MAXIMA | string | 5 | Tasa máxima (ej: 10.25) |
| CANT_CREDITOS | integer | 10 | Cantidad de créditos |
| VAL_MONTO_CREDITOS | integer | 18 | Valor monto créditos |
| PLAZO_CREDITO | integer | 2 | Plazo en meses (Tabla 206) |
| PORCENTAJE_SUBSIDIO | string | 5 | Porcentaje subsidio (ej: 10.25) |

#### P-024A - CARTERA_POR_EDADES

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| RAN_EDAD | integer | 2 | Rango edad afiliado (>=18) |
| EDAD_CARTERA | integer | 1 | Edad cartera (Tabla 205) |
| MODALIDAD_CREDITO | integer | 1 | Modalidad crédito (Tabla 66) |
| COD_CATEGORIA | integer | 2 | Categoría (Tabla 8) |
| CANT_CREDITOS | integer | 10 | Cantidad créditos activos |
| VALOR_TOTAL_MONTO_CARTERA | integer | 18 | Valor total cartera |

#### P-026A - ARRENDAMIENTO_BIENES_INMUEBLES

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| FEC_CERT_TRADICION_LIBERTAD | string | 8 | Fecha certificado AAAAMMDD |
| FEC_AVALUO | string | 8 | Fecha avalúo AAAAMMDD |
| PERITO | string | 50 | Nombre del perito avaluador |
| VALOR_AVALUO | integer | 18 | Valor del avalúo |
| VAL_CANON_MENSUAL | integer | 18 | Valor canon mensual |
| TMP_CONTRATO | integer | 3 | Tiempo contrato meses |
| DESTINACION_INMUEBLE | string | 50 | Destinación del inmueble |
| USO_AUTORIZADO | string | 50 | Uso de suelo autorizado |

#### P-034A - COMPRA_BIENES_INMUEBLES

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| FEC_CERT_TRADICION_LIBERTAD | string | 8 | Fecha certificado AAAAMMDD |
| FEC_AVALUO | string | 8 | Fecha avalúo AAAAMMDD |
| PERITO | string | 50 | Nombre del perito avaluador |
| VALOR_AVALUO | integer | 18 | Valor del avalúo |
| DESTINACION_INMUEBLE | string | 50 | Destinación del inmueble |
| USO_AUTORIZADO | string | 50 | Uso de suelo autorizado |

#### P-050A / P-055A - NEGOCIACIÓN / CAPITALIZACIONES

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| NUM_ACCIONES_CUOTAS | integer | 10 | Número de acciones/cuotas |
| VALOR_ACCIONES_CUOTAS | integer | 18 | Valor de las acciones/cuotas |
| PORCENTAJE_PARTICIPACION | string | 5 | Porcentaje participación (ej: 10.25) |
| VALOR_NOMINAL_ACCIONES | integer | 18 | Valor nominal certificado |
| VALOR_MERCADO_ACCIONES | integer | 18 | Valor mercado certificado |

#### P-001F - PRINCIPAL_PROYECTOS_FOVIS

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código: XX-XX-XXXXX |
| VAL_TOTAL_PROYECTO | integer | 18 | Valor total proyecto |
| MODALIDAD_FOVIS | string | 2 | Modalidad FOVIS (Tabla 210) |
| DESCRIPCION_PROYECTO | string | 4000 | Descripción detallada |
| OBJETIVO_PROYECTO | string | 4000 | Objetivo general y específicos |
| JUSTIFICACION | string | 4000 | Justificación del proyecto |
| APROBACION | integer | 1 | Aprobación AEI (1=Si, 2=No) |
| NUM_ACTA_AEI | integer | 10 | Número acta/resolución |
| FECHA_APR_AEI | string | 8 | Fecha aprobación AAAAMMDD |
| NUM_CONSEJEROS | integer | 2 | Número consejeros (>=7) |
| TMP_REINTEGRO | integer | 3 | Tiempo reintegro meses |
| ESTUDIO_MERCADO | string | 4000 | Conclusiones estudio mercado |
| EVALUACION_SOCIAL | string | 4000 | Conclusiones evaluación social |
| EVALUACION_FINANCIERA | string | 4000 | Conclusiones evaluación financiera |
| NUM_PERSONAS_REFERENCIA | integer | 10 | Personas referencia |
| NUM_POBLACION_AFECTADA | integer | 10 | Población afectada |
| FECHA_DESEMBOLSO | string | 8 | Fecha desembolso AAAAMMDD |
| FEC_REINTEGRO | string | 8 | Fecha reintegro AAAAMMDD |

#### P-012F - SEGUIMIENTO_PROYECTO_FOVIS

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| ANO_EJECUCION | integer | 4 | Año de ejecución |
| MES_EJECUCION | integer | 2 | Mes de ejecución |
| TIPO_ACTIVIDAD | string | 5 | Tipo actividad (Tabla 115) |
| VALOR_EJECUTADO | string | 18 | Porcentaje ejecutado |
| CANTIDAD_CREDITOS | string | 5 | Cantidad créditos asignados |
| VALOR_CREDITO_ACUMULADO | string | 18 | Valor crédito acumulado |
| FECHA_COMPRA | string | 8 | Fecha compra lote AAAAMMDD |
| VALOR_COMPRA | string | 18 | Valor de la compra |
| VALOR_OTROS_COSTOS | string | 18 | Otros costos adquisición |
| COMENTARIO_CUMPLIMIENTO | string | 4000 | Comentarios ejecución |

#### P-014F - ASPECTOS_ESPECIFICOS_PROYECTOS_FOVIS

| Campo XML | Tipo XSD | Longitud | Descripción |
|-----------|----------|----------|-------------|
| COD_PROYECTO | string | 15 | Código FK proyecto |
| MODALIDAD_SOLUCION_VIVIENDA | string | 50 | Modalidad solución vivienda |
| INTERVENTORIA_SUPERVISION | integer | 1 | Forma interventoría (Tabla 203) |
| ENT_COMPETENTE | string | 50 | Entidad competente |
| NUM_RADICADO_LICENCIA | string | 30 | Número radicado licencia |
| FEC_SOLICITUD_LICENCIA | string | 8 | Fecha solicitud licencia AAAAMMDD |
| NUMERO_LICENCIA | string | 30 | Número de licencia |
| FEC_LICENCIA | string | 8 | Fecha licencia AAAAMMDD |
| VIGENCIA_LICENCIA | string | 1 | Vigencia licencia años |
| FEC_RADICACION_AAA | string | 8 | Fecha radicación acueducto |
| NUM_RADICADO_SOLICITUD_AAA | string | 30 | Número radicado acueducto |
| FEC_EXPEDICION_AAA | string | 8 | Fecha expedición acueducto |
| NUM_DISPONIBILIDAD_AAA | string | 30 | Número disponibilidad acueducto |
| VG_DISPONIBILIDAD_AAA | string | 1 | Vigencia acueducto años |
| FEC_RADICACION_EEA | string | 8 | Fecha radicación energía |
| NUM_RADICADO_SOLICITUD_EEA | string | 30 | Número radicado energía |
| FEC_EXPEDICION_EEA | string | 8 | Fecha expedición energía |
| NUM_DISPONIBILIDAD_EEA | string | 30 | Número disponibilidad energía |
| VG_DISPONIBILIDAD_EEA | string | 1 | Vigencia energía años |
| FEC_RADICACION_GNA | string | 8 | Fecha radicación gas |
| NUM_RADICADO_SOLICITUD_GNA | string | 30 | Número radicado gas |
| FEC_EXPEDICION_GNA | string | 8 | Fecha expedición gas |
| NUM_DISPONIBILIDAD_GNA | string | 30 | Número disponibilidad gas |
| VG_DISPONIBILIDAD_GNA | string | 1 | Vigencia gas años |

### 11.3 Servicio de Generación de XML

```java
// Puerto outbound para generación de XML
public interface XmlGeneracionPort {
    byte[] generarXml(EstructuraReporte estructura, byte[] xsdSchema);
    List<XmlReporte> generarTodosLosXmlsDelProyecto(String codigoProyecto);
    boolean validarXmlContraXsd(byte[] xml, byte[] xsd);
}
```

```java
// Servicio de dominio para generación de XML
public class XmlGeneracionDomainService {
    
    public XmlReporte generarXmlProyecto(Proyecto proyecto, TipoEstructura tipo) {
        // 1. Obtener XSD correspondiente al tipo de estructura
        XsdSchema schema = xsdRepository.obtenerSchema(tipo);
        
        // 2. Construir documento XML con los datos del proyecto
        XmlDocumento documento = construirDocumento(proyecto, schema);
        
        // 3. Validar contra XSD
        if (!validarContraXsd(documento, schema)) {
            throw new XmlValidacionException("El XML no cumple con el esquema XSD");
        }
        
        // 4. Serializar y retornar
        return documento.serializar();
    }
}
```

### 11.4 Endpoint REST para Exportar XML

```java
@RestController
@RequestMapping("/api/v1/xml")
public class XmlReporteController {
    
    @GetMapping("/proyecto/{codigo}/estructura/{tipo}")
    public ResponseEntity<byte[]> exportarXml(
            @PathVariable String codigo,
            @PathVariable TipoEstructura tipo) {
        
        byte[] xml = xmlUseCase.generarXml(codigo, tipo);
        
        return ResponseEntity.ok()
            .header("Content-Type", "application/xml")
            .header("Content-Disposition", 
                "attachment; filename=" + generarNombreArchivo(tipo))
            .body(xml);
    }
    
    @GetMapping("/proyecto/{codigo}/todos")
    public ResponseEntity<byte[]> exportarTodosLosXmls(
            @PathVariable String codigo) {
        
        List<XmlReporte> xmls = xmlUseCase.generarTodosLosXmls(codigo);
        
        // Generar ZIP con todos los XMLs
        byte[] zip = zipService.comprimir(xmls);
        
        return ResponseEntity.ok()
            .header("Content-Type", "application/zip")
            .header("Content-Disposition", 
                "attachment; filename=proyecto_" + codigo + "_xmls.zip")
            .body(zip);
    }
}
```

### 11.5 Nomenclatura de Archivos XML Exportados

```
{CodigoCaja}_{CodigoEstructura}_{Periodo}{Consecutivo}.xml

Ejemplos:
CCF001_P-001A_202503.xml
CCF001_P-002A_202503.xml
CCF001_P-001F_202503.xml
```

### 11.6 Validación de XML contra XSD

```java
public class XmlValidationService {
    
    public ValidationResult validar(byte[] xml, byte[] xsd) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new ByteArrayInputStream(xsd));
            Validator validator = schema.newValidator();
            
            validator.validate(new StreamSource(new ByteArrayInputStream(xml)));
            
            return ValidationResult.success();
        } catch (SAXException e) {
            return ValidationResult.failure(e.getMessage());
        }
    }
}
```

### 11.7 Mapeo XSD → Dominio

| XSD File | Aggregate DDD | Root Entity |
|----------|--------------|-------------|
| PROYECTOS_NUEVOS | ProyectoAggregate | Proyecto |
| CRONOGRAMA_INICIAL_PROYECTO | ProyectoAggregate | Cronograma |
| PROYECTOS_CONSTITUCION_AMPLIACION_FONDOS_CREDITO | CreditoAggregate | Credito |
| CARTERA_POR_EDADES | CreditoAggregate | CarteraPorEdades |
| ARRENDAMIENTO_BIENES_INMUEBLES | BienInmuebleAggregate | Arrendamiento |
| COMPRA_BIENES_INMUEBLES | BienInmuebleAggregate | CompraInmueble |
| PRINCIPAL_PROYECTOS_FOVIS | ProyectoFOVISAggregate | ProyectoFOVIS |
| SEGUIMIENTO_PROYECTO_FOVIS | ProyectoFOVISAggregate | Seguimiento |
| ASPECTOS_NEGOCIACION | AccionesAggregate | NegociacionAcciones |
| ESPECIFICAS_CAPITALIZACIONES | AccionesAggregate | Capitalizacion |

---

## 12. INTEGRACIÓN GOOGLE DRIVE

### 12.1 Configuración de Google Drive API

```yaml
# application.yml - Google Drive Configuration
spring:
  google:
    drive:
      credentials-path: ${GOOGLE_CREDENTIALS_PATH:/config/google-credentials.json}
      folder-id: ${GOOGLE_DRIVE_FOLDER_ID:}
      api-key: ${GOOGLE_API_KEY:}
```

### 12.2 Dependencias Maven

```xml
<!-- Google Drive API -->
<dependency>
    <groupId>com.google.api-client</groupId>
    <artifactId>google-api-client</artifactId>
    <version>2.2.0</version>
</dependency>
<dependency>
    <groupId>com.google.apis</groupId>
    <artifactId>google-api-services-drive</artifactId>
    <version>v3-rev20240521-2.0.0</version>
</dependency>
<dependency>
    <groupId>com.google.auth</groupId>
    <artifactId>google-auth-library-oauth2-http</artifactId>
    <version>1.23.0</version>
</dependency>
```

### 12.3 Estructura de Carpetas en Google Drive

```
CCF-GestionProyectos/
├── 2025/
│   ├── P-001A/
│   │   ├── CCF001_P-001A_202503.pdf
│   │   └── CCF001_P-005A_FichaTecnica_202503.pdf
│   ├── P-014A/
│   │   └── CCF001_P-014A_Infraestructura_202503.pdf
│   └── ...
└── ...
```

### 12.4 GoogleDriveAdapter (Outbound Port Implementation)

```java
package com.ccf.gestionproyectos.infrastructure.adapter.storage.googledrive;

@Component
@RequiredArgsConstructor
public class GoogleDriveAdapter implements DocumentoStoragePort {
    
    private final Drive driveService;
    private final String rootFolderId;
    
    @Override
    public String uploadPdf(DocumentoProyecto documento, byte[] contenido) {
        // 1. Crear/carpeta del proyecto si no existe
        String projectFolderId = ensureProjectFolder(documento.getCodigoProyecto());
        
        // 2. Crear nombre de archivo: {codigo}_{tipoDocumento}_{fecha}.pdf
        String fileName = generarNombreArchivo(documento);
        
        // 3. Subir archivo a Google Drive
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(List.of(projectFolderId));
        
        InputStreamContent mediaContent = new InputStreamContent(
            "application/pdf",
            new ByteArrayInputStream(contenido)
        );
        
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
            .setFields("id, name, webViewLink")
            .execute();
        
        return uploadedFile.getId();
    }
    
    @Override
    public byte[] downloadPdf(String googleFileId) {
        return driveService.files().get(googleFileId)
            .executeMediaAsInputStream().readAllBytes();
    }
    
    @Override
    public String getViewerUrl(String googleFileId) {
        return driveService.files().get(googleFileId)
            .setFields("webViewLink")
            .execute().getWebViewLink();
    }
    
    private String ensureProjectFolder(String codigoProyecto) {
        // Verificar si existe carpeta, crear si no existe
        // Estructura: CCF-CODIGO/2025/P-001A/
    }
}
```

### 12.5 Puerto (Interface) para Almacenamiento

```java
package com.ccf.gestionproyectos.application.ports.outbound;

public interface DocumentoStoragePort {
    String uploadPdf(DocumentoProyecto documento, byte[] contenido);
    byte[] downloadPdf(String googleFileId);
    String getViewerUrl(String googleFileId);
    void deletePdf(String googleFileId);
    List<DocumentoProyecto> listarDocumentosProyecto(String codigoProyecto);
}
```

### 12.6 Endpoint REST para PDFs

```java
@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {
    
    @PostMapping("/upload/{codigoProyecto}/{tipoDocumento}")
    public ResponseEntity<DocumentoResponse> uploadPdf(
            @PathVariable String codigoProyecto,
            @PathVariable String tipoDocumento,
            @RequestParam("file") MultipartFile file) {
        
        // Validar que sea PDF
        if (!file.getContentType().equals("application/pdf")) {
            throw new InvalidDocumentTypeException("Solo se permiten archivos PDF");
        }
        
        // Validar tamaño máximo (50MB)
        if (file.getSize() > 50 * 1024 * 1024) {
            throw new DocumentSizeExceededException("El archivo excede 50MB");
        }
        
        DocumentoResponse response = documentoUseCase.cargarDocumento(
            codigoProyecto, tipoDocumento, file
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/proyecto/{codigoProyecto}")
    public ResponseEntity<List<DocumentoResponse>> listarDocumentos(
            @PathVariable String codigoProyecto) {
        return ResponseEntity.ok(documentoUseCase.listarDocumentos(codigoProyecto));
    }
    
    @GetMapping("/{googleFileId}/viewer")
    public ResponseEntity<String> obtenerViewerUrl(@PathVariable String googleFileId) {
        String url = documentoUseCase.obtenerViewerUrl(googleFileId);
        return ResponseEntity.ok().body("{\"viewerUrl\": \"" + url + "\"}");
    }
}
```

### 12.7 Modelos de Dominio para Documentos

```java
// Value Object para tipo de documento
public enum TipoDocumento {
    P_005A_FICHA_TECNICA("P-005A", "Ficha Técnica"),
    P_014A_INFRAESTRUCTURA("P-014A", "Soportes Infraestructura"),
    P_027A_ARRENDAMIENTO("P-027A", "Soportes Arrendamiento"),
    P_032A_COMODATO("P-032A", "Soportes Comodato"),
    P_035A_COMPRA("P-035A", "Soportes Compra"),
    P_041A_PERMUTA("P-041A", "Soportes Permuta"),
    P_051A_NEGOCIACION("P-051A", "Soportes Negociación"),
    P_056A_CAPITALIZACIONES("P-056A", "Soportes Capitalizaciones"),
    P_005F_FICHA_FOVIS("P-005F", "Soportes FOVIS Ficha Técnica"),
    P_015F_VIS("P-015F", "Soportes Adquisición VIS"),
    // ... más tipos
}

// Root Entity Documento
public class DocumentoProyecto {
    private final DocumentoId id;
    private final CodigoProyecto codigoProyecto;
    private final TipoDocumento tipoDocumento;
    private final String googleFileId;
    private final String nombreArchivo;
    private final long tamanhoBytes;
    private final LocalDateTime fechaCargue;
    private final String hashSHA256;
}
```

---

## 13. CONSIDERACIONES ESPECÍFICAS DB2/AS400

### 13.1 Configuración de Conexión

```yaml
# application.yml - Spring Boot
spring:
  datasource:
    url: jdbc:as400://{servidor_AS400}:{puerto}/{biblioteca}
    username: {usuario_AS400}
    password: {contraseña_AS400}
    driver-class-name: com.ibm.as400.access.AS400JDBCDriver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000

  jpa:
    database-platform: org.hibernate.dialect.DB2AS400Dialect
    properties:
      hibernate:
        format_sql: true
        jdbc:
          time-zone: America/Bogota
```

### 13.2 Dependencias Maven

```xml
<!-- IBM AS400 JDBC Driver -->
<dependency>
    <groupId>com.ibm.as400</groupId>
    <artifactId>jt400</artifactId>
    <version>12.0.0</version>
</dependency>

<!-- Hibernate DB2 Dialect -->
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
</dependency>
```

### 13.3 Consideraciones de Diseño para DB2/AS400

| Aspecto | Recomendación |
|---------|---------------|
| **Nomenclatura** | Usar convención AS400: nombres en mayúscula, máximo 10 caracteres para archivos físicos |
| **Esquemas** | Utilizar bibliotecas AS400 como esquemas (Libraries) |
| **Indices** | Crear índices para campos frecuentemente consultados (codigo_proyecto, estado) |
| **Secuencias** | Usar generadores AS400 (OVERRIDING USER DEFAULTS) |
| **Campos fecha** | Manejar como DATE AS400, evitar TIMESTAMP |
| **Campos numéricos** | Definir precisión adecuada (DECIMAL) |
| **Transacciones** | Configurar commit explícito según necesidades |
| **Nombres archivos físicos** | Máximo 10 caracteres: `P001A`, `P002A`, `PF001F` |

### 13.4 Estructura de Bibliotecas AS400 Sugerida

```
Biblioteca: CCFPROY
├── Archivos Físicos (Tablas)
│   ├── P001A (Estructuras Generales)
│   ├── P002A (Cronograma)
│   ├── P023A (Fondos Crédito)
│   ├── P024A (Cartera Edades)
│   ├── P026A (Arrendamiento)
│   ├── P031A (Comodato)
│   ├── P034A (Compra Inmuebles)
│   ├── P040A (Permuta)
│   ├── P050A (Negociación Acciones)
│   ├── P055A (Capitalizaciones)
│   ├── PF001F (Proyectos FOVIS)
│   ├── PF002F (Cronograma FOVIS)
│   ├── PF011F (Cobertura Proyectada)
│   ├── PF012F (Seguimiento)
│   ├── PF014F (Adquisición VIS)
│   ├── PF022F (Tipología VIS)
│   ├── PF023F (Créditos Hipotecarios)
│   ├── PF028F (Financiación Oferentes)
│   ├── PF039F (Adquisición Lotes)
│   └── PF049F (Renovación Urbana)
│
├── Archivos Lógicos (Vistas/Índices)
│   ├── P001AL0 (Por código proyecto)
│   ├── P001AL1 (Por estado)
│   └── PF001FL0 (Por modalidad FOVIS)
│
└── Programas (SPs/Funciones)
    ├── VALIDAR_CODIGO (Validación código proyecto)
    └── GEN_CONSECUTIVO (Generación consecutivos)
```

### 13.5 Mapeo Tipos de Datos DB2/AS400

| Tipo Java | Tipo DB2/AS400 | Longitud |
|-----------|----------------|----------|
| String (texto) | VARCHAR | Según campo |
| Long | DECIMAL(18,0) | 18 dígitos |
| Integer | DECIMAL(10,0) | 10 dígitos |
| BigDecimal | DECIMAL(18,2) | 18 dígitos, 2 decimales |
| LocalDate | DATE | Fecha |
| Boolean | CHAR(1) | 1 carácter |
| Double | DOUBLE | 8 bytes |

### 13.6 Validaciones SQL específicas AS400

```sql
-- Validar formato código proyecto (ej: CCF001-01-00001)
WHERE CODIGO_PROYECTO LIKE 'CCF%-%-%'
  AND LENGTH(TRIM(CODIGO_PROYECTO)) = 15

-- Validar fecha formato AAAAMMDD
WHERE FECHA_APROBACION BETWEEN '19000101' AND '20991231'

-- Validar número consejeros >= 7
WHERE NUMERO_CONSEJEROS >= 7

-- Historial de cambios ( JOURNALING )
-- Habilitar journaling en tablas para auditoría
```

### 13.7 Consideraciones de Rendimiento

1. **Join con archivos lógicos** - Utilizar archivos lógicos para optimizar joins
2. **Query Optimization** - Evitar SELECT *; especificar campos
3. **Bloqueo de registros** - Usar COMMIT automáticamente
4. **OLAP vs OLTP** - Separar cargas de reportes (Query Manager)
5. **Nombres DDS** - Mantener compatibilidad con nombres Java

### 13.8 Integración con Sistemas Legados

```
┌─────────────────────────────────────────────────────────┐
│                 FRONTEND ANGULAR 21                      │
└─────────────────────────────────────────────────────────┘
                            │ REST API
                            ▼
┌─────────────────────────────────────────────────────────┐
│              BACKEND JAVA 21 (Spring Boot)               │
│  ┌────────────────────────────────────────────────────┐ │
│  │              Data Access Layer                      │ │
│  │  ┌──────────┐  ┌──────────┐  ┌──────────┐         │ │
│  │  │  JPA     │  │  JDBC    │  │  MyBatis │         │ │
│  │  │(Simple)  │  │(Complex) │  │(Reports) │         │ │
│  │  └──────────┘  └──────────┘  └──────────┘         │ │
│  └────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────┘
                            │
              ┌─────────────┼─────────────┐
              ▼             ▼             ▼
        ┌──────────┐  ┌──────────┐  ┌──────────┐
        │   DB2    │  │   RPG    │  │  COBOL   │
        │  AS400   │  │ Programs │  │ Programs │
        │ (SQL)    │  │ (LE)     │  │ (LE)     │
        └──────────┘  └──────────┘  └──────────┘
```

---

## ANEXO: MODELO DE DATOS CONCEPTUAL

### Entidades Principales

```
Proyecto (abstract)
├── id: Long
├── codigo: String (único)
├── nombre: String
├── descripcion: String
├── estado: EnumEstado
├── fechaCreacion: Date
├── fechaActualizacion: Date
│
├── ProyectoInversion (P-001A)
│   ├── modalidad: String
│   ├── numeroActa: String
│   ├── fechaActa: LocalDate
│   ├── numeroConsejeros: Integer
│   ├── valorTotal: BigDecimal
│   ├── origenRecursos: String
│   └── tiempoRecuperacion: Integer
│
├── ProyectoFOVIS (P-001F)
│   ├── modalidadFOVIS: String
│   ├── aprobacion: Boolean
│   ├── numeroActaResolucion: String
│   ├── numeroConsejeros: Integer
│   ├── tiempoReintegro: Integer
│   ├── conclusionesEstudioMercado: String
│   ├── conclusionesEvaluacionSocial: String
│   └── conclusionesEvaluacionFinanciera: String
│
├── Cronograma (abstract)
│   ├── proyecto: Proyecto
│   ├── ano: Integer
│   └── mes: Integer
│
├── CronogramaInversion (P-002A)
│   └── porcentajeProyectado: Integer
│
├── CronogramaFOVIS (P-002F)
│   ├── porcentajeProyectado: Integer
│   ├── cantidadCreditos: Integer
│   └── valorCreditos: BigDecimal
│
├── CarteraPorEdades (P-024A)
│   ├── proyecto: ProyectoInversion
│   ├── rangoEdadAfiliado: Integer
│   ├── edadesCartera: String
│   ├── modalidadCredito: String
│   ├── categoria: String
│   ├── cantidadCreditos: Integer
│   └── valorTotalCartera: BigDecimal
│
├── BienInmueble (abstract)
│   ├── proyecto: ProyectoInversion
│   ├── fechaCertificadoLibertad: LocalDate
│   ├── destinacion: String
│   └── usoAutorizado: String
│
├── Arrendamiento (P-026A)
│   ├── fechaAvaluo: LocalDate
│   ├── peritoAvaluador: String
│   ├── valorComercialCanon: BigDecimal
│   ├── valorCanonMensual: BigDecimal
│   └── tiempoContrato: Integer
│
├── Comodato (P-031A)
│
├── CompraInmueble (P-034A)
│   ├── fechaAvaluo: LocalDate
│   ├── peritoAvaluador: String
│   └── valorComercial: BigDecimal
│
├── Permuta (P-040A)
│   ├── inmuebleRecibe: InmuebleInfo
│   ├── inmuebleEntrega: InmuebleInfo
│   └── utilidadPerdida: BigDecimal
│
├── NegociacionAcciones (P-050A)
│   ├── numeroAcciones: Integer
│   ├── valorAcciones: BigDecimal
│   ├── porcentajeParticipacion: BigDecimal
│   ├── valorNominal: BigDecimal
│   └── valorMercado: BigDecimal
│
├── Capitalizacion (P-055A)
│   └── [campos similares a NegociacionAcciones]
│
├── CoberturaProyectada (P-011F)
│   ├── proyectoFOVIS: ProyectoFOVIS
│   ├── categoria: String
│   └── numeroSoluciones: Integer
│
├── SeguimientoProyecto (P-012F)
│   ├── proyectoFOVIS: ProyectoFOVIS
│   ├── tipoActividad: String
│   ├── valorEjecutado: BigDecimal
│   ├── cantidadCreditos: Integer
│   └── comentarios: String
│
├── AdquisicionVIS (P-014F)
│   ├── proyectoFOVIS: ProyectoFOVIS
│   ├── modalidadesSolucionVivienda: String
│   ├── interventoriaSupervision: String
│   └── [campos licencia y servicios públicos]
│
├── TipologiaVIS (P-022F)
│   ├── proyectoFOVIS: ProyectoFOVIS
│   ├── tipologia: String
│   ├── areaPorUnidad: BigDecimal
│   ├── valorVenta: BigDecimal
│   └── numeroUnidades: Integer
│
├── CreditosHipotecarios (P-023F)
│   ├── proyectoFOVIS: ProyectoFOVIS
│   ├── valorIndividual: BigDecimal
│   ├── sistemasAmortizacion: String
│   ├── tasasInteres: BigDecimal
│   └── [demas campos]
│
├── FinanciacionOferentes (P-028F)
│   └── [similar a AdquisicionVIS]
│
├── AdquisicionLotes (P-039F)
│   ├── proyectoFOVIS: ProyectoFOVIS
│   ├── areaLote: BigDecimal
│   ├── valorLote: BigDecimal
│   └── [demas campos]
│
└── RenovacionUrbana (P-049F)
    ├── proyectoFOVIS: ProyectoFOVIS
    └── numeroSoluciones: Integer

DocumentoProyecto
├── id: Long
├── proyecto: Proyecto
├── tipoDocumento: String (P-005A, P-014A, etc.)
├── nombreArchivo: String
├── rutaAlmacenamiento: String
├── hashArchivo: String
├── fechaCargue: LocalDateTime
├── tamanhoBytes: Long
└── numeroVersion: Integer
```

---

## CHECKLIST DE IMPLEMENTACIÓN

### Backend
- [ ] Spring Boot 3.x con Java 21 configurado
- [ ] Arquitectura Hexagonal + DDD implementada
- [ ] DB2/AS400 conectado (jt400 driver)
- [ ] Dominio completo (Proyecto, FOVIS, Credito, BienInmueble)
- [ ] Use Cases implementados (todos los módulos)
- [ ] Controllers REST implementados
- [ ] Google Drive Adapter implementado
- [ ] Auth externo configurado
- [ ] Generación XML implementada
- [ ] Validación XSD implementada
- [ ] Pruebas unitarias > 80% coverage

### Frontend
- [ ] Angular 21 proyecto creado
- [ ] Atomic Design CSS implementado (sin inline styles)
- [ ] Componentes atómicos y moleculares creados
- [ ] Módulo proyectos implementado
- [ ] Módulo FOVIS implementado
- [ ] Módulo créditos implementado
- [ ] Módulo inmuebles implementado
- [ ] Módulo documentos implementado
- [ ] Módulo reportes implementado
- [ ] Dashboard de cumplimiento
- [ ] Pruebas unitarias > 80% coverage

---

*Documento generado para el desarrollo del Sistema de Gestión de Proyectos*
*Versión 1.0 - Mayo 2025*