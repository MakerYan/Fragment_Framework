package com.makeryan.lib.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader;
import com.jcodecraeer.xrecyclerview.LoadingMoreFooter;

/**
 * 自动换行布局管理
 * Created by chengxiakuan on 2016/10/14.
 */
public class WrapLayoutManager
		extends RecyclerView.LayoutManager {

	protected boolean mMeasureArrowRefreshHeader = true;

	protected boolean mMeasureLoadingMoreFooter = true;

	public WrapLayoutManager() {

		super();
		setAutoMeasureEnabled(true);
	}

	public WrapLayoutManager(boolean measureArrowRefreshHeader) {

		super();
		setAutoMeasureEnabled(true);
		mMeasureArrowRefreshHeader = measureArrowRefreshHeader;
	}

	public WrapLayoutManager(boolean measureArrowRefreshHeader, boolean measureLoadingMoreFooter) {

		super();
		setAutoMeasureEnabled(true);
		mMeasureArrowRefreshHeader = measureArrowRefreshHeader;
		mMeasureLoadingMoreFooter = measureLoadingMoreFooter;
	}

	@Override
	public RecyclerView.LayoutParams generateDefaultLayoutParams() {

		return new RecyclerView.LayoutParams(
				RecyclerView.LayoutParams.WRAP_CONTENT,
				RecyclerView.LayoutParams.WRAP_CONTENT
		);
	}

	/**
	 * 自适应高度
	 *
	 * @params
	 * @email light.yan@qq.com
	 * @author makeryan
	 * @Date 2017/5/11 上午10:43
	 */
	@Override
	public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

		try {
			if (getChildCount() == 0) {
				return;
			}

			View view = recycler.getViewForPosition(0);

			if (view == null) {
				return;
			}

			if (view.getVisibility() == View.GONE) {
				return;
			}

			if (view instanceof ArrowRefreshHeader && !mMeasureArrowRefreshHeader) {
				return;
			}

			if (view instanceof LoadingMoreFooter && !mMeasureLoadingMoreFooter) {
				return;
			}

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
		} finally {
			super.onMeasure(
					recycler,
					state,
					widthSpec,
					heightSpec
						   );
		}
	}

	/**
	 * 经过优化的自动换行
	 *
	 * @params
	 * @email light.yan@qq.com
	 * @author makeryan
	 * @Date 2017/5/11 上午10:44
	 */
	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

		detachAndScrapAttachedViews(recycler);

		int sumWidth = getWidth() - getPaddingRight();

		int curLineWidth      = getPaddingLeft(), curLineTop = getPaddingTop();
		int lastLineMaxHeight = 0;
		for (int i = 0; i < getItemCount(); i++) {
			View view = recycler.getViewForPosition(i);

			if (view == null) {
				continue;
			}

			if (view.getVisibility() == View.GONE) {
				continue;
			}

			if (view instanceof ArrowRefreshHeader && !mMeasureArrowRefreshHeader) {
				continue;
			}

			if (view instanceof LoadingMoreFooter && !mMeasureLoadingMoreFooter) {
				continue;
			}

			addView(view);
			measureChildWithMargins(
					view,
					0,
					0
								   );
			int width  = getDecoratedMeasuredWidth(view);
			int height = getDecoratedMeasuredHeight(view);

			curLineWidth += width;
			if (curLineWidth <= sumWidth) {//不需要换行
				layoutDecorated(
						view,
						curLineWidth - width,
						curLineTop,
						curLineWidth,
						curLineTop + height
							   );
				//比较当前行所有item的最大高度
				lastLineMaxHeight = Math.max(
						lastLineMaxHeight,
						height
											);
			} else {//换行
				curLineWidth = width + getPaddingLeft();
				if (lastLineMaxHeight == 0) {
					lastLineMaxHeight = height;
				}
				//记录当前行top
				curLineTop += lastLineMaxHeight;

				layoutDecorated(
						view,
						curLineWidth - width,
						curLineTop,
						curLineWidth,
						curLineTop + height
							   );
				lastLineMaxHeight = height;
			}
		}
	}

	public boolean isMeasureArrowRefreshHeader() {

		return mMeasureArrowRefreshHeader;
	}

	public void setMeasureArrowRefreshHeader(boolean measureArrowRefreshHeader) {

		mMeasureArrowRefreshHeader = measureArrowRefreshHeader;
		requestLayout();
	}

	public boolean isMeasureLoadingMoreFooter() {

		return mMeasureLoadingMoreFooter;
	}

	public void setMeasureLoadingMoreFooter(boolean measureLoadingMoreFooter) {

		mMeasureLoadingMoreFooter = measureLoadingMoreFooter;
		requestLayout();
	}
}
