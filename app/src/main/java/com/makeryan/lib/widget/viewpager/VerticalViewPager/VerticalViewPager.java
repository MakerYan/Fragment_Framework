package com.makeryan.lib.widget.viewpager.VerticalViewPager;

/**
 * Copyright (C) 2015 Kaelaela
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.makeryan.lib.widget.viewpager.VerticalViewPager.transforms.DefaultTransformer;


public class VerticalViewPager
		extends ViewPager {

	public VerticalViewPager(Context context) {

		this(
				context,
				null
			);
	}

	public VerticalViewPager(Context context, AttributeSet attrs) {

		super(
				context,
				attrs
			 );
		setPageTransformer(
				false,
				new DefaultTransformer()
						  );
	}

	private MotionEvent swapTouchEvent(MotionEvent event) {

		float width = getWidth();
		float height = getHeight();

		float swappedX = (event.getY() / height) * width;
		float swappedY = (event.getX() / width) * height;

		event.setLocation(
				swappedX,
				swappedY
						 );

		return event;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {

		boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
		//If not intercept, touch event should not be swapped.
		swapTouchEvent(event);
		return intercept;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		return super.onTouchEvent(swapTouchEvent(ev));
	}

}
