package hhs.bitcoinchecker.bitcoinchecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BitcoinChecker extends Application {
    public static TrayIcon trayIcon;
    private static Stage stage;

    private static void maakBalkIcoon(){
        // java.awt omdat balk icoon niet kan in javafx
        java.awt.Image trayImage = Toolkit.getDefaultToolkit().getImage(BitcoinChecker.class.getResource("images/btc-icon.png"));

        // Lees plaatje uit en maak het plaatje de goede hoogte en breedte
        BufferedImage trayIconImage = null;
        try {
            trayIconImage = ImageIO.read(Objects.requireNonNull(BitcoinChecker.class.getResource("images/btc-icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int trayIconWidth = new TrayIcon(trayImage).getSize().width;
        assert trayIconImage != null;
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, java.awt.Image.SCALE_SMOOTH));

        // Voeg icoon aan balk toe
        SystemTray systemTray = SystemTray.getSystemTray();
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(String fileNaam) {
        FXMLLoader fxmlLoader = new FXMLLoader(BitcoinChecker.class.getResource("fxml/" + fileNaam));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
    }

    @Override
    public void start(Stage stage) {
        // Begin van stage venster
        BitcoinChecker.stage = stage;

        maakBalkIcoon();

        // Maak stage venster af met plaatje
        Image JavaFXimage = new Image(String.valueOf(BitcoinChecker.class.getResource("images/btc-icon.png")));
        stage.getIcons().add(JavaFXimage);
        stage.setTitle("Bitcoin Checker");
        setScene("AdressenView.fxml");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}