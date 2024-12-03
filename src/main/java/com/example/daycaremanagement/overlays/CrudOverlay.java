package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.MainApp;
import com.example.daycaremanagement.pojo.*;
import com.example.daycaremanagement.tables.*;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/x.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/Room.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/Position.png")))),
            new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/daycaremanagement/icons/City.png"))))

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
    protected Button setEscape(String table) {
        // Create escape button
        Button esc = new Button();
        esc.setGraphic(setIcon(ICONS[7], 15));
        esc.setOnAction(e-> {
            loadInfo(table);
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
    protected void loadInfo(String table) {

        VBox pageInfo = new VBox();

        Label formInfo = new Label("Use the form icons below to add, update, or remove an item.");
        formInfo.setStyle("-fx-padding: 30 0 0 0;");

        Label bottomLabel = new Label("To refresh, select the Table button on the left navigation.");
        bottomLabel.setStyle("-fx-padding: 0 0 20 0;");

        pageInfo.getChildren().add(formInfo);

        if (table != null) {
            StudentTable studentTable = null;
            GuardianTable guardianTable = null;
            StaffTable staffTable = null;

            switch (table) {
                case "students":
                    try {
                        studentTable = StudentTable.getInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ArrayList<Student> students = studentTable.getAllStudents();

                    // Get Lowest and Highest age
                    int firstAge = getAge(students.get(0).getBirthdate());
                    int lowest = firstAge;
                    int highest = firstAge;
                    for (Student student : studentTable.getAllStudents()) {
                        int age = getAge(student.getBirthdate());
                        if (age > highest) {
                            highest = age;
                        }
                        if (age < lowest) {
                            lowest = age;
                        }
                    }

                    //Display info
                    pageInfo.getChildren().addAll(new Label("Amount of Students: "+students.size()), new Label("Oldest: "+highest), new Label("Youngest: "+lowest) );
                    break;

                case "guardians":
                    try {
                        guardianTable = GuardianTable.getInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ArrayList<Guardian> guardians = guardianTable.getAllGuardians();

                    //Display info
                    pageInfo.getChildren().addAll(new Label("Amount of Guardians: "+guardians.size()));
                    break;

                case "staff":
                    try {
                        staffTable = StaffTable.getInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ArrayList<Staff> staff = staffTable.getAllStaff();

                    // Get Lowest and Highest wage
                    double firstWage = staff.get(0).getWage();
                    double least = firstWage;
                    double most = firstWage;
                    for (Staff s : staffTable.getAllStaff()) {
                        double wage = s.getWage();
                        if (wage > most) {
                            most = wage;
                        }
                        if (wage < least) {
                            least = wage;
                        }
                    }

                    //Display info
                    pageInfo.getChildren().addAll(new Label("Amount of Staff: "+staff.size()), new Label("Highest Wage: "+most), new Label("Lowest Wage: "+least) );
                    break;
            }


        }else {
            pageInfo.getChildren().addAll(new Label("Can't get extra table information."));
        }

        this.content.setBottom(pageInfo);
    }


    // Tests if a string is an integer
    public boolean isInteger(String i){
        try{
            Integer.parseInt(i);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    // Tests if a string is a double
    public boolean isDouble(String i){
        try{
            Double.parseDouble(i);
            return true;
        } catch (Exception e1){
            return false;
        }
    }

    // Checks if a string is 10 digits long and an integer
    public boolean isValidPhone(String i){
        if (isInteger(i)){
            return i.length() == 10 && Integer.parseInt(i) >= 0;
        }
        return false;
    }

    public boolean isValidId(String input, String table){
        if (isInteger(input)){
            int intInput = Integer.parseInt(input);
            switch (table.toLowerCase()) {
                case "room" -> {
                    try {
                        return RoomTable.getInstance().getRoom(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                case "city" -> {
                    try {
                        return CityTable.getInstance().getCity(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                case "position" -> {
                    try {
                        return PositionTable.getInstance().getPosition(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                case "student" -> {
                    try {
                        return StudentTable.getInstance().getStudent(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                case "guardian" -> {
                    try {
                        return GuardianTable.getInstance().getGuardian(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                case "relation" -> {
                    try {
                        return GuardianStudentRelationTable.getInstance().getRelation(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                case "staff" -> {
                    try {
                        return StaffTable.getInstance().getStaff(intInput) != null;
                    } catch (Exception e) {
                        return false;
                    }
                }
                default -> {
                    System.out.println("Invalid table input. Options are: room, city, position, student, guardian, relation, and staff");
                    return false;
                }
            }
        }
        return false;
    }

    public double roundToTwo(double num){
        num*=100;
        num = (double) Math.round(num);
        num/= 100;
        return num;
    }

    public boolean isValidDateFormat(String date){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(date);
            Date currentDate = new Date();
            // min date of jan 1 2000, max date of today
            Date minDate = dateFormat.parse("2000-01-01");

            return parsedDate.equals(minDate) || parsedDate.after(minDate) && !parsedDate.after(currentDate);
        } catch (Exception e){
            return false;
        }
    }

    public boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // Checks if the wage is a double and is greater than or equal to 0
    public boolean isValidWage(String wage){
        return (isDouble(wage) && Double.parseDouble(wage) >= 0);
    }


    /**
     * Gets the students age by getting the difference between their birthdate and today.
     * @return age as double
     */
    protected int getAge(String dob) {
        // Get student birthday
        // YYYY-MM-DD
        String[] birthdaySplit = dob.split("-");
        LocalDate birthdayDate = LocalDate.of(Integer.parseInt(birthdaySplit[0]), Integer.parseInt(birthdaySplit[1]), Integer.parseInt(birthdaySplit[2]));
        LocalDate now = LocalDate.now();
        int age;

        // Get the difference to find age
        age = (int) ChronoUnit.YEARS.between(birthdayDate, now);
        // Return age
        return age;
    }

}
