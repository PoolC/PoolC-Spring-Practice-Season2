package org.poolc.springpractice.validator;

import org.poolc.springpractice.payload.request.schedule.ScheduleRequest;
import org.poolc.springpractice.payload.request.schedule.ScheduleUpdateRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BothOrNoneTimeValidator implements ConstraintValidator<BothOrNoneTime, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value.getClass().equals(ScheduleRequest.class)) {
            ScheduleRequest scheduleRequest = (ScheduleRequest) value;
            return scheduleRequest.getEndTime()!=null && scheduleRequest.getStartTime()!=null;
        }
        else if (value.getClass().equals(ScheduleUpdateRequest.class)) {
            ScheduleUpdateRequest scheduleUpdateRequest = (ScheduleUpdateRequest) value;
            return scheduleUpdateRequest.getStartTime()!=null && scheduleUpdateRequest.getEndTime()!=null;
        }
        return false;
    }
}
