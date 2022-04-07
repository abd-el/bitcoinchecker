import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonHandler {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void slaOp(ArrayList<TrackedBitcoinAdres> object, String fileName) {
        // Zoek de gegeven /storage/:fileName file
        String file = (new File("").getAbsolutePath() + "/src/" + fileName);

        // Schrijf naar de file met een FileWriter object via gson
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(object, writer);

        // Sluit de FileWriter
        try {
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

    public static Object haalOp(String fileName) {
        // Zoek de gegeven /storage/:fileName file
        String file = (new File("").getAbsolutePath() + "/src/" + fileName);

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