package com.makeryan.modules.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.makeryan.lib.ContainerActivity;
import com.makeryan.lib.util.ToastUtil;

public class NotificationReceiver
		extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
		// 接收MessagePresenter->showNotification传送过来的信息
		ToastUtil.showMessage(
				context,
				intent.getStringExtra("PAGE")
							 );
		ContainerActivity.start(
				context,
				intent.getStringExtra("PAGE")
							   );
	}
}
