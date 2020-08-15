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
    // 注入启动监听
    @Bean
    public IdempotentStartListener listener() {
        return new IdempotentStartListener();
    }

    // 注入防护实现
    @Bean
    public Protector idempotent(StringRedisTemplate stringRedisTemplate) {
        return new RedisProtector(stringRedisTemplate);
    }

    // 注入AOP切面
    @Bean
    public IdempotentInterceptor idempotentInterceptor(Protector idempotent) {
        return new IdempotentInterceptor(idempotent);
    }
}
