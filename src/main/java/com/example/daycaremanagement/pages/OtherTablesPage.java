package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;
import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.pojo.display.DisplayStaff;
import com.example.daycaremanagement.pojo.display.DisplayStudent;
import com.example.daycaremanagement.tables.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class OtherTablesPage extends CrudOverlay {
    // DF
    private static OtherTablesPage instance;
    private Label title;

    // Active table
    int active = 0;

    private PositionTable posTable;
    private RoomTable roomTable;
    private CityTable cityTable;
    private GuardianStudentRelationTable relationTable;

    protected TableView tableView;


    /**
     * Gets an instance of this class
     * @return the instance
     */
    public static OtherTablesPage getInstance() {
        if (instance == null) {
            instance = new OtherTablesPage();
        }
        return instance;
    }

    private OtherTablesPage() {
        super();
        title = new Label("Other Tables");
        content.setTop(title);
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        // Set Icons for buttons we use
        graph1.setText("Rel.");
        graph1.setMinWidth(50);
        graph2.setText("Room");
        graph2.setMinWidth(50);
        graph3.setText("Pos.");
        graph3.setMinWidth(50);
        graph4.setText("City");
        graph4.setMinWidth(50);

        this.tableView = new TableView();

        loadTable();
        loadInfo(null);
    }

    @Override
    protected void sideButtonBar() {
        // Load Relation Tbale
        graph1.setOnAction(e->{
            loadTable();

            // Reset Form
            this.content.setBottom(createBottomBar());
        });

        // load Room Table
        graph2.setOnAction(e->{
            loadRoomTable();

            // Reset Form
            this.content.setBottom(createBottomBar());
        });

        // Position Table
        graph3.setOnAction(ex -> {
            loadPosTable();

            // Reset Form
            this.content.setBottom(createBottomBar());
        });

        // Remove buttons here
        graph4.setOnAction(e-> {
            loadCityTable();

            // Reset Form
            this.content.setBottom(createBottomBar());
        });
    }

    @Override
    protected void bottomButtonBar() {
        // Conditions for forms depending on what table is selected
        switch (active) {
            // Forms for Relation Table
            case 0:
                loadTable();

                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        GuardianStudentRelation deleteRelation = (GuardianStudentRelation) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        relationTable.deleteRelation(deleteRelation);
                        loadTable();
                    } else {
                        System.out.println("Relation not selected");
                    }
                });


                create.setOnAction(e-> {
                    Label parentId = new Label("Parent ID");
                    TextField parentIdInput = new TextField();
                    VBox parentIdGroup = new VBox(parentId, parentIdInput);

                    Label studentId = new Label("Student ID");
                    TextField studentIdInput = new TextField();
                    VBox studentIdGroup = new VBox(studentId, studentIdInput);

                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{
                        if (isValidId(parentIdInput.getText(), "guardian") && isValidId(studentIdInput.getText(), "student")) {
                            GuardianStudentRelation createRelation = new GuardianStudentRelation(0, Integer.parseInt(parentIdInput.getText()), Integer.parseInt(studentIdInput.getText()));
                            relationTable.createRelation(createRelation);
                        } else {
                            System.out.println("One or more IDs were not valid");
                        }
                        parentIdInput.setText("");
                        studentIdInput.setText("");
                        loadTable();
                    });

                    HBox createCollection = new HBox(parentIdGroup, studentIdGroup);
                    createCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), createCollection, createInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

                update.setOnAction(e-> {
                    Label idNum = new Label("ID");
                    TextField idNumInput = new TextField();
                    VBox idNumGroup = new VBox(idNum, idNumInput);
                    // Get col here
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        GuardianStudentRelation getIdRelation = (GuardianStudentRelation) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        idNumInput.setText(""+getIdRelation.getId());
                    }

                    Label columnName = new Label("Column");
                    ComboBox<String> columnNameChoice = new ComboBox<>();
                    // Temporary Options
                    // Grab Columns
                    columnNameChoice.getItems().addAll("Parent ID", "Student ID");
                    VBox columnNameGroup = new VBox(columnName, columnNameChoice);

                    Label updateName = new Label("New");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {
                        if (isInteger(idNumInput.getText())) {
                            GuardianStudentRelation updateRelation = relationTable.getRelation(Integer.parseInt(idNumInput.getText()));
                            if (updateRelation != null) {
                                switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                                    case "Parent ID" -> {
                                        if (isValidId(updateNameInput.getText(), "guardian")) {
                                            updateRelation.setGuardian_id(Integer.parseInt(updateNameInput.getText()));
                                        } else {
                                            System.out.println("Invalid Guardian ID");
                                        }
                                    }
                                    case "Student ID" -> {
                                        if (isValidId(updateNameInput.getText(), "student")) {
                                            updateRelation.setStudent_id(Integer.parseInt(updateNameInput.getText()));
                                        } else {
                                            System.out.println("Invalid Student ID");
                                        }
                                    }
                                    default -> System.out.println("Category not selected");
                                }
                                relationTable.updateRelation(updateRelation);
                            } else {
                                System.out.println("ID was not numeric or specified relation does not exist");
                            }
                            updateNameInput.setText("");
                            loadTable();
                        } else {
                            System.out.println("Invalid ID");
                        }
                    });

                    HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });
                break;

            // Forms for Rooms Table
            case 1:
                loadRoomTable();

                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        Room deleteRoom = (Room) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        roomTable.deleteRoom(deleteRoom);
                        loadRoomTable();
                    }
                });
                create.setOnAction(e-> {
                    Label name = new Label("Name");
                    TextField nameInput = new TextField();
                    VBox nameGroup = new VBox(name, nameInput);

                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{
                        Room createRoom = new Room(0, nameInput.getText());
                        roomTable.createRoom(createRoom);

                        nameInput.setText("");
                        loadRoomTable();
                    });

                    HBox createCollection = new HBox(nameGroup);
                    createCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), createCollection, createInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

                update.setOnAction(e-> {
                    Label idNum = new Label("ID");
                    TextField idNumInput = new TextField();
                    VBox idNumGroup = new VBox(idNum, idNumInput);

                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        Room getIdRoom = (Room) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        idNumInput.setText(""+getIdRoom.getId());
                    }

                    Label updateName = new Label("New Room name");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {
                        if (isInteger(idNumInput.getText())) {
                            Room updateRoom = roomTable.getRoom(Integer.parseInt(idNumInput.getText()));
                            if (updateRoom != null) {
                                updateRoom.setName(updateNameInput.getText());
                                roomTable.updateRoom(updateRoom);
                            }
                            updateNameInput.setText("");
                            loadRoomTable();
                        } else {
                            System.out.println("Invalid ID");
                        }
                    });

                    HBox updateCollection = new HBox(idNumGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });
                break;

            // Forms for Position Table
            case 2:
                loadPosTable();

                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        Position deletePosition = (Position) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        posTable.deletePosition(deletePosition);
                        loadPosTable();
                    }
                });

                create.setOnAction(e-> {
                    Label name = new Label("Name");
                    TextField nameInput = new TextField();
                    VBox nameGroup = new VBox(name, nameInput);


                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{
                        Position createPosition = new Position(0, nameInput.getText());
                        posTable.createPosition(createPosition);
                        nameInput.setText("");
                        loadPosTable();
                    });

                    HBox createCollection = new HBox(nameGroup);
                    createCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), createCollection, createInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

                update.setOnAction(e-> {
                    Label idNum = new Label("ID");
                    TextField idNumInput = new TextField();
                    VBox idNumGroup = new VBox(idNum, idNumInput);

                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        Position getIdPos = (Position) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        idNumInput.setText(""+getIdPos.getId());
                    }

                    Label updateName = new Label("New Position name");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {
                        if (isInteger(idNumInput.getText())) {
                            Position updatePosition = posTable.getPosition(Integer.parseInt(idNumInput.getText()));
                            if (updatePosition != null) {
                                updatePosition.setName(updateNameInput.getText());
                                posTable.updatePosition(updatePosition);
                            }
                            updateNameInput.setText("");
                            loadPosTable();
                        } else {
                            System.out.println("Invalid ID");
                        }
                    });

                    HBox updateCollection = new HBox(idNumGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });
                break;

            // Cities table
            case 3:
                loadCityTable();

                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        City deleteCity = (City) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        cityTable.deleteCity(deleteCity);
                        loadPosTable();
                    }
                });

                create.setOnAction(e-> {
                    Label name = new Label("Name");
                    TextField nameInput = new TextField();
                    VBox nameGroup = new VBox(name, nameInput);

                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{
                        City createCity = new City(0, nameInput.getText());
                        cityTable.createCity(createCity);

                        nameInput.setText("");
                        loadCityTable();
                    });

                    HBox createCollection = new HBox(nameGroup);
                    createCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), createCollection, createInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

                update.setOnAction(e-> {
                    Label idNum = new Label("ID");
                    TextField idNumInput = new TextField();
                    VBox idNumGroup = new VBox(idNum, idNumInput);

                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        City getIdCity = (City) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        idNumInput.setText(""+getIdCity.getId());
                    }

                    Label updateName = new Label("New City Name");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {
                        if (isInteger(idNumInput.getText())) {
                            City updateCity = cityTable.getCity(Integer.parseInt(idNumInput.getText()));
                            if (updateCity != null) {
                                updateCity.setName(updateNameInput.getText());
                                cityTable.updateCity(updateCity);
                            }
                            updateNameInput.setText("");
                            loadRoomTable();
                        } else {
                            System.out.println("Invalid ID");
                        }
                    });

                    HBox updateCollection = new HBox(idNumGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(null), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });
                break;
        }
    }

    @Override
    protected void loadTable() {
        // Set active table to 0
        active = 0;
        title = new Label("Guardian - Student Relations");

        this.tableView = new TableView();
        // Grab table data
        GuardianTable guardians;
        ArrayList<Guardian> guardianList = new ArrayList<>();
        StudentTable students;
        ArrayList<Student> studentList = new ArrayList<>();

        try {
            relationTable = GuardianStudentRelationTable.getInstance();
            students = StudentTable.getInstance();
            guardians = GuardianTable.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        guardianList.addAll(guardians.getAllGuardians());
        studentList.addAll(students.getAllStudents());

        // Create Columns
        TableColumn<GuardianStudentRelation, String> columnId = new TableColumn<>("Rel. Id");
        columnId.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getId())));

        TableColumn<GuardianStudentRelation, String> column1 = new TableColumn<>("Guardian");
        column1.setCellValueFactory(e -> new SimpleStringProperty(findGuardian(guardianList, e.getValue().getGuardian_id())));

        TableColumn<GuardianStudentRelation, String> column2 = new TableColumn<>("Student");
        column2.setCellValueFactory(e -> new SimpleStringProperty(findStudent(studentList, e.getValue().getStudent_id())));


        this.tableView.getColumns().addAll(columnId, column1, column2);
        this.tableView.getItems().addAll(relationTable.getAllRelations());
        this.tableView.setStyle("");

        this.content.setCenter(this.tableView);
    }

    private void loadRoomTable() {
        // Set active table to 1
        active = 1;
        title = new Label("Room Table");

        this.tableView = new TableView();
        // Grab table data
        try {
            roomTable = RoomTable.getInstance();
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }


        // Create Columns
        TableColumn<Room, String> column1 = new TableColumn<>("Room ID");
        column1.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getId())));

        TableColumn<Room, String> column2 = new TableColumn<>("Room Name");
        column2.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getName())));


        this.tableView.getColumns().addAll(column1, column2);
        this.tableView.getItems().addAll(roomTable.getAllRooms());
        this.tableView.setStyle("");

        this.content.setCenter(this.tableView);
    }

    private void loadPosTable() {
        // Set active table to 2
        active = 2;
        title = new Label("Positions Table");

        this.tableView = new TableView();
        // Grab table data
        try {
            posTable = PositionTable.getInstance();
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }


        // Create Columns
        TableColumn<Position, String> column1 = new TableColumn<>("Position ID");
        column1.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getId())));

        TableColumn<Position, String> column2 = new TableColumn<>("Position Name");
        column2.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getName())));


        this.tableView.getColumns().addAll(column1, column2);
        this.tableView.getItems().addAll(posTable.getAllPositions());
        this.tableView.setStyle("");

        this.content.setCenter(this.tableView);
    }

    private void loadCityTable() {
        active = 3;
        title = new Label("Cities Table");
        this.tableView = new TableView();

        try {
            cityTable = CityTable.getInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        // Create Columns
        TableColumn<City, String> column1 = new TableColumn<>("City Id");
        column1.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getId())));

        TableColumn<City, String> column2 = new TableColumn<>("City Name");
        column2.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getName())));


        this.tableView.getColumns().addAll(column1, column2);
        this.tableView.getItems().addAll(cityTable.getAllCities());
        this.tableView.setStyle("");

        this.content.setCenter(this.tableView);
    }


    /**
     * Finds Guardian in array
     * @param list
     * @param id
     * @return student name
     */
    private String findStudent(ArrayList<Student> list, int id) {
        Student student = null;
        for (Student s: list) {
            if (s.getId() == id) {
                student = s;
            }
        }
        return student.toString();
    }

    /**
     * Finds Student in array
     * @param list
     * @param id
     * @return guardian name
     */
    private String findGuardian(ArrayList<Guardian> list, int id) {
        Guardian guardian = null;
        for (Guardian g: list) {
            if (g.getId() == id) {
                guardian = g;
            }
        }
        return guardian.toString();
    }
}
