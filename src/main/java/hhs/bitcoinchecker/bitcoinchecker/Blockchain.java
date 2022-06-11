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
        URL url = null;
        try {
            url = new URL("https://api.coinbase.com/v2/prices/spot?currency=EUR");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = JsonHandler.getHTTPS(url);

        return Double.parseDouble((jsonObject.getAsJsonObject("data")).get("amount").getAsString());
    }

    public static Double getAdresSaldo(BitcoinAdres adres) {
        URL url = null;
        try {
            url = new URL("https://api.blockchair.com/bitcoin/dashboards/address/"+ adres.getHash() +"?transaction_details=true?limit=25");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = JsonHandler.getHTTPS(url);

        jsonObject = jsonObject.getAsJsonObject("data").getAsJsonObject(adres.getHash()).getAsJsonObject("address");
        return jsonObject.get("balance").getAsDouble() / 100000000;
    }
}
