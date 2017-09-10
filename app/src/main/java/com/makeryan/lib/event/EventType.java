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

	/**
	 * 保存录制视频
	 */
	int SAVE_VIDEO = 6671;

	/**
	 * RecyclerView item 移除
	 */
	int REMOVE_ITEM = 1991;

	/**
	 * 故障详情签到, 通知待办任务刷新状态
	 */
	int EXECUTE_STATUS = 7701;

	/**
	 * eventbus notify refresh to do task
	 */
	int REFRESH_TO_DO_TASKS = 7702;

	/**
	 * 首页巡检
	 */
	int ALL_INSPECTION = 7703;

	/**
	 * 首页报修
	 */
	int ALL_REPAIR = 7704;

	/**
	 * 信鸽_振动
	 */
	int XG_CONFIG = 7705;
}
