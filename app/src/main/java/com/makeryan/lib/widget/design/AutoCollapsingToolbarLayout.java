package com.makeryan.lib.widget.design;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;


/**
 * Created by MakerYan on 2017/4/20 21:08.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : kongrongqi_android
 * package name : com.makeryan.lib.widget.design
 */

public class AutoCollapsingToolbarLayout
		extends CollapsingToolbarLayout {

	private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

	public AutoCollapsingToolbarLayout(Context context) {

		this(context,
			 null);
	}

	public AutoCollapsingToolbarLayout(Context context, AttributeSet attrs) {

		this(context,
			 attrs,
			 0
			);
	}

	public AutoCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {

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
			extends android.support.design.widget.CollapsingToolbarLayout.LayoutParams
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
