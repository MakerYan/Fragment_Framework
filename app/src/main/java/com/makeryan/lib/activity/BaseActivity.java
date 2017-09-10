package com.makeryan.lib.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.fragment.fragmentation.SupportActivity;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.fragment.fragmentation.helper.FragmentLifecycleCallbacks;
import com.makeryan.lib.fragment.fragmentation_swipeback.SwipeBackActivity;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.utils.PermissionsUtils;
import com.socks.library.KLog;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.makeryan.lib.R;

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

		beforeOnCreate();
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
		doAction();
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
							   .getSimpleName() + " called onSaveInstanceState()");
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
							   .getSimpleName() + " called onEnterAnimationEnd()");
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
							   .getSimpleName() + " called onLazyInitView()");
			}

			/**
			 * Called when the Fragment is called onSupportVisible().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentSupportVisible(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onSupportVisible()");
			}

			/**
			 * Called when the Fragment is called onSupportInvisible().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentSupportInvisible(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onSupportInvisible()");
			}

			/**
			 * Called when the Fragment is called onAttach().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentAttached(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onAttach()");
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
							   .getSimpleName() + " called onCreate()");
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
							   .getSimpleName() + " called ViewCreated()");
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
							   .getSimpleName() + " called onActivityCreated()");
			}

			/**
			 * Called when the Fragment is called onStart().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentStarted(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onStart()");
			}

			/**
			 * Called when the Fragment is called onResume().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentResumed(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onResume()");
			}

			/**
			 * Called when the Fragment is called onPause().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentPaused(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onPause()");
			}

			/**
			 * Called when the Fragment is called onStop().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentStopped(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onStop()");
			}

			/**
			 * Called when the Fragment is called onDestroyView().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentDestroyView(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onDestroyView()");
			}

			/**
			 * Called when the Fragment is called onDestroy().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentDestroyed(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onDestroy()");
			}

			/**
			 * Called when the Fragment is called onDetach().
			 *
			 * @param fragment
			 */
			@Override
			public void onFragmentDetached(SupportFragment fragment) {

				KLog.d(fragment.getClass()
							   .getSimpleName() + " called onDetach()");
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
	public void finish() {

		if (getPresenter() != null) {
			getPresenter().finish();
		}
		super.finish();
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

	protected void beforeOnCreate() {

	}

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

		onBackPressed();
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
