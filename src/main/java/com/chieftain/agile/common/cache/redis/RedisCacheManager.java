package com.chieftain.agile.common.cache.redis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import com.chieftain.agile.common.cache.api.RedisDao;

/**
 * com.chieftain.junite.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/17
 *
 * @author Richard on 2018/5/17
 */
public class RedisCacheManager implements CacheManager {

    private Logger logger = LogManager.getLogger(RedisCacheManager.class);

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Resource
    private RedisDao redisDao;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache c = caches.get(name);
        if (c == null) {
            c = new RedisCache<K, V>(redisDao);
            caches.put(name, c);
        }
        return (Cache<K, V>) c;
    }
}
