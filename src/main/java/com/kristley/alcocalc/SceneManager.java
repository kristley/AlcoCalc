package com.kristley.alcocalc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private final Stage stage;
    private static SceneManager instance;
    private final double width;
    private final double height;

    public SceneManager(Stage stage, Scene scene) {
        this.stage = stage;
        width = scene.getWidth();
        height = scene.getHeight();

        if(instance != null)
            return;
        instance = this;
    }

    public static void loadSceneFromResource(String resource){
        FXMLLoader loader = new FXMLLoader(instance.getClass().getResource(resource));
        Scene scene;
        try {
            scene = new Scene(loader.load(), instance.width, instance.height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        instance.stage.setScene(scene);
    }
}
