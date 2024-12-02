package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.pojo.City;
import com.example.daycaremanagement.pojo.Position;
import com.example.daycaremanagement.pojo.Room;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import static com.example.daycaremanagement.MainApp.primaryStage;

public abstract class CrudOverlay extends StackPane {

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
    private Button navArrowButton = new Button();
    private ImageView navArrow;

    /**
     * This adds the different Bars into the scene with the content in the center
     */
    public CrudOverlay() {
        // Adding Styling Sheet
        this.getStylesheets().add(MainApp.class.getResource("Styles/CrudOverlay.css").toExternalForm());

        // Adding Image
        navArrow = new ImageView(new Image(MainApp.class.getResourceAsStream("icons/blackArrow.png")));
        navArrow.setFitWidth(30);
        navArrow.setFitHeight(30);

        // Styling Button / Positioning
        navArrowButton.setGraphic(navArrow);
        navArrowButton.setTranslateY(50);
        navArrowButton.setTranslateX(-10);
        navArrowButton.getStyleClass().add("ArrowButton");


        // Moving the pane of arrow button to the top right of screen
        StackPane arrow = new StackPane(navArrowButton);
        arrow.setTranslateX(-490);
        arrow.setTranslateY(-345);
        arrow.setMaxSize(30, 30);

        // Styling Content
        content.getStyleClass().add("content");
        content.bottomProperty().addListener(e->content.getBottom().setTranslateY(content.getBottom().getTranslateY()-20));

        // Making a new Borderpane to add the bottomBar to the bottom of the content
        BorderPane allContent = new BorderPane();
        allContent.setCenter(content);
        allContent.setBottom(createBottomBar());
        allContent.setMaxHeight(primaryStage.getScene().getHeight()-70);

        // Pane to Display all content
        StackPane displayContent = new StackPane(allContent);

        // Adding all panes to the main pane
        this.getChildren().addAll(displayContent, createSideBar(), arrow);
    }

    /**
     * This is the creation of the sideBar that has the graph Buttons inside
     * @return sideBar
     */
    private StackPane createSideBar() {
        NavButtons = new VBox(graph1, graph2, graph3, graph4);
        NavButtons.setAlignment(Pos.CENTER);
        NavButtons.getStyleClass().add("NavButtons");
        NavButtons.setMinWidth(70);

        sideButtonBar();

        StackPane displaySideBar = new StackPane(NavButtons);
        displaySideBar.setVisible(false);
        displaySideBar.setMaxWidth(0);

        // Animation the Nav into the scene When arrowButton is pressed
        navArrowButton.setOnMouseClicked(event -> {
            // If the Arrow is in resting position
            if(navArrowButton.getTranslateX() == -10) {
                // att - Arrow Translate Transition
                TranslateTransition att = new TranslateTransition(Duration.millis(300), navArrowButton);
                att.setFromX(-10);
                att.setToX(7.5);
                att.play();

                // afit - Arrow Fade In Transition
                FadeTransition afit = new FadeTransition(Duration.millis(150), navArrowButton);
                afit.setFromValue(100);
                afit.setToValue(0);
                afit.play();

                // Switching the Arrow color/Image
                navArrow = new ImageView(new Image(MainApp.class.getResourceAsStream("icons/whiteArrow.png")));
                navArrow.setFitHeight(30);
                navArrow.setFitWidth(30);
                navArrowButton.setGraphic(navArrow);

                // afot - Arrow Fade Out Transition
                FadeTransition afot = new FadeTransition(Duration.millis(150), navArrowButton);
                afot.setDelay(Duration.millis(100));
                afot.setFromValue(0);
                afot.setToValue(100);
                afot.play();

                // art - Arrow Rotate Transition
                RotateTransition art = new RotateTransition(Duration.millis(300), navArrowButton);
                art.setFromAngle(0);
                art.setToAngle(180);
                art.play();

                displaySideBar.setVisible(true);
                // sbft - Side Bar Fade Transition
                FadeTransition sbft = new FadeTransition(Duration.millis(300), displaySideBar);
                sbft.setFromValue(0.0);
                sbft.setToValue(1.0);
                sbft.play();

                // sbtt - Side Bar Translate Transition
                TranslateTransition sbtt = new TranslateTransition(Duration.millis(300), displaySideBar);
                sbtt.setFromX(-60);
                sbtt.setToX(0);
                sbtt.play();

            // If The ArrowButton is in a different location
            } else {
                // sbtt - Side Bar Translate Transition
                TranslateTransition sbtt = new TranslateTransition(Duration.millis(300), displaySideBar);
                sbtt.setFromX(0);
                sbtt.setToX(-60);
                sbtt.play();

                // sbft - Side Bar Fade Transition
                FadeTransition sbft = new FadeTransition(Duration.millis(300), displaySideBar);
                sbft.setOnFinished(e1 -> displaySideBar.setVisible(false));
                sbft.setFromValue(1.0);
                sbft.setToValue(0.0);
                sbft.play();

                // afit - Arrow Fade In Transition
                FadeTransition afit = new FadeTransition(Duration.millis(150), navArrowButton);
                afit.setFromValue(100);
                afit.setToValue(0);
                afit.play();

                // Switching the Arrow color/Image
                navArrow = new ImageView(new Image(MainApp.class.getResourceAsStream("icons/blackArrow.png")));
                navArrow.setFitHeight(30);
                navArrow.setFitWidth(30);
                navArrowButton.setGraphic(navArrow);

                // afot - Arrow Fade Out Transition
                FadeTransition afot = new FadeTransition(Duration.millis(150), navArrowButton);
                afot.setFromValue(0);
                afot.setToValue(100);
                afot.play();

                // att - Arrow Translate Transition
                TranslateTransition att = new TranslateTransition(Duration.millis(300), navArrowButton);
                att.setFromX(7.5);
                att.setToX(-10);
                att.play();

                // art - Arrow Rotate Transition
                RotateTransition art = new RotateTransition(Duration.millis(300), navArrowButton);
                art.setFromAngle(180);
                art.setToAngle(0);
                art.play();
            }
        });
        StackPane.setAlignment(displaySideBar, Pos.CENTER_LEFT);
        return displaySideBar;
    }

    /**
     * This is the creation of the Bottom bar with the main CRUD operations
     * @return crudButtons
     */
    protected HBox createBottomBar() {
        // Set Icons
        create.setGraphic(createBtn(setIcon(ICONS[3], 30), ""));
        update.setGraphic(createBtn(setIcon(ICONS[4], 30), ""));
        delete.setGraphic(createBtn(setIcon(ICONS[5], 30), ""));

        HBox crudButtons = new HBox(create, update, delete);
        bottomButtonBar();
        crudButtons.setAlignment(Pos.CENTER);
        crudButtons.getStyleClass().add("crudButtons");

        // Set Positioning
        crudButtons.setTranslateX((primaryStage.getScene().getWidth()/2)-150);
        crudButtons.setTranslateY(crudButtons.getTranslateY()-10);

        return crudButtons;
    }

    /**
     * Creates a button with an icon and text
     * @param icon ImageView
     * @param text String
     * @return Node with icon and text to put in a button
     */
    protected Node createBtn(ImageView icon, String text) {
        VBox layout = new VBox(icon, new Label(text));
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(2);
        if (Objects.equals(text, "")) {
            layout.getChildren().remove(1);
        }
        return layout;
    }

    /**
     * Sets the height and width of an image, Used mainly for setting icons.
     * @param icon ImageView
     * @param x double
     * @return ImageView set to given size
     */
    protected ImageView setIcon(ImageView icon, double x) {
        icon.setFitHeight(x);
        icon.setFitWidth(x);
        return icon;
    }

    /**
     * Creates an escape button for crud forms
     * @return a button that loadsInfo() on click
     */
    protected Button setEscape() {
        // Create escape button
        Button esc = new Button();
        esc.setGraphic(setIcon(ICONS[7], 15));
        esc.setOnAction(e1-> {
            loadInfo();
        });

        return esc;
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
    protected void loadInfo() {
        VBox pageInfo = new VBox();
        Label testInfo = new Label("Test info: Will hold information on table");
        testInfo.setStyle("-fx-padding: 30 0 0 0;");
        Label testInfo2 = new Label("Test info: Information like Table total, How many Students per room and etc.");
        pageInfo.getChildren().addAll(testInfo, testInfo2);
        this.content.setBottom(pageInfo);
    };


}
