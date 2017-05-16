package com.makeryan.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.makeryan.lib.R;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/15 下午9:43
 */
public class BaseDialog {

	protected Dialog mDialog;

	protected int mGravity = -100;

	protected int mPaddingLeft = 0;

	protected int mPaddingTop = 0;

	protected int mPaddingRight = 0;

	protected int mPaddingBottom = 0;

	protected int mWidth = -100;

	protected int mHeight = -100;

	protected Window mWindow;

	protected View mDecorView;

	protected DialogInterface.OnCancelListener mCancelListener;

	protected DialogInterface.OnDismissListener mOnDismissListener;

	protected DialogInterface.OnShowListener mOnShowListener;

	public static BaseDialog create(Context context) {

		return new BaseDialog(context);
	}

	public static BaseDialog create(Context context, DialogInterface.OnCancelListener cancelListener) {

		return new BaseDialog(
				context,
				true,
				cancelListener
		);
	}

	private BaseDialog(Context context) {

		init(
				context,
				R.style.BaseDialog,
				true,
				null
			);
	}

	private BaseDialog(Context context, int themeResId) {

		init(
				context,
				themeResId,
				true,
				null
			);
	}

	private BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {

		init(
				context,
				R.style.BaseDialog,
				cancelable,
				cancelListener
			);
	}

	private void init(Context context, int themeResId, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {

		mDialog = new Dialog(
				context,
				themeResId
		);
		mWindow = mDialog.getWindow();
		mDecorView = mWindow.getDecorView();
		mDialog.setCancelable(cancelable);
		mDialog.setOnCancelListener(dialogInterface -> {
			if (cancelListener != null) {
				cancelListener.onCancel(dialogInterface);
			}
			mWindow = null;
			dialogInterface.cancel();
		});
	}

	public BaseDialog setContentView(@LayoutRes int view) {

		mDialog.setContentView(view);
		mWindow.setLayout(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT
						 );
		return this;
	}

	public BaseDialog setContentView(View view) {

		mDialog.setContentView(view);
		mWindow.setLayout(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT
						 );
		return this;
	}

	public BaseDialog setCancelable(boolean cancelable) {

		mDialog.setCancelable(cancelable);
		return this;
	}

	public BaseDialog setCanceledOnTouchOutside(boolean onTouchOutside) {

		mDialog.setCanceledOnTouchOutside(onTouchOutside);
		return this;
	}

	public BaseDialog setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {

		mCancelListener = cancelListener;
		mDialog.setOnCancelListener(dialogInterface -> {
			if (mCancelListener != null) {
				mCancelListener.onCancel(dialogInterface);
			}
			dialogInterface.cancel();
		});
		return this;
	}

	public BaseDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {

		mOnDismissListener = onDismissListener;
		mDialog.setOnDismissListener(dialogInterface -> {
			if (mOnDismissListener != null) {
				mOnDismissListener.onDismiss(dialogInterface);
			}
			if (mCancelListener != null) {
				mCancelListener.onCancel(dialogInterface);
			}
			dialogInterface.cancel();
			dialogInterface.dismiss();
		});
		return this;
	}

	public BaseDialog setOnShowListener(DialogInterface.OnShowListener onShowListener) {

		mOnShowListener = onShowListener;
		mDialog.setOnShowListener(dialogInterface -> {
			if (mOnShowListener != null) {
				mOnShowListener.onShow(dialogInterface);
			}
		});
		return this;
	}

	public BaseDialog setGravity(int gravity) {

		mGravity = gravity;
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.gravity = gravity;
		mWindow.setAttributes(lp);
		return this;
	}

	public BaseDialog setWidth(int width) {

		mWidth = AutoUtils.getPercentWidthSize(width);
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width = mWidth;
		mWindow.setAttributes(lp);
		return this;
	}

	public BaseDialog setHeight(int height) {

		mHeight = AutoUtils.getPercentHeightSize(height);
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.height = height;
		mWindow.setAttributes(lp);
		return this;
	}

	public BaseDialog setPadding(int left, int top, int right, int bottom) {

		mPaddingLeft = AutoUtils.getPercentWidthSize(left);
		mPaddingTop = AutoUtils.getPercentHeightSize(top);
		mPaddingRight = AutoUtils.getPercentWidthSize(right);
		mPaddingBottom = AutoUtils.getPercentHeightSize(bottom);
		mDecorView.setPadding(
				mPaddingLeft,
				mPaddingTop,
				mPaddingRight,
				mPaddingBottom
							 );
		return this;
	}

	public BaseDialog show() {

		mDialog.show();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if (mGravity != -100) {
			lp.gravity = mGravity;
		}
		if (mWidth != -100) {
			lp.width = mWidth;
		} else {
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		}
		if (mHeight != -100) {
			lp.height = mHeight;
		} else {
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		}
		mWindow.setAttributes(lp);
		mDecorView.setPadding(
				mPaddingLeft,
				mPaddingTop,
				mPaddingRight,
				mPaddingBottom
							 );
		return this;
	}

	public void dismiss() {

		mDialog.dismiss();
		mDecorView = null;
		mWindow = null;
	}
}
