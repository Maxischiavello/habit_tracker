package com.java.habit_tracker.controllers;

import com.java.habit_tracker.models.Habit;
import com.java.habit_tracker.services.HabitService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.CheckBox;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {

    @Autowired
    private HabitService habitService;

    @FXML
    private Label totalHabitsCompleted;

    @FXML
    private Label overallCompletionRate;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<HBox> habitListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadHabits();
        updateStatistics();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> loadHabits());
    }

    private void loadHabits() {
        habitListView.getItems().clear();
        String searchText = searchField.getText().toLowerCase();
        List<Habit> habits = habitService.getAllHabits().stream()
                .filter(habit -> habit.getName().toLowerCase().contains(searchText))
                .toList();

        for (Habit habit : habits) {
            HBox habitBox = new HBox(10);
            habitBox.setAlignment(Pos.CENTER_LEFT);

            CheckBox habitCheckBox = new CheckBox(habit.getName());
            habitCheckBox.setSelected(habit.isCompletedToday());

            habitCheckBox.setOnAction(event -> {
                habit.setCompletedToday(habitCheckBox.isSelected());
                habitService.updateHabit(habit);
                updateStatistics();
            });

            Label completionRateLabel = new Label(String.format("%.2f%%", habit.getWeeklyCompletionRate()));
            HBox.setHgrow(completionRateLabel, Priority.ALWAYS);
            completionRateLabel.setMaxWidth(Double.MAX_VALUE);
            completionRateLabel.setAlignment(Pos.CENTER_RIGHT);

            habitBox.getChildren().addAll(habitCheckBox, completionRateLabel);
            habitListView.getItems().add(habitBox);
        }
    }

    private void updateStatistics() {
        List<Habit> habits = habitService.getAllHabits();
        long completedToday = habits.stream().filter(Habit::isCompletedToday).count();
        double overallCompletionRateValue = habits.stream()
                .mapToDouble(Habit::getWeeklyCompletionRate)
                .average()
                .orElse(0.0);

        totalHabitsCompleted.setText(String.valueOf(completedToday));
        overallCompletionRate.setText(String.format("%.2f%%", overallCompletionRateValue));
    }
}
