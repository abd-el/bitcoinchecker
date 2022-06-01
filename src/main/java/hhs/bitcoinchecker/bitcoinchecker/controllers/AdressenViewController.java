package hhs.bitcoinchecker.bitcoinchecker.controllers;

import hhs.bitcoinchecker.bitcoinchecker.BitcoinChecker;
import hhs.bitcoinchecker.bitcoinchecker.TrackedBitcoinAdres;
import hhs.bitcoinchecker.bitcoinchecker.Tracker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdressenViewController implements Initializable {
    @FXML
    private Button button;
    @FXML
    private Button voegAdresToe;
    @FXML
    private TableView<TrackedBitcoinAdres> adresTabel;
    @FXML
    private TableColumn<TrackedBitcoinAdres, String> adresNaamKolom;
    @FXML
    private TableColumn<TrackedBitcoinAdres, String> adresHashKolom;
    @FXML
    private TableColumn<TrackedBitcoinAdres, String> verwijderAdresKolom;

    @FXML
    protected void voegAdresToeGeklikt() {
        BitcoinChecker.setScene("VoegAdresToeView.fxml");
    }

    @FXML
    protected void voegAdresToe(TrackedBitcoinAdres trackedBitcoinAdres){
        adresTabel.getItems().add(trackedBitcoinAdres);
    }

    @FXML
    protected void verwijderGeklikt(TrackedBitcoinAdres trackedBitcoinAdres){
        Tracker.verwijderAdres(trackedBitcoinAdres);
        updateTabel();
    };

    @FXML
    protected void updateTabel(){
        adresTabel.getItems().clear();
        ArrayList<TrackedBitcoinAdres> adressen = Tracker.getAdressen();
        for (TrackedBitcoinAdres trackedBitcoinAdres : adressen) {
            voegAdresToe(trackedBitcoinAdres);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adresNaamKolom.setCellValueFactory(new PropertyValueFactory<>("naam"));
        adresHashKolom.setCellValueFactory(new PropertyValueFactory<>("hash"));
        verwijderAdresKolom.setCellFactory(param->  new TableCell<TrackedBitcoinAdres, String>() {
            private final Button verwijderKnop = new Button("Verwijder");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    verwijderKnop.setOnAction(event->verwijderGeklikt(getTableRow().getItem()));
                    setGraphic(verwijderKnop);
                    setText(null);
                }
            }
        });

        updateTabel();
    }
}