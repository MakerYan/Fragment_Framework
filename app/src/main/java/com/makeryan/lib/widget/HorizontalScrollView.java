package com.makeryan.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;


/**
 * Created by MakerYan on 2017/8/29 09:44.
 * Modify by MakerYan on 2017/8/29 09:44.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.widget
 */
public class HorizontalScrollView
		extends android.widget.HorizontalScrollView {

	private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

	public HorizontalScrollView(Context context) {

		super(context);
	}

	public HorizontalScrollView(Context context, AttributeSet attrs) {

		super(context,
			  attrs);
	}

	public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context,
			  attrs,
			  defStyleAttr);
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
	}public static class LayoutParams
			extends android.widget.HorizontalScrollView.LayoutParams
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
