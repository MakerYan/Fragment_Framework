package com.makeryan.lib.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.makeryan.lib.R;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.util.StatusBarUtil;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by MakerYan on 16/3/31 16:55.
 * Email: light.yan@qq.com
 */
public abstract class BaseActivity
		extends SwipeBackActivity
		implements View.OnClickListener {

	protected Bundle mExtras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			getExtras(extras);
		}
		BasePresenter presenter = getPresenter();
		if (presenter != null && extras != null) {
			presenter.setExtras(extras);
		}
		beforeSetContentView();

		EventBus.getDefault()
				.register(this);

		if (getLayoutResID() != 0) {
			ViewDataBinding binding = initDatabinding();
			if (binding == null) {
				setContentView(getLayoutResID());
			}
			assignViews();
			if (getToolbar() != null) {
				setSupportActionBar(getToolbar());
			}
			initWidget(savedInstanceState);
			registerListener();
		}
		enqueueAction(() -> {
			doAction();
		});
		registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

			/**
			 * Called when the Fragment is called onSaveInstanceState().
			 *
			 * @param fragment
			 * @param outState
			 */
			@Override
			public void onFragmentSaveInstanceState(SupportFragment fragment, Bundle outState) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onSaveInstanceState()");
			}

			/**
			 * Called when the Fragment is called onEnterAnimationEnd().
			 *
			 * @param fragment
			 * @param savedInstanceState
			 */
			@Override
			public void onFragmentEnterAnimationEnd(SupportFragment fragment, Bundle savedInstanceState) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onEnterAnimationEnd()");
			}

			/**
			 * Called when the Fragment is called onLazyInitView().
			 *
			 * @param fragment
			 * @param savedInstanceState
			 */
			@Override
			public void onFragmentLazyInitView(SupportFragment fragment, Bundle savedInstanceState) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onLazyInitView()");
			}

			/**
			 * Called when the Fragment is called onSupportVisible().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentSupportVisible(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onSupportVisible()");
			}

			/**
			 * Called when the Fragment is called onSupportInvisible().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentSupportInvisible(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onSupportInvisible()");
			}

			/**
			 * Called when the Fragment is called onAttach().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentAttached(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onAttach()");
			}

			/**
			 * Called when the Fragment is called onCreate().
			 *
			 * @param fragment
			 * @param savedInstanceState
			 */
			@Override
			public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onCreate()");
			}

			/**
			 * Called when the Fragment is called ViewCreated().
			 *
			 * @param fragment
			 * @param savedInstanceState
			 */
			@Override
			public void onFragmentViewCreated(SupportFragment fragment, Bundle savedInstanceState) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called ViewCreated()");
			}

			/**
			 * Called when the Fragment is called onActivityCreated().
			 *
			 * @param fragment
			 * @param savedInstanceState
			 */
			@Override
			public void onFragmentActivityCreated(SupportFragment fragment, Bundle savedInstanceState) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onActivityCreated()");
			}

			/**
			 * Called when the Fragment is called onStart().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentStarted(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onStart()");
			}

			/**
			 * Called when the Fragment is called onResume().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentResumed(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onResume()");
			}

			/**
			 * Called when the Fragment is called onPause().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentPaused(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onPause()");
			}

			/**
			 * Called when the Fragment is called onStop().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentStopped(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onStop()");
			}

			/**
			 * Called when the Fragment is called onDestroyView().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentDestroyView(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onDestroyView()");
			}

			/**
			 * Called when the Fragment is called onDestroy().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentDestroyed(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onDestroy()");
			}

			/**
			 * Called when the Fragment is called onDetach().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentDetached(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + "called onDetach()");
			}
		});
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	protected void onResume() {

		super.onResume();
		enqueueAction(() -> {
			if (getStatusColor() != 0) {
				StatusBarUtil.setColor(
						this,
						getResources().getColor(getStatusColor()),
						getStatusBarAlpha()
									  );
			}
			if (getPresenter() != null) {
				getPresenter().resume();
			}
		});

	}

	@Override
	protected void onPause() {

		super.onPause();
		if (getPresenter() != null) {
			getPresenter().pause();
		}
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		EventBus.getDefault()
				.unregister(this);
		if (getPresenter() != null) {
			getPresenter().detachView(true);
			getPresenter().destroy();
		}
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		if (getToolbar() != null) {
			getToolbar().setNavigationOnClickListener(view -> setNavigationOnClickListener(view));
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		setOnMenuItemClickListener(item);

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean isFinishing() {

		return super.isFinishing();
	}

	@Override
	public void finish() {

		if (getPresenter() != null) {
			getPresenter().finish();
		}
		super.finish();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		return super.dispatchTouchEvent(ev);
	}

	/**
	 * @return com.chenggua.cg.app.lib.activity.BaseActivity
	 */
	@Override
	public SupportActivity getSupportActivity() {

		return this;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		super.onRequestPermissionsResult(
				requestCode,
				permissions,
				grantResults
										);
		SupportFragment topFragment = getTopFragment();
		if (topFragment != null) {
			topFragment.onRequestPermissionsResult(
					requestCode,
					permissions,
					grantResults
												  );
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		SupportFragment topFragment = getTopFragment();
		if (topFragment != null) {
			topFragment.onActivityResult(
					requestCode,
					resultCode,
					data
										);
		}

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

	/**
	 * @param extras
	 * 		上层传过来的数据
	 */
	protected void getExtras(Bundle extras) {

		mExtras = extras;
	}

	/**
	 * 实例化Presenter
	 */
	public abstract BasePresenter getPresenter();

	/**
	 * 加载布局之前调用
	 */
	protected void beforeSetContentView() {

	}

	/**
	 * @return 布局Id
	 */
	protected abstract int getLayoutResID();

	/**
	 * @return 是否使用Databinding绑定了布局
	 */
	protected abstract ViewDataBinding initDatabinding();

	/**
	 * @return 状态栏颜色
	 */
	protected int getStatusColor() {

		return R.color.colorPrimaryDark;
	}

	/**
	 * @return Toolbar透明色
	 */
	protected int getStatusBarAlpha() {

		return 0;
	}

	/**
	 * @return 获取当前Toolbar
	 */
	protected android.support.v7.widget.Toolbar getToolbar() {

		View view = findViewById(R.id.toolbar);
		if (view != null) {
			return (Toolbar) view;
		} else {
			return null;
		}
	}

	/**
	 * @return 获取Toolbar Menu监听
	 */
	protected void setOnMenuItemClickListener(MenuItem item) {

	}

	/**
	 * @param view
	 * 		navigation click
	 */
	protected void setNavigationOnClickListener(View view) {

		SupportFragment topFragment = getTopFragment();
		if (topFragment != null && !topFragment.onBackPressedSupport()) {
			topFragment.pop();
		}
	}

	/**
	 * 获取控件
	 */
	protected void assignViews() {

	}

	/**
	 * 控件赋值
	 *
	 * @param savedInstanceState
	 */
	protected void initWidget(Bundle savedInstanceState) {

	}

	/**
	 * 注册控件监听
	 */
	protected void registerListener() {

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
