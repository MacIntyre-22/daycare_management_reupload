package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import com.example.daycaremanagement.pojo.Guardian;
import com.example.daycaremanagement.pojo.GuardianStudentRelation;
import com.example.daycaremanagement.pojo.display.DisplayGuardian;
import com.example.daycaremanagement.tables.GuardianStudentRelationTable;
import com.example.daycaremanagement.tables.GuardianTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;


import java.sql.SQLException;

public class GuardiansPage extends CrudOverlay {
    private static GuardiansPage instance;
    private Label title = new Label("Guardians");
    private GuardianTable guardians;
    private RoomTable roomTable;
    private CityTable cityTable;
    private GuardianStudentRelationTable familyRelationTable;
    private StudentTable studentTable;

    // Pre Loaded Array of data
    ArrayList<City> cities = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();

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


    /**
     * This Pages Displays the Guardians Page.
     * With the Table data
     * and with the CRUD overlay
     * and a some low level table info at the bottom of the table
     */
    private GuardiansPage() {
        super();
        this.getStylesheets().add(MainApp.class.getResource("Styles/GuardiansPage.css").toExternalForm());
        title.getStyleClass().add("title");
        content.setTop(title);

        // Add rooms to array
        // Significantly increases the load speed and lagginess of the tableView
        try {
            roomTable = RoomTable.getInstance();
            rooms.addAll(roomTable.getAllRooms());
            cityTable = CityTable.getInstance();
            cities.addAll(cityTable.getAllCities());
        } catch (Exception e) {
            System.out.println("Error From: StudentsPage.java, line 56. Couldn't get Rooms Table.");
        }

        // Set Icons for buttons we use
        graph1.setGraphic(createBtn(setIcon(ICONS[0], 30), "Table"));
        graph2.setGraphic(createBtn(setIcon(ICONS[1], 30), "City"));
        graph3.setGraphic(createBtn(setIcon(ICONS[6], 30), "Rel."));

        loadTable();
        loadInfo("guardians");
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
            chart.setTitle("Guardians Location");

            // Grab Tables
            try {
                guardians = GuardianTable.getInstance();
                cityTable = CityTable.getInstance();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


            // Grab list of rooms
            ArrayList<City> cityArray = cityTable.getAllCities();
            ArrayList<PieChart.Data> data = new ArrayList<>();

            // Creat guardian list
            ArrayList<Guardian> guardianList = guardians.getAllGuardians();

            // Count how many guardians have city id equal to their city id
            for(City city : cityArray){
                double count = 0;

                // Check all guardians
                for (Guardian g : guardianList){
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
        graph3.setOnAction(ex -> {
            TableView relationTable = new TableView();
            // Grab table data
            try {
                guardians = GuardianTable.getInstance();
                familyRelationTable = GuardianStudentRelationTable.getInstance();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            // Create Columns
            TableColumn<Guardian, String> column1 = new TableColumn<>("First Name");
            column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

            TableColumn<Guardian, String> column2 = new TableColumn<>("Last Name");
            column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

            TableColumn<Guardian, String> column3 = new TableColumn<>("Children");
            column3.setCellValueFactory(e -> new SimpleStringProperty(getChildren(e.getValue().getId())));// Pass array of kids


            relationTable.getColumns().addAll(column1, column2, column3);
            relationTable.getItems().addAll(guardians.getAllGuardians());
            relationTable.setStyle("");

            this.content.setCenter(relationTable);
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
            fNameGroup.setAlignment(Pos.CENTER);

            Label lastName = new Label("Last Name");
            TextField lNameInput = new TextField();
            VBox lNameGroup = new VBox(lastName, lNameInput);
            lNameGroup.setAlignment(Pos.CENTER);

            Label phone = new Label("Phone");
            TextField phoneTF = new TextField();
            VBox phoneGroup = new VBox(phone, phoneTF);
            phoneGroup.setAlignment(Pos.CENTER);

            Label email = new Label("Email");
            TextField emailTF = new TextField();
            VBox emailGroup = new VBox(email, emailTF);
            emailGroup.setAlignment(Pos.CENTER);

            Label city = new Label("City Id");
            TextField cityTF = new TextField();
            VBox cityGroup = new VBox(city, cityTF);
            cityGroup.setAlignment(Pos.CENTER);

            Label streetNum = new Label("Street Number");
            TextField streetNumTF = new TextField();
            VBox streetNumGroup = new VBox(streetNum, streetNumTF);
            streetNumGroup.setAlignment(Pos.CENTER);

            Label streetName = new Label("Street name");
            TextField streetNameTF = new TextField();
            VBox streetNameGroup = new VBox(streetName, streetNameTF);
            streetNameGroup.setAlignment(Pos.CENTER);

            Button createInput = new Button("Create!");

            createInput.setOnAction(e1->{
                if (isValidId(cityTF.getText(), "city") && isInteger(streetNumTF.getText()) && isValidPhone(phoneTF.getText()) && isValidEmail(emailTF.getText())) {
                    Guardian createGuardian = new Guardian(0, fNameInput.getText(), lNameInput.getText(), phoneTF.getText(), emailTF.getText(), Integer.parseInt(cityTF.getText()), Integer.parseInt(streetNumTF.getText()), streetNameTF.getText());
                    guardians.createGuardian(createGuardian);
                    loadTable();
                } else {
                    System.out.println("Input error. Potential causes: Invalid City ID, non-numeric street number, invalid phone number, invalid email.");
                }
                fNameInput.setText("");
                lNameInput.setText("");
                phoneTF.setText("");
                emailTF.setText("");
                cityTF.setText("");
                streetNumTF.setText("");
                streetNameTF.setText("");
            });

            HBox escapeGroup = new HBox(setEscape("guardians"));
            escapeGroup.setAlignment(Pos.TOP_RIGHT);

            HBox createCollection = new HBox(fNameGroup, lNameGroup, emailGroup, phoneGroup, cityGroup, streetNumGroup, streetNameGroup);
            createCollection.setSpacing(10);
            createCollection.setAlignment(Pos.CENTER);

            createCollection.getChildren().forEach(node-> {
                node.setOnKeyPressed(keyEvent -> {
                    if (isValidId(cityTF.getText(), "city") && isInteger(streetNumTF.getText()) && isValidPhone(phoneTF.getText()) && isValidEmail(emailTF.getText())) {
                        Guardian createGuardian = new Guardian(0, fNameInput.getText(), lNameInput.getText(), phoneTF.getText(), emailTF.getText(), Integer.parseInt(cityTF.getText()), Integer.parseInt(streetNumTF.getText()), streetNameTF.getText());
                        guardians.createGuardian(createGuardian);
                        loadTable();
                    } else {
                        System.out.println("Input error. Potential causes: Invalid City ID, non-numeric street number, invalid phone number, invalid email.");
                    }
                    fNameInput.setText("");
                    lNameInput.setText("");
                    phoneTF.setText("");
                    emailTF.setText("");
                    cityTF.setText("");
                    streetNumTF.setText("");
                    streetNameTF.setText("");
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

            Label columnName = new Label("Column");
            ComboBox<String> columnNameChoice = new ComboBox<>();

            if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                DisplayGuardian getIdGuardian = (DisplayGuardian) this.tableView.getSelectionModel().getSelectedItems().get(0);
                idNumInput.setText(""+getIdGuardian.getId());
            }

            columnNameChoice.getItems().addAll("First Name", "Last Name", "Phone", "Email", "City ID", "Street Number", "Street Name");
            VBox columnNameGroup = new VBox(columnName, columnNameChoice);

            Label updateName = new Label("New");
            TextField updateNameInput = new TextField();
            VBox updateNameGroup = new VBox(updateName, updateNameInput);

            Button updateInput = new Button("Update!");
            updateInput.setOnAction(e1->{
                if (isInteger(idNumInput.getText())){
                    Guardian updateGuardian = guardians.getGuardian(Integer.parseInt(idNumInput.getText()));
                    if (updateGuardian != null) {
                        switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                            case ("First Name") -> updateGuardian.setFirst_name(updateNameInput.getText());
                            case ("Last Name") -> updateGuardian.setLast_name(updateNameInput.getText());
                            case ("Phone") -> {
                                if (isValidPhone(updateNameInput.getText())) {
                                    updateGuardian.setPhone(updateNameInput.getText());
                                } else {
                                    System.out.println("Please enter a valid 10 digit phone number, with no spaces or hyphens");
                                }
                            }
                            case ("Email") -> {
                                if (isValidEmail(updateNameInput.getText())){
                                    updateGuardian.setEmail(updateNameInput.getText());
                                } else {
                                    System.out.println("Invalid email");
                                }
                            }

                            case ("City ID") -> {
                                if (isValidId(updateNameInput.getText(), "city")) {
                                    updateGuardian.setCity_id(Integer.parseInt(updateNameInput.getText()));
                                } else {
                                    System.out.println("Invalid City ID");
                                }
                            }
                            case ("Street Number") -> {
                                if (isInteger(updateNameInput.getText())) {
                                    updateGuardian.setStreet_num(Integer.parseInt(updateNameInput.getText()));
                                } else {
                                    System.out.println("Input error, enter a number");
                                }
                            }
                            case ("Street Name") -> updateGuardian.setStreet_name(updateNameInput.getText());
                            default -> System.out.println("Category not selected");
                        }
                        updateNameInput.setText("");
                        guardians.updateGuardian(updateGuardian);
                        loadTable();
                    } else {
                        System.out.println("Specified ID does not exist");
                    }
                } else {
                    System.out.println("Invalid ID");
                }
            });

            HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
            updateCollection.setSpacing(10);
            updateCollection.setAlignment(Pos.CENTER);

            HBox escapeGroup = new HBox(setEscape("guardians"));
            escapeGroup.setAlignment(Pos.TOP_RIGHT);

            updateCollection.getChildren().forEach(node-> {
                node.setOnKeyPressed(keyEvent -> {
                    if (isInteger(idNumInput.getText())){
                        Guardian updateGuardian = guardians.getGuardian(Integer.parseInt(idNumInput.getText()));
                        if (updateGuardian != null) {
                            switch (columnNameChoice.getSelectionModel().getSelectedItem()) {
                                case ("First Name") -> updateGuardian.setFirst_name(updateNameInput.getText());
                                case ("Last Name") -> updateGuardian.setLast_name(updateNameInput.getText());
                                case ("Phone") -> {
                                    if (isValidPhone(updateNameInput.getText())) {
                                        updateGuardian.setPhone(updateNameInput.getText());
                                    } else {
                                        System.out.println("Please enter a valid 10 digit phone number, with no spaces or hyphens");
                                    }
                                }
                                case ("Email") -> {
                                    if (isValidEmail(updateNameInput.getText())){
                                        updateGuardian.setEmail(updateNameInput.getText());
                                    } else {
                                        System.out.println("Invalid email");
                                    }
                                }

                                case ("City ID") -> {
                                    if (isValidId(updateNameInput.getText(), "city")) {
                                        updateGuardian.setCity_id(Integer.parseInt(updateNameInput.getText()));
                                    } else {
                                        System.out.println("Invalid City ID");
                                    }
                                }
                                case ("Street Number") -> {
                                    if (isInteger(updateNameInput.getText())) {
                                        updateGuardian.setStreet_num(Integer.parseInt(updateNameInput.getText()));
                                    } else {
                                        System.out.println("Input error, enter a number");
                                    }
                                }
                                case ("Street Name") -> updateGuardian.setStreet_name(updateNameInput.getText());
                                default -> System.out.println("Category not selected");
                            }
                            updateNameInput.setText("");
                            guardians.updateGuardian(updateGuardian);
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

        // Deletes the guardian and all of their relations
        // Does not delete the students connected to them (in-case the student has other guardians)
        delete.setOnAction(e->{
            if (!this.tableView.getSelectionModel().getSelectedItems().isEmpty()) {
                DisplayGuardian deleteGuardian = (DisplayGuardian) this.tableView.getSelectionModel().getSelectedItems().get(0);
                try {
                    ArrayList<GuardianStudentRelation> deleteRelations = GuardianStudentRelationTable.getInstance().getRelationByEitherId(deleteGuardian.getId(), true);
                    for (GuardianStudentRelation deleteRelation : deleteRelations) {
                        GuardianStudentRelationTable.getInstance().deleteRelation(deleteRelation);
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

                guardians.deleteGuardian(guardians.getGuardian(deleteGuardian.getId()));
                loadTable();
            }
        });
    }

    @Override
    protected void loadTable() {
        this.tableView = new TableView();
        try {
            guardians = GuardianTable.getInstance();
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not get table.");
        }

        // Create Columns
        TableColumn<DisplayGuardian, String> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getId())));

        TableColumn<DisplayGuardian, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<DisplayGuardian, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<DisplayGuardian, String> column3 = new TableColumn<>("Phone");
        column3.setCellValueFactory(e -> new SimpleStringProperty(String.format("(%s) %s-%s", e.getValue().getPhone().substring(0, 3), e.getValue().getPhone().substring(3, 6),
                e.getValue().getPhone().substring(6, 10))));

        TableColumn<DisplayGuardian, String> column4 = new TableColumn<>("Email");
        column4.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEmail()));

        TableColumn<DisplayGuardian, String> column5 = new TableColumn<>("City");
        column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getCity())));

        TableColumn<DisplayGuardian, String> column6 = new TableColumn<>("Street Number");
        column6.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getStreet_num())));

        TableColumn<DisplayGuardian, String> column7 = new TableColumn<>("Street Name");
        column7.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getStreet_name()));


        tableView.getColumns().addAll(columnId, column1, column2, column3, column4, column5, column6, column7);
        tableView.getItems().addAll(guardians.getAllDisplayGuardians());
        tableView.setStyle("");

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
     * Finds all the students that is related to the guardian id given.
     * @param guardianId int
     * @return a list as a String of student's names
     */
    private String getChildren(int guardianId) {
        try {
            familyRelationTable = GuardianStudentRelationTable.getInstance();
            studentTable = StudentTable.getInstance();
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
