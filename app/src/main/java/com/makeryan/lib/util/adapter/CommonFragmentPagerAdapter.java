package com.makeryan.lib.util.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.makeryan.lib.fragment.fragmentation.SupportFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MakerYan on 2017/8/17 23:43.
 * Modify by MakerYan on 2017/8/17 23:43.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.util.adapter
 */
public class CommonFragmentPagerAdapter<V extends SupportFragment, D extends CharSequence>
		extends FragmentPagerAdapter {


	protected List<D> mTabNameList = new ArrayList<D>();

	protected List<V> mViewList = new ArrayList<V>();

	public CommonFragmentPagerAdapter(FragmentManager fm) {

		super(fm);
	}

	public CommonFragmentPagerAdapter(FragmentManager fm, List<D> tabNameList, List<V> viewList) {

		super(fm);
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

	/**
	 * Return the Fragment associated with a specified position.
	 *
	 * @param position
	 */
	@Override
	public Fragment getItem(int position) {

		return mViewList.get(position);
	}

	/**
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

	@Override
	public CharSequence getPageTitle(int position) {

		return mTabNameList.get(position);
	}

	@Override
	public int getItemPosition(Object object) {

		return POSITION_NONE;
	}
}
