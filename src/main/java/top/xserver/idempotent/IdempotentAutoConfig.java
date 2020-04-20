package top.xserver.idempotent;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.xserver.idempotent.protector.Protector;
import top.xserver.idempotent.protector.RedisProtector;

@Configuration
@ConditionalOnClass(RedisAutoConfiguration.class)
public class IdempotentAutoConfig {
    @Bean
    public IdempotentStartListener listener() {
        return new IdempotentStartListener();
    }

    @Bean
    public Protector idempotent(StringRedisTemplate stringRedisTemplate) {
        return new RedisProtector(stringRedisTemplate);
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor(Protector idempotent) {
        return new IdempotentInterceptor(idempotent);
    }
}
