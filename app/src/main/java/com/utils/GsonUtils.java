package com.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Google旗下的Json与Java映射工具类
 */
public class GsonUtils {

    private Gson gson = null;

    private volatile static GsonUtils instance;

    /**
     * Returns singleton class instance
     */
    public static GsonUtils getInstance() {
        if (instance == null) {
            synchronized (GsonUtils.class) {
                if (instance == null) {
                    instance = new GsonUtils();
                }
            }
        }
        return instance;
    }

    private GsonUtils() {
        gson = new GsonBuilder()
                .registerTypeAdapter(String.class, stringTypeAdapter)
                .serializeNulls().create();
    }


    //自定义Strig适配器
    private static final TypeAdapter<String> stringTypeAdapter = new TypeAdapter<String>() {
        @Override
        public void write(JsonWriter out, String value) throws IOException {
            if (value == null) {
                // 在这里处理null改为空字符串
                out.value("");
                return;
            }
            out.value(value);
        }

        @Override
        public String read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            }
            return in.nextString();
        }
    };

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public String toString(Object object) {
        if (gson != null) {
            return gson.toJson(object);
        }
        return null;
    }

    /**
     * 转成bean
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public <T> T toObject(String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> toList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param jsonString
     * @return
     */
    public <T> List<Map<String, T>> toListMaps(String jsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param jsonString
     * @return
     */
    public <T> Map<String, T> toMaps(String jsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}