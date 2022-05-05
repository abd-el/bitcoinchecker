package hhs.bitcoinchecker.bitcoinchecker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class BitcoinAdres {
    private String naam;
    private String adres;
    private Double saldo;
    private ArrayList<BitcoinTransactie> geschiedenis;

    public BitcoinAdres(String naam, String adres, Double saldo, ArrayList<BitcoinTransactie> geschiedenis) {
        this.naam = naam;
        this.adres = adres;
        this.saldo = saldo;
        this.geschiedenis = geschiedenis;
    }

    public BitcoinAdres(String naam, String adres, Double saldo) throws IOException, ParseException {
        this.naam = naam;
        this.adres = adres;
        this.saldo = saldo;
        this.geschiedenis = Blockchain.getAdresGeschiedenis(this);
    }

    public BitcoinAdres(String naam, String adres) throws IOException, ParseException {
        this.naam = naam;
        this.adres = adres;
        this.geschiedenis = Blockchain.getAdresGeschiedenis(this);
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setAdres(String adres) {
        this.adres = adres;
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

    public String getAdres() {
        return adres;
    }

    public Double getSaldo() {
        return saldo;
    }

    public ArrayList<BitcoinTransactie> getGeschiedenis() {
        return geschiedenis;
    }
}
