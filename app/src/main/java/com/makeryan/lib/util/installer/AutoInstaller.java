package com.makeryan.lib.util.installer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AutoInstaller
		extends Handler {


	private static volatile AutoInstaller mAutoInstaller;

	private Context mContext;

	private String mTempPath = Environment.getExternalStorageDirectory()
										  .getAbsolutePath();

	public enum MODE {
		ROOT_ONLY,
		AUTO_ONLY,
		BOTH
	}

	private MODE mMode = MODE.BOTH;

	private AutoInstaller(Context context) {

		mContext = context;
	}

	public static AutoInstaller getDefault(Context context) {

		if (mAutoInstaller == null) {
			synchronized (AutoInstaller.class) {
				if (mAutoInstaller == null) {
					mAutoInstaller = new AutoInstaller(context);
				}
			}
		}
		return mAutoInstaller;
	}


	public interface OnStateChangedListener {

		void onStart();

		void onComplete();

		void onNeed2OpenService();
	}

	private OnStateChangedListener mOnStateChangedListener;

	public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {

		mOnStateChangedListener = onStateChangedListener;
	}

	private boolean installUseRoot(String filePath) {

		if (TextUtils.isEmpty(filePath)) {
			throw new IllegalArgumentException("Please check apk file path!");
		}
		boolean        result       = false;
		Process        process      = null;
		OutputStream   outputStream = null;
		BufferedReader errorStream  = null;
		try {
			process = Runtime.getRuntime()
							 .exec("su");
			outputStream = process.getOutputStream();

			String command = "pm install -r " + filePath + "\n";
			outputStream.write(command.getBytes());
			outputStream.flush();
			outputStream.write("exit\n".getBytes());
			outputStream.flush();
			process.waitFor();
			errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			StringBuilder msg = new StringBuilder();
			String        line;
			while ((line = errorStream.readLine()) != null) {
				msg.append(line);
			}
			KLog.d("install msg is " + msg);
			if (!msg.toString()
					.contains("Failure")) {
				result = true;
			}
		} catch (Exception e) {
			KLog.e(
					e.getMessage(),
					e
				  );
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (errorStream != null) {
					errorStream.close();
				}
			} catch (IOException e) {
				outputStream = null;
				errorStream = null;
				process.destroy();
			}
		}
		return result;
	}

	private void installUseAS(String filePath) {

		Uri    uri    = Uri.fromFile(new File(filePath));
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(
				uri,
				"application/vnd.android.package-archive"
							 );
		mContext.startActivity(intent);
		if (!isAccessibilitySettingsOn(mContext)) {
			toAccessibilityService();
			sendEmptyMessage(3);
		}
	}

	private void toAccessibilityService() {

		Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
		mContext.startActivity(intent);
	}


	private boolean isAccessibilitySettingsOn(Context mContext) {

		int          accessibilityEnabled = 0;
		final String service              = mContext.getPackageName() + "/" + InstallAccessibilityService.class.getCanonicalName();
		try {
			accessibilityEnabled = Settings.Secure.getInt(
					mContext.getApplicationContext()
							.getContentResolver(),
					android.provider.Settings.Secure.ACCESSIBILITY_ENABLED
														 );
			KLog.v("accessibilityEnabled = " + accessibilityEnabled);
		} catch (Settings.SettingNotFoundException e) {
			KLog.e("Error finding setting, default accessibility to not found: " + e.getMessage());
		}
		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

		if (accessibilityEnabled == 1) {
			KLog.v("***ACCESSIBILITY IS ENABLED*** -----------------");
			String settingValue = Settings.Secure.getString(
					mContext.getApplicationContext()
							.getContentResolver(),
					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
														   );
			if (settingValue != null) {
				mStringColonSplitter.setString(settingValue);
				while (mStringColonSplitter.hasNext()) {
					String accessibilityService = mStringColonSplitter.next();

					KLog.v("-------------- > accessibilityService :: " + accessibilityService + " " + service);
					if (accessibilityService.equalsIgnoreCase(service)) {
						KLog.v("We've found the correct setting - accessibility is switched on!");
						return true;
					}
				}
			}
		} else {
			KLog.v("***ACCESSIBILITY IS DISABLED***");
		}

		return false;
	}

	public void install(final String filePath) {

		if (TextUtils.isEmpty(filePath) || !filePath.endsWith(".apk")) {
			throw new IllegalArgumentException("not a correct apk file path");
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				sendEmptyMessage(1);

				switch (mMode) {
					case BOTH:
						if (!checkRooted() || !installUseRoot(filePath)) {
							installUseAS(filePath);
						}
						break;
					case ROOT_ONLY:
						installUseRoot(filePath);
						break;
					case AUTO_ONLY:
						installUseAS(filePath);
				}
				sendEmptyMessage(0);

			}
		}).start();
	}

	@Override
	public void handleMessage(Message msg) {

		super.handleMessage(msg);
		switch (msg.what) {
			case 0:
				if (mOnStateChangedListener != null) {
					mOnStateChangedListener.onComplete();
				}
				break;
			case 1:
				if (mOnStateChangedListener != null) {
					mOnStateChangedListener.onStart();
				}
				break;

			case 3:
				if (mOnStateChangedListener != null) {
					mOnStateChangedListener.onNeed2OpenService();
				}
				break;

		}
	}

	public void install(File file) {

		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		install(file.getAbsolutePath());
	}


	public void installFromUrl(final String httpUrl) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				sendEmptyMessage(1);
				File file = downLoadFile(httpUrl);
				install(file);
			}
		}).start();
	}

	private File downLoadFile(String httpUrl) {

		if (TextUtils.isEmpty(httpUrl)) {
			throw new IllegalArgumentException();
		}
		File file = new File(mTempPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(mTempPath + File.separator + "update.apk");
		InputStream       inputStream  = null;
		FileOutputStream  outputStream = null;
		HttpURLConnection connection   = null;
		try {
			URL url = new URL(httpUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10 * 1000);
			connection.setReadTimeout(10 * 1000);
			connection.connect();
			inputStream = connection.getInputStream();
			outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int    len    = 0;
			while ((len = inputStream.read(buffer)) > 0) {
				outputStream.write(
						buffer,
						0,
						len
								  );
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				inputStream = null;
				outputStream = null;
			}
		}
		return file;
	}

	public static class Builder {

		private MODE mode = MODE.BOTH;

		private Context context;

		private OnStateChangedListener onStateChangedListener;

		private String directory = Environment.getExternalStorageDirectory()
											  .getAbsolutePath();

		public Builder(Context c) {

			context = c;
		}

		public Builder setMode(MODE m) {

			mode = m;
			return this;
		}

		public Builder setOnStateChangedListener(OnStateChangedListener o) {

			onStateChangedListener = o;
			return this;
		}

		public Builder setCacheDirectory(String path) {

			directory = path;
			return this;
		}

		public AutoInstaller build() {

			AutoInstaller autoInstaller = new AutoInstaller(context);
			autoInstaller.mMode = mode;
			autoInstaller.mOnStateChangedListener = onStateChangedListener;
			autoInstaller.mTempPath = directory;
			return autoInstaller;
		}

	}

	public static boolean checkRooted() {

		boolean result = false;
		try {
			result = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 安装
	 *
	 * @param context
	 * 		接收外部传进来的context
	 */
	public static void install(Context context, String url) {

		install(
				context,
				new File(url)
			   );
	}

	/**
	 * 安装
	 *
	 * @param context
	 * 		接收外部传进来的context
	 */
	public static void install(Context context, File apkFile) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri    uri    = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			uri = FileProvider.getUriForFile(
					context,
					context.getApplicationContext()
						   .getPackageName() + ".provider",
					apkFile
											);
			intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
			intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		} else {
			uri = Uri.fromFile(apkFile);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(
				uri,
				"application/vnd.android.package-archive"
							 );
		context.startActivity(intent);
	}

	/**
	 * 安装
	 *
	 * @param context
	 * 		接收外部传进来的context
	 */
	public static void installApk(Context context, File apkFile) {

		String fileName = apkFile.getAbsolutePath();
		if (fileName.endsWith(".apk")) {
			if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
				File file = new File(fileName);
				Uri apkUri = FileProvider.getUriForFile(
						context,
						"cn.com.cnpc.hubei.provider",
						// BuildConfig.APPLICATION_ID + ".provider"
						file
													   );//在AndroidManifest中的android:authorities值
				Intent install = new Intent(Intent.ACTION_VIEW);
				install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
				install.setDataAndType(
						apkUri,
						"application/vnd.android.package-archive"
									  );
				context.startActivity(install);
			} else {
				Intent install = new Intent(Intent.ACTION_VIEW);
				install.setDataAndType(
						Uri.fromFile(new File(fileName)),
						"application/vnd.android.package-archive"
									  );
				install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(install);
			}
		}
	}
}
