package org.poolc.springpractice.payload.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.poolc.springpractice.validator.StartBeforeEnd;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder(toBuilder = true)
@StartBeforeEnd
public class ScheduleRequest {
    @NotBlank
    @Length(max = 50)
    private String title;

    @NotEmpty
    private LocalDate startDate;

    @NotEmpty
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;
    @Length(max = 200)
    private String description;

    public ScheduleRequest() {}

}
