#!/bin/bash
# Detect changed services based on git diff vs origin/main.
# Returns: "backend frontend" (both) | "backend" | "frontend" | "" (none).

set -e

SERVICIOS="backend frontend"
DETECTED=""

# Intentar detectar contra origin/main ( funciona en pipelines GitLab con código fuente disponible )
if git rev-parse --verify origin/main >/dev/null 2>&1; then
    CHANGES=$(git diff --name-only origin/main...HEAD 2>/dev/null || echo "")
else
    # Fallback: diff contra HEAD~1 (último commit)
    CHANGES=$(git diff --name-only HEAD~1...HEAD 2>/dev/null || echo "")
fi

if [ -z "$CHANGES" ]; then
    echo ""
    exit 0
fi

for svc in $SERVICIOS; do
    case "$CHANGES" in
        *"$svc/"*) DETECTED="${DETECTED}${svc} " ;;
    esac
done

echo "${DETECTED}" | xargs