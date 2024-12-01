package com.example.daycaremanagement.pages;

import com.example.daycaremanagement.overlays.CrudOverlay;
import com.example.daycaremanagement.tables.CityTable;
import com.example.daycaremanagement.tables.GuardianStudentRelationTable;
import com.example.daycaremanagement.tables.PositionTable;
import com.example.daycaremanagement.tables.RoomTable;
import javafx.scene.control.Label;

public class OtherTablesPage extends CrudOverlay {
    // DF
    private static OtherTablesPage instance;
    private Label title = new Label("Other Tables");
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

    @Override
    protected void sideButtonBar() {

    }

    @Override
    protected void bottomButtonBar() {

    }

    @Override
    protected void loadTable() {

    }
}
