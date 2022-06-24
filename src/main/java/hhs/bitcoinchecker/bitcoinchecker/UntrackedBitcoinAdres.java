package hhs.bitcoinchecker.bitcoinchecker;

public class UntrackedBitcoinAdres extends BitcoinAdres {
    private long laatstGecontroleerd = 0;

    public UntrackedBitcoinAdres(String naam, String hash) {
        super(naam, hash, false);
    }

    @Override
    public Double getSaldo(){
        setLaatstGecontroleerd(System.currentTimeMillis());
        return super.getSaldo();
    }

    @Override
    public void setLaatstGecontroleerd(long laatstGecontroleerd) {

    }

    @Override
    public long getLaatstGecontroleerd() {
        return 0;
    }
}
