package hhs.bitcoinchecker.bitcoinchecker.controllers;

import hhs.bitcoinchecker.bitcoinchecker.BitcoinChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VoegAdresToeViewController {
    @FXML
    private TextField adresHashInvoer;
    @FXML
    private TextField adresNaamInvoer;
    @FXML
    private Button annuleerKnop;
    @FXML
    private Button okKnop;

    @FXML
    protected void okGeklikt() {
        BitcoinChecker.setScene("AdressenView.fxml");
    }

    @FXML
    protected void annuleerGeklikt() {
        BitcoinChecker.setScene("AdressenView.fxml");
    }
}