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

    public BitcoinAdres(String naam, String adres, Double saldo) {
        this.naam = naam;
        this.adres = adres;
        this.saldo = saldo;
    }

    public BitcoinAdres(String naam, String adres) {
        this.naam = naam;
        this.adres = adres;
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
