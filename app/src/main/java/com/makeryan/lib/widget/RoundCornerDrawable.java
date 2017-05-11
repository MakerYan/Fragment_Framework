package com.makeryan.lib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

/**
 * 圆角图片
 * Created by Maker on 16/12/2.
 */
public class RoundCornerDrawable
		extends Drawable {

	private Paint mPaint;

	private Bitmap mBitmap;

	private RectF rectF;

	private int mCorners;

	public RoundCornerDrawable(Context context, Bitmap bitmap, int corner) {

		mBitmap = bitmap;
		mCorners = corner;
		BitmapShader bitmapShader = new BitmapShader(
				bitmap,
				TileMode.CLAMP,
				TileMode.CLAMP
		);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(bitmapShader);
	}

	@Override
	public void setBounds(int left, int top, int right, int bottom) {

		super.setBounds(
				left,
				top,
				right,
				bottom
					   );
		rectF = new RectF(
				left,
				top,
				right,
				bottom
		);
	}

	@Override
	public void draw(Canvas canvas) {

		canvas.drawRoundRect(
				rectF,
				mCorners,
				mCorners,
				mPaint
							);
	}

	@Override
	public int getIntrinsicWidth() {

		return mBitmap.getWidth();
	}

	@Override
	public int getIntrinsicHeight() {

		return mBitmap.getHeight();
	}

	@Override
	public void setAlpha(int alpha) {

		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {

		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {

		return PixelFormat.TRANSLUCENT;
	}

}
