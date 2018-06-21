package com.utils.jpush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.utils.LogUtils;
import com.utils.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String extraJson_RECEIVED = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtils.e("---->" + extraJson_RECEIVED);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            try {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
                String extraJson_OPENED = bundle.getString(JPushInterface.EXTRA_EXTRA);
                LogUtils.e("用户点击打开了通知---->" + extraJson_OPENED);
                if (null != extraJson_OPENED && !"".equals(extraJson_OPENED)) {
                    JSONObject jsonObj = new JSONObject(extraJson_OPENED);
                    launchPage(context, jsonObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 执行操作
     */
    private void launchPage(Context context, JSONObject jsonObject) {
        try {
            //判断app进程是否存活
            if (SystemUtils.isAppAlive(context, AppUtils.getAppPackageName())) {
                if (!isAppOnForeground(context)) {
                    launchApp(context);
                } else {
//                    String type = jsonObject.getString("type");
//                    Intent intent = new Intent();
//                    intent.setClass(context, MyMessageActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.getApplicationContext().startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void launchApp(Context context) {
        if (!isAppOnForeground(context)) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(AppUtils.getAppPackageName());
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.getApplicationContext().startActivity(launchIntent);
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            switch (key) {
                case JPushInterface.EXTRA_NOTIFICATION_ID:
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
                    break;
                case JPushInterface.EXTRA_CONNECTION_CHANGE:
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
                    break;
                case JPushInterface.EXTRA_EXTRA:
                    if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                        Log.i(TAG, "This message has no Extra data");
                        continue;
                    }
                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();
                        while (it.hasNext()) {
                            String myKey = it.next().toString();
                            sb.append("\nkey:" + key + ", value: [" +
                                    myKey + " - " + json.optString(myKey) + "]");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Get message extra JSON error!");
                    }

                    break;
                default:
//                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
                    break;
            }
        }
        return sb.toString();
    }
}