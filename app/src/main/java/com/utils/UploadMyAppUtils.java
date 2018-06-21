package com.utils;

import android.app.Activity;

import com.constant.NetConfig;
import com.blankj.utilcode.util.ActivityUtils;
import com.ui.main.bean.RespUpdate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @date：2016/9/10 11:08
 */
public class UploadMyAppUtils {
    private OkHttpClient mOkHttpClient;
    private static UploadMyAppUtils instance;

    /**
     * Returns singleton class instance
     */
    public static UploadMyAppUtils getInstance() {
        if (instance == null) {
            synchronized (UploadMyAppUtils.class) {
                if (instance == null) {
                    instance = new UploadMyAppUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 检测新版本
     *
     * @param isUpdate 如果为true 没有新版信息时 弹出toast提示
     */
    public void update(final boolean isUpdate) {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
//                .addInterceptor(new LoggerInterceptor("TAG"))
                    .build();
        }
        Request request = new Request.Builder().url(NetConfig.Url.UPLOAD_URL).build();
//        LogUtil.e("http", request.url().url().toString());
        // 开启异步线程访问网络
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("updateIOException-->" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String bj = response.body().string();
                    Activity topActivity = ActivityUtils.getTopActivity();
                    topActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RespUpdate respUpdate = GsonUtils.getInstance().toObject(bj, RespUpdate.class);
                            if (respUpdate != null) {
                                UpdateManager updateManager = new UpdateManager();
                                updateManager.checkIsNeedUpdate(respUpdate, isUpdate);
                            }
                        }
                    });
                }
            }
        });

    }
}
