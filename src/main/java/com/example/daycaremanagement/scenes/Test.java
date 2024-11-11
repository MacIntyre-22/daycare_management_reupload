package com.example.daycaremanagement.scenes;

import com.example.daycaremanagement.interfaces.SideBar;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Test extends BorderPane implements SideBar {

    private static Test instance;

    /**
     * Gets an instance of this student test class
     * @return the instance
     */
    public static Test getInstance(){
        if (instance == null){
            instance = new Test();
        }
        return instance;
    }
    private Test() {
       this.setTop(new Label("Student Test Page"));
       this.setLeft(sideBar());
       this.setBottom(bottomBar());
    }


    @Override
    public void sideButtonBar() {
        graph1.setOnAction(e->{

        });
        graph2.setOnAction(e->{

        });
        graph3.setOnAction(e->{

        });
        graph4.setOnAction(e->{

        });
    }

    @Override
    public void bottomButtonBar() {
        // Insert Into Column ~ input for each column except Id's
        create.setOnAction(e->{
            this.getChildren().remove(2);

            Label firstName = new Label("First Name");
            TextField fNameInput = new TextField();
            VBox fNameGroup = new VBox(firstName, fNameInput);
            fNameGroup.setAlignment(Pos.CENTER);

            Label lastName = new Label("Last Name");
            TextField lNameInput = new TextField();
            VBox lNameGroup = new VBox(lastName, lNameInput);
            lNameGroup.setAlignment(Pos.CENTER);

            Label birthday = new Label("Birthday");
            TextField birthdayInput = new TextField();
            VBox birthdayGroup = new VBox(birthday, birthdayInput);
            birthdayGroup.setAlignment(Pos.CENTER);

            Label classroom = new Label("Classroom");
            TextField classroomInput = new TextField();
            VBox classroomGroup = new VBox(classroom, classroomInput);
            classroomGroup.setAlignment(Pos.CENTER);

            Label behaviour = new Label("Behaviour");
            TextField behaviourInput = new TextField();
            VBox behaviourGroup = new VBox(behaviour, behaviourInput);
            behaviourGroup.setAlignment(Pos.CENTER);

            Label age = new Label("Age");
            TextField ageInput = new TextField();
            VBox ageGroup = new VBox(age, ageInput);
            ageGroup.setAlignment(Pos.CENTER);

            Button createInput = new Button("Create!");
            createInput.setOnAction(e1->{
                // Grabs the text in the fields
            });

            HBox createCollection = new HBox(fNameGroup, lNameGroup, birthdayGroup, classroomGroup, behaviourGroup, ageGroup);
            createCollection.setAlignment(Pos.CENTER);
            createCollection.setSpacing(2);

            VBox items = new VBox(bottomBar(), createCollection, createInput);
            items.setStyle("-fx-background-color: lightblue;");
            items.setAlignment(Pos.CENTER);
            this.setBottom(items);
        });
        // Read ~ removes inputs fields
        // We could Probably move the graph buttons into here
        //I wouldn't remove the sideBar we might be able to use it for something else.
        read.setOnAction(e->{
            this.getChildren().remove(2);
            this.setBottom(bottomBar());

        });
        // update ~ Have a dropdown menu to all columns and an input field beside
        update.setOnAction(e-> {
            this.getChildren().remove(2);

            Label idNum = new Label("Id");
            TextField idNumInput = new TextField();
            VBox idNumGroup = new VBox(idNum, idNumInput);
            idNumGroup.setAlignment(Pos.CENTER);

            Label columnName = new Label("Column Choice");
            ComboBox<String> columnNameChoice = new ComboBox<>();
            columnNameChoice.getItems().addAll("Name1", "Name2", "Name3");
            VBox columnNameGroup = new VBox(columnName, columnNameChoice);
            columnNameGroup.setAlignment(Pos.CENTER);

            Label updateName = new Label("New Name");
            TextField updateNameInput = new TextField();
            VBox updateNameGroup = new VBox(updateName, updateNameInput);
            updateNameGroup.setAlignment(Pos.CENTER);

            Button updateInput = new Button("Update!");
            updateInput.setOnAction(e1->{
                // Grabs the text in the fields
            });

            HBox updateCollection = new HBox(idNumGroup, columnNameGroup, updateNameGroup);
            updateCollection.setAlignment(Pos.CENTER);
            updateCollection.setSpacing(2);

            VBox items = new VBox(bottomBar(), updateCollection, updateInput);
            items.setStyle("-fx-background-color: lightblue;");
            items.setAlignment(Pos.CENTER);
            this.setBottom(items);

        });
        // Delete ~ input field for the id, and a two-step for deleting
        // We Will probably move this beside each row, but for now its testing
        delete.setOnAction(e->{
            Label idNum = new Label("Id");
            TextField idNumInput = new TextField();
            VBox idNumGroup = new VBox(idNum, idNumInput);
            idNumGroup.setAlignment(Pos.CENTER);

            Button deleteInput = new Button("Delete!");


            // If we want more in here
            HBox updateCollection = new HBox(idNumGroup);
            updateCollection.setAlignment(Pos.CENTER);
            updateCollection.setSpacing(2);

            VBox items = new VBox(bottomBar(), updateCollection, deleteInput);
            items.setStyle("-fx-background-color: lightblue;");
            items.setAlignment(Pos.CENTER);

            deleteInput.setOnAction(e1 -> {
                Label twoStep = new Label("Are you sure you want to delete this row? \nOnce you delete there is no way of getting it back!");
                twoStep.setAlignment(Pos.CENTER);
                Button yesButton = new Button("Yes");
                yesButton.setOnAction(e2-> {
                    // Grabs the text in the fields
                });
                Button noButton = new Button("No!");
                noButton.setOnAction(e2-> {
                    // Removes the text in the fields
                });

                HBox buttons = new HBox(yesButton, noButton);
                buttons.setSpacing(2);
                buttons.setAlignment(Pos.CENTER);

                VBox twoStepCheck = new VBox(twoStep, buttons);
                twoStepCheck.setStyle("-fx-background-color: lightblue;");
                twoStepCheck.setAlignment(Pos.CENTER);

                items.getChildren().add(twoStepCheck);
            });


            this.setBottom(items);
        });
    }
}
