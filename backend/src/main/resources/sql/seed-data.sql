MERGE INTO prompt_template (id, name, description, system_prompt, category, match_keywords, match_mode, is_preset, sort_order)
KEY(id) VALUES
(1, 'General', 'General purpose', 'You are a friendly AI assistant. Answer concisely.', 'general', 'hello,help,what,how', 1, 1, 1),
(2, 'Java Expert', 'Java programming', 'You are a senior Java developer expert in JDK 21 and Spring Boot.', 'code', 'Java,Spring,JVM,thread,code,JDK', 1, 1, 10),
(3, 'Python Expert', 'Python programming', 'You are a senior Python developer.', 'code', 'Python,Flask,Django,pip,pandas', 1, 1, 20),
(4, 'Translator', 'Translation', 'You are a professional translator. Output translation only.', 'translate', 'translate,Chinese,English,Japanese', 1, 1, 30),
(5, 'SQL Expert', 'Database & SQL', 'You are a database expert specialized in SQL optimization.', 'code', 'SQL,MySQL,database,table,query,index', 1, 1, 40),
(6, 'Summarizer', 'Text summarization', 'You extract key points from long content. Use bullet points.', 'office', 'summary,summarize,tldr,key points', 1, 1, 50),
(7, 'Code Review', 'Code review', 'Review code from 4 angles: style, performance, security, maintainability.', 'code', 'code review,review,refactor', 1, 1, 60);

MERGE INTO model_config (id, name, code, base_url, api_key, model_name, is_active, sort_order)
KEY(id) VALUES
(1, 'DeepSeek V3',  'deepseek-v3', 'https://api.deepseek.com/v1', 'sk-placeholder', 'deepseek-chat', 1, 1),
(2, 'Qwen Max',    'qwen-max',    'https://dashscope.aliyuncs.com/compatible-mode/v1', 'sk-placeholder', 'qwen-max', 1, 10),
(3, 'Qwen Plus',   'qwen-plus',   'https://dashscope.aliyuncs.com/compatible-mode/v1', 'sk-placeholder', 'qwen-plus', 1, 20);