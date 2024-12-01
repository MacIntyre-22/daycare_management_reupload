package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;
import com.example.daycaremanagement.pojo.*;
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
            TableColumn<Room, String> column1 = new TableColumn<>("Room Id");
            column1.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getId())));

            TableColumn<Room, String> column2 = new TableColumn<>("Room Name");
            column2.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getName())));


            tableView.getColumns().addAll(column1, column2);
            tableView.getItems().addAll(roomTable.getAllRooms());
            tableView.setStyle("");

            this.content.setCenter(tableView);
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
            TableColumn<Position, String> column1 = new TableColumn<>("Position Id");
            column1.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getId())));

            TableColumn<Position, String> column2 = new TableColumn<>("Position Name");
            column2.setCellValueFactory(e1 -> new SimpleStringProperty(String.valueOf(e1.getValue().getName())));


            tableView.getColumns().addAll(column1, column2);
            tableView.getItems().addAll(posTable.getAllPositions());
            tableView.setStyle("");

            this.content.setCenter(tableView);
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
                create.setOnAction(e-> {


                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{

                    });

                    HBox createCollection = new HBox();
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

                    Label columnName = new Label("Column");
                    ComboBox<String> columnNameChoice = new ComboBox<>();
                    // Temporary Options
                    // Grab Columns
                    columnNameChoice.getItems().addAll();
                    VBox columnNameGroup = new VBox(columnName, columnNameChoice);

                    Label updateName = new Label("New");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {

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
                create.setOnAction(e-> {


                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{

                    });

                    HBox createCollection = new HBox();
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

                    Label columnName = new Label("Column");
                    ComboBox<String> columnNameChoice = new ComboBox<>();
                    // Temporary Options
                    // Grab Columns
                    columnNameChoice.getItems().addAll();
                    VBox columnNameGroup = new VBox(columnName, columnNameChoice);

                    Label updateName = new Label("New");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {

                    });

                    HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
                    updateCollection.setSpacing(10);

                    VBox items = new VBox();
                    items.getChildren().addAll(setEscape(), updateCollection, updateInput);
                    items.setStyle("-fx-background-color: lightblue; -fx-padding: 15; -fx-spacing: 10");
                    this.content.setBottom(items);
                });

            // Forms for Position Table
            case 2:
                create.setOnAction(e-> {


                    Button createInput = new Button("Create!");
                    createInput.setOnAction(e1->{

                    });

                    HBox createCollection = new HBox();
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

                    Label columnName = new Label("Column");
                    ComboBox<String> columnNameChoice = new ComboBox<>();
                    // Temporary Options
                    // Grab Columns
                    columnNameChoice.getItems().addAll();
                    VBox columnNameGroup = new VBox(columnName, columnNameChoice);

                    Label updateName = new Label("New");
                    TextField updateNameInput = new TextField();
                    VBox updateNameGroup = new VBox(updateName, updateNameInput);

                    Button updateInput = new Button("Update!");
                    updateInput.setOnAction(e1-> {

                    });

                    HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
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
        title.setText("Single Parent - Student Relation");

        TableView tableView = new TableView();
        // Grab table data
        try {
            relationTable = GuardianStudentRelationTable.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Create Columns
        TableColumn<GuardianStudentRelation, String> column1 = new TableColumn<>("Parent Id");
        column1.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getGuardian_id())));

        TableColumn<GuardianStudentRelation, String> column2 = new TableColumn<>("Parent Id");
        column2.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getGuardian_id())));

        TableColumn<GuardianStudentRelation, String> column3 = new TableColumn<>("Student Id");
        column3.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getStudent_id())));


        tableView.getColumns().addAll(column1, column2, column3);
        tableView.getItems().addAll(relationTable.getAllRelations());
        tableView.setStyle("");

        this.content.setCenter(tableView);
    }
}
