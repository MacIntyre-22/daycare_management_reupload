package com.example.daycaremanagement;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginPagePane extends BorderPane {

    // Added variables here so they're visible inside the button actions
    private String usernameText;
    private String passwordText;
    private TextField visiblePassInput;
    private PasswordField hiddenPassInput;

    public LoginPagePane(){

    // Input Fields
        TextField usernameInput = new TextField();
        visiblePassInput = new TextField();
        hiddenPassInput = new PasswordField();

    // Buttons
        Button showPass = new Button("Show Password");
        Button loginButton = new Button("Login");
        Button resetButton = new Button("Reset");
        Button testConnectionButton = new Button("Test Connection");
        Button exitButton = new Button("Exit");

    // Groupings
        HBox password = new HBox(hiddenPassInput, showPass);
        HBox buttons = new HBox(loginButton, resetButton);
        VBox inputs = new VBox(usernameInput, password,testConnectionButton, buttons, exitButton);

    // Input Field styling
        usernameInput.setPromptText("Username");
        usernameInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        usernameInput.setMaxWidth(200);

        hiddenPassInput.setPromptText("Password");
        hiddenPassInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        hiddenPassInput.setMaxWidth(200);

    // Grouping Styling
        password.setSpacing(10);

        inputs.setSpacing(10);

    // Button Actions


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



        loginButton.setOnAction(e -> {

        // Saves the Username & Password
            usernameText = usernameInput.getText();
            passwordText = hiddenPassInput.getText();

        // Try's it use the username & pass
            try {
                if(!usernameText.isEmpty() || !passwordText.isEmpty()){
                    System.out.println(usernameText);
                    System.out.println(passwordText);
                }

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        resetButton.setOnAction(e-> {

            // Clears the username & all password inputs
            usernameInput.clear();
            visiblePassInput.clear();
            hiddenPassInput.clear();

            // Resets the password field back to hidden
            password.getChildren().clear();
            password.getChildren().addAll(hiddenPassInput, showPass);


            showPass.setText("Show Password");
        });

        // Closes the program
        exitButton.setOnAction(e -> HelloApplication.primaryStage.close());

    // Added to my pane...
        this.setCenter(inputs);
    }
}
