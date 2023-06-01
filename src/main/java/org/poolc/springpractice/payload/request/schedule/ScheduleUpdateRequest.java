package org.poolc.springpractice.payload.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.poolc.springpractice.validator.StartBeforeEnd;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder(toBuilder = true)
@StartBeforeEnd
public class ScheduleUpdateRequest {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    public ScheduleUpdateRequest() {}
}
