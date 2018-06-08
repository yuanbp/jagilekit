package com.chieftain.agile.common.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;

import com.chieftain.agile.common.cache.api.CustomizeCache;

/**
 * com.chieftain.junite.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/15
 *
 * @author Richard on 2018/5/15
 */
public class CustomizeHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private CustomizeCache<String, Object> redisCache;
    private Integer retryDisable = 5;
    private final String retryPerfix = "retry";
    private final String retrysuffix = "times";
    private final long expireSeconds = 300;
    private final long expireMicroseconds = 300000;

    public CustomizeHashedCredentialsMatcher(CacheManager cacheManager, int recvRetryDisable) {
        redisCache = (CustomizeCache)cacheManager.getCache("redisCache");
        retryDisable = recvRetryDisable;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        String retryKey = retryPerfix+username+retrysuffix;
        Integer retryCount = (Integer) redisCache.get(retryKey);
        if (retryCount == null) {
            retryCount = new Integer(0);
            redisCache.put(retryKey, retryCount,expireMicroseconds);
        }
        retryCount++;
        if (retryCount > retryDisable) {
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            redisCache.remove(retryKey);
        }else {
            redisCache.put(retryKey,retryCount,expireMicroseconds);
            redisCache.remove(username);
        }

        return matches;
    }
}
