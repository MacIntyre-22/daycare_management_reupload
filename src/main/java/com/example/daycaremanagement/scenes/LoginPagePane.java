package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.MainApp;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

public class LoginPagePane extends BorderPane {

    // Data Fields
    // Added variables here so they're visible inside the button actions
    private String usernameText;
    private String passwordText;
    private String dbText;

    // Input Fields
    private TextField usernameInput = new TextField();
    private Label messageLabel = new Label("Please enter your username and password");
    private TextField visiblePassInput = new TextField();
    private PasswordField hiddenPassInput = new PasswordField();

    // Login button
    private Button loginButton = new Button("Login");

    public LoginPagePane(){

    // Heading Text
        Label title = new Label("(Daycare Name) Login Page");

    // Buttons
        Button showPass = new Button("Show Password");
        Button resetButton = new Button("Reset");
        Button testConnectionButton = new Button("Test Connection");
        Button exitButton = new Button("Exit");

    // Groupings
        VBox inputFields = new VBox(usernameInput, hiddenPassInput);
        HBox otherButtons = new HBox(testConnectionButton, showPass);
        HBox mainButtons = new HBox(loginButton, resetButton);
        VBox inputs = new VBox(title, inputFields, otherButtons, messageLabel, mainButtons, exitButton);

    // Title Styling
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 30));

    // Input Field styling
        usernameInput.setPromptText("Username");
        usernameInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        usernameInput.setMaxWidth(200);

        hiddenPassInput.setPromptText("Password");
        hiddenPassInput.setStyle("-fx-prompt-text-fill: rgb(100, 100, 100)");
        hiddenPassInput.setMaxWidth(200);

        visiblePassInput.setMaxWidth(200);


    // Grouping Styling
        title.setTextFill(Color.LIGHTBLUE);
        messageLabel.setTextFill(Color.LIGHTBLUE);


        otherButtons.setSpacing(10);
        otherButtons.setAlignment(Pos.CENTER);

        inputs.setSpacing(10);
        inputs.setAlignment(Pos.CENTER);
        inputs.setMaxSize(475, 500);
        inputs.setStyle("-fx-background-radius: 50;"+
                        "-fx-border-radius: 50;" +
                        "-fx-border-width: 5;" +
                        "-fx-background-color: #001C55;");
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setRadius(100);
        shadow.setColor(Color.BLACK);
        inputs.setEffect(shadow);

        mainButtons.setAlignment(Pos.CENTER);
        mainButtons.setSpacing(10);
        loginButton.setMinSize(75, 25);
        loginButton.setStyle("-fx-background-radius: 50;" +
                            "-fx-background-color: #0E6BA8;" +
                            "-fx-color: white;");
        loginButton.setOnMouseEntered(e->{
            loginButton.setStyle("-fx-background-radius: 50;" +
                    "-fx-background-color: #A6E1FA;" +
                    "-fx-color: white;");
        });
        loginButton.setOnMouseExited(e->{
            loginButton.setStyle("-fx-background-radius: 50;" +
                    "-fx-background-color: #0E6BA8;" +
                    "-fx-color: white;");
        });

        resetButton.setMinSize(75, 25);
        resetButton.setStyle("-fx-background-radius: 50;" +
                            "-fx-background-color: #0E6BA8;" +
                            "-fx-color: white;");
        resetButton.setOnMouseEntered(e->{
            resetButton.setStyle("-fx-background-radius: 50;" +
                    "-fx-background-color: #A6E1FA;" +
                    "-fx-color: white;");
        });
        resetButton.setOnMouseExited(e->{
            resetButton.setStyle("-fx-background-radius: 50;" +
                    "-fx-background-color: #0E6BA8;" +
                    "-fx-color: white;");
        });


        inputFields.setAlignment(Pos.CENTER);
        inputFields.setSpacing(10);

    // Styling the pane
        this.setStyle("-fx-background-color: #0E6BA8");

    // Button Actions
        // Login button action is in MainApp.java
        showPass.setOnAction(e -> {
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


        testConnectionButton.setOnAction(e -> {
            // This is test code
            if (messageLabel.getText().equals("Error Connecting to Database")){
                shadow.setColor(Color.RED);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.RED);
            } else if (messageLabel.getText().equals("All fields must be filled out.")) {
                shadow.setColor(Color.YELLOW);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.YELLOW);
            } else {
                shadow.setColor(Color.BLACK);
                inputs.setEffect(shadow);
                messageLabel.setTextFill(Color.LIGHTBLUE);
            }
            try {
                
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
        if (username.getText().isEmpty() || pass.getText().isEmpty()) {
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
                String loginValues = database.getText()+"java" + "\n" +
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
