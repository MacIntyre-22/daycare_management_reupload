package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.interfaces.SideBar;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class Staff extends BorderPane implements SideBar {
  private static Staff instance;
  private BorderPane content = new BorderPane();

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
    this.setLeft(sideBar());
    this.setBottom(bottomBar());
    content.setTop(new Label("Students"));
    this.setCenter(content);
  }


  @Override
  public VBox sideBar() {
    return SideBar.super.sideBar();
  }

  @Override
  public HBox bottomBar() {
    return SideBar.super.bottomBar();
  }

  @Override
  public void sideButtonBar() {

  }

  @Override
  public void bottomButtonBar() {

  }
}


