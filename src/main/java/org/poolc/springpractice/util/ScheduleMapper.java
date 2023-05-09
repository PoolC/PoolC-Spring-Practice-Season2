package org.poolc.springpractice.util;

import org.mapstruct.*;
import org.poolc.springpractice.model.Schedule;
import org.poolc.springpractice.payload.request.schedule.ScheduleRequest;
import org.poolc.springpractice.payload.request.schedule.ScheduleUpdateRequest;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildScheduleFromRequest(ScheduleRequest scheduleRequest, @MappingTarget Schedule schedule);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateScheduleFromRequest(ScheduleUpdateRequest scheduleUpdateRequest, @MappingTarget Schedule schedule);
}
