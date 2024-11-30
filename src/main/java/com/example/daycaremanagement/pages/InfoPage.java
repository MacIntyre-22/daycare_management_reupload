package com.example.daycaremanagement.pages;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class InfoPage extends BorderPane {

  public InfoPage() {
    VBox root = new VBox(10);
    root.setPadding(new Insets(15));

    // Load the logo and add it to the VBox
    Image logo = new Image((getClass().getResourceAsStream("/com/example/daycaremanagement/images/Day.jpeg")));
    ImageView logoView = new ImageView(logo);
    logoView.setFitWidth(200);

    root.getChildren().add(logoView);

    // Add title label
    Label title = new Label("Project: Daycare Management");
    title.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-font-family: 'Cooper'; -fx-padding: 5px 20px;");
    root.getChildren().add(title);

    // Add project description and member names
    addName(root, "This is a management system for daycare programs, that keeps track of the students that \n" +
            "attend the daycare with their information, along with employees/staff with their position and \n" +
            "wage, and the guardians/emergency contacts for the students.");

    addName(root, "Project Members:");
    addName(root, "  Ben MacIntyre");
    addName(root, "  Kasey Johns");
    addName(root, "  Gursimran Kaur");
    addName(root, "  Alex Zarankin");

    this.setCenter(root);
  }

  private void addName(VBox container, String name) {
    Label nameLabel = new Label(name);
    nameLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: italic; -fx-font-family: 'Baguet Script';");
    container.getChildren().add(nameLabel);
  }
}
