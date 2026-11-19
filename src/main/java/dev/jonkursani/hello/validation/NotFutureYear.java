package dev.jonkursani.hello.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// 1. kush e kontrollon
@Constraint(validatedBy = NotFutureYearValidator.class)
public @interface NotFutureYear {
    // 2. mesazhi kur s'kalon
    String message() default "Year cannot be in the future";
    // 3. dy rreshta qe i kerkon Bean Validation
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
