package org.poolc.springpractice.service;

import lombok.RequiredArgsConstructor;
import org.poolc.springpractice.exception.InvalidRequestException;
import org.poolc.springpractice.model.Calendar;
import org.poolc.springpractice.model.User;
import org.poolc.springpractice.payload.request.calendar.CalendarDeleteRequest;
import org.poolc.springpractice.payload.request.calendar.CalendarRequest;
import org.poolc.springpractice.repository.CalendarRepository;
import org.poolc.springpractice.repository.UserRepository;
import org.poolc.springpractice.util.CalendarMapper;
import org.poolc.springpractice.util.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final CalendarMapper calendarMapper;

    public void saveCalendar(String username, CalendarRequest calendarRequest) throws InvalidRequestException {
        Calendar calendar = new Calendar();
        calendarMapper.buildCalendarFromRequest(calendarRequest, calendar);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException(Message.USER_DOES_NOT_EXIST));

        calendar.setUser(user);

        calendarRepository.save(calendar);
    }

    public void deleteCalendar(String username, CalendarDeleteRequest calendarDeleteRequest) throws InvalidRequestException {
        Calendar calendar = calendarRepository.findByTitle(calendarDeleteRequest.getTitle())
                .orElseThrow(() -> new InvalidRequestException(Message.CALENDAR_DOES_NOT_EXIST));

        if (calendar.getUser().getUsername().equals(username)) {
            calendarRepository.deleteById(calendar.getId());
        } else {
            throw new InvalidRequestException(Message.ACCESS_DENIED);
        }
    }


}
