package com.makeryan.lib.pojo;

import com.makeryan.lib.widget.tablayout.listener.ITabBody;

/**
 * Created by MakerYan on 2017/5/16 10:28.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.lib.pojo
 */
public class TabBody
		implements ITabBody {

	public String mTabName;

	public int mSelectedIcon;

	public int mUnSelectIcon;

	public TabBody(String tabName, int selectedIcon, int unSelectIcon) {

		mTabName = tabName;
		mSelectedIcon = selectedIcon;
		mUnSelectIcon = unSelectIcon;
	}

	@Override
	public String getTabTitle() {

		return mTabName;
	}

	@Override
	public int getTabSelectedIcon() {

		return mSelectedIcon;
	}

	@Override
	public int getTabUnselectedIcon() {

		return mUnSelectIcon;
	}
}
