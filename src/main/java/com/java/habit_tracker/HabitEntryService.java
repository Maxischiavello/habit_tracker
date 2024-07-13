package com.java.habit_tracker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitEntryService {
    private final HabitEntryRepository habitEntryRepository;

    public HabitEntry createEntry(HabitEntry entry) {
        return habitEntryRepository.save(entry);
    }

    public HabitEntry updateEntry(HabitEntry entry) {
        return habitEntryRepository.save(entry);
    }

    public void deleteEntry(Long id) {
        habitEntryRepository.deleteById(id);
    }

    public List<HabitEntry> getEntriesByHabit(Long habitId) {
        return habitEntryRepository.findByHabitId(habitId);
    }
}
