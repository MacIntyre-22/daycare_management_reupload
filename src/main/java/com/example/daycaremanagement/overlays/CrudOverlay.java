package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.pojo.Room;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class CrudOverlay extends BorderPane {

    // Table
    protected TableView tableView;

    // Shared buttons
    protected Button graph1 = new Button("Table");
    protected Button graph2 = new Button("Pie");
    protected Button graph3 = new Button("Bar");
    protected Button graph4 = new Button("Bubble");

    protected Button create = new Button("Create");
    protected Button read = new Button("Read");
    protected Button update = new Button("Update");
    protected Button delete = new Button("Delete");

    protected BorderPane content = new BorderPane();

    public CrudOverlay() {
        this.setLeft(createSideBar());
        this.setBottom(createBottomBar());
        this.setCenter(content);
        content.setStyle("-fx-padding: 10px 50px 50px 50px;");
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
        stylebtns();
        NavButtons.setAlignment(Pos.CENTER);
        NavButtons.setSpacing(20);

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

    private void stylebtns() {
        graph1.setStyle("-fx-min-width: 100px");
        graph2.setStyle("-fx-min-width: 100px");
        graph3.setStyle("-fx-min-width: 100px");
        graph4.setStyle("-fx-min-width: 100px");
    }

    private HBox createBottomBar() {
        HBox crudButtons = new HBox(create, read, update, delete);
        bottomButtonBar();
        crudButtons.setAlignment(Pos.CENTER);
        crudButtons.setMinHeight(50);
        crudButtons.setMinWidth(100);
        crudButtons.setStyle("-fx-background-color: lightblue;");
        crudButtons.setSpacing(50);
        return crudButtons;
    }

    protected String getRoomName(ArrayList<Room> rooms, int roomId) {
        String roomName = "";
        for (Room room : rooms) {
            if (roomId == room.getId()) {
                roomName = room.getName();
            }
        }
        return roomName;
    }

    // Abstract methods for subclasses to define specific behavior\

    /**
     * Sets the actions and styling for the pop out buttons on the page.
     */
    protected abstract void sideButtonBar();

    /**
     * Sets the actions and styling for the crud buttons on bottom of the page.
     */
    protected abstract void bottomButtonBar();

    /**
     * Loads the table for that page into the content view.
     */
    protected abstract void loadTable();

    /**
     * Loads basic info about the table to the page.
     */
    protected abstract void loadInfo();


}
