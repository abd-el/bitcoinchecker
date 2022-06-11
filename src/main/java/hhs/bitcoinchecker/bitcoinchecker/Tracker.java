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
            controleerAlleAdressen();
        }
    };

    public static void initialiseer(){
        ArrayList<TrackedBitcoinAdres> adressen = JsonHandler.haalTrackedBitcoinAdressenOp();
        setAdressen(adressen);

        timer.scheduleAtFixedRate(timerTask, 0, 5 * 60 * 1000);
    }

    private static void controleerAlleAdressen() {
        for (TrackedBitcoinAdres adres : adressen) {
            adres.controleer();
        }
    }

    public static void stuurMelding(BitcoinTransactie bitcoinTransactie, Double prijs){
        double totaal = bitcoinTransactie.getVerandering() * prijs;
        totaal = Math.round(totaal * 100.0) / 100.0;
        String caption;
        String text;
        String adresNaam = bitcoinTransactie.getBitcoinAdres().getNaam();
        String adresHash = bitcoinTransactie.getBitcoinAdres().getHash();

        if( totaal > 0 ){
            caption = "Bitcoin gestort!";
            text = "Jouw adres (" + adresNaam + ": " + adresHash + ")" + " heeft € " + totaal + " aan bitcoin ontvangen!";
        } else {
            caption = "Bitcoin afgeschreven!";
            text = "Er is € " + totaal + " van je adres (" + adresNaam + ": " + adresHash + ")" + " aan bitcoin afgeschreven !";
        }
        BitcoinChecker.trayIcon.displayMessage(caption, text, TrayIcon.MessageType.NONE);
    }

    public static void voegAdresToe(TrackedBitcoinAdres bitcoinAdres){
        boolean bestaatAl = false;
        for (TrackedBitcoinAdres adres : adressen) {
            String naam = adres.getNaam();
            if (bitcoinAdres.getNaam().equals(naam)) {
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

    public static @NotNull ArrayList<TrackedBitcoinAdres> getAdressen() {
        return adressen;
    }

    public static void setAdressen(ArrayList<TrackedBitcoinAdres> nieuweAdressen){
        if(nieuweAdressen == null){
            // kan niet
            nieuweAdressen = new ArrayList<>();
        }
        adressen = nieuweAdressen;
    }
}
