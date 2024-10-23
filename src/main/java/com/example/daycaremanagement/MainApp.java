package com.example.daycaremanagement;

import com.example.daycaremanagement.scenes.LoginPageScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
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

    /**
     * Check if the "login/const.txt" file exists to decide if login is needed.
     *
     * @return true if the file exists, false otherwise
     */
    private boolean loginExists() {
        return false;
    }
}