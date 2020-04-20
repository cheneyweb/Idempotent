package top.xserver.idempotent;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import top.xserver.idempotent.annotation.Idempotent;
import top.xserver.idempotent.constant.IdempotentTypeEnum;
import top.xserver.idempotent.protector.Protector;

import java.lang.reflect.Constructor;

@Aspect
public class IdempotentInterceptor {
    private Protector protector;

    public IdempotentInterceptor(Protector protector) {
        this.protector = protector;
    }

    @Before("@annotation(top.xserver.idempotent.annotation.Idempotent)")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        // 获取方法切面信息，注解信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        Idempotent idempotent = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Idempotent.class);
        // 将方法参数和值放入上下文，解析SpEL表达式获取结果
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            ctx.setVariable(paramNames[i], paramValues[i]);
        }
        Object keyObj = new SpelExpressionParser().parseExpression(idempotent.key()).getValue(ctx);
        if (keyObj == null || !isIdempotent(keyObj.toString(), idempotent)) {
            Class<? extends Exception> exception = idempotent.exception();
            Constructor<? extends Exception> declaredConstructor = exception.getDeclaredConstructor(String.class);
            throw declaredConstructor.newInstance(idempotent.msg());
        }
    }

    private boolean isIdempotent(String key, Idempotent idempotent) {
        key = idempotent.prefix() + key.hashCode();
        if (IdempotentTypeEnum.FIXED_WINDOW == idempotent.type()) {
            return protector.fixed(key, idempotent.duration());
        } else if (IdempotentTypeEnum.SLIDING_WINDOW == idempotent.type()) {
            return protector.sliding(key, idempotent.duration());
        }
        return protector.sliding(key, idempotent.duration());
    }
}
