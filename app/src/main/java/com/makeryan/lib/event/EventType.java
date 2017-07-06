package com.makeryan.lib.event;

/**
 * Created by Maker on 16/10/20.
 */

public interface EventType {


	/**
	 * 退出登录
	 */
	int LOGINOUT = 6667;

	/**
	 * 与MainFragment同级的Fragment,全类名
	 */
	int TARGET_FRAGMENT_IN_MAIN_BY_NAME = 6668;

	/**
	 * 与MainFragment同级的Fragment
	 */
	int TARGET_FRAGMENT_IN_MAIN = 6669;

	/**
	 *
	 */
	int TARGET_FRAGMENT_IN_MAIN_SINGLE_TASK = 6670;

	/**
	 * 选择照片
	 */
	int PHOTO_PICKER = 6677;

	/**
	 * SupportFragment
	 */
	int TARGET_FRAGMENT = 6668;
}
