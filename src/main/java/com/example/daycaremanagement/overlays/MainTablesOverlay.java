package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.pages.GuardiansPage;
import com.example.daycaremanagement.pages.InfoPage;
import com.example.daycaremanagement.pages.StaffPage;
import com.example.daycaremanagement.pages.StudentsPage;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainTablesOverlay extends BorderPane {
  private StaffPage staffPageDisplay = StaffPage.getInstance();
  private GuardiansPage guardiansPageDisplay = GuardiansPage.getInstance();
  private StudentsPage studentDisplay = StudentsPage.getInstance();


  public MainTablesOverlay() {
    // Left Side Navigation (VBox)
    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-background-color:SkyBlue; -fx-padding: 20;");

    Button studentsButton = new Button("Students");
    Button guardiansButton = new Button("Guardians");
    Button staffButton = new Button("Staff ");
    Button infoButton = new Button("Info ");

    studentsButton.setMaxWidth(Double.MAX_VALUE);
    guardiansButton.setMaxWidth(Double.MAX_VALUE);
    staffButton.setMaxWidth(Double.MAX_VALUE);
    infoButton.setMaxWidth(Double.MAX_VALUE);

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


    vbox.getChildren().addAll(studentsButton, guardiansButton, staffButton, infoButton);
    this.setLeft(vbox);
    this.setCenter(studentDisplay);
  }




}
