package com.example.attendancems.controller;

import com.example.attendancems.dto.AttendanceResponse;
import com.example.attendancems.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/totalHours/{employeeId}")
    public ResponseEntity<AttendanceResponse> getTotalHours(@PathVariable String employeeId, @RequestParam LocalDate date) {
        // get total attendance hours from db and calculate attendance status
        AttendanceResponse attendanceResponse = attendanceService.getTotalHours(employeeId, date);
        return ResponseEntity.ok(attendanceResponse);
    }
}
