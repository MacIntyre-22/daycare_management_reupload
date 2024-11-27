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



    Image logo = new Image("file:resources/images/Day.jpeg");
    ImageView logoView = new ImageView(logo);
    logoView.setFitWidth(200); // Set width to fit

    root.getChildren().add(logoView);





    Label title = new Label("Project:Daycare Management ");
    title.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-font-family: 'Cooper';-fx-padding: 5px 20px;");
    root.getChildren().add(title);



    addName(root, "This is a management system for daycare programs, that keeps track of the students that \n attend the daycare with their information, along with employees/staff with their position and \n wage, and the guardians/emergency contacts for the students. ");

    addName(root,"Project Members -:");

    addName(root, "  Ben MacIntyre ");
    addName(root, "  Kasey Johns ");
    addName(root, "  Gursimran Kaur");
    addName(root, "  Alex Zarankin ");



    this.setCenter(root);

  }


  private void addName(VBox container, String name) {
    Label nameLabel = new Label(name);
    nameLabel.setStyle("-fx-font-size: 19px;-fx-font-weight: Italic;-fx-font-family: 'Baguet Script';");

    container.getChildren().addAll(nameLabel);
  }



}
