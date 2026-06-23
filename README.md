# SmartKB — 智能知识库对话平台

[English](./README.en.md)

> 动态 Prompt 插件系统 | 多模型自由切换 | RAG 知识库问答 | SSE 流式输出

基于 **Spring Boot 3.4 + Vue 3 + LangChain4j** 的全栈智能对话平台。内置 Prompt 动态模板引擎，支持 **DeepSeek / 百炼通义千问** 双模型一键切换，提供文档上传 RAG 能力，SSE 流式实时推送。

---

## 技术栈

| 层 | 技术 | 版本 |
|----|------|------|
| 语言 | JDK | 21 LTS |
| 后端 | Spring Boot | 3.4.3 |
| ORM | MyBatis-Plus | 3.5.9 |
| AI | LangChain4j | 0.36.2 |
| 开发库 | H2 | 2.3.232 |
| 生产库 | MySQL | 8.0 |
| 文档解析 | Apache Tika | 2.9.2 |
| 前端 | Vue 3 + Element Plus | 3.5 + 2.9 |
| 构建 | Vite | 6.0 |
| LLM | DeepSeek / 百炼 | — |

---

## 环境要求

| 工具 | 版本 | 检查 |
|------|------|------|
| JDK | 21+ | `java --version` |
| Maven | 3.6+ | `mvn --version` |
| Node.js | 18+ | `node --version` |
| MySQL | 8.0 (仅生产) | `mysql --version` |

> 必须注册 [DeepSeek](https://platform.deepseek.com) 或 [百炼](https://bailian.console.aliyun.com) 获取 API Key。

---

## 本地开发启动

### 1. 后端

```bash
cd backend

# 设置 API Key（PowerShell）
$env:DEEPSEEK_API_KEY = "sk-你的真实密钥"

# 编译 & 启动
mvn clean compile
mvn spring-boot:run
```

> IDEA：Edit Configurations → Environment variables 填 `DEEPSEEK_API_KEY=sk-xxx`，之后每次运行自动注入。

验证：`GET http://localhost:8080/api/prompts`

### 2. 前端

```bash
cd frontend
npm install
npm run dev
```

浏览器打开 `http://localhost:5173`。

---

## Docker 部署

项目内置自包含部署文件夹 `deploy/`，可直接复制到服务器。

```bash
# 1. 构建产物
.\\deploy\\build.ps1      # Windows
./deploy/build.sh          # Linux / macOS

# 2. 编辑环境变量
cp deploy/.env.example deploy/.env
# 编辑 .env 填入 API Key

# 3. 启动
cd deploy
docker-compose up -d
```

启动后直接访问 `http://服务器IP`。

> 详细说明见 [deploy/README.md](deploy/README.md)。

---

## 配置参数说明

核心配置在 `backend/src/main/resources/application.yml`：

```yaml
smartkb:
  chat:
    max-history-messages: 20          # 对话上下文窗口
  model:
    deepseek:
      api-key: ${DEEPSEEK_API_KEY}   # 从环境变量读取
    bailian:
      api-key: ${BAILIAN_API_KEY}
```

API Key 通过环境变量注入，绝不硬编码在配置文件里。

---

## 数据库切换（H2 / MySQL）

`application.yml` 中已预留两种配置，取消注释即可切换。

`pom.xml` 中同样注释了 MySQL 依赖。切换需同时改两处 + 首次 `sql.init.mode: always` 建表。

---

## API 接口文档

统一响应格式：`{"code":200,"message":"操作成功","data":...}`

### Prompt 模板

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/prompts` | 启用模板列表 |
| GET | `/api/prompts/match?message=xx` | 智能匹配 |
| POST | `/api/prompts` | 新增模板 |
| PUT | `/api/prompts/{id}` | 更新（即时生效） |
| DELETE | `/api/prompts/{id}` | 删除（预设保护） |

### 会话 & 对话

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/conversations` | 创建会话 |
| GET | `/api/conversations` | 会话列表 |
| DELETE | `/api/conversations/{id}` | 删除 |
| POST | `/api/chat/{id}` | 非流式对话 |
| GET | `/api/chat/{id}/stream?content=xxx` | SSE 流式对话 |

### 文档

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/documents` | 上传文档 |
| GET | `/api/documents` | 文档列表 |
| DELETE | `/api/documents/{id}` | 删除 |

---

## 常见问题

**Q：启动报 Authentication Fails？**
A：API Key 未设置。IDEA Run Config → Environment variables 填 `DEEPSEEK_API_KEY=sk-xxx`

**Q：数据重启就丢？**
A：不会。H2 文件存 `backend/data/smartkb.mv.db`，持久保留

**Q：H2 控制台？**
A：`http://localhost:8080/h2-console`，JDBC URL `jdbc:h2:file:./data/smartkb`，用户 `sa`，密码留空

**Q：怎么重置数据库？**
A：删 `backend/data/smartkb.mv.db`，改 `sql.init.mode: always`，重启

**Q：Docker 部署后怎么进容器？**
A：`docker exec -it smartkb-backend sh`
