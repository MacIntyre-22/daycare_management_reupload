package com.example.daycaremanagement.scenes.pages;
import javafx.scene.control.Label;

public class Students extends BasePage {
    private static Students instance;

    /**
     * Gets an instance of this class
     * @return the instance
     */
    public static Students getInstance(){
        if (instance == null){
            instance = new Students();
        }
        return instance;
    }


    private Students() {
        super();
        content.setTop(new Label("Students"));
    }

    @Override
    protected void sideButtonBar() {
        // Define actions specific to Guardians’ side buttons here
    }

    @Override
    protected void bottomButtonBar() {
        // Define actions specific to Guardians’ CRUD buttons here
    }
}
