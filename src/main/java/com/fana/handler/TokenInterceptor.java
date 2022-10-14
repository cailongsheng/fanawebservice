package com.fana.handler;

import com.fana.config.Status;
import com.fana.exception.CustomException;
import com.fana.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class TokenInterceptor extends WebMvcConfigurationSupport {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {

            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hander) throws Exception {
                // 放行OPTIONS请求，防止因跨域导致的请求失败
                if (request.getMethod().toUpperCase().equals("OPTIONS")) {
                    return true;
                }
                // 非OPTIONS请求TOKEN验证
                String token = request.getHeader("Authorization");
                if (token == null) {
                    throw new CustomException(Status.TOKEN_ERROR.code, Status.TOKEN_ERROR.message);
                }
                boolean flag = tokenManager.checkToken(token);
                return flag;


            }

        };


        registry.addInterceptor(handlerInterceptor)
//                .addPathPatterns(
//                        "/user/**"
//                )
                .excludePathPatterns(
                        "/user/web/login",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html/**",
                        "/error",
                        "/file/**"

                );

    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
