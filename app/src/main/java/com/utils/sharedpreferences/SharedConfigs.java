package com.utils.sharedpreferences;

/**
 * SharedPreferences 保存的key
 */
public class SharedConfigs {

    /**
     * 加密串
     */
    public class Secret {
        public static final String SECRET_KEY = "secret_key";
    }

    /**
     * 用户信息 key
     */
    public static class UserData {
        public static final String SessionKey = "SessionKey";
        public static final String UserId = "UserID";
        public static final String UserName = "UserName";
        public static final String AccountName = "AccountName";
        public static final String PassWord = "PassWord";
    }

}
