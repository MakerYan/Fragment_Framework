package com.makeryan.lib.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentPermissionsBinding;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.util.GlobUtils;

/**
 * Created by MakerYan on 2017/5/6 09:47.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : kongrongqi_android
 * package name : com.makeryan.lib.fragment
 */

public class PermissionsFragment
		extends BaseFragment {

	public static final int REQUEST_CODE = 78078; // 请求码

	public static final int RESULT_CODE = 78178; // 返回码

	public static final int PERMISSIONS_GRANTED = 0; // 权限授权

	public static final int PERMISSIONS_DENIED = 1; // 权限拒绝

	public static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数

	public static final String EXTRA_PERMISSIONS = "makeryan.permission.extra_permission"; // 权限参数

	public static final String PACKAGE_URL_SCHEME = "package:"; // 方案

	private boolean isRequireCheck; // 是否需要系统权限检测

	protected FragmentPermissionsBinding mBinding;

	public static PermissionsFragment newInstance(String... permissions) {

		PermissionsFragment fragment = new PermissionsFragment();
		Bundle              bundle   = new Bundle();
		bundle.putStringArray(
				EXTRA_PERMISSIONS,
				permissions
							 );
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (mExtras == null || mExtras.getStringArray(EXTRA_PERMISSIONS) == null) {
			throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
		}
		isRequireCheck = true;
	}

	/**
	 * @return 初始化并返回当前Presenter
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

		return R.layout.fragment_permissions;
	}

	/**
	 * 初始化DataBinding
	 *
	 * @param inflater
	 * @param parent
	 */
	@Override
	protected ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup parent) {

		return mBinding == null ?
				mBinding = FragmentPermissionsBinding.inflate(
						inflater,
						parent,
						false
															 ) :
				mBinding;
	}

	/**
	 * 开始处理
	 */
	@Override
	protected void doAction() {


	}


	// 返回传递的权限参数
	private String[] getPermissions() {

		return mExtras.getStringArray(EXTRA_PERMISSIONS);
	}

	// 请求权限兼容低版本
	private void requestPermissions(String... permissions) {

		ActivityCompat.requestPermissions(
				getActivity(),
				permissions,
				PERMISSION_REQUEST_CODE
										 );
	}

	// 全部权限均已获取
	private void allPermissionsGranted() {

		Bundle bundle = new Bundle();
		bundle.putInt(
				EXTRA_PERMISSIONS,
				PERMISSIONS_GRANTED
					 );
		setFragmentResult(
				RESULT_CODE,
				bundle
						 );
		enqueueAction(() -> pop());
	}

	/**
	 * 用户权限处理,
	 * 如果全部获取, 则直接过.
	 * 如果权限缺失, 则提示Dialog.
	 *
	 * @param requestCode
	 * 		请求码
	 * @param permissions
	 * 		权限
	 * @param grantResults
	 * 		结果
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
			isRequireCheck = true;
			allPermissionsGranted();
		} else {
			isRequireCheck = false;
			showMissingPermissionDialog();
		}
	}

	// 含有全部的权限
	private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {

		for (int grantResult : grantResults) {
			if (grantResult == PackageManager.PERMISSION_DENIED) {
				return false;
			}
		}
		return true;
	}

	// 显示缺失权限提示
	private void showMissingPermissionDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.help);
		builder.setMessage(R.string.string_help_text);

		// 拒绝, 退出应用
		builder.setNegativeButton(
				R.string.quit,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Bundle bundle = new Bundle();
						bundle.putInt(
								EXTRA_PERMISSIONS,
								PERMISSIONS_DENIED
									 );
						setFragmentResult(
								RESULT_CODE,
								bundle
										 );
						pop();
					}
				}
								 );

		builder.setPositiveButton(
				R.string.settings,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						startAppSettings();
					}
				}
								 );

		builder.setCancelable(false);

		builder.show();
	}

	// 启动应用的设置
	private void startAppSettings() {

		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getActivity().getPackageName()));
		startActivity(intent);
	}

	@Override
	public void onResume() {

		super.onResume();
		if (isRequireCheck) {
			String[] permissions = getPermissions();
			if (GlobUtils.lacksPermissions(permissions)) {
				requestPermissions(permissions); // 请求权限
			} else {
				allPermissionsGranted(); // 全部权限都已获取
			}
		} else {
			isRequireCheck = true;
		}
	}
}
