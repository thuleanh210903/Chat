module com.example.chatrmi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires java.sql;


    opens com.example.chatrmi to javafx.fxml;
    exports com.example.chatrmi;
    exports com.example.chatrmi.server;
    exports com.example.chatrmi.client;
}