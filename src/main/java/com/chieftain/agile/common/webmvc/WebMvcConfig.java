package com.chieftain.agile.common.webmvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

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

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter(){
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<MediaType> list = new ArrayList();
        Map<String,String> map = new HashMap<>();
        MediaType mediaType = null;
        map.put("charset","UTF-8");
        mediaType = new MediaType("text","plain",map);
        list.add(mediaType);
        converter.setSupportedMediaTypes(list);
        map.clear();
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(fastJsonHttpMessageConverter());
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> list = new ArrayList();
        Map<String,String> map = new HashMap<>();
        MediaType mediaType = null;
        map.put("charset","UTF-8");
        mediaType = new MediaType("text","htmlType",map);
        list.add(mediaType);
        converter.setSupportedMediaTypes(list);
        map.clear();
        map.put("charset","UTF-8");
        mediaType = new MediaType("application","json",map);
        list.add(mediaType);
        converter.setSupportedMediaTypes(list);
        map.clear();
        return converter;
    }
}
