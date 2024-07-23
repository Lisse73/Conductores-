module Conductores {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens application to javafx.base, javafx.controls, javafx.fxml;
    
    exports application;
}
