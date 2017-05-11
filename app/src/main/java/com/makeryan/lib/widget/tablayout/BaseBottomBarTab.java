package com.makeryan.lib.widget.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by MakerYan on 2017/4/26 15:49.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : kongrongqi_android
 * package name : com.makeryan.lib.widget.tablayout
 */

public abstract class BaseBottomBarTab
		extends LinearLayout {


	public BaseBottomBarTab(Context context) {

		super(context);
	}

	public BaseBottomBarTab(Context context, @Nullable AttributeSet attrs) {

		super(
				context,
				attrs
			 );
	}

	public BaseBottomBarTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

		super(
				context,
				attrs,
				defStyleAttr
			 );
	}

	public abstract int getTabPosition();

	public abstract void setTabPosition(int position);

	/**
	 * 设置未读数量
	 */
	public abstract void setUnreadCount(int num);

	/**
	 * 获取当前未读数量
	 */
	public abstract int getUnreadCount();
}
