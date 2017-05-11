package com.makeryan.lib.widget.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.socks.library.KLog;

import java.lang.reflect.Field;

/**
 * 控制是否可否滑动
 */
public class CustomViewPager
		extends ViewPager {

	private boolean canScroll;

	public CustomViewPager(Context context) {

		super(context);
		canScroll = false;
	}

	public CustomViewPager(Context context, AttributeSet attrs) {

		super(
				context,
				attrs
			 );
		canScroll = false;
	}

	@Override
	protected void onAttachedToWindow() {

		super.onAttachedToWindow();
		try {
			Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
			mFirstLayout.setAccessible(true);
			mFirstLayout.set(
					this,
					false
							);
			PagerAdapter adapter = getAdapter();
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
			int currentItem = getCurrentItem();
			setCurrentItem(currentItem);
		} catch (Exception e) {
			KLog.e(e.getCause());
			KLog.e(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		if (canScroll) {
			try {
				return super.onInterceptTouchEvent(ev);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (canScroll) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	public void toggleLock() {

		canScroll = !canScroll;
	}

	public void setCanScroll(boolean canScroll) {

		this.canScroll = canScroll;
	}

	public boolean isCanScroll() {

		return canScroll;
	}

}
