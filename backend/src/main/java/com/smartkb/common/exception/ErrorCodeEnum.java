package com.smartkb.common.exception;

/**
 * 错误码枚举
 *
 * <p>统一管理业务异常的错误码和提示信息</p>
 *
 * @author codex
 * @since 1.0.0
 */
public enum ErrorCodeEnum {

    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数有误"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "系统内部错误"),
    CONVERSATION_NOT_FOUND(1001, "会话不存在"),
    PROMPT_TEMPLATE_NOT_FOUND(2001, "Prompt模板不存在"),
    PROMPT_TEMPLATE_PRESET_DELETE(2002, "预设模板不可删除"),
    PROMPT_TEMPLATE_NO_MATCH(2003, "未找到匹配的Prompt模板"),
    MODEL_NOT_FOUND(3001, "模型配置不存在"),
    MODEL_NOT_ACTIVE(3002, "模型未启用"),
    AI_CALL_ERROR(4001, "AI调用失败，请稍后重试"),
    AI_STREAM_ERROR(4002, "AI流式调用异常");

    private final Integer code;
    private final String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() { return code; }
    public String getMessage() { return message; }
}
