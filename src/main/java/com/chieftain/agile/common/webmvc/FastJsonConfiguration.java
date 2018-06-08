package com.chieftain.agile.common.webmvc;

import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * com.chieftain.junite.common.webmvc [workset_idea_01]
 * Created by Richard on 2018/5/14
 *
 * @author Richard on 2018/5/14
 */

@Configuration
public class FastJsonConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 修改自定义消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        //1.创建一个convert消息转换对象
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        //2.创建一个fastJson的配置对象,然后配置格式化信息
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //3.在convert中添加配置信息
        fastConvert.setFastJsonConfig(config);
        //4.将convert添加到converts里面
        converters.add(fastConvert);
    }

    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1.创建一个convert消息转换对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2.创建一个fastJson的配置对象,然后配置格式化信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //3.在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converters = fastConverter;
        return new HttpMessageConverters(converters);
    }
}
