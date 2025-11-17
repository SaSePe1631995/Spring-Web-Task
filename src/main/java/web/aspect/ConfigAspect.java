package web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class ConfigAspect {

    private static final Logger logger = LoggerFactory.getLogger(ConfigAspect.class);

    @Before("@annotation(org.springframework.context.annotation.Bean)")
    public void logConfig(JoinPoint joinPoint) {
        logger.info("{}, {}", "Package: ", joinPoint.getSignature().getDeclaringTypeName());
        logger.info("{}, {}", "Method: ", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(org.springframework.context.annotation.Bean)", throwing = "ex")
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
