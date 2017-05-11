package com.makeryan.lib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.socks.library.KLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by MakerYan on 16/4/7 13:34.
 * Email: light.yan@qq.com
 */
public class GlobUtils {

	private static Activity mActivity;

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	private GlobUtils() {

	}

	public static void init(Activity activity) {

		mActivity = activity;
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

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		final int height       = options.outHeight;
		final int width        = options.outWidth;
		int       inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth  = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * @param src
	 * @param dstWidth
	 * @param dstHeight
	 *
	 * @return 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
	 */
	private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight) {

		Bitmap dst = Bitmap.createScaledBitmap(
				src,
				dstWidth,
				dstHeight,
				false
											  );
		if (src != dst) { // 如果没有缩放，那么不回收
			src.recycle(); // 释放Bitmap的native像素数组
		}
		return dst;
	}

	/**
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 *
	 * @return 从Resources中加载图片
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(
				res,
				resId,
				options
									); // 读取图片长款
		options.inSampleSize = calculateInSampleSize(
				options,
				reqWidth,
				reqHeight
													); // 计算inSampleSize
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeResource(
				res,
				resId,
				options
												 ); // 载入一个稍大的缩略图
		return createScaleBitmap(
				src,
				reqWidth,
				reqHeight
								); // 进一步得到目标大小的缩略图
	}

	/**
	 * @param pathName
	 * @param reqWidth
	 * @param reqHeight
	 *
	 * @return 从sd卡上加载图片
	 */
	public static Bitmap decodeSampledBitmapFromFd(String pathName, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(
				pathName,
				options
								);
		options.inSampleSize = calculateInSampleSize(
				options,
				reqWidth,
				reqHeight
													);
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeFile(
				pathName,
				options
											 );
		return createScaleBitmap(
				src,
				reqWidth,
				reqHeight
								);
	}


	/**
	 * drawable convert to bitmap
	 *
	 * @param drawable
	 *
	 * @return
	 */
	public static Bitmap drawableConvertBitmap(Drawable drawable) {

		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if (bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(
					1,
					1,
					Bitmap.Config.ARGB_8888
										); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(
					drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(),
					Bitmap.Config.ARGB_8888
										);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(
				0,
				0,
				canvas.getWidth(),
				canvas.getHeight()
						  );
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * bitmap convert to drawable
	 *
	 * @param bitmap
	 *
	 * @return
	 */
	public static Drawable bitmapConvertDrawable(Bitmap bitmap) {

		return new BitmapDrawable(bitmap);
	}

	/**
	 * 将矩形转为圆形
	 *
	 * @param bitmap
	 *
	 * @return
	 */
	public static Bitmap makeRoundCorner(Bitmap bitmap) {

		int   width   = bitmap.getWidth();
		int   height  = bitmap.getHeight();
		int   left    = 0, top = 0, right = width, bottom = height;
		float roundPx = height / 2;
		if (width > height) {
			left = (width - height) / 2;
			top = 0;
			right = left + height;
			bottom = height;
		} else if (height > width) {
			left = 0;
			top = (height - width) / 2;
			right = width;
			bottom = top + width;
			roundPx = width / 2;
		}

		KLog.i("ps:" + left + ", " + top + ", " + right + ", " + bottom);

		Bitmap output = Bitmap.createBitmap(
				width,
				height,
				Bitmap.Config.ARGB_8888
										   );
		Canvas canvas = new Canvas(output);
		int    color  = 0xff424242;
		Paint  paint  = new Paint();
		Rect rect = new Rect(
				left,
				top,
				right,
				bottom
		);
		RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(
				0,
				0,
				0,
				0
					   );
		paint.setColor(color);
		canvas.drawRoundRect(
				rectF,
				roundPx,
				roundPx,
				paint
							);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(
				bitmap,
				rect,
				rect,
				paint
						 );
		return output;
	}

	/**
	 * 给矩形添加圆角
	 *
	 * @param bitmap
	 * @param px
	 *
	 * @return
	 */
	public static Bitmap makeRoundCorner(Bitmap bitmap, int px) {

		int width  = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(
				width,
				height,
				Bitmap.Config.ARGB_8888
										   );
		Canvas canvas = new Canvas(output);
		int    color  = 0xff424242;
		Paint  paint  = new Paint();
		Rect rect = new Rect(
				0,
				0,
				width,
				height
		);
		RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(
				0,
				0,
				0,
				0
					   );
		paint.setColor(color);
		canvas.drawRoundRect(
				rectF,
				px,
				px,
				paint
							);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(
				bitmap,
				rect,
				rect,
				paint
						 );
		return output;
	}

	/**
	 * Get bitmap from specified image path
	 *
	 * @param imgPath
	 *
	 * @return
	 */
	public Bitmap getBitmap(String imgPath) {
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
	public void compressAndGenImage(Bitmap image, String outPath, int maxSize) {

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
	 * @param needsDelete
	 * 		Whether delete original file after compress
	 *
	 * @throws IOException
	 */
	public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) {

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
	 * @param image
	 * @param outPath
	 * @param pixelW
	 * 		target pixel of width
	 * @param pixelH
	 * 		target pixel of height
	 *
	 * @throws FileNotFoundException
	 */
	public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) {

		Bitmap bitmap = ratio(
				image,
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
				mActivity,
				permission
												) == PackageManager.PERMISSION_DENIED;
	}

	/**
	 * 获取设备ID(IMEI)
	 *
	 * @param context
	 *
	 * @return
	 */
	public static String getDeviceId(Activity context) {

		if (context == null) {
			context = mActivity;
		}

		String deviceId;

		if (lacksPermission(Manifest.permission.READ_PHONE_STATE)) {
			deviceId = "NO_IMEI";
		} else {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = tm.getDeviceId();
		}

		return deviceId;
	}

	public static String getTime(Date date, String dateFormat) {//可根据需要自行截取数据显示
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	public static void destroy() {

		mActivity = null;
	}
}
