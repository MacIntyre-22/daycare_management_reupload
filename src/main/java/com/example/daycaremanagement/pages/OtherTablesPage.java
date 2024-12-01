package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;
import com.example.daycaremanagement.pojo.Guardian;
import com.example.daycaremanagement.pojo.GuardianStudentRelation;
import com.example.daycaremanagement.tables.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

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
        graph2.setGraphic(createBtn(setIcon(ICONS[1], 30), "Room"));
        graph3.setGraphic(createBtn(setIcon(ICONS[2], 30), "Pos."));

        loadTable();
        loadInfo();
    }

    @Override
    protected void sideButtonBar() {

    }

    @Override
    protected void bottomButtonBar() {

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

        TableColumn<GuardianStudentRelation, String> column2 = new TableColumn<>("Student Id");
        column2.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getStudent_id())));


        tableView.getColumns().addAll(column1, column2);
        tableView.getItems().addAll(relationTable.getAllRelations());
        tableView.setStyle("");

        this.content.setCenter(tableView);
    }
}
