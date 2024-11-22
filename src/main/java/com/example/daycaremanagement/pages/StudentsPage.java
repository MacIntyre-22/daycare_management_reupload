package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.GuardianStudentRelation;
import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.pojo.display.DisplayStaff;
import com.example.daycaremanagement.pojo.display.DisplayStudent;
import com.example.daycaremanagement.tables.GuardianStudentRelationTable;
import com.example.daycaremanagement.tables.StudentTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

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
        loadTable();
        loadInfo();
    }

    @Override
    protected void sideButtonBar() {
        // Define actions specific to Guardians’ side buttons here
    }

    @Override
    protected void bottomButtonBar() {
        // Define actions specific to Guardians’ CRUD buttons here


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
        TableColumn<DisplayStudent, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<DisplayStudent, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<DisplayStudent, String> column3 = new TableColumn<>("Birthday");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBirthdate()));

        TableColumn<DisplayStudent, String> column4 = new TableColumn<>("Room");
        column4.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRoom())));

        tableView.getColumns().addAll(column1, column2, column3, column4);
        tableView.getItems().addAll(students.getAllDisplayStudents());

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
