package com.makeryan.lib.util.adapter;

import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用TabLayout PagerAdapter
 * Created by MakerYan on 2017/4/20 14:39.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.kongrongqi.kongrongqi.pub.adapters
 */

public class NoTabPagerAdapter<V>
		extends PagerAdapter {

	private List<V> mViewList = new ArrayList<V>();

	public NoTabPagerAdapter() {

	}

	public NoTabPagerAdapter(List<V> viewList) {

		mViewList = viewList;
	}

	public List<V> getViewList() {

		return mViewList;
	}

	public void addView(V... viewArr) {

		for (V v : viewArr) {
			this.mViewList.add(v);
		}
		notifyDataSetChanged();
	}

	public void setViewList(List<V> mViewList) {

		this.mViewList = mViewList;
		notifyDataSetChanged();
	}

	//销毁一个页卡(即ViewPager的一个item)
	public void destroyItem(ViewGroup container, int position, Object object) {

		V view = getItem(position);
		if (view instanceof ViewDataBinding) {
			((ViewDataBinding) view).getRoot();
			container.removeView(((ViewDataBinding) view).getRoot());
		} else {
			container.removeView((View) view);
		}
	}


	//对应页卡添加上数据
	public Object instantiateItem(ViewGroup container, int position) {

		V child = getItem(position);
		if (child instanceof ViewDataBinding) {
			View root = ((ViewDataBinding) child).getRoot();
			container.addView(root);//千万别忘记添加到container
			return root;
		}
		container.addView((View) child);//千万别忘记添加到container
		return child;
	}

	/**
	 * 数据源的数目
	 * Return the number of views available.
	 */
	@Override
	public int getCount() {

		int count = 0;
		if (mViewList == null) {
			return count;
		}
		count = mViewList.size();
		return count;
	}

	/**
	 * Determines whether a page View is associated with a specific key object
	 * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
	 * required for a PagerAdapter to function properly.
	 *
	 * @param view
	 * 		Page View to check for association with <code>object</code>
	 * @param object
	 * 		Object to check for association with <code>view</code>
	 *
	 * @return true if <code>view</code> is associated with the key object <code>object</code>
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {

		return view == object;
	}

	@Override
	public int getItemPosition(Object object) {

		return POSITION_NONE;
	}

	public V getItem(int position) {

		return mViewList.get(position);
	}
}
