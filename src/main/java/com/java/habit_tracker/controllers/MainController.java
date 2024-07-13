package com.java.habit_tracker.controllers;

import com.java.habit_tracker.ApplicationContextHolder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Pane mainContent;

    @FXML
    private void showDashboard() throws IOException {
        loadView("fxml/Dashboard.fxml");
    }

    @FXML
    private void showManageHabits() throws IOException {
        loadView("fxml/Habit.fxml");
    }

    @FXML
    private void showSettings() throws IOException {
        loadView("fxml/Settings.fxml");
    }

    private void loadView(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlPath));
        loader.setControllerFactory(ApplicationContextHolder::getBean);  // Reemplaza springContext::getBean con ApplicationContextHolder::getBean
        Pane view = loader.load();
        mainContent.getChildren().setAll(view);
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
