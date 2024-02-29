package com.example.attendancems.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwipeEvent {

    private String employeeId;
    private SwipeType swipeType;
    private LocalDateTime timestamp;

}
