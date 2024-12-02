package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pages.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.daycaremanagement.MainApp.loginPageScene;
import static com.example.daycaremanagement.MainApp.primaryStage;

public class MainTablesOverlay extends BorderPane {
  private StaffPage staffPageDisplay = StaffPage.getInstance();
  private GuardiansPage guardiansPageDisplay = GuardiansPage.getInstance();
  private StudentsPage studentDisplay = StudentsPage.getInstance();


  /**
   * This Overlay is the way the user can switch to each class type
   */
  public MainTablesOverlay() {
    Label title = new Label("Daycare Management");
    title.getStyleClass().add("title");

    // Top Navigation (HBox)
    HBox mainButtonBox = new HBox(10);
    mainButtonBox.setAlignment(Pos.CENTER_RIGHT);
    mainButtonBox.getStyleClass().add("ButtonBox");

    HBox addLoginButtonBox = new HBox(50);
    HBox layoutBox = new HBox();
    layoutBox.getStyleClass().add("Box");

    Button studentsButton = new Button("Students");
    Button guardiansButton = new Button("Guardians");
    Button staffButton = new Button("Staff");
    Button infoButton = new Button("Info");
    Button logoutButton = new Button("Logout");

    logoutButton.getStyleClass().add("logoutButton");

    // Action for Information button
    infoButton.setOnAction(e -> {
      InfoPage infoDisplay = new InfoPage();
      this.setCenter(infoDisplay);
    });

    // Placeholder action for Guardians button
    guardiansButton.setOnAction(e -> {
      this.setCenter(guardiansPageDisplay);
    });

    // Placeholder action for Staff button
    staffButton.setOnAction(e -> {
      this.setCenter(staffPageDisplay);
    });

    studentsButton.setOnAction(e -> {
      this.setCenter(studentDisplay);
    });

    logoutButton.setOnAction(e->{
      Database.clearInstance();
      File file = new File("login", "const.txt");
      if (file.delete()){
          try {
              file.createNewFile();
          } catch (IOException ex) {
              throw new RuntimeException(ex);
          }
      } else {
        System.out.println("File deletion error");
      }

      LoginPage loginPage = new LoginPage();
      loginPageScene.setRoot(loginPage);
      primaryStage.setScene(loginPageScene);
    });

    mainButtonBox.getChildren().addAll(studentsButton, guardiansButton, staffButton, infoButton);
    addLoginButtonBox.getChildren().addAll(mainButtonBox, logoutButton);
    addLoginButtonBox.setAlignment(Pos.TOP_RIGHT);
    layoutBox.getChildren().addAll(title, addLoginButtonBox);


    this.setTop(layoutBox);
    this.setCenter(studentDisplay);
  }






}
