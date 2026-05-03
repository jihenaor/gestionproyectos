# ── Etapa de producción ───────────────────────────────────────────────────────
FROM nginx:1.25.4-alpine

RUN apk add --no-cache curl \
    && mkdir -p /usr/share/nginx/html/gestionproyectos

COPY dist/gestionproyectos-frontend/browser/ /usr/share/nginx/html/gestionproyectos/

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost/gestionproyectos/ || exit 1