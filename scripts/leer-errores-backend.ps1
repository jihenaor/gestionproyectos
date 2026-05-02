# ============================================
# GestionProyectos - Backend Error Reader
# ============================================
# Uso: .\leer-errores-backend.ps1 [opciones]
#   - Sin parametros: Muestra ultimos errores
#   - -tail N:       Muestra ultimas N lineas
#   - -full:         Muestra logs completos
#   - -search "txt": Busca texto en logs
#   - -run:          Ejecuta bootRun y muestra output en tiempo real

param(
    [int]$tail = 80,
    [switch]$full = $false,
    [string]$search = $null,
    [switch]$run = $false,
    [string]$logPath = "C:\Users\CenApoAdm03\desarrollo\gestionproyectos\backend"
)

# Evitar que warnings de comandos nativos se eleven como errores
if (Get-Variable -Name PSNativeCommandUseErrorActionPreference -ErrorAction SilentlyContinue) {
    $PSNativeCommandUseErrorActionPreference = $false
}

$backendDir = $logPath

# Si es modo -run, capturar output de bootRun
if ($run) {
    Write-Host ""
    Write-Host "================================================================" -ForegroundColor Magenta
    Write-Host "    GESTIONPROYECTOS - INICIANDO BACKEND CON CAPTURA          " -ForegroundColor Magenta
    Write-Host "================================================================" -ForegroundColor Magenta
    Write-Host ""
    Write-Host "[RUN] Ejecutando gradlew bootRun..." -ForegroundColor Yellow
    Write-Host "[INFO] Presiona Ctrl+C para detener" -ForegroundColor Gray
    Write-Host ""

    # Crear carpeta logs si no existe
    $logsDir = Join-Path $backendDir "logs"
    if (-not (Test-Path $logsDir)) {
        New-Item -ItemType Directory -Force -Path $logsDir | Out-Null
    }

    $timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
    $outputLog = Join-Path $logsDir "backend-run-$timestamp.log"
    $errorLog = Join-Path $logsDir "backend-error-$timestamp.log"

    $job = Start-Job -ScriptBlock {
        param($dir, $outFile, $errFile)
        Set-Location $dir
        $pinfo = New-Object System.Diagnostics.ProcessStartInfo
        $pinfo.FileName = ".\gradlew.bat"
        $pinfo.Arguments = "bootRun"
        $pinfo.RedirectStandardOutput = $true
        $pinfo.RedirectStandardError = $true
        $pinfo.UseShellExecute = $false
        $pinfo.CreateNoWindow = $true
        $pinfo.WorkingDirectory = $dir

        $process = New-Object System.Diagnostics.Process
        $process.StartInfo = $pinfo

        $process.Start() | Out-Null

        $stdout = $process.StandardOutput.ReadToEnd()
        $stderr = $process.StandardError.ReadToEnd()

        $process.WaitForExit()

        $stdout | Out-File -FilePath $outFile -Encoding UTF8
        $stderr | Out-File -FilePath $errFile -Encoding UTF8

        Write-Output "EXIT_CODE:$($process.ExitCode)"
    } -ArgumentList $backendDir, $outputLog, $errorLog

    $exitCaptured = $false
    $lastOutput = @()

    while ($job.State -eq "Running" -or $job.State -eq "NotStarted") {
        Start-Sleep -Seconds 3

        $output = Receive-Job -Name $job.Name -Keep 2>&1
        if ($output) {
            $lastOutput = @($output)
            foreach ($line in $output) {
                $s = $line.ToString()

                # Detectar si hay senal de salida
                if ($s -match "EXIT_CODE:") {
                    $exitCaptured = $true
                    $exitCode = $s -replace "EXIT_CODE:", ""
                }

                # Colorear por tipo
                if ($s -match "BUILD FAILED|FAILURE:|NoClassDefFoundError|ClassNotFoundException|Caused by:") {
                    Write-Host $s -ForegroundColor Red
                }
                elseif ($s -match "error|ERROR") {
                    Write-Host $s -ForegroundColor Red
                }
                elseif ($s -match "warn|WARN") {
                    Write-Host $s -ForegroundColor Yellow
                }
                elseif ($s -match "Started |BUILD SUCCESSFUL|Application startup complete") {
                    Write-Host $s -ForegroundColor Green
                }
                elseif ($s -match "INFO|DEBUG") {
                    Write-Host $s -ForegroundColor Cyan
                }
                else {
                    Write-Host $s -ForegroundColor Gray
                }
            }
        }

        # Mostrar si hubo nueva linea
        if ($output -and -not $exitCaptured) {
            Write-Host "" -ForegroundColor DarkGray
        }
    }

    # Obtener cualquier output final
    $finalOutput = Receive-Job -Name $job.Name -Keep 2>&1

    Write-Host ""
    Write-Host "================================================================" -ForegroundColor Magenta
    Write-Host "[FIN] Proceso terminado" -ForegroundColor Green

    if (Test-Path $outputLog) {
        Write-Host "[LOG] Output guardado: $outputLog" -ForegroundColor Cyan
    }
    if (Test-Path $errorLog) {
        Write-Host "[ERR] Errores guardados: $errorLog" -ForegroundColor Cyan
    }

    Write-Host ""
    Write-Host "Opciones de lectura:" -ForegroundColor Yellow
    Write-Host "  .\leer-errores-backend.ps1 -tail 100" -ForegroundColor DarkGray
    Write-Host "  .\leer-errores-backend.ps1 -search 'NoClassDefFoundError'" -ForegroundColor DarkGray
    Write-Host ""

    exit 0
}

# Modo normal: leer archivos de log existentes

# Verificar que exista la carpeta de logs
$logsDir = Join-Path $logPath "logs"
$logFiles = @()

# Primero buscar en carpeta logs/
if (Test-Path $logsDir) {
    $logFiles = Get-ChildItem -Path $logsDir -Filter "*.log" -ErrorAction SilentlyContinue | Sort-Object LastWriteTime -Descending
}

# Si no hay, buscar directamente en el directorio backend
if ($logFiles.Count -eq 0) {
    $logFiles = Get-ChildItem -Path $logPath -Filter "*.log" -ErrorAction SilentlyContinue | Sort-Object LastWriteTime -Descending
}

if ($logFiles.Count -eq 0) {
    Write-Host ""
    Write-Host "[INFO] No hay archivos de log en $logPath" -ForegroundColor Yellow
    Write-Host "[INFO] Usa -run para iniciar el backend y capturar logs" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "  .\leer-errores-backend.ps1 -run" -ForegroundColor DarkGray
    Write-Host ""
    exit 0
}

Write-Host ""
Write-Host "================================================================" -ForegroundColor Magenta
Write-Host "         GESTIONPROYECTOS - LECTOR DE LOGS BACKEND             " -ForegroundColor Magenta
Write-Host "================================================================" -ForegroundColor Magenta
Write-Host ""
Write-Host "Archivos disponibles:" -ForegroundColor Cyan

for ($i = 0; $i -lt $logFiles.Count; $i++) {
    $f = $logFiles[$i]
    $sizeKB = [math]::Round($f.Length / 1KB, 1)
    $date = $f.LastWriteTime.ToString("yyyy-MM-dd HH:mm:ss")
    Write-Host "  [$($i+1)] $($f.Name) - $sizeKB KB - $date" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "================================================================" -ForegroundColor Magenta

# Si se especifica busqueda
if ($search) {
    Write-Host "[SEARCH] Buscando: '$search'" -ForegroundColor Cyan
    Write-Host ""

    foreach ($logFile in $logFiles) {
        $content = Get-Content $logFile.FullName -ErrorAction SilentlyContinue
        $matches = $content | Select-String -Pattern $search

        if ($matches) {
            Write-Host "=== $($logFile.Name) ($($matches.Count) coincidencias) ===" -ForegroundColor Green

            foreach ($m in $matches) {
                $lineNum = $m.LineNumber
                $contextStart = [Math]::Max(0, $lineNum - 3)
                $contextEnd = [Math]::Min($content.Count, $lineNum + 2)

                Write-Host ""
                Write-Host "Linea $lineNum:" -ForegroundColor Gray
                for ($i = $contextStart; $i -lt $contextEnd; $i++) {
                    $line = $content[$i]
                    $prefix = if ($i + 1 -eq $lineNum) { ">>>" } else { "   " }
                    $color = if ($i + 1 -eq $lineNum) { "White" } else { "DarkGray" }

                    if ($line -match "error|exception|failed|BUILD FAILED|NoClassDefFoundError|ClassNotFoundException") {
                        $color = "Red"
                    }
                    elseif ($line -match "warn|warning") {
                        $color = "Yellow"
                    }

                    Write-Host "$prefix $($line)" -ForegroundColor $color
                }
                Write-Host ""
            }
        }
    }

    Write-Host "================================================================" -ForegroundColor Magenta
    Write-Host ""
    exit 0
}

# Si es modo completo
if ($full) {
    foreach ($logFile in $logFiles) {
        Write-Host "`n================================================================" -ForegroundColor Cyan
        Write-Host "=== $($logFile.Name) ===" -ForegroundColor Cyan
        Write-Host "================================================================" -ForegroundColor Cyan

        $content = Get-Content $logFile.FullName -ErrorAction SilentlyContinue

        if ($content) {
            $content | ForEach-Object {
                $line = $_.ToString()
                if ($line -match "error|exception|failed|BUILD FAILED|NoClassDefFoundError|ClassNotFoundException") {
                    Write-Host $line -ForegroundColor Red
                }
                elseif ($line -match "warn|warning") {
                    Write-Host $line -ForegroundColor Yellow
                }
                elseif ($line -match "success|BUILD SUCCESSFUL|Started") {
                    Write-Host $line -ForegroundColor Green
                }
                else {
                    Write-Host $line -ForegroundColor Gray
                }
            }
        }
    }
    exit 0
}

# Modo default: mostrar ultimas N lineas con colores
Write-Host "[TAIL] Mostrando ultimas $tail lineas" -ForegroundColor Cyan
Write-Host ""

foreach ($logFile in $logFiles) {
    Write-Host "================================================================" -ForegroundColor Cyan
    Write-Host "=== $($logFile.Name) ===" -ForegroundColor Cyan
    Write-Host "================================================================" -ForegroundColor Cyan

    $content = Get-Content $logFile.FullName -ErrorAction SilentlyContinue -Tail $tail

    if ($content) {
        $content | ForEach-Object {
            $line = $_.ToString()
            if ($line -match "error|exception|failed|BUILD FAILED|NoClassDefFoundError|ClassNotFoundException") {
                Write-Host $line -ForegroundColor Red
            }
            elseif ($line -match "warn|warning") {
                Write-Host $line -ForegroundColor Yellow
            }
            elseif ($line -match "success|BUILD SUCCESSFUL|Started") {
                Write-Host $line -ForegroundColor Green
            }
            elseif ($line -match "INFO|DEBUG") {
                Write-Host $line -ForegroundColor Cyan
            }
            else {
                Write-Host $line -ForegroundColor Gray
            }
        }
    }
    else {
        Write-Host "  (archivo vacio o sin permisos)" -ForegroundColor DarkGray
    }

    Write-Host ""
}

Write-Host "================================================================" -ForegroundColor Magenta
Write-Host "[FIN] Lectura completada" -ForegroundColor Green
Write-Host ""
Write-Host "Opciones:" -ForegroundColor Yellow
Write-Host "  -tail N      : Mostrar ultimas N lineas (default: 80)" -ForegroundColor DarkGray
Write-Host "  -full        : Mostrar log completo" -ForegroundColor DarkGray
Write-Host "  -search 'x'  : Buscar texto en logs" -ForegroundColor DarkGray
Write-Host "  -run         : Iniciar backend y capturar logs en tiempo real" -ForegroundColor DarkGray
Write-Host ""