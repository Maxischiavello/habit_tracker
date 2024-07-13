package com.java.habit_tracker.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String description;
    private boolean active;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HabitEntry> entries = new ArrayList<>();

    @Transient
    private boolean completedToday;

    @Transient
    private double weeklyCompletionRate;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateTransientFields() {
        LocalDate today = LocalDate.now();
        this.completedToday = entries.stream().anyMatch(entry -> entry.getDate().isEqual(today) && entry.isCompleted());
        calculateWeeklyCompletionRate();
    }

    public void setCompletedToday(boolean completedToday) {
        LocalDate today = LocalDate.now();
        HabitEntry todayEntry = entries.stream()
                .filter(entry -> entry.getDate().isEqual(today))
                .findFirst()
                .orElse(null);

        if (todayEntry == null) {
            todayEntry = new HabitEntry();
            todayEntry.setDate(today);
            todayEntry.setCompleted(completedToday);
            todayEntry.setHabit(this);
            entries.add(todayEntry);
        } else {
            todayEntry.setCompleted(completedToday);
        }

        this.completedToday = completedToday;
        calculateWeeklyCompletionRate();
    }

    private void calculateWeeklyCompletionRate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        long completedCount = entries.stream()
                .filter(entry -> !entry.getDate().isBefore(startOfWeek) && entry.isCompleted())
                .count();

        long daysInWeek = Math.min(today.getDayOfWeek().getValue(), 7);
        this.weeklyCompletionRate = (completedCount / (double) daysInWeek) * 100;
    }
}
