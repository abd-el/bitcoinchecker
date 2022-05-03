package hhs.bitcoinchecker.bitcoinchecker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    public Button button;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Bitcoin Checker!");
    }

    @FXML
    protected void button_clicked() { welcomeText.setText("voorbeeld tekst"); }
}