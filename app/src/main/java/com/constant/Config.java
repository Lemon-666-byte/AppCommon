package com.constant;

import android.os.Environment;
import android.support.annotation.IntDef;

import com.blankj.utilcode.util.Utils;
import com.hxky.common.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量类
 */
public class Config {


    public static class AppConfig {
        /**
         * 是否开启log
         */
        public static boolean OPEN_LOG = BuildConfig.LOG_DEBUG;
        /**
         * 内部浏览器
         */
        public static final String BROWSER_URI_SCHEME = Utils.getApp().getPackageName() + "://";
    }

    public static class Path {
        /**
         * 外部存储目录路径
         */
        private static final String LOCAL_STORE = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Android/data/" + Utils.getApp().getPackageName() + "/";
        /**
         * 相机储存地址
         */
        public static final String LOADER_IMAGE_PATH = LOCAL_STORE + "cameraImage/";
        /**
         * 日志文件存放跟目录
         */
        public static String LOG_PATH = LOCAL_STORE + "log";

        /**
         * 图片加载框架 存储目录
         */
        public static final String LOADER_IMAGE = LOCAL_STORE + "imageCache";
    }



    /**
     * 界面跳转 传值key
     */
    public static class Extras {
        public static final String id = "id";
        public static final String item = "item";
        public static final String url = "url";
        public static final String listType = "listType";
        public static final String pictureList = "pictureList";
        public static final String position = "position";
        public static final String searchKey = "searchKey";
        public static final String picture = "picture";
    }

    /**
     * 界面返回 结果码
     */
    public static class ResultCode {
        public static final int resultCode1 = 0x0011;
        public static final int resultCode2 = 0x0012;
        public static final int resultCode3 = 0x0013;
        public static final int resultCode4 = 0x0014;
    }

}
