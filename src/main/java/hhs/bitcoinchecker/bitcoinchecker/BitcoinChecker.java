package hhs.bitcoinchecker.bitcoinchecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.TrayIcon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BitcoinChecker extends Application {
    public static TrayIcon trayIcon;
    public static Stage stage;

    private static void toonMenu(){
        System.out.println("== Menu ==");
        System.out.println("1) Toon alle adressen");
        System.out.println("2) Verwijder adres");
        System.out.println("3) Voeg adres toe");
        System.out.println("0) Exit");
    }

    @Override
    public void start(Stage stage) {
        BitcoinChecker.stage = stage;
        Image JavaFXimage = new Image(String.valueOf(BitcoinChecker.class.getResource("images/btc-icon.png")));
        stage.getIcons().add(JavaFXimage);
        stage.setTitle("Bitcoin Checker");
        setScene("AdressenView.fxml");
        stage.show();
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

    public static void main(String[] args) {
        launch();

        // MENU
        toonMenu();

        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerLine = new Scanner(System.in);

        int gekozen = 0; // scannerInt.nextInt();
        while (gekozen != 0) {
            if (gekozen == 1) {
                ArrayList<TrackedBitcoinAdres> trackedAdressen = Tracker.getAdressen();
                System.out.println(trackedAdressen);
                System.out.println(trackedAdressen.getClass());
                System.out.println("====");
                if (trackedAdressen.size() == 0) {
                    System.out.println("Er zijn geen adressen");
                } else {
                    for (TrackedBitcoinAdres adres : trackedAdressen) {
                        System.out.println(adres.getNaam() + ": " + adres.getHash());
                    }
                }
                System.out.println("====");
                toonMenu();
                gekozen = scannerInt.nextInt();
            } else if (gekozen == 2) {
                System.out.println("Gebruik de GUI");
                toonMenu();
            } else if (gekozen == 3) {
                System.out.println("Geef een naam. Type exit om te verlaten");
                String naam = scannerLine.nextLine();
                ArrayList<TrackedBitcoinAdres> trackedAdressen = Tracker.getAdressen();

                if (naam.equals("exit")) {
                    toonMenu();
                    gekozen = scannerInt.nextInt();
                    continue;
                } else {
                    boolean geldig = true;
                    for (TrackedBitcoinAdres adres : trackedAdressen) {
                        if (naam.equals(adres.getNaam())) {
                            System.out.println("Adres " + naam + " bestaat al!");
                            geldig = false;
                            break;
                        }
                    }
                    if (!geldig) {
                        continue;
                    }
                }

                System.out.println("Geef het adres. Type exit om te verlaten");
                String adres = scannerLine.nextLine();

                if (adres.equals("exit")) {
                    toonMenu();
                    gekozen = scannerInt.nextInt();
                    continue;
                } else {
                    boolean geldig = true;
                    for (TrackedBitcoinAdres bitcoinAdres : trackedAdressen) {
                        if (adres.equals(bitcoinAdres.getHash())) {
                            System.out.println("Adres " + bitcoinAdres.getHash() + " bestaat al met de naam " + bitcoinAdres.getNaam() + "!");
                            geldig = false;
                            break;
                        }
                    }
                    if (!geldig) {
                        continue;
                    }
                }

                TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres(naam, adres, 0);
                Tracker.voegAdresToe(bitcoinAdres);

                toonMenu();
                gekozen = scannerInt.nextInt();
            }
        }

        System.exit(0);
    }
}