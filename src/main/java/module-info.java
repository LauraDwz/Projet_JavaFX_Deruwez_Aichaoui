module com.fst.projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.swing;

    opens com.fst.projet to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.fst.projet;

}