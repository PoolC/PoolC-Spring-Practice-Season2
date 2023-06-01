package org.poolc.springpractice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CorrectPasswordValidator.class)
public @interface CorrectPassword {
    public String message() default "Your passwords should match.";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
