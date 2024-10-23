package com.example.daycaremanagement;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.scenes.LoginPagePane;
import com.example.daycaremanagement.scenes.LoginPageScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.example.daycaremanagement.AppConst.SCREEN_HEIGHT;
import static com.example.daycaremanagement.AppConst.SCREEN_WIDTH;

public class MainApp extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        // Initalization
        LoginPageScene loginPage = new LoginPageScene();
        primaryStage = stage;
        primaryStage.setTitle("Daycare Management");


        // Test Page for login
        BorderPane testRoot = new BorderPane();
        Scene testScene = new Scene(testRoot, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Set the Logic for login button press in LoginPageScene
        // Has to grab the button in main app for the ability to change the primary stage to test page
        LoginPagePane loginpagepane = (LoginPagePane)(loginPage.getRoot());
        loginpagepane.getLoginButton().setOnAction(e -> {
            if(loginpagepane.saveLoginInfo(loginpagepane.getDbNameInput(), loginpagepane.getUsernameInput(), loginpagepane.getHiddenPassInput())) {
                // Set Consts Here


                Database.getInstance();
            }
        });

        // Login Page Logic
        if (loginExists()) {
            // Test Connection
            primaryStage.setScene(testScene);
        } else {
            primaryStage.setScene(loginPage);
        }
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