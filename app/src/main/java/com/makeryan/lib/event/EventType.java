package com.makeryan.lib.event;

/**
 * Created by Maker on 16/10/20.
 */

public interface EventType {

	/**
	 * 关闭MainActivity
	 */
	int CLOSE_MAIN = 6667;

	/**
	 * SupportFragment
	 */
	int TARGET_FRAGMENT = 6668;

	/**
	 * 编辑页面返回数据到 我 的页面
	 */
	int EDIT_INFORMATION_RETURN_MINE = 6670;

	int REGISTER_STEP3 = 6671;

	/**
	 * 选择行业职业
	 */
	int SELECT_INDUSTRY = 6672;

	/**
	 * 容器信息
	 */
	int CONTAINER_INFORMATION = 6673;

	/**
	 * 容器百科关键字
	 */
	int CONTAINER_WIKI_KEYWORDS = 6674;

	/**
	 * 取消关注容器
	 */
	int EXIT_CONTAINER = 6675;
}
