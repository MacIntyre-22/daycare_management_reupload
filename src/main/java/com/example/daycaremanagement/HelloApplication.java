package com.example.daycaremanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(new LoginPageScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}