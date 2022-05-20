package com.fana.handler;




import com.fana.config.ResponseResult;
import com.fana.exception.BaseException;
import com.fana.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 
 * @author dhamecha
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseResult baseException(BaseException e) {
        log.error(1+e.getMessage(), e);
        return new ResponseResult(-1,e.getMessage());
    }



    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e)
    {
        log.error(2+e.getMessage(), e);
        return new ResponseResult(-1,e.getMessage());
    }
//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseResult handleException2(IllegalStateException e)
//    {
//        log.error(e.getMessage(), e);
//        return ResponseResult.error("文件过大");
//    }

    /**
     * 运行时异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleException1(Exception e)
    {
        log.error(e.getMessage(), e);
        return new ResponseResult(-1,"unknown exception !");
    }



    /**
     * 自定义
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult handleException2(CustomException e)
    {
        log.error(e.getMessage(), e);
        return new ResponseResult(e.getCode(),e.getMessage());
    }

    /**
     * json格式捕获
     * @param e
     * JsonParseException
     */
    //json format error!
    @ExceptionHandler(cn.hutool.json.JSONException.class)
    public ResponseResult jsonException(cn.hutool.json.JSONException e)
    {
        log.error(e.getMessage(), e);
        return ResponseResult.error(-1,"Parameter format error!");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(4+e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseResult.error(message);
    }









    /**
     * 基础异常
     *//*

    *//**
     * 业务异常
     *//*
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e)
    {
        if (StringUtils.isNull(e.getCode()))
        {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e)
    {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handleAccountExpiredException(AccountExpiredException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handleUsernameNotFoundException(UsernameNotFoundException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    *//**
     * 自定义验证异常
     *//*
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    *//**
     * 自定义验证异常
     *//*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    *//**
     * 演示模式异常
     *//*
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult demoModeException(DemoModeException e)
    {
        return AjaxResult.error("演示模式，不允许操作");
    }*/
}
