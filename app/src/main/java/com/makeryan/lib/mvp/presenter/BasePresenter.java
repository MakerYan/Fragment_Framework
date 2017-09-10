package com.makeryan.lib.mvp.presenter;//package com.xiaoxixi8.shiguangji.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.makeryan.lib.R;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.fragment.PermissionsFragment;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.fragment.fragmentation.SupportActivity;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.listeners.OnResponseListener;
import com.makeryan.lib.net.API;
import com.makeryan.lib.net.FileRequestBody;
import com.makeryan.lib.net.HttpUtil;
import com.makeryan.lib.net.ProgressListener;
import com.makeryan.lib.net.Response;
import com.makeryan.lib.net.RetrofitCallback;
import com.makeryan.lib.net.request.SuperRequest;
import com.makeryan.lib.net.response.SuperResponse;
import com.makeryan.lib.util.ToastUtil;
import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MakerYan on 16/4/4 10:46.
 * Email: light.yan@qq.com
 */
public abstract class BasePresenter<V extends ISupport>
		implements IBasePresenter<V>,
				   View.OnClickListener,
				   XRecyclerView.LoadingListener {

	protected final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

	protected final List<Call> mCallList = new ArrayList<>();

	private WeakReference<V> viewRef;

	protected Gson mGson = new Gson();

	protected Bundle mExtras;

	protected boolean mCanRefresh = false;

	public BasePresenter(V v) {

		attachView(v);
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

		mCanRefresh = true;
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

	public FragmentManager getChildFragmentManager() {

		return getView().getChildFragmentManager();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 拒绝时, 关闭页面, 缺少主要权限, 无法运行
		if (requestCode == PermissionsFragment.REQUEST_CODE && data != null) {
			boolean b = data.getIntExtra(
					PermissionsFragment.EXTRA_PERMISSIONS,
					-1
										) == PermissionsFragment.PERMISSIONS_DENIED;
			if (b) {
				getSupportActivity().onBackPressedSupport();
			}
		}
	}

	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {

		// 拒绝时, 关闭页面, 缺少主要权限, 无法运行
		if (requestCode == PermissionsFragment.REQUEST_CODE && data != null && data.getInt(PermissionsFragment.EXTRA_PERMISSIONS) == PermissionsFragment.PERMISSIONS_DENIED) {
			getSupportActivity().onBackPressedSupport();
		}
	}

	protected boolean checkPermission(String permission) {

		return getView().checkPermission(permission);
	}

	/**
	 * 请求权限
	 *
	 * @param requestCode
	 * @param permission
	 */
	protected void requestPermission(int requestCode, String permission) {

		getView().requestPermission(
				requestCode,
				permission
								   );
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

	/**
	 * 显示错误信息弹出框
	 *
	 * @param hintStr
	 * 		提示信息
	 */
	protected void showHint(String hintStr) {

		showHint(
				hintStr,
				R.drawable.icon_path_warning_yellow
				);
	}


	/**
	 * 显示错误信息弹出框
	 *
	 * @param hintStr
	 * 		提示信息
	 */
	protected void showHint(String hintStr, int res) {

		Activity activity = getActivity();
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

	public void addSubscription(Subscription subscription) {

		mCompositeSubscription.add(subscription);
	}

	public void addAllSubscription(Subscription... subscriptions) {

		mCompositeSubscription.addAll(subscriptions);
	}

	@Override
	public void destroy() {

		if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed() && mCompositeSubscription.hasSubscriptions()) {
			mCompositeSubscription.unsubscribe();
		}
		if (!mCallList.isEmpty()) {
			for (Call call : mCallList) {
				call.cancel();
			}
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

	public Object getSystemService(@NonNull String name) {

		return getSupportActivity().getSystemService(name);
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

	public boolean onBackPressedSupport() {

		return false;
	}

	public void setFragmentResult() {

	}

	public void finish() {

	}

	public void showLoading() {

		showLoading(true);
	}

	public void showLoading(boolean showLoading) {

		if (!showLoading) {
			return;
		}
		enqueueAction(() -> StyledDialog.buildLoading()
										.show());
	}


	public void showLoading(@StringRes int stringRes) {

		showLoading(
				stringRes,
				true
				   );
	}


	public void showLoading(String text) {

		showLoading(
				text,
				true
				   );
	}

	public void showLoading(@StringRes int stringRes, boolean showLoading) {

		showLoading(
				getString(stringRes),
				showLoading
				   );
	}


	public void showLoading(String text, boolean showLoading) {

		if (!showLoading) {
			return;
		}
		enqueueAction(() -> StyledDialog.buildLoading(text)
										.show());
	}

	public void dismissLoading() {

		dismissLoading(true);
	}

	public void dismissLoading(boolean showLoading) {

		if (!showLoading) {
			return;
		}
		enqueueAction(() -> StyledDialog.dismissLoading());
	}

	public void pop() {

		V view = getView();
		if (view != null) {
			view.pop();
		}
	}

	public Subscription requestApi(String api, OnResponseListener listener) {

		return requestApi(
				api,
				true,
				listener
						 );
	}

	public Subscription requestApi(String api, boolean showLoading, OnResponseListener listener) {

		Subscription subscribe = HttpUtil.getApiService()
										 .postApi(api)
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<SuperResponse>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(API.BASE_URL + api);
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<SuperResponse> superResponseResponse) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 superResponseResponse,
														 API.BASE_URL + api
																	 )) {
													 if (listener != null) {
														 listener.onNext(superResponseResponse);
														 listener.onNext(superResponseResponse.getResult());
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}

	public Subscription requestApi(String api, SuperRequest request, OnResponseListener listener) {

		return requestApi(
				api,
				request,
				true,
				listener
						 );
	}

	public Subscription requestApi(String api, SuperRequest request, boolean showLoading, OnResponseListener listener) {

		Subscription subscribe = HttpUtil.getApiService()
										 .postApi(
												 api,
												 request
												 )
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<SuperResponse>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(API.BASE_URL + api);
												 KLog.json(mGson.toJson(request));
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<SuperResponse> superResponseResponse) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 superResponseResponse,
														 API.BASE_URL + api
																	 )) {
													 if (listener != null) {
														 listener.onNext(superResponseResponse);
														 listener.onNext(superResponseResponse.getResult());
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}

	public Subscription requestListApi(String api, OnResponseListener listener) {

		return requestListApi(
				api,
				true,
				listener
							 );
	}

	public Subscription requestListApi(String api, boolean showLoading, OnResponseListener listener) {

		Subscription subscribe = HttpUtil.getApiService()
										 .postListApi(api)
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<List<SuperResponse>>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(api);
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<List<SuperResponse>> superResponseResponse) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 superResponseResponse,
														 API.BASE_URL + api
																	 )) {
													 if (listener != null) {
														 List<SuperResponse> responses = superResponseResponse.getResult();
														 listener.onNextList(superResponseResponse);
														 if (responses == null || responses.isEmpty()) {
															 listener.onListEmpty();
														 } else {
															 listener.onNextList(responses);
														 }
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}

	public Subscription requestListApi(String api, SuperRequest request, OnResponseListener listener) {

		return requestListApi(
				api,
				request,
				true,
				listener
							 );
	}

	public Subscription requestListApi(String api, SuperRequest request, boolean showLoading, OnResponseListener listener) {

		Subscription subscribe = HttpUtil.getApiService()
										 .postListApi(
												 api,
												 request
													 )
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<List<SuperResponse>>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(API.BASE_URL + api);
												 KLog.json(mGson.toJson(request));
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<List<SuperResponse>> superResponseResponse) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 superResponseResponse,
														 API.BASE_URL + api
																	 )) {
													 if (listener != null) {
														 List<SuperResponse> responses = superResponseResponse.getResult();
														 listener.onNextList(superResponseResponse);
														 if (responses == null || responses.isEmpty()) {
															 listener.onListEmpty();
														 } else {
															 listener.onNextList(responses);
														 }
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}

	public Subscription requestApi(String api, SuperResponse request, OnResponseListener listener) {

		return requestApi(
				api,
				request,
				true,
				listener
						 );
	}

	public Subscription requestApi(String api, SuperResponse request, boolean showLoading, OnResponseListener listener) {

		Subscription subscribe = HttpUtil.getApiService()
										 .postApi(
												 api,
												 request
												 )
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<SuperResponse>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(API.BASE_URL + api);
												 KLog.json(mGson.toJson(request));
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<SuperResponse> superResponseResponse) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 superResponseResponse,
														 API.BASE_URL + api
																	 )) {

													 if (listener != null) {
														 listener.onNext(superResponseResponse);
														 SuperResponse response = superResponseResponse.getResult();
														 listener.onNext(response);
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}

	public Subscription requestListApi(String api, SuperResponse request, OnResponseListener listener) {

		return requestListApi(
				api,
				request,
				true,
				listener
							 );
	}

	public Subscription requestListApi(String api, SuperResponse request, boolean showLoading, OnResponseListener listener) {

		Subscription subscribe = HttpUtil.getApiService()
										 .postListApi(
												 api,
												 request
													 )
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<List<SuperResponse>>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(API.BASE_URL + api);
												 KLog.json(mGson.toJson(request));
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<List<SuperResponse>> superResponseResponse) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 superResponseResponse,
														 API.BASE_URL + api
																	 )) {
													 if (listener != null) {
														 List<SuperResponse> responses = superResponseResponse.getResult();
														 listener.onNextList(superResponseResponse);
														 if (responses == null || responses.isEmpty()) {
															 listener.onListEmpty();
														 } else {
															 listener.onNextList(responses);
														 }
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}

	public Subscription uploadFile(String api, String filePath, OnResponseListener listener) {

		return uploadFile(
				api,
				filePath,
				true,
				listener
						 );
	}

	/**
	 * 上传文件
	 *
	 * @param api
	 * @param path
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadFile(String api, String path, boolean showLoading, OnResponseListener listener) {

		File file = new File(path);
		return uploadFile(
				api,
				file,
				showLoading,
				listener
						 );
	}

	/**
	 * 上传文件
	 *
	 * @param api
	 * @param paths
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadFile(String api, List<String> paths, boolean showLoading, OnResponseListener listener) {

		ArrayList<File> files = new ArrayList<>();
		for (int i = 0; i < paths.size(); i++) {
			File file = new File(paths.get(i));
			if (file.exists()) {
				files.add(file);
			}
		}
		return uploadListFile(
				api,
				files,
				showLoading,
				listener
							 );
	}

	/**
	 * 上传文件
	 *
	 * @param api
	 * @param file
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadFile(String api, File file, boolean showLoading, OnResponseListener listener) {

		ArrayList<File> files = new ArrayList<>();
		files.add(file);
		return uploadListFile(
				api,
				files,
				showLoading,
				listener
							 );
	}

	/**
	 * 批量上传
	 *
	 * @param api
	 * @param files
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadListFile(String api, List<File> files, boolean showLoading, OnResponseListener listener) {

		String                key     = "file_";
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
		//多张图片
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);//filePath 图片地址
			RequestBody imageBody = RequestBody.create(
					MediaType.parse("multipart/form-data"),
					file
													  );
			String name = file.getName();
			builder.addFormDataPart(
					key + 1,
					name,
					imageBody
								   );//"imgfile"+i 后台接收图片流的参数名
		}

		List<MultipartBody.Part> parts = builder.build()
												.parts();
		Subscription subscribe = HttpUtil.getApiService()
										 .uploadListFile(
												 api,
												 parts
														)
										 .subscribeOn(Schedulers.io())
										 .observeOn(AndroidSchedulers.mainThread())
										 .subscribe(new Subscriber<Response<SuperResponse>>() {

											 @Override
											 public void onStart() {

												 super.onStart();
												 showLoading(showLoading);
												 if (listener != null) {
													 listener.onStart();
												 }
											 }

											 @Override
											 public void onCompleted() {

												 if (listener != null) {
													 listener.onCompleted();
												 }
												 dismissLoading(showLoading);
											 }

											 @Override
											 public void onError(Throwable e) {

												 KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
												 KLog.d(API.BASE_URL + api);
												 dismissLoading(showLoading);
												 if (listener != null) {
													 listener.onError(e);
												 }
											 }

											 @Override
											 public void onNext(Response<SuperResponse> response) {

												 dismissLoading(showLoading);
												 if (checkApiResponse(
														 response,
														 API.BASE_URL + api
																	 )) {
													 if (listener != null) {
														 KLog.d(API.BASE_URL + api);
														 KLog.json(mGson.toJson(response));
														 listener.onNext(response);
														 listener.onNext(response.getResult());
													 }
												 }
											 }
										 });
		addSubscription(subscribe);
		return subscribe;
	}


	public Subscription uploadFileWithProgress(String api, String filePath, OnResponseListener listener) {

		return uploadFileWithProgress(
				api,
				filePath,
				true,
				listener
									 );
	}

	/**
	 * 上传文件
	 *
	 * @param api
	 * @param path
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadFileWithProgress(String api, String path, boolean showLoading, OnResponseListener listener) {

		File file = new File(path);
		return uploadFileWithProgress(
				api,
				file,
				showLoading,
				listener
									 );
	}

	/**
	 * 上传文件
	 *
	 * @param api
	 * @param paths
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadFileWithProgress(String api, List<String> paths, boolean showLoading, OnResponseListener listener) {

		ArrayList<File> files = new ArrayList<>();
		for (int i = 0; i < paths.size(); i++) {
			File file = new File(paths.get(i));
			if (file.exists()) {
				files.add(file);
			}
		}
		return uploadListFileWithProgress(
				api,
				files,
				showLoading,
				listener
										 );
	}

	/**
	 * 上传文件
	 *
	 * @param api
	 * @param file
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadFileWithProgress(String api, File file, boolean showLoading, OnResponseListener listener) {

		ArrayList<File> files = new ArrayList<>();
		files.add(file);
		return uploadListFileWithProgress(
				api,
				files,
				showLoading,
				listener
										 );
	}

	/**
	 * 批量上传,带进度
	 *
	 * @param api
	 * @param files
	 * @param showLoading
	 * @param listener
	 *
	 * @return
	 */
	public Subscription uploadListFileWithProgress(String api, List<File> files, boolean showLoading, OnResponseListener listener) {

		Subscription subscription = onThreading(
				new Observable.OnSubscribe<Response<SuperResponse>>() {

					@Override
					public void call(Subscriber<? super Response<SuperResponse>> subscriber) {

						RetrofitCallback<Response<SuperResponse>> callback = new RetrofitCallback<Response<SuperResponse>>() {

							@Override
							public void onSuccess(Call<Response<SuperResponse>> call, retrofit2.Response<Response<SuperResponse>> response) {

								//进度更新结束
								if (listener != null) {
									listener.downloadComplete();
								}
								subscriber.onNext(response.body());
							}

							@Override
							public void onFailure(Call<Response<SuperResponse>> call, Throwable t) {

								//进度更新结束
								subscriber.onError(t);
							}

							@Override
							public void onLoading(long total, long progress) {

								super.onLoading(
										total,
										progress
											   );
								//此处进行进度更新
								KLog.d(progress + "/" + total);
								if (listener != null) {
									listener.downloadProgress(
											total,
											progress,
											total == progress,
											api
															 );
								}
							}
						};


						String                key     = "file_";
						MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
						//多张图片
						for (int i = 0; i < files.size(); i++) {
							File file = files.get(i);//filePath 图片地址
							RequestBody body = RequestBody.create(
									MediaType.parse("multipart/form-data"),
									file
																 );
							//通过该行代码将RequestBody转换成特定的FileRequestBody
							FileRequestBody fileBody = new FileRequestBody(
									body,
									callback
							);

							String name = file.getName();
							builder.addFormDataPart(
									key + 1,
									name,
									fileBody
												   );//"imgfile"+i 后台接收图片流的参数名
						}

						List<MultipartBody.Part> parts = builder.build()
																.parts();


						Call<Response<SuperResponse>> call = HttpUtil.getApiService()
																	 .uploadFileWithProgress(
																			 api,
																			 parts
																							);
						subscriber.onStart();
						call.enqueue(callback);
					}
				},
				new Subscriber<Response<SuperResponse>>() {

					@Override
					public void onStart() {

						super.onStart();
						showLoading(showLoading);
						if (listener != null) {
							listener.onStart();
						}
					}

					@Override
					public void onCompleted() {

						dismissLoading(showLoading);
						if (listener != null) {
							listener.downloadComplete();
						}
					}

					@Override
					public void onError(Throwable e) {

						showLongToast(e.getCause() + "\n" + e.getMessage());
						dismissLoading(showLoading);
						if (listener != null) {
							listener.onError(e);
						}
					}

					@Override
					public void onNext(Response<SuperResponse> response) {

						dismissLoading(showLoading);
						if (checkApiResponse(
								response,
								api
											)) {
							if (listener != null) {
								listener.onNext(response);
								listener.onNext(response.getResult());
							}
						}
					}

				}
											   );
		addSubscription(subscription);
		return subscription;
	}

	public void downloadGetFileWithProgress(String api, OnResponseListener listener) {

		downloadGetFileWithProgress(
				api,
				true,
				listener
								   );
	}


	public void downloadGetFileWithProgress(String api, boolean showLoading, OnResponseListener listener) {

		showLoading(showLoading);
		RetrofitCallback<ResponseBody> callback = new RetrofitCallback<ResponseBody>() {

			@Override
			public void onSuccess(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

				onThreading(
						new Observable.OnSubscribe<File>() {

							@Override
							public void call(Subscriber<? super File> subscriber) {

								/* // 这段代码放在这里
									ResponseBody body  = response.body();
									String[]     split = api.split("/");
									File file = GlobUtils.writeToDisk(
											body,
											split[split.length - 1]
																	 );
									subscriber.onNext(file);
								  */

								File                file = null;
								InputStream         is   = null;
								FileOutputStream    fos  = null;
								BufferedInputStream bis  = null;
								try {
									is = response.body()
												 .byteStream();
									//获取文件总长度,本地进度
									// long totalLength = is.available();

									File filePath = new File(Environment.getExternalStorageDirectory()
																		.getPath() + File.separator + "CNPC" + File.separator + "download");
									if (!filePath.exists()) {
										filePath.mkdirs();
									}
									String[] split = api.split("/");
									String   name  = split[split.length - 1];
									file = new File(
											filePath.getAbsolutePath(),
											name
									);
									fos = new FileOutputStream(file);
									bis = new BufferedInputStream(is);
									byte[] buffer = new byte[1024];
									int    len;
									while ((len = bis.read(buffer)) != -1) {
										//len即可理解为已下载的字节数
										fos.write(
												buffer,
												0,
												len
												 );
										//此处进行更新操作, 本地进度
									}
									fos.flush();
								} catch (IOException e) {
									subscriber.onError(e);
									e.printStackTrace();
								} finally {
									if (fos != null) {
										try {
											fos.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									if (bis != null) {
										try {
											bis.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									if (is != null) {
										try {
											is.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
								subscriber.onNext(file);
							}
						},
						new Action1<File>() {

							@Override
							public void call(File file) {

								dismissLoading(showLoading);
								if (listener != null) {
									if (file == null) {
										listener.downloadFileEmpty(api);
									} else {
										listener.downloadFile(
												file,
												api
															 );
									}
								}
							}
						}
						   );
			}
		};
		Call<ResponseBody> call = HttpUtil.getApiService(new ProgressListener() {

			@Override
			public void onProgress(long progress, long total, boolean done) {

				if (listener != null) {
					listener.downloadProgress(
							progress,
							total,
							done,
							api
											 );
				}
			}
		})
										  .downloadGetFileWithProgress(api);
		call.enqueue(callback);
		mCallList.add(call);
	}

	/**
	 * 线程中执行耗时操作
	 *
	 * @param subscriber
	 * @param <T>
	 *
	 * @return
	 */
	public <T> Subscription onThreading(Observable.OnSubscribe<T> subscriber) {

		Subscription subscribe = Observable.create(subscriber)
										   .subscribeOn(Schedulers.io())
										   .observeOn(AndroidSchedulers.mainThread())
										   .subscribe(new Action1<T>() {

											   @Override
											   public void call(T t) {

											   }
										   });
		addSubscription(subscribe);
		return subscribe;
	}

	/**
	 * 线程中执行耗时操作
	 *
	 * @param subscriber
	 * @param action
	 * @param <T>
	 *
	 * @return
	 */
	public <T> Subscription onThreading(Observable.OnSubscribe<T> subscriber, Action1<T> action) {

		Subscription subscribe = Observable.create(subscriber)
										   .subscribeOn(Schedulers.io())
										   .observeOn(AndroidSchedulers.mainThread())
										   .subscribe(action);
		addSubscription(subscribe);
		return subscribe;
	}


	/**
	 * 线程中执行耗时操作
	 *
	 * @param observable
	 * @param subscriber
	 * @param <T>
	 *
	 * @return
	 */
	public <T> Subscription onThreading(Observable.OnSubscribe<T> observable, Subscriber<T> subscriber) {

		Subscription subscribe = Observable.create(observable)
										   .subscribeOn(Schedulers.io())
										   .observeOn(AndroidSchedulers.mainThread())
										   .subscribe(subscriber);
		addSubscription(subscribe);
		return subscribe;
	}

	/**
	 * 主线程执行任务
	 *
	 * @param runnable
	 */
	public void enqueueAction(Runnable runnable) {

		V view = getView();
		if (view != null) {
			view.enqueueAction(runnable);
		}
	}

	/**
	 * 检查网络请求回来的错误码
	 *
	 * @param response
	 *
	 * @return
	 */
	public boolean checkApiResponse(Response response, String api) {

		int code = response.getCode();

		if (code != 1 && code != 99) {
			showLongToast(response.getMsg());
		}
		if (code == 2) {

		}
		return code == 1;
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
	 * 子类复写需要带上super
	 */
	@Override
	public void onRefresh(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter) {

		recyclerView.setLoadingMoreEnabled(true);
		recyclerView.refreshComplete();
	}

	/**
	 * XRecyclerView 加载更多回调监听
	 */
	@Override
	public void onLoadMore(XRecyclerView recyclerView, CommonRecyclerViewAdapter adapter) {

		recyclerView.loadMoreComplete();
	}

	public void start(SupportFragment fragment) {

		V view = getView();
		if (null == view) {
			return;
		}
		view.start(fragment);
	}

	public void start(SupportFragment fragment, int launchMode) {

		V view = getView();
		if (null == view) {
			return;
		}
		view.start(
				fragment,
				launchMode
				  );
	}

	public void startWithPop(SupportFragment toFragment) {

		V view = getView();
		if (null == view) {
			return;
		}
		view.startWithPop(toFragment);
	}

	public void startBySingleTask(SupportFragment fragment) {

		V view = getView();
		if (null == view) {
			return;
		}
		view.startBySingleTask(fragment);
	}

	public void startBySingleTop(SupportFragment fragment) {

		V view = getView();
		if (null == view) {
			return;
		}
		view.startBySingleTop(fragment);
	}

	public void startFragmentByEventBus(int target, SupportFragment fragment) {

		if (null == fragment) {
			return;
		}
		EventBus.getDefault()
				.post(new EventBean<>(
						target,
						fragment
				));
	}

	public void startFragmentByEventBus(int target, String targetFragmentFullName) {

		V view = getView();
		if (null == view) {
			return;
		}
		SupportFragment fragment = view.findChildFragment(targetFragmentFullName);
		if (null == fragment) {
			return;
		}
		EventBus.getDefault()
				.post(new EventBean<>(
						target,
						fragment
				));
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