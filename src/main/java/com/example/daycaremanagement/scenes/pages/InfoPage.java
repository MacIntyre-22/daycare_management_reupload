package com.example.daycaremanagement.scenes.pages;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoPage extends BorderPane {


  public InfoPage() {
    VBox root = new VBox(10);
    root.setPadding(new javafx.geometry.Insets(15));

    addName(root, "Ben MacIntyre ");
    addName(root, "Kasey Johns ");
    addName(root, "Gursimran Kaur");
    addName(root, "Alex Zarankin ");
    addName(root, "This is a management system for daycare programs, that keeps track of the students that \n attend the daycare with their information, along with employees/staff with their position and \n wage, and the guardians/emergency contacts for the students. ");

    this.setCenter(root);

  }

  private void addName(VBox container, String name) {
    Label nameLabel = new Label(name);
    nameLabel.setStyle("-fx-font-weight: bold;");


    container.getChildren().addAll(nameLabel);
  }
}
