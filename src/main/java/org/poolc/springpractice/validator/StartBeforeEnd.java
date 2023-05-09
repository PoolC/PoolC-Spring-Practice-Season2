package org.poolc.springpractice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StartBeforeEndValidator.class)
public @interface StartBeforeEnd {
    public String message() default "The starting date/time should come before ending date/time.";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
