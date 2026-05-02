@echo off
REM Script para importar datos de proyecto desde XML usando curl
REM Uso: import-project.bat "ruta\al\archivo.xml"

set XML_PATH=%1
set API_BASE_URL=http://localhost:8080/api

if "%XML_PATH%"=="" (
    echo Uso: import-project.bat "ruta\al\archivo.xml"
    exit /b 1
)

if not exist "%XML_PATH%" (
    echo ERROR: No se encontro el archivo XML: %XML_PATH%
    exit /b 1
)

echo Extrayendo datos del proyecto...

REM Parsear XML manualmente y construir JSON
REM Esto es una version simplificada - para casos complejos usar PowerShell

echo Ejecutando import-project.ps1 con PowerShell...
powershell -ExecutionPolicy Bypass -File "%~dp0import-project.ps1" -xmlPath "%XML_PATH%" -apiBaseUrl "%API_BASE_URL%"

echo.
echo Operacion completada.