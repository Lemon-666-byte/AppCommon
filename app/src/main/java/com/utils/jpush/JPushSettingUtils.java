package com.utils.jpush;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @ClassName: JPushSettingUtils
 * @date 2016-5-7
 * @Description: 设置JPush工具类
 */
public class JPushSettingUtils {

    private static final String TAG = "JPush";
    private static JPushSettingUtils instance;
    private Context mContext;

    public static JPushSettingUtils getInstance() {
        if (null == instance) {
            synchronized (JPushSettingUtils.class) {
                if (null == instance) {
                    instance = new JPushSettingUtils(Utils.getApp());
                }
            }
        }
        return instance;
    }

    private JPushSettingUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 设置tag标签
     *
     * @param tagSet
     */
    public void setTag(Set<String> tagSet) {
        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }

    /**
     * 设置别名
     *
     * @param alias
     */
    public void setAlias(String alias) {
        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    /**
     * 停止推送
     */
    public void stopPush() {
        JPushInterface.stopPush(mContext);
    }

    /**
     * @return 检查 Push Service 是否已经被停止
     */
    public boolean isPushStopped() {
        return JPushInterface.isPushStopped(mContext);
    }

    /**
     * 恢复推送
     */
    public void resumePush() {
        JPushInterface.resumePush(mContext);
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (isConnected()) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (isConnected()) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(mContext, null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    public boolean isConnected() {
        ConnectivityManager conn = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

//    public void setCustomPushNotification() {
//
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(mContext);
//        builder.statusBarDrawable = R.mipmap.logo;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
//        builder.notificationDefaults = Notification.DEFAULT_SOUND
//                | Notification.DEFAULT_VIBRATE
//                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
//        JPushInterface.setPushNotificationBuilder(1, builder);
//
////        CustomPushNotificationBuilder builder2 = new
////                CustomPushNotificationBuilder(mContext,
////                R.layout.customer_notitfication_layout,
////                R.id.icon,
////                R.id.title,
////                R.id.text);
////        // 指定定制的 Notification Layout
////        builder2.statusBarDrawable = R.mipmap.icon_plogo;
////        // 指定最顶层状态栏小图标
////        builder2.layoutIconDrawable = R.mipmap.icon_logo;
////        // 指定下拉状态栏时显示的通知图标
////        JPushInterface.setPushNotificationBuilder(2, builder2);
//    }
}