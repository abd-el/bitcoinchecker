package hhs.bitcoinchecker.bitcoinchecker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class BitcoinTransactie {
    private String hash;
    private long tijd;
    private transient BitcoinAdres bitcoinAdres;
    private Double verandering;

    public BitcoinTransactie(BitcoinAdres adres, String hash, long tijd, Double verandering){
        this.setBitcoinAdres(adres);
        this.hash = hash;
        this.tijd = tijd;
        this.verandering = verandering;
    }

    private void setBitcoinAdres(BitcoinAdres adres) {
        this.bitcoinAdres = adres;
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
}
