package com.java.habit_tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HabitTrackerApplication extends Application {

	public static void main(String[] args) {
		System.out.println("AHI VA LA CONCHA DE TU MADRE");
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		var context = SpringApplication.run(HabitTrackerApplication.class);
		var fxml = new FXMLLoader(getClass().getResource("/Main.fxml"));

		String title = context.getBean("title", String.class);
		var scene = new Scene(fxml.load());

		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
}
