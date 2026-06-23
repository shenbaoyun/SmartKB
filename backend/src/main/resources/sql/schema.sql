-- SmartKB H2 Schema
-- MODE=MySQL compat

CREATE TABLE IF NOT EXISTS conversation (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    title           VARCHAR(200) NOT NULL DEFAULT 'New Chat',
    model_code      VARCHAR(50)  NOT NULL DEFAULT 'deepseek-v3',
    prompt_id       BIGINT       DEFAULT NULL,
    prompt_mode     VARCHAR(20)  NOT NULL DEFAULT 'auto',
    created_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS message (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    conversation_id BIGINT       NOT NULL,
    role            VARCHAR(20)  NOT NULL,
    content         CLOB         NOT NULL,
    prompt_id       BIGINT       DEFAULT NULL,
    prompt_name     VARCHAR(100) DEFAULT NULL,
    model_code      VARCHAR(50)  DEFAULT NULL,
    created_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_msg_conv ON message(conversation_id);

CREATE TABLE IF NOT EXISTS prompt_template (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    description     VARCHAR(500) DEFAULT NULL,
    system_prompt   CLOB         NOT NULL,
    category        VARCHAR(50)  NOT NULL DEFAULT 'general',
    match_keywords  VARCHAR(500) DEFAULT NULL,
    match_mode      TINYINT      NOT NULL DEFAULT 1,
    is_preset       TINYINT      NOT NULL DEFAULT 0,
    is_active       TINYINT      NOT NULL DEFAULT 1,
    sort_order      INT          NOT NULL DEFAULT 0,
    created_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS model_config (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    code            VARCHAR(50)  NOT NULL,
    base_url        VARCHAR(500) NOT NULL,
    api_key         VARCHAR(200) NOT NULL,
    model_name      VARCHAR(100) NOT NULL DEFAULT '',
    is_active       TINYINT      NOT NULL DEFAULT 1,
    sort_order      INT          NOT NULL DEFAULT 0,
    created_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS knowledge_document (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(200) NOT NULL,
    file_type       VARCHAR(20),
    file_size       BIGINT       DEFAULT 0,
    file_path       VARCHAR(500),
    chunk_count     INT          DEFAULT 0,
    status          VARCHAR(20)  DEFAULT 'PENDING',
    created_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);