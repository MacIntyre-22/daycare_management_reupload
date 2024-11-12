package com.example.daycaremanagement.pages;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class BasePage extends BorderPane {

    // Shared buttons
    protected Button graph1 = new Button("Graph 1");
    protected Button graph2 = new Button("Graph 2");
    protected Button graph3 = new Button("Graph 3");
    protected Button graph4 = new Button("Graph 4");

    protected Button create = new Button("Create");
    protected Button read = new Button("Read");
    protected Button update = new Button("Update");
    protected Button delete = new Button("Delete");

    protected BorderPane content = new BorderPane();

    public BasePage() {
        this.setLeft(createSideBar());
        this.setBottom(createBottomBar());
        this.setCenter(content);
    }

    private VBox createSideBar() {
        Label closedNavWords = new Label("Show Display Options");
        closedNavWords.setRotate(-90);
        Group closedNav = new Group(closedNavWords);
        closedNav.setTranslateY(50);

        Label openedNavWords = new Label("Hide Display Options");
        Label arrow = new Label("------------>");
        VBox TopNavGroup = new VBox(openedNavWords, arrow);
        TopNavGroup.setMinWidth(125);
        TopNavGroup.setAlignment(Pos.CENTER);
        TopNavGroup.setTranslateY(-200);

        VBox NavButtons = new VBox(graph1, graph2, graph3, graph4);
        NavButtons.setAlignment(Pos.CENTER);
        NavButtons.setSpacing(5);

        sideButtonBar();

        VBox sideBar = new VBox();
        sideBar.setStyle("-fx-background-color: lightblue;");
        sideBar.setSpacing(5);
        sideBar.getChildren().add(closedNav);

        sideBar.setOnMouseEntered(event -> {
            sideBar.getChildren().remove(closedNav);
            sideBar.getChildren().addAll(TopNavGroup, NavButtons);
            sideBar.setAlignment(Pos.CENTER);
        });

        sideBar.setOnMouseExited(e -> {
            sideBar.getChildren().removeAll(TopNavGroup, NavButtons);
            sideBar.setAlignment(Pos.TOP_LEFT);
            sideBar.getChildren().add(closedNav);
        });

        return sideBar;
    }

    private HBox createBottomBar() {
        HBox crudButtons = new HBox(create, read, update, delete);
        bottomButtonBar();
        crudButtons.setAlignment(Pos.TOP_CENTER);
        crudButtons.setMinHeight(50);
        crudButtons.setStyle("-fx-background-color: lightblue;");
        crudButtons.setSpacing(50);
        return crudButtons;
    }

    // Abstract methods for subclasses to define specific behavior
    protected abstract void sideButtonBar();
    protected abstract void bottomButtonBar();
}
