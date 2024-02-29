package com.example.swipems.util;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwipeEvent {

    @NotBlank(message = "Employee Id is required")
    private String employeeId;
    private SwipeType swipeType;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

}
