package com.makeryan.lib.fragment.fragmentation;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/16 下午9:49
 */
public interface ISupport {

	/**
	 * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
	 *
	 * @param containerId
	 * 		容器id
	 * @param toFragment
	 * 		目标Fragment
	 */
	void loadRootFragment(int containerId, SupportFragment toFragment);

	/**
	 * 以replace方式加载根Fragment
	 */
	void replaceLoadRootFragment(int containerId, SupportFragment toFragment, boolean addToBack);

	/**
	 * 加载多个根Fragment
	 *
	 * @param containerId
	 * 		容器id
	 * @param toFragments
	 * 		目标Fragments
	 */
	void loadMultipleRootFragment(int containerId, int showPosition, SupportFragment... toFragments);

	/**
	 * show一个Fragment,hide上一个Fragment
	 * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
	 *
	 * @param showFragment
	 * 		需要show的Fragment
	 */
	void showHideFragment(SupportFragment showFragment);

	/**
	 * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
	 *
	 * @param showFragment
	 * 		需要show的Fragment
	 * @param hideFragment
	 * 		需要hide的Fragment
	 */
	void showHideFragment(SupportFragment showFragment, SupportFragment hideFragment);

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
	void start(SupportFragment toFragment);

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
	void startBySingleTop(SupportFragment toFragment);

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
	void startBySingleTask(SupportFragment toFragment);

	/**
	 * 启动目标Fragment
	 *
	 * @param toFragment
	 * 		目标Fragment
	 * @param launchMode
	 * 		启动模式
	 */
	void start(SupportFragment toFragment, @SupportFragment.LaunchMode int launchMode);

	/**
	 * 类似startActivityForResult
	 *
	 * @param toFragment
	 * 		目标Fragment
	 * @param requestCode
	 * 		requsetCode
	 */
	void startForResult(SupportFragment toFragment, int requestCode);

	/**
	 * 类似startActivityForResult
	 *
	 * @param toFragment
	 * 		目标Fragment
	 * @param requestCode
	 * 		requsetCode
	 */
	void startForResult(String toFragment, int requestCode);

	/**
	 * 启动目标Fragment,并pop当前Fragment
	 *
	 * @param toFragment
	 * 		目标Fragment
	 */
	void startWithPop(SupportFragment toFragment);

	/**
	 * @return 栈顶Fragment
	 */
	SupportFragment getTopFragment();

	/**
	 * @param fragmentClass
	 * 		目标Fragment的Class
	 * @param <T>
	 * 		继承自SupportFragment的Fragment
	 *
	 * @return 目标Fragment
	 */
	<T extends SupportFragment> T findFragment(Class<T> fragmentClass);

	/**
	 * @param fragmentTag
	 * @param <T>
	 *
	 * @return
	 */
	<T extends SupportFragment> T findFragment(String fragmentTag);

	/**
	 * @param fragmentFullName
	 *
	 * @return
	 */
	SupportFragment findFragmentByReflect(String fragmentFullName);

	/**
	 * @param fragmentFullName
	 * @param bundle
	 *
	 * @return
	 */
	SupportFragment findFragmentByReflect(String fragmentFullName, Bundle bundle);

	/**
	 * 出栈
	 */
	void pop();

	/**
	 * 出栈到目标Fragment
	 *
	 * @param targetFragmentClass
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 */
	void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment);

	/**
	 * 出栈到目标Fragment
	 *
	 * @param targetFragmentTag
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 */
	void popTo(String targetFragmentTag, boolean includeTargetFragment);

	/**
	 * 出栈到目标Fragment,并在出栈后立即进行Fragment事务(可以防止出栈后,直接进行Fragment事务的异常)
	 *
	 * @param targetFragmentClass
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 * @param afterPopTransactionRunnable
	 * 		出栈后紧接着的Fragment事务
	 */
	void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable);

	/**
	 * 出栈到目标Fragment,并在出栈后立即进行Fragment事务(可以防止出栈后,直接进行Fragment事务的异常)
	 *
	 * @param targetFragmentTag
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 * @param afterPopTransactionRunnable
	 * 		出栈后紧接着的Fragment事务
	 */
	void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable);


	/**
	 * @return activity
	 */
	Activity getActivity();

	/**
	 * @return SupportActivity
	 */
	SupportActivity getSupportActivity();

	/**
	 * 隐藏软键盘
	 */
	void hideSoftInput();

	/**
	 * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
	 */
	void showSoftInput(View view);

	void enqueueAction(Runnable runnable);

	/**
	 * 检查权限
	 *
	 * @param permission
	 *
	 * @return
	 */
	boolean checkPermission(String permission);

	/**
	 * 请求权限
	 *
	 * @param requestCode
	 * @param permission
	 */
	void requestPermission(int requestCode, String permission);

	FragmentManager getChildFragmentManager();

    /*--------------------Fragment专用方法start---------------------------*/

	/**
	 * belong fragment
	 * replace目标Fragment, 主要用于Fragment之间的replace
	 *
	 * @param toFragment
	 * 		目标Fragment
	 * @param addToBack
	 * 		是否添加到回退栈
	 */
	void replaceFragment(SupportFragment toFragment, boolean addToBack);

	/**
	 * belong fragment
	 *
	 * @return 位于栈顶的子Fragment
	 */
	SupportFragment getTopChildFragment();

	/**
	 * belong fragment
	 *
	 * @return 当前Fragment的前一个Fragment
	 */
	SupportFragment getPreFragment();

	/**
	 * belong fragment
	 *
	 * @param fragmentClass
	 * 		目标子Fragment的Class
	 * @param <T>
	 * 		继承自SupportFragment的Fragment
	 *
	 * @return 目标子Fragment
	 */
	<T extends SupportFragment> T findChildFragment(Class<T> fragmentClass);

	<T extends SupportFragment> T findChildFragment(String fragmentTag);

	/**
	 * belong fragment
	 * 子栈内 出栈
	 */
	void popChild();

	/**
	 * belong fragment
	 * 子栈内 出栈到目标Fragment
	 *
	 * @param targetFragmentClass
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 */
	void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment);

	/**
	 * belong fragment
	 * 子栈内 出栈到目标Fragment
	 *
	 * @param targetFragmentTag
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 */
	void popToChild(String targetFragmentTag, boolean includeTargetFragment);

	/**
	 * belong fragment
	 * 子栈内 出栈到目标Fragment,并在出栈后立即进行Fragment事务(可以防止出栈后,直接进行Fragment事务的异常)
	 *
	 * @param targetFragmentClass
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 * @param afterPopTransactionRunnable
	 * 		出栈后紧接着的Fragment事务
	 */
	void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable);

	/**
	 * belong fragment
	 *
	 * @param targetFragmentTag
	 * 		目标Fragment的Class
	 * @param includeTargetFragment
	 * 		是否包含目标Fragment
	 * @param afterPopTransactionRunnable
	 * 		出栈后紧接着的Fragment事务
	 */
	void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable);

	/**
	 * belong fragment
	 *
	 * @return android.support.v4.app.FragmentManager
	 */
	android.support.v4.app.FragmentManager getSupportFragmentManager();

	/**
	 * belong fragment
	 * <p>
	 * 接受Result数据 (通过startForResult的返回数据)
	 */
	void setFragmentResult(int resultCode, Bundle bundle);

    /*--------------------Fragment专用方法end---------------------------*/
}
