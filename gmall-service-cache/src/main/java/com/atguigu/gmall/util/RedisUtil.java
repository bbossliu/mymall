package com.atguigu.gmall.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author dalaoban
 * @create 2020-02-19-12:42
 */
public class RedisUtil {

    private JedisPool jedisPool;

    public void initPool(String host,int port,int database,String password){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(30);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(10*1000);
        poolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(poolConfig, host, port, database, password);
    }

    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}