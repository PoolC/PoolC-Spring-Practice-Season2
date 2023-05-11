package org.poolc.springpractice.payload.response;

import lombok.Builder;

@Builder(toBuilder = true)
public class UserDto {
    private String username;
    private String email;

    public UserDto() {}
}
