package com.makeryan.lib.activity;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.antfortune.freeline.FreelineCore;
import com.hss01248.dialog.StyledDialog;
import com.makeryan.lib.BuildConfig;
import com.makeryan.lib.fragment.fragmentation.Fragmentation;
import com.makeryan.lib.fragment.fragmentation.helper.ExceptionHandler;
import com.makeryan.lib.util.GlobUtils;
import com.makeryan.lib.util.ImageUtil;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.socks.library.KLog;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by MakerYan on 16/4/8 22:32.
 * Email: light.yan@qq.com
 */
public class BaseApplication
		extends MultiDexApplication {

	protected static BaseApplication mApplication;

	//各个平台的配置，建议放在全局Application或者程序入口
	{
		//微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
		//		PlatformConfig.setWeixin(
		//				"wx01330e6a2e6a64c4",
		//				"6195cc83d8fa1469ce0cc42552ef5f01"
		//								);
	}

	private Application.ActivityLifecycleCallbacks mLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {

		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

		}

		@Override
		public void onActivityStarted(Activity activity) {

		}

		@Override
		public void onActivityResumed(Activity activity) {
			// TODO: 2017/4/1 在这里获取Activity
			KLog.e(activity.getComponentName()
						   .getShortClassName() + "执行onActivityResumed");
			GlobUtils.init(activity);
			StyledDialog.init(activity);
		}

		@Override
		public void onActivityPaused(Activity activity) {

		}

		@Override
		public void onActivityStopped(Activity activity) {

		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			// TODO: 2017/4/1 在这里销毁Activity
		}
	};

	public static BaseApplication getApplication() {

		return mApplication;
	}

	public static BaseApplication getInstance() {

		return mApplication;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		FreelineCore.init(this);
		// LocationUtils.init(this);
		StyledDialog.init(this);
		mApplication = this;
		Fragmentation.builder()
					 // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
					 .stackViewMode(Fragmentation.NONE)
					 // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
					 // false时，不会抛出，会捕获，可以在handleException()里监听到
					 .debug(BuildConfig.DEBUG)
					 // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
					 // 建议在回调处上传至我们的Crash检测服务器
					 .handleException(new ExceptionHandler() {

						 @Override
						 public void onException(Exception e) {
							 // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
							 // Bugtags.sendException(e);
							 KLog.d(e);
						 }
					 })
					 .install();

		AutoLayoutConifg.getInstance()
						.useDeviceSize()
						.init(this);
		FlowManager.init(new FlowConfig.Builder(this).build());
		GlobUtils.init(this);
		ImageUtil.init(this);
		registerActivityLifecycleCallbacks(mLifecycleCallbacks);
		//开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
		//		Config.DEBUG = true;
		//		UMShareAPI.get(this);
		KLog.init(BuildConfig.DEBUG);
	}

	@Override
	public void onTerminate() {

		super.onTerminate();
		GlobUtils.destroy();
		ImageUtil.destroy();
		FlowManager.destroy();
		// LocationUtils.destroy();
	}
}