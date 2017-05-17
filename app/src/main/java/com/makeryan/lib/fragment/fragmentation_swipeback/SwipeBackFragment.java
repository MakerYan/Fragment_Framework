package com.makeryan.lib.fragment.fragmentation_swipeback;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.fragment.fragmentation.SwipeBackLayout;

/**
 * SwipeBackFragment
 *
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/15 下午8:56
 */
public class SwipeBackFragment
		extends SupportFragment {

	private SwipeBackLayout mSwipeBackLayout;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (canSwipe()) {
			onFragmentCreate();
		}
	}

	private void onFragmentCreate() {

		mSwipeBackLayout = new SwipeBackLayout(_mActivity);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		mSwipeBackLayout.setLayoutParams(params);
		mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
	}

	protected View attachToSwipeBack(View view) {

		mSwipeBackLayout.attachToFragment(
				this,
				view
										 );
		return mSwipeBackLayout;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {

		super.onHiddenChanged(hidden);
		if (hidden && mSwipeBackLayout != null && canSwipe()) {
			mSwipeBackLayout.hiddenFragment();
		}
	}

	@Override
	protected void initFragmentBackground(View view) {

		if (view instanceof SwipeBackLayout) {
			View childView = ((SwipeBackLayout) view).getChildAt(0);
			setBackground(childView);
		} else {
			setBackground(view);
		}
	}

	public SwipeBackLayout getSwipeBackLayout() {

		return mSwipeBackLayout;
	}

	public void setSwipeBackEnable(boolean enable) {

		mSwipeBackLayout.setEnableGesture(enable);
	}

	/**
	 * Set the offset of the parallax slip.
	 */
	public void setParallaxOffset(@FloatRange(from = 0.0f, to = 1.0f) float offset) {

		mSwipeBackLayout.setParallaxOffset(offset);
	}
}
