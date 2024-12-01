package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.Room;
import com.example.daycaremanagement.pojo.GuardianStudentRelation;
import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.tables.RoomTable;
import com.example.daycaremanagement.pojo.display.DisplayStaff;
import com.example.daycaremanagement.pojo.display.DisplayStudent;
import com.example.daycaremanagement.tables.GuardianStudentRelationTable;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.ArrayList;

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

    /**
     * This Pages Displays the Students Page.
     * With the Table data
     * and with the CRUD overlay
     * and a some low level table info at the bottom of the table
     */
    private StudentsPage() {
        super();
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        content.setTop(title);
        // Add rooms to array
        // Significantly increases the load speed and lagginess of the tableView
        try {
            roomTable = RoomTable.getInstance();
            rooms.addAll(roomTable.getAllRooms());
        } catch (Exception e) {
            System.out.println("Error From: StudentsPage.java, line 56. Couldn't get Rooms Table.");
        }

        // Set Icons for buttons we use
        graph1.setGraphic(createBtn(setIcon(ICONS[0], 30), "Table"));
        graph2.setGraphic(createBtn(setIcon(ICONS[1], 30), "Room"));
        graph3.setGraphic(createBtn(setIcon(ICONS[2], 30), "Ages"));

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
                students = StudentTable.getInstance();
                roomTable = RoomTable.getInstance();
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
                students = StudentTable.getInstance();
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
                students = StudentTable.getInstance();
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
            birthdayInput.setPromptText("YYYY-MM-DD");
            VBox birthdayGroup = new VBox(birthday, birthdayInput);

            Label classroom = new Label("Classroom");
            TextField classroomInput = new TextField();
            VBox classroomGroup = new VBox(classroom, classroomInput);
            // TODO: Validate birthdate format

            Button createInput = new Button("Create!");
            createInput.setOnAction(e1->{
                if (isInteger(classroomInput.getText())) {
                    Student createStudent = new Student(0, fNameInput.getText(), lNameInput.getText(), birthdayInput.getText(), Integer.parseInt(classroomInput.getText()));
                    students.createStudent(createStudent);
                    loadTable();
                } else {
                    System.out.println("Input error, Room ID was not numeric");
                }
            });

            HBox createCollection = new HBox(fNameGroup, lNameGroup, birthdayGroup, classroomGroup);
            createCollection.setSpacing(10);

            VBox items = new VBox();
            items.getChildren().addAll(setEscape(), createCollection, createInput);
            items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
            this.content.setBottom(items);
        });

        update.setOnAction(e-> {
            Label idNum = new Label("ID");
            TextField idNumInput = new TextField();
            VBox idNumGroup = new VBox(idNum, idNumInput);
            if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                DisplayStudent getIdStudent = (DisplayStudent) this.tableView.getSelectionModel().getSelectedItems().get(0);
                idNumInput.setText(""+getIdStudent.getId());
            }

            Label columnName = new Label("Column");
            ComboBox<String> columnNameChoice = new ComboBox<>();
            // Temporary Options
            // Grab Columns
            columnNameChoice.getItems().addAll("First Name", "Last Name", "Birthday", "Classroom ID");
            VBox columnNameGroup = new VBox(columnName, columnNameChoice);



            Label updateName = new Label("New");
            TextField updateNameInput = new TextField();
            VBox updateNameGroup = new VBox(updateName, updateNameInput);

            Button updateInput = new Button("Update!");


            updateInput.setOnAction(e1->{
                Student updateStudent = students.getStudent(Integer.parseInt(idNumInput.getText()));
                if (updateStudent != null) {
                    switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                        case ("First Name") -> updateStudent.setFirst_name(updateNameInput.getText());
                        case ("Last Name") -> updateStudent.setLast_name(updateNameInput.getText());
                        case ("Birthday") -> updateStudent.setBirthdate(updateNameInput.getText());
                        case ("Classroom ID") -> {
                            if (isInteger(updateNameInput.getText())) {
                                updateStudent.setRoom_id(Integer.parseInt(updateNameInput.getText()));
                            } else {
                                System.out.println("Input error, Room ID was not numeric");
                            }
                        }
                        default -> System.out.println("Category was not selected");
                    }
                    students.updateStudent(updateStudent);
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


        // Deletes the student and all of their relations (but doesn't delete the guardians connected to them)
        delete.setOnAction(e->{
            if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                DisplayStudent deleteStudent = (DisplayStudent) this.tableView.getSelectionModel().getSelectedItems().get(0);
                try {
                    ArrayList<GuardianStudentRelation> deleteRelations = GuardianStudentRelationTable.getInstance().getRelationByEitherId(deleteStudent.getId(), false);
                    for (GuardianStudentRelation deleteRelation : deleteRelations) {
                        GuardianStudentRelationTable.getInstance().deleteRelation(deleteRelation);
                    }
                } catch (SQLException e2){
                    e2.printStackTrace();
                }

                students.deleteStudent(students.getStudent(deleteStudent.getId()));
                loadTable();
            }
        });
    }

    // Create Table
    @Override
    protected void loadTable() {
        this.tableView = new TableView();
        try {
            students = StudentTable.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get table.");
        }

        // Create Columns
        TableColumn<DisplayStudent, String> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getId())));

        TableColumn<DisplayStudent, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<DisplayStudent, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<DisplayStudent, String> column3 = new TableColumn<>("Birthday");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBirthdate()));

        TableColumn<DisplayStudent, String> column4 = new TableColumn<>("Age");
        column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf((int) getAge(e.getValue().getBirthdate()))));

        TableColumn<DisplayStudent, String> column5 = new TableColumn<>("Room");
        column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRoom())));

        tableView.getColumns().addAll(columnId, column1, column2, column3, column4, column5);
        tableView.getItems().addAll(students.getAllDisplayStudents());

        this.content.setCenter(tableView);
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
            int age = (int) getAge(student.getBirthdate());

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

    /**
     * Gets the students age by getting the difference between their birthdate and today.
     * @return age as double
     */
    public double getAge(String dob) {
        // Get student birthday
        // YYYY-MM-DD
        String[] birthdaySplit = dob.split("-");
        LocalDate birthdayDate = LocalDate.of(Integer.parseInt(birthdaySplit[0]), Integer.parseInt(birthdaySplit[1]), Integer.parseInt(birthdaySplit[2]));
        LocalDate now = LocalDate.now();
        double age;

        // Get the difference to find age
        age = (double) ChronoUnit.YEARS.between(birthdayDate, now);
        // Return age
        return age;
    }
}
