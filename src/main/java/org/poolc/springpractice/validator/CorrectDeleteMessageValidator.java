package org.poolc.springpractice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectDeleteMessageValidator implements ConstraintValidator<CorrectDeleteMessage, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("I confirm the deletion.");
    }
}
