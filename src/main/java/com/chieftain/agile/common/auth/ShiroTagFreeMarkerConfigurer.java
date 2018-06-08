package com.chieftain.agile.common.auth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;

/**
 * com.chieftain.agile.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/21
 *
 * @author Richard on 2018/5/21
 */
@Component
public class ShiroTagFreeMarkerConfigurer implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
