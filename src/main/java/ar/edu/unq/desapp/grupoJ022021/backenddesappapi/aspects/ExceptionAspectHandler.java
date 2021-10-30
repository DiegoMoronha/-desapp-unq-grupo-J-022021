package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.aspects;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions.CancelTransactionException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions.UserAlreadyExistsException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions.UserDoesntExistException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions.ValidationException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ErrorLoginRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import io.jsonwebtoken.MalformedJwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
class ExceptionAspectHandler {

   private ErrorLoginRegisterDto error =new ErrorLoginRegisterDto();

    @Around("@annotation(ar.edu.unq.desapp.grupoJ022021.backenddesappapi.aspects.ExceptionAspect)")
   public Object exceptionAspectHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (UserAlreadyExistsException ex) {

            error.setError_msg(ex.getMessage());
            return ResponseEntity.status(409).body(error);
        } catch (UserDoesntExistException ex) {
            error.setError_msg(ex.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (ValidationException ex) {
            error.setError_msg(ex.getMessage());
            return ResponseEntity.status(400).body(error);
        } catch (CancelTransactionException e) {
            TransactionBooleanResponseDto resp = new TransactionBooleanResponseDto();
            resp.setErr_msg(e.getMessage());
            return ResponseEntity.status(404).body(resp);
        }
    }
}