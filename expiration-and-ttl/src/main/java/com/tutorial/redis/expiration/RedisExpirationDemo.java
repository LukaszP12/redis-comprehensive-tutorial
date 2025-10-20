package com.tutorial.redis.expiration;

import redis.clients.jedis.Jedis;

public class RedisExpirationDemo {
    public static void main(String[] args) throws InterruptedException {
        String host = System.getProperty("redis.host", "localhost");
        int port = Integer.getInteger("redis.port", 6379);

        try (Jedis jedis = new Jedis(host, port)) {
            System.out.println("✅ Connected to Redis: " + jedis.ping());

            demoExpire(jedis);
            demoPExpire(jedis);
            demoSetEx(jedis);
        }
    }

    private static void demoExpire(Jedis jedis) throws InterruptedException {
        System.out.println("\n--- ⏳ EXPIRE (seconds) ---");
        jedis.set("temp:key", "I will expire in 5 seconds");
        jedis.expire("temp:key", 5);
        System.out.println("Value now: " + jedis.get("temp:key") + " (TTL: " + jedis.ttl("temp:key") + "s)");

        Thread.sleep(6000);
        System.out.println("After 6 seconds: " + jedis.get("temp:key"));
    }

    private static void demoPExpire(Jedis jedis) throws InterruptedException {
        System.out.println("\n--- ⚡ PEXPIRE (milliseconds) ---");
        jedis.set("temp:ms:key", "Expire fast");
        jedis.pexpire("temp:ms:key", 1500);
        System.out.println("Value now: " + jedis.get("temp:ms:key") + " (PTTL: " + jedis.pttl("temp:ms:key") + "ms)");

        Thread.sleep(2000);
        System.out.println("After 2 seconds: " + jedis.get("temp:ms:key"));
    }

    private static void demoSetEx(Jedis jedis) {
        System.out.println("\n--- 🔒 SET with expiration ---");
        jedis.setex("login:session:123", 10, "user42");
        System.out.println("Session value: " + jedis.get("login:session:123"));
        System.out.println("Session TTL: " + jedis.ttl("login:session:123") + " seconds");
    }
}
