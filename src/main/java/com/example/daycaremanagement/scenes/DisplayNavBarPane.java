package com.example.daycaremanagement.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DisplayNavBarPane extends VBox {

    public DisplayNavBarPane() {
        this.setStyle("-fx-background-color: lightblue;");
        this.setSpacing(5);

        Label show = new Label("Show Display Options");
        Label display = new Label("Show Display Options");
        Label options = new Label("Show Display Options");
        Group displayNav = new Group(show, display, options);

        show.setRotate(-90);
        display.setRotate(-90);
        options.setRotate(-90);
        displayNav.setTranslateY(50);

        this.getChildren().add(displayNav);

        Label words = new Label("Hide Display Options");
        Label arrow = new Label("------------>");
        VBox hideNav = new VBox(words, arrow);

        hideNav.setMinWidth(125);
        hideNav.setAlignment(Pos.CENTER);
        hideNav.setTranslateY(-200);

        Button graph1 = new Button("Graph 1");
        Button graph2 = new Button("Graph 2");
        Button graph3 = new Button("Graph 3");
        Button graph4 = new Button("Graph 4");

        Button read = new Button("Read");
        Button update = new Button("Update");
        Button delete = new Button("Delete");

        Line line = new Line();
        line.setStartX(0);
        line.setEndX(75);
        line.setStrokeWidth(2);

        this.setOnMouseEntered(e-> {
            this.getChildren().remove(displayNav);
            this.setAlignment(Pos.CENTER);
            this.getChildren().addAll(hideNav, graph1, graph2, graph3, graph4, line, read, update, delete);
        });
        this.setOnMouseExited(e->{
            this.getChildren().removeAll(hideNav, graph1, graph2, graph3, graph4, line, read, update, delete);
            this.setAlignment(Pos.TOP_LEFT);
            this.getChildren().add(displayNav);
        });



    }
}
