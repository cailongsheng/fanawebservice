package com.fana.swagger2;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//swagger2的配置文件，在项目的启动类的同级文件建立
@Configuration
@EnableSwagger2
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
//@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class Swagger2 implements WebMvcConfigurer {
    //动态配置多环境是否开启
//    @Value("${swagger.enable}")
//    private boolean swagger2Enable;


    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等@Bean
    public Docket createRestApi() {
//        //添加head参数配置start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name("access_token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.fana"))
                .paths(PathSelectors.any())
//                .pathMapping("/")
                .build();
//                .globalOperationParameters(pars);//注意这里

    }

    /**
     * Docket类：一个用于作为进入swagger-springmvc框架的主要接口的构建器。提供合理的默认设置和方便的配置方法
     * @return
     */
//    @Bean
//    public Docket createApiDocket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(swagger2Enable)//动态配置是否开启swagger，生产环境设置为false
//                .groupName("延庆版本")//设置栏目名称，如果包含多个栏目，只需要将该生成Docket的方法复制多份，修改方法名和gropname的名称（不支持多个Docket用同样的groupName）和即可
//                .pathMapping("/")//设置api根路径
//                .apiInfo(apiInfo())//也可以不通过方法直接在参数中new出来
//                .select()//启动用于api选择的构造器。返回api选择生成器。要完成api选择器的构建，需要调用api选择器的构建方法，这将在调用build()方法时自动返回到构建docket
//                .apis(RequestHandlerSelectors.basePackage("com.dhc"))// controller路径
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Bean
//    public Docket createApiDocketSpare() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(swagger2Enable)
//                .groupName("备用栏目")
//                .pathMapping("/")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.dhc"))
//                .paths(PathSelectors.any())
//                .build();
//    }



    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                // 创建人信息
                .contact(new Contact("MrFana",  "",  "2746680956@qq.com"))
                // 版本号
                .version("1.0")
                // 描述
                .description("API 描述")
                .build();
    }



}