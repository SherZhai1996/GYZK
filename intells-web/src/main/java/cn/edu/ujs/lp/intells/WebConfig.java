package cn.edu.ujs.lp.intells;

import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 上传文件访问路径的配置
 */
@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dr = OSUtils.getBasefilepath();
        dr = dr.replace("\\","/");
        dr = dr.replace(":",":/");

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/DBfile/**").addResourceLocations("file:"+dr+"/");
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        registry.addMapping("/API/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}



