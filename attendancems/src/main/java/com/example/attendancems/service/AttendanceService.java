package com.example.attendancems.service;

import com.example.attendancems.dto.AttendanceResponse;
import com.example.attendancems.entity.Attendance;
import com.example.attendancems.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public AttendanceResponse getTotalHours(String employeeId, LocalDate date) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findByEmployeeIdAndAttendanceDate(employeeId, date);
        if (optionalAttendance.isPresent()) {
            Attendance attendance = optionalAttendance.get();

            String attendanceStatus = calculateAttendanceStatus(attendance.getTotalHours());
            return new AttendanceResponse(employeeId, attendance.getTotalHours(), attendanceStatus);
        } else {
            return new AttendanceResponse(employeeId, LocalTime.MIDNIGHT, "Absent");
        }
    }

    private String calculateAttendanceStatus(LocalTime totalHours) {
        long totalSeconds = totalHours.toSecondOfDay();
        if (totalSeconds < 4 * 3600) {
            return "Absent";
        } else if (totalSeconds < 8 * 3600) {
            return "Half day";
        } else {
            return "Present";
        }
    }

}
