package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.database.Database;
import com.example.daycaremanagement.pages.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.daycaremanagement.MainApp.primaryStage;

public class MainTablesOverlay extends BorderPane {
  private StaffPage staffPageDisplay = StaffPage.getInstance();
  private GuardiansPage guardiansPageDisplay = GuardiansPage.getInstance();
  private StudentsPage studentDisplay = StudentsPage.getInstance();
  private OtherTablesPage otherTablesDisplay = OtherTablesPage.getInstance();


  /**
   * This Overlay is the way the user can switch to each class type
   */
  public MainTablesOverlay() {
    // Left Side Navigation (VBox)
    VBox vbox = new VBox(10);
    VBox logoutBox = new VBox(570);
    logoutBox.setStyle("-fx-background-color:SkyBlue; -fx-padding: 20;");

    Button studentsButton = new Button("Students");
    Button guardiansButton = new Button("Guardians");
    Button staffButton = new Button("Staff ");
    Button otherTablesButton = new Button("Tables");
    Button infoButton = new Button("Info ");
    Button logoutButton = new Button("Logout");

    studentsButton.setMaxWidth(Double.MAX_VALUE);
    guardiansButton.setMaxWidth(Double.MAX_VALUE);
    staffButton.setMaxWidth(Double.MAX_VALUE);
    otherTablesButton.setMaxWidth(Double.MAX_VALUE);
    infoButton.setMaxWidth(Double.MAX_VALUE);
    logoutButton.setMaxWidth(Double.MAX_VALUE);

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

    otherTablesButton.setOnAction(e -> {
      this.setCenter(otherTablesDisplay);
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
      primaryStage.setScene(new Scene(loginPage, 1024, 768));
    });

    vbox.getChildren().addAll(studentsButton, guardiansButton, staffButton, infoButton);
    logoutBox.getChildren().addAll(vbox, logoutButton);
    logoutBox.setAlignment(Pos.BOTTOM_LEFT);
    this.setLeft(logoutBox);
    this.setCenter(studentDisplay);
  }






}
