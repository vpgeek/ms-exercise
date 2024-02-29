package com.example.swipems.entity;

import com.example.swipems.util.SwipeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwipeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;

    @Enumerated(EnumType.STRING)
    private SwipeType swipeType;

    private LocalDateTime timestamp;
}
