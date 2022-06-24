package hhs.bitcoinchecker.bitcoinchecker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract class BitcoinAdres {
    private String naam;
    private String hash;
    private Double saldo;
    private ArrayList<BitcoinTransactie> geschiedenis;
    private boolean tracked;

    public BitcoinAdres(String naam, String hash, boolean tracked) {
        this.naam = naam;
        this.hash = hash;
        this.tracked = tracked;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean getTracked() { return tracked; }

    public String getNaam() {
        return naam;
    }

    public String getHash() {
        return hash;
    }

    public Double getSaldo() {
        return saldo;
    }

    protected long laatstGecontroleerd;

    public long getLaatstGecontroleerd() {
        return 0;
    }

    public void setLaatstGecontroleerd(long laatstGecontroleerd) {

    }

    public void controleer() {
        if(this.getTracked()){
            Double prijs = Blockchain.getBitcoinPrijs();

            ArrayList<BitcoinTransactie> geschiedenis = this.getGeschiedenisVanBlockchain();
            long tijd = System.currentTimeMillis();

            for (BitcoinTransactie ts : geschiedenis) {
                if (this.getLaatstGecontroleerd() < ts.getTijd()) {
                    Tracker.stuurMelding(ts, prijs);
                }
            }

            this.setLaatstGecontroleerd(tijd);
            JsonHandler.slaTrackedBitcoinAdressenOp();
        } else {
            this.setLaatstGecontroleerd(0);
        }
    }

    public ArrayList<BitcoinTransactie> getGeschiedenisVanBlockchain() {
        URL url = null;
        try {
            url = new URL("https://api.blockchair.com/bitcoin/dashboards/address/"+ this.getHash() +"?transaction_details=true?limit=25");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = JsonHandler.getHTTPS(url)
                .getAsJsonObject("data")
                .getAsJsonObject(this.getHash());

        JsonArray jsonArray = jsonObject.getAsJsonArray("transactions");

        ArrayList<BitcoinTransactie> transacties = new ArrayList<>();

        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jObj = jsonArray.get(i).getAsJsonObject();

            String hash = jObj.get("hash").getAsString();
            Double change = jObj.get("balance_change").getAsDouble() / 100000000;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = null;
            try {
                date = format.parse(jObj.get("time").getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            long tijd = date.getTime();

            BitcoinTransactie transactie = new BitcoinTransactie(this, hash, tijd, change);
            transacties.add(transactie);
        }

        return transacties;
    }
}
