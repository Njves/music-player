module com.njves.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens com.njves.demo to javafx.fxml;
    exports com.njves.demo;
}