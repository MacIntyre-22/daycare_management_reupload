package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.MainApp;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoPage extends BorderPane {


  /**
   * This Pages Displays the Information about the creators, and the program itself
   */
  public InfoPage() {
    this.getStylesheets().add(MainApp.class.getResource("Styles/InfoPage.css").toExternalForm());
    HBox root = new HBox();
    root.getStyleClass().add("content");

    VBox creators = new VBox();
    creators.setAlignment(Pos.CENTER);
    creators.setMaxWidth(200);

    Label creatorsTitle = new Label("Authors:");
    creatorsTitle.getStyleClass().add("title");
    creatorsTitle.setUnderline(true);

    creators.getChildren().add(creatorsTitle);


    VBox aboutProject = new VBox();
    aboutProject.setAlignment(Pos.CENTER);
    aboutProject.setMaxWidth(400);

    Label aboutProjectTitle = new Label("About Project:");
    aboutProjectTitle.getStyleClass().add("title");
    aboutProjectTitle.setUnderline(true);

    aboutProject.getChildren().add(aboutProjectTitle);


    addName(creators, "Ben MacIntyre ");
    addName(creators, "Kasey Johns ");
    addName(creators, "Gursimran Kaur");
    addName(creators, "Alex Zarankin ");

    addName(aboutProject, "This is a management system for daycare programs, that keeps track of the students that attend the daycare with their information, along with employees/staff with their position and wage, and the guardians/emergency contacts for the students.");


    root.getChildren().addAll(creators, aboutProject);
    this.setCenter(root);

  }

  /**
   *
   * @param container that is a VBox
   * @param name This is string
   * Makes the name into a label
   * This adds styling to the label
   * Then adds the label to the container
   */
  private void addName(VBox container, String name) {
    Label nameLabel = new Label(name);

    container.getChildren().addAll(nameLabel);
  }
}
