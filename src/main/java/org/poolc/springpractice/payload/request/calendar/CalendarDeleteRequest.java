package org.poolc.springpractice.payload.request.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.poolc.springpractice.validator.CorrectDeleteMessage;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class CalendarDeleteRequest {

    @NotEmpty
    private  String title;

    @NotEmpty
    @CorrectDeleteMessage
    private String deleteMessage;

    public CalendarDeleteRequest() {}
}
