package me.yokeyword.fragmentation;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.makeryan.lib.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation.debug.StackViewTouchListener;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;
import me.yokeyword.fragmentation.helper.internal.LifecycleHelper;

import static me.yokeyword.fragmentation.SupportFragment.SINGLETASK;
import static me.yokeyword.fragmentation.SupportFragment.SINGLETOP;
import static me.yokeyword.fragmentation.SupportFragment.STANDARD;

public class SupportActivity
		extends AutoLayoutActivity
		implements ISupport,
				   SensorEventListener {

	private static final long SHOW_SPACE = 200L;

	private FragmentationDelegate mFragmentationDelegate;

	private LifecycleHelper mLifecycleHelper;

	private ArrayList<FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks;

	private FragmentAnimator mFragmentAnimator;

	private int mDefaultFragmentBackground = 0;

	boolean mPopMultipleNoAnim = false;

	// 防抖动 是否可以点击
	private boolean mFragmentClickable = true;

	private Handler mHandler;

	private SensorManager mSensorManager;

	private boolean mNeedHideSoft;  // 隐藏软键盘

	private InputMethodManager mIMM;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mFragmentationDelegate = getFragmentationDelegate();
		mFragmentAnimator = onCreateFragmentAnimator();
		initSensorManager();
	}

	public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {

		synchronized (this) {
			if (mFragmentLifecycleCallbacks == null) {
				mFragmentLifecycleCallbacks = new ArrayList<>();
				mLifecycleHelper = new LifecycleHelper(mFragmentLifecycleCallbacks);
			}
			mFragmentLifecycleCallbacks.add(callback);
		}
	}

	public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {

		synchronized (this) {
			if (mFragmentLifecycleCallbacks != null) {
				mFragmentLifecycleCallbacks.remove(callback);
			}
		}
	}

	FragmentationDelegate getFragmentationDelegate() {

		if (mFragmentationDelegate == null) {
			mFragmentationDelegate = new FragmentationDelegate(this);
		}
		return mFragmentationDelegate;
	}

	Handler getHandler() {

		if (mHandler == null) {
			mHandler = new Handler();
		}
		return mHandler;
	}

	@Override
	public void setContentView(@LayoutRes int layoutResID) {

		super.setContentView(layoutResID);
		setStackFloatingView();
	}

	@Override
	public void setContentView(View view) {

		super.setContentView(view);
		setStackFloatingView();
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {

		super.setContentView(
				view,
				params
							);
		setStackFloatingView();
	}

	/**
	 * 获取设置的全局动画 copy
	 *
	 * @return FragmentAnimator
	 */
	public FragmentAnimator getFragmentAnimator() {

		return new FragmentAnimator(
				mFragmentAnimator.getEnter(),
				mFragmentAnimator.getExit(),
				mFragmentAnimator.getPopEnter(),
				mFragmentAnimator.getPopExit()
		);
	}

	/**
	 * 设置全局动画, 一般情况建议复写onCreateFragmentAnimator()设置
	 */
	public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {

		this.mFragmentAnimator = fragmentAnimator;
	}

	/**
	 * 构建Fragment转场动画
	 * <p/>
	 * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
	 * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
	 *
	 * @return FragmentAnimator对象
	 */
	protected FragmentAnimator onCreateFragmentAnimator() {

		return new DefaultVerticalAnimator();
	}

	/**
	 * 当Fragment根布局 没有 设定background属性时,
	 * Fragmentation默认使用Theme的android:windowbackground作为Fragment的背景,
	 * 可以通过该方法改变Fragment背景。
	 */
	protected void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {

		mDefaultFragmentBackground = backgroundRes;
	}

	/**
	 * (因为事务异步的原因) 如果你想在onCreate()中使用start/pop等 Fragment事务方法, 请使用该方法把你的任务入队
	 *
	 * @param runnable
	 * 		需要执行的任务
	 */
	public void enqueueAction(Runnable runnable) {

		getHandler().post(runnable);
	}

	/**
	 * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替
	 */
	@Override
	final public void onBackPressed() {
		// 这里是防止动画过程中，按返回键取消加载Fragment
		if (!mFragmentClickable) {
			setFragmentClickable(true);
		}

		// 获取activeFragment:即从栈顶开始 状态为show的那个Fragment
		SupportFragment activeFragment = mFragmentationDelegate.getActiveFragment(
				null,
				getSupportFragmentManager()
																				 );
		if (mFragmentationDelegate.dispatchBackPressedEvent(activeFragment)) {
			return;
		}

		onBackPressedSupport();
	}

	/**
	 * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
	 * 请尽量复写该方法,避免复写onBaadb shell pm uninstall me.yokeyword.sampleckPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
	 */
	public void onBackPressedSupport() {

		int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
		if (backStackEntryCount > 1) {
			pop();
		} else {
			finish();
		}
	}

	/**
	 * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
	 *
	 * @param containerId
	 * 		容器id
	 * @param toFragment
	 * 		目标Fragment
	 */
	@Override
	public void loadRootFragment(int containerId, SupportFragment toFragment) {

		mFragmentationDelegate.loadRootTransaction(
				getSupportFragmentManager(),
				containerId,
				toFragment
												  );
	}

	/**
	 * 以replace方式加载根Fragment
	 */
	@Override
	public void replaceLoadRootFragment(int containerId, SupportFragment toFragment, boolean addToBack) {

		mFragmentationDelegate.replaceLoadRootTransaction(
				getSupportFragmentManager(),
				containerId,
				toFragment,
				addToBack
														 );
	}

	/**
	 * 加载多个根Fragment
	 *
	 * @param containerId
	 * 		容器id
	 * @param toFragments
	 * 		目标Fragments
	 */
	@Override
	public void loadMultipleRootFragment(int containerId, int showPosition, SupportFragment... toFragments) {

		mFragmentationDelegate.loadMultipleRootTransaction(
				getSupportFragmentManager(),
				containerId,
				showPosition,
				toFragments
														  );
	}

	/**
	 * show一个Fragment,hide其他同栈所有Fragment
	 * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
	 * <p>
	 * 建议使用更明确的{@link #showHideFragment(SupportFragment, SupportFragment)}
	 *
	 * @param showFragment
	 * 		需要show的Fragment
	 */
	@Override
	public void showHideFragment(SupportFragment showFragment) {

		showHideFragment(
				showFragment,
				null
						);
	}

	/**
	 * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
	 *
	 * @param showFragment
	 * 		需要show的Fragment
	 * @param hideFragment
	 * 		需要hide的Fragment
	 */
	@Override
	public void showHideFragment(SupportFragment showFragment, SupportFragment hideFragment) {

		mFragmentationDelegate.showHideFragment(
				getSupportFragmentManager(),
				showFragment,
				hideFragment
											   );
	}

	/**
	 * 以默认模式启动目标Fragment
	 * 默认模式，可以不用写配置。在这个模式下，都会默认创建一个新的实例。因此，在这种模式下，可以有多个相同的实例，也允许多个相同Activity叠加。
	 * 例如：
	 * 若我有一个Activity名为A1, 上面有一个按钮可跳转到A1。那么如果我点击按钮，便会新启一个Activity A1叠在刚才的A1之上，
	 * 再点击，又会再新启一个在它之上……
	 * 点back键会依照栈顺序依次退出。
	 *
	 * @param toFragment
	 */
	@Override
	public void start(SupportFragment toFragment) {

		start(
				toFragment,
				STANDARD
			 );
	}

	/**
	 * 以启动目标Fragment
	 * 可以有多个实例，但是不允许多个相同Activity叠加。即，如果Activity在栈顶的时候，启动相同的Activity，不会创建新的实例，而会调用其onNewIntent方法。
	 * 例如：
	 * 若我有两个Activity名为B1,B2,两个Activity内容功能完全相同，都有两个按钮可以跳到B1或者B2，唯一不同的是B1为standard，B2为singleTop。
	 * 若我意图打开的顺序为B1->B2->B2，则实际打开的顺序为B1->B2（后一次意图打开B2，实际只调用了前一个的onNewIntent方法）
	 * 若我意图打开的顺序为B1->B2->B1->B2，则实际打开的顺序与意图的一致，为B1->B2->B1->B2。
	 *
	 * @param toFragment
	 */
	@Override
	public void startBySingleTop(SupportFragment toFragment) {

		start(
				toFragment,
				SINGLETOP
			 );
	}

	/**
	 * 以SingleTask启动目标Fragment
	 * 只有一个实例。在同一个应用程序中启动他的时候，若Activity不存在，则会在当前task创建一个新的实例，
	 * 若存在，则会把task中在其之上的其它Activity destory掉并调用它的onNewIntent方法。
	 * 如果是在别的应用程序中启动它，则会新建一个task，并在该task中启动这个Activity，singleTask允许别的Activity与其在一个task中共存，
	 * 也就是说，如果我在这个singleTask的实例中再打开新的Activity，这个新的Activity还是会在singleTask的实例的task中。
	 * <p>
	 * 例如：
	 * 若我的应用程序中有三个Activity,C1,C2,C3，三个Activity可互相启动，其中C2为singleTask模式，那么，无论我在这个程序中如何点击启动，
	 * 如：C1->C2->C3->C2->C3->C1-C2，C1,C3可能存在多个实例，但是C2只会存在一个，并且这三个Activity都在同一个task里面。
	 * 但是C1->C2->C3->C2->C3->C1-C2，这样的操作过程实际应该是如下这样的，因为singleTask会把task中在其之上的其它Activity destory掉。
	 * 操作：C1->C2	C1->C2->C3  C1->C2->C3->C2  C1->C2->C3->C2->C3->C1		C1->C2->C3->C2->C3->C1-C2
	 * 实际：C1->C2  C1->C2->C3  C1->C2  			C1->C2->C3->C1				C1->C2
	 * <p>
	 * 若是别的应用程序打开C2，则会新启一个task。
	 * 如别的应用Other中有一个activity，taskId为200，从它打开C2，则C2的taskIdI不会为200，
	 * 例如C2的taskId为201，那么再从C2打开C1、C3，则C2、C3的taskId仍为201。
	 * 注意：如果此时你点击home，然后再打开Other，发现这时显示的肯定会是Other应用中的内容，而不会是我们应用中的C1 C2 C3中的其中一个。
	 *
	 * @param toFragment
	 */
	@Override
	public void startBySingleTask(SupportFragment toFragment) {

		start(
				toFragment,
				SINGLETASK
			 );
	}

	@Override
	public void start(SupportFragment toFragment, @SupportFragment.LaunchMode int launchMode) {

		mFragmentationDelegate.dispatchStartTransaction(
				getSupportFragmentManager(),
				getTopFragment(),
				toFragment,
				0,
				launchMode,
				FragmentationDelegate.TYPE_ADD
													   );
	}

	@Override
	public void startForResult(SupportFragment toFragment, int requestCode) {

		mFragmentationDelegate.dispatchStartTransaction(
				getSupportFragmentManager(),
				getTopFragment(),
				toFragment,
				requestCode,
				STANDARD,
				FragmentationDelegate.TYPE_ADD_RESULT
													   );
	}

	@Override
	public void startWithPop(SupportFragment toFragment) {

		mFragmentationDelegate.dispatchStartTransaction(
				getSupportFragmentManager(),
				getTopFragment(),
				toFragment,
				0,
				STANDARD,
				FragmentationDelegate.TYPE_ADD_WITH_POP
													   );
	}

	/**
	 * 得到位于栈顶Fragment
	 */
	@Override
	public SupportFragment getTopFragment() {

		return mFragmentationDelegate.getTopFragment(getSupportFragmentManager());
	}

	/**
	 * 获取栈内的fragment对象
	 */
	@Override
	public <T extends SupportFragment> T findFragment(Class<T> fragmentClass) {

		return mFragmentationDelegate.findStackFragment(
				fragmentClass,
				null,
				getSupportFragmentManager()
													   );
	}

	@Override
	public <T extends SupportFragment> T findFragment(String fragmentTag) {

		FragmentationDelegate.checkNotNull(
				fragmentTag,
				"tag == null"
										  );
		return mFragmentationDelegate.findStackFragment(
				null,
				fragmentTag,
				getSupportFragmentManager()
													   );
	}

	/**
	 * @param fragmentFullName
	 * @param bundle
	 *
	 * @return
	 */
	@Override
	public SupportFragment findFragmentByReflect(String fragmentFullName, Bundle bundle) {

		SupportFragment        fragment = null;
		Class<SupportFragment> aClass   = null;
		try {
			aClass = (Class<SupportFragment>) Class.forName(fragmentFullName);
			fragment = aClass.newInstance();
			if (fragment != null && bundle != null) {
				fragment.setArguments(bundle);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return fragment;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return fragment;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return fragment;
		}
		if (fragment == null) {

		}
		return fragment;
	}

	/**
	 * 出栈
	 */
	@Override
	public void pop() {

		hideSoftInput();
		mFragmentationDelegate.back(getSupportFragmentManager());
	}

	/**
	 * 出栈到目标fragment
	 *
	 * @param targetFragmentClass
	 * 		目标fragment
	 * @param includeTargetFragment
	 * 		是否包含该fragment
	 */
	@Override
	public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {

		popTo(
				targetFragmentClass.getName(),
				includeTargetFragment
			 );
	}

	@Override
	public void popTo(String targetFragmentTag, boolean includeTargetFragment) {

		popTo(
				targetFragmentTag,
				includeTargetFragment,
				null
			 );
	}

	/**
	 * 用于出栈后,立刻进行FragmentTransaction操作
	 */
	@Override
	public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

		popTo(
				targetFragmentClass.getName(),
				includeTargetFragment,
				afterPopTransactionRunnable
			 );
	}

	@Override
	public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

		hideSoftInput();
		mFragmentationDelegate.popTo(
				targetFragmentTag,
				includeTargetFragment,
				afterPopTransactionRunnable,
				getSupportFragmentManager()
									);
	}

	/**
	 * @return activity
	 */
	@Override
	public Activity getActivity() {

		return this;
	}

	@Override
	public SupportActivity getSupportActivity() {

		return this;
	}


	private void initImm() {

		if (mIMM == null) {
			mIMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		}
	}

	/**
	 * 隐藏软键盘
	 */
	@Override
	public void hideSoftInput() {

		SupportFragment fragment = getTopFragment();
		if (fragment == null) {
			return;
		}

		if (fragment.getView() != null) {
			initImm();
			mIMM.hideSoftInputFromWindow(
					fragment.getView()
							.getWindowToken(),
					0
										);
		}
	}

	/**
	 * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
	 *
	 * @param view
	 */
	@Override
	public void showSoftInput(View view) {

		if (view == null) {
			return;
		}
		initImm();
		view.requestFocus();
		mNeedHideSoft = true;
		view.postDelayed(
				new Runnable() {

					@Override
					public void run() {

						mIMM.showSoftInput(
								view,
								InputMethodManager.SHOW_FORCED
										  );
					}
				},
				SHOW_SPACE
						);
	}

	void preparePopMultiple() {

		mPopMultipleNoAnim = true;
	}

	void popFinish() {

		mPopMultipleNoAnim = false;
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		if (mFragmentLifecycleCallbacks != null) {
			mFragmentLifecycleCallbacks.clear();
		}
		if (mSensorManager != null) {
			mSensorManager.unregisterListener(this);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// 这里是防止动画过程中，按返回键取消加载Fragment
			if (!mFragmentClickable) {
				setFragmentClickable(true);
			}
		}
		return super.onKeyDown(
				keyCode,
				event
							  );
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 防抖动(防止点击速度过快)
		if (!mFragmentClickable) {
			return true;
		}

		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 防抖动(防止点击速度过快)
	 */
	void setFragmentClickable(boolean clickable) {

		mFragmentClickable = clickable;
	}

	public int getDefaultFragmentBackground() {

		return mDefaultFragmentBackground;
	}

	void dispatchFragmentLifecycle(int lifecycle, SupportFragment fragment, Bundle bundle, boolean visible) {

		if (mLifecycleHelper == null) {
			return;
		}
		mLifecycleHelper.dispatchLifecycle(
				lifecycle,
				fragment,
				bundle,
				visible
										  );
	}

	/**
	 * 显示栈视图dialog,调试时使用
	 */
	public void showFragmentStackHierarchyView() {

		mFragmentationDelegate.showFragmentStackHierarchyView();
	}

	/**
	 * 显示栈视图日志,调试时使用
	 */
	public void logFragmentStackHierarchy(String TAG) {

		mFragmentationDelegate.logFragmentRecords(TAG);
	}

	private void setStackFloatingView() {

		if (Fragmentation.getDefault()
						 .getMode() != Fragmentation.BUBBLE) {
			return;
		}
		View root = findViewById(android.R.id.content);
		if (root instanceof FrameLayout) {
			FrameLayout     content   = (FrameLayout) root;
			final ImageView stackView = new ImageView(this);
			stackView.setImageResource(R.drawable.fragmentation_ic_stack);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT
			);
			params.gravity = Gravity.END;
			final int dp18 = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP,
					18,
					getResources().getDisplayMetrics()
															);
			params.topMargin = dp18 * 5;
			params.rightMargin = dp18;
			stackView.setLayoutParams(params);
			content.addView(stackView);
			stackView.setOnTouchListener(new StackViewTouchListener(
					stackView,
					dp18 / 4
			));
			stackView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					showFragmentStackHierarchyView();
				}
			});
		}
	}

	private void initSensorManager() {

		if (Fragmentation.getDefault()
						 .getMode() != Fragmentation.SHAKE) {
			return;
		}
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager.registerListener(
				this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL
									   );
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		int     sensorType = event.sensor.getType();
		float[] values     = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			int value = 12;
			if ((Math.abs(values[0]) >= value || Math.abs(values[1]) >= value || Math.abs(values[2]) >= value)) {
				showFragmentStackHierarchyView();
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public boolean canSwipe() {

		return true;
	}

    /*--------------------Fragment专用方法start---------------------------*/

	@Override
	public void replaceFragment(SupportFragment toFragment, boolean addToBack) {

	}

	@Override
	public SupportFragment getTopChildFragment() {

		return null;
	}

	@Override
	public SupportFragment getPreFragment() {

		return null;
	}

	@Override
	public <T extends SupportFragment> T findChildFragment(Class<T> fragmentClass) {

		return null;
	}

	@Override
	public <T extends SupportFragment> T findChildFragment(String fragmentTag) {

		return null;
	}

	@Override
	public void popChild() {

	}

	@Override
	public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {

	}

	@Override
	public void popToChild(String targetFragmentTag, boolean includeTargetFragment) {

	}

	@Override
	public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

	}

	@Override
	public void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

	}

	/**
	 * belong fragment
	 * <p>
	 * 接受Result数据 (通过startForResult的返回数据)
	 *
	 * @param resultCode
	 * @param bundle
	 */
	@Override
	public void setFragmentResult(int resultCode, Bundle bundle) {

	}

	/*--------------------Fragment专用方法end---------------------------*/

}
