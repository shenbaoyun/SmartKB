package com.smartkb.common.enums;

/**
 * 消息角色枚举
 *
 * <p>定义对话消息的发送者角色</p>
 *
 * @author codex
 * @since 1.0.0
 */
public enum MessageRoleEnum {

    /** 用户消息 */
    USER("USER", "用户消息"),

    /** AI 助手消息 */
    ASSISTANT("ASSISTANT", "AI助手"),

    /** 系统消息 */
    SYSTEM("SYSTEM", "系统消息");

    private final String code;
    private final String desc;

    MessageRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() { return code; }
    public String getDesc() { return desc; }
}

