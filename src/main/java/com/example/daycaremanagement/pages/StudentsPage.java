package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.Room;
import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.tables.RoomTable;
import com.example.daycaremanagement.tables.StudentTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.*;

public class StudentsPage extends CrudOverlay {
    private static StudentsPage instance;
    private Label title = new Label("Students");
    private StudentTable students;
    private RoomTable roomTable;
    private ArrayList<Room> rooms = new ArrayList<>();


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
        // Add rooms to array
        // Significantly increases the load speed and lagginess of the tableView
        try {
            roomTable = new RoomTable();
            rooms.addAll(roomTable.getAllRooms());
        } catch (Exception e) {
            System.out.println("Error From: StudentsPage.java, line 56. Couldn't get Rooms Table.");
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
        graph1.setOnAction(e->{
            loadTable();
        });

        // Students per room bar Chart
        graph2.setOnAction(e->{
            PieChart chart = new PieChart();
            chart.setTitle("Students Per Room");

            // Grab Tables
            try {
                students = new StudentTable();
                roomTable = new RoomTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Could not get table.");
            }

            // Grab list of rooms
            ArrayList<Room> roomArray = roomTable.getAllRooms();
            ArrayList<PieChart.Data> data = new ArrayList<>();

            // Count how many students have room id equal to room
            for(Room room : roomArray){
                double count = students.getItemCount(room.getId());

                if(count > 0) {
                    data.add(new PieChart.Data(room.getName(), count));
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
                students = new StudentTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Could not get table.");
            }
            //Defining the x axis
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Rooms");

            //Defining the y axis
            NumberAxis yAxis = new NumberAxis(0, 16, 2);
            yAxis.setLabel("Students");

            //Creating the Bar chart
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Age Count By Room");

            // Series
            ArrayList<XYChart.Series<String, Number>> seriesArray = new ArrayList<>();
            // Each age group is a series
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

            String series4Name = "4+";
            XYChart.Series<String, Number> age4 = new XYChart.Series<>();
            age4.setName(series4Name);
            seriesArray.add(age4);

            // Add data using setDataByRoom()
            setDataByRoom(seriesArray, 1);
            setDataByRoom(seriesArray, 2);
            setDataByRoom(seriesArray, 3);
            setDataByRoom(seriesArray, 4);
            setDataByRoom(seriesArray, 5);


            barChart.getData().addAll(age0, age1, age2, age3, age4);
            barChart.setLegendSide(Side.LEFT);
            content.setCenter(barChart);

        });

        // Bubble Chart
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

        // Remove buttons here
        NavButtons.getChildren().remove(graph4);
    }

    @Override
    protected void bottomButtonBar() {
        // Define actions specific to Guardians’ CRUD buttons here
        create.setOnAction(e->{
            Label firstName = new Label("First Name");
            TextField fNameInput = new TextField();
            VBox fNameGroup = new VBox(firstName, fNameInput);

            Label lastName = new Label("Last Name");
            TextField lNameInput = new TextField();
            VBox lNameGroup = new VBox(lastName, lNameInput);

            Label birthday = new Label("Birthday");
            TextField birthdayInput = new TextField();
            VBox birthdayGroup = new VBox(birthday, birthdayInput);

            Label classroom = new Label("Classroom");
            TextField classroomInput = new TextField();
            VBox classroomGroup = new VBox(classroom, classroomInput);

            Label behaviour = new Label("Behaviour");
            TextField behaviourInput = new TextField();
            VBox behaviourGroup = new VBox(behaviour, behaviourInput);

            Label age = new Label("Age");
            TextField ageInput = new TextField();
            VBox ageGroup = new VBox(age, ageInput);

            Button createInput = new Button("Create!");
            createInput.setOnAction(e1->{
                // Grabs the text in the fields
            });

            HBox createCollection = new HBox(fNameGroup, lNameGroup, birthdayGroup, classroomGroup, behaviourGroup, ageGroup);
            createCollection.setSpacing(10);

            VBox items = new VBox();
            items.getChildren().addAll(setEscape(), createCollection, createInput);
            items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
            this.content.setBottom(items);
        });
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

        TableColumn<Student, String> column3 = new TableColumn<>("Birth Date");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBirthdate()));

        TableColumn<Student, String> column4 = new TableColumn<>("Age");
        column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf((int) e.getValue().getAge())));

        TableColumn<Student, String> column5 = new TableColumn<>("Room");
        column5.setCellValueFactory(e -> new SimpleStringProperty(getRoomName(this.rooms,e.getValue().getRoom_id())));

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
     * @param seriesArray XYChart.Series<String, Number>
     * @param roomId int
     */
    private void setDataByRoom(ArrayList<XYChart.Series<String, Number>> seriesArray, int roomId) {
        ArrayList<Student> students = this.students.getAllStudents();
        // Set counts for each age group
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
            int age = (int) student.getAge();

            if (age < 0) {
                count0++;
            } else if (age == 1) {
                count1++;
            } else if (age == 2) {
                count2++;
            } else if (age == 3) {
                count3++;
            } else if (age >= 4) {
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
