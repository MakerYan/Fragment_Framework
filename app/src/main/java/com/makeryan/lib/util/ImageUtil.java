package com.makeryan.lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

	public static RequestManager getRequestManager() {

		return sRequestManager;
	}

	public enum DrawableDirection {
		LEFT,
		TOP,
		RIGHT,
		BOTTOM
	}

	public static DrawableRequestBuilder<String> getDrawableRequestBuilder(String url) {

		return getDrawableRequestBuilder(
				url,
				0.3f
										);
	}

	public static DrawableRequestBuilder<String> getDrawableRequestBuilder(String url, float thumbnail) {

		return sRequestManager.load(url)
							  .thumbnail(thumbnail)
							  .skipMemoryCache(false)
							  .diskCacheStrategy(DiskCacheStrategy.RESULT)
							  .crossFade()
							  .centerCrop();
	}

	public static BitmapRequestBuilder<String, Bitmap> getBitmapRequestBuilder(String url) {

		return getBitmapRequestBuilder(
				url,
				0.3f
									  );
	}

	public static BitmapRequestBuilder<String, Bitmap> getBitmapRequestBuilder(String url, float thumbnail) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(thumbnail)
							  .skipMemoryCache(false)
							  .diskCacheStrategy(DiskCacheStrategy.RESULT)
							  .centerCrop();
	}

	public static DrawableRequestBuilder<String> load(String url) {

		return load(
				url,
				0.3f
				   );
	}

	public static DrawableRequestBuilder<String> load(String url, float thumbnail) {

		return sRequestManager.load(url)
							  .skipMemoryCache(false)
							  .thumbnail(thumbnail)
							  .diskCacheStrategy(DiskCacheStrategy.RESULT)
							  .crossFade();
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView) {

		return loadAsBitmap(
				url,
				imageView,
				0.3f
						   );
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, float thumbnail) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(thumbnail)
							  .centerCrop()
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, int error) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(0.03f)
							  .error(error)
							  .centerCrop()
							  .into(imageView);

	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, Drawable error) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(0.03f)
							  .error(error)
							  .centerCrop()
							  .into(imageView);

	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, int placeHolder, int error) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(0.03f)
							  .error(error)
							  .placeholder(placeHolder)
							  .centerCrop()
							  .into(imageView);

	}


	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, float thumbnail, Drawable placeHolder, Drawable error) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(thumbnail)
							  .error(error)
							  .placeholder(placeHolder)
							  .centerCrop()
							  .into(imageView);

	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, Drawable placeHolder, Drawable error) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(0.03f)
							  .error(error)
							  .placeholder(placeHolder)
							  .centerCrop()
							  .into(imageView);

	}


	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, float thumbnail, int placeHolder, int error) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(thumbnail)
							  .error(error)
							  .placeholder(placeHolder)
							  .centerCrop()
							  .into(imageView);

	}


	public static com.bumptech.glide.request.target.Target loadAsBitmap(String url, ImageView imageView, int placeHolder, float thumbnail) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .thumbnail(thumbnail)
							  .error(placeHolder)
							  .placeholder(placeHolder)
							  .centerCrop()
							  .into(imageView);

	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(byte[] b, ImageView imageView) {

		return loadAsBitmap(
				b,
				imageView,
				0.3f
						   );
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(byte[] b, ImageView imageView, float thumbnail) {

		return sRequestManager.load(b)
							  .asBitmap()
							  .centerCrop()
							  .thumbnail(thumbnail)
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(File file, ImageView imageView) {

		return loadAsBitmap(
				file,
				imageView,
				0.3f
						   );
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(File file, ImageView imageView, float thumbnail) {

		return sRequestManager.load(file)
							  .asBitmap()
							  .centerCrop()
							  .thumbnail(thumbnail)
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(Integer integer, ImageView imageView) {

		return loadAsBitmap(
				integer,
				imageView,
				0.3f
						   );
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(Integer integer, ImageView imageView, float thumbnail) {

		return sRequestManager.load(integer)
							  .asBitmap()
							  .centerCrop()
							  .thumbnail(thumbnail)
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(Uri uri, ImageView imageView) {

		return loadAsBitmap(
				uri,
				imageView,
				0.3f
						   );
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(Uri uri, ImageView imageView, float thumbnail) {

		return sRequestManager.load(uri)
							  .asBitmap()
							  .centerCrop()
							  .thumbnail(thumbnail)
							  .into(imageView);
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(URL url, ImageView imageView) {

		return loadAsBitmap(
				url,
				imageView,
				0.3f
						   );
	}

	public static com.bumptech.glide.request.target.Target loadAsBitmap(URL url, ImageView imageView, float thumbnail) {

		return sRequestManager.load(url)
							  .asBitmap()
							  .centerCrop()
							  .thumbnail(thumbnail)
							  .into(imageView);
	}

	public static <T> com.bumptech.glide.request.target.Target loadAsBitmap(T model, ImageView imageView) {

		return loadAsBitmap(
				model,
				imageView,
				0.3f
						   );
	}

	public static <T> com.bumptech.glide.request.target.Target loadAsBitmap(T model, ImageView imageView, float thumbnail) {

		return sRequestManager.load(model)
							  .asBitmap()
							  .centerCrop()
							  .thumbnail(thumbnail)
							  .into(imageView);
	}

}

