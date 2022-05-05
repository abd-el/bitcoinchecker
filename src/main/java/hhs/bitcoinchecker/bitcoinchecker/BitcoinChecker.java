package hhs.bitcoinchecker.bitcoinchecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class BitcoinChecker extends Application {
    public static TrayIcon trayIcon;

    private static void toonMenu(){
        System.out.println("== Menu ==");
        System.out.println("1) Toon alle adressen");
        System.out.println("2) Verwijder adres");
        System.out.println("3) Voeg adres toe");
        System.out.println("0) Exit");
    }

    private static void initialiseer() throws IOException, ParseException {
        Tracker.initialiseer();

        // Maak systeem balk object aan
        SystemTray systemTray = SystemTray.getSystemTray();

        // Zoek icoon
        java.awt.Image JWTimage = Toolkit.getDefaultToolkit().getImage(BitcoinChecker.class.getResource("images/btc-icon.png"));
        trayIcon = new TrayIcon(JWTimage, "Bitcoin Checker");
        trayIcon.setImageAutoSize(true);

        // Voeg ook toe aan systeem balk
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BitcoinChecker.class.getResource("fxml/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image JavaFXimage = new Image(String.valueOf(BitcoinChecker.class.getResource("images/btc-icon.png")));
        stage.getIcons().add(JavaFXimage);

        stage.setTitle("Bitcoin Checker!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, ParseException {
        initialiseer();
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
                        System.out.println(adres.getNaam() + ": " + adres.getAdres());
                    }
                }
                System.out.println("====");
                toonMenu();
                gekozen = scannerInt.nextInt();
            } else if (gekozen == 2) {
                ArrayList<TrackedBitcoinAdres> trackedAdressen = Tracker.getAdressen();
                // scanner.close();
                System.out.println("Geef de naam. Type exit om te verlaten");
                String naam = scannerLine.nextLine();
                if (naam.equals("exit")) {
                    // Er gebeurt hier niets
                } else if (trackedAdressen.size() == 0) {
                    System.out.println("Er zijn geen adressen");
                } else {
                    boolean gevonden = false;
                    for (TrackedBitcoinAdres adres : trackedAdressen) {
                        if (naam.equals(adres.getNaam())) {
                            Tracker.verwijderAdres(adres);
                            System.out.println("Adres " + naam + " verwijdert!");
                            gevonden = true;
                            break;
                        }
                    }
                    if (!gevonden) {
                        System.out.println("Geen adres gevonden met de naam " + naam + "!");
                    }
                }
                toonMenu();
                gekozen = scannerInt.nextInt();
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
                        if (adres.equals(bitcoinAdres.getAdres())) {
                            System.out.println("Adres " + bitcoinAdres.getAdres() + " bestaat al met de naam " + bitcoinAdres.getNaam() + "!");
                            geldig = false;
                            break;
                        }
                    }
                    if (!geldig) {
                        continue;
                    }
                }

                TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres(naam, adres);
                Tracker.voegAdresToe(bitcoinAdres);

                toonMenu();
                gekozen = scannerInt.nextInt();
            }
        }

        // System.exit(0);
    }
}