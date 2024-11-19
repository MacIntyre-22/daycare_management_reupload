package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.tables.StudentTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class StudentsPage extends CrudOverlay {
    private static StudentsPage instance;
    private Label title = new Label("Students");
    private StudentTable students;


    /**
     * Gets an instance of this class
     * @return the instance
     */
    public static StudentsPage getInstance(){
        if (instance == null){
            instance = new StudentsPage();
        }
        return instance;
    }


    private StudentsPage() {
        super();
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        content.setTop(title);
        loadTable();
        loadInfo();
    }

    @Override
    protected void sideButtonBar() {
        graph1.setOnAction(e->{
            loadTable();
        });
        graph2.setOnAction(e->{
            PieChart chart = new PieChart();
            chart.setTitle("Students Per Room");
            chart.setLegendVisible(true);
            try {
                students = new StudentTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Could not get table.");
            }

            //Grab a list of all the coin types
            ArrayList<Student> studentsArray = students.getAllStudents();
            ArrayList<PieChart.Data> data = new ArrayList<>();
            for(Student student : studentsArray){
                double roomId = student.getRoom_id();
                //"PENNY", 5
                if(roomId > 0) {
                    data.add(new PieChart.Data(student.getFirst_name(), roomId));
                }
            }
            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
            chart.setData(chartData);

            // Set the graph
            content.setCenter(chart);
        });
        graph3.setOnAction(e->{
            try {
                students = new StudentTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Could not get table.");
            }
            //Defining the x axis
            CategoryAxis xAxis = new CategoryAxis();

            xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Room")));
            xAxis.setLabel("Rooms");

            //Defining the y axis
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Students");

            //Creating the Bar chart
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Students Per Room");

            // Add data using XYChart.Series
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Data");

            for (Student student : students.getAllStudents()) {
                series.getData().add(new XYChart.Data("Data", student.getId()));
            }

            barChart.getData().addAll(series);
            content.setCenter(barChart);

        });
        graph4.setOnAction(e->{

        });
    }

    @Override
    protected void bottomButtonBar() {
        // Define actions specific to Guardians’ CRUD buttons here
    }

    // Create Table
    @Override
    protected void loadTable() {
        this.tableView = new TableView();
        try {
            students = new StudentTable();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get table.");
        }

        // Create Columns
        TableColumn<Student, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<Student, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<Student, String> column3 = new TableColumn<>("Birthday");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBirthdate()));

        TableColumn<Student, String> column4 = new TableColumn<>("Room");
        column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRoom_id())));

        tableView.getColumns().addAll(column1, column2, column3, column4);
        tableView.getItems().addAll(students.getAllStudents());

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
