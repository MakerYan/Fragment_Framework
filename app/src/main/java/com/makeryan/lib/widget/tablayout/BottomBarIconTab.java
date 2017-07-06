package com.makeryan.lib.widget.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeryan.lib.R;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * Created by YoKeyword on 16/6/3.
 */
public class BottomBarIconTab
		extends BaseBottomBarTab {

	private ImageView mIcon;

	private Context mContext;

	private int mTabPosition = -1;

	private TextView mTvUnreadCount;

	public BottomBarIconTab(Context context, @DrawableRes int icon) {

		this(
				context,
				null,
				icon
			);
	}

	public BottomBarIconTab(Context context, AttributeSet attrs, int icon) {

		this(
				context,
				attrs,
				0,
				icon
			);
	}

	public BottomBarIconTab(Context context, AttributeSet attrs, int defStyleAttr, int icon) {

		super(
				context,
				attrs,
				defStyleAttr
			 );
		init(
				context,
				icon
			);
	}

	private void init(Context context, int icon) {

		mContext = context;
		TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
		Drawable   drawable   = typedArray.getDrawable(0);
		setBackgroundDrawable(drawable);
		typedArray.recycle();

		RelativeLayout rLContainer = new RelativeLayout(context);
		rLContainer.setGravity(Gravity.CENTER);
		LayoutParams paramsContainer = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		paramsContainer.gravity = Gravity.CENTER;
		rLContainer.setLayoutParams(paramsContainer);

		mIcon = new ImageView(context);
		int size = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				27,
				getResources().getDisplayMetrics()
												  );
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT
		);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		mIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		mIcon.setPadding(
				0,
				AutoUtils.getPercentHeightSize(12),
				0,
				AutoUtils.getPercentHeightSize(12)
						);
		mIcon.setImageResource(icon);
		mIcon.setLayoutParams(params);
		mIcon.setColorFilter(ContextCompat.getColor(
				context,
				R.color.tab_unselect
												   ));
		rLContainer.addView(mIcon);

		addView(rLContainer);

		int min = dip2px(
				context,
				20
						);
		int padding = dip2px(
				context,
				5
							);
		mTvUnreadCount = new TextView(context);
		mTvUnreadCount.setBackgroundResource(R.drawable.bg_msg_bubble);
		mTvUnreadCount.setMinWidth(min);
		mTvUnreadCount.setTextColor(Color.WHITE);
		mTvUnreadCount.setPadding(
				padding,
				0,
				padding,
				0
								 );
		mTvUnreadCount.setGravity(Gravity.CENTER);
		LayoutParams tvUnReadParams = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				min
		);
		tvUnReadParams.gravity = Gravity.CENTER;
		tvUnReadParams.leftMargin = dip2px(
				context,
				17
										  );
		tvUnReadParams.bottomMargin = dip2px(
				context,
				14
											);
		mTvUnreadCount.setLayoutParams(tvUnReadParams);
		mTvUnreadCount.setVisibility(GONE);

		addView(mTvUnreadCount);
	}

	@Override
	public void setSelected(boolean selected) {

		super.setSelected(selected);
		if (selected) {
			mIcon.setColorFilter(ContextCompat.getColor(
					mContext,
					R.color.cl_313133
													   ));
		} else {
			mIcon.setColorFilter(ContextCompat.getColor(
					mContext,
					R.color.cl_bcbcbc
													   ));
		}
	}

	public void setTabPosition(int position) {

		mTabPosition = position;
		if (position == 0) {
			setSelected(true);
		}
	}

	public int getTabPosition() {

		return mTabPosition;
	}

	/**
	 * 设置未读数量
	 */
	public void setUnreadCount(int num) {

		if (num <= 0) {
			mTvUnreadCount.setText(String.valueOf(0));
			mTvUnreadCount.setVisibility(GONE);
		} else {
			mTvUnreadCount.setVisibility(VISIBLE);
			if (num > 99) {
				mTvUnreadCount.setText("99+");
			} else {
				mTvUnreadCount.setText(String.valueOf(num));
			}
		}
	}

	/**
	 * 获取当前未读数量
	 */
	public int getUnreadCount() {

		int count = 0;
		if (TextUtils.isEmpty(mTvUnreadCount.getText())) {
			return count;
		}
		if (mTvUnreadCount.getText()
						  .toString()
						  .equals("99+")) {
			return 99;
		}
		try {
			count = Integer.valueOf(mTvUnreadCount.getText()
												  .toString());
		} catch (Exception ignored) {
		}
		return count;
	}

	private int dip2px(Context context, float dp) {

		return (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				dp,
				context.getResources()
					   .getDisplayMetrics()
											  );
	}
}
