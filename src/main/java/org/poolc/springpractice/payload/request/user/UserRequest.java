package org.poolc.springpractice.payload.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.poolc.springpractice.validator.CorrectPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@ToString
@Builder(toBuilder = true)
@CorrectPassword
public class UserRequest {

    @NotBlank(message = "Username is required for registration.")
    @Size(min = 4, max = 12)
    private String username;

    @NotEmpty(message = "Password is required for registration.")
    @Size(min = 8)
    private String password;

    @NotEmpty(message = "Confirmation of password is required for registration.")
    @Size(min = 8)
    private String confirmPassword;

    @NotEmpty(message = "Email is required for registration.")
    @Email
    private String email;

    public UserRequest() {}
}
