package com.makeryan.Entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by MakerYan on 2017/6/14 14:02.
 * Modify by MakerYan on 2017/6/14 14:02.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.Entity
 */
public class TabIconText
		implements CustomTabEntity {

	public String tabTitle;

	public int tabSelectedIcon;

	public int tabUnselectedIcon;

	public TabIconText() {

	}

	public TabIconText(String tabTitle, int tabSelectedIcon, int tabUnselectedIcon) {

		this.tabTitle = tabTitle;
		this.tabSelectedIcon = tabSelectedIcon;
		this.tabUnselectedIcon = tabUnselectedIcon;
	}

	@Override
	public String getTabTitle() {

		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {

		this.tabTitle = tabTitle;
	}

	@Override
	public int getTabSelectedIcon() {

		return tabSelectedIcon;
	}

	public void setTabSelectedIcon(int tabSelectedIcon) {

		this.tabSelectedIcon = tabSelectedIcon;
	}

	@Override
	public int getTabUnselectedIcon() {

		return tabUnselectedIcon;
	}

	public void setTabUnselectedIcon(int tabUnselectedIcon) {

		this.tabUnselectedIcon = tabUnselectedIcon;
	}
}
