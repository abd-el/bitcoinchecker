import java.util.ArrayList;

public class BitcoinTransactie {
    private String hash;
    private long tijd;
    private BitcoinAdres bitcoinAdres;
    private Double verandering;

    public BitcoinTransactie(BitcoinAdres adres, String hash, long tijd, Double verandering){
        this.bitcoinAdres = adres;
        this.hash = hash;
        this.tijd = tijd;
        this.verandering = verandering;
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
}
