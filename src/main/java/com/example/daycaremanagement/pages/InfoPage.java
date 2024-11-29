package com.example.daycaremanagement.pages;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;


public class InfoPage extends BorderPane {


  public InfoPage() {

    VBox root = new VBox(10);
    root.setPadding(new Insets(15));



    //Image logo = new Image(getClass().getResourceAsStream("images/Day.jpeg"));
   // ImageView logoView = new ImageView(logo);
   // logoView.setFitWidth(200); // Set width to fit

   // root.getChildren().add(logoView);

    //Image background=new Image(Objects.requireNonNull(getClass().getResourceAsStream("Day.jpeg")));
   //BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
   //root.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize)));




    Label title = new Label("Project:Daycare Management ");
    //title.setTranslateY(-300);
    //title.setFont(Font.font("Comic Sans MS", 20));
    //title.setTextFill(Color.RED);
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
