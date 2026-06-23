# SmartKB - Intelligent Knowledge Base Chat Platform

[简体中文](./README.zh-CN.md)

> Dynamic Prompt Plugin System | Multi-Model Switching | RAG Q&A | SSE Streaming

A full-stack intelligent chat platform built with **Spring Boot 3.4 + Vue 3 + LangChain4j**. Features a dynamic Prompt template engine, supports **DeepSeek / Alibaba Bailian Qianwen** dual-model switching, RAG-powered document Q&A, and real-time SSE streaming output.

---

## Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | JDK | 21 LTS |
| Backend | Spring Boot | 3.4.3 |
| ORM | MyBatis-Plus | 3.5.9 |
| AI | LangChain4j | 0.36.2 |
| DB (dev) | H2 | 2.3.232 |
| DB (prod) | MySQL | 8.0 |
| Doc Parser | Apache Tika | 2.9.2 |
| Frontend | Vue 3 + Element Plus | 3.5 + 2.9 |
| Build | Vite | 6.0 |
| LLM | DeepSeek / Bailian | - |

---

## Prerequisites

| Tool | Version | Check |
|------|---------|-------|
| JDK | 21+ | `java --version` |
| Maven | 3.6+ | `mvn --version` |
| Node.js | 18+ | `node --version` |
| MySQL | 8.0 (prod only) | `mysql --version` |

> You must register at [DeepSeek](https://platform.deepseek.com) or [Bailian](https://bailian.console.aliyun.com) to obtain an API Key.

---

## Local Development

### 1. Backend

```bash
cd backend

# Set API Key (PowerShell)
$env:DEEPSEEK_API_KEY = "sk-your-real-key"

# Compile & Run
mvn clean compile
mvn spring-boot:run
```

> IDEA: Run Configurations -> Environment variables -> `DEEPSEEK_API_KEY=sk-xxx`

Verify: `GET http://localhost:8080/api/prompts`

### 2. Frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173` in browser.

---

## Docker Deployment

The project includes a self-contained `deploy/` folder that can be copied directly to a server.

```bash
# Build artifacts
.\\deploy\\build.ps1      # Windows
./deploy/build.sh          # Linux / macOS

# Configure environment
cp deploy/.env.example deploy/.env
# Edit .env with your real API Key

# Start
cd deploy
docker-compose up -d
```

Access `http://your-server-ip` after startup.

> See [deploy/README.md](deploy/README.md) for details.

---

## Configuration

Core config in `backend/src/main/resources/application.yml`:

```yaml
smartkb:
  chat:
    max-history-messages: 20          # context window size
  model:
    deepseek:
      api-key: ${DEEPSEEK_API_KEY}   # from env var, never hardcoded
    bailian:
      api-key: ${BAILIAN_API_KEY}
```

API Key is injected via environment variable - secure and supports different keys per environment.

---

## Database Switching (H2 / MySQL)

Both configs are pre-defined in `application.yml`. Uncomment to switch. Also update `pom.xml` dependencies.

---

## API Reference

Uniform response: `{"code":200,"message":"success","data":...}`

### Prompts

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/prompts` | List active templates |
| GET | `/api/prompts/match?message=xx` | Smart match |
| POST | `/api/prompts` | Create template |
| PUT | `/api/prompts/{id}` | Update (instant) |
| DELETE | `/api/prompts/{id}` | Delete (preset protected) |

### Conversations & Chat

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/conversations` | Create conversation |
| GET | `/api/conversations` | List conversations |
| DELETE | `/api/conversations/{id}` | Delete |
| POST | `/api/chat/{id}` | Non-streaming chat |
| GET | `/api/chat/{id}/stream?content=xxx` | SSE streaming chat |

### Documents

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/documents` | Upload document |
| GET | `/api/documents` | List documents |
| DELETE | `/api/documents/{id}` | Delete |

---

## Core Features

- **Dynamic Prompt Plugin**: Templates stored in DB, edit = instant effect, no restart
- **Smart Matching**: Keyword + LLM classifier, auto-select best Prompt
- **Multi-Model**: DeepSeek V3 / Qwen-Max / Qwen-Plus, dropdown switch in frontend
- **RAG**: Upload PDF/TXT/MD/DOCX -> Tika parse -> Embedding -> auto-retrieve in chat
- **SSE Streaming**: Token-by-token push, typewriter effect

---

## FAQ

**Q: Authentication Fails on startup?**
A: API Key not set. IDEA Run Config -> Environment variables -> `DEEPSEEK_API_KEY=sk-xxx`

**Q: Data lost after restart?**
A: No. H2 file mode persists in `backend/data/smartkb.mv.db`

**Q: H2 console?**
A: `http://localhost:8080/h2-console`, URL `jdbc:h2:file:./data/smartkb`, user `sa`, no password

**Q: Reset database?**
A: Delete `backend/data/smartkb.mv.db`, set `sql.init.mode: always`, restart

**Q: Access container after Docker deploy?**
A: `docker exec -it smartkb-backend sh`
