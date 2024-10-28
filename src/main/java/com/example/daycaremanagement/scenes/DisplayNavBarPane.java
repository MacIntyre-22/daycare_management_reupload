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

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(graph1, graph2, graph3, graph4, line, read, update, delete);





    }
}
