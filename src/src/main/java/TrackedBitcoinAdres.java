import java.sql.Time;
import java.util.ArrayList;

public class TrackedBitcoinAdres extends BitcoinAdres {
    private Time laatstGecontroleerd;

    public TrackedBitcoinAdres(String naam, String adres, Double saldo, ArrayList<BitcoinTransactie> geschiedenis, Time laatstGecontroleerd) {
        super(naam, adres, saldo, geschiedenis);
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    public Time getLaatstGecontroleerd() {
        return laatstGecontroleerd;
    }

    public void setLaatstGecontroleerd(Time laatstGecontroleerd) {
        this.laatstGecontroleerd = laatstGecontroleerd;
    }

    @Override
    public Double getSaldo(){
        return super.getSaldo();
    }
}
