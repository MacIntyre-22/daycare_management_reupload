package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.scenes.pages.Guardians;
import com.example.daycaremanagement.scenes.pages.Info;
import com.example.daycaremanagement.scenes.pages.Staff;
import com.example.daycaremanagement.scenes.pages.Students;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainPage extends BorderPane {
  private Staff staffDisplay = Staff.getInstance();
  private Guardians guardiansDisplay = Guardians.getInstance();
  private Students studentDisplay = Students.getInstance();


  public MainPage() {
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
      Info infoDisplay = new Info();
      this.setCenter(infoDisplay);
    });

    // Placeholder action for Guardians button
    guardiansButton.setOnAction(e -> {
      this.setCenter(guardiansDisplay);
    });

    // Placeholder action for Staff button
    staffButton.setOnAction(e -> {
      this.setCenter(staffDisplay);
    });

    studentsButton.setOnAction(e -> {
      this.setCenter(studentDisplay);
    });


    vbox.getChildren().addAll(studentsButton, guardiansButton, staffButton,infoButton);
    this.setLeft(vbox);
    this.setCenter(studentDisplay);
  }




}
