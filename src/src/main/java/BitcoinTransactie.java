import java.sql.Time;
import java.util.ArrayList;

public class BitcoinTransactie {
    private ArrayList<BitcoinAdres> ontvangers;
    private ArrayList<Double> ontvangenBedragen;
    private ArrayList<BitcoinAdres> afzenders;
    private ArrayList<Double> verzondenBedragen;
    private long tijd;

    public BitcoinTransactie(ArrayList<BitcoinAdres> ontvangers, ArrayList<Double> ontvangenBedragen, ArrayList<BitcoinAdres> afzenders, ArrayList<Double> verzondenBedragen, long tijd) {
        this.ontvangers = ontvangers;
        this.ontvangenBedragen = ontvangenBedragen;
        this.afzenders = afzenders;
        this.verzondenBedragen = verzondenBedragen;
        this.tijd = tijd;
    }

    public ArrayList<BitcoinAdres> getOntvangers() {
        return ontvangers;
    }

    public ArrayList<Double> getOntvangenBedragen() {
        return ontvangenBedragen;
    }

    public ArrayList<BitcoinAdres> getAfzenders() {
        return afzenders;
    }

    public ArrayList<Double> getVerzondenBedragen() {
        return verzondenBedragen;
    }

    public long getTijd() {
        return tijd;
    }
}
