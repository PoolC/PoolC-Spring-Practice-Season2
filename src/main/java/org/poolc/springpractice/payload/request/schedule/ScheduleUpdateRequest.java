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
public class ScheduleUpdateRequest {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    public ScheduleUpdateRequest() {}
}
