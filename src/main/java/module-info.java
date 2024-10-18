module com.example.daycaremanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.daycaremanagement to javafx.fxml;
    exports com.example.daycaremanagement;
}