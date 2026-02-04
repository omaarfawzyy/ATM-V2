package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T read(String path, Class<T> clazz) {
        try (Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    // Overload to support deserializing generic types, e.g. new TypeToken<List<Foo>>(){}.getType()
    public static <T> T read(String path, Type typeOfT) {
        try (Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public static void write(String path, Object data) {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
