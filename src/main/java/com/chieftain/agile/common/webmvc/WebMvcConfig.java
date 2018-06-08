package com.chieftain.agile.common.webmvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * com.chieftain.junite.common.webmvc [workset_idea_01]
 * Created by Richard on 2018/5/10
 *
 * @author Richard on 2018/5/10
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
