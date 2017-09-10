package com.makeryan.lib.widget.wraplayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.utils.AutoLayoutHelper;

import java.util.ArrayList;

import com.makeryan.lib.R;

/**
 * 自动换行布局
 * https://github.com/AlexMofer/WrapLayout
 */
public class WrapLayout
		extends ViewGroup {

	public static final int GRAVITY_PARENT = -1;// 使用全局对齐方案

	public static final int GRAVITY_TOP = 0;// 子项顶部对齐

	public static final int GRAVITY_CENTER = 1;// 子项居中对齐

	public static final int GRAVITY_BOTTOM = 2; // 子项底部对齐

	private static final int[] ATTRS = new int[]{
			android.R.attr.horizontalSpacing,
			android.R.attr.verticalSpacing
	};

	private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

	private int mVerticalSpacing = 0;

	private int mHorizontalSpacing = 0;

	private int mNumRows = 0;

	private int mMaxNumRows = Integer.MAX_VALUE;

	private ArrayList<Integer> mNumColumns = new ArrayList<>();

	private ArrayList<Integer> mChildMaxWidth = new ArrayList<>();

	private int mGravity = GRAVITY_TOP;

	public WrapLayout(Context context) {

		super(context);
		initView(
				context,
				null,
				0
				);
	}

	public WrapLayout(Context context, AttributeSet attrs) {

		super(
				context,
				attrs
			 );
		initView(
				context,
				attrs,
				0
				);
	}

	@TargetApi(11)
	public WrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {

		super(
				context,
				attrs,
				defStyleAttr
			 );
		initView(
				context,
				attrs,
				defStyleAttr
				);
	}

	@TargetApi(21)
	public WrapLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

		super(
				context,
				attrs,
				defStyleAttr,
				defStyleRes
			 );
		initView(
				context,
				attrs,
				defStyleAttr
				);
	}

	private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

		final TypedArray a = context.obtainStyledAttributes(
				attrs,
				ATTRS,
				defStyleAttr,
				0
														   );
		int n                 = a.getIndexCount();
		int horizontalSpacing = 0;
		int verticalSpacing   = 0;
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case 0:
					horizontalSpacing = a.getDimensionPixelSize(
							attr,
							horizontalSpacing
															   );
					break;
				case 1:
					verticalSpacing = a.getDimensionPixelSize(
							attr,
							verticalSpacing
															 );
					break;
			}
		}
		a.recycle();
		TypedArray custom = context.obtainStyledAttributes(
				attrs,
				R.styleable.WrapLayout
														  );
		horizontalSpacing = custom.getDimensionPixelSize(
				R.styleable.WrapLayout_wlyHorizontalSpacing,
				horizontalSpacing
														);
		verticalSpacing = custom.getDimensionPixelSize(
				R.styleable.WrapLayout_wlyVerticalSpacing,
				verticalSpacing
													  );
		int gravity = custom.getInt(
				R.styleable.WrapLayout_wlyGravity,
				GRAVITY_TOP
								   );
		int maxNumRows = custom.getInteger(
				R.styleable.WrapLayout_wlyMaxRows,
				Integer.MAX_VALUE
										  );
		custom.recycle();
		mHorizontalSpacing = horizontalSpacing;
		mVerticalSpacing = verticalSpacing;
		mGravity = gravity;
		mMaxNumRows = maxNumRows;
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {

		return new LayoutParams(
				getContext(),
				attrs
		);
	}

	/**
	 * Returns a set of layout parameters with a width of
	 * {@link ViewGroup.LayoutParams#WRAP_CONTENT},
	 * a height of {@link ViewGroup.LayoutParams#WRAP_CONTENT} and no spanning.
	 */
	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {

		return new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
		);
	}

	// Override to allow type-checking of LayoutParams.
	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {

		return p instanceof LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {

		if (lp instanceof LayoutParams) {
			return new LayoutParams((LayoutParams) lp);
		} else {
			return new LayoutParams(lp);
		}
	}

	@Override
	public void addView(View child) {

		if (getNumRows() > mMaxNumRows) {
			return;
		}
		super.addView(child);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (!isInEditMode()) {
			mHelper.adjustChildren();
		}
		final int paddingStart           = Compat.getPaddingStart(this);
		final int paddingEnd             = Compat.getPaddingEnd(this);
		final int paddingTop             = getPaddingTop();
		final int paddingBottom          = getPaddingBottom();
		final int suggestedMinimumWidth  = getSuggestedMinimumWidth();
		final int suggestedMinimumHeight = getSuggestedMinimumHeight();
		final int widthMode              = MeasureSpec.getMode(widthMeasureSpec);
		final int widthSize              = MeasureSpec.getSize(widthMeasureSpec);
		int       itemsWidth             = 0;
		int       itemsHeight            = 0;
		mNumRows = 0;
		mNumColumns.clear();
		mChildMaxWidth.clear();
		if (getChildCount() > 0) {
			if (widthMode == MeasureSpec.UNSPECIFIED) {
				int numColumns = 0;
				for (int index = 0; index < getChildCount(); index++) {
					View child = getChildAt(index);
					if (child.getVisibility() == View.GONE) {
						continue;
					}
					if (mNumRows == 0) {
						mNumRows = 1;
					}
					measureChild(
							child,
							widthMeasureSpec,
							heightMeasureSpec
								);
					final int childWidth  = child.getMeasuredWidth();
					final int childHeight = child.getMeasuredHeight();
					if (numColumns == 0) {
						itemsWidth = -mHorizontalSpacing;
					}
					itemsWidth += mHorizontalSpacing + childWidth;
					itemsHeight = Math.max(
							childHeight,
							itemsHeight
										  );
					numColumns++;
				}
				if (numColumns != 0) {
					mNumColumns.add(numColumns);
					mChildMaxWidth.add(itemsHeight);
				}
			} else {
				int       numColumns    = 0;
				final int maxItemsWidth = widthSize - paddingStart - paddingEnd;
				int       rowWidth      = 0;
				int       rowHeight     = 0;
				for (int index = 0; index < getChildCount(); index++) {
					View child = getChildAt(index);
					if (child.getVisibility() == View.GONE) {
						continue;
					}
					if (mNumRows == 0) {
						mNumRows = 1;
					}
					measureChild(
							child,
							widthMeasureSpec,
							heightMeasureSpec
								);
					final int childWidth  = child.getMeasuredWidth();
					final int childHeight = child.getMeasuredHeight();
					if (numColumns == 0) {
						rowWidth = -mHorizontalSpacing;
					}
					if (rowWidth + childWidth + mHorizontalSpacing <= maxItemsWidth) {
						rowWidth += childWidth + mHorizontalSpacing;
						rowHeight = Math.max(
								childHeight,
								rowHeight
											);
						numColumns++;
					} else {
						itemsWidth = Math.max(
								rowWidth,
								itemsWidth
											 );
						itemsHeight += mNumRows == 1 ?
								rowHeight :
								mVerticalSpacing + rowHeight;
						mNumColumns.add(numColumns);
						mChildMaxWidth.add(rowHeight);
						mNumRows++;
						rowWidth = 0;
						rowHeight = 0;
						numColumns = 0;
						rowWidth += childWidth;
						rowHeight = Math.max(
								childHeight,
								rowHeight
											);
						numColumns++;
					}
				}
				if (numColumns != 0) {
					itemsHeight += mNumRows == 1 ?
							rowHeight :
							mVerticalSpacing + rowHeight;
					mNumColumns.add(numColumns);
					mChildMaxWidth.add(rowHeight);
				}
			}
		}
		itemsWidth = Math.max(
				paddingStart + itemsWidth + paddingEnd,
				suggestedMinimumWidth
							 );
		itemsHeight = Math.max(
				paddingTop + itemsHeight + paddingBottom,
				suggestedMinimumHeight
							  );
		setMeasuredDimension(
				resolveSize(
						itemsWidth,
						widthMeasureSpec
						   ),
				resolveSize(
						itemsHeight,
						heightMeasureSpec
						   )
							);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		final int paddingStart = Compat.getPaddingStart(this);
		final int paddingTop   = getPaddingTop();
		final int gravity      = mGravity;
		int       numChild     = 0;
		int       columnTop    = paddingTop - mVerticalSpacing;
		for (int row = 0; row < mNumRows; row++) {
			int numColumn      = mNumColumns.get(row);
			int childMaxHeight = mChildMaxWidth.get(row);
			int startX         = paddingStart - mHorizontalSpacing;
			for (int index = 0; index < numColumn; ) {
				View childView = getChildAt(numChild);
				numChild++;
				if (childView == null || childView.getVisibility() == View.GONE) {
					continue;
				}
				final int          childWidth   = childView.getMeasuredWidth();
				final int          childHeight  = childView.getMeasuredHeight();
				final LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
				final int          childGravity = layoutParams.getGravity();

				startX += mHorizontalSpacing;
				int topOffset;
				switch (childGravity) {
					default:
					case GRAVITY_PARENT: {
						switch (gravity) {
							case GRAVITY_CENTER:
								topOffset = Math.round((childMaxHeight - childHeight) * 0.5f);
								break;
							case GRAVITY_BOTTOM:
								topOffset = childMaxHeight - childHeight;
								break;
							default:
							case GRAVITY_TOP:
								topOffset = 0;
								break;
						}
					}
					break;
					case GRAVITY_CENTER:
						topOffset = Math.round((childMaxHeight - childHeight) * 0.5f);
						break;
					case GRAVITY_BOTTOM:
						topOffset = childMaxHeight - childHeight;
						break;
					case GRAVITY_TOP:
						topOffset = 0;
						break;
				}
				int startY = columnTop + mVerticalSpacing + topOffset;
				childView.layout(
						startX,
						startY,
						startX + childWidth,
						startY + childHeight
								);
				startX += childWidth;
				index++;
			}
			columnTop += mVerticalSpacing + childMaxHeight;
		}
	}

	/**
	 * 获取水平间距
	 *
	 * @return 水平间距
	 */
	@SuppressWarnings("unused")
	public int getHorizontalSpacing() {

		return mHorizontalSpacing;
	}

	/**
	 * 设置水平间距
	 *
	 * @param pixelSize
	 * 		水平间距
	 */
	@SuppressWarnings("unused")
	public void setHorizontalSpacing(int pixelSize) {

		mHorizontalSpacing = pixelSize;
		requestLayout();
	}

	/**
	 * 获取垂直间距
	 *
	 * @return 垂直间距
	 */
	@SuppressWarnings("unused")
	public int getVerticalSpacing() {

		return mVerticalSpacing;
	}

	/**
	 * 设置垂直间距
	 *
	 * @param pixelSize
	 * 		垂直间距
	 */
	@SuppressWarnings("unused")
	public void setVerticalSpacing(int pixelSize) {

		mVerticalSpacing = pixelSize;
		requestLayout();
	}

	/**
	 * 获取行数目
	 *
	 * @return 行数目
	 */
	@SuppressWarnings("unused")
	public int getNumRows() {

		return mNumRows;
	}

	/**
	 * 获取某一行列数目
	 *
	 * @param index
	 * 		行号
	 *
	 * @return 列数目
	 */
	@SuppressWarnings("unused")
	public int getNumColumns(int index) {

		int numColumns = -1;
		if (index < 0 || index >= mNumColumns.size()) {
			return numColumns;
		}
		return mNumColumns.get(index);
	}

	/**
	 * 获取子项对齐模式
	 *
	 * @return 对齐模式
	 */
	@SuppressWarnings("unused")
	public int getGravity() {

		return mGravity;
	}

	/**
	 * 设置子项对齐模式
	 *
	 * @param gravity
	 * 		对齐模式
	 */
	@SuppressWarnings("unused")
	public void setGravity(int gravity) {

		if (gravity != GRAVITY_TOP && gravity != GRAVITY_CENTER && gravity != GRAVITY_BOTTOM) {
			return;
		}
		mGravity = gravity;
		requestLayout();
	}

	/**
	 * Per-child layout information associated with WrapLayout.
	 */
	@SuppressWarnings("all")
	public static class LayoutParams
			extends ViewGroup.LayoutParams
			implements AutoLayoutHelper.AutoLayoutParams {

		private int mGravity = WrapLayout.GRAVITY_PARENT;

		private AutoLayoutInfo mAutoLayoutInfo;

		public LayoutParams(Context c, AttributeSet attrs) {

			super(
					c,
					attrs
				 );


			int gravity = WrapLayout.GRAVITY_PARENT;
			TypedArray custom = c.obtainStyledAttributes(
					attrs,
					R.styleable.WrapLayout
														);
			gravity = custom.getInt(
					R.styleable.WrapLayout_wlyGravity,
					gravity
								   );
			custom.recycle();
			mGravity = gravity;
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

		public LayoutParams(LayoutParams source) {

			super(source);
			mGravity = source.mGravity;
		}

		@Override
		public AutoLayoutInfo getAutoLayoutInfo() {

			return mAutoLayoutInfo;
		}

		/**
		 * 设置布局
		 *
		 * @param gravity
		 * 		布局
		 */
		public void setGravity(int gravity) {

			mGravity = gravity;
		}

		/**
		 * 获取布局
		 *
		 * @return 布局
		 */
		public int getGravity() {

			return mGravity;
		}
	}
}
