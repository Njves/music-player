module com.njves.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;
    requires javafx.graphics;
    requires junit;
    requires mp3agic;
    requires com.google.gson;

    opens com.njves.demo to javafx.fxml, com.google.gson;
    opens com.njves.demo.list to com.google.gson;
    opens com.njves.demo.model to com.google.gson;

    exports com.njves.demo;
    exports com.njves.demo.test;
    exports com.njves.demo.model;
}