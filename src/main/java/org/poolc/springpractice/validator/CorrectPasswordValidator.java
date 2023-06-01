package org.poolc.springpractice.validator;

import org.poolc.springpractice.payload.request.user.UserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectPasswordValidator implements ConstraintValidator<CorrectPassword, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserRequest userRequest = (UserRequest) value;
        return userRequest.getPassword().equals(userRequest.getConfirmPassword());
    }

}
