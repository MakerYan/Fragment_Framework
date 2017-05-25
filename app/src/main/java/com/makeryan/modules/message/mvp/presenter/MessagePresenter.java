package com.makeryan.modules.message.mvp.presenter;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.makeryan.lib.BR;
import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentMessageBinding;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.jnis.Mk;
import com.makeryan.modules.message.listeners.MessageListener;
import com.makeryan.modules.message.ui.fragment.SiblingFragment;
import com.makeryan.modules.message.vo.MessageVO;
import com.makeryan.modules.notifications.NotificationReceiver;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by MakerYan on 2017/5/16 10:11.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message.mvp.presenter
 */
public class MessagePresenter
		extends BasePresenter
		implements MessageListener<MessageVO> {

	protected FragmentMessageBinding mBinding;

	private static final int NOTIFI_ID = 123;

	private static int mNotifiCount;

	public MessagePresenter(ISupport iSupport) {

		super(iSupport);
	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {

	}

	public void init(FragmentMessageBinding binding) {

		mBinding = binding;
		Mk        myNdk     = new Mk();
		MessageVO messageVO = new MessageVO();
		messageVO.setContent2(Mk.getStaticMessage());
		messageVO.setContent3(myNdk.getMessage());
		binding.setVariable(
				BR.bean,
				messageVO
						   );
		binding.setVariable(
				BR.listener,
				this
						   );
	}

	/**
	 * 启动一个同级Fragment
	 */
	@Override
	public void startSibling() {

		EventBus.getDefault()
				.post(new EventBean<>(
						EventType.TARGET_FRAGMENT_IN_MAIN,
						SiblingFragment.newInstance(null)
				));
	}

	/**
	 * 启动一个同级Fragment并实时刷新数据
	 *
	 * @param params
	 */
	@Override
	public void startSibling(MessageVO params) {

		EventBus.getDefault()
				.post(new EventBean<>(
						EventType.TARGET_FRAGMENT_IN_MAIN,
						SiblingFragment.newInstance(params)
				));
	}

	/**
	 * 显示一个消息推送
	 */
	@Override
	public void showNotification() {

		mNotifiCount++;
		Activity activity = getActivity();
		// 启动Receiver的Intent
		Intent broadcastIntent = new Intent(
				activity,
				NotificationReceiver.class
		);
		// 传递参数
		broadcastIntent.putExtra(
				"PAGE",
				SiblingFragment.class.getName()
								);
		// 延时点击的Intent
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				activity,
				0,
				broadcastIntent,
				PendingIntent.FLAG_UPDATE_CURRENT
																);
		// 构建Notification对象及设置内容
		NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
		builder.setSmallIcon(R.mipmap.ic_launcher_round);
		builder.setContentTitle("您有" + mNotifiCount + "条消息");
		builder.setContentText("this is content text");
		builder.setContentInfo("this is content info");
		builder.setSubText("this is sub text");
		builder.setContentIntent(pendingIntent);
		Notification notification = builder.build();
		// 点击后消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(
				NOTIFI_ID,
				notification
					  );
	}
}
