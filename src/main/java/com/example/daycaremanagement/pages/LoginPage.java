package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.database.DBConst;
import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.overlays.MainTablesOverlay;
import com.example.daycaremanagement.database.Database;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.Scanner;

import static com.example.daycaremanagement.MainApp.primaryStage;

public class LoginPage extends BorderPane {

    // This Message Label is to explain to the user what to do
    // ex. enter inputs into fields and Errors that are happening
    private Label messageLabel = new Label("Please enter your username and password");

    // Main Page Scene
    private MainTablesOverlay root;
    private Scene mainPageScene;
    public boolean firstLogin = true;


    /**
     * This Pages Displays the Login fields,
     * then signs the user in, If their credentials are right
     */
    public LoginPage(){
    // Heading Text
        Label title = new Label("Login");

    //Input Fields
        TextField usernameInput = new TextField();
        TextField visiblePassInput = new TextField();
        PasswordField hiddenPassInput = new PasswordField();

    // Buttons
        Button showPass = new Button("Show Password");
        Button loginButton = new Button("Login");
        Button resetButton = new Button("Reset");
        Button testConnectionButton = new Button("Test Connection");
        Button exitButton = new Button("Exit");

    // Groupings
        VBox inputFields = new VBox(usernameInput, hiddenPassInput);
        HBox otherButtons = new HBox(testConnectionButton, showPass);
        HBox mainButtons = new HBox(loginButton, resetButton);
        VBox inputs = new VBox(title, inputFields, otherButtons, messageLabel, mainButtons, exitButton);

    // Title Styling
        //title.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
        title.getStyleClass().add("title");

    // Input Field styling
        usernameInput.setPromptText("Username");
        hiddenPassInput.setPromptText("Password");




    // Grouping Styling

        inputs.getStyleClass().add("inputs");
        DropShadow shadow = new DropShadow();

        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setRadius(100);


    // Button Actions
        showPass.setOnAction(e -> {
            String passwordText;
            if(showPass.getText().equals("Show Password")) {

                // Saves pass and clears the hidden input field
                passwordText = hiddenPassInput.getText();
                hiddenPassInput.clear();

                if (!passwordText.isEmpty()){

                    // Replacing the hidden Input with the visible one
                    inputFields.getChildren().clear();
                    inputFields.getChildren().addAll(usernameInput,visiblePassInput);

                    // Displays the user's input into the visible field
                    visiblePassInput.setText(passwordText);

                    showPass.setText("Hide Password");
                }

            } else {
                // Saves pass and clears the visible input field
                passwordText = visiblePassInput.getText();
                visiblePassInput.clear();

                // Replacing the visible Input with the hidden one
                inputFields.getChildren().clear();
                inputFields.getChildren().addAll(usernameInput, hiddenPassInput);

                // hides the user's input by putting it back into the hidden field
                hiddenPassInput.setText(passwordText);


                showPass.setText("Show Password");
            }
        });


        // This only will Test the connection to the database
        testConnectionButton.setOnAction(e -> {
            // This is to make the password still work even if its visible
            if(!visiblePassInput.getText().isEmpty()){
                hiddenPassInput.setText(visiblePassInput.getText());
            }

            // This saves the user input
            if(saveLoginInfo(usernameInput, usernameInput, hiddenPassInput)) {
                if(setConst()){
                    // And then it test to see if there is a good connection
                    if(Database.checkConnection()){
                        messageLabel.setText("Connection is Good!");
                    // If there isn't a good connection then it gives a slight idea what it is
                    } else {
                        messageLabel.setText("Username or Passwords is incorrect");
                    }
                }

            }

            // This is test the Db Connection
            if (messageLabel.getText().equals("Error Connecting to Database") || messageLabel.getText().equals("Username or Passwords is incorrect")) {
                shadow.setColor(Color.RED);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.RED);
            } else if (messageLabel.getText().equals("All fields must be filled out.")) {
                shadow.setColor(Color.YELLOW);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.YELLOW);
            } else if (messageLabel.getText().equals("Connection is Good!")) {
                shadow.setColor(Color.LIMEGREEN);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.LIMEGREEN);
            } else {
                shadow.setColor(Color.BLACK);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.LIGHTBLUE);
            }
            try {
                Database.checkConnection();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        resetButton.setOnAction(e-> {
            // Clears the username & all otherButtons inputs
            usernameInput.clear();
            visiblePassInput.clear();
            hiddenPassInput.clear();

            // Resets the otherButtons field back to hidden
            inputFields.getChildren().clear();
            inputFields.getChildren().addAll(usernameInput, hiddenPassInput);

            shadow.setColor(Color.BLACK);
            inputs.setEffect(shadow);

            messageLabel.setText("Please enter your username and password");
            messageLabel.setTextFill(Color.LIGHTBLUE);
            showPass.setText("Show Password");
        });

        loginButton.setOnAction(e -> {
            if(!visiblePassInput.getText().isEmpty()){
                hiddenPassInput.setText(visiblePassInput.getText());
            }
            if(saveLoginInfo(usernameInput, usernameInput, hiddenPassInput)) {
                connectToDatabase();
            }
        });

        // Closes the program
        exitButton.setOnAction(e -> primaryStage.close());


    // Added to my pane...
        this.setCenter(inputs);
    }


    /**
     * Save login information to the file "login/const.txt"
     * @return true or false depending on if it created the login information
     */
    public boolean saveLoginInfo(TextField database, TextField username, TextField pass) {
        // Check if any of the text fields are empty before proceeding
        if (username.getText().isEmpty() || pass.getText().isEmpty()) {
            messageLabel.setText("All fields must be filled out.");
            return false;
        }else {

            // Ensure the login folder exists
            File loginFolder = new File("login");
            if (!loginFolder.exists()) {
                loginFolder.mkdirs();
            }

            // Write the login information to a const.txt file
            try (PrintWriter writer = new PrintWriter(new File(loginFolder, "const.txt"))) {
                String loginValues = database.getText()+"java" + "\n" +
                        username.getText() + "\n" +
                        pass.getText();
                writer.print(loginValues);
            } catch (FileNotFoundException ex) {
                messageLabel.setText("Unable to create login file");
                return false;
            }
            messageLabel.setText("Login Saved");
            return true;
        }
    }



    /**
     * Sets the DbConst File values for a db connection
     *
     * @return true if constants were set, false otherwise
     * */
    public boolean setConst() {
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
     * Uses the setConst() Function
     * Then Checks the connection to the Database
     * And If all is true then it sets the scene to the mainPageScene
     * */
    public void connectToDatabase() {

        // Set Consts Here
        if (setConst()) {
            // Check Connection here
            if (Database.checkConnection()) {
                root = new MainTablesOverlay();
                mainPageScene = new Scene(root, 1024, 768);
                primaryStage.setScene(mainPageScene);
            } else if (!firstLogin) {
                messageLabel.setText("Error Connecting to Database");
            } else {
                firstLogin = false;
            }
        } else {
            messageLabel.setText("Error Setting Constants");
        }
    }
}
