package com.chieftain.agile.common.cache.jedis;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.chieftain.agile.common.cache.api.JedisClient;
import com.chieftain.agile.utils.CustomizSerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * com.chieftain.agile.common.cache [workset_idea_01]
 * Created by Richard on 2018/5/22
 *
 * @author Richard on 2018/5/22
 */
@Component
public class JedisClientImpl implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public byte[] getByte(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            return jedis.get(keyStr);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String set(String key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            jedis.del(keyStr);
            return jedis.set(keyStr, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String set(String key, byte[] value, long expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            jedis.del(keyStr);
            return jedis.psetex(keyStr, expire, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Long delConvert(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            return jedis.del(keyStr);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String flushDB(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.flushDB();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public long dbSize(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.dbSize();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(pattern);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }


    /**********************************************************************************************************
     **********************************************************************************************************
     **********************************                                         *******************************
     **********************************         以下往后的方法暂未使用              ******************************
     **********************************                                         *******************************
     **********************************************************************************************************
     **********************************************************************************************************/

    /**
     * 以下往后的方法咱未使用
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public String setConvert(String key, Object value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            jedis.del(key);
            return jedis.set(keyStr, CustomizSerializationUtils.serialize(value));
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String setConvertEx(String key, int seconds, Object value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            jedis.del(key);
            return jedis.setex(keyStr, seconds, CustomizSerializationUtils.serialize(value));
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Object getConvert(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyStr = serializer.serialize(key);
            return CustomizSerializationUtils.deserialize(jedis.get(keyStr));
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String set(String key, long seconds, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.psetex(key, seconds, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Long setNx(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.setnx(key, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String set(byte[] key, byte[] value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String set(byte[] key, long seconds, byte[] value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.psetex(key, seconds, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Long setNx(byte[] key, byte[] value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.setnx(key, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    /**
     * nxxx nxxx 的值只能取 NX 或者 XX，如果取 NX，则只有当 key 不存在是才进行 set，如果取 XX，则只有当 key 已经存在时才进行 set
     * expx expx 的值只能取 EX 或者 PX，代表数据过期时间的单位，EX 代表秒，PX 代表毫秒
     *
     * @param key
     * @param value
     * @param expire
     */
    @Override
    public String setEx(String key, String value, long expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value, "NX", "EX", expire);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String setEx(byte[] key, byte[] value, long expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value, "NX".getBytes("UTF-8"), "EX".getBytes("UTF-8"), expire);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String update(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String update(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String updateEx(String key, String value, long expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value, "NX", "EX", expire);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String updateEx(byte[] key, byte[] value, long expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return jedis.set(key, value, "NX".getBytes("UTF-8"), "EX".getBytes("UTF-8"), expire);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public String get(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public byte[] get(byte[] key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Long delete(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Long delete(byte[] key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    @Override
    public Long delete(String[] keys) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    public RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
}
