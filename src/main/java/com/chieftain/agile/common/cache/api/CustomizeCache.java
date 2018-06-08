package com.chieftain.agile.common.cache.api;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * com.chieftain.agile.common.auth [workset_idea_01]
 * Created by Richard on 2018/5/22
 *
 * @author Richard on 2018/5/22
 */
public abstract class CustomizeCache<K, V> implements Cache<K, V> {

    public abstract V put(K key, V value, long expire)throws CacheException;
}
