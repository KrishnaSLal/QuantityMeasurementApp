$ErrorActionPreference = "Stop"

$workspaceRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

$services = @(
    @{
        Name = "Eureka Server"
        Path = Join-Path $workspaceRoot "eurekaserver"
        DelaySeconds = 12
    },
    @{
        Name = "User Service"
        Path = Join-Path $workspaceRoot "user-service"
        DelaySeconds = 10
    },
    @{
        Name = "Measurement Service"
        Path = Join-Path $workspaceRoot "measurement-service"
        DelaySeconds = 10
    },
    @{
        Name = "API Gateway"
        Path = Join-Path $workspaceRoot "apigateway"
        DelaySeconds = 0
    }
)

foreach ($service in $services) {
    if (-not (Test-Path $service.Path)) {
        throw "Service folder not found: $($service.Path)"
    }

    $mavenWrapper = Join-Path $service.Path "mvnw.cmd"
    if (-not (Test-Path $mavenWrapper)) {
        throw "Maven wrapper not found: $mavenWrapper"
    }

    Write-Host "Starting $($service.Name)..." -ForegroundColor Cyan

    $command = "Set-Location '$($service.Path)'; .\mvnw.cmd spring-boot:run"

    Start-Process powershell.exe -ArgumentList @(
        "-NoExit",
        "-Command",
        $command
    )

    if ($service.DelaySeconds -gt 0) {
        Write-Host "Waiting $($service.DelaySeconds)s before starting the next service..." -ForegroundColor DarkGray
        Start-Sleep -Seconds $service.DelaySeconds
    }
}

Write-Host ""
Write-Host "All service windows have been launched." -ForegroundColor Green
Write-Host "Startup order: Eureka -> User Service -> Measurement Service -> API Gateway" -ForegroundColor Green
Write-Host "Check each window for successful startup logs." -ForegroundColor Yellow
