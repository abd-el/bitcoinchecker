import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static void toonMenu(){
        System.out.println("== Menu ==");
        System.out.println("1) Toon alle adressen");
        System.out.println("2) Verwijder adres");
        System.out.println("3) Voeg adres toe");
        System.out.println("0) Exit");
    }


    private static SystemTray systemTray;
    public static TrayIcon trayIcon;

    private static void initialiseer() throws AWTException {
        Tracker.initialiseerTracker();
        systemTray = SystemTray.getSystemTray();
        String imagePath = (new File("").getAbsolutePath() + "/src/main/resources/images/icon.png");
        Image image = Toolkit.getDefaultToolkit().getImage(imagePath);
        System.out.println(imagePath);
        trayIcon = new TrayIcon(image, "Bitcoin Checker");
        trayIcon.setImageAutoSize(true);
        systemTray.add(trayIcon);
    }

    public static void main(String[] args) throws AWTException {
        initialiseer();

        // MENU
        toonMenu();

        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerLine = new Scanner(System.in);

        int gekozen = scannerInt.nextInt();
        while (gekozen != 0) {
            if (gekozen == 1) {
                ArrayList<TrackedBitcoinAdres> trackedAdressen = Tracker.getAdressen();
                System.out.println(trackedAdressen);
                System.out.println(trackedAdressen.getClass());
                System.out.println("====");
                if (trackedAdressen.size() == 0) {
                    System.out.println("Er zijn geen adressen");
                } else {
                    for (int i = 0; i < trackedAdressen.size(); i++) {
                        TrackedBitcoinAdres adres = trackedAdressen.get(i);
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
                    for (int i = 0; i < trackedAdressen.size(); i++) {
                        TrackedBitcoinAdres adres = trackedAdressen.get(i);
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
                    for (int i = 0; i < trackedAdressen.size(); i++) {
                        TrackedBitcoinAdres adres = trackedAdressen.get(i);
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
                    for (int i = 0; i < trackedAdressen.size(); i++) {
                        TrackedBitcoinAdres bitcoinAdres = trackedAdressen.get(i);
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

                TrackedBitcoinAdres bitcoinAdres = new TrackedBitcoinAdres(naam, adres, 0);
                Tracker.voegAdresToe(bitcoinAdres);

                toonMenu();
                gekozen = scannerInt.nextInt();
            }
        }
    }
}
