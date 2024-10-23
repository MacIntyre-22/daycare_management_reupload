package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.MainApp;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LoginPagePane extends BorderPane {

    // Data Fields
    // Added variables here so they're visible inside the button actions
    private String usernameText;
    private String passwordText;
    private String dbText;

    // Input Fields
    private TextField usernameInput = new TextField();
    private TextField dbNameInput = new TextField();
    private Label messageLabel = new Label("Please enter your username and password");
    private TextField visiblePassInput = new TextField();
    private PasswordField hiddenPassInput = new PasswordField();

    // Login button
    private Button loginButton = new Button("Login");

    public LoginPagePane(){

    // Buttons
        Button showPass = new Button("Show Password");
        Button resetButton = new Button("Reset");
        Button testConnectionButton = new Button("Test Connection");
        Button exitButton = new Button("Exit");

    // Groupings
        HBox password = new HBox(hiddenPassInput, showPass);
        HBox buttons = new HBox(loginButton, resetButton);
        VBox inputs = new VBox(dbNameInput, usernameInput, password,testConnectionButton, buttons, messageLabel, exitButton);

    // Input Field styling
        usernameInput.setPromptText("Username");
        usernameInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        usernameInput.setMaxWidth(200);

        hiddenPassInput.setPromptText("Password");
        hiddenPassInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        hiddenPassInput.setMaxWidth(200);

        dbNameInput.setPromptText("Database Name");
        dbNameInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        dbNameInput.setMaxWidth(200);

    // Grouping Styling
        password.setSpacing(10);

        inputs.setSpacing(10);

    // Button Actions
        // Login button action is in MainApp.java
        showPass.setOnAction(e -> {
            if(showPass.getText().equals("Show Password")) {

                // Saves pass and clears the hidden input field
                passwordText = hiddenPassInput.getText();
                hiddenPassInput.clear();

                if (!passwordText.isEmpty()){

                    // Replacing the hidden Input with the visible one
                    password.getChildren().clear();
                    password.getChildren().addAll(visiblePassInput, showPass);

                    // Displays the user's input into the visible field
                    visiblePassInput.setText(passwordText);

                    showPass.setText("Hide Password");
                }

            } else {
                // Saves pass and clears the visible input field
                passwordText = visiblePassInput.getText();
                visiblePassInput.clear();

                // Replacing the visible Input with the hidden one
                password.getChildren().clear();
                password.getChildren().addAll(hiddenPassInput, showPass);

                // hides the user's input by putting it back into the hidden field
                hiddenPassInput.setText(passwordText);


                showPass.setText("Show Password");
            }
        });


        testConnectionButton.setOnAction(e -> {
            try {
                
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        resetButton.setOnAction(e-> {

            // Clears the username & all password inputs
            dbNameInput.clear();
            usernameInput.clear();
            visiblePassInput.clear();
            hiddenPassInput.clear();

            // Resets the password field back to hidden
            password.getChildren().clear();
            password.getChildren().addAll(hiddenPassInput, showPass);


            showPass.setText("Show Password");
        });

        // Closes the program
        exitButton.setOnAction(e -> MainApp.primaryStage.close());

    // Added to my pane...
        this.setCenter(inputs);
    }


    /**
     * Save login information to the file "login/const.txt"
     */
    public boolean saveLoginInfo(TextField database, TextField username, TextField pass) {
        // Check if any of the text fields are empty before proceeding
        if (database.getText().isEmpty() || username.getText().isEmpty() || pass.getText().isEmpty()) {
            this.messageLabel.setText("All fields must be filled out.");
            return false;
        }else {

            // Ensure the login folder exists
            File loginFolder = new File("login");
            if (!loginFolder.exists()) {
                loginFolder.mkdirs();
            }

            // Write the login information to a const.txt file
            try (PrintWriter writer = new PrintWriter(new File(loginFolder, "const.txt"))) {
                String loginValues = database.getText() + "\n" +
                        username.getText() + "\n" +
                        pass.getText();
                writer.print(loginValues);
            } catch (FileNotFoundException ex) {
                this.messageLabel.setText("Unable to create login file");
                return false;
            }
            this.messageLabel.setText("Login Saved");
            return true;
        }
    }

    // Setters and getters

    public TextField getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(TextField usernameInput) {
        this.usernameInput = usernameInput;
    }

    public TextField getDbNameInput() {
        return dbNameInput;
    }

    public void setDbNameInput(TextField dbNameInput) {
        this.dbNameInput = dbNameInput;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public PasswordField getHiddenPassInput() {
        return hiddenPassInput;
    }

    public void setHiddenPassInput(PasswordField hiddenPassInput) {
        this.hiddenPassInput = hiddenPassInput;
    }
}
