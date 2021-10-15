package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.aspects;

import net.bytebuddy.implementation.attribute.AnnotationRetention;
import org.jboss.jandex.AnnotationTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionAspect {

}
