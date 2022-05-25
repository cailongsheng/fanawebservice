package com.fana.config;

/**
 * 枚举类对象
 */
public enum Status {

    // 公共
    SUCCESS(200, "succeed"),
    //参数校验为空
    PARAMETER_ERROR(1001,"Incorrect parameter format."),
    //用户模块
    PLEASE_REGISTER(1002,"Please register first."),
    PASSWORD_ERROR(1003,"Please enter the correct password."),
    USER_REGISTERED(1004,"The user is registered"),
    FILE_TYPE_ERROR(1005,"file type error"),
    SEND_EMAIL_ERROR(1005,"Email sending failure"),
    CODE_OVERDUE(1006,"The verification code has expired. Please send it again"),
    CODE_EQUALS_FALSE(1007,"The verification code you entered is incorrect"),
    USER_VOID(1008,"user viod"),
    //token校验
    TOKEN_ERROR(2001,"token error"),
    ;

    //bank


    public int code;
    public String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
