package com.kristley.alcocalc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        new NightsManager("src/main/resources/com/kristley/alcocalc/nights.json");

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("alcoCalc-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
        stage.setTitle("AlcoCalc");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        new SceneManager(stage, scene);
    }

    public static void main(String[] args) {
        launch();
    }
}