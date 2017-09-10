package com.makeryan.lib.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.makeryan.lib.R;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.utils.PermissionsUtils;
import com.makeryan.modules.container.ui.fragment.ContainerFragment;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;
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

		setTheme(R.style.TransparentStatusBar);
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
			if (null == fragment) {
				fragment = ContainerFragment.newInstance();
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
			/*
			UserInfo userInfo = FlowUtils.getUserInfo();
			if (fragment == null) {
				if (TextUtils.isEmpty(userInfo.getToken())) {
					fragment = SplashFragment.newInstance();
				} else {
					fragment = MainFragment.newInstance();
				}
				loadRootFragment(
						R.id.container,
						fragment
								);
			} else {
				if (TextUtils.isEmpty(userInfo.getToken())) {
					fragment = LoginFragment.newInstance();
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
			}*/
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		setIntent(intent);// 必须要调用这句
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
		/*
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
		*/
	}

	@Override
	public boolean canSwipe() {

		return false;
	}

	@Override
	public void onBackPressedSupport() {

		super.onBackPressedSupport();
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
