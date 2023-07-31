package com.example.springbootaopredis.util;

import com.example.springbootaopredis.exception.CommonExcept;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @title RepeatSubmitAspect
 * @Author zhongcg
 * @Description TODO
 * @Date 2023/6/15 16:21
 **/
@Slf4j
@Component
@Aspect
public class RepeatSubmitAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.example.springbootaopredis.util.RepeatSubmit)")
    public void repeatSubmit() {}

    @Around("repeatSubmit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取防重复提交注解
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
        // 获取token当做key，没有token的话可以采用同一方法，同一用户,同一接口拼接锁前缀
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new CommonExcept("token不存在，请登录！");
        }
        String url = request.getRequestURI();
        /**
         *  通过前缀 + url + token 来生成redis上的 key
         *  可以在加上用户id，这里没办法获取，大家可以在项目中加上
         */
        String redisKey = "repeat_submit_key:".concat(url).concat(":").concat(token);
        log.info("==========redisKey ====== {}",redisKey);

        if (!redisTemplate.hasKey(redisKey)) {
            redisTemplate.opsForValue().set(redisKey, redisKey, annotation.expireTime(), TimeUnit.SECONDS);
            //正常执行方法并返回
            return joinPoint.proceed();
        } else {
            // 抛出异常
            throw new CommonExcept("请勿重复提交");
        }
    }

}
