package com.example.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisUtil {
    private volatile static JedisUtil jedisUtil;
    private Jedis jedis = new Jedis("localhost");
    private double counter;

    private JedisUtil() {
    }

    public static JedisUtil getJedisUtil() {
        if (jedisUtil == null) {
            synchronized (JedisUtil.class) {
                if (jedisUtil == null) {
                    jedisUtil = new JedisUtil();
                }
            }
        }

        return jedisUtil;
    }

    public Long add(String member) {
        return jedis.zadd("mySet", counter++, member);
    }

    public Long add(String key, double score, String member) {
        return jedis.zadd(key, score, member);
    }

    public Double getScoreOfMember(String key, String member) {
        return jedis.zscore(key, member);
    }

    public Set<String> getAll(String key) {
        return jedis.zrange(key, 0, -1);
    }

    public Set<String> getAll() {
        return jedis.zrange("mySet", 0, -1);
    }
}
