# Script para importar datos de proyecto desde XML
# Uso: .\import-project.ps1 -xmlPath "ruta\al\archivo.xml" -apiBaseUrl "http://localhost:8080/api"

param(
    [Parameter(Mandatory=$true)]
    [string]$xmlPath,

    [Parameter(Mandatory=$false)]
    [string]$apiBaseUrl = "http://localhost:8080/api"
)

# Verificar que el archivo existe
if (-not (Test-Path $xmlPath)) {
    Write-Host "ERROR: No se encontró el archivo XML: $xmlPath" -ForegroundColor Red
    exit 1
}

# Cargar y parsear XML
[xml]$xml = Get-Content -Path $xmlPath -Encoding UTF8

# Extraer datos del proyecto
$proyectoNode = $xml.SelectSingleNode("//T_PROYECTOS_NUEVOS")

if ($null -eq $proyectoNode) {
    Write-Host "ERROR: No se encontró el nodo T_PROYECTOS_NUEVOS" -ForegroundColor Red
    exit 1
}

# Mapear campos del XML a la estructura P-001A
$codigoProyecto = $proyectoNode.COD_PROYECTO.Trim()
$nombreProyecto = $proyectoNode.DESCRIPCION_PROYECTO.Trim()
$modalidadInversion = $proyectoNode.MOD_PROYECTO.Trim()
$valorTotalProyecto = [long]$proyectoNode.VAL_TOTAL_PROYECTO
$valorAprobadoVigencia = [long]$proyectoNode.VALOR_APR_VIGENCIA
$justificacion = $proyectoNode.JUSTIFICACION.Trim()
$objetivos = $proyectoNode.OBJETIVO_PROYECTO.Trim()
$resolucionAEI = [int]$proyectoNode.RESOLUCION_AEI
$numActa = $proyectoNode.NUM_ACTA_AEI.Trim()
$fechaAprAEI = $proyectoNode.FECHA_APR_AEI.Trim()
$numConsejeros = [int]$proyectoNode.NUM_CONSEJEROS
$tiempoRecuperacion = [int]$proyectoNode.TMP_RECUPERACION
$numPersonasReferencia = [long]$proyectoNode.NUM_PERSONAS_REFERENCIA

# Convertir fecha de AAAAMMDD a ISO 8601
function Convertir-Fecha {
    param([string]$fecha)
    if ($fecha -match "^(\d{4})(\d{2})(\d{2})$") {
        return "$($Matches[1])-$($Matches[2])-$($Matches[3])"
    }
    return $fecha
}

$fechaConsejo = Convertir-Fecha $fechaAprAEI

# Estudios adicionales (estos campos no están en P001ADatosGenerales básico
# pero se pueden agregar al campo descripcionObjetivo o como metadata)
$estudioMercado = $proyectoNode.ESTUDIO_MERCADO.Trim()
$evaluacionSocial = $proyectoNode.EVALUACION_SOCIAL.Trim()
$evaluacionFinanciera = $proyectoNode.EVALUACION_FINANCIERA.Trim()

Write-Host "=== Datos del Proyecto ===" -ForegroundColor Cyan
Write-Host "Código: $codigoProyecto"
Write-Host "Nombre: $($nombreProyecto.Substring(0, [Math]::Min(80, $nombreProyecto.Length)))..."
Write-Host "Modalidad: $modalidadInversion"
Write-Host "Valor Total: $valorTotalProyecto"
Write-Host "Valor Aprobado: $valorAprobadoVigencia"
Write-Host "======================"

# Construir JSON para P-001A
$body = @{
    codigoProyecto = $codigoProyecto
    nombreProyecto = $nombreProyecto
    modalidadInversion = $modalidadInversion
    valorTotalProyecto = $valorTotalProyecto
    valorAprobadoVigencia = $valorAprobadoVigencia
    justificacion = $justificacion
    objetivos = $objetivos
    resolucionAEI = $resolucionAEI
    numActa = $numActa
    fechaConsejo = $fechaConsejo
    numConsejeros = $numConsejeros
    tiempoRecuperacion = $tiempoRecuperacion
    tasaDescuento = $null
    numeroBeneficiarios = $numPersonasReferencia
    descripcionObjetivo = "Estudio Mercado: $estudioMercado `n`n Evaluacion Social: $evaluacionSocial `n`n Evaluacion Financiera: $evaluacionFinanciera"
} | ConvertTo-Json -Depth 10

Write-Host "`n=== JSON a enviar ===" -ForegroundColor Yellow
Write-Host $body

# Enviar a la API
$url = "$apiBaseUrl/v1/estructuras/P-001A"

Write-Host "`n=== Enviando a $url ===" -ForegroundColor Cyan

try {
    $response = Invoke-WebRequest -Uri $url -Method Post -Body $body -ContentType "application/json" -TimeoutSec 30
    Write-Host "`nRespuesta exitosa:" -ForegroundColor Green
    Write-Host $response.Content
} catch {
    Write-Host "`nError al enviar datos:" -ForegroundColor Red
    Write-Host $_.Exception.Message
    Write-Host "Response: $($_.Exception.Response)"
}

Write-Host "`n=== Importación completada ===" -ForegroundColor Cyan