package com.chieftain.agile.common.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chieftain.agile.common.cache.jedis.JedisCacheManager;
import com.chieftain.agile.common.cache.redis.RedisCacheManager;

/**
 * com.chieftain.junite.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/14
 *
 * @author Richard on 2018/5/14
 */
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfig {

    @Value("${spring.cache.ehcache.config}")
    private String ehcacheConfig;

    @Value("${shiro.credentials.hashIterations}")
    private int hashIterations;
    @Value("${shiro.credentials.hashAlgorithmName}")
    private String hashAlgorithmName;
    @Value("${shiro.credentials.retryDisable}")
    private int retryDisable;
    @Value("${shiro.session.timeout}")
    private int shiroSessionTimeout;

    @Autowired
    private CacheManager cacheManager;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/defaultKaptcha", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/do_Login","anon");
        filterChainDefinitionMap.put("/intoRegistry","anon");
        filterChainDefinitionMap.put("/registryUser","anon");
        filterChainDefinitionMap.put("/checkExist","anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/go_Login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public CustomizeShiroRealm defaultShiroRealm() {
        CustomizeShiroRealm defaultShiroRealm = new CustomizeShiroRealm();
        defaultShiroRealm.setCredentialsMatcher(CustomizeCredentialsMatcher());
//        defaultShiroRealm.setCachingEnabled(true);
//        defaultShiroRealm.setAuthenticationCachingEnabled(true);
//        defaultShiroRealm.setAuthenticationCacheName("defaultShiroRealmAuthenticationCache");
//        defaultShiroRealm.setAuthorizationCachingEnabled(true);
//        defaultShiroRealm.setAuthorizationCacheName("defaultShiroRealmAuthorizationCache");
        return defaultShiroRealm;
    }

    @Bean
    public CredentialsMatcher CustomizeCredentialsMatcher() {
//        CustomizeHashedCredentialsMatcher credentialsMatcher = new CustomizeHashedCredentialsMatcher(ehcacheManager(), retryDisable);
//        CustomizeHashedCredentialsMatcher credentialsMatcher = new CustomizeHashedCredentialsMatcher(redisCacheManager(), retryDisable);
        CustomizeHashedCredentialsMatcher credentialsMatcher = new CustomizeHashedCredentialsMatcher(jedisCacheManager(), retryDisable);
        //加密方式
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        //加密次数
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    @Bean
    public CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        return hashedCredentialsMatcher;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(defaultShiroRealm());
//        securityManager.setCacheManager(ehcacheManager());
//        securityManager.setCacheManager(redisCacheManager());
        securityManager.setCacheManager(jedisCacheManager());
        securityManager.setSessionManager(defaultWebSessionManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public EhCacheManager ehcacheManager() {
        EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManager(((EhCacheCacheManager)cacheManager).getCacheManager());
        return ehcacheManager;
    }

    @Bean
    public org.apache.shiro.cache.CacheManager redisCacheManager(){
        org.apache.shiro.cache.CacheManager cacheManager = new RedisCacheManager();
        return cacheManager;
    }

    @Bean
    public org.apache.shiro.cache.CacheManager jedisCacheManager(){
        org.apache.shiro.cache.CacheManager cacheManager = new JedisCacheManager();
        return cacheManager;
    }

    @Bean
    public SessionManager defaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDao());
        sessionManager.setSessionIdCookie(sessionCookie());
        sessionManager.setGlobalSessionTimeout(shiroSessionTimeout);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean
    public SessionDAO redisSessionDao(){
        RedisSessionDao sessionDAO = new RedisSessionDao();
        sessionDAO.setExpire(shiroSessionTimeout);
        return sessionDAO;
    }

    @Bean
    public Cookie sessionCookie(){
        SimpleCookie cookie = new SimpleCookie("SHAREJSESSIONID");
        cookie.setPath("/");
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager() ;
        SimpleCookie cookie = new SimpleCookie() ;
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1800);
        cookie.setName("wfx-rememberMe");
        rememberMeManager.setCookie(cookie);
        return rememberMeManager ;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor sourceAdvisor(){
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager());
        return sourceAdvisor;
    }
}
