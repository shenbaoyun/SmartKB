package com.smartkb.common.exception;

/**
 * 业务异常
 *
 * <p>由 GlobalExceptionHandler 统一捕获并返回标准 ApiResponse</p>
 *
 * @author codex
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    /** 错误码 */
    private final Integer code;

    /**
     * 使用错误码枚举构造异常
     *
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 使用错误码枚举 + 自定义消息构造异常
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误消息
     */
    public BusinessException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public Integer getCode() { return code; }
}
