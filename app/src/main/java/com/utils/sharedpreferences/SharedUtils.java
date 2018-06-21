package com.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.utils.GsonUtils;

/**
 *
 */
public class SharedUtils {

    private static String SHARE_VERSION = "share_version";
    private static String SHARE_COMMON = "share_common";
    private static String SHARE_DATA_CACHE = "share_data_cache";
    public static String key;

    public static String getSecretKey(Context mContext) {
        if (TextUtils.isEmpty(key)) {
            key = SharedUtils.getCommon(mContext, SharedConfigs.Secret.SECRET_KEY);
        }
        return key;
    }

    public static void saveCommon(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    /**
     * @return
     */
    public static String getCommon(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void saveShareVer(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_VERSION, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    public static int getShareVer(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_VERSION, Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    /**
     * 字符串数据加密
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveAESCommon(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON,
                Context.MODE_PRIVATE);
        try {
            String secretKey = getSecretKey(context);
            if (!TextUtils.isEmpty(secretKey))
                preferences.edit().putString(key, AES.Encrypt(secretKey, value)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * @return
     */
    public static String getAESCommon(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        String value = preferences.getString(key, "");
        try {
            String secretKey = getSecretKey(context);
            if (!TextUtils.isEmpty(secretKey) && !TextUtils.isEmpty(value))
                return AES.Decrypt(secretKey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void saveBooleanCommon(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanCommon(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static boolean getBooleanCommon(Context context, String key, boolean defaultValue) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
            return preferences.getBoolean(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static void saveLongCommon(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).apply();
    }

    public static long getLongCommon(Context context, String key, long defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);

        return preferences.getLong(key, defaultValue);
    }

    public static void clearCommon(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }

    public static void saveIntegerCommon(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    public static int getIntegerCommon(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    public static int getIntegerCommon(Context context, String key, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    public static void saveLongCommon(Context context, String key, Long value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).apply();
    }

    public static long getLongCommon(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        return preferences.getLong(key, -1);
    }

    public static void saveObject(Context context, String key, Object obj) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        try {
            preferences.edit().putString(key, getAESEncrypt(context, obj)).apply();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public static <T> T getObject(Context context, String key, Class<T> classOfT) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_COMMON, Context.MODE_PRIVATE);
        try {
            String gson = preferences.getString(key, "");
            return GsonUtils.getInstance().toObject(getAESDncrypt(context, gson), classOfT);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    public synchronized static void saveObjectCache(Context context, String key, Object obj) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(SHARE_DATA_CACHE,
                    Context.MODE_PRIVATE);
            preferences.edit().putString(key, getAESEncrypt(context, obj)).apply();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObjectCache(Context context, String key, Class<T> classOfT) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_DATA_CACHE,
                Context.MODE_PRIVATE);
        try {
            String gson = preferences.getString(key, "");
            return GsonUtils.getInstance().toObject(getAESDncrypt(context, gson), classOfT);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void clearObject(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_DATA_CACHE,
                Context.MODE_PRIVATE);
        preferences.edit().remove(key).apply();
    }

    private static String getAESEncrypt(Context context, Object object) {
        try {
            String value = GsonUtils.getInstance().toString(object);
            String secretKey = getSecretKey(context);
            return AES.Encrypt(secretKey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getAESDncrypt(Context context, String value) {
        if (TextUtils.isEmpty(value))
            return "";
        String secretKey = getSecretKey(context);
        try {
            return AES.Decrypt(secretKey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
