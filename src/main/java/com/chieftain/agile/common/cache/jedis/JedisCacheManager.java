package com.chieftain.agile.common.cache.jedis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import com.chieftain.agile.common.cache.api.JedisClient;

/**
 * com.chieftain.agile.common.cache [workset_idea_01]
 * Created by Richard on 2018/5/23
 *
 * @author Richard on 2018/5/23
 */
public class JedisCacheManager implements CacheManager {

    private Logger logger = LogManager.getLogger(JedisCacheManager.class);

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Resource
    private JedisClient jedisClient;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache c = caches.get(name);
        if (c == null) {
            c = new JedisCache<K, V>(jedisClient);
            caches.put(name, c);
        }
        return (Cache<K, V>) c;
    }
}
