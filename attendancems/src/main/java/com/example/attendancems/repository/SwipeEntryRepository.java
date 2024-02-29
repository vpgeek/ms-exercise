package com.example.attendancems.repository;

import com.example.attendancems.entity.SwipeEntry;
import com.example.attendancems.util.SwipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SwipeEntryRepository extends JpaRepository<SwipeEntry, Long> {

        public Optional<SwipeEntry> findFirstByEmployeeIdAndSwipeTypeAndTimestampBetween(
                String employeeId, SwipeType swipeType, LocalDateTime start, LocalDateTime end, Sort sort);

}
