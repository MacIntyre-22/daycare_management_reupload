package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;

import com.example.daycaremanagement.pojo.Guardian;
import com.example.daycaremanagement.tables.GuardianTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class GuardiansPage extends CrudOverlay {
    private static GuardiansPage instance;
    private Label title = new Label("Guardians");
    private GuardianTable guardians;


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


    private GuardiansPage() {
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
        tableView.setStyle("");

        this.content.setCenter(tableView);
    }

    @Override
    protected void loadInfo() {
        VBox pageInfo = new VBox();
        Label Guardianinfo=new Label("Information:\n Information about db content:\n Display Guardian info:\n Basic info:");

        Guardianinfo.setStyle("-fx-font-size: 15px; -fx-font-weight:light; -fx-padding: 5px 20px");
        content.setLeft(Guardianinfo);

        pageInfo.getChildren().addAll(Guardianinfo);
        this.content.setBottom(pageInfo);
    }
}
