package com.example.daycaremanagement;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface TemporaryCrudOverlay {
    // We could probably move these buttons into read button
    Button graph1 = new Button("Graph 1");
    Button graph2 = new Button("Graph 2");
    Button graph3 = new Button("Graph 3");
    Button graph4 = new Button("Graph 4");


    Button create = new Button("Create");
    Button read = new Button("Read");
    Button update = new Button("Update");
    Button delete = new Button("Delete");

    /**
     * This is the VBox that is called sidebar that has the graph button inside
     * @return a VBox that is called sidebar
     */
    default VBox sideBar() {

    // The Closed Nav

        // I had to add closedNavWords into a group to make the Nav small
        // If I make it without the group the Closed Nav is wider than it opened
        Label closedNavWords = new Label("Show Display Options");
        closedNavWords.setRotate(-90);
        Group closedNav = new Group(closedNavWords);
        closedNav.setTranslateY(50);

    // The Opened Nav

        // This is all the objects in the top of the nav
        Label openedNavWords = new Label("Hide Display Options");
        Label arrow = new Label("------------>"); // This doesn't have to be final, if you want to make it an image. cool

        // Some basic styling for the top of Nav
        VBox TopNavGroup = new VBox(openedNavWords, arrow);
        TopNavGroup.setMinWidth(125);
        TopNavGroup.setAlignment(Pos.CENTER);
        TopNavGroup.setTranslateY(-200);

        // All the button getting added into a group of all the button inside the sideNav
        VBox NavButtons = new VBox(graph1, graph2, graph3, graph4);
        NavButtons.setAlignment(Pos.CENTER);
        NavButtons.setSpacing(5);

        // Calling the function to all the button actions
        sideButtonBar();


    // Sidebar Nav
        VBox sideBar = new VBox();
        // The Styling
        sideBar.setStyle("-fx-background-color: lightblue;");
        sideBar.setSpacing(5);
        sideBar.getChildren().add(closedNav);

        // When the user Hovers their mouse over the Nav
        //  It opens the Nav to show the buttons to different graphs
        sideBar.setOnMouseEntered(event-> {
            sideBar.getChildren().remove(closedNav);
            sideBar.getChildren().addAll(TopNavGroup, NavButtons);
            sideBar.setAlignment(Pos.CENTER);
        });
        // When the user is not Hovering their mouse over the Nav
        // It closes the Nav and goes back to default
        sideBar.setOnMouseExited(e->{
            sideBar.getChildren().removeAll(TopNavGroup, NavButtons);
            sideBar.setAlignment(Pos.TOP_LEFT);
            sideBar.getChildren().add(closedNav);
        });


        return sideBar;
    }

    /**
     * This is a Hbox with the CRUD actions inside
     * @return the Hbox with the CRUD button inside
     */
    default HBox bottomBar() {
        HBox crudButtons = new HBox(create, read, update, delete);

        bottomButtonBar();

        crudButtons.setAlignment(Pos.TOP_CENTER);
        crudButtons.setMinHeight(50);
        crudButtons.setStyle("-fx-background-color: lightblue;");
        crudButtons.setSpacing(50);

        return crudButtons;
    }



    /**
     *To Perform the action when the side buttons are clicked
     */
    void sideButtonBar();

    /**
     * To perform the action when the bottom buttons are clicked
     */
    void bottomButtonBar();
}
