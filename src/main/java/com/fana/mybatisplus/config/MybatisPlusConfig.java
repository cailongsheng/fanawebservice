package com.fana.mybatisplus.config;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 配置分页插件
 * 创建/更新时间自动跟新
 */
@Configuration
@MapperScan("com.fana.mapper")
public class MybatisPlusConfig  implements MetaObjectHandler {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称，不是字段名称
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
//        this.setFieldValByName("status", 0, metaObject);
        this.setFieldValByName("createAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("isDelete", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateAt", LocalDateTime.now(), metaObject);
    }
}
