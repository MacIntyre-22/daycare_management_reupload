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
      InfoPage info = new InfoPage();
      this.setCenter(info);
    });


    vbox.getChildren().addAll(studentsButton, guardiansButton, staffButton,infoButton);
    this.setLeft(vbox);
  }




}
