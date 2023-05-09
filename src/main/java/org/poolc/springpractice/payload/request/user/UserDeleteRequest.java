package org.poolc.springpractice.payload.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.poolc.springpractice.validator.CorrectDeleteMessage;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class UserDeleteRequest {
    @NotEmpty
    @CorrectDeleteMessage
    private String deleteMessage;
}