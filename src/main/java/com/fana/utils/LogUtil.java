package com.fana.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/***
 *@author: wwm
 *@description:
 *@creattime: 2021-08-26
 */
@Slf4j
public  class LogUtil {
    /**
     * info
     * @param objects
     */
    @Value("${fana.ip}")
    private static String ip;
    public static void addInfoLog(Object... objects){
        log.info("++++++++++++++++++++++{}+++++++++++++++++++++++",objects[0]);
         log.info("请求路径::{}",objects[1]);
         log.info("请求IP::{}",ip);
         log.info("请求方式::{}","POST");
         log.info("请求时间::{}", DataUtils.getTime());
         log.info("请求参数::{}",objects[2]);
    }

    /**
     * error
     * @param objects
     */
    public static void addErrorLog(Object... objects){
        log.error("++++++++++++++++++++++{}+++++++++++++++++++++++",objects[0]);
        log.error("请求路径::{}", objects[1]);
        log.error("请求IP::{}",ip);
        log.error("请求方式::{}","POST");
        log.error("请求时间::{}", DataUtils.getTime());
        log.error("请求参数::{}",objects[2]);
    }

    public static void returnInfoLog(Object... objects){
        log.info("++++++++++++++++++++++{}+++++++++++++++++++++++",objects[0]);
        log.info("请求路径::{}",objects[1]);
        log.info("请求IP::{}",ip);
        log.info("请求方式::{}","POST");
        log.info("请求时间::{}", DataUtils.getTime());
        log.info("返回结果::{}",objects[2]);
    }
}
