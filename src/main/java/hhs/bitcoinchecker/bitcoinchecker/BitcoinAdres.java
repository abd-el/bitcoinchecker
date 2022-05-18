package hhs.bitcoinchecker.bitcoinchecker;

import java.util.ArrayList;

public abstract class BitcoinAdres {
    private String naam;
    private String hash;
    private Double saldo;
    private ArrayList<BitcoinTransactie> geschiedenis;

    public BitcoinAdres(String naam, String hash, Double saldo, ArrayList<BitcoinTransactie> geschiedenis) {
        this.naam = naam;
        this.hash = hash;
        this.saldo = saldo;
        this.geschiedenis = geschiedenis;
    }

    public BitcoinAdres(String naam, String hash, Double saldo) {
        this.naam = naam;
        this.hash = hash;
        this.saldo = saldo;
    }

    public BitcoinAdres(String naam, String hash) {
        this.naam = naam;
        this.hash = hash;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setGeschiedenis(ArrayList<BitcoinTransactie> geschiedenis) {
        this.geschiedenis = geschiedenis;
    }

    public String getNaam() {
        return naam;
    }

    public String getHash() {
        return hash;
    }

    public Double getSaldo() {
        return saldo;
    }

    public ArrayList<BitcoinTransactie> getGeschiedenis() {
        return geschiedenis;
    }
}
