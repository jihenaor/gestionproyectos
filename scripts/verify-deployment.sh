#!/bin/bash
# Verifica que los contenedores de gestionproyectos estén en pie tras el despliegue.
# Uso: scripts/verify-deployment.sh [SERVICIO1 SERVICIO2 ...]
# Sin argumentos: verifica todos los servicios (backend, frontend).

set -e

SERVICIOS_DEF="backend frontend"
SERVICIOS=${*:-"$SERVICIOS_DEF"}

echo "=================================================="
echo " Verificando despliegue — Gestion de Proyectos"
echo "=================================================="
echo " Servicios a verificar: $SERVICIOS"

FALLAS=0
for svc in $SERVICIOS; do
    STATE=$(docker inspect --format='{{.State.Status}}' "$svc" 2>/dev/null || echo "NOT_FOUND")
    HEALTH=$(docker inspect --format='{{if .State.Health}}{{.State.Health.Status}}{{else}}no-healthcheck{{end}}' "$svc" 2>/dev/null || echo "NOT_FOUND")

    if [ "$STATE" = "running" ]; then
        echo "  [OK]   $svc — State: $STATE, Health: $HEALTH"
    else
        echo "  [FAIL] $svc — State: $STATE (esperado: running)"
        FALLAS=$((FALLAS + 1))
    fi
done

# Verificar que el backend responde
if echo "$SERVICIOS" | grep -qw "backend"; then
    echo ""
    echo "Verificando API del backend..."
    MAX_WAIT=30
    WAITED=0
    while [ $WAITED -lt $MAX_WAIT ]; do
        HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8087/api/actuator/health 2>/dev/null || echo "000")
        if [ "$HTTP_CODE" = "200" ]; then
            echo "  [OK]   Backend responde (HTTP $HTTP_CODE)"
            break
        fi
        echo "  Intentando... ${WAITED}s / ${MAX_WAIT}s (HTTP $HTTP_CODE)"
        sleep 3
        WAITED=$((WAITED + 3))
    done
    if [ $WAITED -ge $MAX_WAIT ]; then
        echo "  [WARN]  Backend no respondió tras ${MAX_WAIT}s"
    fi
fi

# Verificar que el frontend responde
if echo "$SERVICIOS" | grep -qw "frontend"; then
    echo ""
    echo "Verificando frontend..."
    MAX_WAIT=20
    WAITED=0
    while [ $WAITED -lt $MAX_WAIT ]; do
        HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:4203/gestionproyectos/ 2>/dev/null || echo "000")
        if [ "$HTTP_CODE" = "200" ]; then
            echo "  [OK]   Frontend responde (HTTP $HTTP_CODE)"
            break
        fi
        echo "  Intentando... ${WAITED}s / ${MAX_WAIT}s (HTTP $HTTP_CODE)"
        sleep 3
        WAITED=$((WAITED + 3))
    done
    if [ $WAITED -ge $MAX_WAIT ]; then
        echo "  [WARN]  Frontend no respondió tras ${MAX_WAIT}s"
    fi
fi

echo ""
echo "=================================================="
if [ $FALLAS -gt 0 ]; then
    echo " ❌ Verificación completed con $FALLAS falha(s)"
    echo "=================================================="
    exit 1
else
    echo " ✅ Verificación completada — todos los servicios en pie"
    echo "=================================================="
    exit 0
fi