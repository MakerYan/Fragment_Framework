package com.makeryan.lib.mvp.presenter;//package com.xiaoxixi8.shiguangji.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.fragment.PermissionsFragment;
import com.makeryan.lib.net.Response;
import com.makeryan.lib.util.ToastUtil;
import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.fragment.fragmentation.SupportActivity;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MakerYan on 16/4/4 10:46.
 * Email: light.yan@qq.com
 */
public abstract class BasePresenter<V extends ISupport>
		implements IBasePresenter<V>,
				   View.OnClickListener,
				   XRecyclerView.LoadingListener {

	public String TAG;

	protected final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

	private WeakReference<V> viewRef;

	protected Gson mGson = new Gson();

	protected Bundle mExtras;

	public BasePresenter(V v) {

		attachView(v);
		TAG = getClass().getSimpleName();
		EventBus.getDefault()
				.register(this);
	}

	public void setExtras(Bundle extras) {

		mExtras = extras;
	}

	@Override
	public void attachView(V view) {

		viewRef = new WeakReference<V>(view);
	}

	/**
	 * Get the attached view. You should always call {@link #isViewAttached()} to check if the view
	 * is
	 * attached to avoid NullPointerExceptions.
	 *
	 * @return <code>null</code>, if view is not attached, otherwise the concrete view instance
	 */
	@Nullable
	public V getView() {

		return viewRef == null ?
				null :
				viewRef.get();
	}

	/**
	 * Checks if a view is attached to this presenter. You should always call this method before
	 * calling {@link #getView()} to get the view instance.
	 */
	public boolean isViewAttached() {

		return viewRef != null && viewRef.get() != null;
	}

	@Override
	public void detachView(boolean retainInstance) {

		EventBus.getDefault()
				.unregister(this);
		if (viewRef != null) {
			viewRef.clear();
			viewRef = null;
		}
	}

	public Activity getActivity() {

		return getView().getActivity();
	}

	public SupportActivity getSupportActivity() {

		return getView().getSupportActivity();
	}

	/**
	 * 当前Fragment 为可见时调用
	 * Called when the Fragment is called onSupportVisible().
	 *
	 * @param fragment
	 */
	public void onSupportVisible(SupportFragment fragment) {

	}

	/**
	 * 当前Fragment 为不可见时调用
	 * Called when the Fragment is called onSupportInvisible().
	 *
	 * @param fragment
	 */
	public void onSupportInvisible(SupportFragment fragment) {

	}

	/**
	 * @param fullName
	 * 		必需是Fragment的子类
	 * @param bundle
	 *
	 * @return
	 *
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Fragment newInstanceFragment(String fullName, Bundle bundle)
			throws ClassNotFoundException, IllegalAccessException, InstantiationException {

		Class<Fragment> aClass   = (Class<Fragment>) Class.forName(fullName);
		Fragment        fragment = aClass.newInstance();
		if (bundle != null) {
			fragment.setArguments(bundle);
		}
		return fragment;
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 拒绝时, 关闭页面, 缺少主要权限, 无法运行
		if (requestCode == PermissionsFragment.REQUEST_CODE && data != null) {
			boolean b = data.getIntExtra(
					PermissionsFragment.EXTRA_PERMISSIONS,
					-1
										) == PermissionsFragment.PERMISSIONS_DENIED;
			if (b) {
				getView().pop();
			}
		}
	}

	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {

		// 拒绝时, 关闭页面, 缺少主要权限, 无法运行
		if (requestCode == PermissionsFragment.REQUEST_CODE && data != null && data.getInt(PermissionsFragment.EXTRA_PERMISSIONS) == PermissionsFragment.PERMISSIONS_DENIED) {
			getView().pop();
		}
	}

	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

	}

	public void processonCreateViewSavedInstanceState(Bundle savedInstanceState) {

	}

	public void onViewStateRestored(Bundle savedInstanceState) {

	}

	public void onSaveInstanceState(Bundle savedInstanceState) {

	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {

	}

	public boolean pop() {

		return false;
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftInput() {

		getView().hideSoftInput();
	}

	/**
	 * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
	 */
	public void showSoftInput(View view) {

		getView().showSoftInput(view);
	}

	/**
	 * rx 异常, 回调方法
	 *
	 * @return
	 */
	@Override
	public Action1<Throwable> rxOnError() {

		return rxOnError(8001);
	}

	/**
	 * rx 异常, 回调方法
	 *
	 * @return
	 */
	@Override
	public Action1<Throwable> rxOnError(final int tag) {

		return new Action1<Throwable>() {

			@Override
			public void call(Throwable throwable) {

				KLog.e(throwable.getCause());
				KLog.e(throwable.getMessage());
			}
		};
	}

	/**
	 * rx 完成, 回调方法
	 *
	 * @return
	 */
	@Override
	public Action0 rxOnCompleted() {

		return rxOnCompleted(8000);
	}

	/**
	 * rx 完成, 回调方法
	 *
	 * @param tag
	 *
	 * @return
	 */
	@Override
	public Action0 rxOnCompleted(final int tag) {

		return new Action0() {

			@Override
			public void call() {

				KLog.e("OnCompleted Tag : " + tag);
			}
		};
	}

	@Override
	public void destroy() {

		if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed() && mCompositeSubscription.hasSubscriptions()) {
			mCompositeSubscription.unsubscribe();
		}
		EventBus.getDefault()
				.unregister(this);
	}

	public CharSequence getText(@StringRes int id) {

		return getView().getActivity()
						.getResources()
						.getText(id);
	}

	public String getString(@StringRes int id) {

		return getView().getActivity()
						.getResources()
						.getString(id);
	}

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

	protected void onCreateStatusUI(String status, View statusView) {
		// TODO: 16/12/3 子类必需带有super
		setViewClickListener(statusView);
	}

	protected void setViewClickListener(View statusView) {

		if (statusView instanceof ViewGroup) {
			ViewGroup viewGroup  = (ViewGroup) statusView;
			int       childCount = viewGroup.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View childAt = viewGroup.getChildAt(i);
				setViewClickListener(childAt);
			}
		} else {
			statusView.setOnClickListener(this);
		}
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

	public void startActivity(Intent intent) {

		getView().getSupportActivity()
				 .startActivity(intent);
	}

	public void startActivity(Class<Activity> clazz) {

		startActivity(new Intent(
				getActivity(),
				clazz
		));
	}

	public void setNavigationOnClickListener(View view) {

	}

	public boolean onBackPressed() {

		return false;
	}

	public void setFragmentResult() {

	}

	public void finish() {

	}

	/**
	 * 检查网络请求回来的错误码
	 *
	 * @param response
	 *
	 * @return
	 */
	public boolean checkApiResponse(Response response) {

		int code = response.getCode();

		if (code != 200) {
			showLongToast(response.getMsg());
		}

		return code == 200;
	}

	public void resetRecyclerViewStatus(XRecyclerView xRecyclerView, List list, boolean isRefresh) {

		if (isRefresh) {
			xRecyclerView.refreshComplete();
			xRecyclerView.setLoadingMoreEnabled(true);
		} else {
			xRecyclerView.loadMoreComplete();
			xRecyclerView.setLoadingMoreEnabled(list.size() > 0);
		}
	}

	/**
	 * 获取res/strings.xml 中的数组
	 *
	 * @param id
	 *
	 * @return
	 */
	public ArrayList<String> getStringArray(@ArrayRes int id) {

		String[] stringArray = getActivity().getResources()
											.getStringArray(id);
		return new ArrayList<String>(Arrays.asList(stringArray));
	}

	public void showShortToast(@StringRes int text) {

		ToastUtil.showMessage(
				getActivity(),
				text,
				Toast.LENGTH_SHORT
							 );
	}

	public void showLongToast(@StringRes int text) {

		ToastUtil.showMessage(
				getActivity(),
				text,
				Toast.LENGTH_LONG
							 );
	}

	public void showShortToast(String text) {

		ToastUtil.showMessage(
				getActivity(),
				text,
				Toast.LENGTH_SHORT
							 );
	}

	public void showLongToast(String text) {

		ToastUtil.showMessage(
				getActivity(),
				text,
				Toast.LENGTH_LONG
							 );
	}

	public void showLongToast(Serializable obj) {

		ToastUtil.showMessage(
				getActivity(),
				mGson.toJson(obj),
				Toast.LENGTH_LONG
							 );
	}

	/**
	 * XRecyclerView 下拉刷新回调监听
	 */
	@Override
	public void onRefresh(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter) {

	}

	/**
	 * XRecyclerView 加载更多回调监听
	 */
	@Override
	public void onLoadMore(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter) {

	}

	/**
	 * 打印Log
	 *
	 * @param object
	 */
	public void printJson(Object object) {

		KLog.d(object);
	}

	/**
	 * 打印Log
	 *
	 * @param jsonFormat
	 */
	public void printJson(String jsonFormat) {

		KLog.d(jsonFormat);
	}

	/**
	 * 打印Log
	 *
	 * @param tag
	 * @param jsonFormat
	 */
	public void printJson(String tag, String jsonFormat) {

		KLog.d(
				tag,
				jsonFormat
			  );
	}
}