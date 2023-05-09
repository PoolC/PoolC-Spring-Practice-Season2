package org.poolc.springpractice.payload.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class UserDeleteRequest {
    @NotEmpty
    private String deleteMessage;
}