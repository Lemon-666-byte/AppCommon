package com.constant;

import com.hxky.common.BuildConfig;

/**
 * 接口请求地址
 *
 * @author yang
 */
public class NetConfig {
    /**
     * 服务器主机测试地址
     */
    public static final String HOST = BuildConfig.HOST;

    public class Net {
        /**
         * 超时时间
         */
        public static final long CONNECT_TIMEOUT = 60L;
        public static final long READ_TIMEOUT = 10L;
        public static final long WRITE_TIMEOUT = 10L;
    }

    /**
     * 每页请求的条数
     */
    public class PageSize {
        public static final int SMALL = 10;
        public static final int NORMAL = 20;
        public static final int LARGE = 50;
    }

    /**
     * 网络请求参数
     */
    public static class Url {
        /**
         * 测试 http://47.93.80.92:8090/Android/version.js
         * 正式 http://47.93.80.92:8088/Android/version.js
         */
        public static final String UPLOAD_URL = HOST + "Android/version.js";
        /**
         * 上传照片图片
         */
        public static final String PhotoUpload = HOST + "api/Account/PhotoUpload";

    }

    /**
     * 任务 id
     */
    public static class Task {
        public static final int MSG_ERROR = 0x1000;
    }

}
