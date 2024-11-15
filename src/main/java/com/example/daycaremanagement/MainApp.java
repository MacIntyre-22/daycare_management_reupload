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


public class MainApp extends Application {
    public static Stage primaryStage;
    // Test Page for login
    private LoginPage loginPage = new LoginPage();
    private MainTablesOverlay root = new MainTablesOverlay();
    private Scene mainPageScene = new Scene(root, 1024, 768);
    private Scene loginPageScene = new Scene(loginPage, 1024, 768);


    @Override
    public void start(Stage stage) throws IOException {
        // Initalization
        primaryStage = stage;
        primaryStage.setTitle("Daycare Management");

        // Set the Logic for login button press in LoginPageScene
        // Has to grab the button in main app for the ability to change the primary stage to test page

        loginPage.getLoginButton().setOnAction(e -> {
            if(loginPage.saveLoginInfo(loginPage.getUsernameInput(), loginPage.getUsernameInput(), loginPage.getHiddenPassInput())) {
                connectToDatabase();
            }
        });

        // Login Page Logic

        if (loginExists()) {
            connectToDatabase();
        } else {
            primaryStage.setScene(loginPageScene);
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
            DBConst.setDbName(fileConst[0]);
            DBConst.setDbUser(fileConst[1]);
            DBConst.setDbPass(fileConst[2]);
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
            // Returns false
            return false;
        }
    }


    /**
     * Checks connection and sets scene
     * Cleans up code by putting the logic for a connection in one function
     * */
    private void connectToDatabase() {

        // Set Consts Here
        if (setConst()) {
            // Check Connection here
            if (isConnected()) {
                primaryStage.setScene(mainPageScene);
            } else {
                loginPage.getMessageLabel().setText("Error Connecting to Database");
                primaryStage.setScene(loginPageScene);
            }
        } else {
            loginPage.getMessageLabel().setText("Error Setting Constants");
            primaryStage.setScene(loginPageScene);
        }
    }
}