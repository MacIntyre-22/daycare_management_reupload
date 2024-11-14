package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.BasePage;
import javafx.scene.control.Label;

public class Students extends BasePage {
    private static Students instance;
    private Label title = new Label("Students");

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
}
