package org.poolc.springpractice.payload.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.poolc.springpractice.validator.CorrectPassword;

import javax.validation.constraints.Size;

@CorrectPassword
@Getter
@ToString
@Builder(toBuilder = true)
public class UserUpdateRequest {

    private String password;
    private String confirmPassword;

    public UserUpdateRequest() {}
}
