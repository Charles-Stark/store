package com.example.backend.pojo;

import com.example.backend.constant.Status;
import com.example.backend.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult {

    private Integer code;
    private String msg;
    private Object data;

    public static ApiResult of(Integer code, String msg, Object data) {
        return new ApiResult(code, msg, data);
    }

    public static ApiResult ofSuccess(Object data) {
        return ofStatus(Status.OK, data);
    }

    public static ApiResult ofMessage(String msg) {
        return of(Status.OK.getCode(), msg, null);
    }

    public static ApiResult ofStatus(Status status) {
        return ofStatus(status, null);
    }

    public static ApiResult ofStatus(Status status, Object data) {
        return of(status.getCode(), status.getMsg(), data);
    }

    public static <T extends BaseException> ApiResult ofException(T t, Object data) {
        return of(t.getCode(), t.getMsg(), data);
    }

    public static <T extends BaseException> ApiResult ofException(T t) {
        return ofException(t, null);
    }

    public static ApiResult ofError() {
        return ofStatus(Status.UNKNOWN_ERROR);
    }

}
