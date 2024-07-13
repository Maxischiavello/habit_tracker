package com.java.habit_tracker;

import com.java.habit_tracker.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.java.habit_tracker")
public class HabitTrackerApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private BorderPane root;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws IOException {
		springContext = SpringApplication.run(HabitTrackerApplication.class);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
		loader.setControllerFactory(springContext::getBean);
		root = loader.load();
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(root, 300, 500);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Habit Tracker");
		primaryStage.show();

		// Load the dashboard view by default
		MainController mainController = springContext.getBean(MainController.class);
		try {
			mainController.showDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		springContext.close();
	}
}
