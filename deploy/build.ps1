# ============================================================
# SmartKB 部署构建脚本（Windows PowerShell）
# 使用：.\build.ps1
# ============================================================

$ErrorActionPreference = "Stop"
$ROOT = Split-Path -Parent $PSScriptRoot

Write-Host "=== SmartKB 部署构建 ===" -ForegroundColor Cyan

Write-Host "[1/3] 构建后端 JAR..." -ForegroundColor Yellow
$env:JAVA_HOME = (Get-ChildItem "D:\Java\jdk-21*" | Select-Object -First 1).FullName
Push-Location "$ROOT\backend"
mvn clean package -DskipTests -q
$jar = Get-ChildItem target\smartkb-backend-*.jar | Select-Object -First 1
Copy-Item $jar.FullName "$ROOT\deploy\backend\smartkb-backend.jar" -Force
Pop-Location
Write-Host "       后端 JAR 已复制" -ForegroundColor Green

Write-Host "[2/3] 构建前端静态文件..." -ForegroundColor Yellow
Push-Location "$ROOT\frontend"
npm run build 2>&1 | Out-Null
if (Test-Path "$ROOT\deploy\frontend\dist") { Remove-Item -Recurse -Force "$ROOT\deploy\frontend\dist" }
Copy-Item -Recurse "dist" "$ROOT\deploy\frontend\dist" -Force
Pop-Location
Write-Host "       前端静态文件已复制" -ForegroundColor Green

Write-Host "[3/3] 检查 .env..." -ForegroundColor Yellow
if (-not (Test-Path "$ROOT\deploy\.env")) {
    Copy-Item "$ROOT\deploy\.env.example" "$ROOT\deploy\.env"
    Write-Host "       .env 已从模板创建 —— 请编辑填入真实 API Key！" -ForegroundColor Red
} else {
    Write-Host "       .env 已存在" -ForegroundColor Green
}

Write-Host "=== 构建完成 ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "下一步："
Write-Host "  1. 编辑 deploy\\.env 填入真实 API Key"
Write-Host "  2. 复制 deploy\\ 文件夹到服务器"
Write-Host "  3. docker-compose up -d"
