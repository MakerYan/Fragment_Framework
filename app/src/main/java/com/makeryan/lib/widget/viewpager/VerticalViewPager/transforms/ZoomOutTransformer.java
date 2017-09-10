package com.makeryan.lib.widget.viewpager.VerticalViewPager.transforms;

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

import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutTransformer
		implements ViewPager.PageTransformer {

	private static final float MIN_SCALE = 0.90f;

	@Override
	public void transformPage(View view, float position) {

		int   pageWidth  = view.getWidth();
		int   pageHeight = view.getHeight();
		float alpha      = 0;
		if (0 <= position && position <= 1) {
			alpha = 1 - position;
		} else if (-1 < position && position < 0) {
			float scaleFactor      = Math.max(
					MIN_SCALE,
					1 - Math.abs(position)
											 );
			float verticalMargin   = pageHeight * (1 - scaleFactor) / 2;
			float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
			if (position < 0) {
				view.setTranslationX(horizontalMargin - verticalMargin / 2);
			} else {
				view.setTranslationX(-horizontalMargin + verticalMargin / 2);
			}

			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);

			alpha = position + 1;
		}

		view.setAlpha(alpha);
		view.setTranslationX(view.getWidth() * -position);
		float yPosition = position * view.getHeight();
		view.setTranslationY(yPosition);
	}

}
