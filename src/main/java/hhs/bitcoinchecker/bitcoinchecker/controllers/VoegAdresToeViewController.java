package hhs.bitcoinchecker.bitcoinchecker.controllers;

import hhs.bitcoinchecker.bitcoinchecker.BitcoinChecker;
import hhs.bitcoinchecker.bitcoinchecker.TrackedBitcoinAdres;
import hhs.bitcoinchecker.bitcoinchecker.Tracker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

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
        ArrayList<TrackedBitcoinAdres> trackedAdressen = Tracker.getAdressen();
        String hash = adresHashInvoer.getText();
        String naam = adresNaamInvoer.getText();

        boolean geldig = true;
        for (TrackedBitcoinAdres adres : trackedAdressen) {
            if (naam.equals(adres.getNaam()) || hash.equals(adres.getHash())) {
                geldig = false;
                break;
            }
        }

        if (!geldig) {
            System.out.println("Een adres met deze naam en/of hash bestaat al!");
            return;
        }

        TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres(naam, hash);
        Tracker.voegAdresToe(bitcoinAdres);

        BitcoinChecker.setScene("AdressenView.fxml");
    }

    @FXML
    protected void annuleerGeklikt() {
        BitcoinChecker.setScene("AdressenView.fxml");
    }
}