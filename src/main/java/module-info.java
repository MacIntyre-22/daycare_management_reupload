module com.example.daycaremanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.daycaremanagement to javafx.fxml;
    exports com.example.daycaremanagement;
    exports com.example.daycaremanagement.overlays;
    opens com.example.daycaremanagement.overlays to javafx.fxml;
    exports com.example.daycaremanagement.pages;
    opens com.example.daycaremanagement.pages to javafx.fxml;
}