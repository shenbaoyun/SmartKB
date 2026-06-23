#!/bin/bash
# ============================================================
# SmartKB 部署构建脚本（Linux / macOS）
# 使用：./build.sh
# ============================================================

set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"

echo "=== SmartKB 部署构建 ==="

echo "[1/3] 构建后端 JAR..."
cd "$ROOT/backend"
mvn clean package -DskipTests -q
cp target/smartkb-backend-*.jar "$ROOT/deploy/backend/smartkb-backend.jar"
echo "       后端 JAR 已复制"

echo "[2/3] 构建前端静态文件..."
cd "$ROOT/frontend"
npm run build --silent
rm -rf "$ROOT/deploy/frontend/dist"
cp -r dist "$ROOT/deploy/frontend/dist"
echo "       前端静态文件已复制"

echo "[3/3] 检查 .env..."
if [ ! -f "$ROOT/deploy/.env" ]; then
    cp "$ROOT/deploy/.env.example" "$ROOT/deploy/.env"
    echo "       .env 已从模板创建 —— 请编辑填入真实 API Key！"
else
    echo "       .env 已存在"
fi

echo "=== 构建完成 ==="
echo ""
echo "下一步："
echo "  1. 编辑 deploy/.env 填入真实 API Key"
echo "  2. 复制 deploy/ 文件夹到服务器"
echo "  3. docker-compose up -d"
