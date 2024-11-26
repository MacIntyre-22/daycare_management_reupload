package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class StaffPage extends CrudOverlay {
  private static StaffPage instance;
  private Label title = new Label("Staff");
  private StaffTable staffTable;
  private RoomTable roomTable;
  private PositionTable positionTable;

    // Pre Loaded Array of data
    ArrayList<City> cities = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Position> positions = new ArrayList<>();



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
      title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
      content.setTop(title);

      // Add rooms to array
      // Significantly increases the load speed and lagginess of the tableView
      try {
          roomTable = new RoomTable();
          rooms.addAll(roomTable.getAllRooms());
          positionTable = new PositionTable();
          positions.addAll(positionTable.getAllPositions());
      } catch (Exception e) {
          System.out.println("Error From: StaffPage.java, line 51. Couldn't get tables.");
      }

      loadTable();
      loadInfo();
  }

  @Override
  protected void sideButtonBar() {
    // Define actions specific to Guardians’ side buttons here
      // Define actions specific to Guardians’ side buttons here
      graph1.setOnAction(e->{
          loadTable();
      });

      // Position Piechart
      graph2.setOnAction(e->{
          PieChart chart = new PieChart();
          chart.setTitle("Staff Positions");

          // Grab Tables
          staffTable = new StaffTable();
          positionTable = new PositionTable();

          // Grab list of positions
          ArrayList<Position> posArray = positionTable.getAllPositions();
          ArrayList<PieChart.Data> data = new ArrayList<>();

          // Create staff list
          ArrayList<Staff> staff = staffTable.getAllStaff();

          // Count how many staff have pos id
          for(Position pos : posArray){
              double count = 0;

              // Check all staff
              for (Staff s : staff){
                  if (pos.getId() == s.getPosition_id()){
                      count++;
                  }
              }

              if(count > 0) {
                  // Add count and position to data
                  data.add(new PieChart.Data(pos.getName(), count));
              }
          }
          // Add data to piechart data
          ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
          chart.setLegendVisible(false);
          // Set piechart data to ObservableList
          chart.setData(chartData);

          // Set the graph
          content.setCenter(chart);
      });
  }

  @Override
  protected void bottomButtonBar() {
    // Define actions specific to Guardians’ CRUD buttons here
  }

  @Override
  protected void loadTable() {
      this.tableView = new TableView();

      try {
          staffTable = new StaffTable();
          positionTable = new PositionTable();
      } catch (Exception e) {
          System.out.println("Could not get tables.");
          e.printStackTrace();
      }


      // Create Columns
      TableColumn<Staff, String> column1 = new TableColumn<>("First Name");
      column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

      TableColumn<Staff, String> column2 = new TableColumn<>("Last Name");
      column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

      TableColumn<Staff, String> column3 = new TableColumn<>("Wage");
      column3.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getWage())));

      TableColumn<Staff, String> column4 = new TableColumn<>("Room");
      column4.setCellValueFactory(e -> new SimpleStringProperty(getRoomName(this.rooms,e.getValue().getRoom_id())));

      TableColumn<Staff, String> column5 = new TableColumn<>("Position");
      // TODO CREATE getPositionName function, I can grab this info right out of the table because there isn't that much but if it were to grow it would be slow
      column5.setCellValueFactory(e -> new SimpleStringProperty(getPositionName(positions, e.getValue().getPosition_id())));

      tableView.getColumns().addAll(column1, column2, column3, column4, column5);
      tableView.getItems().addAll(staffTable.getAllStaff());

      this.content.setCenter(tableView);
  }

    @Override
    protected void loadInfo() {
        VBox pageInfo = new VBox();
        Label testInfo = new Label("Test info: Will hold information on table");
        Label testInfo2 = new Label("Test info: Information like Table total, How many Students per room and etc.");
        pageInfo.getChildren().addAll(testInfo, testInfo2);
        this.content.setBottom(pageInfo);
    }
}


