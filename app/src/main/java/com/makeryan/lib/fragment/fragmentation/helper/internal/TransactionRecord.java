package com.makeryan.lib.fragment.fragmentation.helper.internal;

import android.view.View;

import java.util.ArrayList;

/**
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/16 下午9:50
 */
public final class TransactionRecord {

	public String tag;

	public Integer requestCode;

	public Integer launchMode;

	public Boolean withPop;

	public ArrayList<SharedElement> sharedElementList;

	public static class SharedElement {

		public View sharedElement;

		public String sharedName;

		public SharedElement(View sharedElement, String sharedName) {

			this.sharedElement = sharedElement;
			this.sharedName = sharedName;
		}
	}
}
