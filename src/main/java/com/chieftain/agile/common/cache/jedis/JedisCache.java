package com.chieftain.agile.common.cache.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;

import com.chieftain.agile.common.cache.api.CustomizeCache;
import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.utils.CustomizSerializationUtils;

/**
 * com.chieftain.agile.common.cache.jedis [workset_idea_01]
 * Created by Richard on 2018/5/23
 *
 * @author Richard on 2018/5/23
 */
public class JedisCache<K, V> extends CustomizeCache<K, V> {

    private Logger logger = LogManager.getLogger(JedisCache.class);

    private JedisClient jedisClient;

    private String keyPrefix = "shiro_jedis_session:";

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public JedisCache(JedisClient jedisClient) {
        if (jedisClient == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.jedisClient = jedisClient;
    }

    public JedisCache(JedisClient jedisClient,
                      String prefix) {

        this(jedisClient);

        this.keyPrefix = prefix;
    }

    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        } else {
            return CustomizSerializationUtils.serialize(key);
        }
    }

    @Override
    public V put(K key, V value, long expire) throws CacheException {
        logger.debug("根据key从存储 key [" + key + "]");
        try {
            jedisClient.set(key.toString(), CustomizSerializationUtils.serialize(value), expire);
            return value;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new CacheException(t);
        }
    }

    @Override
    public V get(K k) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [" + k + "]");
        try {
            if (k == null) {
                return null;
            } else {
                byte[] rawValue = jedisClient.getByte(k.toString());
                @SuppressWarnings("unchecked")
                V value = (V) CustomizSerializationUtils.deserialize(rawValue);
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        logger.debug("根据key从存储 key [" + k + "]");
        try {
            jedisClient.set(k.toString(), CustomizSerializationUtils.serialize(v));
            return v;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K k) throws CacheException {
        logger.debug("从redis中删除 key [" + k + "]");
        try {
            V previous = get(k);
            jedisClient.delConvert(k.toString());
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("从redis中删除所有元素");
        try {
            jedisClient.flushDB();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        try {
            Long longSize = new Long(jedisClient.dbSize());
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Set<K> keys() {
        try {
            Set<String> keys = jedisClient.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<K>();
                for (String key : keys) {
                    newKeys.add((K) key);
                }
                return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        try {
            Set<String> keys = jedisClient.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (String key : keys) {
                    @SuppressWarnings("unchecked")
                    V value = get((K) key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
}
