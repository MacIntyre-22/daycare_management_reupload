package com.example.daycaremanagement;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.DBConst;
import com.example.daycaremanagement.overlays.MainTablesOverlay;
import com.example.daycaremanagement.pages.LoginPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.example.daycaremanagement.database.DBConst.*;


public class MainApp extends Application {
    public static Stage primaryStage;
    // Test Page for login
    private LoginPage loginPage = new LoginPage();
    private Scene loginPageScene = new Scene(loginPage, 1024, 768);


    @Override
    public void start(Stage stage) throws IOException {
        // Initalization
        primaryStage = stage;
        primaryStage.setTitle("Daycare Management");

        // Set the Logic for login button press in LoginPageScene
        // Has to grab the button in main app for the ability to change the primary stage to test page

        // Login Page Logic
        primaryStage.setScene(loginPageScene);
        if (loginExists()) {
            loginPage.connectToDatabase();
        }


        primaryStage.show();
    }

    public static void main(String[] args) {launch();}

    /**
     * Check if the "login/const.txt" file exists to decide if login is needed.
     *
     * @return true if the file exists, false otherwise
     */
    private boolean loginExists() {
        File loginFolder = new File("login");

        // Checks ig login directory exists
        if (loginFolder.exists() && loginFolder.isDirectory()) {

            // Checks if const.txt file exists
            File loginFile = new File(loginFolder, "const.txt");
            if (loginFile.exists() && loginFile.isFile()) {
                return true;
            }
        }
        return false;
    }
}