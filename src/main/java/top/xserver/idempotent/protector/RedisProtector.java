package top.xserver.idempotent.protector;

import org.springframework.data.redis.core.StringRedisTemplate;
import top.xserver.idempotent.annotation.Idempotent;

import java.time.Duration;

public class RedisProtector implements Protector {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisProtector(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 固定窗口
     *
     * @param key        唯一码
     * @param idempotent 幂等参数对象(duration,times)
     * @return Boolean
     */
    @Override
    public Boolean fixed(String key, Idempotent idempotent) {
        // 单次幂等控制
        if (idempotent.times() == 1) {
            return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(idempotent.duration()));
        }

        // 多次幂等控制
        Boolean isSet = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(idempotent.duration()));
        // 首次设置直接通过
        if (isSet) {
            return true;
        }
        // 后续自增后检查是否超过最大容忍次数
        else {
            return stringRedisTemplate.opsForValue().increment(key) <= idempotent.times();
        }
//        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(idempotent.duration()));
    }

    /**
     * 滑动窗口
     *
     * @param key        唯一码
     * @param idempotent 幂等参数对象(duration,times)
     * @return Boolean
     */
    @Override
    public Boolean sliding(String key, Idempotent idempotent) {
        // 单次幂等控制
        if (idempotent.times() == 1) {
            return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(idempotent.duration()));
        }

        // 多次幂等控制
        Boolean isSet = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(idempotent.duration()));
        // 首次设置直接通过
        if (isSet) {
            return true;
        }
        // 后续自增后检查是否超过最大容忍次数
        else {
            return stringRedisTemplate.opsForValue().increment(key) <= idempotent.times();
        }
//        return stringRedisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMillis(idempotent.duration()));
    }
}
