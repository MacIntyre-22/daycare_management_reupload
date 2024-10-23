package com.example.daycaremanagement;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class LoginPagePane extends BorderPane {
    private String usernameText;
    private String passwordText;
    private TextField visiblePasswordInput;
    private PasswordField hiddenPasswordInput;

    public LoginPagePane(){

        TextField usernameInput = new TextField();
        visiblePasswordInput = new TextField();
        hiddenPasswordInput = new PasswordField();

        usernameInput.setPromptText("Username");
        usernameInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        usernameInput.setMaxWidth(200);

        hiddenPasswordInput.setPromptText("Password");
        hiddenPasswordInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        hiddenPasswordInput.setMaxWidth(200);



        Button showPass = new Button("Show Password");


        HBox password = new HBox(hiddenPasswordInput, showPass);
        password.setSpacing(10);


        showPass.setOnAction(e -> {
            if(Objects.equals(showPass.getText(), "Show Password")) {
                passwordText = hiddenPasswordInput.getText();
                if (!passwordText.isEmpty()){
                    hiddenPasswordInput.clear();
                    password.getChildren().clear();
                    password.getChildren().addAll(visiblePasswordInput, showPass);

                    visiblePasswordInput.setText(passwordText);
                    showPass.setText("Hide Password");
                }


            } else {
                passwordText = visiblePasswordInput.getText();
                visiblePasswordInput.clear();

                password.getChildren().clear();
                password.getChildren().addAll(hiddenPasswordInput, showPass);

                hiddenPasswordInput.setText(passwordText);


                showPass.setText("Show Password");
            }
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            usernameText = usernameInput.getText();
            passwordText = hiddenPasswordInput.getText();


            try {
                if(!usernameText.isEmpty() || passwordText.isEmpty()){
                    System.out.println(usernameText);
                    System.out.println(passwordText);
                }

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e-> {
            usernameInput.clear();
            visiblePasswordInput.clear();
            hiddenPasswordInput.clear();
            password.getChildren().clear();
            password.getChildren().addAll(hiddenPasswordInput, showPass);
            showPass.setText("Show Password");
        });




        HBox buttons = new HBox(submitButton, resetButton);

        VBox inputs = new VBox(usernameInput, password, buttons);
        this.setCenter(inputs);
    }

}
