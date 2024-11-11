package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.interfaces.SideBar;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Guardians extends BorderPane implements SideBar {

    private static Guardians instance;
    private BorderPane content = new BorderPane();

    /**
     * Gets an instance of this student test class
     * @return the instance
     */
    public static Guardians getInstance(){
        if (instance == null){
            instance = new Guardians();
        }
        return instance;
    }
    private Guardians() {
        this.setLeft(sideBar());
        this.setBottom(bottomBar());
        content.setTop(new Label("Guardians"));
        this.setCenter(content);
    }


    @Override
    public VBox sideBar() {
        return SideBar.super.sideBar();
    }

    @Override
    public HBox bottomBar() {
        return SideBar.super.bottomBar();
    }

    @Override
    public void sideButtonBar() {

    }

    @Override
    public void bottomButtonBar() {

    }
}
