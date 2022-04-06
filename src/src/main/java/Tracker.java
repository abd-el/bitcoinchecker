import java.util.ArrayList;

public class Tracker {
    private static ArrayList<TrackedBitcoinAdres> adressen = new ArrayList<TrackedBitcoinAdres>();

    public static void controleerAdres(TrackedBitcoinAdres bitcoinAdres){

    }

    public static void stuurMelding(BitcoinTransactie bitcoinTransactie){

    }

    public static void voegAdresToe(TrackedBitcoinAdres bitcoinAdres){
        adressen.add(bitcoinAdres);
    }

    public static void verwijderAdres(TrackedBitcoinAdres bitcoinAdres){
        for(int i = 0; i < adressen.size(); i++){
            TrackedBitcoinAdres adres = adressen.get(i);
            if(bitcoinAdres.getAdres().equals(adres.getAdres())){
                adressen.remove(i);
                break;
            }
        }
    }

    public static Double getBitcoinPrijs(){
        return null;
    }

    public static ArrayList<TrackedBitcoinAdres> getAdressen() {
        return adressen;
    }
}
