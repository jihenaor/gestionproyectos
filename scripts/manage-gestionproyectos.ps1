# powershell -ExecutionPolicy Bypass -File .\manage-gestionproyectos.ps1
# ============================================
# GestionProyectos - Service Manager
# ============================================
# Gestor de servicios para Backend (Java/Spring) y Frontend (Angular)

# Evita que warnings en STDERR de comandos nativos (gradlew) se eleven como excepciones.
if (Get-Variable -Name PSNativeCommandUseErrorActionPreference -ErrorAction SilentlyContinue) {
    $PSNativeCommandUseErrorActionPreference = $false
}

# GRADLE_OPTS con rutas con espacios puede provocar ClassNotFoundException
if ($env:GRADLE_OPTS -match 'org\.gradle\.java\.home') {
    Remove-Item Env:GRADLE_OPTS -ErrorAction SilentlyContinue
}

# Forzar JAVA_HOME=Java 21
$java21Home = $null
$javaCmd = Get-Command java -ErrorAction SilentlyContinue
if ($javaCmd) {
    $verOut = & java -version 2>&1 | Out-String
    if ($verOut -match '"21\.') {
        $java21Home = (Get-Item $javaCmd.Source).Directory.Parent.FullName
    }
}
if (-not $java21Home) {
    $paths = @(
        "C:\Program Files\Eclipse Adoptium\jdk-21*",
        "C:\Program Files\Java\jdk-21*",
        "C:\Program Files\Microsoft\jdk-21*"
    )
    foreach ($p in $paths) {
        $found = Get-Item $p -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($found -and (Test-Path "$($found.FullName)\bin\javac.exe")) {
            $java21Home = $found.FullName
            break
        }
    }
}
if ($java21Home) {
    try {
        $fso = New-Object -ComObject Scripting.FileSystemObject
        $javaShortPath = $fso.GetFolder($java21Home).ShortPath
    } catch {
        $javaShortPath = $java21Home
    }
    $env:JAVA_HOME = $javaShortPath
    Write-Host "[JAVA] JAVA_HOME=$env:JAVA_HOME" -ForegroundColor DarkGray
} elseif (-not $env:JAVA_HOME) {
    Write-Host "[JAVA] ADVERTENCIA: No se encontro Java 21. Instale JDK 21." -ForegroundColor Yellow
}

# Rutas de proyectos
$BACKEND_DIR = "C:\Users\CenApoAdm03\desarrollo\gestionproyectos\backend"
$FRONTEND_DIR = "C:\Users\CenApoAdm03\desarrollo\gestionproyectos\frontend"

# Definicion de servicios
$services = @(
    @{Name = "Backend"; Command = ".\gradlew.bat bootRun"; Port = 8080; WorkDir = $BACKEND_DIR; Type = "java" },
    @{Name = "Frontend"; Command = "npx ng serve --port 4200"; Port = 4200; WorkDir = $FRONTEND_DIR; Type = "node" }
)

# Ultimo perfil Spring del Backend (local=H2, as400=IBM i). Lo reutiliza Reiniciar sin volver a preguntar.
$script:BackendSpringProfile = 'local'
# Host IBM i fijo para produccion/as400 (manage-gestionproyectos.ps1 y fallback en application-as400.yml).
# Biblioteca JDBC + esquema JPA: por defecto BDSUPER; otra: $env:AS400_LIBRARY = 'OTRA' antes de arrancar el backend.
$script:As400HostProduccion = '10.25.2.10'
# Usuario/clave IBM i: application-as400.yml (no pasar por env desde este job; evita que PowerShell altere el $).
$script:BackendAs400HostForJob = ''

# ============================================
# FUNCIONES UTILITARIAS
# ============================================

function Test-Java21Available {
    $javacPath = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME "bin\javac.exe" } else { $null }
    if ($javacPath -and (Test-Path $javacPath)) {
        $javacOut = & $javacPath -version 2>&1 | Out-String
        if ($javacOut -match "javac 21(\.|$)") {
            return $true
        }
    }
    $javacCmd = Get-Command javac -ErrorAction SilentlyContinue
    if ($javacCmd) {
        $javacOut = & javac -version 2>&1 | Out-String
        if ($javacOut -match "javac 21(\.|$)") {
            return $true
        }
    }
    return $false
}

function Get-ServiceByName($name) {
    return $services | Where-Object { $_.Name -eq $name } | Select-Object -First 1
}

<#
 Pregunta desarrollo vs produccion (base de datos) y actualiza $script:BackendSpringProfile.
 D -> local + H2. P -> as400 + IBM i; AS400_HOST fijo $($script:As400HostProduccion) (no se pregunta).
#>
function Read-BackendDatabaseEnvironment {
    Write-Host ""
    Write-Host "================================================================" -ForegroundColor Cyan
    Write-Host " Ambiente del BACKEND (define SPRING_PROFILES_ACTIVE y la BD)" -ForegroundColor Yellow
    Write-Host "================================================================" -ForegroundColor Cyan
    Write-Host "  [D] Desarrollo  -> perfil ""local""" -ForegroundColor White
    Write-Host "       Base de datos: H2 en memoria (sin IBM i)." -ForegroundColor DarkGray
    Write-Host "  [P] Produccion  -> perfil ""as400""" -ForegroundColor White
    Write-Host "       Base de datos: IBM i / DB2 en $($script:As400HostProduccion) (AS400_HOST configurado)." -ForegroundColor DarkGray
    Write-Host "================================================================" -ForegroundColor Cyan
    while ($true) {
        $choice = Read-Host "Seleccione D (desarrollo) o P (produccion / AS400)"
        if ($null -eq $choice) { continue }
        $c = $choice.Trim().ToUpperInvariant()
        if ($c -eq 'D' -or $c -eq 'DESARROLLO' -or $c -eq 'DEV') {
            $script:BackendSpringProfile = 'local'
            $script:BackendAs400HostForJob = ''
            Write-Host "[OK] Perfil Spring: local  |  Datasource: H2 (application-local.yml)" -ForegroundColor Green
            return
        }
        if ($c -eq 'P' -or $c -eq 'PRODUCCION' -or $c -eq 'PRODUCCIÓN' -or $c -eq 'AS400') {
            $script:BackendSpringProfile = 'as400'
            $script:BackendAs400HostForJob = $script:As400HostProduccion
            $env:AS400_HOST = $script:As400HostProduccion
            Write-Host "[OK] Perfil Spring: as400  |  AS400_HOST=$($script:As400HostProduccion) (IBM i)" -ForegroundColor Green
            return
        }
        Write-Host "Opcion invalida. Escriba D o P." -ForegroundColor Red
    }
}

function Start-BackendGradleJob {
    $springProfile = $script:BackendSpringProfile
    if (-not $springProfile) { $springProfile = 'local' }
    $as400HostVal = $script:BackendAs400HostForJob
    if (-not $as400HostVal) { $as400HostVal = $env:AS400_HOST }
    if ($springProfile -eq 'as400' -and (-not $as400HostVal -or $as400HostVal.Trim().Length -eq 0)) {
        $as400HostVal = $script:As400HostProduccion
    }
    if (-not $as400HostVal) { $as400HostVal = '' }

    $scriptBlock = {
        param([string]$Dir, [string]$Profile, [string]$As400Host)
        Set-Location -LiteralPath $Dir
        $env:SPRING_PROFILES_ACTIVE = $Profile
        if ($Profile -eq 'as400' -and $As400Host -and $As400Host.Trim().Length -gt 0) {
            $env:AS400_HOST = $As400Host.Trim()
            # Quitar credenciales heredadas del padre que podrían pisar application-as400.yml (p. ej. password truncada).
            Remove-Item Env:\SPRING_DATASOURCE_USERNAME -ErrorAction SilentlyContinue
            Remove-Item Env:\SPRING_DATASOURCE_PASSWORD -ErrorAction SilentlyContinue
        }
        & .\gradlew.bat bootRun
    }
    Start-Job -ScriptBlock $scriptBlock -ArgumentList @($BACKEND_DIR, $springProfile, $as400HostVal) -Name "Backend" | Out-Null

    Write-Host "[INFO] Job Backend: SPRING_PROFILES_ACTIVE=$springProfile" -ForegroundColor Cyan
    if ($springProfile -eq 'as400') {
        if ($as400HostVal) {
            Write-Host "       AS400_HOST=$as400HostVal (pasado al proceso Gradle)" -ForegroundColor DarkGray
        }
        $libMsg = if ($env:AS400_LIBRARY -and $env:AS400_LIBRARY.Trim().Length -gt 0) { $env:AS400_LIBRARY.Trim() } else { 'BDSUPER (default YAML; defina AS400_LIBRARY para otra biblioteca)' }
        Write-Host "       AS400_LIBRARY=$libMsg" -ForegroundColor DarkGray
    }
}

# Gradle usa el directorio de trabajo actual como raiz del proyecto.
# Llamar a backend\gradlew.bat con ruta absoluta sin cambiar cwd falla (busca settings.gradle en scripts).
function Invoke-GestionGradle {
    param([Parameter(Mandatory)][string[]]$GradleArguments)
    Push-Location -LiteralPath $BACKEND_DIR
    try {
        $text = & .\gradlew.bat @GradleArguments 2>&1 | Out-String
        [pscustomobject]@{ Text = $text; ExitCode = $LASTEXITCODE }
    } finally {
        Pop-Location
    }
}

# ============================================
# FUNCIONES DE COMPILACION Y VALIDACION
# ============================================

function Test-Compilation {
    Write-Host "`n[VALIDACION] Compilando servicios..." -ForegroundColor Cyan
    Write-Host "================================================================" -ForegroundColor Cyan

    if (-not (Test-Java21Available)) {
        Write-Host "[FALLO] Java 21 no disponible para Gradle." -ForegroundColor Red
        Write-Host "Instala JDK 21 y configura JAVA_HOME." -ForegroundColor Yellow
        return $false
    }

    $null = Invoke-GestionGradle @('--stop')

    Write-Host "[BUILD] Compilando backend..." -ForegroundColor Yellow
    Write-Host "  > (en $BACKEND_DIR) .\gradlew.bat classes" -ForegroundColor DarkCyan

    $gradleResult = Invoke-GestionGradle @('classes')
    $output = $gradleResult.Text
    $exitCode = $gradleResult.ExitCode

    if ($exitCode -ne 0) {
        Write-Host "[ERROR] Fallo en compilacion del backend" -ForegroundColor Red
        Write-Host "================================================================" -ForegroundColor Red

        $lines = $output -split "`n"
        $inErrorSection = $false
        $errorCount = 0

        for ($i = 0; $i -lt $lines.Count; $i++) {
            $line = $lines[$i].Trim()

            if ($line -match "> Task :compileJava") {
                $inErrorSection = $true
                continue
            }

            if ($inErrorSection) {
                if ($line -match "\.java:\d+: error:") {
                    Write-Host "`n$line" -ForegroundColor Red
                    $errorCount++

                    for ($j = 1; $j -le 5 -and ($i + $j) -lt $lines.Count; $j++) {
                        $contextLine = $lines[$i + $j].Trim()
                        if ($contextLine.Length -gt 0 -and $contextLine -notmatch "^>") {
                            Write-Host "  $contextLine" -ForegroundColor DarkRed
                        }
                    }
                }
                elseif ($line -match "\.java:\d+: warning:") {
                    Write-Host "`n$line" -ForegroundColor Yellow
                }
                elseif ($line -match "^\d+ error") {
                    Write-Host "`n$line" -ForegroundColor Red
                }
                elseif ($line -match "BUILD FAILED|FAILURE:") {
                    break
                }
            }
        }

        if ($errorCount -eq 0) {
            Write-Host "No se pudieron extraer detalles del error. Salida completa:" -ForegroundColor Yellow
            Write-Host $output -ForegroundColor DarkGray
        }
        else {
            Write-Host "`n[RESUMEN] Total de errores encontrados: $errorCount" -ForegroundColor Red
        }

        Write-Host "================================================================" -ForegroundColor Red
        Write-Host "`nPresiona 'Q' para volver al menu..." -ForegroundColor Yellow
        do {
            $key = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        } while ($key.Character -notmatch '(?i)q')
        return $false
    }
    else {
        Write-Host "[OK] Backend compilado correctamente" -ForegroundColor Green
    }

    Write-Host "`n================================================================" -ForegroundColor Cyan
    Write-Host "[EXITO] Compilacion correcta" -ForegroundColor Green
    Start-Sleep -Seconds 2
    return $true
}

function Test-SpringContext {
    Write-Host "`n[SPRING] Validando contexto de Spring (Context Load)..." -ForegroundColor Cyan
    Write-Host "================================================================" -ForegroundColor Cyan

    if (-not (Test-Java21Available)) {
        Write-Host "[FALLO] Java 21 no disponible." -ForegroundColor Red
        return $false
    }

    $null = Invoke-GestionGradle @('--stop')

    Write-Host "[TEST] Ejecutando tests del backend..." -ForegroundColor Yellow
    $gradleResult = Invoke-GestionGradle @('test')
    $output = $gradleResult.Text
    $exitCode = $gradleResult.ExitCode

    if ($exitCode -ne 0) {
        Write-Host "[ERROR] Fallo en tests o arranque de Spring" -ForegroundColor Red
        Write-Host "================================================================" -ForegroundColor Red

        $lines = $output -split "`n"
        foreach ($line in $lines) {
            if ($line -match "UnsatisfiedDependencyException|BeanCreationException|Ambiguous|test failed|FAILURE|NoClassDefFoundError|ClassNotFoundException") {
                Write-Host $line.Trim() -ForegroundColor Red
            }
        }

        Write-Host "`nRevisa los logs detallados de Gradle para mas informacion." -ForegroundColor Yellow
        Write-Host "================================================================" -ForegroundColor Red
        Write-Host "`nPresiona 'Q' para volver al menu..." -ForegroundColor Yellow
        do {
            $key = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        } while ($key.Character -notmatch '(?i)q')
        return $false
    }
    else {
        Write-Host "[OK] Tests y contexto de Spring verificados correctamente" -ForegroundColor Green
    }

    Write-Host "`n================================================================" -ForegroundColor Cyan
    Start-Sleep -Seconds 2
    return $true
}

# ============================================
# FUNCIONES DE CONTROL DE SERVICIOS
# ============================================

<#
 Termina procesos que tienen conexiones TCP en LocalPort (p. ej. 8080, 4200).
 Usa Get-NetTCPConnection en Windows; si no hay datos, complementa con netstat LISTENING.
#>
function Stop-ProcessesOnLocalPort {
    param(
        [Parameter(Mandatory)][int]$Port,
        [string]$Etiqueta = ""
    )
    $label = if ($Etiqueta) { " ($Etiqueta)" } else { "" }
    $pids = New-Object 'System.Collections.Generic.HashSet[int]'

    if (Get-Command Get-NetTCPConnection -ErrorAction SilentlyContinue) {
        try {
            Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue | ForEach-Object {
                $op = $_.OwningProcess
                if ($op -and $op -gt 0) { [void]$pids.Add([int]$op) }
            }
        } catch { }
    }

    if ($pids.Count -eq 0) {
        $raw = netstat -ano 2>$null | findstr ":$Port "
        foreach ($line in $raw) {
            if ($line -match 'LISTENING\s+(\d+)\s*$') {
                [void]$pids.Add([int]$matches[1])
            }
        }
    }

    foreach ($processId in $pids) {
        if ($processId -le 4) { continue }
        try {
            $p = Get-Process -Id $processId -ErrorAction SilentlyContinue
            $nombre = if ($p) { $p.ProcessName } else { "?" }
            Write-Host "    Matando $nombre (PID $processId) en puerto $Port$label" -ForegroundColor Red
            Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
            taskkill /PID $processId /F 2>$null | Out-Null
        } catch {
            Write-Host "    [WARN] No se pudo terminar PID $processId" -ForegroundColor Yellow
        }
    }
}

function Stop-ServiceByName($serviceName) {
    $service = Get-ServiceByName $serviceName
    if (-not $service) { return }

    $job = Get-Job -Name $serviceName -ErrorAction SilentlyContinue
    if ($job) {
        Stop-Job -Name $serviceName -ErrorAction SilentlyContinue
        Remove-Job -Name $serviceName -Force -ErrorAction SilentlyContinue
    }

    Stop-ProcessesOnLocalPort -Port $service.Port -Etiqueta $serviceName
}

function Start-ServiceByName($serviceName) {
    $service = Get-ServiceByName $serviceName
    if (-not $service) { return }

    Stop-ServiceByName $serviceName
    Start-Sleep -Seconds 2

    $icon = switch ($serviceName) {
        "Backend" { "[BACKEND]" }
        "Frontend" { "[FRONT]" }
        default { "[???]" }
    }
    Write-Host "$icon Iniciando $($serviceName)..." -ForegroundColor Yellow

    $scriptBlock = {
        param($dir, $cmd)
        Set-Location $dir
        Invoke-Expression $cmd
    }

    Start-Job -ScriptBlock $scriptBlock -ArgumentList $service.WorkDir, $service.Command -Name $serviceName | Out-Null

    $waitTime = if ($serviceName -eq "Backend") { 10 } else { 15 }
    Start-Sleep -Seconds $waitTime
}

function Stop-AllServices {
    Write-Host "`n[STOP] Deteniendo todos los servicios..." -ForegroundColor Red

    $null = Invoke-GestionGradle @('--stop')

    $jobs = Get-Job
    if ($jobs) {
        Write-Host "`n[JOBS] Deteniendo jobs de PowerShell..." -ForegroundColor Yellow
        $jobs | Stop-Job -ErrorAction SilentlyContinue
        $jobs | Remove-Job -Force -ErrorAction SilentlyContinue
    }

    Start-Sleep -Seconds 2

    Write-Host "`n[CLEANUP] Liberando puertos (8080 Backend, 4200 Frontend)..." -ForegroundColor Yellow

    foreach ($service in $services) {
        $port = $service.Port
        Write-Host "  Puerto $port ($($service.Name))..." -ForegroundColor Gray
        Stop-ProcessesOnLocalPort -Port $port -Etiqueta $service.Name
    }

    Write-Host "`n[JAVA] Cierre adicional: Java Gradle/Spring de este proyecto..." -ForegroundColor Yellow
    $javaProcesses = Get-Process -Name "java" -ErrorAction SilentlyContinue
    if ($javaProcesses) {
        foreach ($proc in $javaProcesses) {
            try {
                $cmdLine = (Get-CimInstance Win32_Process -Filter "ProcessId = $($proc.Id)" -ErrorAction SilentlyContinue).CommandLine
                if ($cmdLine -and $cmdLine -match "gestionproyectos|gradle|spring-boot") {
                    Write-Host "    Terminando Java (PID: $($proc.Id))" -ForegroundColor Red
                    taskkill /PID $proc.Id /F 2>&1 | Out-Null
                }
            } catch { }
        }
    }

    Start-Sleep -Seconds 2

    function Test-PortsStillInUse {
        foreach ($svc in $services) {
            $busy = $false
            if (Get-Command Get-NetTCPConnection -ErrorAction SilentlyContinue) {
                $busy = $null -ne (Get-NetTCPConnection -LocalPort $svc.Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1)
            }
            if (-not $busy) {
                $ns = netstat -ano 2>$null | findstr ":$($svc.Port) "
                $busy = $null -ne ($ns | Where-Object { $_ -match 'LISTENING' })
            }
            if ($busy) { return $true }
        }
        return $false
    }

    Write-Host "`n[VERIFICACION] Verificando puertos..." -ForegroundColor Cyan
    $allPortsFree = -not (Test-PortsStillInUse)

    if (-not $allPortsFree) {
        Write-Host "  [REINTENTO] Segundo pase forzando cierre en puertos..." -ForegroundColor Yellow
        Start-Sleep -Seconds 1
        foreach ($service in $services) {
            Stop-ProcessesOnLocalPort -Port $service.Port -Etiqueta "reintento"
        }
        Start-Sleep -Seconds 2
        $allPortsFree = -not (Test-PortsStillInUse)
    }

    foreach ($service in $services) {
        $still = $false
        if (Get-Command Get-NetTCPConnection -ErrorAction SilentlyContinue) {
            $still = $null -ne (Get-NetTCPConnection -LocalPort $service.Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1)
        }
        if (-not $still) {
            $ns = netstat -ano 2>$null | findstr ":$($service.Port) "
            $still = $null -ne ($ns | Where-Object { $_ -match 'LISTENING' })
        }
        if ($still) {
            Write-Host "  [WARN] Puerto $($service.Port) ($($service.Name)) AUN OCUPADO" -ForegroundColor Red
        } else {
            Write-Host "  [OK] Puerto $($service.Port) ($($service.Name)) LIBRE" -ForegroundColor Green
        }
    }

    if ($allPortsFree) {
        Write-Host "`n[EXITO] Todos los servicios detenidos y puertos liberados" -ForegroundColor Green
    } else {
        Write-Host "`n[ADVERTENCIA] Algunos puertos siguen ocupados (TIME_WAIT u otro proceso). Espere 1-2 min o ejecute como administrador." -ForegroundColor Yellow
    }

    Start-Sleep -Seconds 3
}

function Start-BothServices {
    Write-Host "`n[INICIO] Iniciando ambos servicios..." -ForegroundColor Green

    if (-not (Test-Java21Available)) {
        Write-Host "[FALLO] Java 21 no disponible. No se puede iniciar el backend." -ForegroundColor Red
        Start-Sleep -Seconds 3
        return
    }

    Read-BackendDatabaseEnvironment

    Write-Host "`n[PRE-START] Deteniendo servicios previos..." -ForegroundColor Yellow
    Stop-AllServices

    Write-Host "`n[VALIDACION] Validando compilacion antes de iniciar..." -ForegroundColor Cyan
    $compilationOk = Test-Compilation

    if (-not $compilationOk) {
        Write-Host "`n[ERROR] No se pueden iniciar los servicios debido a errores de compilacion" -ForegroundColor Red
        Start-Sleep -Seconds 3
        return
    }

    New-Item -ItemType Directory -Force -Path "$BACKEND_DIR\logs" | Out-Null

    Write-Host "`n[START] Iniciando Backend..." -ForegroundColor Cyan
    $backendJob = Get-Job -Name "Backend" -ErrorAction SilentlyContinue
    if (-not $backendJob) {
        Start-BackendGradleJob
        Write-Host "[OK] Backend iniciando... (espera 20-30s)" -ForegroundColor Yellow
        Start-Sleep -Seconds 10
    }

    Write-Host "`n[START] Iniciando Frontend (modo desarrollo: ng serve)..." -ForegroundColor Cyan
    $frontendJob = Get-Job -Name "Frontend" -ErrorAction SilentlyContinue
    if (-not $frontendJob) {
        $scriptBlock = { param($dir, $cmd); Set-Location $dir; Invoke-Expression $cmd }
        Start-Job -ScriptBlock $scriptBlock -ArgumentList $FRONTEND_DIR, "npx ng serve --port 4200" -Name "Frontend" | Out-Null
        Write-Host "[OK] Frontend iniciando... (espera 15-20s)" -ForegroundColor Yellow
        Start-Sleep -Seconds 5
    }

    Write-Host "`n[OK] Servicios iniciando..." -ForegroundColor Green
    Write-Host "[INFO] Backend: http://localhost:8080  (perfil $($script:BackendSpringProfile))" -ForegroundColor Cyan
    Write-Host "[INFO] Frontend: http://localhost:4200" -ForegroundColor Cyan
    Start-Sleep -Seconds 3
}

function Start-BackendOnly {
    Write-Host "`n[INICIO] Iniciando solo Backend..." -ForegroundColor Green

    if (-not (Test-Java21Available)) {
        Write-Host "[FALLO] Java 21 no disponible." -ForegroundColor Red
        Start-Sleep -Seconds 3
        return
    }

    Read-BackendDatabaseEnvironment

    Write-Host "`n[PRE-START] Deteniendo Backend previo..." -ForegroundColor Yellow
    Stop-ServiceByName "Backend"

    Write-Host "`n[VALIDACION] Validando compilacion..." -ForegroundColor Cyan
    $compilationOk = Test-Compilation

    if (-not $compilationOk) {
        Write-Host "`n[ERROR] Errores de compilacion. No se puede iniciar." -ForegroundColor Red
        Start-Sleep -Seconds 3
        return
    }

    New-Item -ItemType Directory -Force -Path "$BACKEND_DIR\logs" | Out-Null

    Write-Host "`n[START] Iniciando Backend..." -ForegroundColor Cyan
    Start-BackendGradleJob

    Write-Host "[OK] Backend iniciando... (espera 20-30s)" -ForegroundColor Yellow
    Write-Host "[INFO] Backend: http://localhost:8080  (perfil $($script:BackendSpringProfile))" -ForegroundColor Cyan
    Start-Sleep -Seconds 3
}

function Start-FrontendOnly {
    Write-Host "`n[INICIO] Iniciando solo Frontend..." -ForegroundColor Green

    Write-Host "`n[PRE-START] Deteniendo Frontend previo..." -ForegroundColor Yellow
    Stop-ServiceByName "Frontend"

    Write-Host "`n[START] Iniciando Frontend..." -ForegroundColor Cyan
    $scriptBlock = { param($dir, $cmd); Set-Location $dir; Invoke-Expression $cmd }
    Start-Job -ScriptBlock $scriptBlock -ArgumentList $FRONTEND_DIR, "npx ng serve --port 4200" -Name "Frontend" | Out-Null

    Write-Host "[OK] Frontend iniciando... (espera 15-20s)" -ForegroundColor Yellow
    Write-Host "[INFO] Frontend: http://localhost:4200" -ForegroundColor Cyan
    Start-Sleep -Seconds 3
}

# ============================================
# FUNCIONES DE VISUALIZACION
# ============================================

function Show-Menu {
    try { [Console]::Clear() } catch { Clear-Host }
    $menu = @"
================================================================
       GESTIONPROYECTOS - SERVICE MANAGER
================================================================

  [1] Iniciar AMBOS servicios (Backend + Frontend) - pregunta ambiente BD del backend
  [A] Iniciar solo BACKEND - pregunta D=desarrollo(H2) o P=produccion(AS400/IBM i)
  [F] Iniciar solo FRONTEND (Angular)
  [2] Detener TODOS los servicios
  [3] Ver estado de servicios
  [4] Ver logs en tiempo real (todos)
  [5] Ver logs de un servicio especifico
  [6] Verificar health checks
  [7] Reiniciar servicio(s) (ej: 1 o 1,2)
  [D] Detener servicio(s) (ej: 1 o 1,2)
  [8] Exportar logs a archivo
  [C] Validar compilacion (Backend)
  [S] Validar arranque de Spring (Tests)
  [0] Salir

================================================================
"@
    Write-Host $menu -ForegroundColor Cyan
}

function Show-ServiceStatus {
    Write-Host "`n[STATUS] Estado de los servicios:" -ForegroundColor Yellow
    Write-Host "================================================================" -ForegroundColor Cyan

    $jobs = Get-Job

    foreach ($service in $services) {
        $job = $jobs | Where-Object { $_.Name -eq $service.Name }

        $icon = switch ($service.Name) {
            "Backend" { "[BACKEND]" }
            "Frontend" { "[FRONT]" }
            default { "[???]" }
        }

        if ($job) {
            $status = $job.State
            $color = switch ($status) {
                "Running" { "Green" }
                "Failed" { "Red" }
                "Stopped" { "Yellow" }
                default { "Gray" }
            }
            Write-Host "$icon $($service.Name.PadRight(15)) [$status] - Puerto: $($service.Port)" -ForegroundColor $color
        } else {
            Write-Host "$icon $($service.Name.PadRight(15)) [No iniciado] - Puerto: $($service.Port)" -ForegroundColor Gray
        }
    }

    Write-Host ""
    Write-Host "Ultimo perfil Spring del backend (reinicio):" -ForegroundColor Yellow
    Write-Host "  SPRING_PROFILES_ACTIVE=$($script:BackendSpringProfile)" -ForegroundColor Cyan
    if ($script:BackendSpringProfile -eq 'as400') {
        Write-Host "  AS400_HOST (produccion): $($script:As400HostProduccion)" -ForegroundColor DarkGray
    }
    Write-Host ""
    Write-Host "URLs de acceso:" -ForegroundColor Yellow
    Write-Host "  Backend:  http://localhost:8080" -ForegroundColor Cyan
    Write-Host "  Frontend: http://localhost:4200" -ForegroundColor Cyan

    Write-Host "================================================================" -ForegroundColor Cyan
    Write-Host "`nPresiona cualquier tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
}

function Show-AllLogs {
    Write-Host "`n[LOGS] Mostrando logs de todos los servicios..." -ForegroundColor Magenta
    Write-Host "Presiona 'Q' para salir`n" -ForegroundColor Yellow
    Start-Sleep -Seconds 2

    $jobs = Get-Job
    $continue = $true

    while ($continue) {
        Clear-Host
        Write-Host "================================================================" -ForegroundColor Magenta
        Write-Host "                    LOGS EN TIEMPO REAL                         " -ForegroundColor Magenta
        Write-Host "================================================================" -ForegroundColor Magenta
        Write-Host ""

        foreach ($service in $services) {
            $job = $jobs | Where-Object { $_.Name -eq $service.Name }
            if ($job) {
                Write-Host "--- $($service.Name) ---" -ForegroundColor Cyan
                $logs = Receive-Job -Name $service.Name -Keep 2>&1 | Select-Object -Last 600
                if ($logs) {
                    $logs | ForEach-Object {
                        $line = $_.ToString()
                        if ($line -match "error|ERROR|exception|failed|BUILD FAILED|NoClassDefFoundError|ClassNotFoundException") {
                            Write-Host $line -ForegroundColor Red
                        }
                        elseif ($line -match "warn|WARN") {
                            Write-Host $line -ForegroundColor Yellow
                        }
                        elseif ($line -match "success|BUILD SUCCESSFUL|Started |Application startup complete") {
                            Write-Host $line -ForegroundColor Green
                        }
                        elseif ($line -match "Compil|Building") {
                            Write-Host $line -ForegroundColor Cyan
                        }
                        else {
                            Write-Host $line -ForegroundColor Gray
                        }
                    }
                } else {
                    Write-Host "  (Sin logs recientes)" -ForegroundColor DarkGray
                }
                Write-Host ""
            }
        }

        Write-Host "Presiona 'Q' para salir o espera 20 segundos para actualizar..." -ForegroundColor Yellow

        for ($i = 0; $i -lt 20; $i++) {
            if ([Console]::KeyAvailable) {
                $key = [Console]::ReadKey($true)
                if ($key.Key -eq 'Q' -or $key.Key -eq 'Escape') {
                    $continue = $false
                    break
                }
            }
            Start-Sleep -Seconds 1
        }

        $jobs = Get-Job
    }
}

function Show-ServiceLogs {
    Write-Host "`n[LOGS] Servicios disponibles:" -ForegroundColor Cyan

    for ($i = 0; $i -lt $services.Count; $i++) {
        Write-Host "  [$($i+1)] $($services[$i].Name)" -ForegroundColor Yellow
    }

    Write-Host "`nSelecciona un servicio (1-$($services.Count)): " -NoNewline -ForegroundColor White
    $selection = Read-Host

    if ($selection -match '^\d+$' -and [int]$selection -ge 1 -and [int]$selection -le $services.Count) {
        $service = $services[[int]$selection - 1]
        Write-Host "`n[LOGS] Logs de $($service.Name):" -ForegroundColor Magenta
        Write-Host "Presiona 'Q' para salir o 'R' para refrescar`n" -ForegroundColor Yellow
        Start-Sleep -Seconds 1

        $continue = $true
        while ($continue) {
            Clear-Host
            Write-Host "================================================================" -ForegroundColor Magenta
            Write-Host "              LOGS - $($service.Name.ToUpper().PadRight(40)) " -ForegroundColor Magenta
            Write-Host "================================================================" -ForegroundColor Magenta
            Write-Host ""

            $logs = Receive-Job -Name $service.Name -Keep 2>&1 | Select-Object -Last 200
            if ($logs) {
                $logs | ForEach-Object {
                    $line = $_.ToString()
                    if ($line -match "error|ERROR|exception|failed|BUILD FAILED|NoClassDefFoundError|ClassNotFoundException") {
                        Write-Host $line -ForegroundColor Red
                    }
                    elseif ($line -match "warn|WARN") {
                        Write-Host $line -ForegroundColor Yellow
                    }
                    elseif ($line -match "success|BUILD SUCCESSFUL|Started |Application startup complete") {
                        Write-Host $line -ForegroundColor Green
                    }
                    elseif ($line -match "Compil|Building") {
                        Write-Host $line -ForegroundColor Cyan
                    }
                    else {
                        Write-Host $line -ForegroundColor Gray
                    }
                }
            } else {
                Write-Host "  (Sin logs disponibles)" -ForegroundColor DarkGray
            }

            Write-Host "`nPresiona 'Q' para salir o 'R' para refrescar..." -ForegroundColor Yellow

            $key = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
            if ($key.Character -match '(?i)q' -or $key.VirtualKeyCode -eq 27) {
                $continue = $false
            }
        }
    } else {
        Write-Host "[ERROR] Seleccion invalida" -ForegroundColor Red
        Start-Sleep -Seconds 2
    }
}

function Test-HealthChecks {
    Write-Host "`n[HEALTH] Verificando health checks..." -ForegroundColor Blue
    Write-Host "================================================================" -ForegroundColor Cyan

    $healthChecks = @(
        @{Name = "Backend"; Url = "http://localhost:8080/api/v1/proyectos" },
        @{Name = "Frontend"; Url = "http://localhost:4200" }
    )

    foreach ($check in $healthChecks) {
        try {
            $response = Invoke-WebRequest -Uri $check.Url -TimeoutSec 3 -UseBasicParsing -ErrorAction Stop
            if ($response.StatusCode -eq 200 -or $response.StatusCode -eq 201) {
                Write-Host "[OK] $($check.Name.PadRight(15)) - OK ($($response.StatusCode))" -ForegroundColor Green
            } else {
                Write-Host "[WARN] $($check.Name.PadRight(15)) - Status: $($response.StatusCode)" -ForegroundColor Yellow
            }
        } catch {
            Write-Host "[FAIL] $($check.Name.PadRight(15)) - NO DISPONIBLE" -ForegroundColor Red
        }
    }

    Write-Host ""
    Write-Host "URLs de prueba:" -ForegroundColor Gray
    Write-Host "  Backend:  http://localhost:8080/api/v1/proyectos" -ForegroundColor DarkGray
    Write-Host "  Frontend: http://localhost:4200" -ForegroundColor DarkGray

    Write-Host "================================================================" -ForegroundColor Cyan
    Write-Host "`nPresiona cualquier tecla para continuar..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
}

function Restart-ServiceUI {
    Write-Host "`n[RESTART] Servicios disponibles:" -ForegroundColor Cyan

    for ($i = 0; $i -lt $services.Count; $i++) {
        Write-Host "  [$($i+1)] $($services[$i].Name)" -ForegroundColor Yellow
    }

    Write-Host "`nSelecciona servicio(s) para reiniciar (ej: 1 o 1,2): " -NoNewline -ForegroundColor White
    $selection = Read-Host

    $selectedNumbers = $selection -split ',' | ForEach-Object { $_.Trim() } | Where-Object { $_ -match '^\d+$' }

    if ($selectedNumbers.Count -eq 0) {
        Write-Host "[ERROR] Seleccion invalida" -ForegroundColor Red
        Start-Sleep -Seconds 2
        return
    }

    $servicesToRestart = @()
    foreach ($num in $selectedNumbers) {
        $index = [int]$num
        if ($index -ge 1 -and $index -le $services.Count) {
            $servicesToRestart += $services[$index - 1]
        } else {
            Write-Host "[WARN] Numero $num fuera de rango, ignorando..." -ForegroundColor Yellow
        }
    }

    if ($servicesToRestart.Count -eq 0) {
        Write-Host "[ERROR] No hay servicios validos para reiniciar" -ForegroundColor Red
        Start-Sleep -Seconds 2
        return
    }

    foreach ($svc in $servicesToRestart) {
        Write-Host "`n[INFO] Reiniciando: $($svc.Name)" -ForegroundColor Cyan

        if ($svc.Name -eq "Backend") {
            if (-not (Test-Java21Available)) {
                Write-Host "[ERROR] Java 21 no disponible. No se puede reiniciar Backend." -ForegroundColor Red
                continue
            }

            Write-Host "`n[VALIDACION] Compilando..." -ForegroundColor Cyan
            $gradleResult = Invoke-GestionGradle @('classes')
            $output = $gradleResult.Text
            $exitCode = $gradleResult.ExitCode

            if ($exitCode -ne 0) {
                Write-Host "[ERROR] Fallo en compilacion" -ForegroundColor Red
                $lines = $output -split "`n"
                $errorCount = 0
                $inError = $false
                for ($i = 0; $i -lt $lines.Count; $i++) {
                    $line = $lines[$i].Trim()
                    if ($line -match "> Task :compileJava") { $inError = $true; continue }
                    if ($inError -and $line -match "\.java:\d+: error:") {
                        Write-Host $line -ForegroundColor Red
                        $errorCount++
                        for ($j = 1; $j -le 4 -and ($i+$j) -lt $lines.Count; $j++) {
                            $ctx = $lines[$i+$j].Trim()
                            if ($ctx.Length -gt 0 -and $ctx -notmatch "^>") { Write-Host "  $ctx" -ForegroundColor DarkRed }
                        }
                    }
                }
                Write-Host "Total errores: $errorCount" -ForegroundColor Red
                Write-Host "`n[CANCELADO] No se puede reiniciar Backend. Corrige errores." -ForegroundColor Red
                Start-Sleep -Seconds 3
                continue
            }
            Write-Host "[OK] Compilacion exitosa" -ForegroundColor Green
        }

        Write-Host "`n[STOP] Deteniendo $($svc.Name)..." -ForegroundColor Yellow
        Stop-ServiceByName $svc.Name
        Start-Sleep -Seconds 2

        Write-Host "[START] Iniciando $($svc.Name)..." -ForegroundColor Green
        if ($svc.Name -eq "Backend") {
            Write-Host "[INFO] Reinicio Backend con perfil $($script:BackendSpringProfile) (opcion 1 o A para cambiar ambiente)" -ForegroundColor DarkGray
            Start-BackendGradleJob
        }
        else {
            $scriptBlock = { param($dir, $cmd); Set-Location $dir; Invoke-Expression $cmd }
            Start-Job -ScriptBlock $scriptBlock -ArgumentList $svc.WorkDir, $svc.Command -Name $svc.Name | Out-Null
        }

        $waitTime = if ($svc.Name -eq "Backend") { 10 } else { 15 }
        Start-Sleep -Seconds $waitTime

        Write-Host "[OK] $($svc.Name) reiniciado" -ForegroundColor Green
    }

    Write-Host "`n[COMPLETADO] $($servicesToRestart.Count) servicio(s) reiniciado(s)" -ForegroundColor Green
    Start-Sleep -Seconds 2
}

function Stop-ServiceUI {
    Write-Host "`n[STOP] Servicios disponibles:" -ForegroundColor Red

    for ($i = 0; $i -lt $services.Count; $i++) {
        Write-Host "  [$($i+1)] $($services[$i].Name)" -ForegroundColor Yellow
    }

    Write-Host "`nSelecciona servicio(s) para detener (ej: 1 o 1,2): " -NoNewline -ForegroundColor White
    $selection = Read-Host

    $selectedNumbers = $selection -split ',' | ForEach-Object { $_.Trim() } | Where-Object { $_ -match '^\d+$' }

    if ($selectedNumbers.Count -eq 0) {
        Write-Host "[ERROR] Seleccion invalida" -ForegroundColor Red
        Start-Sleep -Seconds 2
        return
    }

    $servicesToStop = @()
    foreach ($num in $selectedNumbers) {
        $index = [int]$num
        if ($index -ge 1 -and $index -le $services.Count) {
            $servicesToStop += $services[$index - 1]
        }
    }

    foreach ($svc in $servicesToStop) {
        Write-Host "`n[STOP] Deteniendo $($svc.Name)..." -ForegroundColor Red
        Stop-ServiceByName $svc.Name
        Write-Host "[OK] $($svc.Name) detenido" -ForegroundColor Green
    }

    Start-Sleep -Seconds 2
}

function Export-AllLogs {
    Write-Host "`n[EXPORT] Exportando logs completos..." -ForegroundColor DarkMagenta

    $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
    $logFolder = "logs_export_$timestamp"
    New-Item -ItemType Directory -Force -Path $logFolder | Out-Null

    $jobs = Get-Job

    foreach ($service in $services) {
        $job = $jobs | Where-Object { $_.Name -eq $service.Name }
        if ($job) {
            $logFile = Join-Path $logFolder "$($service.Name).log"
            Write-Host "[EXPORT] Exportando logs de $($service.Name)..." -ForegroundColor Yellow

            $allLogs = Receive-Job -Name $service.Name -Keep 2>&1
            if ($allLogs) {
                $allLogs | Out-File -FilePath $logFile -Encoding UTF8
                Write-Host "   [OK] Guardado: $logFile" -ForegroundColor Green
            } else {
                "Sin logs disponibles" | Out-File -FilePath $logFile -Encoding UTF8
                Write-Host "   [WARN] Sin logs para este servicio" -ForegroundColor Yellow
            }
        }
    }

    Write-Host "`n[OK] Logs exportados en: $logFolder" -ForegroundColor Green
    Start-Process explorer.exe -ArgumentList $logFolder

    Start-Sleep -Seconds 3
}

# ============================================
# PROGRAMA PRINCIPAL (MENU CICLICO)
# ============================================

while ($true) {
    Show-Menu
    Write-Host "Selecciona una opcion: " -NoNewline -ForegroundColor White
    $option = Read-Host

    switch ($option) {
        "1" { Start-BothServices }
        "A" { Start-BackendOnly }
        "a" { Start-BackendOnly }
        "F" { Start-FrontendOnly }
        "f" { Start-FrontendOnly }
        "2" { Stop-AllServices }
        "3" { Show-ServiceStatus }
        "4" { Show-AllLogs }
        "5" { Show-ServiceLogs }
        "6" { Test-HealthChecks }
        "7" { Restart-ServiceUI }
        "D" { Stop-ServiceUI }
        "d" { Stop-ServiceUI }
        "8" { Export-AllLogs }
        "C" { Test-Compilation }
        "c" { Test-Compilation }
        "S" { Test-SpringContext }
        "s" { Test-SpringContext }
        "0" {
            Write-Host "`n[EXIT] Saliendo..." -ForegroundColor Cyan
            exit
        }
        default {
            Write-Host "`n[ERROR] Opcion invalida" -ForegroundColor Red
            Start-Sleep -Seconds 1
        }
    }
}