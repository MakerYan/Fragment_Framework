package com.makeryan.lib.widget;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.makeryan.lib.util.ImageUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * Created by MakerYan on 16/9/21 18:47.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : ChengGua2
 * package name : com.chenggua.cg.app.lib.view.widget
 */

public class ImageHorizontalContainer
		extends LinearLayout {

	protected LayoutInflater mInflater;

	public ImageHorizontalContainer(Context context) {

		super(context);
		init(context);
	}

	public ImageHorizontalContainer(Context context, AttributeSet attrs) {

		super(
				context,
				attrs
			 );
		init(context);
	}

	public ImageHorizontalContainer(Context context, AttributeSet attrs, int defStyleAttr) {

		super(
				context,
				attrs,
				defStyleAttr
			 );
		init(context);
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	protected void init(Context context) {

		setOrientation(OrientationHelper.HORIZONTAL);
		mInflater = LayoutInflater.from(context);
	}

	/**
	 * 添加图片
	 */
	public void addImages(List<String> imgs) {

		addImages(
				imgs,
				0
				 );
	}

	/**
	 * 添加图片
	 *
	 * @param imgs
	 * 		Url list
	 * @param imgSize
	 * 		图片大小 比如 R.dimen.y38
	 */
	public void addImages(List<String> imgs, int imgSize) {

		int size = imgSize == 0 ?
				AutoUtils.getPercentWidthSize(38) :
				imgSize;
		removeAllViews();
		Context context = getContext();
		LayoutParams layoutParams = new LayoutParams(
				getResources().getDimensionPixelSize(size),
				getResources().getDimensionPixelSize(size)
		);
		layoutParams.rightMargin = AutoUtils.getPercentWidthSize(15);
		layoutParams.weight = 1;
		for (String url : imgs) {
			CircleImageView circleImageView = new CircleImageView(context);
			circleImageView.setLayoutParams(layoutParams);
			circleImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			ImageUtil.loadAsBitmap(
					url,
					circleImageView
								  );
			addView(circleImageView);
		}
		requestLayout();
	}
}
