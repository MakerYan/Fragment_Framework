package com.jcodecraeer.xrecyclerview;

import android.support.design.widget.AppBarLayout;

/**
 * Created by jianghejie on 16/6/19.
 */

public abstract class AppBarStateChangeListener
		implements AppBarLayout.OnOffsetChangedListener {

	private State mCurrentState = State.IDLE;

	@Override
	public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {

		if (i == 0) {
			if (mCurrentState != State.EXPANDED) {
				onStateChanged(
						appBarLayout,
						State.EXPANDED,
						i
							  );
			}
			mCurrentState = State.EXPANDED;
		} else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
			if (mCurrentState != State.COLLAPSED) {
				onStateChanged(
						appBarLayout,
						State.COLLAPSED,
						i
							  );
			}
			mCurrentState = State.COLLAPSED;
		} else {
			if (mCurrentState != State.IDLE) {
				onStateChanged(
						appBarLayout,
						State.IDLE,
						i
							  );
			}
			mCurrentState = State.IDLE;
		}
	}

	public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset);

	public enum State {
		EXPANDED,
		COLLAPSED,
		IDLE
	}
}

