package org.poolc.springpractice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BothOrNoneTimeValidator.class)
public @interface BothOrNoneTime {
    public String message() default "You can either choose to specify both the start and end time or not specify both.";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
