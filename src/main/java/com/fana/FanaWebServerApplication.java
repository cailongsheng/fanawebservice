package com.fana;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.fana.mapper")
@ComponentScan(basePackages = {"com.fana.swagger2", "com.fana"})
public class FanaWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FanaWebServerApplication.class, args);

    }
//上海
    //    @PostConstruct
    //    void setDefaultTimezone() {
    //        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    //    }

// 伦敦
        @PostConstruct
        void setDefaultTimezone() {
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        }
}
