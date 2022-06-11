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

    public BitcoinAdres(String naam, String hash) {
        this.naam = naam;
        this.hash = hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
