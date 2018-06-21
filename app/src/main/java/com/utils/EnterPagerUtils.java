package com.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.App;
import com.blankj.utilcode.util.ActivityUtils;


public class EnterPagerUtils {
    /**
     * 进入页面
     *
     * @param cla 启动的页面
     */
    public static void enterPage(Class<?> cla) {
        if (cla == null) {
            return;
        }
        enterPage(cla, null);
    }

    /**
     * 进入页面
     *
     * @param cla    要启动的页面
     * @param bundle 要传递的参数
     */
    public static void enterPage(Class<?> cla, Bundle bundle) {
        if (cla == null) {
            return;
        }
        enterPageForResult(cla, bundle, 0);
    }

    /**
     * 进入页面
     *
     * @param cla
     * @param requestCode 请求码
     */
    public static void enterPageForResult(Class<?> cla, int requestCode) {
        if (cla == null) {
            return;
        }
        enterPageForResult(cla, null, requestCode);
    }

    /**
     * 进入页面
     *
     * @param cla
     * @param bundle
     * @param requestCode 请求码
     */
    public static void enterPageForResult(Class<?> cla, Bundle bundle, int requestCode) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (cla == null || topActivity == null) {
            return;
        }
        Intent intent = new Intent(App.getAppContext(), cla);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode > 0) {
            topActivity.startActivityForResult(intent, requestCode);
        } else {
            topActivity.startActivity(intent);
        }
    }

}
