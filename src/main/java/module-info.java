module com.example.semestralni_prace_a {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;


    opens com.example.semestralni_prace_a to javafx.fxml;
    exports com.example.semestralni_prace_a;
}