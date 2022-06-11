package hhs.bitcoinchecker.bitcoinchecker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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

    public BitcoinAdres(String naam, String hash, Double saldo, ArrayList<BitcoinTransactie> geschiedenis) {
        this.naam = naam;
        this.hash = hash;
        this.saldo = saldo;
        this.geschiedenis = geschiedenis;
    }

    public BitcoinAdres(String naam, String hash, Double saldo) {
        this.naam = naam;
        this.hash = hash;
        this.saldo = saldo;
    }

    public BitcoinAdres(String naam, String hash) {
        this.naam = naam;
        this.hash = hash;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public String getHash() {
        return hash;
    }

    public Double getSaldo() {
        return saldo;
    }

    public ArrayList<BitcoinTransactie> getGeschiedenis() {
        return geschiedenis;
    }

    public ArrayList<BitcoinTransactie> getGeschiedenisVanBlockchain() {
        URL myurl = null;
        try {
            myurl = new URL("https://api.blockchair.com/bitcoin/dashboards/address/"+ this.getHash() +"?transaction_details=true?limit=25");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection con = null;
        try {
            assert myurl != null;
            con = (HttpsURLConnection) myurl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert con != null;
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fullResponse = "";
        String txt = null;
        while (true){
            try {
                assert br != null;
                if ((txt = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            fullResponse = fullResponse + txt;
        }

        JsonObject jsonObject = JsonHandler.gson.fromJson(fullResponse, JsonObject.class)
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
