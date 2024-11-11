package com.example.daycaremanagement.scenes;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainPage extends BorderPane {
  public MainPage() {
    // Left Side Navigation (VBox)
    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-background-color:SkyBlue; -fx-padding: 20;");

    Button studentsButton = new Button("Students");
    Button guardiansButton = new Button("Guardians");
    Button staffButton = new Button("Staff ");
    Button infoButton = new Button("Information ");



    studentsButton.setMaxWidth(Double.MAX_VALUE);
    guardiansButton.setMaxWidth(Double.MAX_VALUE);
    staffButton.setMaxWidth(Double.MAX_VALUE);
    infoButton.setMaxWidth(Double.MAX_VALUE);

    infoButton.setOnAction(e -> {
      Info info = new Info();
      this.setCenter(info);
    });

    // Action for Information button
    infoButton.setOnAction(e -> {
      Info info = new Info();   // Assuming Info class exists and is configured to show content
      this.setCenter(info);
    });

    // Action for Students button
    studentsButton.setOnAction(e -> {
      Students studentDisplay = new Students();  // Assuming Staff class displays information for Students
      this.setCenter(studentDisplay);
    });

    // Placeholder action for Guardians button
    guardiansButton.setOnAction(e -> {
      Guardians guardiansDisplay = Guardians.getInstance();
      this.setCenter(guardiansDisplay);
    });

    // Placeholder action for Staff button
    staffButton.setOnAction(e -> {
      Staff staffDisplay = new Staff();  // Assuming Staff class displays information for Staff
      this.setCenter(staffDisplay);
    });


    vbox.getChildren().addAll(studentsButton, guardiansButton, staffButton,infoButton);
    this.setLeft(vbox);
  }




}
