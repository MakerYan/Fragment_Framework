package com.makeryan.lib.util;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeryan.lib.net.Base64;
import com.makeryan.lib.widget.CircleImageView;
import com.makeryan.lib.widget.CustomShapeImageView;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.IOException;

/**
 * Created by MakerYan on 16/7/15 11:18.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : dormapp-android
 * package name : com.huanxiao.dorm.module.business_loans.utils
 */
public class BindingUtils {

	@BindingAdapter({"src"})
	public static void src(ImageView view, String url) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(ImageView view, Bitmap url) {

		if (url != null) {
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(ImageView view, byte[] url) {

		if (url != null) {
			ImageUtil.getRequestManager()
					 .load(url)
					 .centerCrop()
					 .into(view);
		}
	}

	@BindingAdapter({
			"src",
			"error"
	})
	public static void src(ImageView view, String url, int error) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					error
								  );
		}
	}

	@BindingAdapter({
			"src",
			"placeHolder",
			"error"
	})
	public static void src(ImageView view, String url, int placeHolder, int error) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					placeHolder,
					error
								  );
		}
	}

	@BindingAdapter({
			"src",
			"error"
	})
	public static void src(ImageView view, String url, Drawable error) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					error
								  );
		}
	}

	@BindingAdapter({
			"src",
			"placeHolder",
			"error"
	})
	public static void src(ImageView view, String url, Drawable placeHolder, Drawable error) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					placeHolder,
					error
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(ImageView view, int url) {

		if (url != 0) {
			view.setImageDrawable(view.getResources()
									  .getDrawable(url));
		}
	}

	@BindingAdapter({"src"})
	public static void src(ImageView view, Drawable url) {

		if (url != null) {
			view.setImageDrawable(url);
		}
	}

	@BindingAdapter({
			"src",
			"thumbnail"
	})
	public static void src(ImageView view, String url, float thumbnail) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					thumbnail
								  );
		}
	}

	@BindingAdapter({
			"src",
			"scaleType"
	})
	public static void src(ImageView view, String url, ImageView.ScaleType scaleType) {

		if (!TextUtils.isEmpty(url)) {
			view.setScaleType(scaleType);
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(RelativeLayout view, int url) {

		if (url != 0) {
			view.setBackground(view.getResources()
								   .getDrawable(url));
		}
	}

	@BindingAdapter({"src"})
	public static void src(AutoRelativeLayout view, int url) {

		if (url != 0) {
			view.setBackground(view.getResources()
								   .getDrawable(url));
		}
	}

	@BindingAdapter({
			"src",
			"scaleType"
	})
	public static void src(ImageView view, int url, ImageView.ScaleType scaleType) {

		if (url != 0) {
			view.setScaleType(scaleType);
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(CustomShapeImageView view, String url) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({
			"src",
			"thumbnail"
	})
	public static void src(CustomShapeImageView view, String url, float thumbnail) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					thumbnail
								  );
		}
	}

	@BindingAdapter({
			"src",
			"scaleType"
	})
	public static void src(CustomShapeImageView view, String url, ImageView.ScaleType scaleType) {

		if (!TextUtils.isEmpty(url)) {
			view.setScaleType(scaleType);
			ImageUtil.loadAsBitmap(
					url,
					view,
					0.1f
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(CustomShapeImageView view, int url) {

		if (url != 0) {
			ImageUtil.loadAsBitmap(
					url,
					view,
					0.1f
								  );
		}
	}

	@BindingAdapter({
			"src",
			"scaleType"
	})
	public static void src(CustomShapeImageView view, int url, ImageView.ScaleType scaleType) {

		if (url != 0) {
			view.setScaleType(scaleType);
			ImageUtil.loadAsBitmap(
					url,
					view,
					0.1f
								  );
		}
	}

	@BindingAdapter("src")
	public static void src(CustomShapeImageView view, Drawable url) {

		ImageUtil.loadAsBitmap(
				url,
				view,
				0.1f
							  );
	}

	@BindingAdapter({
			"src",
			"border_color"
	})
	public static void src(CircleImageView view, String url, int border_color) {

		if (border_color != 0) {
			view.setBorderColor(border_color);
		}
		if (!TextUtils.isEmpty(url)) {
			view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(RelativeLayout relativeLayout, String url) {

		if (!TextUtils.isEmpty(url)) {
			Glide.with(relativeLayout.getContext())
				 .load(url)
				 .centerCrop()
				 .crossFade()
				 .into(new SimpleTarget<GlideDrawable>() {

					 @Override
					 public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

						 relativeLayout.setBackgroundDrawable(resource);
					 }
				 });
		}
	}

	@BindingAdapter({"src"})
	public static void src(AutoRelativeLayout relativeLayout, String url) {

		if (!TextUtils.isEmpty(url)) {
			Glide.with(relativeLayout.getContext())
				 .load(url)
				 .centerCrop()
				 .crossFade()
				 .into(new SimpleTarget<GlideDrawable>() {

					 @Override
					 public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

						 relativeLayout.setBackgroundDrawable(resource);
					 }
				 });
		}
	}

	@BindingAdapter({"drawableLeft"})
	public static void drawableLeft(TextView view, int res) {

		if (res != 0) {
			view.setCompoundDrawablesWithIntrinsicBounds(
					res,
					0,
					0,
					0
														);
		}
	}

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableLeft(TextView view, int res, int drawableWidth, int drawableHeight) {

		if (res != 0) {
			Resources resources = view.getResources();
			Drawable  drawable  = resources.getDrawable(res);
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					drawable,
					null,
					null,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableLeft(TextView view, Drawable drawable, int drawableWidth, int drawableHeight) {

		if (drawable != null) {
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					drawable,
					null,
					null,
					null
									 );
		}
	}


	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableLeft(final TextView view, String url, final int drawableWidth, final int drawableHeight) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 drawable.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
											   );
							 view.setCompoundDrawables(
									 drawable,
									 null,
									 null,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({"drawableLeft"})
	public static void drawableLeft(EditText view, int res) {

		if (res != 0) {
			view.setCompoundDrawablesWithIntrinsicBounds(
					res,
					0,
					0,
					0
														);
		}
	}

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableLeft(EditText view, int res, int drawableWidth, int drawableHeight) {

		if (res != 0) {
			Resources resources = view.getResources();
			Drawable  drawable  = resources.getDrawable(res);
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					drawable,
					null,
					null,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableLeft(EditText view, Drawable drawable, int drawableWidth, int drawableHeight) {

		if (drawable != null) {
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					drawable,
					null,
					null,
					null
									 );
		}
	}


	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableLeft(final EditText view, String url, final int drawableWidth, final int drawableHeight) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 drawable.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
											   );
							 view.setCompoundDrawables(
									 drawable,
									 null,
									 null,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({"drawableTop"})
	public static void drawableTop(TextView view, int res) {

		if (res != 0) {
			view.setCompoundDrawablesWithIntrinsicBounds(
					0,
					res,
					0,
					0
														);
		}
	}

	@BindingAdapter({
			"drawableTop",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableTop(TextView view, int res, int drawableWidth, int drawableHeight) {

		if (res != 0) {
			Resources resources = view.getResources();
			Drawable  drawable  = resources.getDrawable(res);
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					null,
					drawable,
					null,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableTop",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableTop(TextView view, Drawable drawable, int drawableWidth, int drawableHeight) {

		if (drawable != null) {
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					null,
					drawable,
					null,
					null
									 );
		}
	}


	@BindingAdapter({
			"drawableTop",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableTop(final TextView view, String url, final int drawableWidth, final int drawableHeight) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 drawable.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
											   );
							 view.setCompoundDrawables(
									 null,
									 drawable,
									 null,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({"drawableRight"})
	public static void drawableRight(TextView view, int res) {

		if (res != 0) {
			view.setCompoundDrawablesWithIntrinsicBounds(
					0,
					0,
					res,
					0
														);
		}
	}

	@BindingAdapter({
			"drawableRight",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableRight(TextView view, int res, int drawableWidth, int drawableHeight) {

		if (res != 0) {
			Resources resources = view.getResources();
			Drawable  drawable  = resources.getDrawable(res);
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					null,
					null,
					drawable,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableRight",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableRight(TextView view, Drawable drawable, int drawableWidth, int drawableHeight) {

		if (drawable != null) {
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					null,
					null,
					drawable,
					null
									 );
		}
	}


	@BindingAdapter({
			"drawableRight",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableRight(final TextView view, String url, final int drawableWidth, final int drawableHeight) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 drawable.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
											   );
							 view.setCompoundDrawables(
									 null,
									 null,
									 drawable,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({"drawableBottom"})
	public static void drawableBottom(TextView view, int res) {

		if (res != 0) {
			view.setCompoundDrawablesWithIntrinsicBounds(
					0,
					0,
					0,
					res
														);
		}
	}

	@BindingAdapter({
			"drawableBottom",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableBottom(TextView view, int res, int drawableWidth, int drawableHeight) {

		if (res != 0) {
			Resources resources = view.getResources();
			Drawable  drawable  = resources.getDrawable(res);
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					null,
					null,
					null,
					drawable
									 );
		}
	}

	@BindingAdapter({
			"drawableBottom",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableBottom(TextView view, Drawable drawable, int drawableWidth, int drawableHeight) {

		if (drawable != null) {
			drawable.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
							  );
			view.setCompoundDrawables(
					null,
					null,
					null,
					drawable
									 );
		}
	}


	@BindingAdapter({
			"drawableBottom",
			"drawableWidth",
			"drawableHeight"
	})
	public static void drawableBottom(final TextView view, String url, final int drawableWidth, final int drawableHeight) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 drawable.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
											   );
							 view.setCompoundDrawables(
									 null,
									 null,
									 null,
									 drawable
													  );
						 }
					 });
		}
	}

	@BindingAdapter({"drawablePadding"})
	public static void drawablePadding(TextView view, float res) {

		if (res != 0) {
			view.setCompoundDrawablePadding((int) res);
		}
	}

	@BindingAdapter({"text"})
	public static void setText(TextView view, int res) {

		if (res != 0) {
			view.setText(view.getContext()
							 .getResources()
							 .getText(res));
		}
	}

	@BindingAdapter({"text"})
	public static void setText(TextView view, String res) {

		if (!TextUtils.isEmpty(res)) {
			view.setText(res);
		}
	}

	@BindingAdapter({"title"})
	public static void setTitle(Toolbar view, String title) {

		if (TextUtils.isEmpty(title)) {
			view.setTitle("");
		} else {
			view.setTitle(title);
		}
	}


	@BindingAdapter({"title"})
	public static void setTitle(Toolbar view, int res) {

		if (res != 0) {
			view.setTitle(res);
		}
	}

	@BindingAdapter({"background"})
	public static void background(RelativeLayout relativeLayout, String url) {

		if (!TextUtils.isEmpty(url)) {
			Glide.with(relativeLayout.getContext())
				 .load(url)
				 .centerCrop()
				 .thumbnail(0.2f)
				 .crossFade()
				 .into(new SimpleTarget<GlideDrawable>() {

					 @Override
					 public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

						 relativeLayout.setBackgroundDrawable(resource);
					 }
				 });
		}
	}

	@BindingAdapter({"background"})
	public static void background(LinearLayout linearLayout, String url) {

		if (!TextUtils.isEmpty(url)) {
			Glide.with(linearLayout.getContext())
				 .load(url)
				 .thumbnail(0.2f)
				 .centerCrop()
				 .crossFade()
				 .into(new SimpleTarget<GlideDrawable>() {

					 @Override
					 public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

						 linearLayout.setBackgroundDrawable(resource);
					 }
				 });
		}
	}

	@BindingAdapter({"setSpanCount"})
	public static void setSpanCount(RecyclerView view, int spanCount) {

		RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
		if (layoutManager == null) {
			return;
		}
		if (layoutManager instanceof GridLayoutManager) {
			((GridLayoutManager) layoutManager).setSpanCount(spanCount);
			view.setLayoutManager(layoutManager);
		}
	}

	@BindingAdapter({
			"layout_width",
			"layout_height"
	})
	public static void setLayoutParams(View view, int width, int height) {

		ViewParent parent = view.getParent();
		if (parent == null) {
			return;
		}
		if (parent instanceof FrameLayout) {
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = width == 0 ?
					FrameLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(width);
			layoutParams.height = height == 0 ?
					FrameLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentHeightSize(height);
			view.setLayoutParams(layoutParams);
			return;
		}
		if (parent instanceof RelativeLayout) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = width == 0 ?
					RelativeLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(width);
			layoutParams.height = height == 0 ?
					RelativeLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentHeightSize(height);
			view.setLayoutParams(layoutParams);
			return;
		}
		if (parent instanceof LinearLayout) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = width == 0 ?
					LinearLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(width);
			layoutParams.height = height == 0 ?
					LinearLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentHeightSize(height);
			view.setLayoutParams(layoutParams);
			return;
		}
	}

	@BindingAdapter({"layout_width"})
	public static void setLayoutWidth(View view, int width) {

		ViewParent parent = view.getParent();
		if (parent == null) {
			return;
		}
		if (parent instanceof FrameLayout) {
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = width == 0 ?
					FrameLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(width);
			view.setLayoutParams(layoutParams);
			return;
		}
		if (parent instanceof RelativeLayout) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = width == 0 ?
					RelativeLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(width);
			view.setLayoutParams(layoutParams);
			return;
		}
		if (parent instanceof LinearLayout) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = width == 0 ?
					LinearLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(width);
			view.setLayoutParams(layoutParams);
			return;
		}
	}

	@BindingAdapter("layout_height")
	public static void setLayoutHeight(View view, int height) {

		ViewParent parent = view.getParent();
		if (parent == null) {
			return;
		}
		if (parent instanceof FrameLayout) {
			FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
			layoutParams.height = height == 0 ?
					FrameLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(height);
			view.setLayoutParams(layoutParams);
			return;
		}
		if (parent instanceof RelativeLayout) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			layoutParams.height = height == 0 ?
					RelativeLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(height);
			view.setLayoutParams(layoutParams);
			return;
		}
		if (parent instanceof LinearLayout) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
			layoutParams.height = height == 0 ?
					LinearLayout.LayoutParams.MATCH_PARENT :
					AutoUtils.getPercentWidthSize(height);
			view.setLayoutParams(layoutParams);
			return;
		}
	}

	@BindingAdapter("canScroll")
	public static void setCanScroll(NestedScrollView view, boolean canScroll) {

		view.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return !canScroll;
			}
		});
	}

	@BindingAdapter("canScroll")
	public static void setCanScroll(ScrollView view, boolean canScroll) {

		view.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return !canScroll;
			}
		});
	}

	@BindingAdapter({"decodeTwice2Str"})
	public static void decodeTwice2Str(TextView view, String encodeContent) {

		try {
			String content = Base64.bytes2Str(Base64.decode(Base64.decode(encodeContent)));
			view.setText(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
