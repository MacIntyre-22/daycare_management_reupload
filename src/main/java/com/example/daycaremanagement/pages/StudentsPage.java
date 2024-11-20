package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.Room;
import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.tables.RoomTable;
import com.example.daycaremanagement.tables.StudentTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class StudentsPage extends CrudOverlay {
    private static StudentsPage instance;
    private Label title = new Label("Students");
    private StudentTable students;
    private RoomTable roomTable;


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
                roomTable = new RoomTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Could not get table.");
            }

            ArrayList<Room> roomArray = roomTable.getAllRooms();

            ArrayList<PieChart.Data> data = new ArrayList<>();


            for(Room room : roomArray){
                double count = students.getItemCount(room.getId());

                if(count > 0) {
                    data.add(new PieChart.Data(room.getName(), count));
                }
            }
            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
            chart.setLegendSide(Side.LEFT);
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
            xAxis.setLabel("Rooms");

            //Defining the y axis
            NumberAxis yAxis = new NumberAxis(0, 14, 2);
            yAxis.setLabel("Students");

            //Creating the Bar chart
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Age Count By Room");

            // Series
            ArrayList<XYChart.Series<String, Number>> seriesArray = new ArrayList<>();
            // Each Room is a series
            String series0Name = "<1";
            XYChart.Series<String, Number> age0 = new XYChart.Series<>();
            age0.setName(series0Name);
            seriesArray.add(age0);
            // Set Data for all series for each room under here

            String series1Name = "1";
            XYChart.Series<String, Number> age1 = new XYChart.Series<>();
            age1.setName(series1Name);
            seriesArray.add(age1);

            String series2Name = "2";
            XYChart.Series<String, Number> age2 = new XYChart.Series<>();
            age2.setName(series2Name);
            seriesArray.add(age2);

            String series3Name = "3";
            XYChart.Series<String, Number> age3 = new XYChart.Series<>();
            age3.setName(series3Name);
            seriesArray.add(age3);

            String series4Name = "4";
            XYChart.Series<String, Number> age4 = new XYChart.Series<>();
            age4.setName(series4Name);
            seriesArray.add(age4);

            // Add data using setDataByRoom()
            setDataByRoom(seriesArray, 1);
            setDataByRoom(seriesArray, 2);
            setDataByRoom(seriesArray, 3);
            setDataByRoom(seriesArray, 4);
            setDataByRoom(seriesArray, 5);


            barChart.setLegendSide(Side.LEFT);
            content.setCenter(barChart);

        });
        graph4.setOnAction(e->{
            try {
                students = new StudentTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Could not get table.");
            }

            // Create the X and Y axes
            NumberAxis xAxis = new NumberAxis(0, 20, 2);
            xAxis.setLabel("Students");

            NumberAxis yAxis = new NumberAxis(0, 20, 1);
            yAxis.setLabel("Age");

            // Create the BubbleChart
            BubbleChart<Number, Number> bubbleChart = new BubbleChart<>(xAxis, yAxis);
            bubbleChart.setTitle("Age Chart");

            // Create a dataset
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Data");

            // Create Birthday Data here

            // Sample data
            series.getData().add(new XYChart.Data<>(4, 4));
            series.getData().add(new XYChart.Data<>(9, 9));
            series.getData().add(new XYChart.Data<>(11, 11));
            series.getData().add(new XYChart.Data<>(9, 9));

            // Add the series to the chart
            bubbleChart.getData().add(series);
            content.setCenter(bubbleChart);
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
            roomTable = new RoomTable();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get table.");
        }

        // Create Columns
        TableColumn<Student, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<Student, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<Student, String> column3 = new TableColumn<>("Birth Date");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBirthdate()));

        TableColumn<Student, String> column4 = new TableColumn<>("Age");
        column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getAge())));

        TableColumn<Student, String> column5 = new TableColumn<>("Room");
        column5.setCellValueFactory(e -> new SimpleStringProperty(roomTable.getRoom(e.getValue().getRoom_id()).getName()));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
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

    /**
     * Takes series for barchart and adds the data, from students table, based on a given room id.
     * @param seriesArray
     * @param roomId
     */
    private void setDataByRoom(ArrayList<XYChart.Series<String, Number>> seriesArray, int roomId) {
        ArrayList<Student> students = this.students.getAllStudents();
        // Counts for each age
        int count0 = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        // Sorted students by room
        ArrayList<Student> studentsList = new ArrayList<>();

        // Popular studentList
        for (Student student : students) {
            if (student.getRoom_id() == roomId) {
                studentsList.add(student);
            }
        }

        // Get age count for room
        for (Student student : studentsList) {
            System.out.println(student.getAge());
            switch ((int) student.getAge()) {
                case 0:
                    count0++;
                case 1:
                    count1++;
                case 2:
                    count2++;
                case 3:
                    count3++;
                case 4:
                    count4++;
            }
        }

        // Add data to room
        seriesArray.get(0).getData().add(new XYChart.Data(roomTable.getRoom(roomId).getName(), count0));
        seriesArray.get(1).getData().add(new XYChart.Data(roomTable.getRoom(roomId).getName(), count1));
        seriesArray.get(2).getData().add(new XYChart.Data(roomTable.getRoom(roomId).getName(), count2));
        seriesArray.get(3).getData().add(new XYChart.Data(roomTable.getRoom(roomId).getName(), count3));
        seriesArray.get(4).getData().add(new XYChart.Data(roomTable.getRoom(roomId).getName(), count4));
    }
}
