package com.liezh.domain.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2017/10/3.
 *  忘记密码token的缓存
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public  static String FORGET_TOKEN_PREFIX = "forget_token_";

    // 配置最大的缓存大小，当缓存超过缓存大小时，会使用LRU（最少使用算法）清除缓存
    private static LoadingCache<String, String> localCache =
            CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
                    .expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                // 默认的数据加载实现， 当调用get取值的时候，如果key没有对应的值时（key没有命中时），就调用这个方法加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("localCache 获取异常 ", e);
        }
        return null;
    }

}
