package hhs.bitcoinchecker.bitcoinchecker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class BitcoinTransactie {
    private String hash;
    private long tijd;
    private transient BitcoinAdres bitcoinAdres;
    private Double verandering;
    private String bitcoinAdresNaam;
    private String bitcoinAdresHash;

    public BitcoinTransactie(BitcoinAdres adres, String hash, long tijd, Double verandering){
        this.setBitcoinAdres(adres);
        this.hash = hash;
        this.tijd = tijd;
        this.verandering = verandering;
    }

    public BitcoinTransactie(String naam, String hash, long tijd, Double verandering) throws IOException, ParseException {
        this.setBitcoinAdres(naam, hash);
        this.hash = hash;
        this.tijd = tijd;
        this.verandering = verandering;
    }

    private void setBitcoinAdres(BitcoinAdres adres) {
        this.bitcoinAdres = adres;
        this.bitcoinAdresNaam = adres.getNaam();
        this.bitcoinAdresHash = adres.getAdres();
    }

    public void setBitcoinAdres(String naam, String hash) throws IOException, ParseException {
        this.bitcoinAdresNaam = naam;
        this.bitcoinAdresHash = hash;

        ArrayList<TrackedBitcoinAdres> adressen = Tracker.getAdressen();
        boolean bestaat = false;
        for(TrackedBitcoinAdres adres : adressen){
            if(adres.getNaam().equals(naam)){
                bestaat = true;
                this.bitcoinAdres = adres;
                break;
            }
        }

        if(!bestaat){
            this.bitcoinAdres = new TrackedBitcoinAdres(naam, hash);
        }
    }

    public String getHash() {
        return hash;
    }

    public long getTijd() {
        return tijd;
    }

    public BitcoinAdres getBitcoinAdres() {
        return bitcoinAdres;
    }

    public Double getVerandering() {
        return verandering;
    }

    public void setTijd(long tijd) {
        this.tijd = tijd;
    }

    public void setVerandering(Double verandering) {
        this.verandering = verandering;
    }

    public String getBitcoinAdresNaam() {
        return bitcoinAdresNaam;
    }

    public String getBitcoinAdresHash() {
        return bitcoinAdresHash;
    }
}
