package com.example.backend.constant;

import lombok.Getter;

@Getter
public enum Status {

    OK(0, "操作成功"),
    UNKNOWN_ERROR(500, "服务器出错啦"),

    USER_EXISTS(1001, "用户已存在"),
    USER_NOT_EXISTS(1002, "用户不存在"),
    ALREADY_LOGGED_IN(1003, "用户已登录"),
    NOT_LOGGED_IN(1004, "用户未登录"),
    WRONG_PASSWORD(1005, "密码错误"),
    USERNAME_TOO_LONG(1006, "用户名最多20位"),
    USERNAME_EMPTY(1007, "用户名不能为空"),
    ;

    private final Integer code;
    private final String msg;

    Status(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
