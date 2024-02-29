package com.example.attendancems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private String employeeId;
    private LocalTime totalHours;
    private String attendanceStatus;

}
