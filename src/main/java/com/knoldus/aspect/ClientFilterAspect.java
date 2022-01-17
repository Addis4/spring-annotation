package com.knoldus.aspect;

import com.knoldus.helper.SmokeTestClientFilter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class ClientFilterAspect {
    private final Logger logger = LoggerFactory.getLogger(ClientFilterAspect.class);
    SmokeTestClientFilter smokeTestHelper = new SmokeTestClientFilter();

    @Around(value = "@annotation(com.knoldus.aspect.ClientFilter)")
    public Object successfulSmokeTestClientFilter(ProceedingJoinPoint joinPoint) throws Throwable {

        if (smokeTestHelper.isValid(joinPoint)) {
            logger.trace("Continuing the flow because it is appropriate clientId");
            return joinPoint.proceed();
        } else {
            logger.info("Skipping the flow because this type of clientId is for Smoke Tests");
            return new Object();
        }
    }

}
