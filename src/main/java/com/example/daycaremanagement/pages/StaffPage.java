package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.pojo.display.DisplayStaff;
import com.example.daycaremanagement.tables.StaffTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

import java.sql.SQLException;

public class StaffPage extends CrudOverlay {
  private static StaffPage instance;
  private Label title = new Label("Staff");
  private StaffTable staff;
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

    /**
     * This Pages Displays the Students Page.
     * With the Table data
     * and with the CRUD overlay
     * and a some low level table info at the bottom of the table
     */
  private StaffPage() {
      super();
      title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
      content.setTop(title);

      // Add rooms to array
      // Significantly increases the load speed and lagginess of the tableView
      try {
          roomTable = RoomTable.getInstance();
          rooms.addAll(roomTable.getAllRooms());
          positionTable = PositionTable.getInstance();
          positions.addAll(positionTable.getAllPositions());
      } catch (Exception e) {
          System.out.println("Error From: StaffPage.java, line 51. Couldn't get tables.");
      }

      // Set Icons for buttons we use
      graph1.setGraphic(createBtn(setIcon(ICONS[0], 30), "Table"));
      graph2.setGraphic(createBtn(setIcon(ICONS[1], 30), "Pos."));
      graph3.setGraphic(createBtn(setIcon(ICONS[2], 30), "Wage"));

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
          try {
              staff = StaffTable.getInstance();
              positionTable = PositionTable.getInstance();
          } catch (SQLException ex) {
              throw new RuntimeException(ex);
          }

          // Grab list of positions
          ArrayList<Position> posArray = positionTable.getAllPositions();
          ArrayList<PieChart.Data> data = new ArrayList<>();

          // Create staff list
          ArrayList<Staff> staffList = staff.getAllStaff();

          // Count how many staff have pos id
          for(Position pos : posArray){
              double count = 0;

              // Check all staff
              for (Staff s : staffList){
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

      // Student Age per Room bar Chart
      graph3.setOnAction(e->{
          try {
              staff = StaffTable.getInstance();
          } catch (SQLException ex) {
              throw new RuntimeException(ex);
          }

          //Defining the x axis
          CategoryAxis xAxis = new CategoryAxis();
          xAxis.setLabel("Positions");

          //Defining the y axis
          NumberAxis yAxis = new NumberAxis(0, 16, 2);
          yAxis.setLabel("Staff");

          //Creating the Bar chart
          BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
          barChart.setTitle("Wage by Position");

          // Series
          ArrayList<XYChart.Series<String, Number>> seriesArray = new ArrayList<>();
          // Each age group is a series
          String series0Name = "<17.00";
          XYChart.Series<String, Number> wage0 = new XYChart.Series<>();
          wage0.setName(series0Name);
          seriesArray.add(wage0);
          // Set Data for all series for each room under here

          String series1Name = "17.00+";
          XYChart.Series<String, Number> wage1 = new XYChart.Series<>();
          wage1.setName(series1Name);
          seriesArray.add(wage1);

          String series2Name = "18.00+";
          XYChart.Series<String, Number> wage2 = new XYChart.Series<>();
          wage2.setName(series2Name);
          seriesArray.add(wage2);


          // Add data using setDataByRoom()
          setDataByPosition(seriesArray, 1);
          setDataByPosition(seriesArray, 2);
          setDataByPosition(seriesArray, 3);
          setDataByPosition(seriesArray, 4);
          setDataByPosition(seriesArray, 5);
          setDataByPosition(seriesArray, 6);
          setDataByPosition(seriesArray, 7);


          barChart.getData().addAll(wage0, wage1, wage2);
          barChart.setLegendSide(Side.LEFT);
          content.setCenter(barChart);

      });

      // Remove buttons here
      NavButtons.getChildren().remove(graph4);
  }

  @Override
  protected void bottomButtonBar() {

      delete.setOnAction(e->{
          if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
              DisplayStaff deleteStaff = (DisplayStaff) this.tableView.getSelectionModel().getSelectedItems().get(0);
              staff.deleteStaff(staff.getStaff(deleteStaff.getId()));
              loadTable();
          }
      });


      create.setOnAction(e->{
          Label firstName = new Label("First Name");
          TextField fNameInput = new TextField();
          VBox fNameGroup = new VBox(firstName, fNameInput);

          Label lastName = new Label("Last Name");
          TextField lNameInput = new TextField();
          VBox lNameGroup = new VBox(lastName, lNameInput);

          Label wage = new Label("Wage");
          TextField wageTF = new TextField();
          VBox wageGroup = new VBox(wage, wageTF);

          Label classroom = new Label("Classroom");
          TextField classroomInput = new TextField();
          VBox classroomGroup = new VBox(classroom, classroomInput);

          Label pos = new Label("Position");
          TextField posTF = new TextField();
          VBox posGroup = new VBox(pos, posTF);

          // TODO: Limit wage to 2 decimal places
          Button createInput = new Button("Create!");
          createInput.setOnAction(e1->{
              if (isDouble(wageTF.getText()) && isInteger(classroomInput.getText()) && isInteger(posTF.getText())) {
                  Staff createStaff = new Staff(0, fNameInput.getText(), lNameInput.getText(), Double.parseDouble(wageTF.getText()), Integer.parseInt(classroomInput.getText()), Integer.parseInt(posTF.getText()));
                  staff.createStaff(createStaff);
                  loadTable();
              }
              fNameInput.setText("");
              lNameInput.setText("");
              classroomInput.setText("");
              wageTF.setText("");
              posTF.setText("");
          });

          HBox createCollection = new HBox(fNameGroup, lNameGroup, wageGroup, classroomGroup, posGroup);
          createCollection.setSpacing(10);

          VBox items = new VBox();
          items.getChildren().addAll(setEscape(), createCollection, createInput);
          items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
          this.content.setBottom(items);
      });

      update.setOnAction(e-> {
          Label idNum = new Label("Id");
          TextField idNumInput = new TextField();
          VBox idNumGroup = new VBox(idNum, idNumInput);

          Label columnName = new Label("Column");
          ComboBox<String> columnNameChoice = new ComboBox<>();

          if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
              DisplayStaff getIdStaff = (DisplayStaff) this.tableView.getSelectionModel().getSelectedItems().get(0);
              idNumInput.setText(""+getIdStaff.getId());
          }

          columnNameChoice.getItems().addAll("First Name", "Last Name", "Wage", "Room ID", "Position ID");
          VBox columnNameGroup = new VBox(columnName, columnNameChoice);

          Label updateName = new Label("New");
          TextField updateNameInput = new TextField();
          VBox updateNameGroup = new VBox(updateName, updateNameInput);

          Button updateInput = new Button("Update!");
          updateInput.setOnAction(e1->{
              Staff updateStaff = staff.getStaff(Integer.parseInt(idNumInput.getText()));
              if (updateStaff != null) {
                  switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                      case ("First Name") -> updateStaff.setFirst_name(updateNameInput.getText());
                      case ("Last Name") -> updateStaff.setLast_name(updateNameInput.getText());
                      case ("Wage") -> {
                          if (isDouble(updateNameInput.getText())) {
                              updateStaff.setWage(Double.parseDouble(updateNameInput.getText()));
                          } else {
                              System.out.println("Wage input was not numeric");
                          }
                      }
                      case ("Room ID") -> {
                          if (isInteger(updateNameInput.getText())) {
                              updateStaff.setRoom_id(Integer.parseInt(updateNameInput.getText()));
                          } else {
                              System.out.println("Room ID must be an integer");
                          }
                      }
                      case ("Position ID") -> {
                          if (isInteger(updateNameInput.getText())) {
                              updateStaff.setPosition_id(Integer.parseInt(updateNameInput.getText()));
                          } else {
                              System.out.println("Position ID must be an integer");
                          }
                      }
                      default -> System.out.println("Category not selected");
                  }
                  updateNameInput.setText("");
                  staff.updateStaff(updateStaff);
                  loadTable();
              }
          });

          HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
          updateCollection.setSpacing(10);

          VBox items = new VBox();
          items.getChildren().addAll(setEscape(), updateCollection, updateInput);
          items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
          this.content.setBottom(items);
      });
  }

  @Override
  protected void loadTable() {
      this.tableView = new TableView();
      try {
          staff = StaffTable.getInstance();
      } catch (SQLException e){
          e.printStackTrace();
          System.out.println("Could not get table.");
      }

      // Create Columns
      TableColumn<DisplayStaff, String> columnId = new TableColumn<>("ID");
      columnId.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getId())));

      TableColumn<DisplayStaff, String> column1 = new TableColumn<>("First Name");
      column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

      TableColumn<DisplayStaff, String> column2 = new TableColumn<>("Last Name");
      column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

      TableColumn<DisplayStaff, String> column3 = new TableColumn<>("Wage");
      column3.setCellValueFactory(e -> new SimpleStringProperty(String.format("$%.2f", e.getValue().getWage())));

      TableColumn<DisplayStaff, String> column4 = new TableColumn<>("Room");
      column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRoom())));

      TableColumn<DisplayStaff, String> column5 = new TableColumn<>("Position");
      column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getPosition())));

      tableView.getColumns().addAll(columnId, column1, column2, column3, column4, column5);
      tableView.getItems().addAll(staff.getAllDisplayStaff());

      this.content.setCenter(tableView);
  }

    /**
     * Takes series for barchart and adds the data, from staff table, based on a given position id.
     * @param seriesArray XYChart.Series<String, Number>
     * @param posId int
     */
    private void setDataByPosition(ArrayList<XYChart.Series<String, Number>> seriesArray, int posId) {
        ArrayList<Staff> staff = this.staff.getAllStaff();
        // Set counts for each wage group
        int count0 = 0, count1 = 0, count2 = 0;
        // Sorted staff by pos
        ArrayList<Staff> staffList = new ArrayList<>();

        // Populate staffList
        for (Staff s : staff) {
            if (s.getPosition_id() == posId) {
                staffList.add(s);
            }
        }

        // Get wage count for pos
        for (Staff s : staffList) {
            double wage = s.getWage();

            if (wage < 17.00) {
                count0++;
            } else if (wage < 18.00) {
                count1++;
            } else if (wage >= 18.00) {
                count2++;
            }
        }

        // Add data to room
        String posName = positionTable.getPosition(posId).getName();
        seriesArray.get(0).getData().add(new XYChart.Data(posName, count0));
        seriesArray.get(1).getData().add(new XYChart.Data(posName, count1));
        seriesArray.get(2).getData().add(new XYChart.Data(posName, count2));
    }
}


