package com.example.daycaremanagement.pages;
import com.example.daycaremanagement.overlays.CrudOverlay;
import javafx.scene.control.Label;

public class GuardiansPage extends CrudOverlay {
    private static GuardiansPage instance;
    private Label title = new Label("Guardians");

    private Label Guardianinfo=new Label("Information:\n Information about db content:\n Display Guardian info:\n Basic info:");


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
        title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-padding: 5px 20px");
        content.setTop(title);

        Guardianinfo.setStyle("-fx-font-size: 15px; -fx-font-weight:light; -fx-padding: 5px 20px");
        content.setLeft(Guardianinfo);

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
