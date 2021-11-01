package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.aspects;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.BackendDesappApiApplication;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper.UserDetail;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller..*(..))")
    public Object logEndpoint(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        BackendDesappApiApplication.logger.info(
                "\n" +
                        getTime() +
                        getControllerMethod(joinPoint) +
                        getMethodParameters(joinPoint) +
                        getExecutionTime(startTime) +
                        "\n"
        );

        return proceed;
    }

    private String getTime() {
        return "Date is " + new Date() + "\n";
    }

    private String getControllerMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return "Method called is " + signature.getMethod().getName() + "\n";
    }

    private String getMethodParameters(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return "Parameters are " + Arrays.toString(signature.getParameterNames()) + "\n";
    }

    private String getExecutionTime(Long startTime) {
        return "Execution time is " + (System.currentTimeMillis() - startTime) + " milliseconds" + "\n";
    }
}