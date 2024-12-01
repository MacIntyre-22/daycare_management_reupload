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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class OtherTablesPage extends CrudOverlay {
    // DF
    private static OtherTablesPage instance;
    private Label title = new Label("Other Tables");

    // Active table
    int active = 0;

    private PositionTable posTable;
    private RoomTable roomTable;
    private CityTable cityTable;
    private GuardianStudentRelationTable relationTable;


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
        content.setTop(title);

        // Set Icons for buttons we use
        graph1.setGraphic(createBtn(setIcon(ICONS[0], 30), "Rel."));
        graph2.setGraphic(createBtn(setIcon(ICONS[0], 30), "Room"));
        graph3.setGraphic(createBtn(setIcon(ICONS[0], 30), "Pos."));

        loadTable();
        loadInfo();
    }

    @Override
    protected void sideButtonBar() {
        // Load Relation Tbale
        graph1.setOnAction(e->{
            loadTable();

            // Reset Form
            this.setBottom(createBottomBar());
        });

        // load Room Table
        graph2.setOnAction(e->{
            // Set active table to 1
            active = 1;
            title.setText("Room Table");

            TableView tableView = new TableView();
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


            tableView.getColumns().addAll(column1, column2);
            tableView.getItems().addAll(roomTable.getAllRooms());
            tableView.setStyle("");

            this.content.setCenter(tableView);

            // Reset Form
            this.setBottom(createBottomBar());
        });

        // Position Table
        graph3.setOnAction(ex -> {
            // Set active table to 2
            active = 2;
            title.setText("Positions Table");

            TableView tableView = new TableView();
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


            tableView.getColumns().addAll(column1, column2);
            tableView.getItems().addAll(posTable.getAllPositions());
            tableView.setStyle("");

            this.content.setCenter(tableView);

            // Reset Form
            this.setBottom(createBottomBar());
        });

        // Remove buttons here
        NavButtons.getChildren().remove(graph4);
    }

    @Override
    protected void bottomButtonBar() {
        // Conditions for forms depending on what table is selected
        switch (active) {
            // Forms for Relation Table
            case 0:

                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        GuardianStudentRelation deleteRelation = (GuardianStudentRelation) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        relationTable.deleteRelation(deleteRelation);
                        loadTable();
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
                        if (isInteger(parentIdInput.getText()) && isInteger(studentIdInput.getText())) {
                            GuardianStudentRelation createRelation = new GuardianStudentRelation(0, Integer.parseInt(parentIdInput.getText()), Integer.parseInt(studentIdInput.getText()));
                            relationTable.createRelation(createRelation);
                        } else {
                            System.out.println("One or more IDs were not numeric");
                        }
                        parentIdInput.setText("");
                        studentIdInput.setText("");
                        loadTable();
                    });

                    HBox createCollection = new HBox(parentIdGroup, studentIdGroup);
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
                        GuardianStudentRelation updateRelation = relationTable.getRelation(Integer.parseInt(idNumInput.getText()));
                        if (isInteger(updateNameInput.getText()) && updateRelation != null){
                            switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                                case "Parent ID" ->
                                        updateRelation.setGuardian_id(Integer.parseInt(updateNameInput.getText()));
                                case "Student ID" ->
                                        updateRelation.setStudent_id(Integer.parseInt(updateNameInput.getText()));
                                default -> System.out.println("");
                            }
                            relationTable.updateRelation(updateRelation);
                        } else {
                            System.out.println("ID was not numeric or specified relation does not exist");
                        }
                        updateNameInput.setText("");
                        loadTable();
                    });

                    HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

            // Forms for Rooms Table
            case 1:
                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        Room deleteRoom = (Room) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        roomTable.deleteRoom(deleteRoom);
                        loadTable();
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
                        loadTable();
                    });

                    HBox createCollection = new HBox(nameGroup);
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

                    Label updateName = new Label("New Room name");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {
                        Room updateRoom = roomTable.getRoom(Integer.parseInt(idNumInput.getText()));
                        if (updateRoom != null) {
                            updateRoom.setName(updateNameInput.getText());
                            roomTable.updateRoom(updateRoom);
                        }
                        updateNameInput.setText("");
                        loadTable();
                    });

                    HBox updateCollection = new HBox(idNumGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

            // Forms for Position Table
            case 2:
                delete.setOnAction(e->{
                    if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                        Position deletePosition = (Position) this.tableView.getSelectionModel().getSelectedItems().get(0);
                        posTable.deletePosition(deletePosition);
                        loadTable();
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
                        loadTable();
                    });

                    HBox createCollection = new HBox(nameGroup);
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


                    Label updateName = new Label("New Position name");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {
                        Position updatePosition = posTable.getPosition(Integer.parseInt(idNumInput.getText()));
                        if (updatePosition != null){
                            updatePosition.setName(updateNameInput.getText());
                            posTable.updatePosition(updatePosition);
                        }
                        updateNameInput.setText("");
                        loadTable();
                    });

                    HBox updateCollection = new HBox(idNumGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });


        }
    }

    @Override
    protected void loadTable() {
        // Set active table to 0
        active = 0;
        title.setText("Guardian - Student Relations");

        TableView tableView = new TableView();
        // Grab table data
        try {
            relationTable = GuardianStudentRelationTable.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Create Columns

        // TODO: update to showing them by name
        TableColumn<GuardianStudentRelation, String> column1 = new TableColumn<>("Parent Id");
        column1.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getGuardian_id())));

        TableColumn<GuardianStudentRelation, String> column2 = new TableColumn<>("Student Id");
        column2.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getStudent_id())));


        tableView.getColumns().addAll(column1, column2);
        tableView.getItems().addAll(relationTable.getAllRelations());
        tableView.setStyle("");

        this.content.setCenter(tableView);
    }
}
