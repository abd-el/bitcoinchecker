package hhs.bitcoinchecker.bitcoinchecker;

import java.util.ArrayList;

public class TrackedBitcoinAdres extends BitcoinAdres {
    private long laatstGecontroleerd;

    public TrackedBitcoinAdres(String naam, String adres, Double saldo, ArrayList<BitcoinTransactie> geschiedenis, long laatstGecontroleerd) {
        super(naam, adres, saldo, geschiedenis);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public TrackedBitcoinAdres(String naam, String adres, Double saldo, long laatstGecontroleerd) {
        super(naam, adres, saldo);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public TrackedBitcoinAdres(String naam, String adres, long laatstGecontroleerd) {
        super(naam, adres);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public long getLaatstGecontroleerd() {
        return laatstGecontroleerd;
    }

    public void setLaatstGecontroleerd(long laatstGecontroleerd) {
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    @Override
    public Double getSaldo(){
        setLaatstGecontroleerd(System.currentTimeMillis());
        return super.getSaldo();
    }
}
