package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;

public class GuardiansPage extends CrudOverlay {
    private static GuardiansPage instance;
    private Label title = new Label("Guardians");
    private GuardianTable guardianTable;
    private RoomTable roomTable;
    private CityTable cityTable;
    private GuardianStudentRelationTable familyRelationTable;
    private StudentTable studentTable;

    /**
     * Gets an instance of this class
     * @return the instance
     */
    public static GuardiansPage getInstance(){
        if (instance == null){
            instance = new GuardiansPage();
        }
        return instance;
    }


    private GuardiansPage() {
        super();
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        content.setTop(title);
        loadTable();
        loadInfo();
    }

    @Override
    protected void sideButtonBar() {
        // Define actions specific to Guardians’ side buttons here
        graph1.setOnAction(e->{
            loadTable();
        });

        // City Pie Chart
        graph2.setOnAction(e->{
            PieChart chart = new PieChart();
            chart.setTitle("Guardians City");
            chart.setLegendVisible(true);
            // Grab Tables
            guardianTable = new GuardianTable();
            cityTable = new CityTable();

            // Grab list of rooms
            ArrayList<City> cityArray = cityTable.getAllCities();
            ArrayList<PieChart.Data> data = new ArrayList<>();

            // Creat guardian list
            ArrayList<Guardian> guardians = guardianTable.getAllGuardians();

            // Count how many guardians have city id equal to their city id
            for(City city : cityArray){
                double count = 0;

                // Check all guardians
                for (Guardian g : guardians){
                    if (city.getId() == g.getCity_id()){
                        count++;
                    }
                }

                if(count > 0) {
                    // Add count and city name to data
                    data.add(new PieChart.Data(city.getName(), count));
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

        // Guardian Relation Table
        graph3.setText("Children");
        graph3.setOnAction(ex -> {
            TableView relationTable = new TableView();
            // Grab table data
            guardianTable = new GuardianTable();
            familyRelationTable = new GuardianStudentRelationTable();

            // Create Columns
            TableColumn<Guardian, String> column1 = new TableColumn<>("First Name");
            column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

            TableColumn<Guardian, String> column2 = new TableColumn<>("Last Name");
            column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

            TableColumn<Guardian, String> column3 = new TableColumn<>("Children");
            // TODO MAKE TABLE LOAD FASTER, THIS LINE CAUSES SUPER SLOW LOAD
            column3.setCellValueFactory(e -> new SimpleStringProperty(getChildren(e.getValue().getId())));// Pass array of kids


            relationTable.getColumns().addAll(column1, column2, column3);
            relationTable.getItems().addAll(guardianTable.getAllGuardians());
            relationTable.setStyle("");

            // Create a layout with a label and add to center
            VBox layout = new VBox();
            layout.autosize();
            // Table label
            Label tableLabel = new Label("Guardian Relation");
            tableLabel.setFont(new Font(15));

            // Border Pane for table
            BorderPane tablePane = new BorderPane();
            tablePane.setCenter(relationTable);
            layout.getChildren().addAll(tableLabel, tablePane);
            layout.setAlignment(Pos.TOP_CENTER);
            layout.setSpacing(10);

            this.content.setCenter(layout);
        });
    }

    @Override
    protected void bottomButtonBar() {
        // Define actions specific to Guardians’ CRUD buttons here
    }

    @Override
    protected void loadTable() {
        this.tableView = new TableView();
        guardianTable = new GuardianTable();
        cityTable = new CityTable();


        // Create Columns
        TableColumn<Guardian, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<Guardian, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<Guardian, String> column3 = new TableColumn<>("Phone");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPhone()));

        TableColumn<Guardian, String> column4 = new TableColumn<>("Email");
        column4.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPhone()));

        TableColumn<Guardian, String> column5 = new TableColumn<>("City ID");
        column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getCity_id())));

        TableColumn<Guardian, String> column6 = new TableColumn<>("Street Number");
        column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getStreet_num())));

        TableColumn<Guardian, String> column7 = new TableColumn<>("Street Name");
        column4.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getStreet_name()));


        tableView.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);
        tableView.getItems().addAll(guardianTable.getAllGuardians());
        tableView.setStyle("");

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
     * Finds all the students that is related to the guardian id given.
     * @param guardianId int
     * @return a list as a String of student's names
     */
    private String getChildren(int guardianId) {
        try {
            familyRelationTable = new GuardianStudentRelationTable();
            studentTable = new StudentTable();
        } catch (Exception e) {
            System.out.println("Error in GuardiansPage.java, Line 174: Cannot get student Table.");
        }

        // Empty array to hold students that are the children of the given id
        ArrayList<Student> childrenArray = new ArrayList<>();
        // Get student table and relation table
        ArrayList<Student> students = studentTable.getAllStudents();

        // Grab each Child that belongs to the guardian
        for (GuardianStudentRelation rel : familyRelationTable.getAllRelations()) {
            if (rel.getGuardian_id() == guardianId) {
                childrenArray.add(studentTable.getStudent(rel.getStudent_id()));
            }
        }

        // Build a string to return
        String result = "";
        for (Student s : childrenArray) {
            result += s.toString();
            result += ", ";
        }

        // Return string of children
        return result;
    }
}
