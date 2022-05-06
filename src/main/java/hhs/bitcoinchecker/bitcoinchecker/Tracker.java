package hhs.bitcoinchecker.bitcoinchecker;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Tracker {
    @NotNull
    private static ArrayList<TrackedBitcoinAdres> adressen = new ArrayList<TrackedBitcoinAdres>();
    public static final Timer timer = new Timer();
    public static final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                controleerAlleAdressen();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    };

    public static void initialiseer() throws IOException, ParseException {
        ArrayList<TrackedBitcoinAdres> adressen = JsonHandler.haalTrackedBitcoinAdressenOp();
        setAdressen(adressen);

        timer.scheduleAtFixedRate(timerTask, 0, 5 * 60 * 1000);
    }

    private static void controleerAlleAdressen() throws IOException, ParseException {
        for (TrackedBitcoinAdres adres : adressen) {
            controleerAdres(adres);
        }
    }

    public static void controleerAdres(TrackedBitcoinAdres bitcoinAdres) throws IOException, ParseException {
        Double prijs = Blockchain.getBitcoinPrijs();
        ArrayList<BitcoinTransactie> geschiedenis = Blockchain.getAdresGeschiedenis(bitcoinAdres);
        long tijd = System.currentTimeMillis();
        if(geschiedenis == null){
            bitcoinAdres.setLaatstGecontroleerd(tijd);
            return;
        }

        for (BitcoinTransactie ts : geschiedenis) {
            if (bitcoinAdres.getLaatstGecontroleerd() < ts.getTijd()) {
                System.out.println(ts.getHash());
                System.out.println(ts.getBitcoinAdres());
                System.out.println(ts.getTijd());
                stuurMelding(ts, prijs);
            }
        }

        bitcoinAdres.setLaatstGecontroleerd(tijd);
        JsonHandler.slaTrackedBitcoinAdressenOp();
    }

    public static boolean stuurMelding(BitcoinTransactie bitcoinTransactie, Double prijs){
        double verandering = bitcoinTransactie.getVerandering() * prijs;
        verandering = Math.round(verandering * 100.0) / 100.0;
        String caption = null;
        String text = null;
        String adresNaam = bitcoinTransactie.getBitcoinAdres().getNaam();
        String adresHash = bitcoinTransactie.getBitcoinAdres().getHash();
        long laatstGecontroleerd = 0;

        boolean adresWordtGevolgd = false;
        for (TrackedBitcoinAdres adres : adressen) {
            String naam = adres.getNaam();
            if (adresNaam.equals(naam)) {
                laatstGecontroleerd = adres.getLaatstGecontroleerd();
                adresWordtGevolgd = true;
                break;
            }
        }

        boolean transactieIsNieuw = bitcoinTransactie.getTijd() > laatstGecontroleerd;
        boolean gestuurd = false;

        if(adresWordtGevolgd && transactieIsNieuw){
            if(verandering > 0){
                caption = "Bitcoin gestort!";
                text = "Jouw adres (" + adresNaam + ": " + adresHash + ")" + " heeft € " + verandering + " aan bitcoin ontvangen!";
                BitcoinChecker.trayIcon.displayMessage(caption, text, TrayIcon.MessageType.NONE);
                gestuurd = true;
            } else if(verandering < 0) {
                caption = "Bitcoin afgeschreven!";
                text = "Er is € " + verandering + " van je adres (" + adresNaam + ": " + adresHash + ")" + " aan bitcoin afgeschreven !";
                BitcoinChecker.trayIcon.displayMessage(caption, text, TrayIcon.MessageType.NONE);
                gestuurd = true;
            }
        }

        return gestuurd;
    }

    public static void voegAdresToe(TrackedBitcoinAdres bitcoinAdres){
        boolean bestaatAl = false;
        for (TrackedBitcoinAdres adres : adressen) {
            String naam = adres.getNaam();
            String hash = adres.getHash();
            if (bitcoinAdres.getNaam().equals(naam) || bitcoinAdres.getHash().equals(hash)) {
                bitcoinAdres.setHash(adres.getHash());
                bestaatAl = true;
            }
        }

        if(!bestaatAl){
            adressen.add(bitcoinAdres);
        }

        JsonHandler.slaTrackedBitcoinAdressenOp();
    }

    public static void verwijderAdres(TrackedBitcoinAdres bitcoinAdres) {
        for(int i = 0; i < adressen.size(); i++){
            TrackedBitcoinAdres adres = adressen.get(i);
            if(bitcoinAdres.getHash().equals(adres.getHash())){
                adressen.remove(i);
                break;
            }
        }

        JsonHandler.slaTrackedBitcoinAdressenOp();
    }

    public static ArrayList<TrackedBitcoinAdres> getAdressen() {
        return adressen;
    }

    public static void setAdressen(ArrayList<TrackedBitcoinAdres> nieuweAdressen){
        if(nieuweAdressen == null){
            // kan niet
            return;
        }
        adressen = nieuweAdressen;
    }
}
