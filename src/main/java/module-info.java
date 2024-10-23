module com.example.daycaremanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.daycaremanagement to javafx.fxml;
    exports com.example.daycaremanagement;
    exports com.example.daycaremanagement.scenes;
    opens com.example.daycaremanagement.scenes to javafx.fxml;
}