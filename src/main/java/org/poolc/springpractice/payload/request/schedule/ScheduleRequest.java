package org.poolc.springpractice.payload.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder(toBuilder = true)
public class ScheduleRequest {
    @NotBlank
    private String title;

    @NotEmpty
    private LocalDate startDate;

    @NotEmpty
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    public ScheduleRequest() {}

}
