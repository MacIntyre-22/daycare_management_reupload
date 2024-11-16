package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;
import javafx.scene.control.Label;

public class StudentsPage extends CrudOverlay {
    private static StudentsPage instance;
    private Label title = new Label("Students");

    private Label info=new Label("Information:\n Information about db content:\n Display Student info:\n Basic info:");
     private Label student=new Label("Information about db content");
   private  Label basicinfo=new Label("Display Student info:");









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


    private StudentsPage() {
        super();
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-padding: 5px 20px");
        content.setTop(title);

        info.setStyle("-fx-font-size: 15px; -fx-font-weight:light; -fx-padding: 5px 20px");
        content.setLeft(info);


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
