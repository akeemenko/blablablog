package blablablog.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author UNKNOWN on 9/26/16.
 */
public class LazyGson {
    private static final Gson gson = new Gson();

    public static synchronized String objectToGson(Object object) {
        return gson.toJson(object);
    }

    public static synchronized Object getJsonToObject(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public static synchronized Object getJsonToObject(Object json, Class clazz) {
        return gson.fromJson(json.toString(), clazz);
    }

    public static synchronized Object getJsonToObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static synchronized <T> T fromJson(String content_json, Class<T> classOfT) {
        return gson.fromJson(content_json, classOfT);
    }
}
