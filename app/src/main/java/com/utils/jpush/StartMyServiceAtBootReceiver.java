package com.utils.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author
 * @ClassName: StartMyServiceAtBootReceiver
 * @date 2016-5-7
 * @Description: 监听应用进程结束通知
 */
public class StartMyServiceAtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            context.startService(new Intent(context, BackgroundService.class));
        }
    }
}