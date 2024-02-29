package com.example.attendancems.consumer;

import com.example.attendancems.service.SwipeEntryService;
import com.example.attendancems.util.SwipeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SwipeEventConsumer {

    @Autowired
    private SwipeEntryService swipeEntryService;

    @KafkaListener(topics = "attendance-topic-1", groupId = "attendance-group-1")
    public void handleSwipeEvent(SwipeEvent swipeEvent) {
        swipeEntryService.handleSwipeEvent(swipeEvent);
    }

}
