package com.makeryan.lib.widget.layoutmanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

import com.makeryan.lib.R;

/**
 * Created by Maker on 16/11/13.
 */

public class DividerItemDecoration
		extends RecyclerView.ItemDecoration {

	private static final int[] ATTRS = new int[]{
			android.R.attr.listDivider
	};

	protected int margin_left = 0;

	protected int margin_right = 0;

	public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

	public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

	private Drawable mDivider;

	private int mOrientation = VERTICAL_LIST;

	public DividerItemDecoration(Context context) {

		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		mDivider = a.getDrawable(0);
		a.recycle();
		setOrientation(mOrientation);
	}

	public DividerItemDecoration(Context context, @DrawableRes int deviderRes) {

		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		//		mDivider = a.getDrawable(0);
		mDivider = context.getResources()
						  .getDrawable(deviderRes);
		a.recycle();
		setOrientation(mOrientation);
	}

	public DividerItemDecoration(Context context, int orientation, @DrawableRes int deviderRes) {

		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		//		mDivider = a.getDrawable(0);
		mDivider = context.getResources()
						  .getDrawable(deviderRes);
		a.recycle();
		setOrientation(orientation);
	}

	public DividerItemDecoration(Context context, int orientation, @DrawableRes int deviderRes, int margin_left, int margin_right) {

		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		//		mDivider = a.getDrawable(0);
		mDivider = context.getResources()
						  .getDrawable(deviderRes);
		a.recycle();
		this.margin_left = AutoUtils.getPercentWidthSize(margin_left);
		this.margin_right = AutoUtils.getPercentWidthSize(margin_right);
		setOrientation(orientation);
	}

	public DividerItemDecoration(Context context, int orientation, int margin_left, int margin_right) {

		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		//		mDivider = a.getDrawable(0);
		mDivider = context.getResources()
						  .getDrawable(R.drawable.devider_linear_item_decoration_e9e9e9);
		a.recycle();
		this.margin_left = AutoUtils.getPercentWidthSize(margin_left);
		this.margin_right = AutoUtils.getPercentWidthSize(margin_right);
		setOrientation(orientation);
	}

	public void setOrientation(int orientation) {

		if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
			throw new IllegalArgumentException("invalid orientation");
		}
		mOrientation = orientation;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent) {

		if (mOrientation == VERTICAL_LIST) {
			drawVertical(
					c,
					parent
						);
		} else {
			drawHorizontal(
					c,
					parent
						  );
		}

	}


	public void drawVertical(Canvas c, RecyclerView parent) {

		final int left  = parent.getPaddingLeft() + margin_left;
		final int right = parent.getWidth() - parent.getPaddingRight() - margin_right;

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View                      child  = parent.getChildAt(i);
			RecyclerView                    v      = new RecyclerView(parent.getContext());
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int                       top    = child.getBottom() + params.bottomMargin;
			final int                       bottom = top + mDivider.getIntrinsicHeight();
			mDivider.setBounds(
					left,
					top,
					right,
					bottom
							  );
			mDivider.draw(c);
		}
	}

	public void drawHorizontal(Canvas c, RecyclerView parent) {

		final int top    = parent.getPaddingTop();
		final int bottom = parent.getHeight() - parent.getPaddingBottom();

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View                      child  = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int                       left   = child.getRight() + params.rightMargin;
			final int                       right  = left + mDivider.getIntrinsicHeight();
			mDivider.setBounds(
					left,
					top,
					right,
					bottom
							  );
			mDivider.draw(c);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {

		if (mOrientation == VERTICAL_LIST) {
			outRect.set(
					0,
					0,
					0,
					mDivider.getIntrinsicHeight()
					   );
		} else {
			outRect.set(
					0,
					0,
					mDivider.getIntrinsicWidth(),
					0
					   );
		}
	}
}
