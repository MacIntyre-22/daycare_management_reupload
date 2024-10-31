package com.example.daycaremanagement;

import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.database.DbConst;
import com.example.daycaremanagement.scenes.LoginPagePane;
import com.example.daycaremanagement.scenes.LoginPageScene;
import com.example.daycaremanagement.scenes.Test;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.example.daycaremanagement.AppConst.SCREEN_HEIGHT;
import static com.example.daycaremanagement.AppConst.SCREEN_WIDTH;

public class MainApp extends Application {
    public static Stage primaryStage;
    private LoginPageScene loginPage = new LoginPageScene();
    // Test Page for login
    private BorderPane testRoot = new BorderPane();
    private Scene testScene = new Scene(new Test(), SCREEN_WIDTH, SCREEN_HEIGHT);


    @Override
    public void start(Stage stage) throws IOException {
        // Initalization
        primaryStage = stage;
        primaryStage.setTitle("Daycare Management");

        // Set the Logic for login button press in LoginPageScene
        // Has to grab the button in main app for the ability to change the primary stage to test page
        LoginPagePane loginpagepane = (LoginPagePane)(loginPage.getRoot());
        loginpagepane.getLoginButton().setOnAction(e -> {
            if(loginpagepane.saveLoginInfo(loginpagepane.getDbNameInput(), loginpagepane.getUsernameInput(), loginpagepane.getHiddenPassInput())) {
                connectToDatabase();
            }
        });

        // Login Page Logic

        if (loginExists()) {
            connectToDatabase();
        } else {
            primaryStage.setScene(loginPage);
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

    /**
     * Sets the DbConst File values for a db connection
     *
     * @return true if constants were set, false otherwise
     * */
    private boolean setConst() {
        // Read const.txt file for creds
        try (Scanner scanner = new Scanner(new File("login/const.txt"))) {
            String[] fileConst = new String[3];
            // Add creds to array
            while (scanner.hasNextLine()) {
                fileConst[0] = scanner.nextLine();
                fileConst[1] = scanner.nextLine();
                fileConst[2] = scanner.nextLine();
            }

            // Set DbConst values
            DbConst.setDbName(fileConst[0]);
            DbConst.setDbUser(fileConst[1]);
            DbConst.setDbPass(fileConst[2]);
            return true;

        } catch (FileNotFoundException e) {
            // Catch exception
            return false;
        }
    }


    /**
     * Checks database connection
     *
     * @return true if connection worked, false otherwise
     * */
    private boolean isConnected() {
        try {
            // Might throw exception
            Database db = Database.getInstance();
            // Will return true if it does not throw exception
            return true;

        } catch (Exception e1) {
            // If the database connection fails, display an error and stay on the login screen
            System.out.println(e1);
            // Returns false
            return false;
        }
    }


    /**
     * Checks connection and sets scene
     * Cleans up code by putting the logic for a connection in one function
     * */
    private void connectToDatabase() {
        LoginPagePane loginpagepane = (LoginPagePane)(loginPage.getRoot());
        // Set Consts Here
        if (setConst()) {
            // Check Connection here
            if (isConnected()) {
                primaryStage.setScene(testScene);
            } else {
                loginpagepane.getMessageLabel().setText("Error Connecting to Database");
                primaryStage.setScene(loginPage);
            }
        } else {
            loginpagepane.getMessageLabel().setText("Error Setting Constants");
            primaryStage.setScene(loginPage);
        }
    }
}