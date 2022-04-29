module com.kristley.alcocalc {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.kristley.alcocalc to javafx.fxml, com.google.gson;
    exports com.kristley.alcocalc;
}