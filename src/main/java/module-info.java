module com.fst.projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.fst.projet to javafx.fxml;
    exports com.fst.projet;
}