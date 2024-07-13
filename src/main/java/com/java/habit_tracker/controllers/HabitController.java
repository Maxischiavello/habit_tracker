package com.java.habit_tracker.controllers;

import com.java.habit_tracker.models.Habit;
import com.java.habit_tracker.services.HabitService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HabitController implements Initializable {

    @Autowired
    private HabitService habitService;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private ListView<String> habitListView;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadHabits();
        habitListView.setOnMouseClicked(this::handleHabitSelection);
    }

    @FXML
    private void addHabit() {
        String name = nameTextField.getText();
        String category = categoryTextField.getText();
        String description = descriptionTextField.getText();
        boolean active = activeCheckBox.isSelected();

        Habit habit = new Habit();
        habit.setName(name);
        habit.setCategory(category);
        habit.setDescription(description);
        habit.setActive(active);

        habitService.createHabit(habit);
        habitListView.getItems().add(habit.getName());
        clearFields();
    }

    @FXML
    private void deleteHabit() {
        String selectedHabitName = habitListView.getSelectionModel().getSelectedItem();
        if (selectedHabitName != null) {
            habitService.getAllHabits().stream()
                    .filter(habit -> habit.getName().equals(selectedHabitName))
                    .findFirst()
                    .ifPresent(habit -> habitService.deleteHabit(habit.getId()));
            habitListView.getItems().remove(selectedHabitName);
            deleteButton.setDisable(true);
        }
    }

    private void loadHabits() {
        habitListView.getItems().clear();
        habitService.getAllHabits().forEach(habit -> habitListView.getItems().add(habit.getName()));
    }

    private void clearFields() {
        nameTextField.clear();
        categoryTextField.clear();
        descriptionTextField.clear();
        activeCheckBox.setSelected(false);
    }

    private void handleHabitSelection(MouseEvent event) {
        String selectedHabit = habitListView.getSelectionModel().getSelectedItem();
        deleteButton.setDisable(selectedHabit == null);
    }
}
