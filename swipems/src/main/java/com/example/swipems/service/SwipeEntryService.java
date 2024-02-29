package com.example.swipems.service;

import com.example.swipems.entity.SwipeEntry;
import com.example.swipems.repository.SwipeEntryRepository;
import com.example.swipems.util.SwipeEvent;
import com.example.swipems.util.SwipeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwipeEntryService {

    @Autowired
    private SwipeEntryRepository swipeEntryRepository;

    public void validateSwipeIn(SwipeEvent swipeEvent) {

        // check if there is a previous or previous-out entry for employee
        SwipeEntry prevSwipeEntry = swipeEntryRepository.findTopByEmployeeIdOrderByTimestampDesc(swipeEvent.getEmployeeId());
        if (prevSwipeEntry != null && prevSwipeEntry.getSwipeType() != SwipeType.OUT) {
            throw new IllegalStateException("Invalid Entry! You cannot swipe in again");
        }

        // check if timestamp is greater than or equal to previous entry
        if (prevSwipeEntry != null && swipeEvent.getTimestamp().isBefore(prevSwipeEntry.getTimestamp())) {
            throw new IllegalStateException("Invalid Timestamp! It should be greater than or equal to your previous entry");
        }
    }

    public void validateSwipeOut(SwipeEvent swipeEvent) {

        // check if there is a previous or previous-in entry for employee
        SwipeEntry prevSwipeEntry = swipeEntryRepository.findTopByEmployeeIdOrderByTimestampDesc(swipeEvent.getEmployeeId());
        if (prevSwipeEntry != null && prevSwipeEntry.getSwipeType() != SwipeType.IN) {
            throw new IllegalStateException("Invalid Entry! You cannot swipe out again");
        }

        // check if timestamp is greater than or equal to previous entry
        if (prevSwipeEntry != null && swipeEvent.getTimestamp().isBefore(prevSwipeEntry.getTimestamp())) {
            throw new IllegalStateException("Invalid Timestamp! It should be greater than or equal to your previous entry");
        }
    }

}
