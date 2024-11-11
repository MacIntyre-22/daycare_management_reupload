package com.example.daycaremanagement.scenes.pages;

import com.example.daycaremanagement.interfaces.SideBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class Staff extends BasePage {
  private static Staff instance;
  private Label title = new Label("Staff");

  /**
   * Gets an instance of this class
   * @return the instance
   */
  public static Staff getInstance(){
    if (instance == null){
      instance = new Staff();
    }
    return instance;
  }


  private Staff() {
    super();
    title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-padding: 5px 20px");
    content.setTop(title);
  }

  @Override
  protected void sideButtonBar() {
    // Define actions specific to Guardians’ side buttons here
  }

  @Override
  protected void bottomButtonBar() {
    // Define actions specific to Guardians’ CRUD buttons here
  }
}


