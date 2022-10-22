package uhs.alphabet.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class TimerAop {
    @Pointcut("execution(* uhs.alphabet..*.*(..))")
    private void cut() {
    }

    @Pointcut("@annotation(uhs.alphabet.annotation.Timer)")
    public void enableTimer() {
    }

    @Around("cut() && enableTimer()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Long before = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        Long after = System.currentTimeMillis();
        Long res = after - before;
        Logger logger = LoggerFactory.getLogger(TimerAop.class);
        logger.info(method.getName() + " method takes " + res / 1000 + "sec");
        return obj;
    }
}