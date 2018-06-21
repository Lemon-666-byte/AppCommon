package com.utils.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @ClassName: ServiceStarter
 * @date 2016-5-7
 * @Description: 启动杀不死进程
 */
public class ServiceStarter extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceLauncher = new Intent(context, BackgroundService.class);
		context.startService(serviceLauncher);
	}
}
