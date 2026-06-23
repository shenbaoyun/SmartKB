-- ============================================================
-- SmartKB MySQL 初始化脚本（Docker 首次启动自动执行）
-- ============================================================

CREATE TABLE IF NOT EXISTS conversation (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    title           VARCHAR(200) NOT NULL DEFAULT 'New Chat',
    model_code      VARCHAR(50)  NOT NULL DEFAULT 'deepseek-v3',
    prompt_id       BIGINT       DEFAULT NULL,
    prompt_mode     VARCHAR(20)  NOT NULL DEFAULT 'auto',
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_updated_time (updated_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS message (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    conversation_id BIGINT       NOT NULL,
    role            VARCHAR(20)  NOT NULL,
    content         TEXT         NOT NULL,
    prompt_id       BIGINT       DEFAULT NULL,
    prompt_name     VARCHAR(100) DEFAULT NULL,
    model_code      VARCHAR(50)  DEFAULT NULL,
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_conversation_id (conversation_id),
    INDEX idx_conversation_created (conversation_id, created_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS prompt_template (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    description     VARCHAR(500) DEFAULT NULL,
    system_prompt   TEXT         NOT NULL,
    category        VARCHAR(50)  NOT NULL DEFAULT 'general',
    match_keywords  VARCHAR(500) DEFAULT NULL,
    match_mode      TINYINT(1)   NOT NULL DEFAULT 1,
    is_preset       TINYINT(1)   NOT NULL DEFAULT 0,
    is_active       TINYINT(1)   NOT NULL DEFAULT 1,
    sort_order      INT          NOT NULL DEFAULT 0,
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS model_config (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    code            VARCHAR(50)  NOT NULL,
    base_url        VARCHAR(500) NOT NULL,
    api_key         VARCHAR(200) NOT NULL,
    model_name      VARCHAR(100) NOT NULL DEFAULT '',
    is_active       TINYINT(1)   NOT NULL DEFAULT 1,
    sort_order      INT          NOT NULL DEFAULT 0,
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS knowledge_document (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(200) NOT NULL,
    file_type       VARCHAR(20),
    file_size       BIGINT       DEFAULT 0,
    file_path       VARCHAR(500),
    chunk_count     INT          DEFAULT 0,
    status          VARCHAR(20)  DEFAULT 'PENDING',
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 种子数据（INSERT IGNORE 已存在则跳过）
INSERT IGNORE INTO prompt_template (id, name, description, system_prompt, category, match_keywords, match_mode, is_preset, sort_order) VALUES
(1, 'General', 'General purpose', 'You are a friendly AI assistant. Answer concisely.', 'general', 'hello,help,what,how', 1, 1, 1),
(2, 'Java Expert', 'Java programming', 'You are a senior Java developer expert in JDK 21 and Spring Boot.', 'code', 'Java,Spring,JVM,thread,code,JDK', 1, 1, 10),
(3, 'Python Expert', 'Python programming', 'You are a senior Python developer.', 'code', 'Python,Flask,Django,pip,pandas', 1, 1, 20),
(4, 'Translator', 'Translation', 'You are a professional translator. Output translation only.', 'translate', 'translate,Chinese,English,Japanese', 1, 1, 30),
(5, 'SQL Expert', 'Database & SQL', 'You are a database expert specialized in SQL optimization.', 'code', 'SQL,MySQL,database,table,query,index', 1, 1, 40),
(6, 'Summarizer', 'Text summarization', 'Extract key points from long content. Use bullet points.', 'office', 'summary,summarize,tldr,key points', 1, 1, 50),
(7, 'Code Review', 'Code review', 'Review code from 4 angles: style, performance, security, maintainability.', 'code', 'code review,review,refactor', 1, 1, 60);

INSERT IGNORE INTO model_config (id, name, code, base_url, api_key, model_name, is_active, sort_order) VALUES
(1, 'DeepSeek V3', 'deepseek-v3', 'https://api.deepseek.com/v1', 'sk-placeholder', 'deepseek-chat', 1, 1),
(2, 'Qwen Max', 'qwen-max', 'https://dashscope.aliyuncs.com/compatible-mode/v1', 'sk-placeholder', 'qwen-max', 1, 10),
(3, 'Qwen Plus', 'qwen-plus', 'https://dashscope.aliyuncs.com/compatible-mode/v1', 'sk-placeholder', 'qwen-plus', 1, 20);