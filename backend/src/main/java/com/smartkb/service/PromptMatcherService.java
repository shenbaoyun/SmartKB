package com.smartkb.service;

import com.smartkb.entity.model.PromptTemplateDO;

public interface PromptMatcherService {
    PromptTemplateDO matchPrompt(String userMessage);
}