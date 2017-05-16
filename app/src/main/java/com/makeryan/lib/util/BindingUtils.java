package com.makeryan.lib.util;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeryan.lib.widget.CircleImageView;
import com.makeryan.lib.widget.CustomShapeImageView;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

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
	public static void src(ImageView view, int url) {

		if (url != 0) {
			view.setImageDrawable(view.getResources()
									  .getDrawable(url));
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

	@BindingAdapter("src")
	public static void src(ImageView view, Drawable src) {

		view.setImageDrawable(src);
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
			"scaleType"
	})
	public static void src(CustomShapeImageView view, String url, ImageView.ScaleType scaleType) {

		if (!TextUtils.isEmpty(url)) {
			view.setScaleType(scaleType);
			ImageUtil.loadAsBitmap(
					url,
					view
								  );
		}
	}

	@BindingAdapter({"src"})
	public static void src(CustomShapeImageView view, int url) {

		if (url != 0) {
			view.setImageDrawable(view.getResources()
									  .getDrawable(url));
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
					view
								  );
		}
	}

	@BindingAdapter("src")
	public static void src(CustomShapeImageView view, Drawable src) {

		view.setImageDrawable(src);
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

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableLeft(final TextView view, String url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 Drawable db = isCircle ?
									 GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(drawable))) :
									 GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
											 GlobUtils.drawableConvertBitmap(drawable),
											 AutoUtils.getPercentWidthSize(30)
																							  ));
							 db.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
										 );
							 view.setCompoundDrawables(
									 db,
									 null,
									 null,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableLeft(final TextView view, @DrawableRes int url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (url != 0) {
			Context   context   = view.getContext();
			Resources resources = context.getResources();
			Drawable  drawable  = resources.getDrawable(url);
			Drawable db = isCircle ?
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(drawable))) :
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
							GlobUtils.drawableConvertBitmap(drawable),
							AutoUtils.getPercentWidthSize(30)
																			 ));
			db.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
						);
			view.setCompoundDrawables(
					db,
					null,
					null,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableLeft",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableLeft(final TextView view, Drawable url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (url != null) {

			Drawable db = isCircle ?
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(url))) :
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
							GlobUtils.drawableConvertBitmap(url),
							AutoUtils.getPercentWidthSize(30)
																			 ));
			db.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
						);
			view.setCompoundDrawables(
					db,
					null,
					null,
					null
									 );
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

	@BindingAdapter({
			"drawableTop",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableTop(final TextView view, String url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 Drawable db = isCircle ?
									 GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(drawable))) :
									 GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
											 GlobUtils.drawableConvertBitmap(drawable),
											 AutoUtils.getPercentWidthSize(30)
																							  ));
							 db.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
										 );
							 view.setCompoundDrawables(
									 null,
									 db,
									 null,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({
			"drawableTop",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableTop(final TextView view, @DrawableRes int url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (url != 0) {
			Context   context   = view.getContext();
			Resources resources = context.getResources();
			Drawable  drawable  = resources.getDrawable(url);
			Drawable db = isCircle ?
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(drawable))) :
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
							GlobUtils.drawableConvertBitmap(drawable),
							AutoUtils.getPercentWidthSize(30)
																			 ));
			db.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
						);
			view.setCompoundDrawables(
					null,
					db,
					null,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableTop",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableTop(final TextView view, Drawable url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (url != null) {

			Drawable db = isCircle ?
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(url))) :
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
							GlobUtils.drawableConvertBitmap(url),
							AutoUtils.getPercentWidthSize(30)
																			 ));
			db.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
						);
			view.setCompoundDrawables(
					null,
					db,
					null,
					null
									 );
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

	@BindingAdapter({
			"drawableRight",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableRight(final TextView view, String url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (!TextUtils.isEmpty(url)) {
			ImageUtil.getDrawableRequestBuilder(url)
					 .into(new SimpleTarget<GlideDrawable>() {

						 @Override
						 public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {

							 Drawable db = isCircle ?
									 GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(drawable))) :
									 GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
											 GlobUtils.drawableConvertBitmap(drawable),
											 AutoUtils.getPercentWidthSize(30)
																							  ));
							 db.setBounds(
									 0,
									 0,
									 AutoUtils.getPercentWidthSize(drawableWidth),
									 AutoUtils.getPercentHeightSize(drawableHeight)
										 );
							 view.setCompoundDrawables(
									 null,
									 null,
									 db,
									 null
													  );
						 }
					 });
		}
	}

	@BindingAdapter({
			"drawableRight",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableRight(final TextView view, @DrawableRes int url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (url != 0) {
			Context   context   = view.getContext();
			Resources resources = context.getResources();
			Drawable  drawable  = resources.getDrawable(url);
			Drawable db = isCircle ?
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(drawable))) :
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
							GlobUtils.drawableConvertBitmap(drawable),
							AutoUtils.getPercentWidthSize(30)
																			 ));
			db.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
						);
			view.setCompoundDrawables(
					null,
					null,
					db,
					null
									 );
		}
	}

	@BindingAdapter({
			"drawableRight",
			"drawableWidth",
			"drawableHeight",
			"isCircle"
	})
	public static void drawableRight(final TextView view, Drawable url, final int drawableWidth, final int drawableHeight, final boolean isCircle) {

		if (url != null) {

			Context   context   = view.getContext();
			Resources resources = context.getResources();
			Drawable db = isCircle ?
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(GlobUtils.drawableConvertBitmap(url))) :
					GlobUtils.bitmapConvertDrawable(GlobUtils.makeRoundCorner(
							GlobUtils.drawableConvertBitmap(url),
							AutoUtils.getPercentWidthSize(30)
																			 ));
			db.setBounds(
					0,
					0,
					AutoUtils.getPercentWidthSize(drawableWidth),
					AutoUtils.getPercentHeightSize(drawableHeight)
						);
			view.setCompoundDrawables(
					null,
					null,
					db,
					null
									 );
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

	@BindingAdapter("layout_height")
	public static void setLayoutHeight(RelativeLayout view, float height) {

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					(int) height
			);
		} else {
			layoutParams.height = (int) height;
		}
		view.setLayoutParams(layoutParams);
	}

	@BindingAdapter("layout_height")
	public static void setLayoutHeight(LinearLayout view, int dimenHeightRes) {

		Context                context      = view.getContext();
		Resources              resources    = context.getResources();
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					dimenHeightRes == -2 ?
							ViewGroup.LayoutParams.WRAP_CONTENT :
							resources.getDimensionPixelSize(dimenHeightRes)
			);
		} else {
			layoutParams.height = dimenHeightRes == -2 ?
					ViewGroup.LayoutParams.WRAP_CONTENT :
					resources.getDimensionPixelSize(dimenHeightRes);
		}
		view.setLayoutParams(layoutParams);
	}

	@BindingAdapter("android:layout_height")
	public static void setLayoutHeight(FrameLayout view, float height) {

		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					(int) height
			);
		} else {
			layoutParams.height = (int) height;
		}
		view.setLayoutParams(layoutParams);
	}

	@BindingAdapter("layout_width")
	public static void setLayoutWidth(ImageView view, int dimenWidth) {

		Context                context      = view.getContext();
		Resources              resources    = context.getResources();
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new ViewGroup.LayoutParams(
					AutoUtils.getPercentWidthSize(dimenWidth),
					ViewGroup.LayoutParams.WRAP_CONTENT
			);
		} else {
			layoutParams.width = AutoUtils.getPercentWidthSize(dimenWidth);
		}
		view.setLayoutParams(layoutParams);
	}


	@BindingAdapter("layout_height")
	public static void setLayoutHeight(ImageView view, int height) {

		Context                context      = view.getContext();
		Resources              resources    = context.getResources();
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					AutoUtils.getPercentHeightSize(height)
			);
		} else {
			layoutParams.height = AutoUtils.getPercentHeightSize(height);
		}
		view.setLayoutParams(layoutParams);
	}
}
