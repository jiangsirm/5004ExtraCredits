module com.example.extracredits {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.extracredits to javafx.fxml;
    exports com.example.extracredits;
}