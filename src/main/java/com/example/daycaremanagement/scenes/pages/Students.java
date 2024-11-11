package com.example.daycaremanagement.scenes.pages;

import com.example.daycaremanagement.interfaces.SideBar;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Students extends BorderPane implements SideBar {
    private static Students instance;
    private BorderPane content = new BorderPane();

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
        this.setLeft(sideBar());
        this.setBottom(bottomBar());
        content.setTop(new Label("Students"));
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
