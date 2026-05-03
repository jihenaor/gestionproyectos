#!/bin/bash
# ============================================================
# Destino Gestion de Proyectos — Script de Despliegue
# Ejecución: ./destino-proyectos.sh
# ============================================================

set -e

APP_DIR="/home/comfa/gestionproyectos"
JAR_FILE="gestionproyectos.jar"
FRONTEND_DIR="frontend/dist/gestionproyectos-frontend/browser"

echo "=========================================="
echo " Destino Gestion de Proyectos"
echo "=========================================="

# Ir al directorio de la aplicación
cd "$APP_DIR" || { echo "ERROR: No se pudo acceder a $APP_DIR"; exit 1; }

echo "[1/7] Directorio de trabajo: $(pwd)"

# Crear estructura de carpetas
echo "[2/7] Creando estructura de carpetas..."
mkdir -p "$APP_DIR/backend"
mkdir -p "$APP_DIR/frontend"
mkdir -p "$APP_DIR/scripts"
mkdir -p "$APP_DIR/prebuilt-jars"
echo "  Estructura creada"

# Detener servicios existentes
echo "[3/7] Deteniendo servicios existentes..."
docker compose down 2>/dev/null || echo "  No había servicios activos"
echo "  Servicios detenidos"

# Compilar backend (si no existe JAR preconstruido localmente)
echo "[4/7] Compilando backend..."
cd "$APP_DIR/backend"

# Usar Gradle 8.5 existente (disponible en /tmp/gradle-8.5)
GRADLE_CMD="/tmp/gradle-8.5/bin/gradle"
echo "  Usando Gradle 8.5 de /tmp/gradle-8.5"

if [ ! -f "build/libs/gestionproyectos.jar" ]; then
    "$GRADLE_CMD" bootJar -x test --no-daemon --console=plain
fi
JAR_BUILT=$(find . -name "*.jar" -not -name "*-plain.jar" 2>/dev/null | head -1)
if [ -n "$JAR_BUILT" ]; then
    cp "$JAR_BUILT" "$APP_DIR/prebuilt-jars/$JAR_FILE"
    echo "  JAR copiado: $(basename $JAR_BUILT)"
else
    echo "  WARN: No se encontró JAR compilado"
fi

# Compilar frontend (si no existe dist localmente)
echo "[5/7] Compilando frontend..."
cd "$APP_DIR/frontend"
if [ ! -d "dist/gestionproyectos-frontend/browser" ]; then
    npm ci --prefer-offline --no-audit || npm ci
    npm run build -- --configuration production --base-href=/gestionproyectos/ --deploy-url=/gestionproyectos/
fi
if [ -d "dist/gestionproyectos-frontend/browser" ]; then
    echo "  Frontend compilado OK"
else
    echo "  WARN: No se encontró dist del frontend"
fi

# Construir imágenes Docker
echo "[6/7] Construyendo imágenes Docker..."
cd "$APP_DIR"
export DOCKER_BUILDKIT=1
export COMPOSE_DOCKER_CLI_BUILD=1
docker compose build

# Levantar servicios
echo "[7/7] Levantando servicios..."
docker compose up -d

# Esperar y verificar
echo ""
echo "Esperando inicio de servicios..."
WAITED=0
while [ $WAITED -lt 60 ]; do
    sleep 5
    WAITED=$((WAITED + 5))
    BACKEND_OK=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8087/api/actuator/health 2>/dev/null || echo "000")
    FRONTEND_OK=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:4203/gestionproyectos/ 2>/dev/null || echo "000")
    echo "  ${WAITED}s — Backend: HTTP $BACKEND_OK, Frontend: HTTP $FRONTEND_OK"
    if [ "$BACKEND_OK" = "200" ] && [ "$FRONTEND_OK" = "200" ]; then
        break
    fi
    [ $WAITED -ge 60 ] && echo "  TIME OUT"
done

# Mostrar estado final
echo ""
echo "=========================================="
docker compose ps
echo "=========================================="
echo ""
echo " URLs de acceso:"
echo "   Backend: http://10.25.2.135:8087/api/"
echo "   Frontend: http://10.25.2.135:4203/gestionproyectos/"
echo "=========================================="

if [ "$BACKEND_OK" = "200" ] && [ "$FRONTEND_OK" = "200" ]; then
    echo " ✅ Despliegue completado exitosamente"
    exit 0
else
    echo " ⚠️  Despliegue completado con advertencias"
    exit 0
fi