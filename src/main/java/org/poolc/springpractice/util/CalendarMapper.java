package org.poolc.springpractice.util;

import org.mapstruct.*;
import org.poolc.springpractice.model.Calendar;
import org.poolc.springpractice.payload.request.calendar.CalendarRequest;
import org.poolc.springpractice.payload.request.calendar.CalendarUpdateRequest;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildCalendarFromRequest(CalendarRequest calendarRequest, @MappingTarget Calendar calendar);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCalendarFromRequest(CalendarUpdateRequest calendarUpdateRequest, @MappingTarget Calendar calendar);
}
