package com.makeryan.lib.photopicker.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by donglua on 15/6/21.
 */
public class SquareItemLayout
		extends RelativeLayout {

	private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

	public SquareItemLayout(Context context, AttributeSet attrs, int defStyle) {

		super(
				context,
				attrs,
				defStyle
			 );
	}

	public SquareItemLayout(Context context, AttributeSet attrs) {

		super(
				context,
				attrs
			 );
	}

	public SquareItemLayout(Context context) {

		super(context);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SquareItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

		super(
				context,
				attrs,
				defStyleAttr,
				defStyleRes
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
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

		super.onLayout(
				changed,
				left,
				top,
				right,
				bottom
					  );
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (!isInEditMode()) {
			mHelper.adjustChildren();
		}
		setMeasuredDimension(
				getDefaultSize(
						0,
						widthMeasureSpec
							  ),
				getDefaultSize(
						0,
						heightMeasureSpec
							  )
							);
		int childWidthSize = getMeasuredWidth();
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
				childWidthSize,
				MeasureSpec.EXACTLY
																		  );
		super.onMeasure(
				widthMeasureSpec,
				heightMeasureSpec
					   );
	}

	public static class LayoutParams
			extends RelativeLayout.LayoutParams
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
