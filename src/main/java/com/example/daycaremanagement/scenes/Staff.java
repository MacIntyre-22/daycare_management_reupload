package com.example.daycaremanagement.scenes;

import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
public class Staff extends BorderPane {
  public Staff() {
    // Create the left sidebar
    VBox leftSidebar = new VBox();
    leftSidebar.setPadding(new Insets(10));
    leftSidebar.setSpacing(8);

    // Add components to the sidebar
    Label titleLabel = new Label("Staff Management");
    Button addButton = new Button("Add Staff");
    Button editButton = new Button("Edit Staff");
    Button deleteButton = new Button("Delete Staff");

    // Add components to the VBox
    leftSidebar.getChildren().addAll(titleLabel, addButton, editButton, deleteButton);

    // Set the left sidebar in the BorderPane
    this.setLeft(leftSidebar);
  }
}


