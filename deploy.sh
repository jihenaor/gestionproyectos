#!/bin/bash
# Script de despliegue de Gestion de Proyectos.
# Soporta: modo CI (--ci / $CI), menú interactivo y argumentos en línea.

set -e

SERVICIOS_VALIDOS="backend frontend"

mostrar_menu_interactivo() {
    echo ""
    echo "========================================================="
    echo " ¿Qué desea desplegar?"
    echo "========================================================="
    echo "  1) Todos los servicios"
    echo ""
    echo "  Servicios individuales:"
    local i=2
    for s in $SERVICIOS_VALIDOS; do
        echo "  $i) $s"
        i=$((i + 1))
    done
    local max_opcion=$((i - 1))
    echo ""
    echo "  0) Salir sin desplegar"
    echo "========================================================="
    echo -n " Elija una opción [0-$max_opcion]: "
}

elegir_por_menu() {
    local opcion
    read -r opcion
    if [ -z "$opcion" ]; then
        echo "Opción vacía. Desplegando todos los servicios."
        DESPLIEGUE_PARCIAL=false
        return
    fi
    if [ "$opcion" = "0" ]; then
        echo "Salida sin desplegar."
        exit 0
    fi
    if [ "$opcion" = "1" ]; then
        DESPLIEGUE_PARCIAL=false
        return
    fi
    local i=2
    for s in $SERVICIOS_VALIDOS; do
        if [ "$opcion" = "$i" ]; then
            DESPLIEGUE_PARCIAL=true
            SERVICIOS_A_DESPLEGAR="$s"
            return
        fi
        i=$((i + 1))
    done
    echo "Opción no válida ($opcion). Desplegando todos los servicios."
    DESPLIEGUE_PARCIAL=false
}

mostrar_uso() {
    echo "Uso: $0 [--ci] [SERVICIO1 [SERVICIO2 ...]]"
    echo ""
    echo "  --ci           Modo CI: despliegue completo sin menú ni git pull."
    echo "  Sin argumentos: muestra un menú para elegir desplegar todo o un servicio."
    echo "  Con argumentos: despliega solo los servicios indicados."
    echo ""
    echo "Servicios disponibles:"
    for s in $SERVICIOS_VALIDOS; do
        echo "  - $s"
    done
    echo ""
    echo "Ejemplos:"
    echo "  $0                          # Menú interactivo"
    echo "  $0 --ci                     # Despliegue completo (CI)"
    echo "  $0 frontend                 # Solo el frontend"
    echo "  $0 backend frontend         # Backend y frontend"
    exit 0
}

if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    mostrar_uso
fi

# ── Modo CI ───────────────────────────────────────────────────────────────────
if [ -n "$CI" ] || [ "$1" = "--ci" ]; then
    [ "$1" = "--ci" ] && shift
    echo "========================================================="
    echo " Despliegue en modo CI (no interactivo)"
    echo "========================================================="
    if [ -f ".changed_services" ]; then
        DETECTED=$(cat .changed_services | xargs)
        echo " Servicios leídos del artefacto CI: '${DETECTED}'"
    elif [ -f "scripts/detect-changed-services.sh" ]; then
        chmod +x scripts/detect-changed-services.sh 2>/dev/null || true
        DETECTED=$(./scripts/detect-changed-services.sh 2>/dev/null || echo "")
        echo " Servicios detectados: '${DETECTED}'"
    else
        DETECTED=""
    fi
    if [ -n "$DETECTED" ]; then
        DESPLIEGUE_PARCIAL=true
        SERVICIOS_A_DESPLEGAR="$DETECTED"
        echo " Servicios a desplegar: $SERVICIOS_A_DESPLEGAR"
    else
        DESPLIEGUE_PARCIAL=false
        SERVICIOS_A_DESPLEGAR=""
        echo " Despliegue completo (todos los servicios)."
    fi
else
    DESPLIEGUE_PARCIAL=false
    SERVICIOS_A_DESPLEGAR=""
    if [ $# -gt 0 ]; then
        DESPLIEGUE_PARCIAL=true
        for nombre in "$@"; do
            if echo "$SERVICIOS_VALIDOS" | grep -qw "$nombre"; then
                SERVICIOS_A_DESPLEGAR="$SERVICIOS_A_DESPLEGAR $nombre"
            else
                echo "Error: servicio desconocido '$nombre'."
                echo "Servicios válidos: $SERVICIOS_VALIDOS"
                exit 1
            fi
        done
        SERVICIOS_A_DESPLEGAR=$(echo "$SERVICIOS_A_DESPLEGAR" | xargs)
    else
        mostrar_menu_interactivo
        elegir_por_menu
    fi
fi

if [ "$DESPLIEGUE_PARCIAL" = true ]; then
    echo "========================================================="
    echo " Despliegue parcial — Gestion de Proyectos"
    echo " Servicios: $SERVICIOS_A_DESPLEGAR"
    echo "========================================================="
    echo "$SERVICIOS_A_DESPLEGAR" > .deployed_services
else
    echo "========================================================="
    echo " Iniciando despliegue completo — Gestion de Proyectos..."
    echo "========================================================="
    echo "$SERVICIOS_VALIDOS" > .deployed_services
fi

# git pull solo en despliegue completo fuera de CI
if [ "$DESPLIEGUE_PARCIAL" = false ] && [ -z "$CI" ]; then
    echo "[1/3] Descargando últimos cambios del repositorio..."
    git pull origin main --rebase || echo "WARN: git pull falló (continuando con versión local)"
    echo ""
fi

# Construir y levantar
if [ "$DESPLIEGUE_PARCIAL" = true ]; then
    echo "[1/2] Construyendo servicios seleccionados: $SERVICIOS_A_DESPLEGAR"
    docker compose build $SERVICIOS_A_DESPLEGAR
    docker compose up -d --no-deps $SERVICIOS_A_DESPLEGAR
    echo ""
    echo "[2/2] Verificando estado..."
else
    echo "[1/2] Construyendo y levantando todos los servicios..."
    docker compose up -d --build
    echo ""
    echo "[2/2] Limpiando imágenes huérfanas..."
    docker image prune -f
fi

# Esperar arranque con polling
echo "Esperando inicio de servicios..."
WAITED=0
while [ $WAITED -lt 90 ]; do
    sleep 5
    WAITED=$((WAITED + 5))
    if [ -n "$SERVICIOS_A_DESPLEGAR" ]; then
        PATRON_CHECK=$(echo "$SERVICIOS_A_DESPLEGAR" | tr ' ' '|')
        ACTIVOS=$(docker ps --format "{{.Names}}" 2>/dev/null | grep -cE "$PATRON_CHECK" || echo "0")
        ESPERADOS=$(echo "$SERVICIOS_A_DESPLEGAR" | wc -w | tr -d ' ')
    else
        ACTIVOS=$(docker ps --format "{{.Names}}" 2>/dev/null | grep -cE "backend|frontend" || echo "0")
        ESPERADOS=$(echo "$SERVICIOS_VALIDOS" | wc -w | tr -d ' ')
    fi
    echo "  ${WAITED}s: $ACTIVOS/$ESPERADOS contenedores activos"
    [ "$ACTIVOS" -ge "$ESPERADOS" ] && break
done

# Mostrar estado
docker compose ps

# Validar contenedores en pie
if [ "$DESPLIEGUE_PARCIAL" = true ]; then
    SERVICIOS_CAIDOS=$(docker compose ps -a $SERVICIOS_A_DESPLEGAR --format "{{.Name}}	{{.Status}}" 2>/dev/null | grep -iE "Exited|Restarting|Created" || true)
else
    SERVICIOS_CAIDOS=$(docker compose ps -a --format "{{.Name}}	{{.Status}}" 2>/dev/null | grep -iE "Exited|Restarting|Created" || true)
fi

if [ -n "$SERVICIOS_CAIDOS" ]; then
    echo "========================================================="
    echo " ❌ ERROR: Los siguientes contenedores no están en ejecución:"
    echo "$SERVICIOS_CAIDOS"
    echo ""
    echo "Para investigar: docker logs [NOMBRE_DEL_CONTENEDOR]"
    echo "========================================================="
    exit 1
else
    echo "========================================================="
    if [ "$DESPLIEGUE_PARCIAL" = true ]; then
        echo " ✅ Despliegue parcial completado. Servicios: $SERVICIOS_A_DESPLEGAR"
    else
        echo " ✅ Despliegue completado y validado. Todos los servicios están en pie."
    fi
    echo "========================================================="
fi