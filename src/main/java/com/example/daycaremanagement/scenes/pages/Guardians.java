package com.example.daycaremanagement.scenes.pages;
import com.example.daycaremanagement.pojo.Guardian;
import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.tables.GuardianTable;
import com.example.daycaremanagement.tables.StaffTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Guardians extends BasePage {
    private static Guardians instance;
    private Label title = new Label("Guardians");
    private GuardianTable guardians;

    /**
     * Gets an instance of this class
     * @return the instance
     */
    public static Guardians getInstance(){
        if (instance == null){
            instance = new Guardians();
        }
        return instance;
    }


    private Guardians() {
        super();
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-padding: 5px 20px");
        content.setTop(title);
        loadTable();
    }

    @Override
    protected void sideButtonBar() {
        // Define actions specific to Guardians’ side buttons here
    }

    @Override
    protected void bottomButtonBar() {
        // Define actions specific to Guardians’ CRUD buttons here
    }

    @Override
    protected void loadTable() {
        this.tableView = new TableView();
        guardians = new GuardianTable();

        // Create Columns
        TableColumn<Guardian, String> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirst_name()));

        TableColumn<Guardian, String> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLast_name()));

        TableColumn<Guardian, String> column3 = new TableColumn<>("Phone");
        column3.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPhone()));

        TableColumn<Guardian, String> column4 = new TableColumn<>("Email");
        column4.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPhone()));

        TableColumn<Guardian, String> column5 = new TableColumn<>("City ID");
        column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getCity_id())));

        TableColumn<Guardian, String> column6 = new TableColumn<>("Street Number");
        column5.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getStreet_num())));

        TableColumn<Guardian, String> column7 = new TableColumn<>("Street Name");
        column4.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getStreet_name()));


        tableView.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7);
        tableView.getItems().addAll(guardians.getAllGuardians());

        this.content.setCenter(tableView);
    }
}
