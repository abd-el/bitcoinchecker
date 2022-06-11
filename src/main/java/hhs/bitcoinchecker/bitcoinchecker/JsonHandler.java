package hhs.bitcoinchecker.bitcoinchecker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.Type;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class JsonHandler {
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject getHTTPS(URL url){
        HttpsURLConnection con = null;
        try {
            assert url != null;
            con = (HttpsURLConnection) url.openConnection();
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

        return jsonObject;
    }

    public static void slaTrackedBitcoinAdressenOp() {
        // Verkrijg de adressen
        ArrayList<TrackedBitcoinAdres> adressen = Tracker.getAdressen();

        // Zoek de file
        String file = (new File("").getAbsolutePath() + "/src/main/resources/hhs/bitcoinchecker/bitcoinchecker/storage/trackedBitcoinAdres.json");
        System.out.println(file);

        // Schrijf naar de file met een FileWriter object via gson
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson.toJson(adressen, writer);

        // Sluit de FileWriter
        try {
            assert writer != null;
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<TrackedBitcoinAdres> haalTrackedBitcoinAdressenOp() {
        // Zoek de gegeven /storage/:fileName file
        String file = (new File("").getAbsolutePath() + "/src/main/resources/hhs/bitcoinchecker/bitcoinchecker/storage/trackedBitcoinAdres.json");

        // Lees de file met een FileReader object via gson
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<ArrayList<TrackedBitcoinAdres>>(){}.getType();
        assert reader != null;
        return gson.fromJson(reader, type);
    }
}
