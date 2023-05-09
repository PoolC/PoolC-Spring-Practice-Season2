package org.poolc.springpractice.payload.request.user;

import javax.validation.constraints.Size;

public class UserUpdateRequest {

    private String password;
    private String confirmPassword;

    public UserUpdateRequest() {}
}
