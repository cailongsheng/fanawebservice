package com.fana.aop;

import cn.hutool.core.util.ObjectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by pei.
 * 日志打印
 */
@Aspect
@Component
public class WebLogAcpect {

    private Logger logger = LoggerFactory.getLogger(WebLogAcpect.class);

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
    @Pointcut("execution(public * com.fana.controller..*.*(..))")
    public void webLog(){}

    /**
     * 前置通知：在连接点之前执行的通知
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("---------------------**********--------------------------");
        logger.info("URL(接口url): " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD(请求方式) : " + request.getMethod());
        logger.info("IP（ip） : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD（方法名称） : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS （入参）: " + Arrays.toString(joinPoint.getArgs()));
        logger.info("（客户端ip）: " + request.getRemoteAddr());
        logger.info("（客户端端口）: " + request.getRemotePort());
        logger.info("（客户端user）: " + request.getRemoteUser());
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        logger.info("RESPONSE :（出参） " + (ObjectUtil.isNull(ret)?null:ret.toString()));
    }
}

