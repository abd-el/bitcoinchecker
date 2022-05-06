package hhs.bitcoinchecker.bitcoinchecker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class JsonHandler {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void slaTrackedBitcoinAdressenOp() {
        // Verkrijg de adressen
        ArrayList<TrackedBitcoinAdres> adressen = Tracker.getAdressen();

        // Zoek de file
        String file = Objects.requireNonNull(BitcoinChecker.class.getResource("storage/trackedBitcoinAdres.json")).getFile();

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

    public static ArrayList<TrackedBitcoinAdres> haalTrackedBitcoinAdressenOp() throws IOException, ParseException {
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
        ArrayList<TrackedBitcoinAdres> adressen = gson.fromJson(reader, type);

        for(TrackedBitcoinAdres adres : adressen){
            adres.setGeschiedenis(Blockchain.getAdresGeschiedenis(adres));
        }

        return adressen;
    }
}
