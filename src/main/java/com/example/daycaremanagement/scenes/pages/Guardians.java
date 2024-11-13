package com.example.daycaremanagement.scenes.pages;
import javafx.scene.control.Label;

public class Guardians extends BasePage {
    private static Guardians instance;
    private Label title = new Label("Guardians");

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

    }
}
