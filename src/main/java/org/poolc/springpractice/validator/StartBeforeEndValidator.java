package org.poolc.springpractice.validator;

import org.poolc.springpractice.payload.request.schedule.ScheduleRequest;
import org.poolc.springpractice.payload.request.schedule.ScheduleUpdateRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalTime;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value.getClass().equals(ScheduleRequest.class)) {
            ScheduleRequest scheduleRequest = (ScheduleRequest) value;
            return validateDateTime(scheduleRequest.getStartDate(),
                    scheduleRequest.getEndDate(),
                    scheduleRequest.getStartTime(),
                    scheduleRequest.getEndTime()
            );
        }
        else if (value.getClass().equals(ScheduleUpdateRequest.class)) {
            ScheduleUpdateRequest scheduleUpdateRequest = (ScheduleUpdateRequest) value;
            return validateDateTime(scheduleUpdateRequest.getStartDate(),
                    scheduleUpdateRequest.getEndDate(),
                    scheduleUpdateRequest.getStartTime(),
                    scheduleUpdateRequest.getEndTime()
            );
        }
        return false;
    }

    private boolean validateDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return startDate.isBefore(endDate) || (startDate.isEqual(endDate) && startTime.isBefore(endTime));
    }

}
