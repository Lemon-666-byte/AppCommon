package com.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.hxky.common.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;
import com.utils.LogUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Application
 *
 * @author yang
 */
public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Utils.init(this);
        JPushInterface.init(this);
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         */
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_ID, true);
        initCrash();
        if (com.squareup.leakcanary.LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        com.squareup.leakcanary.LeakCanary.install(this);

    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static App getInstance() {
        return mInstance;
    }



    @SuppressLint("MissingPermission")
    private void initCrash() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                LogUtils.e(crashInfo);
                AppUtils.relaunchApp();
            }
        });
    }

}
