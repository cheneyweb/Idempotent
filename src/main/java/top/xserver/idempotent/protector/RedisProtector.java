package top.xserver.idempotent.protector;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

public class RedisProtector implements Protector {

    private StringRedisTemplate stringRedisTemplate;

    public RedisProtector(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 固定窗口
     *
     * @param key      唯一键
     * @param duration 毫秒
     * @return boolean
     */
    @Override
    public Boolean fixed(String key, Long duration) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(duration));
    }

    /**
     * 滑动窗口
     *
     * @param key      唯一键
     * @param duration 毫秒
     * @return boolean
     */
    @Override
    public Boolean sliding(String key, Long duration) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(duration));
    }
}
