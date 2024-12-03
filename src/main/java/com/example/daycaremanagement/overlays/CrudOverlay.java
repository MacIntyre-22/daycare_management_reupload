package com.example.daycaremanagement.overlays;

import com.example.daycaremanagement.pojo.Guardian;
import com.example.daycaremanagement.pojo.Staff;
import com.example.daycaremanagement.pojo.Student;
import com.example.daycaremanagement.tables.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected HBox createBottomBar() {
        // Set Icons
        create.setGraphic(createBtn(setIcon(ICONS[3], 30), ""));
        update.setGraphic(createBtn(setIcon(ICONS[4], 30), ""));
        delete.setGraphic(createBtn(setIcon(ICONS[5], 30), ""));

        HBox crudButtons = new HBox(create, update, delete);
        bottomButtonBar();
        crudButtons.setAlignment(Pos.CENTER);
        crudButtons.setMinHeight(50);
        crudButtons.setMinWidth(100);
        crudButtons.setStyle("-fx-background-color: lightblue; -fx-padding: 10;");
        crudButtons.setSpacing(50);
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
                    double firstAge = getAge(students.get(0).getBirthdate());
                    double lowest = firstAge;
                    double highest = firstAge;
                    for (Student student : studentTable.getAllStudents()) {
                        double age = getAge(student.getBirthdate());
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

        pageInfo.getChildren().addAll(bottomLabel);
        this.content.setBottom(pageInfo);
    };


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
            return i.length() == 10;
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
    protected double getAge(String dob) {
        // Get student birthday
        // YYYY-MM-DD
        String[] birthdaySplit = dob.split("-");
        LocalDate birthdayDate = LocalDate.of(Integer.parseInt(birthdaySplit[0]), Integer.parseInt(birthdaySplit[1]), Integer.parseInt(birthdaySplit[2]));
        LocalDate now = LocalDate.now();
        double age;

        // Get the difference to find age
        age = (double) ChronoUnit.YEARS.between(birthdayDate, now);
        // Return age
        return age;
    }

}
