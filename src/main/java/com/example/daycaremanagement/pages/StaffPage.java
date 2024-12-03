package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.pojo.display.DisplayStaff;
import com.example.daycaremanagement.tables.StaffTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

import java.sql.SQLException;

public class StaffPage extends CrudOverlay {
  private static StaffPage instance;
  private Label title = new Label("Staff Page");
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
      this.getStylesheets().add(MainApp.class.getResource("Styles/StaffPage.css").toExternalForm());
      title.getStyleClass().add("title");
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
      loadInfo("staff");
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
          fNameGroup.setAlignment(Pos.CENTER);

          Label lastName = new Label("Last Name");
          TextField lNameInput = new TextField();
          VBox lNameGroup = new VBox(lastName, lNameInput);
          lNameGroup.setAlignment(Pos.CENTER);

          Label wage = new Label("Wage");
          TextField wageTF = new TextField();
          VBox wageGroup = new VBox(wage, wageTF);
          wageGroup.setAlignment(Pos.CENTER);

          Label classroom = new Label("Classroom");
          TextField classroomInput = new TextField();
          VBox classroomGroup = new VBox(classroom, classroomInput);
          classroomGroup.setAlignment(Pos.CENTER);

          Label pos = new Label("Position");
          TextField posTF = new TextField();
          VBox posGroup = new VBox(pos, posTF);
          posGroup.setAlignment(Pos.CENTER);

          Button createInput = new Button("Create!");
          createInput.setOnAction(e1->{
              if (isValidWage(wageTF.getText()) && isValidId(classroomInput.getText(), "room") && isValidId(posTF.getText(), "position")) {
                  Staff createStaff = new Staff(0, fNameInput.getText(), lNameInput.getText(), roundToTwo(Double.parseDouble(wageTF.getText())), Integer.parseInt(classroomInput.getText()), Integer.parseInt(posTF.getText()));
                  staff.createStaff(createStaff);
                  loadTable();
              } else {
                  System.out.println("Invalid input");
              }
              fNameInput.setText("");
              lNameInput.setText("");
              classroomInput.setText("");
              wageTF.setText("");
              posTF.setText("");
          });
          HBox escapeGroup = new HBox(setEscape("staff"));
          escapeGroup.setAlignment(Pos.TOP_RIGHT);

          HBox createCollection = new HBox(fNameGroup, lNameGroup, wageGroup, classroomGroup, posGroup);
          createCollection.setSpacing(10);
          createCollection.setAlignment(Pos.CENTER);

          createCollection.getChildren().forEach(node-> {
              node.setOnKeyPressed(keyEvent -> {
                  if (isDouble(wageTF.getText()) && isValidId(classroomInput.getText(), "room") && isValidId(posTF.getText(), "position")) {
                      Staff createStaff = new Staff(0, fNameInput.getText(), lNameInput.getText(), roundToTwo(Double.parseDouble(wageTF.getText())), Integer.parseInt(classroomInput.getText()), Integer.parseInt(posTF.getText()));
                      staff.createStaff(createStaff);
                      loadTable();
                  } else {
                      System.out.println("Invalid input");
                  }
                  fNameInput.setText("");
                  lNameInput.setText("");
                  classroomInput.setText("");
                  wageTF.setText("");
                  posTF.setText("");
              });
          });

          HBox createInputGroup = new HBox(createInput);
          createInputGroup.setAlignment(Pos.CENTER);

          VBox items = new VBox();
          items.getChildren().addAll(escapeGroup, createCollection, createInputGroup);
          items.getStyleClass().add("items");
          this.content.setBottom(items);
      });

      update.setOnAction(e-> {
          Label idNum = new Label("Id");
          TextField idNumInput = new TextField();
          VBox idNumGroup = new VBox(idNum, idNumInput);
          idNumGroup.setAlignment(Pos.CENTER);

          Label columnName = new Label("Column");
          ComboBox<String> columnNameChoice = new ComboBox<>();

          if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
              DisplayStaff getIdStaff = (DisplayStaff) this.tableView.getSelectionModel().getSelectedItems().get(0);
              idNumInput.setText(""+getIdStaff.getId());
          }

          columnNameChoice.getItems().addAll("First Name", "Last Name", "Wage", "Room ID", "Position ID");
          VBox columnNameGroup = new VBox(columnName, columnNameChoice);
          columnNameGroup.setAlignment(Pos.CENTER);

          Label updateName = new Label("New");
          TextField updateNameInput = new TextField();
          VBox updateNameGroup = new VBox(updateName, updateNameInput);
          updateNameGroup.setAlignment(Pos.CENTER);

          Button updateInput = new Button("Update!");
          updateInput.setOnAction(e1->{
              if (isInteger(idNumInput.getText())) {
                  Staff updateStaff = staff.getStaff(Integer.parseInt(idNumInput.getText()));
                  if (updateStaff != null) {
                      switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                          case ("First Name") -> updateStaff.setFirst_name(updateNameInput.getText());
                          case ("Last Name") -> updateStaff.setLast_name(updateNameInput.getText());
                          case ("Wage") -> {
                              if (isValidWage(updateNameInput.getText())) {
                                  updateStaff.setWage(roundToTwo(Double.parseDouble(updateNameInput.getText())));
                              } else {
                                  System.out.println("Wage must be a number greater than or equal to 0");
                              }
                          }
                          case ("Room ID") -> {
                              if (isValidId(updateNameInput.getText(), "room")) {
                                  updateStaff.setRoom_id(Integer.parseInt(updateNameInput.getText()));
                              } else {
                                  System.out.println("Invalid Room ID");
                              }
                          }
                          case ("Position ID") -> {
                              if (isValidId(updateNameInput.getText(), "position")) {
                                  updateStaff.setPosition_id(Integer.parseInt(updateNameInput.getText()));
                              } else {
                                  System.out.println("Invalid Position ID");
                              }
                          }
                          default -> System.out.println("Category not selected");
                      }
                      updateNameInput.setText("");
                      staff.updateStaff(updateStaff);
                      loadTable();
                  } else {
                      System.out.println("Specified ID does not exist");
                  }
              } else {
                  System.out.println("Invalid ID");
              }
          });
          HBox escapeGroup = new HBox(setEscape("staff"));
          escapeGroup.setAlignment(Pos.TOP_RIGHT);

          HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
          updateCollection.setSpacing(10);
          updateCollection.setAlignment(Pos.CENTER);

          updateCollection.getChildren().forEach(node-> {
              node.setOnKeyPressed(keyEvent -> {
                  if (isInteger(idNumInput.getText())) {
                      Staff updateStaff = staff.getStaff(Integer.parseInt(idNumInput.getText()));
                      if (updateStaff != null) {
                          switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                              case ("First Name") -> updateStaff.setFirst_name(updateNameInput.getText());
                              case ("Last Name") -> updateStaff.setLast_name(updateNameInput.getText());
                              case ("Wage") -> {
                                  if (isDouble(updateNameInput.getText())) {
                                      updateStaff.setWage(roundToTwo(Double.parseDouble(updateNameInput.getText())));
                                  } else {
                                      System.out.println("Wage input was not numeric");
                                  }
                              }
                              case ("Room ID") -> {
                                  if (isValidId(updateNameInput.getText(), "room")) {
                                      updateStaff.setRoom_id(Integer.parseInt(updateNameInput.getText()));
                                  } else {
                                      System.out.println("Invalid Room ID");
                                  }
                              }
                              case ("Position ID") -> {
                                  if (isValidId(updateNameInput.getText(), "position")) {
                                      updateStaff.setPosition_id(Integer.parseInt(updateNameInput.getText()));
                                  } else {
                                      System.out.println("Invalid Position ID");
                                  }
                              }
                              default -> System.out.println("Category not selected");
                          }
                          updateNameInput.setText("");
                          staff.updateStaff(updateStaff);
                          loadTable();
                      } else {
                          System.out.println("Specified ID does not exist");
                      }
                  } else {
                      System.out.println("Invalid ID");
                  }
              });
          });
          HBox createUpdateGroup = new HBox(updateInput);
          createUpdateGroup.setAlignment(Pos.CENTER);

          VBox items = new VBox();
          items.getChildren().addAll(escapeGroup, updateCollection, createUpdateGroup);
          items.getStyleClass().add("items");
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

      tableView.setMinWidth(300);
      tableView.setMaxWidth(925);

      columnId.setPrefWidth((tableView.getMaxWidth() / tableView.getColumns().size() ));
      columnId.setResizable(false);
      columnId.setReorderable(false);

      column1.setPrefWidth((tableView.getMaxWidth() / tableView.getColumns().size() ));
      column1.setResizable(false);
      column1.setReorderable(false);

      column2.setPrefWidth((tableView.getMaxWidth() / tableView.getColumns().size()) );
      column2.setResizable(false);
      column2.setReorderable(false);

      column3.setPrefWidth((tableView.getMaxWidth() / tableView.getColumns().size()) );
      column3.setResizable(false);
      column3.setReorderable(false);

      column4.setPrefWidth((tableView.getMaxWidth() / tableView.getColumns().size() ));
      column4.setResizable(false);
      column4.setReorderable(false);

      column5.setPrefWidth((tableView.getMaxWidth() / tableView.getColumns().size()) );
      column5.setResizable(false);
      column5.setReorderable(false);

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


