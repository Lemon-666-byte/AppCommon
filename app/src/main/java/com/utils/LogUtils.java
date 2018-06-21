package com.utils;

import android.support.annotation.StringRes;
import android.util.Log;

import com.constant.Config;
import com.blankj.utilcode.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 打印日志工具类
 */
public class LogUtils {

    private static final String defaultTag = "Tag";

    /**
     * JSON的缩进量.
     */
    private static final int JSON_INDENT = 4;

    public static void i(String tag, String msg) {
        if (Config.AppConfig.OPEN_LOG && !StringUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (Config.AppConfig.OPEN_LOG && !StringUtils.isEmpty(msg)) {
            Log.i(defaultTag, msg);
        }
    }

    public static void i(String tag, int msg) {
        i(tag, String.valueOf(msg));
    }

    public static void i(String tag, boolean msg) {
        i(tag, String.valueOf(msg));
    }

    public static void e(String tag, String msg) {
        if (Config.AppConfig.OPEN_LOG && !StringUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (Config.AppConfig.OPEN_LOG && !StringUtils.isEmpty(msg)) {
            Log.e(defaultTag, msg);
        }
    }

    public static void e(@StringRes int msg) {
        if (Config.AppConfig.OPEN_LOG) {
            Log.e(defaultTag, String.valueOf(msg));
        }
    }

    public static void e(String tag, int msg) {
        e(tag, String.valueOf(msg));
    }

    public static void e(String tag, boolean msg) {
        e(tag, String.valueOf(msg));
    }

    public boolean getIsDebug() {
        return Config.AppConfig.OPEN_LOG;
    }

    public static void json(String tag, String message) {
        String json;
        try {
            if (message.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(message);
                json = jsonObject.toString(JSON_INDENT);
            } else if (message.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(message);
                json = jsonArray.toString(JSON_INDENT);
            } else {
                json = message;
            }
        } catch (JSONException e) {
            json = message;
        }
        e(tag, json);
    }

}
