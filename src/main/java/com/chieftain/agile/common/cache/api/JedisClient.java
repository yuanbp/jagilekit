package com.chieftain.agile.common.cache.api;

import java.util.Set;

/**
 * com.chieftain.agile.common.cache.api [workset_idea_01]
 * Created by Richard on 2018/5/23
 *
 * @author Richard on 2018/5/23
 */
public interface JedisClient {

    public Set<String> keys(String pattern);

    public long dbSize();

    public String flushDB();

    public String set(String key, byte[] value, long expire);

    public String set(String key, byte[] value);

    public byte[] getByte(String key);

    public String setConvert(String key, Object value) throws Exception;

    public String setConvertEx(String key, int seconds, Object value) throws Exception;

    public Object getConvert(String key);

    public Long delConvert(String key);

    public String set(String key, String value) throws Exception;

    public String set(String key, long seconds, String value) throws Exception;

    public Long setNx(String key, String value) throws Exception;

    public String set(byte[] key, byte[] value) throws Exception;

    public String set(byte[] key, long seconds, byte[] value) throws Exception;

    public Long setNx(byte[] key, byte[] value) throws Exception;

    /**
     * nxxx nxxx 的值只能取 NX 或者 XX，如果取 NX，则只有当 key 不存在是才进行 set，如果取 XX，则只有当 key 已经存在时才进行 set
     * expx expx 的值只能取 EX 或者 PX，代表数据过期时间的单位，EX 代表秒，PX 代表毫秒
     *
     * @param key
     * @param value
     * @param expire
     */
    public String setEx(String key,String value,long expire);

    public String setEx(byte[] key,byte[] value,long expire);

    public String update(String key,String value);

    public String update(byte[] key,byte[] value);

    public String updateEx(String key,String value,long expire);

    public String updateEx(byte[] key,byte[] value,long expire);

    public String get(String key) throws Exception;

    public byte[] get(byte[] key) throws Exception;

    public Long delete(String key) throws Exception;

    public Long delete(byte[] key) throws Exception;

    public Long delete(String[] keys) throws Exception;
}
