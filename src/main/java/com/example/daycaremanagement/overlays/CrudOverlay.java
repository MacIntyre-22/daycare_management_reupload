package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.pojo.City;
import com.example.daycaremanagement.pojo.Position;
import com.example.daycaremanagement.pojo.Room;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Objects;

public abstract class CrudOverlay extends BorderPane {

    // Table
    protected TableView tableView;

    // Shared buttons
    protected Button graph1 = new Button();
    protected Button graph2 = new Button();
    protected Button graph3 = new Button();
    protected Button graph4 = new Button();

    // Can be accessed by child classes now
    protected VBox NavButtons;

    protected Button create = new Button();
    protected Button read = new Button();
    protected Button update = new Button();
    protected Button delete = new Button();

    // Icons
    public final ImageView[] ICONS = {
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/table.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/pie.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/bar.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/user_plus.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/user_check.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/user_x.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/relation.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/x.png"))))
    };


    protected BorderPane content = new BorderPane();

    /**
     * This adds the different Bars into the scene with the content in the center
     */
    public CrudOverlay() {
        this.setLeft(createSideBar());
        this.setBottom(createBottomBar());
        this.setCenter(content);
        content.setStyle("-fx-padding: 10px 50px 0px 50px;");
    }

    /**
     * This is the creation of the sideBar that has the graph Buttons inside
     * @return sideBar
     */
    private VBox createSideBar() {
        Label closedNavWords = new Label("Show Display Options");
        closedNavWords.setRotate(-90);
        Group closedNav = new Group(closedNavWords);
        closedNav.setTranslateY(50);


        NavButtons = new VBox(graph1, graph2, graph3, graph4);
        NavButtons.setAlignment(Pos.CENTER);
        NavButtons.setSpacing(20);

        sideButtonBar();

        // Create Side bar
        VBox sideBar = new VBox();
        sideBar.setStyle("-fx-padding: 50px 0 0 0; -fx-background-color: lightblue;");
        sideBar.getChildren().add(closedNav);


        // Removed Closed Nav text
        sideBar.setOnMouseEntered(event -> {
            sideBar.getChildren().remove(closedNav);
            sideBar.setMinWidth(60);
            sideBar.getChildren().addAll(NavButtons);
            sideBar.setAlignment(Pos.TOP_CENTER);
        });

        // Removed Closed Nav text
        sideBar.setOnMouseExited(e -> {
            sideBar.getChildren().removeAll(NavButtons);
            sideBar.setMinWidth(0);
            sideBar.setAlignment(Pos.TOP_LEFT);
            sideBar.getChildren().add(closedNav);
        });

        return sideBar;
    }

    /**
     * This is the creation of the Bottom bar with the main CRUD operations
     * @return crudButtons
     */
    private HBox createBottomBar() {
        HBox crudButtons = new HBox(create, read, update, delete);
    protected HBox createBottomBar() {
        // Set Icons
        create.setGraphic(setIcon(ICONS[3], 30));
        update.setGraphic(setIcon(ICONS[4], 30));
        // Removed delete
        //delete.setGraphic(setIcon(ICONS[5], 30));

        HBox crudButtons = new HBox(create, update);
        bottomButtonBar();
        crudButtons.setAlignment(Pos.CENTER);
        crudButtons.setMinHeight(50);
        crudButtons.setMinWidth(100);
        crudButtons.setStyle("-fx-background-color: lightblue;");
        crudButtons.setSpacing(50);
        return crudButtons;
    }

    protected ImageView setIcon(ImageView icon, double x) {
        icon.setFitHeight(x);
        icon.setFitWidth(x);
        return icon;
    }

    protected Button setEscape() {
        // Create escape button
        Button esc = new Button();
        esc.setGraphic(setIcon(ICONS[7], 15));
        esc.setOnAction(e1-> {
            loadInfo();
        });

        return esc;
    }

    /**
     * Converts a Room id to a Room name
     * @param rooms ArrayList<Room>
     * @param roomId int
     * @return a string of the name, of the room id given
     */
    protected String getRoomName(ArrayList<Room> rooms, int roomId) {
        String roomName = "";
        for (Room room : rooms) {
            if (roomId == room.getId()) {
                roomName = room.getName();
            }
        }
        return roomName;
    }

    /**
     * Converts a City id to a City name
     * @param cities ArrayList<City>
     * @param cityId int
     * @return a string of the name, of the city id given
     */
    protected String getCityName(ArrayList<City> cities, int cityId) {
        String cityName = "";
        for (City city : cities) {
            if (cityId == city.getId()) {
                cityName = city.getName();
                return cityName;
            }
        }
        return cityName;
    }


    /**
     * Converts a Position id to a Position name
     * @param positions ArrayList<Position>
     * @param positionId int
     * @return a string of the name, of the position id given
     */
    protected String getPositionName(ArrayList<Position> positions, int positionId) {
        String posName = "";
        for (Position pos : positions) {
            if (positionId == pos.getId()) {
                posName = pos.getName();
                return posName;
            }
        }
        return posName;
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
     *  Creates the Table
     *  Adding the columns to the display
     *  and all the data under the corresponding
     */
    protected abstract void loadTable();

    /**
     * Loads basic info about the table to the page.
     */
    protected abstract void loadInfo();


}
