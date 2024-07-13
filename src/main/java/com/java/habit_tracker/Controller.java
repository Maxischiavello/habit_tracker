package com.java.habit_tracker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Controller implements Initializable {

    @FXML
    public Label ejemplo;

    public void cambio() {
        ejemplo.setText("Cambio de texto desde el controller");
    }

    @Bean
    String title() {
        return "Titulo desde el controller";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cambio();
    }
}
