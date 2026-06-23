# SmartKB 部署指南

本文件夹可直接复制到服务器，执行 `docker-compose up -d` 一键启动。

---

## 部署架构

```
  用户请求 :80
       |
       v
+-------------+     /api/*     +-------------+     +-------------+
|  Frontend   | -------------> |   Backend   | --> |   MySQL     |
|  Nginx :80  |                |   :8080     |     |   :3306     |
|  (静态文件)  | <------------- |  (Spring)   | <-- |  (数据库)    |
+-------------+   反向代理     +-------------+     +-------------+
                                      |
                                      | (预留)
                                      v
                               +-------------+
                               |    Redis    |
                               |    :6379    |
                               |  (预留缓存)  |
                               +-------------+
```

---

## 目录结构

```
deploy/
|-- docker-compose.yml      # 服务编排
|-- .env                     # 环境变量（从 .env.example 复制）
|-- .env.example             # 环境变量模板
|-- .gitignore               # Git 忽略规则
|-- build.ps1 / build.sh     # 构建脚本
|-- backend/
|   |-- Dockerfile            # 后端镜像
|   +-- smartkb-backend.jar   # 构建产物（build 脚本生成）
|-- frontend/
|   |-- Dockerfile            # 前端镜像（Nginx 静态服务）
|   |-- nginx.conf            # Nginx 配置（含 /api 反向代理）
|   +-- dist/                 # 构建产物（build 脚本生成）
|-- mysql/
|   |-- Dockerfile            # MySQL 镜像
|   +-- init/01-init.sql      # 初始化脚本（建表 + 种子数据）
|-- redis/
|   |-- Dockerfile            # Redis 镜像（预留）
|   +-- redis.conf            # Redis 配置
|-- nginx/                    # 可选网关（多实例负载均衡时使用）
|   |-- Dockerfile
|   +-- nginx.conf
+-- volumes/                  # 数据卷（挂载宿主机持久化）
    |-- mysql_data/
    |-- backend_data/
    +-- redis_data/
```

---

## 首次部署

### 第一步：构建产物

在项目根目录执行：

**Windows：**
```powershell
.\\deploy\\build.ps1
```

**Linux / macOS：**
```bash
./deploy/build.sh
```

脚本会：
- 编译后端 JAR → 复制到 `deploy/backend/smartkb-backend.jar`
- 编译前端静态文件 → 复制到 `deploy/frontend/dist/`
- 检查 `.env` 文件，不存在则自动从 `.env.example` 创建

### 第二步：配置环境变量

编辑 `deploy/.env`，填入真实值：

```env
DEEPSEEK_API_KEY=sk-你的DeepSeek密钥
MYSQL_ROOT_PASSWORD=smartkb123
```

### 第三步：启动

```bash
cd deploy
docker-compose up -d
docker-compose logs -f
docker-compose ps
```

### 第四步：验证

- 前端页面：`http://服务器IP`
- 后端 API：`http://服务器IP:8080/api/prompts`

---

## 常用命令

```bash
docker-compose down              # 停止
docker-compose down -v           # 停止并删除数据卷（重置数据库）
docker-compose restart backend   # 重启单个服务
docker-compose logs -f backend   # 查看后端日志
docker-compose up -d --build     # 重新构建并启动
docker exec -it smartkb-backend sh  # 进入容器
```

---

## 为什么 API Key 用环境变量？

| 方式 | 安全性 | 说明 |
|------|--------|------|
| `${DEEPSEEK_API_KEY}` 从 .env 读取 | ✅ 安全 | .env 被 gitignore，不提交到仓库 |
| 写死在 docker-compose.yml | ❌ 不安全 | 提交到 Git，任何人可见 |
| 写死在 application.yml | ❌ 不安全 | 打包进 Docker 镜像层 |

---

## 服务说明

| 服务 | 基础镜像 | 端口 | 状态 |
|------|---------|------|------|
| MySQL | `mysql:8.0` | 3306 | 必须 |
| Backend | `eclipse-temurin:21-jre-alpine` | 8080 | 必须 |
| Frontend | `nginx:alpine` | 80 | 必须 |
| Redis | `redis:7-alpine` | 6379 | 预留 |

---

## 升级步骤

```bash
# 1. 重新构建产物
.\\build.ps1    # Windows
./build.sh     # Linux

# 2. 重新构建镜像并滚动更新
docker-compose up -d --build

# 3. 清理旧镜像
docker image prune -f
```
