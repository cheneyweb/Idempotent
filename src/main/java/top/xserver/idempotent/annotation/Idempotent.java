package top.xserver.idempotent.annotation;

import org.springframework.core.annotation.AliasFor;
import top.xserver.idempotent.constant.IdempotentTypeEnum;
import top.xserver.idempotent.exception.IdempotentException;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Idempotent {

    @AliasFor("value")
    String key() default "";

    /**
     * 重复关键KEY
     *
     * @return long
     */
    @AliasFor("key")
    String value() default "";

    /**
     * KEY前缀
     *
     * @return String
     */
    String prefix() default "IDEMPOTENT_";

    /**
     * 拦截策略(默认固定窗口)
     *
     * @return RepeatTypeEnum
     */
    IdempotentTypeEnum type() default IdempotentTypeEnum.FIXED_WINDOW;

    /**
     * 时间范围
     *
     * @return long
     */
    long duration() default 300;

    /**
     * 错误提示
     *
     * @return String
     */
    String msg() default "repeat";

    /**
     * 返回指定异常
     *
     * @return Class
     */
    Class<? extends Exception> exception() default IdempotentException.class;
}
