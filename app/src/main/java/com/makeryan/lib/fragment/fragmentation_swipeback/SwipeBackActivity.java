package com.makeryan.lib.fragment.fragmentation_swipeback;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.makeryan.lib.fragment.fragmentation.SupportActivity;
import com.makeryan.lib.fragment.fragmentation.SwipeBackLayout;


/**
 * SwipeBackActivity
 *
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/15 下午8:56
 */
public class SwipeBackActivity
		extends SupportActivity {

	private SwipeBackLayout mSwipeBackLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (canSwipe()) {
			onActivityCreate();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
		if (canSwipe()) {
			mSwipeBackLayout.attachToActivity(this);
		}
	}

	@Override
	public View findViewById(int id) {

		View view = super.findViewById(id);
		if (canSwipe()) {
			if (view == null && mSwipeBackLayout != null) {
				return mSwipeBackLayout.findViewById(id);
			}
		}
		return view;
	}

	void onActivityCreate() {

		if (canSwipe()) {
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			getWindow().getDecorView()
					   .setBackgroundDrawable(null);
			mSwipeBackLayout = new SwipeBackLayout(this);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT
			);
			mSwipeBackLayout.setLayoutParams(params);
		}
	}

	public SwipeBackLayout getSwipeBackLayout() {

		return mSwipeBackLayout;
	}

	public void setSwipeBackEnable(boolean enable) {

		mSwipeBackLayout.setEnableGesture(enable);
	}

	/**
	 * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
	 *
	 * @return true: Activity可以滑动退出, 并且总是优先;  false: Fragment优先滑动退出
	 */
	public boolean swipeBackPriority() {

		return getSupportFragmentManager().getBackStackEntryCount() <= 1;
	}
}
