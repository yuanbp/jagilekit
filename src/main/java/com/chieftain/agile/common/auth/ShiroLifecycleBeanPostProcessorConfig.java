package com.chieftain.agile.common.auth;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.chieftain.agile.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/21
 *
 * @author Richard on 2018/5/21
 */
@Configuration
public class ShiroLifecycleBeanPostProcessorConfig {

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor postProcessor = new LifecycleBeanPostProcessor();
        return postProcessor;
    }
}
