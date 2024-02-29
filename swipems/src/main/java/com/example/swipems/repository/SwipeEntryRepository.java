package com.example.swipems.repository;

import com.example.swipems.entity.SwipeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwipeEntryRepository extends JpaRepository<SwipeEntry, Long> {
    public SwipeEntry findTopByEmployeeIdOrderByTimestampDesc(String employeeId);
}
