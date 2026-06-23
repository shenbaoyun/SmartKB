-- ============================================================
-- SmartKB 智能对话平台 - 建表脚本
-- 数据库：MySQL 8.0
-- 字符集：utf8mb4
-- 说明：全部独立单表，无外键约束，应用层保证一致性
-- ============================================================

CREATE DATABASE IF NOT EXISTS smartkb
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE smartkb;

-- 表1：对话会话表 (conversation)
CREATE TABLE IF NOT EXISTS conversation (
    id              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    title           VARCHAR(200) NOT NULL DEFAULT '新对话' COMMENT '会话标题',
    model_code      VARCHAR(50)  NOT NULL DEFAULT 'deepseek-v3' COMMENT '当前使用的模型代码',
    prompt_id       BIGINT       DEFAULT NULL            COMMENT '当前锁定的Prompt模板ID，NULL表示智能匹配模式',
    prompt_mode     VARCHAR(20)  NOT NULL DEFAULT 'auto' COMMENT 'Prompt模式：auto=智能匹配, manual=手动锁定',
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_updated_time (updated_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='对话会话表';

-- 表2：对话消息表 (message)
CREATE TABLE IF NOT EXISTS message (
    id              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    conversation_id BIGINT       NOT NULL                 COMMENT '会话ID，关联 conversation 表',
    role            VARCHAR(20)  NOT NULL                 COMMENT '角色：USER=用户, ASSISTANT=AI助手, SYSTEM=系统',
    content         TEXT         NOT NULL                 COMMENT '消息内容',
    prompt_id       BIGINT       DEFAULT NULL             COMMENT '生成此消息时使用的Prompt模板ID',
    prompt_name     VARCHAR(100) DEFAULT NULL             COMMENT '生成此消息时使用的Prompt模板名称（冗余，便于展示）',
    model_code      VARCHAR(50)  DEFAULT NULL             COMMENT '生成此消息时使用的模型代码',
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    INDEX idx_conversation_id (conversation_id),
    INDEX idx_conversation_created (conversation_id, created_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='对话消息表';

-- 表3：Prompt 模板表 (prompt_template)
CREATE TABLE IF NOT EXISTS prompt_template (
    id              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    name            VARCHAR(100) NOT NULL                 COMMENT '模板名称，如：Java专家',
    description     VARCHAR(500) DEFAULT NULL             COMMENT '模板描述，简要说明适用场景',
    system_prompt   TEXT         NOT NULL                 COMMENT '系统提示词，即注入到 LLM 的 SystemMessage',
    category        VARCHAR(50)  NOT NULL DEFAULT '对话'  COMMENT '分类：对话/编程/翻译/办公/自定义',
    match_keywords  VARCHAR(500) DEFAULT NULL             COMMENT '匹配关键词，逗号分隔，用于智能匹配',
    match_mode      TINYINT(1)   NOT NULL DEFAULT 1       COMMENT '匹配模式：1=允许智能匹配, 0=仅手动选择',
    is_preset       TINYINT(1)   NOT NULL DEFAULT 0       COMMENT '是否预设：1=系统预设（不可删除）, 0=用户自定义',
    is_active       TINYINT(1)   NOT NULL DEFAULT 1       COMMENT '是否启用：1=启用, 0=禁用',
    sort_order      INT          NOT NULL DEFAULT 0       COMMENT '排序字段，值越小越靠前',
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_category (category),
    INDEX idx_is_active (is_active),
    INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Prompt模板表（动态插件）';

-- 表4：模型配置表 (model_config)
CREATE TABLE IF NOT EXISTS model_config (
    id              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    name            VARCHAR(100) NOT NULL                 COMMENT '模型显示名称，如：DeepSeek V3',
    code            VARCHAR(50)  NOT NULL                 COMMENT '模型唯一代码，前端通过此值切换，如：deepseek-v3',
    base_url        VARCHAR(500) NOT NULL                 COMMENT 'API 基础地址',
    api_key         VARCHAR(200) NOT NULL                 COMMENT 'API 密钥',
    model_name      VARCHAR(100) NOT NULL DEFAULT ''      COMMENT '实际调用时的模型名，如：deepseek-chat / qwen-max',
    is_active       TINYINT(1)   NOT NULL DEFAULT 1       COMMENT '是否启用',
    sort_order      INT          NOT NULL DEFAULT 0       COMMENT '排序字段',
    created_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模型配置表';

