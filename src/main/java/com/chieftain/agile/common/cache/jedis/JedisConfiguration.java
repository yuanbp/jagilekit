package com.chieftain.agile.common.cache.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * com.chieftain.agile.common.cache [workset_idea_01]
 * Created by Richard on 2018/5/22
 *
 * @author Richard on 2018/5/22
 */

@Configuration
public class JedisConfiguration {

    @Bean(name= "jedis.pool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                               @Value("${spring.redis.host}") String host,
                               @Value("${spring.redis.port}")int port,
                               @Value("${spring.redis.timeout}")int timeout,
                               @Value("${spring.redis.password}")String password,
                               @Value("${spring.redis.database}")int database) {
        return new JedisPool(config, host, port, timeout, password, database);
    }

    @Bean(name= "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig (@Value("${spring.redis.jedis.pool.max-active}")int maxTotal,
                                            @Value("${spring.redis.jedis.pool.max-idle}")int maxIdle,
                                            @Value("${spring.redis.jedis.pool.max-wait}")int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }
}
