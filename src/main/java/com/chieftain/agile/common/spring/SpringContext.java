package com.chieftain.agile.common.spring;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * com.chieftain.junite.common.spring [workset_idea_01]
 * Created by Richard on 2018/5/21
 *
 * @author Richard on 2018/5/21
 */
@Configuration
public class SpringContext {

    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator autoProxyCreator(){
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    @Bean
    public SimpleMappingExceptionResolver exceptionResolver(){
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties settings = new Properties();
        settings.setProperty("org.apache.shiro.authz.AuthorizationException","/401");
        settings.setProperty("org.apache.shiro.authz.UnauthorizedException","/500");
        exceptionResolver.setExceptionMappings(settings);
        return exceptionResolver;
    }
}
