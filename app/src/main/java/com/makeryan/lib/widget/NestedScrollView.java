package com.makeryan.lib.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by MakerYan on 2017/4/12 16:47.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.makeryan.lib.widget
 */

public class NestedScrollView
		extends android.support.v4.widget.NestedScrollView {

	private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

	public NestedScrollView(Context context) {

		super(context);
	}

	public NestedScrollView(Context context, AttributeSet attrs) {

		super(
				context,
				attrs
			 );
	}

	public NestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(
				context,
				attrs,
				defStyleAttr
			 );
	}


	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {

		return new LayoutParams(
				getContext(),
				attrs
		);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (!isInEditMode()) {
			mHelper.adjustChildren();
		}
		super.onMeasure(
				widthMeasureSpec,
				heightMeasureSpec
					   );
	}

	public static class LayoutParams
			extends android.support.v4.widget.NestedScrollView.LayoutParams
			implements AutoLayoutHelper.AutoLayoutParams {

		private AutoLayoutInfo mAutoLayoutInfo;

		public LayoutParams(Context c, AttributeSet attrs) {

			super(
					c,
					attrs
				 );
			mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(
					c,
					attrs
																);
		}

		public LayoutParams(int width, int height) {

			super(
					width,
					height
				 );
		}

		public LayoutParams(ViewGroup.LayoutParams source) {

			super(source);
		}

		public LayoutParams(MarginLayoutParams source) {

			super(source);
		}

		@Override
		public AutoLayoutInfo getAutoLayoutInfo() {

			return mAutoLayoutInfo;
		}

	}
}
