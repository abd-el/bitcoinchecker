package hhs.bitcoinchecker.bitcoinchecker;

import java.util.ArrayList;

public class TrackedBitcoinAdres extends BitcoinAdres {
    private long laatstGecontroleerd;

    public TrackedBitcoinAdres(String naam, String hash, Double saldo, ArrayList<BitcoinTransactie> geschiedenis, long laatstGecontroleerd) {
        super(naam, hash, saldo, geschiedenis);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public TrackedBitcoinAdres(String naam, String hash, Double saldo, long laatstGecontroleerd) {
        super(naam, hash, saldo);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public TrackedBitcoinAdres(String naam, String hash, long laatstGecontroleerd) {
        super(naam, hash);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public TrackedBitcoinAdres(String naam, String hash) {
        super(naam, hash);
        this.laatstGecontroleerd = 0;
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
