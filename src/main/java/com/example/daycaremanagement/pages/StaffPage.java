package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;
import javafx.scene.control.Label;

public class StaffPage extends CrudOverlay {
  private static StaffPage instance;
  private Label title = new Label("Staff");

  private Label info=new Label("Information:");






  /**
   * Gets an instance of this class
   * @return the instance
   */
  public static StaffPage getInstance(){
    if (instance == null){
      instance = new StaffPage();
    }
    return instance;
  }


  private StaffPage() {
    super();
    title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-padding: 5px 20px");
    content.setTop(title);
  }

  @Override
  protected void sideButtonBar()
  {

    // Define actions specific to Guardians’ side buttons here



  }

  @Override
  protected void bottomButtonBar() {
    // Define actions specific to Guardians’ CRUD buttons here


  }
}


