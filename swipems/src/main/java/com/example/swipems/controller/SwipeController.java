package com.example.swipems.controller;

import com.example.swipems.service.SwipeEntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.swipems.util.SwipeType;
import com.example.swipems.util.SwipeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

@RestController
@RequestMapping("/api/swipe")
public class SwipeController {

    @Autowired
    private SwipeEntryService swipeEntryService;

    @Autowired
    private KafkaTemplate<String, SwipeEvent> kafkaTemplate;

    @PostMapping("/in")
    public ResponseEntity<String> swipeIn(@Valid @RequestBody SwipeEvent swipeEvent) {
        try {
            swipeEntryService.validateSwipeIn(swipeEvent);
            swipeEvent.setSwipeType(SwipeType.IN);
            kafkaTemplate.send("attendance-topic-1", swipeEvent);
            return ResponseEntity.ok("Swiped in successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/out")
    public ResponseEntity<String> swipeOut(@Valid @RequestBody SwipeEvent swipeEvent) {
        try {
            swipeEntryService.validateSwipeOut(swipeEvent);
            swipeEvent.setSwipeType(SwipeType.OUT);
            kafkaTemplate.send("attendance-topic-1", swipeEvent);
            return ResponseEntity.ok("Swiped out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}