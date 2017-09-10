package com.makeryan.lib.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.makeryan.lib.R;
import okhttp3.ResponseBody;

/**
 * Created by MakerYan on 16/4/7 13:34.
 * Email: light.yan@qq.com
 */
public class GlobUtils {

	private static Context mContext;

	public static final String y4M2d2 = "yyyy-MM-dd";

	public static final String y4M2d2_H2m2s2 = "yyyy-MM-dd HH:mm:ss";

	public static final String y4M2d2H2 = "yyyyMMddHHmm";

	private GlobUtils() {

	}

	public static void init(Context context) {

		mContext = context;
	}

	public static void setViewBgAsNull(ViewGroup vg) {

		if (vg == null) {
			return;
		}
		int count = vg.getChildCount();
		for (int i = 0; i < count; i++) {
			View v = vg.getChildAt(i);
			v.setBackgroundResource(0);
			if (v instanceof ImageView) {
				((ImageView) v).setImageResource(0);
			}
			if (v instanceof ViewGroup) {
				setViewBgAsNull((ViewGroup) v);
			}
		}
		vg.removeAllViews();
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity
	 * 		需要设置的activity
	 * @param color
	 * 		状态栏颜色值
	 */
	public static void setColor(Activity activity, int color) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 设置状态栏透明
			activity.getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 生成一个状态栏大小的矩形
			View statusView = createStatusView(
					activity,
					color
											  );
			// 添加 statusView 到布局中
			ViewGroup decorView = (ViewGroup) activity.getWindow()
													  .getDecorView();
			decorView.addView(statusView);
			// 设置根布局的参数
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
			rootView.setFitsSystemWindows(true);
			rootView.setClipToPadding(true);
		}
	}

	/**
	 * 生成一个和状态栏大小相同的矩形条
	 *
	 * @param activity
	 * 		需要设置的activity
	 * @param color
	 * 		状态栏颜色值
	 *
	 * @return 状态栏矩形条
	 */
	private static View createStatusView(Activity activity, int color) {
		// 获得状态栏高度
		int resourceId = activity.getResources()
								 .getIdentifier(
										 "status_bar_height",
										 "dimen",
										 "android"
											   );
		int statusBarHeight = activity.getResources()
									  .getDimensionPixelSize(resourceId);

		// 绘制一个和状态栏一样高的矩形
		View statusView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				statusBarHeight
		);
		statusView.setLayoutParams(params);
		statusView.setBackgroundColor(color);
		return statusView;
	}

	/**
	 * 使状态栏透明
	 * <p>
	 * 适用于图片作为背景的界面,此时需要图片填充到状态栏
	 *
	 * @param activity
	 * 		需要设置的activity
	 */
	public static void setTranslucent(Activity activity) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 设置状态栏透明
			activity.getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 设置根布局的参数
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
			rootView.setFitsSystemWindows(true);
			rootView.setClipToPadding(true);
		}
	}

	/**
	 * Get bitmap from specified image path
	 *
	 * @param imgPath
	 *
	 * @return
	 */
	public static Bitmap getBitmap(String imgPath) {
		// Get bitmap through image path
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = false;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;
		// Do not compress
		newOpts.inSampleSize = 1;
		newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
		return BitmapFactory.decodeFile(
				imgPath,
				newOpts
									   );
	}

	/**
	 * Store bitmap into specified image path
	 *
	 * @param bitmap
	 * @param outPath
	 *
	 * @throws FileNotFoundException
	 */
	public static void storeImage(Bitmap bitmap, String outPath)
			throws FileNotFoundException {

		FileOutputStream os = new FileOutputStream(outPath);
		bitmap.compress(
				Bitmap.CompressFormat.JPEG,
				100,
				os
					   );
	}

	/**
	 * Compress image by pixel, this will modify image width/height.
	 * Used to get thumbnail
	 *
	 * @param imgPath
	 * 		image path
	 * @param pixelW
	 * 		target pixel of width
	 * @param pixelH
	 * 		target pixel of height
	 *
	 * @return
	 */
	public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {

		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
		// Get bitmap info, but notice that bitmap is null now
		Bitmap bitmap = BitmapFactory.decodeFile(
				imgPath,
				newOpts
												);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 想要缩放的目标尺寸
		float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
		float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;//设置缩放比例
		// 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(
				imgPath,
				newOpts
										 );
		// 压缩好比例大小后再进行质量压缩
		//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
		return bitmap;
	}

	/**
	 * Compress image by size, this will modify image width/height.
	 * Used to get thumbnail
	 *
	 * @param image
	 * @param pixelW
	 * 		target pixel of width
	 * @param pixelH
	 * 		target pixel of height
	 *
	 * @return
	 */
	public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		image.compress(
				Bitmap.CompressFormat.JPEG,
				100,
				os
					  );
		if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			os.reset();//重置baos即清空baos
			image.compress(
					Bitmap.CompressFormat.JPEG,
					50,
					os
						  );//这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream  is      = new ByteArrayInputStream(os.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap bitmap = BitmapFactory.decodeStream(
				is,
				null,
				newOpts
												  );
		newOpts.inJustDecodeBounds = false;
		int   w  = newOpts.outWidth;
		int   h  = newOpts.outHeight;
		float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
		float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		is = new ByteArrayInputStream(os.toByteArray());
		bitmap = BitmapFactory.decodeStream(
				is,
				null,
				newOpts
										   );
		//压缩好比例大小后再进行质量压缩
		//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
		return bitmap;
	}

	/**
	 * Compress by quality,  and generate image to the path specified
	 *
	 * @param image
	 * @param outPath
	 * @param maxSize
	 * 		target will be compressed to be smaller than this size.(kb)
	 *
	 * @throws IOException
	 */
	public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// scale
		int options = 100;
		// Store the bitmap into output stream(no compress)
		image.compress(
				Bitmap.CompressFormat.JPEG,
				options,
				os
					  );
		// Compress by loop
		while (os.toByteArray().length / 1024 > maxSize) {
			// Clean up os
			os.reset();
			// interval 10
			options -= 10;
			image.compress(
					Bitmap.CompressFormat.JPEG,
					options,
					os
						  );
		}

		// Generate compressed image file
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outPath);
			fos.write(os.toByteArray());
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Compress by quality,  and generate image to the path specified
	 *
	 * @param imgPath
	 * @param outPath
	 * @param maxSize
	 * 		target will be compressed to be smaller than this size.(kb)
	 *
	 * @throws IOException
	 */
	public static void compressAndGenImage(String imgPath, String outPath, int maxSize) {

		compressAndGenImage(
				imgPath,
				outPath,
				maxSize,
				false
						   );

	}

	/**
	 * Compress by quality,  and generate image to the path specified
	 *
	 * @param imgPath
	 * @param outPath
	 * @param maxSize
	 * 		target will be compressed to be smaller than this size.(kb)
	 * @param needsDelete
	 * 		Whether delete original file after compress
	 *
	 * @throws IOException
	 */
	public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) {

		compressAndGenImage(
				getBitmap(imgPath),
				outPath,
				maxSize
						   );

		// Delete original file
		if (needsDelete) {
			File file = new File(imgPath);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * Ratio and generate thumb to the path specified
	 *
	 * @param imgPath
	 * 		image
	 * @param outPath
	 * @param pixelW
	 * 		target pixel of width
	 * @param pixelH
	 * 		target pixel of height
	 * @param needsDelete
	 * 		Whether delete original file after compress
	 *
	 * @throws FileNotFoundException
	 */
	public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) {

		Bitmap bitmap = ratio(
				imgPath,
				pixelW,
				pixelH
							 );
		try {
			storeImage(
					bitmap,
					outPath
					  );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Delete original file
		if (needsDelete) {
			File file = new File(imgPath);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static String getCompressedImgPath(String sourceImgPath) {

		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			Bitmap bmp = BitmapFactory.decodeFile(
					sourceImgPath,
					opts
												 );
			opts.inJustDecodeBounds = false;

			int   w         = opts.outWidth;
			int   h         = opts.outHeight;
			float standardW = 480f;
			float standardH = 800f;

			int zoomRatio = 1;
			if (w > h && w > standardW) {
				zoomRatio = (int) (w / standardW);
			} else if (w < h && h > standardH) {
				zoomRatio = (int) (h / standardH);
			}
			if (zoomRatio <= 0) {
				zoomRatio = 1;
			}
			opts.inSampleSize = zoomRatio;

			bmp = BitmapFactory.decodeFile(
					sourceImgPath,
					opts
										  );
			File path = new File(Environment.getExternalStorageDirectory()
											.getAbsolutePath() + "/CompressedImg/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File compressedImg = new File(path.getAbsolutePath() + System.currentTimeMillis() + ".jpg");
			if (!compressedImg.exists()) {
				compressedImg.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(compressedImg);
			bmp.compress(
					Bitmap.CompressFormat.JPEG,
					10,
					fos
						);
			fos.flush();
			fos.close();

			return compressedImg.getPath();

		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param sourceImgPath
	 * @param maxSize
	 * 		压缩到多大?(单位kb)
	 *
	 * @return
	 */
	public static String getCompressedImgPath(String sourceImgPath, int maxSize) {

		FileOutputStream fos = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			Bitmap bmp = BitmapFactory.decodeFile(
					sourceImgPath,
					opts
												 );
			opts.inJustDecodeBounds = false;

			int   w         = opts.outWidth;
			int   h         = opts.outHeight;
			float standardW = 1080f;
			float standardH = 1920f;

			int zoomRatio = 1;
			if (w > h && w > standardW) {
				zoomRatio = (int) (w / standardW);
			} else if (w < h && h > standardH) {
				zoomRatio = (int) (h / standardH);
			}
			if (zoomRatio <= 0) {
				zoomRatio = 1;
			}
			opts.inSampleSize = zoomRatio;

			bmp = BitmapFactory.decodeFile(
					sourceImgPath,
					opts
										  );

			ByteArrayOutputStream os            = new ByteArrayOutputStream();
			int                   theProportion = 100;
			do {
				KLog.d("os.toByteArray().length / 1024 : " + (os.toByteArray().length / 1024));
				// Clean up os
				os.reset();
				// interval 10
				bmp.compress(
						Bitmap.CompressFormat.JPEG,
						theProportion,
						os
							);
			}
			while (os.toByteArray().length / 1024 > maxSize && (theProportion -= 10) > 10);

			File path = new File(Environment.getExternalStorageDirectory()
											.getAbsolutePath() + "/CompressedImg/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File compressedImg = new File(path.getAbsolutePath() + System.currentTimeMillis() + ".jpg");
			if (!compressedImg.exists()) {
				compressedImg.createNewFile();
			}

			fos = new FileOutputStream(compressedImg);
			fos.write(os.toByteArray());
			fos.flush();

			return compressedImg.getPath();

		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取文件指定文件的指定单位的大小
	 *
	 * @param filePath
	 * 		文件路径
	 *
	 * @return double值的大小
	 */
	public static String getFileOrFilesSize(String filePath) {

		File file      = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(
					"获取文件大小",
					"获取失败!"
				 );
		}
		return formatFileSize(blockSize);
	}

	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 *
	 * @param filePath
	 * 		文件路径
	 *
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {

		File file      = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(
					"获取文件大小",
					"获取失败!"
				 );
		}
		return formatFileSize(blockSize);
	}

	/**
	 * 获取指定文件大小
	 *
	 * @param
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	private static long getFileSize(File file)
			throws Exception {

		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e(
					"获取文件大小",
					"文件不存在!"
				 );
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 *
	 * @param f
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	private static long getFileSizes(File f)
			throws Exception {

		long size    = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 *
	 * @return
	 */
	private static String FormetFileSize(long fileS) {

		DecimalFormat df             = new DecimalFormat("#.00");
		String        fileSizeString = "";
		String        wrongSize      = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/**
	 * 转换文件大小,指定转换的类型
	 *
	 * @param fileS
	 *
	 * @return
	 */
	private static String formatFileSize(long fileS) {

		DecimalFormat df             = new DecimalFormat("#.00");
		String        fileSizeString = "";
		String        wrongSize      = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	// 判断权限集合
	public static boolean lacksPermissions(String... permissions) {

		for (String permission : permissions) {
			if (lacksPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	// 判断权限集合
	public static boolean lacksPermissions(List<String> permissions) {

		for (String permission : permissions) {
			if (lacksPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	// 判断是否缺少权限
	public static boolean lacksPermission(String permission) {

		return ContextCompat.checkSelfPermission(
				mContext,
				permission
												) == PackageManager.PERMISSION_DENIED;
	}

	/**
	 * 获取设备ID(IMEI)
	 *
	 * @return
	 */
	public static String getDeviceId() {

		String deviceId;

		if (lacksPermission(Manifest.permission.READ_PHONE_STATE)) {
			deviceId = "NO_IMEI";
		} else {
			TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = tm.getDeviceId();
		}

		return deviceId;
	}


	public static String getTime(Date date, String dateFormat) {//可根据需要自行截取数据显示
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	/**
	 * bean对象转Map
	 *
	 * @param thisObj
	 *
	 * @return
	 */
	public static Map<String, String> convertBean2Map(Object thisObj) {

		Map   map = new HashMap();
		Class c;
		try {
			c = Class.forName(thisObj.getClass()
									 .getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(
									0,
									1
											   )
									 .toLowerCase() + key.substring(1);
							map.put(
									key,
									value
								   );
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("error:" + method);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> convertBean2MapByFields(Object thisObj) {

		Map   map = new HashMap();
		Class c;
		try {
			c = Class.forName(thisObj.getClass()
									 .getName());
			Method[] m      = c.getMethods();
			Field[]  fields = c.getFields();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(
									0,
									1
											   )
									 .toLowerCase() + key.substring(1);
							for (Field field : fields) {
								String fieldName = field.getName();
								if (fieldName.toLowerCase()
											 .equals(key.toLowerCase())) {
									key = fieldName;
									break;
								}
							}
							map.put(
									key,
									value
								   );
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("error:" + method);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}

	public static boolean isMethodsCompat(int VersionCode) {

		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}


	//获取CPU型号
	@SuppressWarnings("resource")
	public static String getCpuName() {

		try {
			FileReader     fr   = new FileReader("/proc/cpuinfo");
			BufferedReader br   = new BufferedReader(fr);
			String         text = br.readLine();
			String[] array = text.split(
					":\\s+",
					2
									   );
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//获取CPU核心数
	public static int getNumCores() {

		class CpuFilter
				implements FileFilter {

			@Override
			public boolean accept(File pathname) {

				if (Pattern.matches(
						"cpu[0-9]",
						pathname.getName()
								   )) {
					return true;
				}
				return false;
			}
		}

		try {
			File   dir   = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			return files.length;
		} catch (Exception e) {
			KLog.e(e);
			e.printStackTrace();
			return 1;
		}
	}


	//获取CPU最大频率
	public static String getMinCpuFreq() {

		String         result = "";
		ProcessBuilder cmd;
		try {
			String[] args = {
					"/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
			};
			cmd = new ProcessBuilder(args);
			Process     process = cmd.start();
			InputStream in      = process.getInputStream();
			byte[]      re      = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			KLog.e(ex);
			ex.printStackTrace();
			result = "N/A";
		}
		return result.trim();
	}


	//RAM内存大小
	public static long getRamMemory() {

		String   str1           = "/proc/meminfo";// 系统内存信息文件
		String   str2;
		String[] arrayOfString;
		long     initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader,
					8192
			);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				KLog.i(str2 + num + "\t");
			}
			initial_memory = Integer.valueOf(arrayOfString[1])
									.intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
			KLog.e(e);
		}
		KLog.d("UXLIBRES 总运存--->>>" + initial_memory / (1024 * 1024));
		return initial_memory / (1024 * 1024);
	}


	//获取屏幕分辨率
	public static String getScreenResolution() {

		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getResources()
					 .getDisplayMetrics();
		String strOpt = dm.widthPixels + " * " + dm.heightPixels;
		return strOpt;
	}

	//获取当前的可用内存
	public static String getAvailMemory() {

		try {
			ActivityManager            am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
			ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
			am.getMemoryInfo(mi);
			return mi.availMem + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}


	public static String getMac() {

		String macSerial = "";
		String str       = "";
		try {
			Process pp = Runtime.getRuntime()
								.exec("cat /sys/class/net/wlan0/address ");
			InputStreamReader ir    = new InputStreamReader(pp.getInputStream());
			LineNumberReader  input = new LineNumberReader(ir);
			for (; null != str; ) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return macSerial;
	}

	@SuppressWarnings("deprecation")
	public static String getUserAgent() {

		return (android.os.Build.MANUFACTURER + android.os.Build.MODEL + "/" + android.os.Build.VERSION.RELEASE + " " + android.os.Build.VERSION.SDK);
	}

	public static String GetNetWork() {

		if (getNetworkIsAvailable()) {
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);//??????????????
			NetworkInfo         activeNetInfo       = connectivityManager.getActiveNetworkInfo();
			if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return "wifi";
			} else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				return "3g";
			}
		}
		return "wifi";
	}


	public static Boolean getNetworkIsAvailable() {

		ConnectivityManager conManager  = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo         networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}


	/**
	 * 获取当前年份向前推100年数组
	 *
	 * @return
	 */
	public static String[] getYearArr() {

		GregorianCalendar calendar    = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int               currentYear = calendar.get(Calendar.YEAR);
		String[]          yearArr     = new String[100];
		for (int i = 0; i < yearArr.length; i++) {
			yearArr[i] = String.valueOf(currentYear - 99 + i);
		}
		return yearArr;
	}

	/**
	 * 获取当前月份
	 *
	 * @return
	 */
	public static int getCurrentMonth() {

		GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+08:00"))
						 .get(GregorianCalendar.YEAR);
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int               month    = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取当前月份的天数数组
	 *
	 * @return
	 */
	public static String[] getDayArr() {

		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int               first    = calendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH);
		int               last     = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		ArrayList<String> dayList  = new ArrayList<>();
		for (int i = first; i <= last; i++) {
			dayList.add(String.valueOf(i));
		}
		return (String[]) dayList.toArray();
	}

	/**
	 * 获取身高数组
	 *
	 * @return
	 */
	public static String[] getHeightDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.height_display);
		return strings;
	}

	/**
	 * 获取体重数组
	 *
	 * @return
	 */
	public static String[] getWeightDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.weight_display);
		return strings;
	}

	/**
	 * 获取学历数组
	 *
	 * @return
	 */
	public static String[] getEducationDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.education_display);
		return strings;
	}

	/**
	 * 获取是否有车数组
	 *
	 * @return
	 */
	public static String[] getCarDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.car_display);
		return strings;
	}

	/**
	 * 获取是否有房数组
	 *
	 * @return
	 */
	public static String[] getHouseDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.car_display);
		return strings;
	}

	/**
	 * 获取收入数组
	 *
	 * @return
	 */
	public static String[] getIncomeDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.income_display);
		return strings;
	}

	/**
	 * 获取性取向数组
	 *
	 * @return
	 */
	public static String[] getSexualOrientationDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.sexual_orientation_display);
		return strings;
	}

	/**
	 * 获取接受或拒绝数组
	 *
	 * @return
	 */
	public static String[] getAcceptDisplay() {

		String[] strings = mContext.getResources()
								   .getStringArray(R.array.accept_display);
		return strings;
	}

	public static List<String> getCustomBell(String path) {

		List<String> list   = new ArrayList<String>();
		File         folder = new File(path);
		File[]       files  = folder.listFiles();
		if (files == null) {
			return list;
		}
		for (int i = 0; i < files.length; i++) {
			list.add(files[i].getAbsolutePath());
		}
		return list;
	}
/*

	public static List<Bell> getCustomBell(String path) {

		List<Bell> list   = new ArrayList<Bell>();
		File       folder = new File(path);
		File[]     files  = folder.listFiles();
		if (files == null) {
			return list;
		}
		for (int i = 0; i < files.length; i++) {
			Bell bell = new Bell();
			bell.setBellName(files[i].getName());
			bell.setBellPath(files[i].getAbsolutePath());
			list.add(bell);
		}
		return list;
	}
*/

	public static String formatCurrentDate(String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String formatDate(Date date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date parse(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 将下载的文件写入手机空间
	 *
	 * @param body
	 * @param fileName
	 * 		文件名
	 *
	 * @return
	 */
	public static File writeToDisk(ResponseBody body, String fileName) {

		File filePath = new File(Environment.getExternalStorageDirectory()
											.getPath() + File.separator + "CNPC" + File.separator + "download");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File file = null;
		try {
			// todo change the file location/name according to your needs
			file = new File(
					filePath,
					fileName
			);
			if (file.exists()) {
				file.delete();
			}
			InputStream  inputStream  = null;
			OutputStream outputStream = null;

			try {
				byte[] fileReader = new byte[1024];

				long fileSize           = body.contentLength();
				long fileSizeDownloaded = 0;

				inputStream = body.byteStream();
				outputStream = new FileOutputStream(file);

				while (true) {
					int read = inputStream.read(fileReader);

					if (read == -1) {
						break;
					}

					outputStream.write(
							fileReader,
							0,
							read
									  );

					fileSizeDownloaded += read;

					KLog.d("file download: " + fileSizeDownloaded + " of " + fileSize);
				}

				outputStream.flush();
				return file;
			} catch (IOException e) {
				KLog.e(e);
				return file;
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			}
		} catch (IOException e) {
			KLog.e(e);
			return file;
		}
	}

	public static void destroy() {

		mContext = null;
	}
}
