# Idempotent
SpringBoot幂等防重组件，基于Redis滤重，一行注解开箱即用

## 配置说明
>SpringBoot项目中需已提前接入**redis**服务，本组件已启用自动配置，默认依赖使用StringRedisTemplate来执行**redis**操作

## 快速上手
>
1、引入maven依赖
```xml
<dependency>
    <groupId>top.xserver</groupId>
    <artifactId>idempotent-spring-boot-starter</artifactId>
    <version>0.0.3.RELEASE</version>
</dependency>
```
2、在控制器方法上加入幂等注解
```java
@Idempotent(key = "#obj.userId + #obj.postId")
@PostMapping("/post")
public String doPost(@RequestBody PostObj obj) {
    ...
}
```

## 参数说明
>幂等KEY采用SPEL表达式，可根据实际项目需求自由组合
```
key | value    幂等唯一键   （必须，SPEL表达式）
type           策略类型     （默认固定窗口）
prefix         前缀        （默认IDEMPOTENT）
duration       持续时间     （默认300ms）
msg            防重消息     （默认repeat）
exception      重复异常     （默认IdempotentException）
```

帮助联系
>
	作者:cheneyxu
	邮箱:457299596@qq.com
	QQ:457299596
    
