package web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class UserDaoAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoAspect.class);

    @Around("@within(org.springframework.stereotype.Repository)")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        long timeStart = System.nanoTime();
        Object result = joinPoint.proceed();
        long timeEnd = System.nanoTime();
        logger.info("The method was performed: {}ns", timeEnd - timeStart);
        logger.info("{}, {}", "Package: ", joinPoint.getSignature().getDeclaringTypeName());
        logger.info("{}, {}", "Method: ", joinPoint.getSignature().getName());
        return result;
    }

    @AfterThrowing(pointcut = "execution(* web.dao.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        Class<?>[] parameterTypes = signature.getParameterTypes();

        logger.error("=== EXCEPTION INFORMATION ===");
        logger.error("Method: {}.{}", className, methodName);
        logger.error("Method parameters: {}", Arrays.toString(parameterTypes));
        logger.error("Exception type: {}", ex.getClass().getName());
        logger.error("Message: {}", ex.getMessage());
    }
}
