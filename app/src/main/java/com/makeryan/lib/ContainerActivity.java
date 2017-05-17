package com.makeryan.lib;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.makeryan.lib.activity.BaseActivity;
import com.makeryan.lib.constants.RequestCodeConstants;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.main.ui.fragment.MainFragment;

import com.makeryan.lib.fragment.fragmentation.SupportFragment;

public class ContainerActivity
		extends BaseActivity {

	/**
	 * 页面KEY
	 */
	public static final String PAGE = "page";

	public static void start(Context context, SupportFragment fragment) {

		Intent starter = new Intent(
				context,
				ContainerActivity.class
		);
		Bundle bundle = fragment.getArguments();
		if (bundle == null) {
			bundle = new Bundle();
		}
		bundle.putString(
				PAGE,
				fragment.getClass()
						.getName()
						);
		starter.putExtras(bundle);
		context.startActivity(starter);
	}

	public static void start(Context context, String supportFragmentFullName) {

		Intent starter = new Intent(
				context,
				ContainerActivity.class
		);
		Bundle bundle = new Bundle();
		bundle.putString(
				PAGE,
				supportFragmentFullName
						);
		starter.putExtras(bundle);
		context.startActivity(starter);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {

		if (savedInstanceState == null) {
			if (mExtras == null) {
				mExtras = new Bundle();
			}
			String name = mExtras.getString(
					PAGE,
					""
										   );
			SupportFragment fragment = findFragmentByReflect(
					name,
					null
															);
			if (fragment == null) {

				loadRootFragment(
						R.id.container,
						MainFragment.newInstance()
								);
			} else {
				fragment.setArguments(mExtras);
				loadRootFragment(
						R.id.container,
						fragment
								);
			}
		}
	}


	/**
	 * 实例化Presenter
	 */
	@Override
	public BasePresenter getPresenter() {

		return null;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.activity_main;
	}

	/**
	 * @return 是否使用Databinding绑定了布局
	 */
	@Override
	protected ViewDataBinding initDatabinding() {

		return null;
	}

	/**
	 * 开始处理
	 */
	@Override
	protected void doAction() {

	}

	@Override
	public void onEventBus(EventBean event) {

		if (event.getTag() == EventType.TARGET_FRAGMENT_IN_MAIN) {
			startForResult(
					event.targetFragment,
					RequestCodeConstants.MAIN_TO_TARGET_FRAGMENT
						  );
		}
	}

	@Override
	public boolean canSwipe() {

		return false;
	}
}
