package com.tutorial.redis.datastructures;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisDataStructuresDemo {
    public static void main(String[] args) {
        String host = System.getProperty("redis.host", "localhost");
        int port = Integer.getInteger("redis.port", 6379);

        try (Jedis jedis = new Jedis(host, port)) {
            System.out.println("✅ Connected to Redis: " + jedis.ping());

            demoStrings(jedis);
            demoHashes(jedis);
            demoLists(jedis);
            demoSets(jedis);
            demoSortedSets(jedis);

            System.out.println("\n✅ Redis data structure demo completed!");
        }
    }

    private static void demoStrings(Jedis jedis) {
        System.out.println("\n--- 🔤 STRINGS ---");
        jedis.set("user:1:name", "Alice");
        jedis.incr("counter:visits");
        System.out.println("user:1:name = " + jedis.get("user:1:name"));
        System.out.println("counter:visits = " + jedis.get("counter:visits"));
    }

    private static void demoHashes(Jedis jedis) {
        System.out.println("\n--- 🏷 HASHES ---");
        jedis.hset("user:2", Map.of("name", "Bob", "email", "bob@mail.com"));
        System.out.println("user:2 = " + jedis.hgetAll("user:2"));
    }

    private static void demoLists(Jedis jedis) {
        System.out.println("\n--- 📚 LISTS ---");
        jedis.del("queue:orders"); // cleanup
        jedis.lpush("queue:orders", "order1", "order2", "order3");
        List<String> orders = jedis.lrange("queue:orders", 0, -1);
        System.out.println("queue:orders = " + orders);
    }

    private static void demoSets(Jedis jedis) {
        System.out.println("\n--- 🔢 SETS ---");
        jedis.sadd("tags:product:1", "electronics", "sale", "popular");
        Set<String> tags = jedis.smembers("tags:product:1");
        System.out.println("tags:product:1 = " + tags);
    }

    private static void demoSortedSets(Jedis jedis) {
        System.out.println("\n--- 🏆 SORTED SETS ---");
        jedis.zadd("leaderboard", 1500, "Alice");
        jedis.zadd("leaderboard", 1800, "Bob");
        jedis.zadd("leaderboard", 1200, "Charlie");
        List<String> topPlayers = jedis.zrevrange("leaderboard", 0, 2);
        System.out.println("Top players = " + topPlayers);
    }
}
