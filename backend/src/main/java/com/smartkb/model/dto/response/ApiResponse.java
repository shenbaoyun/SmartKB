package com.smartkb.model.dto.response;

/**
 * 统一 API 响应体
 *
 * <p>所有 Controller 返回的 JSON 都包装为此格式</p>
 *
 * @param <T> 响应数据类型
 * @author codex
 * @since 1.0.0
 */
public class ApiResponse<T> {

    /** 状态码：200 表示成功 */
    private Integer code;

    /** 提示消息 */
    private String message;

    /** 响应数据 */
    private T data;

    private ApiResponse() {}

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.code = 200;
        resp.message = "操作成功";
        resp.data = data;
        return resp;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.code = 200;
        resp.message = message;
        resp.data = data;
        return resp;
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.code = code;
        resp.message = message;
        resp.data = null;
        return resp;
    }

    public Integer getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public void setCode(Integer code) { this.code = code; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
}
