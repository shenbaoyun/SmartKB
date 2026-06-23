package com.smartkb.common.enums;

/**
 * Prompt 匹配模式枚举
 *
 * <p>定义选择 Prompt 模板的策略</p>
 *
 * @author codex
 * @since 1.0.0
 */
public enum PromptMatchModeEnum {

    /** 智能匹配：根据用户问题内容自动选择最合适的 Prompt 模板 */
    AUTO("auto", "智能匹配"),

    /** 手动锁定：由用户手动指定 Prompt 模板 */
    MANUAL("manual", "手动锁定");

    private final String code;
    private final String desc;

    PromptMatchModeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() { return code; }
    public String getDesc() { return desc; }
}
