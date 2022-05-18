module hhs.bitcoinchecker.bitcoinchecker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.jetbrains.annotations;
    requires com.google.gson;

    opens hhs.bitcoinchecker.bitcoinchecker to javafx.fxml, com.google.gson;
    exports hhs.bitcoinchecker.bitcoinchecker;
    exports hhs.bitcoinchecker.bitcoinchecker.controllers;
    opens hhs.bitcoinchecker.bitcoinchecker.controllers to com.google.gson, javafx.fxml;
}