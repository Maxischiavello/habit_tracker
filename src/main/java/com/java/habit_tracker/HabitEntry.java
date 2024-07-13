package com.java.habit_tracker;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "habit_entries")
@Data
public class HabitEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;
}