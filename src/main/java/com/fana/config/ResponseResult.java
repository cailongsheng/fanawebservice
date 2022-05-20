package com.fana.config;

import lombok.Data;

@Data
public class ResponseResult<T> {


    public static final Integer OK = 200;
    public static final Integer ERROR = -1;

    private Integer code;
    private String message;
    private T data;

    public ResponseResult() {

    }

    public ResponseResult(Integer code) {
        this.code = code;

    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult success(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(OK);
        responseResult.setMessage("succeed");
        return responseResult;
    }

    public static ResponseResult success(Object data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(OK);
        responseResult.setMessage("succeed");
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult error(Object data){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ERROR);
        responseResult.setMessage("error");
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult error(Integer code,String message){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }


}
