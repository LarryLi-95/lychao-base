/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.lycaho.basecommon.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;
    @Resource(name="redisTemplate")
    private HashOperations<String, String, Object> hashOperations;
    @Resource(name="redisTemplate")
    private ListOperations<String, Object> listOperations;
    @Resource(name="redisTemplate")
    private SetOperations<String, Object> setOperations;
    @Resource(name="redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /***
     *
     * @param key
     * @param value
     * @param timeOut
     * @return
     */
    public boolean setIfAbsent(String key, String value, Duration timeOut){
        return valueOperations.setIfAbsent(key, value,timeOut);
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public Boolean exsist(String key){
        return  redisTemplate.hasKey(key);
    };

    public String mapGetString(String key,String hashKey){
        return hashOperations.get(key, hashKey) == null ? null : hashOperations.get(key, hashKey).toString();
    };

    public void hdel(String key, String hashKey) {
        hashOperations.delete(key,hashKey);
    }

    public void hincrByPersist(String key, String hashKey, long delta) {
        hashOperations.increment(key,hashKey,delta);
    }

    public void mapSetString(String key, String hashKey, String value) {
        hashOperations.put(key,hashKey,value);
    }

    /***
     * 发布订阅消息
     * @param channel
     * @param message
     */
    public void convertAndSend(String channel,Object message){
        redisTemplate.convertAndSend(channel,message);
    }

    public void mapPutAll(String key, Map map){
        hashOperations.putAll(key,map);
    }

    /***
     * 设置过期时间，秒
     * @param key
     * @param expire
     * @return
     */
    public boolean expire(String key,Long expire){
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /***
     * 删除某个hashkey
     * @param key
     * @param hashKey
     * @return
     */
    public void delete(String key,String hashKey){
        hashOperations.delete(key, hashKey);
    }

    public Boolean exsist(String key,String hashKey){
        return hashOperations.hasKey(key,hashKey);
    };

    /***
     *  返回列表长度
     * @param key
     * @return
     */
    public Long listSize(String key){
        return listOperations.size(key);
    }
    /***
     *  左弹出一个数据
     * @param key
     * @return
     */
    public <T> T listLeftPop(String key, Class<T> clazz) {
        Object value = listOperations.leftPop(key);
        return value == null ? null : fromJson(value.toString(), clazz);
    }

    /***
     *  队列弹出多个数据
     * @param key
     * @return
     */
    public <T> List<T> listLeftPops(String key,int length,Class<T> clazz) {
        List<Object> list = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < length; i++) {
                    connection.lPop(key.getBytes());
                }
                return null;
            }
        });
        List<T> result = new ArrayList<>();
        if(list!=null){
            for (Object value : list) {
                result.add(fromJson(toJson(value),clazz));
            }
        }
        return result;
    }


    /***
     * 从右边压入数据队列
     * @param key
     * @param value
     */
    public void listRightPush(String key, Object value){
        listOperations.rightPush(key,toJson(value));
    }


    /***
     * 获取多个数据
     * @return
     */
    public  <T> List<T> listLeftPops(String key, Long size,Class<T> targetClass){
        return  (List<T>)redisTemplate.executePipelined(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                List bytes = redisConnection.lRange(key.getBytes(), 0, size - 1);
                redisConnection.lTrim(key.getBytes(), size, -1);
                return bytes;
            }
        });
    }


    /***
     * 把集合从右边压入数据队列
     * @param key
     * @param list
     */
    public void listRightPushAll(String key, Object[] list){
        listOperations.leftPushAll(key,list);
    }

    public void listRightPushList(String key, List list) {
        redisTemplate.executePipelined(new RedisCallback<List>() {
            @Override
            public List doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (Object value : list) {
                    redisConnection.rPush(key.getBytes(),toJson(value).getBytes());
                }
                return null;
            }
        });
    }
}
