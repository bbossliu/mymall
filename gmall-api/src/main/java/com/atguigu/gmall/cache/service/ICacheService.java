package com.atguigu.gmall.cache.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dalaoban
 * @create 2020-02-19-18:06
 */
public interface ICacheService {

    public  long removeFromSortedSetByScore(String keySet, double score);

    public Set<byte[]> getSortedSetRangeReverse(byte[] sortKey, int start,
                                                int size);

    public  Set<byte[]> getSortedSetRange(byte[] sortKey, int start,
                                          int size);

    public  Long getSortByMember(String key, String member);

    public  Double getScore(String key, String member);

    public  Set<String> getSortedSetRangeReverse(String sortKey, int start,
                                                 int size);

    public  Set<String> getSortedSetRange(String sortKey, int start,
                                          int size);

    public  void addToSortedSet(byte[] setKey, Map<Double, byte[]> valueMap);

    public  void addToSortedSet(Object setKey, Map<Double, Object> valueMap);

    public  void addToSortedSet(String setKey, Map<Double, String> valueMap);

    public  void addToSortedSet(byte[] setKey, byte[] value, double score);

    public  void addToSortedSet(Object setKey, Object value, double score);

    public  void addToSortedSet(String setKey, String value, double score);

    public  void removeFromMap(byte[] mapKey, byte[] field);

    public  void removeFromMap(Object mapKey, Object field);

    public  byte[] getFromMap(byte[] mapKey, byte[] field);

    public  Object getFromMap(Object mapKey, Object field);

    public  void putStringToMap(String mapKey, Map<String, String> data);

    public  void putToMap(byte[] mapKey, Map<byte[], byte[]> data);

    public  void putToMap(Object mapKey, Map<Object, Object> data);

    public  void putStringToMap(String mapKey, String field, String value);

    public  void putToMap(byte[] mapKey, byte[] field, byte[] value);

    public  void putToMap(Object mapKey, Object field, Object value);

    public  Map<byte[], byte[]> getMapAllByte(Object mapKey);

    public  Map<String, String> getStringMapAll(String mapKey);

    public  Map<byte[], byte[]> getMapAll(byte[] mapKey);

    public  Map<Object, Object> getMapAll(Object mapKey);

    public List<byte[]> getListRange(byte[] listKey, int start, int size);

    public  List<Object> getListRange(Object listKey, int start, int size);

    public  int getLengthOfList(byte[] listKey);

    public  int getLengthOfList(Object listKey);

    public  byte[] popFromListRight(byte[] listKey);

    public  Object popFromListRight(Object listKey);

    public  byte[] popFromListLeft(byte[] listKey);

    public  Object popFromListLeft(Object listKey);

    public  void addToListRight(byte[] listKey, byte[] value);

    public  void addToListRight(Object listKey, Object value);

    public  void addToListLeft(byte[] listKey, byte[] value);

    public  void addToListLeft(Object listKey, Object value);

    public  List<byte[]> getListAll(byte[] listKey);

    public  List<Object> getListAll(Object listKey);

    public  String getString(String key);

    public  void setString(String key, String value, int exp);

    public  void setString(String key, String value);

    public  byte[] get(byte[] key);

    public  Object get(Object key);

    public  Object get(String key);

    public  long dbSize(Object key);

    public  long ttl(String key);

    public  Set<String> keys(String keys);

    public  void set(byte[] key, byte[] value, int exp);

    public  void set(Object key, Object value, int exp);

    public  void set(String key, Object value, int exp);

    public  void set(Object key, Object value);

    public  void set(String key, Object value);

    public  long delete(byte[] key);

    public  long deleteObjectKey(Object key);

    public  long deleteStringKey(String key);

    public  long delete(String key);

    public  long decrease(byte[] key, int num);

    public  long decrease(byte[] key);

    public  long decrease(String key, int num);

    public  long decrease(String key);

    public  long increase(byte[] key, int num);

    public  long increase(byte[] key);

    public  long increase(String key, int num);

    public  long increase(String key);

    public  boolean checkKeyExisted(byte[] key);

    public  boolean checkKeyExisted(Object key);

    public  long expire(byte[] key, int seconds);

    public  long expire(String key, int seconds);

}
