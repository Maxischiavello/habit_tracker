package com.java.habit_tracker.controllers;

import com.java.habit_tracker.models.Habit;
import com.java.habit_tracker.services.HabitService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class DashboardController implements Initializable {

    @Autowired
    private HabitService habitService;

    @FXML
    private ListView<String> todayHabitsListView;

    @FXML
    private Label completedHabitsLabel;

    @FXML
    private Label completionPercentageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodayHabits();
        loadQuickStats();
    }

    private void loadTodayHabits() {
        List<Habit> allHabits = habitService.getAllHabits();
        List<String> todayHabits = allHabits.stream()
                .map(Habit::getName)
                .toList();
        todayHabitsListView.getItems().addAll(todayHabits);
    }

    private void loadQuickStats() {
        List<Habit> allHabits = habitService.getAllHabits();
        long completedHabits = allHabits.stream().filter(Habit::isActive).count();
        double completionPercentage = (double) completedHabits / allHabits.size() * 100;

        completedHabitsLabel.setText("Habits Completed: " + completedHabits);
        completionPercentageLabel.setText(String.format("Completion Percentage: %.2f%%", completionPercentage));
    }
}
