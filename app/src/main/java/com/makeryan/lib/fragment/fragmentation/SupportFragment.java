package com.makeryan.lib.fragment.fragmentation;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;

import com.makeryan.lib.fragment.fragmentation.anim.FragmentAnimator;
import com.makeryan.lib.fragment.fragmentation.helper.internal.AnimatorHelper;
import com.makeryan.lib.fragment.fragmentation.helper.internal.LifecycleHelper;
import com.makeryan.lib.fragment.fragmentation.helper.internal.OnFragmentDestoryViewListener;
import com.makeryan.lib.fragment.fragmentation.helper.internal.ResultRecord;
import com.makeryan.lib.fragment.fragmentation.helper.internal.TransactionRecord;
import com.makeryan.lib.fragment.fragmentation.helper.internal.VisibleDelegate;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.utils.PermissionsUtils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SupportFragment
		extends Fragment
		implements ISupport {

	// LaunchMode
	public static final int STANDARD = 0;

	public static final int SINGLETOP = 1;

	public static final int SINGLETASK = 2;

	// ResultCode
	public static final int RESULT_CANCELED = 0;

	public static final int RESULT_OK = -1;

	private static final long SHOW_SPACE = 200L;

	private static final long DEFAULT_ANIM_DURATION = 300L;

	private Bundle mNewBundle;

	private boolean mIsRoot, mIsSharedElement;

	private boolean mIsHidden = true;   // 用于记录Fragment show/hide 状态

	// SupportVisible
	private VisibleDelegate mVisibleDelegate;

	private Bundle mSaveInstanceState;

	private InputMethodManager mIMM;

	private boolean mNeedHideSoft;  // 隐藏软键盘

	protected SupportActivity _mActivity;

	protected FragmentationDelegate mFragmentationDelegate;

	private int mContainerId;   // 该Fragment所处的Container的id

	private FragmentAnimator mFragmentAnimator;

	private AnimatorHelper mAnimHelper;

	protected boolean mLocking; // 是否加锁 用于Fragmentation-SwipeBack库

	private OnFragmentDestoryViewListener mOnDestoryViewListener;

	private TransactionRecord mTransactionRecord;

	@IntDef({
			STANDARD,
			SINGLETOP,
			SINGLETASK
	})
	@Retention(RetentionPolicy.SOURCE)
	@interface LaunchMode {

	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		if (activity instanceof SupportActivity) {
			this._mActivity = (SupportActivity) activity;
			mFragmentationDelegate = _mActivity.getFragmentationDelegate();
		} else {
			throw new RuntimeException(activity.getClass()
											   .getSimpleName() + " must extends SupportActivity!");
		}

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONATTACH,
				null,
				false
								 );
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getVisibleDelegate().onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (bundle != null) {
			mIsRoot = bundle.getBoolean(
					FragmentationDelegate.FRAGMENTATION_ARG_IS_ROOT,
					false
									   );
			mIsSharedElement = bundle.getBoolean(
					FragmentationDelegate.FRAGMENTATION_ARG_IS_SHARED_ELEMENT,
					false
												);
			mContainerId = bundle.getInt(FragmentationDelegate.FRAGMENTATION_ARG_CONTAINER);
		}

		if (savedInstanceState == null) {
			mFragmentAnimator = onCreateFragmentAnimator();
			if (mFragmentAnimator == null) {
				mFragmentAnimator = _mActivity.getFragmentAnimator();
			}
		} else {
			mSaveInstanceState = savedInstanceState;
			mFragmentAnimator = savedInstanceState.getParcelable(FragmentationDelegate.FRAGMENTATION_STATE_SAVE_ANIMATOR);
			mIsHidden = savedInstanceState.getBoolean(FragmentationDelegate.FRAGMENTATION_STATE_SAVE_IS_HIDDEN);
			if (mContainerId == 0) { // After strong kill, mContianerId may not be correct restored.
				mIsRoot = savedInstanceState.getBoolean(
						FragmentationDelegate.FRAGMENTATION_ARG_IS_ROOT,
						false
													   );
				mIsSharedElement = savedInstanceState.getBoolean(
						FragmentationDelegate.FRAGMENTATION_ARG_IS_SHARED_ELEMENT,
						false
																);
				mContainerId = savedInstanceState.getInt(FragmentationDelegate.FRAGMENTATION_ARG_CONTAINER);
			}
		}

		if (restoreInstanceState()) {
			// 解决重叠问题
			processRestoreInstanceState(savedInstanceState);
		}

		initAnim();

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONCREATE,
				savedInstanceState,
				false
								 );
	}

	private void processRestoreInstanceState(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (isSupportHidden()) {
				ft.hide(this);
			} else {
				ft.show(this);
			}
			ft.commit();
		}
	}

	/**
	 * 内存重启后,是否让Fragmentation帮你恢复子Fragment状态
	 */
	protected boolean restoreInstanceState() {

		return true;
	}

	private void initAnim() {

		mAnimHelper = new AnimatorHelper(
				_mActivity.getApplicationContext(),
				mFragmentAnimator
		);
		// 监听入栈动画结束(1.为了防抖动; 2.为了Fragmentation的回调所用)
		mAnimHelper.enterAnim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

				_mActivity.setFragmentClickable(false);  // 开启防抖动
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				notifyEnterAnimEnd();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

		if (_mActivity.mPopMultipleNoAnim || mLocking) {
			if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE && enter) {
				return mAnimHelper.getNoneAnimFixed();
			}
			return mAnimHelper.getNoneAnim();
		}
		if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
			if (enter) {
				if (mIsRoot) {
					return mAnimHelper.getNoneAnim();
				}
				return mAnimHelper.enterAnim;
			} else {
				return mAnimHelper.popExitAnim;
			}
		} else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
			return enter ?
					mAnimHelper.popEnterAnim :
					mAnimHelper.exitAnim;
		} else {
			if (mIsSharedElement && enter) {
				notifyNoAnim();
			}
			return super.onCreateAnimation(
					transit,
					enter,
					nextAnim
										  );
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		getVisibleDelegate().onSaveInstanceState(outState);
		if (mIsRoot) {
			outState.putBoolean(
					FragmentationDelegate.FRAGMENTATION_ARG_IS_ROOT,
					true
							   );
		}
		if (mIsSharedElement) {
			outState.putBoolean(
					FragmentationDelegate.FRAGMENTATION_ARG_IS_SHARED_ELEMENT,
					true
							   );
		}
		outState.putInt(
				FragmentationDelegate.FRAGMENTATION_ARG_CONTAINER,
				mContainerId
					   );
		outState.putParcelable(
				FragmentationDelegate.FRAGMENTATION_STATE_SAVE_ANIMATOR,
				mFragmentAnimator
							  );
		outState.putBoolean(
				FragmentationDelegate.FRAGMENTATION_STATE_SAVE_IS_HIDDEN,
				isHidden()
						   );

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONSAVEINSTANCESTATE,
				outState,
				false
								 );
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		getVisibleDelegate().onActivityCreated(savedInstanceState);

		View view = getView();
		initFragmentBackground(view);
		// 防止某种情况 上一个Fragment仍可点击问题
		if (view != null) {
			view.setClickable(true);
		}

		if (savedInstanceState != null || mIsRoot || (getTag() != null && getTag().startsWith("android:switcher:"))) {
			notifyNoAnim();
		}

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONACTIVITYCREATED,
				savedInstanceState,
				false
								 );
	}

	private void notifyNoAnim() {

		notifyEnterAnimationEnd(mSaveInstanceState);
		_mActivity.setFragmentClickable(true);
	}

	protected void initFragmentBackground(View view) {

		setBackground(view);
	}

	protected void setBackground(View view) {

		if (view != null && view.getBackground() == null) {
			int defaultBg = _mActivity.getDefaultFragmentBackground();
			if (defaultBg == 0) {
				int background = getWindowBackground();
				view.setBackgroundResource(background);
			} else {
				view.setBackgroundResource(defaultBg);
			}
		}
	}

	protected int getWindowBackground() {

		TypedArray a = _mActivity.getTheme()
								 .obtainStyledAttributes(new int[]{
										 android.R.attr.windowBackground
								 });
		int background = a.getResourceId(
				0,
				0
										);
		a.recycle();
		return background;
	}

	@Override
	public void onResume() {

		super.onResume();
		getVisibleDelegate().onResume();
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONRESUME,
				null,
				false
								 );
	}

	@Override
	public void onPause() {

		super.onPause();
		getVisibleDelegate().onPause();
		if (mNeedHideSoft) {
			hideSoftInput();
		}

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONPAUSE,
				null,
				false
								 );
	}

	@Override
	public void onHiddenChanged(boolean hidden) {

		super.onHiddenChanged(hidden);
		getVisibleDelegate().onHiddenChanged(hidden);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {

		super.setUserVisibleHint(isVisibleToUser);
		getVisibleDelegate().setUserVisibleHint(isVisibleToUser);
	}

	/**
	 * Called when the fragment is vivible.
	 * <p>
	 * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
	 */
	public void onSupportVisible() {

		if (_mActivity != null) {
			_mActivity.setFragmentClickable(true);
		}
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONSUPPORTVISIBLE,
				null,
				true
								 );
	}

	/**
	 * Called when the fragment is invivible.
	 * <p>
	 * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
	 */
	public void onSupportInvisible() {

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONSUPPORTINVISIBLE,
				null,
				false
								 );
	}

	/**
	 * Return true if the fragment has been supportVisible.
	 */
	final public boolean isSupportVisible() {

		return getVisibleDelegate().isSupportVisible();
	}

	/**
	 * Lazy initial，Called when fragment is first called.
	 * <p>
	 * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
	 */
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {

		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONLAZYINITVIEW,
				null,
				false
								 );
	}

	/**
	 * 入栈动画 结束时,回调
	 */
	protected void onEnterAnimationEnd(Bundle savedInstanceState) {

	}

	boolean isSupportHidden() {

		return mIsHidden;
	}

	/**
	 * 获取该Fragment所在的容器id
	 */
	int getContainerId() {

		return mContainerId;
	}

	long getEnterAnimDuration() {

		if (mIsRoot) {
			return 0;
		}
		if (mAnimHelper == null) {
			return DEFAULT_ANIM_DURATION;
		}
		return mAnimHelper.enterAnim.getDuration();
	}

	long getExitAnimDuration() {

		if (mAnimHelper == null) {
			return DEFAULT_ANIM_DURATION;
		}
		return mAnimHelper.exitAnim.getDuration();
	}

	long getPopExitAnimDuration() {

		if (mAnimHelper == null) {
			return DEFAULT_ANIM_DURATION;
		}
		return mAnimHelper.popExitAnim.getDuration();
	}

	private void notifyEnterAnimationEnd(final Bundle savedInstanceState) {

		_mActivity.getHandler()
				  .post(new Runnable() {

					  @Override
					  public void run() {

						  onEnterAnimationEnd(savedInstanceState);
						  dispatchFragmentLifecycle(
								  LifecycleHelper.LIFECYLCE_ONENTERANIMATIONEND,
								  savedInstanceState,
								  false
												   );
					  }
				  });
	}

	/**
	 * 设定当前Fragmemt动画,优先级比在SupportActivity里高
	 */
	protected FragmentAnimator onCreateFragmentAnimator() {

		return _mActivity.getFragmentAnimator();
	}

	/**
	 * (因为事务异步的原因) 如果你想在onCreateView/onActivityCreated中使用 start/pop 方法,请使用该方法把你的任务入队
	 *
	 * @param runnable
	 * 		需要执行的任务
	 */
	public void enqueueAction(Runnable runnable) {

		_mActivity.getHandler()
				  .postDelayed(
						  runnable,
						  getEnterAnimDuration()
							  );
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftInput() {

		if (getView() != null) {
			initImm();
			mIMM.hideSoftInputFromWindow(
					getView().getWindowToken(),
					0
										);
		}
	}

	/**
	 * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
	 */
	public void showSoftInput(final View view) {

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

	private void initImm() {

		if (mIMM == null) {
			mIMM = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		}
	}

	/**
	 * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
	 *
	 * @return false则继续向上传递, true则消费掉该事件
	 */
	public boolean onBackPressedSupport() {

		return false;
	}

	/**
	 * Add some action when calling start()/startXX()
	 */
	public SupportTransaction transaction() {

		return new SupportTransaction.SupportTransactionImpl<>(this);
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
				getChildFragmentManager(),
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
				getChildFragmentManager(),
				containerId,
				toFragment,
				addToBack
														 );
	}

	/**
	 * 加载多个同级根Fragment
	 *
	 * @param containerId
	 * 		容器id
	 * @param toFragments
	 * 		目标Fragments
	 */
	@Override
	public void loadMultipleRootFragment(int containerId, int showPosition, SupportFragment... toFragments) {

		mFragmentationDelegate.loadMultipleRootTransaction(
				getChildFragmentManager(),
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
	@Deprecated
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
				getChildFragmentManager(),
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
	public void start(final SupportFragment toFragment, @LaunchMode final int launchMode) {

		enqueueAction(() -> {

			mFragmentationDelegate.dispatchStartTransaction(
					getFragmentManager(),
					this,
					toFragment,
					0,
					launchMode,
					FragmentationDelegate.TYPE_ADD
														   );
		});
	}

	@Override
	public void startForResult(String toFragment, int requestCode) {

		startForResult(
				findFragmentByReflect(toFragment),
				requestCode
					  );
	}

	@Override
	public void startForResult(SupportFragment toFragment, int requestCode) {

		enqueueAction(() -> {

			mFragmentationDelegate.dispatchStartTransaction(
					getFragmentManager(),
					this,
					toFragment,
					requestCode,
					STANDARD,
					FragmentationDelegate.TYPE_ADD_RESULT
														   );
		});
	}

	@Override
	public void startWithPop(SupportFragment toFragment) {

		enqueueAction(() -> {

			hideSoftInput();
			mFragmentationDelegate.dispatchStartTransaction(
					getFragmentManager(),
					this,
					toFragment,
					0,
					STANDARD,
					FragmentationDelegate.TYPE_ADD_WITH_POP
														   );
		});
	}

	@Override
	public void replaceFragment(SupportFragment toFragment, boolean addToBack) {

		enqueueAction(() -> {

			mFragmentationDelegate.replaceTransaction(
					this,
					toFragment,
					addToBack
													 );
		});
	}

	/**
	 * @return 位于栈顶的Fragment
	 */
	@Override
	public SupportFragment getTopFragment() {

		return mFragmentationDelegate.getTopFragment(getFragmentManager());
	}

	/**
	 * @return 位于栈顶的子Fragment
	 */
	@Override
	public SupportFragment getTopChildFragment() {

		return mFragmentationDelegate.getTopFragment(getChildFragmentManager());
	}

	/**
	 * @return 位于当前Fragment的前一个Fragment
	 */
	@Override
	public SupportFragment getPreFragment() {

		return mFragmentationDelegate.getPreFragment(this);
	}

	/**
	 * @return 栈内fragmentClass的fragment对象
	 */
	@Override
	public <T extends SupportFragment> T findFragment(Class<T> fragmentClass) {

		return mFragmentationDelegate.findStackFragment(
				fragmentClass,
				null,
				getFragmentManager()
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
				getFragmentManager()
													   );
	}

	/**
	 * @param fragmentFullName
	 *
	 * @return
	 */
	public SupportFragment findFragmentByReflect(String fragmentFullName) {

		return findFragmentByReflect(
				fragmentFullName,
				null
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
		} catch (java.lang.InstantiationException e) {
			e.printStackTrace();
			return fragment;
		}
		if (fragment == null) {

		}
		return fragment;
	}

	/**
	 * @return 栈内fragmentClass的子fragment对象
	 */
	@Override
	public <T extends SupportFragment> T findChildFragment(Class<T> fragmentClass) {

		return mFragmentationDelegate.findStackFragment(
				fragmentClass,
				null,
				getChildFragmentManager()
													   );
	}

	@Override
	public <T extends SupportFragment> T findChildFragment(String fragmentTag) {

		FragmentationDelegate.checkNotNull(
				fragmentTag,
				"tag == null"
										  );
		return mFragmentationDelegate.findStackFragment(
				null,
				fragmentTag,
				getChildFragmentManager()
													   );
	}

	/**
	 * 出栈
	 */
	@Override
	public void pop() {

		enqueueAction(() -> {

			hideSoftInput();
			mFragmentationDelegate.back(getFragmentManager());
		});
	}

	/**
	 * 子栈内 出栈
	 */
	@Override
	public void popChild() {

		enqueueAction(() -> {

			hideSoftInput();
			mFragmentationDelegate.back(getChildFragmentManager());
		});
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

		enqueueAction(() -> {

			hideSoftInput();
			mFragmentationDelegate.popTo(
					targetFragmentTag,
					includeTargetFragment,
					afterPopTransactionRunnable,
					getFragmentManager()
										);
		});
	}

	@Override
	public SupportActivity getSupportActivity() {

		return (SupportActivity) getActivity();
	}

	@Override
	public FragmentManager getSupportFragmentManager() {

		return getFragmentManager();
	}

	/**
	 * 子栈内
	 */
	@Override
	public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {

		popToChild(
				targetFragmentClass.getName(),
				includeTargetFragment
				  );
	}

	@Override
	public void popToChild(String targetFragmentTag, boolean includeTargetFragment) {

		popToChild(
				targetFragmentTag,
				includeTargetFragment,
				null
				  );
	}

	@Override
	public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

		popToChild(
				targetFragmentClass.getName(),
				includeTargetFragment,
				afterPopTransactionRunnable
				  );
	}

	@Override
	public void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {

		enqueueAction(() -> {

			mFragmentationDelegate.popTo(
					targetFragmentTag,
					includeTargetFragment,
					afterPopTransactionRunnable,
					getChildFragmentManager()
										);
		});
	}

	void popForSwipeBack() {

		mLocking = true;
		mFragmentationDelegate.back(getFragmentManager());
		mLocking = false;
	}

	/**
	 * 设置Result数据 (通过startForResult)
	 */
	public void setFragmentResult(int resultCode, Bundle bundle) {

		Bundle args = getArguments();
		if (args == null || !args.containsKey(FragmentationDelegate.FRAGMENTATION_ARG_RESULT_RECORD)) {
			return;
		}

		ResultRecord resultRecord = args.getParcelable(FragmentationDelegate.FRAGMENTATION_ARG_RESULT_RECORD);
		if (resultRecord != null) {
			resultRecord.resultCode = resultCode;
			resultRecord.resultBundle = bundle;
		}
	}

	/**
	 * 接受Result数据 (通过startForResult的返回数据)
	 */
	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {

	}

	/**
	 * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
	 *
	 * @param args
	 * 		通过上个Fragment的putNewBundle(Bundle newBundle)时传递的数据
	 */
	protected void onNewBundle(Bundle args) {

	}

	/**
	 * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
	 */
	public void putNewBundle(Bundle newBundle) {

		this.mNewBundle = newBundle;
	}

	Bundle getNewBundle() {

		return mNewBundle;
	}

	/**
	 * 入场动画结束时,回调
	 */
	void notifyEnterAnimEnd() {

		notifyEnterAnimationEnd(null);
		_mActivity.setFragmentClickable(true);
	}

	void setTransactionRecord(TransactionRecord record) {

		this.mTransactionRecord = record;
	}

	TransactionRecord getTransactionRecord() {

		return mTransactionRecord;
	}

	Bundle getSaveInstanceState() {

		return mSaveInstanceState;
	}

	public VisibleDelegate getVisibleDelegate() {

		if (mVisibleDelegate == null) {
			mVisibleDelegate = new VisibleDelegate(this);
		}
		return mVisibleDelegate;
	}

	/**
	 * @see OnFragmentDestoryViewListener
	 */
	void setOnFragmentDestoryViewListener(OnFragmentDestoryViewListener listener) {

		this.mOnDestoryViewListener = listener;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		super.onViewCreated(
				view,
				savedInstanceState
						   );
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONVIEWCREATED,
				savedInstanceState,
				false
								 );
	}

	@Override
	public void onStart() {

		super.onStart();
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONSTART,
				null,
				false
								 );
	}

	@Override
	public void onStop() {

		super.onStop();
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONSTOP,
				null,
				false
								 );
	}

	@Override
	public void onDestroyView() {

		_mActivity.setFragmentClickable(true);
		super.onDestroyView();
		getVisibleDelegate().onDestroyView();
		if (mOnDestoryViewListener != null) {
			mOnDestoryViewListener.onDestoryView();
			mOnDestoryViewListener = null;
		}
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONDESTROYVIEW,
				null,
				false
								 );
	}

	@Override
	public void onDestroy() {

		mFragmentationDelegate.handleResultRecord(this);
		super.onDestroy();
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONDESTROY,
				null,
				false
								 );
	}

	@Override
	public void onDetach() {

		super.onDetach();
		dispatchFragmentLifecycle(
				LifecycleHelper.LIFECYLCE_ONDETACH,
				null,
				false
								 );
	}

	private void dispatchFragmentLifecycle(int lifecycle, Bundle bundle, boolean visible) {

		if (_mActivity == null) {
			return;
		}
		_mActivity.dispatchFragmentLifecycle(
				lifecycle,
				SupportFragment.this,
				bundle,
				visible
											);
	}

	public boolean canSwipe() {

		return true;
	}


	/**
	 * @return 初始化并返回当前Presenter
	 */
	public BasePresenter getPresenter() {

		return null;
	}

	/**
	 * 检查权限
	 *
	 * @param permission
	 *
	 * @return true:无需请求权限
	 */
	@Override
	public boolean checkPermission(String permission) {

		int intPermission = ContextCompat.checkSelfPermission(
				_mActivity,
				permission
															 );

		return intPermission == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * 请求权限
	 *
	 * @param requestCode
	 */
	@Override
	public void requestPermission(int requestCode, String permission) {

		if (TextUtils.isEmpty(permission)) {
			return;
		}

		if (!MPermissions.shouldShowRequestPermissionRationale(
				_mActivity,
				permission,
				requestCode
															  )) {
			MPermissions.requestPermissions(
					this,
					requestCode,
					permission
										   );
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		MPermissions.onRequestPermissionsResult(
				this,
				requestCode,
				permissions,
				grantResults
											   );
		super.onRequestPermissionsResult(
				requestCode,
				permissions,
				grantResults
										);
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_CALENDAR)
	public void whyNeedGroupCalendar() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_CALENDAR,
				PermissionsUtils.GROUP_CALENDAR
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_CAMERA)
	public void whyNeedGroupCamera() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_CAMERA,
				PermissionsUtils.GROUP_CAMERA
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_CONTACTS)
	public void whyNeedGroupContacts() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_CONTACTS,
				PermissionsUtils.GROUP_CONTACTS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_MICROPHONE)
	public void whyNeedGroupMicrophone() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_MICROPHONE,
				PermissionsUtils.GROUP_MICROPHONE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_PHONE)
	public void whyNeedGroupPhone() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_PHONE,
				PermissionsUtils.GROUP_PHONE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_SENSORS)
	public void whyNeedGroupSensors() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_SENSORS,
				PermissionsUtils.GROUP_SENSORS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_SMS)
	public void whyNeedGroupSms() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_SMS,
				PermissionsUtils.GROUP_SMS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GROUP_STORAGE)
	public void whyNeedGroupStorage() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GROUP_STORAGE,
				PermissionsUtils.GROUP_STORAGE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_WRITE_CONTACTS)
	public void whyNeedWriteContacts() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_WRITE_CONTACTS,
				PermissionsUtils.WRITE_CONTACTS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_GET_ACCOUNTS)
	public void whyNeedGetAccounts() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_GET_ACCOUNTS,
				PermissionsUtils.GET_ACCOUNTS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_READ_CONTACTS)
	public void whyNeedReadContacts() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_READ_CONTACTS,
				PermissionsUtils.READ_CONTACTS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_READ_CALL_LOG)
	public void whyNeedReadCallLog() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_READ_CALL_LOG,
				PermissionsUtils.READ_CALL_LOG
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_READ_PHONE_STATE)
	public void whyNeedReadPhoneState() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_READ_PHONE_STATE,
				PermissionsUtils.READ_PHONE_STATE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_CALL_PHONE)
	public void whyNeedCallPhone() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_CALL_PHONE,
				PermissionsUtils.CALL_PHONE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_WRITE_CALL_LOG)
	public void whyNeedWriteCallLog() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_WRITE_CALL_LOG,
				PermissionsUtils.WRITE_CALL_LOG
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_USE_SIP)
	public void whyNeedUseSip() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_USE_SIP,
				PermissionsUtils.USE_SIP
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_PROCESS_OUTGOING_CALLS)
	public void whyNeedProcessOutgoingCalls() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_PROCESS_OUTGOING_CALLS,
				PermissionsUtils.PROCESS_OUTGOING_CALLS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_ADD_VOICEMAIL)
	public void whyNeedAddVoicemail() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_ADD_VOICEMAIL,
				PermissionsUtils.ADD_VOICEMAIL
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_READ_CALENDAR)
	public void whyNeedReadCalendar() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_READ_CALENDAR,
				PermissionsUtils.READ_CALENDAR
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_WRITE_CALENDAR)
	public void whyNeedWriteCalendar() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_WRITE_CALENDAR,
				PermissionsUtils.WRITE_CALENDAR
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_CAMERA)
	public void whyNeedCamera() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_CAMERA,
				PermissionsUtils.CAMERA
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_BODY_SENSORS)
	public void whyNeedBodySensors() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_BODY_SENSORS,
				PermissionsUtils.BODY_SENSORS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_ACCESS_FINE_LOCATION)
	public void whyNeedAccessFineLocation() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_ACCESS_FINE_LOCATION,
				PermissionsUtils.ACCESS_FINE_LOCATION
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION)
	public void whyNeedAccessCoarseLocation() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION,
				PermissionsUtils.ACCESS_COARSE_LOCATION
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_READ_EXTERNAL_STORAGE)
	public void whyNeedReadExternalStorage() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_READ_EXTERNAL_STORAGE,
				PermissionsUtils.READ_EXTERNAL_STORAGE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
	public void whyNeedWriteExternalStorage() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_WRITE_EXTERNAL_STORAGE,
				PermissionsUtils.WRITE_EXTERNAL_STORAGE
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_RECORD_AUDIO)
	public void whyNeedRecordAudio() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_RECORD_AUDIO,
				PermissionsUtils.RECORD_AUDIO
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_READ_SMS)
	public void whyNeedReadSms() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_READ_SMS,
				PermissionsUtils.READ_SMS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_RECEIVE_WAP_PUSH)
	public void whyNeedReceiveWapPush() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_RECEIVE_WAP_PUSH,
				PermissionsUtils.RECEIVE_WAP_PUSH
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_RECEIVE_MMS)
	public void whyNeedReceiveMms() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_RECEIVE_MMS,
				PermissionsUtils.RECEIVE_MMS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_RECEIVE_SMS)
	public void whyNeedReceiveSms() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_RECEIVE_SMS,
				PermissionsUtils.RECEIVE_SMS
									   );
	}

	@ShowRequestPermissionRationale(PermissionsUtils.REQUEST_CODE_SEND_SMS)
	public void whyNeedSendSms() {

		MPermissions.requestPermissions(
				this,
				PermissionsUtils.REQUEST_CODE_SEND_SMS,
				PermissionsUtils.SEND_SMS
									   );
	}


	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_CALENDAR)
	public void requestGroupCalendarSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_CAMERA)
	public void requestGroupCameraSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_CONTACTS)
	public void requestGroupContactsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_MICROPHONE)
	public void requestGroupMicrophoneSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_PHONE)
	public void requestGroupPhoneSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_SENSORS)
	public void requestGroupSensorsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_SMS)
	public void requestGroupSmsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GROUP_STORAGE)
	public void requestGroupStorageSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_WRITE_CONTACTS)
	public void requestWriteContactsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_GET_ACCOUNTS)
	public void requestGetAccountsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_READ_CONTACTS)
	public void requestReadContactsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_READ_CALL_LOG)
	public void requestReadCallLogSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_READ_PHONE_STATE)
	public void requestReadPhoneStateSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_CALL_PHONE)
	public void requestCallPhoneSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_WRITE_CALL_LOG)
	public void requestWriteCallLogSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_USE_SIP)
	public void requestUseSipSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_PROCESS_OUTGOING_CALLS)
	public void requestProcessOutgoingCallsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_ADD_VOICEMAIL)
	public void requestAddVoicemailSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_READ_CALENDAR)
	public void requestReadCalendarSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_WRITE_CALENDAR)
	public void requestWriteCalendarSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_CAMERA)
	public void requestCameraSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_BODY_SENSORS)
	public void requestBodySensorsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_ACCESS_FINE_LOCATION)
	public void requestAccessFineLocationSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION)
	public void requestAccessCoarseLocationSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_READ_EXTERNAL_STORAGE)
	public void requestReadExternalStorageSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
	public void requestWriteExternalStorageSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_RECORD_AUDIO)
	public void requestRecordAudioSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_READ_SMS)
	public void requestReadSmsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_RECEIVE_WAP_PUSH)
	public void requestReceiveWapPushSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_RECEIVE_MMS)
	public void requestReceiveMmsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_RECEIVE_SMS)
	public void requestReceiveSmsSuccess() {

	}

	@PermissionGrant(PermissionsUtils.REQUEST_CODE_SEND_SMS)
	public void requestSendSmsSuccess() {

	}


	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_CALENDAR)
	public void requestGroupCalendarFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_CAMERA)
	public void requestGroupCameraFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_CONTACTS)
	public void requestGroupContactsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_MICROPHONE)
	public void requestGroupMicrophoneFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_PHONE)
	public void requestGroupPhoneFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_SENSORS)
	public void requestGroupSensorsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_SMS)
	public void requestGroupSmsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GROUP_STORAGE)
	public void requestGroupStorageFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_WRITE_CONTACTS)
	public void requestWriteContactsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_GET_ACCOUNTS)
	public void requestGetAccountsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_READ_CONTACTS)
	public void requestReadContactsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_READ_CALL_LOG)
	public void requestReadCallLogFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_READ_PHONE_STATE)
	public void requestReadPhoneStateFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_CALL_PHONE)
	public void requestCallPhoneFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_WRITE_CALL_LOG)
	public void requestWriteCallLogFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_USE_SIP)
	public void requestUseSipFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_PROCESS_OUTGOING_CALLS)
	public void requestProcessOutgoingCallsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_ADD_VOICEMAIL)
	public void requestAddVoicemailFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_READ_CALENDAR)
	public void requestReadCalendarFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_WRITE_CALENDAR)
	public void requestWriteCalendarFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_CAMERA)
	public void requestCameraFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_BODY_SENSORS)
	public void requestBodySensorsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_ACCESS_FINE_LOCATION)
	public void requestAccessFineLocationFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION)
	public void requestAccessCoarseLocationFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_READ_EXTERNAL_STORAGE)
	public void requestReadExternalStorageFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
	public void requestWriteExternalStorageFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_RECORD_AUDIO)
	public void requestRecordAudioFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_READ_SMS)
	public void requestReadSmsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_RECEIVE_WAP_PUSH)
	public void requestReceiveWapPushFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_RECEIVE_MMS)
	public void requestReceiveMmsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_RECEIVE_SMS)
	public void requestReceiveSmsFailed() {

	}

	@PermissionDenied(PermissionsUtils.REQUEST_CODE_SEND_SMS)
	public void requestSendSmsFailed() {

	}
}
