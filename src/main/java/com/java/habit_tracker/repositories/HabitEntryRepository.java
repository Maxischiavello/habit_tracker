package com.java.habit_tracker.repositories;

import com.java.habit_tracker.models.HabitEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface HabitEntryRepository extends JpaRepository<HabitEntry, Long> {
    List<HabitEntry> findByHabitId(Long habitId);
    List<HabitEntry> findByDate(LocalDate date);
}
