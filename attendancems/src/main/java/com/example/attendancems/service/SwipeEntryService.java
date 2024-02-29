package com.example.attendancems.service;

import com.example.attendancems.entity.Attendance;
import com.example.attendancems.entity.SwipeEntry;
import com.example.attendancems.repository.AttendanceRepository;
import com.example.attendancems.util.SwipeEvent;
import com.example.attendancems.repository.SwipeEntryRepository;
import com.example.attendancems.util.SwipeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class SwipeEntryService {

    @Autowired
    private SwipeEntryRepository swipeEntryRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void handleSwipeEvent(SwipeEvent swipeEvent) {
        // process swipe event and store it in db
        SwipeEntry swipeEntry = convertToSwipeEntryEntity(swipeEvent);
        swipeEntryRepository.save(swipeEntry);

        // for out-entry, calculate total hours and update in db
        if (SwipeType.OUT.equals(swipeEntry.getSwipeType())) {
            // find the latest in-entry of the employee
            LocalDateTime outTime = swipeEntry.getTimestamp();
            LocalDateTime inTime = findMostRecentInTime(swipeEntry.getEmployeeId(), outTime.toLocalDate());

            if (inTime != null) {
                Duration duration = Duration.between(inTime, outTime);

                // get attendance entry of the employee for the date
                Optional<Attendance> existingAttendance = attendanceRepository.findByEmployeeIdAndAttendanceDate(swipeEntry.getEmployeeId(), outTime.toLocalDate());
                if (existingAttendance.isPresent()) {
                    // update the existing entry
                    Attendance attendance = existingAttendance.get();
                    attendance.setTotalHours(attendance.getTotalHours().plus(duration));
                    attendanceRepository.save(attendance);
                } else {
                    // create a new entry
                    Attendance newAttendance = new Attendance();
                    newAttendance.setEmployeeId(swipeEntry.getEmployeeId());
                    newAttendance.setAttendanceDate(outTime.toLocalDate());
                    newAttendance.setTotalHours(LocalTime.MIDNIGHT.plusSeconds(duration.toSeconds()));
                    attendanceRepository.save(newAttendance);
                }
            }
        }
    }

    private LocalDateTime findMostRecentInTime(String employeeId, LocalDate attendanceDate) {
        LocalDateTime start = attendanceDate.atStartOfDay();
        LocalDateTime end = attendanceDate.atTime(LocalTime.MAX);

        Optional<SwipeEntry> recentInEntry = swipeEntryRepository.findFirstByEmployeeIdAndSwipeTypeAndTimestampBetween(
                employeeId, SwipeType.IN, start, end, Sort.by(Sort.Direction.DESC, "timestamp"));

        return recentInEntry.map(SwipeEntry::getTimestamp).orElse(null);
    }

    private SwipeEntry convertToSwipeEntryEntity(SwipeEvent swipeEvent) {
        // convert SwipeEvent to SwipeEntryEntity
        SwipeEntry swipeEntry = new SwipeEntry();
        swipeEntry.setEmployeeId(swipeEvent.getEmployeeId());
        swipeEntry.setSwipeType(swipeEvent.getSwipeType());
        swipeEntry.setTimestamp(swipeEvent.getTimestamp());
        return swipeEntry;
    }

}
