package com.net;

import com.app.App;
import com.constant.NetConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.net.converter.GsonConverterFactory;
import com.net.interceptor.HttpCacheInterceptor;
import com.net.interceptor.HttpHeaderInterceptor;
import com.net.interceptor.LoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络请求工具
 */
public class RetrofitUtils {

    private static volatile OkHttpClient mOkHttpClient;
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

    /**
     * 获取OkHttpClient实例
     *
     * @return mOkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitUtils.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(NetConfig.Net.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(NetConfig.Net.READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(NetConfig.Net.WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(new LoggingInterceptor())
                            .addInterceptor(new HttpHeaderInterceptor())
                            .addNetworkInterceptor(new HttpCacheInterceptor())
                            .cache(cache)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

//    /**
//     * 获取Service
//     *
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T create(Class<T> clazz) {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetConfig.HOST)
//                .client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        return retrofit.create(clazz);
//    }

    /**
     * @return ApiService
     */
    public static ApiService getInstance() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetConfig.HOST)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

}
