import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Tracker {
    @NotNull
    private static ArrayList<TrackedBitcoinAdres> adressen = new ArrayList<TrackedBitcoinAdres>();

    private static void controleerAlleAdressen() throws IOException, ParseException {
        for(int i = 0; i < adressen.size(); i++){
            TrackedBitcoinAdres adres = adressen.get(i);
            controleerAdres(adres);
        }
    }

    public static void controleerAdres(TrackedBitcoinAdres bitcoinAdres) throws IOException, ParseException {
        Double prijs = Blockchain.getBitcoinPrijs();
        ArrayList<BitcoinTransactie> geschiedenis = Blockchain.getAdresGeschiedenis(bitcoinAdres);
        long tijd = System.currentTimeMillis();

        for(int i = 0; i < geschiedenis.size(); i++){
            BitcoinTransactie ts = geschiedenis.get(i);
            if(bitcoinAdres.getLaatstGecontroleerd() < ts.getTijd()){
                System.out.println(ts.getHash());
                System.out.println(ts.getBitcoinAdres());
                System.out.println(ts.getTijd());
                stuurMelding(ts, prijs);
            };
        }
        bitcoinAdres.setLaatstGecontroleerd(tijd);
        JsonHandler.slaOp(adressen, "main/resources/storage/trackedBitcoinAdres.json");
    }

    private static final Timer timer = new Timer();
    private static final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                controleerAlleAdressen();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    };

    public static void initialiseerTracker(){
        ArrayList<TrackedBitcoinAdres> adressen;
        adressen = (ArrayList<TrackedBitcoinAdres>) JsonHandler.haalOp("main/resources/storage/trackedBitcoinAdres.json");
        setAdressen(adressen);

        timer.scheduleAtFixedRate(timerTask, 0, 5 * 60 * 1000);
    }

    private static void stuurMelding(BitcoinTransactie bitcoinTransactie, Double prijs){
        Double totaal = bitcoinTransactie.getVerandering() * prijs;
        totaal = Math.round(totaal * 100.0) / 100.0;
        String caption;
        String text;
        String adresNaam = bitcoinTransactie.getBitcoinAdres().getNaam();
        String adresHash = bitcoinTransactie.getBitcoinAdres().getAdres();

        if( totaal > 0 ){
            caption = "Bitcoin gestort!";
            text = "Jouw adres (" + adresNaam + ": " + adresHash + ")" + " heeft € " + totaal + " aan bitcoin ontvangen!";
        } else {
            caption = "Bitcoin afgeschreven!";
            text = "Er is € " + totaal + " van je adres (" + adresNaam + ": " + adresHash + ")" + " aan bitcoin afgeschreven !";
        }
        Main.trayIcon.displayMessage(caption, text, TrayIcon.MessageType.NONE);
    }

    public static void voegAdresToe(TrackedBitcoinAdres bitcoinAdres){
        adressen.add(bitcoinAdres);
        JsonHandler.slaOp(adressen, "main/resources/storage/trackedBitcoinAdres.json");
    }

    public static void verwijderAdres(TrackedBitcoinAdres bitcoinAdres) {
        for(int i = 0; i < adressen.size(); i++){
            TrackedBitcoinAdres adres = adressen.get(i);
            if(bitcoinAdres.getAdres().equals(adres.getAdres())){
                adressen.remove(i);
                break;
            }
        }

        JsonHandler.slaOp(adressen, "main/resources/storage/trackedBitcoinAdres.json");
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
