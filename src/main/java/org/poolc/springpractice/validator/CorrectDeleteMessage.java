package org.poolc.springpractice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CorrectDeleteMessageValidator.class)
public @interface CorrectDeleteMessage {
    public String message() default "Please type the delete confirmation message correctly.";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
