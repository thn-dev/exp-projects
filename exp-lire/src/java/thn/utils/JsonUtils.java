package thn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonUtils {
    private static final Logger log = Logger.getLogger(JsonUtils.class);

    public static final String JSON_FILE_EXTENSION = "json";

    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String toJSON(final Map<?, ?> data) {
        return GSON.toJson(data);
    }

    public static String toJSON(final Object obj) {
        return GSON.toJson(obj);
    }

    public static JsonArray read(final File jsonFile) {
    	JsonArray jsonArray = null;
        try (final JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(jsonFile)))) {
            final JsonParser parser = new JsonParser();
            jsonArray = parser.parse(reader).getAsJsonArray();
        }
        catch (final FileNotFoundException e) {
        	log.info("Unable to find: " + jsonFile.toString(), e);
        }
        catch (final IOException e) {
        	log.info("Unable to access: " + jsonFile.toString(), e);
        }
        return jsonArray;
    }
}
