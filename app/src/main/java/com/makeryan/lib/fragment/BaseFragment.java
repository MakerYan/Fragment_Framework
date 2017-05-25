package com.makeryan.lib.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.fragment.fragmentation_swipeback.SwipeBackFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.util.GlobUtils;
import com.makeryan.lib.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;


/**
 * Created by MakerYan on 16/3/31 17:13.
 * Email: light.yan@qq.com
 */
public abstract class BaseFragment
		extends SwipeBackFragment
		implements View.OnClickListener {

	protected Bundle mExtras;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		EventBus.getDefault()
				.register(this);
		Bundle extras = getArguments();
		if (null != extras) {
			getExtras(extras);
		}
		BasePresenter presenter = getPresenter();
		if (presenter != null && extras != null) {
			presenter.setExtras(extras);
		}
		beforeSetContentView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = null;
		if (getLayoutResID() != 0) {
			ViewDataBinding binding = initDataBinding(
					inflater,
					container
													 );
			if (binding == null) {
				view = inflater.inflate(
						getLayoutResID(),
						container,
						false
									   );
			} else {
				view = binding.getRoot();
			}
		} else {
			view = super.onCreateView(
					inflater,
					container,
					savedInstanceState
									 );
		}
		if (getToolbar(view) != null) {
			_mActivity.setSupportActionBar(getToolbar(view));
		}
		return view;
	}

	@Override
	public void onResume() {

		super.onResume();
		if (getStatusColor() != 0) {
			StatusBarUtil.setColor(
					_mActivity,
					_mActivity.getResources()
							  .getColor(getStatusColor()),
					getStatusAlpha()
								  );
		}
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {

		super.onLazyInitView(savedInstanceState);
		View view = getView();
		//这里已经做了返回键的处理
		Toolbar toolbar = getToolbar(view);
		if (toolbar != null) {
			toolbar.setTitle("");
			toolbar.setNavigationOnClickListener(v -> {
				SupportFragment topFragment = getTopFragment();
				if (topFragment != null) {
					BasePresenter presenter = topFragment.getPresenter();
					if (presenter != null && presenter.onBackPressedSupport()) {
						getSupportActivity().onBackPressed();
					} else {
						getSupportActivity().onBackPressed();
					}
				} else {
					getSupportActivity().onBackPressed();
				}
			});
		}
		assignViews(view);
		registerListeners();
		doAction();
	}

	@Override
	public void onSupportVisible() {

		super.onSupportVisible();
		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.onSupportVisible(this);
		}
	}

	@Override
	public void onSupportInvisible() {

		super.onSupportInvisible();
		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.onSupportInvisible(this);
		}
	}

	@Override
	public boolean onBackPressedSupport() {

		return super.onBackPressedSupport();
	}

	@Override
	public void setFragmentResult(int resultCode, Bundle bundle) {

		super.setFragmentResult(
				resultCode,
				bundle
							   );
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.onRequestPermissionsResult(
					requestCode,
					permissions,
					grantResults
												);
		}
		super.onRequestPermissionsResult(
				requestCode,
				permissions,
				grantResults
										);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		super.onSaveInstanceState(savedInstanceState);

		SupportFragment topFragment = getTopFragment();
		if (topFragment != null && topFragment != this) {
			topFragment.onSaveInstanceState(savedInstanceState);
		}

		SupportFragment topChildFragment = getTopChildFragment();
		if (topChildFragment != null && topChildFragment != this) {
			topChildFragment.onSaveInstanceState(savedInstanceState);
		}

		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.onSaveInstanceState(savedInstanceState);
		}
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

		super.onViewStateRestored(savedInstanceState);

		SupportFragment topFragment = getTopFragment();
		if (topFragment != null && topFragment != this) {
			topFragment.onViewStateRestored(savedInstanceState);
		}

		SupportFragment topChildFragment = getTopChildFragment();
		if (topChildFragment != null && topChildFragment != this) {
			topChildFragment.onViewStateRestored(savedInstanceState);
		}
		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.onViewStateRestored(savedInstanceState);
		}
	}


	@Override
	public void pop() {

		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.setFragmentResult();
		}
		super.pop();
	}

	@Override
	public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.setFragmentResult();
		}
		super.popTo(
				targetFragmentTag,
				includeTargetFragment,
				afterPopTransactionRunnable
				   );
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		if (getPresenter() != null) {
			getPresenter().detachView(true);
		}
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		EventBus.getDefault()
				.unregister(this);
		if (getPresenter() != null) {
			getPresenter().destroy();
		}
		GlobUtils.setViewBgAsNull((ViewGroup) getView());
	}

	@Override
	public void onDetach() {

		super.onDetach();
		// for bug ---> java.lang.IllegalStateException: Activity has been destroyed
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(
					this,
					null
									);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (getPresenter() != null) {
			getPresenter().onActivityResult(
					requestCode,
					resultCode,
					data
										   );
		}
		super.onActivityResult(
				requestCode,
				resultCode,
				data
							  );
	}

	@Override
	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {

		SupportFragment topFragment = getTopFragment();
		if (topFragment != null && topFragment != this) {
			topFragment.onFragmentResult(
					requestCode,
					resultCode,
					data
										);
		}

		SupportFragment topChildFragment = getTopChildFragment();
		if (topChildFragment != null && topChildFragment != this) {
			topChildFragment.onFragmentResult(
					requestCode,
					resultCode,
					data
											 );
		}

		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.onFragmentResult(
					requestCode,
					resultCode,
					data
									  );
		}
		super.onFragmentResult(
				requestCode,
				resultCode,
				data
							  );
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	public abstract BasePresenter getPresenter();

	/**
	 * 加载布局之前调用
	 */

	protected void beforeSetContentView() {

	}

	/**
	 * @param extras
	 * 		上层传过来的数据
	 */
	protected void getExtras(Bundle extras) {

		mExtras = extras;
	}

	/**
	 * @return 布局Id
	 */
	protected abstract int getLayoutResID();

	protected void processonCreateViewSavedInstanceState(Bundle savedInstanceState) {

		BasePresenter presenter = getPresenter();
		if (presenter != null) {
			presenter.processonCreateViewSavedInstanceState(savedInstanceState);
		}
	}

	/**
	 * 初始化DataBinding
	 *
	 * @param inflater
	 * @param parent
	 */
	protected abstract ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup parent);

	/**
	 * @return 获取状态栏颜色
	 */
	protected int getStatusColor() {

		return 0;
	}

	/**
	 * @return 获取当前Toolbar
	 */
	protected Toolbar getToolbar(View view) {

		View toolbar = view.findViewById(R.id.toolbar);
		if (toolbar != null) {
			return (Toolbar) toolbar;
		} else {
			return null;
		}
	}

	/**
	 * @return Toolbar透明色
	 */
	protected int getStatusAlpha() {

		return 0;
	}


	/**
	 * 获取控件
	 */
	protected void assignViews(View view) {


	}

	/**
	 * 注册控件监听
	 */
	protected void registerListeners() {


	}

	/**
	 * 开始处理
	 */
	protected abstract void doAction();

	/**
	 * @param event
	 * 		eventbus
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEventBus(EventBean event) {

	}

	/**
	 * @param event
	 * 		eventbus
	 */
	@Subscribe(threadMode = ThreadMode.POSTING)
	public void onEventBusByPosting(EventBean event) {

	}

	/**
	 * @param event
	 * 		eventbus
	 */
	@Subscribe(threadMode = ThreadMode.BACKGROUND)
	public void onEventBusByBackground(EventBean event) {

	}

	/**
	 * @param event
	 * 		eventbus
	 */
	@Subscribe(threadMode = ThreadMode.ASYNC)
	public void onEventBusByAsync(EventBean event) {

	}

	/**
	 * Called when a view has been clicked.
	 *
	 * @param v
	 * 		The view that was clicked.
	 */
	@Override
	public void onClick(View v) {

	}
}
