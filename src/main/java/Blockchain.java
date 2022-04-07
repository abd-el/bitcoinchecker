import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Blockchain {
    private static Gson gson = new Gson();

    public static Double getBitcoinPrijs() throws IOException {
        URL myurl = new URL("https://api.coinbase.com/v2/prices/spot?currency=EUR");

        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String fullResponse = "";
        String i;
        while ((i = br.readLine()) != null){
            fullResponse = fullResponse + i;
        }

        JsonObject jsonObject = gson.fromJson( fullResponse, JsonObject.class);
        double amount = Double.parseDouble((jsonObject.getAsJsonObject("data")).get("amount").getAsString());

        return amount;
    }

    public static Double getAdresSaldo(BitcoinAdres adres) throws IOException {
        URL myurl = new URL("https://api.blockchair.com/bitcoin/dashboards/address/"+ adres.getAdres() +"?transaction_details=true?limit=25");

        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String fullResponse = "";
        String i;
        while ((i = br.readLine()) != null){
            fullResponse = fullResponse + i;
        }

        JsonObject jsonObject = gson.fromJson(fullResponse, JsonObject.class);
        jsonObject = jsonObject.getAsJsonObject("data").getAsJsonObject(adres.getAdres()).getAsJsonObject("address");
        Double saldo = jsonObject.get("balance").getAsDouble() / 100000000;

        return saldo;
    }

    public static ArrayList<BitcoinTransactie> getAdresGeschiedenis(BitcoinAdres adres) throws IOException, ParseException {
        URL myurl = new URL("https://api.blockchair.com/bitcoin/dashboards/address/"+ adres.getAdres() +"?transaction_details=true?limit=25");

        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String fullResponse = "";
        String txt;
        while ((txt = br.readLine()) != null){
            fullResponse = fullResponse + txt;
        }

        JsonObject jsonObject = gson.fromJson(fullResponse, JsonObject.class)
                .getAsJsonObject("data")
                .getAsJsonObject(adres.getAdres());

        JsonArray jsonArray = jsonObject.getAsJsonArray("transactions");
        ArrayList<BitcoinTransactie> transacties = new ArrayList<>();

        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jObj = jsonArray.get(i).getAsJsonObject();

            String hash = jObj.get("hash").getAsString();
            Double change = jObj.get("balance_change").getAsDouble() / 100000000;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = format.parse(jObj.get("time").getAsString());
            long tijd = date.getTime();

            BitcoinTransactie transactie = new BitcoinTransactie(adres, hash, tijd, change);
            transacties.add(transactie);
        }

        return transacties;
    }
}