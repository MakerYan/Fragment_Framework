package com.makeryan.lib.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;

import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.util.FlowUtils;
import com.makeryan.modules.database.UserInfo;
import com.makeryan.lib.R;
import com.makeryan.modules.main.ui.fragment.MainFragment;

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
	protected void beforeOnCreate() {

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

			UserInfo userInfo = FlowUtils.getUserInfo();
			if (fragment == null) {
				if (TextUtils.isEmpty(userInfo.getToken())) {
					fragment = MainFragment.newInstance();
				} else {
					fragment = MainFragment.newInstance();
				}
				loadRootFragment(
						R.id.container,
						fragment
								);
			} else {
				if (TextUtils.isEmpty(userInfo.getToken())) {
					fragment = MainFragment.newInstance();
					loadRootFragment(
							R.id.container,
							fragment
									);
				} else {
					fragment.setArguments(mExtras);
					if (findFragment(fragment.getClass()) != null) {
						showHideFragment(fragment);
					} else {
						loadRootFragment(
								R.id.container,
								fragment
										);
					}
				}
			}
		}
	}

	@Override
	protected void onStart() {

		super.onStart();
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
	public boolean canSwipe() {

		return false;
	}
}
