package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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

      // Set Icons for buttons we use
      graph1.setGraphic(setIcon(ICONS[0], 30));
      graph2.setGraphic(setIcon(ICONS[1], 30));
      graph3.setGraphic(setIcon(ICONS[2], 30));

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

      // Student Age per Room bar Chart
      graph3.setOnAction(e->{
          staffTable = new StaffTable();

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

          Button createInput = new Button("Create!");
          createInput.setOnAction(e1->{
              // Grabs the text in the fields
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
          // Temporary Options
          // Grab Columns
          columnNameChoice.getItems().addAll("Name1", "Name2", "Name3");
          VBox columnNameGroup = new VBox(columnName, columnNameChoice);

          Label updateName = new Label("New");
          TextField updateNameInput = new TextField();
          VBox updateNameGroup = new VBox(updateName, updateNameInput);

          Button updateInput = new Button("Update!");
          updateInput.setOnAction(e1->{
              // Grabs the text in the fields
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

    /**
     * Takes series for barchart and adds the data, from staff table, based on a given position id.
     * @param seriesArray XYChart.Series<String, Number>
     * @param posId int
     */
    private void setDataByPosition(ArrayList<XYChart.Series<String, Number>> seriesArray, int posId) {
        ArrayList<Staff> staff = this.staffTable.getAllStaff();
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


