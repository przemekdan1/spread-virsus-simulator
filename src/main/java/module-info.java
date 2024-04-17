module com.example.simulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simulation to javafx.fxml;
    exports com.example.simulation;
}