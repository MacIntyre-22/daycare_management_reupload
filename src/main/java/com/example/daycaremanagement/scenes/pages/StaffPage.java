package com.example.daycaremanagement.scenes.pages;

import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.tables.StaffTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;

public class StaffPage extends BasePage {
  private static StaffPage instance;
  private Label title = new Label("Staff");
  private StaffTable staff;

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
    loadTable();
  }

  @Override
  protected void sideButtonBar() {
    // Define actions specific to Guardians’ side buttons here
  }

  @Override
  protected void bottomButtonBar() {
    // Define actions specific to Guardians’ CRUD buttons here
  }

  @Override
  protected void loadTable() {
      this.tableView = new TableView();
      staff = new StaffTable();

      // Create Columns
      TableColumn<Staff, String> column1 = new TableColumn<>("First Name");
      column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

      TableColumn<Staff, String> column2 = new TableColumn<>("Last Name");
      column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

      TableColumn<Staff, String> column3 = new TableColumn<>("Wage");
      column3.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getWage())));

      TableColumn<Staff, String> column4 = new TableColumn<>("Room");
      column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRoom_id())));

      TableColumn<Staff, String> column5 = new TableColumn<>("Position");
      column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getPosition_id())));

      tableView.getColumns().addAll(column1, column2, column3, column4, column5);
      tableView.getItems().addAll(staff.getAllStaff());

      this.content.setCenter(tableView);
  }
}


