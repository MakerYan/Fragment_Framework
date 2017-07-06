package com.makeryan.lib.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jcodecraeer.xrecyclerview.LoadingMoreFooter;

/**
 * Created by Maker on 16/11/11.
 */

public class CustomLinearLayoutManager
		extends LinearLayoutManager {

	/**
	 * Creates a vertical LinearLayoutManager
	 *
	 * @param context
	 * 		Current context, will be used to access resources.
	 */
	public CustomLinearLayoutManager(Context context) {

		super(context);
	}

	/**
	 * @param context
	 * 		Current context, will be used to access resources.
	 * @param orientation
	 * 		Layout orientation. Should be {@link #HORIZONTAL} or {@link
	 * 		#VERTICAL}.
	 * @param reverseLayout
	 * 		When set to true, layouts from end to start.
	 */
	public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {

		super(
				context,
				orientation,
				reverseLayout
			 );
	}

	/**
	 * Constructor used when layout manager is set in XML by RecyclerView attribute
	 * "layoutManager". Defaults to vertical orientation.
	 *
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 *
	 * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_android_orientation
	 * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_reverseLayout
	 * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_stackFromEnd
	 */
	public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

		super(
				context,
				attrs,
				defStyleAttr,
				defStyleRes
			 );
	}

	@Override
	public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

		try {
			if (getChildCount() == 0) {
				return;
			}
			View view = recycler.getViewForPosition(0);
			measureChild(
					view,
					widthSpec,
					heightSpec
						);
			int measuredWidth  = View.MeasureSpec.getSize(widthSpec);
			int measuredHeight = view.getMeasuredHeight();
			setMeasuredDimension(
					measuredWidth,
					measuredHeight
								);
		} catch (IndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

		detachAndScrapAttachedViews(recycler);

		for (int i = 0; i < getItemCount(); i++) {
			View view = recycler.getViewForPosition(i);

			if (view == null || view.getVisibility() == View.GONE || view instanceof LoadingMoreFooter) {
				removeView(view);
				continue;
			}
		}
		super.onLayoutChildren(
				recycler,
				state
							  );
	}

	@Override
	public boolean canScrollHorizontally() {

		return super.canScrollHorizontally();
	}

	@Override
	public boolean canScrollVertically() {

		return super.canScrollVertically();
	}
}
