package com.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @ClassName:
 * @date 2016-5-11
 * @Description:
 */
public class SystemUtils {
    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /**
     * 资源释放
     *
     * @param activity 当前 activity
     * @param resId    空资源文件ID
     */
    public static void release(Activity activity, int resId) {
        if (activity == null) return;
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field == null) continue;
            if (Modifier.isFinal(field.getModifiers())) continue;
            //关闭安全检查
            field.setAccessible(true);
            try {
                String typeString = field.getType().toString();
                if (typeString.startsWith("class")) {
                    field.set(activity, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (resId != 0)
            activity.setContentView(resId);
    }

//    public static void startDetailActivity(Context context, String name, String price,
//                                           String detail){
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("name", name);
//        intent.putExtra("price", price);
//        intent.putExtra("detail", detail);
//        context.startActivity(intent);
//    }
}
