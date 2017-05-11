package com.makeryan.lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.URL;

/**
 * Created by MakerYan on 16/9/12 19:04.
 * Personal e-mail : light.yan@qq.com
 * project name : ChengGua
 * package name : com.chenggua.cg.app.lib.util
 */
public class ImageUtil {

	protected static ImageUtil INSTANCE;

	protected static Context mContext;

	protected static RequestManager sRequestManager;

	private ImageUtil(Context context) {

		mContext = context;
		sRequestManager = Glide.with(context);
	}

	public static ImageUtil init(Context context) {

		if (INSTANCE == null) {
			synchronized (ImageUtil.class) {
				if (INSTANCE == null) {
					INSTANCE = new ImageUtil(context);
				}
			}
		}
		return INSTANCE;
	}

	public static void destroy() {

		mContext = null;
		sRequestManager = null;
	}

	public enum DrawableDirection {
		LEFT,
		TOP,
		RIGHT,
		BOTTOM
	}

	public static DrawableRequestBuilder<String> getDrawableRequestBuilder(String url) {

		return sRequestManager.load(url)
							  .skipMemoryCache(false)
							  .diskCacheStrategy(DiskCacheStrategy.RESULT)
							  .crossFade()
							  .centerCrop();
	}

	public static BitmapRequestBuilder<String, Bitmap> getBitmapRequestBuilder(String url) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .skipMemoryCache(false)
							  .diskCacheStrategy(DiskCacheStrategy.RESULT)
							  .centerCrop();
	}

	public static DrawableRequestBuilder<String> load(String url) {

		return sRequestManager.load(url)
							  .skipMemoryCache(false)
							  .diskCacheStrategy(DiskCacheStrategy.RESULT)
							  .crossFade();
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, int defaulImgId) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .error(defaulImgId)
							  .placeholder(defaulImgId)
							  .centerCrop()
							  .into(imageView);

	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(byte[] b, ImageView imageView) {

		return sRequestManager.load(b)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(File file, ImageView imageView) {

		return sRequestManager.load(file)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(Integer integer, ImageView imageView) {

		return sRequestManager.load(integer)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(Uri uri, ImageView imageView) {

		return sRequestManager.load(uri)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(URL url, ImageView imageView) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

	public static <T> com.bumptech.glide.request.target.Target loadAsBitmap(T model, ImageView imageView) {

		return sRequestManager.load(model)
							  .asBitmap()
							  .centerCrop()
							  .into(imageView);
	}

}

