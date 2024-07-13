package com.java.habit_tracker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }
}
