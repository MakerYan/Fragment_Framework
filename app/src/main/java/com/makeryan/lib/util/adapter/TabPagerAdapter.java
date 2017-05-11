package com.makeryan.lib.util.adapter;

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

public class TabPagerAdapter<V extends View, D extends CharSequence>
		extends PagerAdapter {

	protected List<D> mTabNameList = new ArrayList<D>();

	private List<V> mViewList = new ArrayList<V>();

	public TabPagerAdapter() {

	}

	public TabPagerAdapter(List<D> tabNameList, List<V> viewList) {

		mTabNameList = tabNameList;
		mViewList = viewList;
	}

	public List<D> getTabNameList() {

		return mTabNameList;
	}

	public void setTabNameList(List<D> tabNameList) {

		mTabNameList = tabNameList;
		notifyDataSetChanged();
	}

	public void setTabNameList(D... d) {

		ArrayList<D> ds = new ArrayList<>();
		for (int i = 0; i < d.length; i++) {
			ds.add(d[i]);
		}
		setTabNameList(ds);
	}

	public List<V> getViewList() {

		return mViewList;
	}

	public void setViewList(List<V> mViewList) {

		this.mViewList = mViewList;
		notifyDataSetChanged();
	}

	//销毁一个页卡(即ViewPager的一个item)
	public void destroyItem(ViewGroup container, int position, Object object) {

		V view = mViewList.get(position);
		container.removeView(view);
	}


	//对应页卡添加上数据
	public Object instantiateItem(ViewGroup container, int position) {

		V child = mViewList.get(position);
		container.addView(child);//千万别忘记添加到container
		return child;
	}

	/**
	 * 数据源的数目
	 * Return the number of views available.
	 */
	@Override
	public int getCount() {

		int count = 0;
		if (mTabNameList == null || mViewList == null || mTabNameList.size() == 0 || mViewList.size() == 0) {
			count = 0;
		}
		if (mTabNameList.size() == mViewList.size()) {
			count = mTabNameList.size();
		} else if (mTabNameList.size() > mViewList.size()) {
			count = mViewList.size();
		} else {
			count = mTabNameList.size();
		}
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
	public CharSequence getPageTitle(int position) {

		return mTabNameList.get(position);
	}

	@Override
	public int getItemPosition(Object object) {

		return POSITION_NONE;
	}
}
