package hhs.bitcoinchecker.bitcoinchecker;

import java.util.ArrayList;

public class TrackedBitcoinAdres extends BitcoinAdres {
    private long laatstGecontroleerd = 0;

    public TrackedBitcoinAdres(String naam, String hash) {
        super(naam, hash, true);
    }

    @Override
    public Double getSaldo(){
        setLaatstGecontroleerd(System.currentTimeMillis());
        return super.getSaldo();
    }

    @Override
    public void setLaatstGecontroleerd(long laatstGecontroleerd) {
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    @Override
    public long getLaatstGecontroleerd() {
        return laatstGecontroleerd;
    }
}
