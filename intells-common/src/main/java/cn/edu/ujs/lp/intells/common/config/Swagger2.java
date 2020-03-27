package cn.edu.ujs.lp.intells.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi(){
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("hosp-ID").description("医院ID").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //指向自己的controller即可
                .apis(RequestHandlerSelectors.basePackage("cn.edu.ujs.lp.intells.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    /**
     * 设置文档信息
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("集团型智慧后勤管理平台API V1.2")
                //描述
                .description("更多资料, 请查看: https://blog.csdn.net/q343509740")
                //设置作者联系方式,可有可无
                .contact("UJS")
                //版本号
                .version("1.0")
                .build();
    }
}

//public class Swagger2 {

  /*  @Bean
    public Docket createRestApi(){
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<springfox.documentation.service.Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("hosp-ID").description("医院ID").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //指向自己的controller即可
                .apis(RequestHandlerSelectors.basePackage("cn.edu.ujs.lp.intells.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }



    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("SpringBoot中使用Swagger2构建RESTful API")
                //描述
                .description("更多资料, 请查看: https://blog.csdn.net/q343509740")
                //设置作者联系方式,可有可无
                .contact("UJS")
                //版本号
                .version("1.0")
                .build();
    }
*/
/*        @Bean
        public Docket swaggerSpringMvcPlugin() {
            return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
        }


    }
 */


