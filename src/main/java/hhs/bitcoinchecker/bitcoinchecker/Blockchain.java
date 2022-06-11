package hhs.bitcoinchecker.bitcoinchecker;

import com.google.gson.Gson;
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

public class Blockchain {
    public static Double getBitcoinPrijs() {
        URL myurl = null;
        try {
            myurl = new URL("https://api.coinbase.com/v2/prices/spot?currency=EUR");
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
        String i = null;
        while (true){
            try {
                assert br != null;
                if ((i = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            fullResponse = fullResponse + i;
        }

        JsonObject jsonObject = JsonHandler.gson.fromJson( fullResponse, JsonObject.class);

        return Double.parseDouble((jsonObject.getAsJsonObject("data")).get("amount").getAsString());
    }

    public static Double getAdresSaldo(BitcoinAdres adres) {
        URL myurl = null;
        try {
            myurl = new URL("https://api.blockchair.com/bitcoin/dashboards/address/"+ adres.getHash() +"?transaction_details=true?limit=25");
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
        String i = null;
        while (true){
            try {
                assert br != null;
                if ((i = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            fullResponse = fullResponse + i;
        }

        JsonObject jsonObject = JsonHandler.gson.fromJson(fullResponse, JsonObject.class);
        jsonObject = jsonObject.getAsJsonObject("data").getAsJsonObject(adres.getHash()).getAsJsonObject("address");

        return jsonObject.get("balance").getAsDouble() / 100000000;
    }
}
