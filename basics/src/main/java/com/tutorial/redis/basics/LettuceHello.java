package com.tutorial.redis.basics;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceHello {
    public static void main(String[] args) {
        String redisHost = System.getProperty("redis.host", "localhost");
        String redisPort = System.getProperty("redis.port", "6379");
        String redisUrl = "redis://" + redisHost + ":" + redisPort;

        RedisClient client = RedisClient.create(redisUrl);
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisCommands<String, String> commands = connection.sync();

            System.out.println("PING -> " + commands.ping());

            commands.set("basics:greeting:lettuce", "hello from lettuce");
            String value = commands.get("basics:greeting:lettuce");
            System.out.println("GET basics:greeting:lettuce -> " + value);

            Long count = commands.incr("basics:counter:lettuce");
            System.out.println("INCR basics:counter:lettuce -> " + count);
        } finally {
            client.shutdown();
        }
    }
}
