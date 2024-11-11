package com.example.daycaremanagement.scenes.pages;

import com.example.daycaremanagement.interfaces.SideBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class Staff extends BorderPane implements SideBar {
  private BorderPane content = new BorderPane();

  public Staff() {
    this.setLeft(sideBar());
    this.setBottom(bottomBar());
    content.setTop(new Label("Staff"));
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


