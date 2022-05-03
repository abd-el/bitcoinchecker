package hhs.bitcoinchecker.bitcoinchecker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller {
    @FXML
    public Button button;

    @FXML
    private Label welcomeText;

    @FXML
    private TableView adresTabel;

    @FXML
    private TableColumn adresNaamKolom;

    @FXML
    private TableColumn adresHashKolom;

    @FXML
    private TableColumn verwijderAdresKolom;

    @FXML
    protected void voegAdresToeGeklikt() {
        
    }
}