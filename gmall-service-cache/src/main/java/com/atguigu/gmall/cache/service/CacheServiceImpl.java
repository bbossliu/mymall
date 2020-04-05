package com.atguigu.gmall.cache.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author dalaoban
 * @create 2020-02-19-18:08
 */
@Service
public class CacheServiceImpl implements ICacheService {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 关闭数据库连接
     */
    private void returnConnection(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

    /**
     * 关闭错误连接
     *
     * @param jedis
     */
    private void returnBorkenConnection(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }
    
    /**
     * 设置key-value失效时间，字符串类型key
     *
     * @param key
     * @param seconds
     * @return
     */
    public long expire(String key, int seconds) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.expire(key, seconds);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    //如果不存在名称为key的string，则向库中添加string，名称为key，值为value
    @Override
    public long setnx(String key, String value) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.setnx(key, value);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    @Override
    public boolean set(String key, String value, String nxxx, String expx, long time) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return false;
        }
        try {
            String result = conn.set(key, value,nxxx,expx,time);
            returnConnection(conn);
            if(!StringUtils.isEmpty(result) && result.equals("OK")){
                return true;
            }
            return false;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return false;
    }

    /**
     * 设置key-value失效时间，字节类型key
     *
     * @param key
     * @param seconds
     * @return
     */
    public long expire(byte[] key, int seconds) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.expire(key, seconds);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    /**
     * 检查key是否存在
     *
     * @param key
     * @return 返回操作后的值
     */
    public boolean checkKeyExisted(byte[] key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return false;
        }
        boolean result = false;
        try {
            result = conn.exists(key);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return result;
    }

    @Override
    public boolean checkKeyExisted(Object key) {
        return false;
    }

    /**
     * 加1操作
     *
     * @param key
     * @return 返回操作后的值
     */
    public long increase(String key) {
        return increase(key, 1);
    }

    /**
     * 加操作，指定加的量
     *
     * @param key
     * @param num
     * @return 返回操作后的值
     */
    public long increase(String key, int num) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.incrBy(key, num);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    /**
     * 加1操作
     *
     * @param key
     * @return 返回操作后的值
     */
    public long increase(byte[] key) {
        return increase(key, 1);
    }

    /**
     * 加操作，指定加的量
     *
     * @param key
     * @param num
     * @return
     */
    public long increase(byte[] key, int num) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.incrBy(key, num);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    /**
     * 减1操作
     *
     * @param key
     * @return 返回操作后的值
     */
    public long decrease(String key) {
        return decrease(key, 1);
    }

    /**
     * 减操作，指定减的值
     *
     * @param key
     * @param num
     * @return 返回操作后的值
     */
    public long decrease(String key, int num) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.decrBy(key, num);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    /**
     * 查询key剩余时间
     * @param key
     * @return
     */
    public long ttl(String key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.ttl(key);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }
    /**
     * 减1操作
     *
     * @param key
     * @return 返回操作后的值
     */
    public long decrease(byte[] key) {
        return decrease(key, 1);
    }

    /**
     * 减操作，指定减的值
     *
     * @param key
     * @param num
     * @return 返回操作后的值
     */
    public long decrease(byte[] key, int num) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.decrBy(key, num);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }


    /**
     * 删除记录
     *
     * @param key
     * @return
     */
    public long delete(byte[] key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.del(key);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }

    @Override
    public long deleteObjectKey(Object key) {
        return 0;
    }

    @Override
    public long deleteStringKey(String key) {
        return 0;
    }

    @Override
    public long delete(String key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return -1;
        }
        try {
            long result = conn.del(key);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return -1;
    }


    /**
     * 设置key-value项，字节类型
     *
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value, int exp) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            if (exp > 0) {
                conn.setex(key, exp, value);
            } else {
                conn.set(key, value);
            }
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void set(Object key, Object value, int exp) {

    }

    @Override
    public void set(String key, Object value, int exp) {

    }

    @Override
    public void set(Object key, Object value) {

    }

    @Override
    public void set(String key, Object value) {

    }


    /**
     * 返回当前数据库的 key 的数量
     *
     * @param key
     * @return
     */
    public long dbSize(Object key) {
        Jedis conn = redisUtil.getJedis();
        Long dbSize = 0L;
        if (conn == null) {
            return dbSize;
        }
        try {
            dbSize = conn.dbSize();
            returnConnection(conn);
            return dbSize;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return dbSize;
    }

    /**
     * 获取key value
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            byte[] data = conn.get(key);
            returnConnection(conn);
            return data;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    /**
     * 设置字符串类型缓存项
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        setString(key, value, -1);
    }

    /**
     * 存储字符串类型缓存项，加入失效时间，单位为秒
     *
     * @param key
     * @param value
     * @param exp
     */
    public void setString(String key, String value, int exp) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            if (exp > 0) {
                conn.setex(key, exp, value);
            } else {
                conn.set(key, value);
            }
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    /**
     * 获取字符串类型
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            String value = conn.get(key);
            returnConnection(conn);
            return value;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 从左边添加到list
     *
     * @param listKey
     * @param value
     */
    public void addToListLeft(byte[] listKey, byte[] value) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.lpush(listKey, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void addToListLeft(Object listKey, Object value) {

    }

    @Override
    public List<byte[]> getListAll(byte[] listKey) {
        return null;
    }

    @Override
    public List<Object> getListAll(Object listKey) {
        return null;
    }

    /**
     * 从右边添加到list
     *
     * @param listKey
     * @param value
     */
    public void addToListRight(byte[] listKey, byte[] value) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.rpush(listKey, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void addToListRight(Object listKey, Object value) {

    }

    /**
     * 从左边移除一个对象，并返回该对象
     *
     * @param listKey
     * @return
     */
    public byte[] popFromListLeft(byte[] listKey) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            byte[] data = conn.lpop(listKey);
            returnConnection(conn);
            return data;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    @Override
    public Object popFromListLeft(Object listKey) {
        return null;
    }

    /**
     * 从右边移除一个对象，并返回该对象
     *
     * @param listKey
     * @return
     */
    public byte[] popFromListRight(byte[] listKey) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            byte[] data = conn.rpop(listKey);
            returnConnection(conn);
            return data;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    @Override
    public Object popFromListRight(Object listKey) {
        return null;
    }

    /**
     * 获取列表长度
     *
     * @param listKey
     * @return
     */
    public int getLengthOfList(byte[] listKey) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return 0;
        }
        try {
            int length = conn.llen(listKey).intValue();
            returnConnection(conn);
            return length;
        } catch (Exception e) {
            returnBorkenConnection(conn);
            return 0;
        }
    }

    @Override
    public int getLengthOfList(Object listKey) {
        return 0;
    }

    /**
     * 获取list某一范围的段
     *
     * @param listKey
     * @param start
     * @param size
     * @return
     */
    public List<byte[]> getListRange(byte[] listKey, int start, int size) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }

        try {
            List<byte[]> data = conn.lrange(listKey, start, start + size - 1);
            returnConnection(conn);
            return data;
        } catch (Exception e) {
            returnBorkenConnection(conn);
            return null;
        }
    }

    @Override
    public List<Object> getListRange(Object listKey, int start, int size) {
        return null;
    }

    public Set<String> keys(String keys) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<String> keysSet = conn.keys(keys);
            returnConnection(conn);
            return keysSet;
        } catch (Exception e) {
            returnBorkenConnection(conn);
            return null;
        }
    }

    /**
     * 获取Map结构所有数据
     *
     * @param mapKey
     * @return
     */
    public Map<byte[], byte[]> getMapAll(byte[] mapKey) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Map<byte[], byte[]> data = conn.hgetAll(mapKey);
            returnConnection(conn);
            return data;
        } catch (Exception e) {
            returnBorkenConnection(conn);
            return null;
        }
    }

    @Override
    public Map<Object, Object> getMapAll(Object mapKey) {
        return null;
    }

    /**
     * 获取Map结构所有数据(key为String)
     *
     * @param mapKey
     * @return
     */
    public Map<String, String> getStringMapAll(String mapKey) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Map<String, String> result = conn.hgetAll(mapKey);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
            return null;
        }
    }


    /**
     * 添加到Map结构
     *
     * @param mapKey
     * @param field
     * @param value
     */
    public void putToMap(byte[] mapKey, byte[] field, byte[] value) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.hset(mapKey, field, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void putToMap(Object mapKey, Object field, Object value) {

    }

    @Override
    public Map<byte[], byte[]> getMapAllByte(Object mapKey) {
        return null;
    }

    /**
     * 添加到Map结构(key为String)
     *
     * @param mapKey
     * @param field
     * @param value
     */
    public void putStringToMap(String mapKey, String field, String value) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.hset(mapKey, field, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    /**
     * 批量设置到hash数据结果，采用byte类型存储
     *
     * @param mapKey
     * @param data
     */
    public void putToMap(byte[] mapKey, Map<byte[], byte[]> data) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.hmset(mapKey, data);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void putToMap(Object mapKey, Map<Object, Object> data) {

    }

    /**
     * 添加到Map结构（key为String）
     *
     * @param mapKey
     * @param data
     */
    public void putStringToMap(String mapKey, Map<String, String> data) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.hmset(mapKey, data);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    /**
     * 从Map结构中获取数据
     *
     * @param mapKey
     * @param field
     * @return
     */
    public byte[] getFromMap(byte[] mapKey, byte[] field) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            byte[] data = conn.hget(mapKey, field);
            returnConnection(conn);
            return data;
        } catch (Exception e) {
            returnBorkenConnection(conn);
            return null;
        }
    }

    @Override
    public Object getFromMap(Object mapKey, Object field) {
        return null;
    }

    /**
     * 从map中移除记录
     *
     * @param mapKey
     * @param field
     */
    public void removeFromMap(byte[] mapKey, byte[] field) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.hdel(mapKey, field);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void removeFromMap(Object mapKey, Object field) {

    }

    /**
     * 添加到sorted set队列，字符串类型
     *
     * @param setKey
     * @param value
     * @param score
     */
    public void addToSortedSet(String setKey, String value, double score) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.zadd(setKey, score, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    /**
     * 添加到sorted set队列，字节类型
     *
     * @param setKey
     * @param value
     * @param score
     */
    public void addToSortedSet(byte[] setKey, byte[] value, double score) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.zadd(setKey, score, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void addToSortedSet(Object setKey, Object value, double score) {

    }

    /**
     * 批量添加到sorted set队列，字符串类型
     *
     * @param setKey
     * @param valueMap
     */
    public void addToSortedSet(String setKey, Map<Double, String> valueMap) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        HashMap<String, Double> tempMap = new HashMap<>(valueMap.size());
        valueMap.forEach((k, v) -> {
            tempMap.put(v, k);
        });
        try {
            conn.zadd(setKey, tempMap);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    /**
     * 批量添加到sorted set队列，字节类型
     *
     * @param setKey
     * @param valueMap
     */
    public void addToSortedSet(byte[] setKey, Map<Double, byte[]> valueMap) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return;
        }
        HashMap<byte[], Double> tempMap = new HashMap<>(valueMap.size());
        valueMap.forEach((k, v) -> {
            tempMap.put(v, k);
        });
        try {
            conn.zadd(setKey, tempMap);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    @Override
    public void addToSortedSet(Object setKey, Map<Double, Object> valueMap) {

    }

    /**
     * 从sorted set中获取一定范围的段，按score从低到高
     *
     * @param sortKey
     * @param start
     * @param size
     * @return
     */
    public Set<String> getSortedSetRange(String sortKey, int start, int size) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<String> result = conn.zrange(sortKey, start, start + size - 1);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 从sorted set中获取一定范围的段，按score从高到低
     *
     * @param sortKey
     * @param start
     * @param size
     * @return
     */
    public Set<String> getSortedSetRangeReverse(String sortKey, int start,
                                                int size) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<String> result = conn.zrevrange(sortKey, start, start + size - 1);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 返回有序SET里成员的值
     *
     * @param key    set对应的key
     * @param member 成员名称
     * @return
     */
    public Double getScore(String key, String member) {

        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Double score = conn.zscore(key, member);
            returnConnection(conn);
            return score;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 返回有序SET里成员的排名
     *
     * @param key
     * @param member
     * @return
     */
    public Long getSortByMember(String key, String member) {


        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Long sorted = conn.zrevrank(key, member);
            sorted = sorted + 1; //排名是从0开始
            returnConnection(conn);
            return sorted;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;

    }

    /**
     * 从sorted set中获取一定范围的段，字节类型，按score从低到高
     *
     * @param sortKey
     * @param start
     * @param size
     * @return
     */
    public Set<byte[]> getSortedSetRange(byte[] sortKey, int start, int size) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<byte[]> result = conn.zrange(sortKey, start, start + size - 1);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 从sorted set中获取一定范围的段，字节类型，按score从高到低
     *
     * @param sortKey
     * @param start
     * @param size
     * @return
     */
    public Set<byte[]> getSortedSetRangeReverse(byte[] sortKey, int start, int size) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<byte[]> result = conn.zrevrange(sortKey, start, start + size - 1);
            returnConnection(conn);
            return result;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 根据score从sorted set中移除记录
     *
     * @param keySet
     * @param score
     */
    public long removeFromSortedSetByScore(String keySet, double score) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return 0;
        }
        try {
            long cnt = conn.zremrangeByScore(keySet, score, score);
            returnConnection(conn);
            return cnt;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return 0;
    }

    /**
     *
     * @param key
     * @param map
     */
    public boolean hmset(String key, Map<String,String> map ) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return false;
        }
        try {
            String hmset = conn.hmset(key, map);
            returnConnection(conn);
            if(hmset.equals("OK")){
                return true;
            }
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return false;
    }

    /**
     *
     * @param key
     * @param fileds
     */
    public List<String> hmget(String key, String... fileds) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            List<String> list = conn.hmget(key, fileds);
            returnConnection(conn);
            return list;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 根据key 查询value
     *
     * @param key
     */
    public List<String> hval(String key) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            List<String> list = conn.hvals(key);
            returnConnection(conn);
            return list;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

    /**
     * 设置过期时间
     *
     * @param key
     */
    public void setex(String key, int seconds, String value) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return ;
        }
        try {
            conn.setex(key, seconds, value);
            returnConnection(conn);
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
    }

    /**
     * lua脚本
     * @param script
     * @param keys
     * @param args
     *
     */
    public Object eval(String script, List<String> keys, List<String> args) {
        Jedis conn = redisUtil.getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Object object = conn.eval(script, keys, args);
            returnConnection(conn);
            return object;
        } catch (Exception e) {
            returnBorkenConnection(conn);
        }
        return null;
    }

}
