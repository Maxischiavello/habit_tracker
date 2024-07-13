package com.java.habit_tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByCategory(String category);
    List<Habit> findByActive(boolean active);
}