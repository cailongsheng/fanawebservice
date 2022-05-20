package com.fana.utils;



public enum MessageUtils {
    //trolley
    MODIFY_FAIL(6201,"fail to edit !"),
    DELETE_FAIL(6201,"delete to fail !"),
    TROLLEY_TEMPLATE_FAIL(6201,"Invalid mac address !"),
    TROLLEY_ADD_FAIL(6201,"Failed to add trolley !"),
    FERESH_LOG_ADD_FAIL(6201,"Fefresh - failed to add log !"),
    LOG_ADD_FAIL(6201,"ADD - failed to add log !"),
    ADD_TRUE(6000,"Add - Can add"),
    ALREADY_EXISTS(6201,"This trolley already exists locally !"),
    TOOTH_REFRESH_FAIL(6201,"blue Tooth failed to refresh status !"),
    DB_ALREADY_EXISTS(6201,"This trolley already exists in db !"),
    UNKNOWN_EXCETION(6201,"Added trolley unknown exception !"),
    REFRESH_MSG(6000,"refresh success"),

    //logs
    ADD_LOGS_MESSAG_FAIL(7201,"Failed to add logs"),
    UPDATE_LOGES_MESSAGE_FAIL(7201,"Failed to update logs"),
    TROLLET_LOGS_LIST_FAIL(7201,"logs - Failed to get trolley list"),

    //staff
    STAFF_GET_LIST(201,"staff did not get related trolley"),


    //其他
    OTHER_MESSAGE(3200,"Did not match the information");
    public String msg;
    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    MessageUtils(int code, String msg) {
        this.msg = msg;
        this.code= code;
    }
    MessageUtils(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
