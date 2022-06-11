package hhs.bitcoinchecker.bitcoinchecker;

import java.util.ArrayList;

public class TrackedBitcoinAdres extends BitcoinAdres {
    private long laatstGecontroleerd;

    public TrackedBitcoinAdres(String naam, String hash) {
        super(naam, hash);
    }

    public long getLaatstGecontroleerd() {
        return laatstGecontroleerd;
    }

    public void setLaatstGecontroleerd(long laatstGecontroleerd) {
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public void controleer() {
        Double prijs = Blockchain.getBitcoinPrijs();

        ArrayList<BitcoinTransactie> geschiedenis = this.getGeschiedenisVanBlockchain();
        long tijd = System.currentTimeMillis();

        for (BitcoinTransactie ts : geschiedenis) {
            if (this.getLaatstGecontroleerd() < ts.getTijd()) {
                System.out.println(ts.getHash());
                System.out.println(ts.getBitcoinAdres());
                System.out.println(ts.getTijd());
                Tracker.stuurMelding(ts, prijs);
            }
        }

        this.setLaatstGecontroleerd(tijd);
        JsonHandler.slaTrackedBitcoinAdressenOp();
    }

    @Override
    public Double getSaldo(){
        setLaatstGecontroleerd(System.currentTimeMillis());
        return super.getSaldo();
    }
}
