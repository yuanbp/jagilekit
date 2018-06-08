package com.chieftain.agile.common.cache.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chieftain.agile.common.cache.api.RedisDao;

/**
 * com.chieftain.junite.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/17
 *
 * @author Richard on 2018/5/17
 */
@Service
public class RedisDaoImpl implements RedisDao {

    @Resource
    protected RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 设置 redisTemplate
     *
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     */
    private RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }


    @Override
    public <T> boolean add(String key, T obj) {
        return add(key, JSON.toJSONString(obj));
    }

    @Override
    public <T> void add(String key, long timeout, T obj) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] object = serializer.serialize(JSON.toJSONString(obj));
                add(key,timeout,object);
                return null;
            }


        });
    }


    @Override
    public void add(String key, long timeout, byte[] object) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.setEx(keyStr, timeout, object);
                return null;
            }
        });
    }


    @Override
    public boolean add(String key, String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] object = serializer.serialize(value);
                return connection.setNX(keyStr, object);
            }
        });
        return result;
    }

    @Override
    public <T> boolean add(String key, List<T> list) {
        return add(key, JSON.toJSONString(list));
    }


    @Override
    public void delete(String key) {
        execDel(key);
    }

    @Override
    public void delete(List<String> keys) {
        execDelBatch(keys);
    }

    public void execDelBatch(List<String> keys){
        for (String key : keys) {
            boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keyStr = serializer.serialize(key);
                    long returni = connection.del(keyStr);
                    if(returni >0){
                        return true;
                    }else{
                        return false;
                    }
                }
            });
        }
    }

    public boolean execDel(String key){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                long returni = connection.del(keyStr);
                if(returni >0){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return result;
    }

    @Override
    public <T> boolean update(String key, T obj) {
        return update(key, JSON.toJSONString(obj));
    }


    @Override
    public boolean update(String key, String value) {
        if (get(key) == null) {
            throw new NullPointerException(" 数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] valueStr = serializer.serialize(value);
                connection.set(keyStr, valueStr);
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean save(String key, String value) {
        if (StringUtils.isNotBlank(get(key))) {
            return add(key, value);
        } else {
            return update(key, value);
        }
    }

    @Override
    public <T> boolean save(String key, T obj) {
        if (get(key, obj.getClass()) == null) {
            return add(key, obj);
        } else {
            return update(key, obj);
        }
    }

    @Override
    public <T> T get(String key, Class clazz) {
        T result = redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return (T) JSON.parseObject(valueStr, clazz);
            }
        });
        return result;
    }


    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        List<T> result = redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return JSON.parseArray(valueStr, clazz);
            }
        });
        return result;
    }



    @Override
    public String get(String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                if (value == null) {
                    return null;
                }
                String valueStr = serializer.deserialize(value);
                return valueStr;
            }
        });
        return result;
    }

    @Override
    public byte[] getByte(String key) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                byte[] value = connection.get(keyStr);
                return value;
            }
        });
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean exist(String key){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                return connection.exists(keyStr);
            }
        });
        return result;
    }


    @Override
    public boolean set(String key, byte[] value){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String>  serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.set(keyStr, value);
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean set(String key, byte[] value, long expire){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String>  serializer = getRedisSerializer();
                byte[] keyStr = serializer.serialize(key);
                connection.set(keyStr, value);
                try {
                    connection.expire(key.getBytes("UTF-8"),expire);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean flushDB(){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return true;
            }
        });
        return result;
    }

    @Override
    public long dbSize(){
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
        return result;
    }
}
