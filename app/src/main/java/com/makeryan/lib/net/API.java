package com.makeryan.lib.net;

/**
 * Created by MakerYan on 2017/5/15 下午8:49.
 *
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/15 下午8:49
 */
public interface API {

	String BASE_URL = "http://120.76.41.154:9988/api/";

	String BASE_NEWURL = "http://120.76.41.154:9988/newApi/";

	String XY_PUBLISHID = "XUNYU-PUBLISHID";

	/**
	 * 精选
	 */
	String JX_LIST = "jx_list.asp";

	/**
	 * 主页推送消息
	 */
	String PERSONALMSG = "push_msg.asp";

	/**
	 * 登录接口
	 */
	String USER_LOGIN = "userLogin.asp";

	/**
	 * 注册接口
	 */
	String AUTOREG = "auto_reg.asp";

	/**
	 * 搜爱列表
	 */
	String HOT_LIST = "hot_list.asp";

	/**
	 * 请求关注某人
	 */
	String USER_LIKE = "userLike.asp";

	/**
	 * 浏览记录
	 */
	String HISTORY_USER = "historyUser.asp";

	/**
	 * 礼物列表
	 */
	String PRESENT_LIST = "present_list.asp";

	/**
	 * 获取用户信息
	 */
	String USER_INFO = "user_info.asp";

	/**
	 * 设置爱情真心话
	 */
	String SET_ZXH = "zxh.asp";

	/**
	 * 获取设置爱情真心话
	 */
	String GET_ZXH = "get_zxh.asp";

	/**
	 * 提交征友条件
	 */
	String SAVE_CONDITION = "save_condition.asp";

	/**
	 * 修改保存个人信息
	 */
	String SAVE_BASEINFO = "save_baseinfo.asp";

	/**
	 * 打招呼
	 * fid
	 */
	String SAY_HI = "sayHi.asp";

	/**
	 * 上传
	 */
	String UPLOAD_FILE = "upload.asp";

	/**
	 * 这个接口是，聊天界面，那个循环滚动播放的 谁赠送谁什么礼物
	 * 不用额外传参数，公共参数即可
	 */
	String PRESENT_MESSAGE = "present_message.asp";

	/**
	 * 这个接口是 取到征友条件填 过的值。因为进入界面，需要帮用户初始化以前填过的内容
	 * 不用额外传参数，公共参数即可
	 */
	String GET_CONDITION = "get_condition.asp";


	/**
	 * 私信->谁关注我,我的关注
	 */
	String PERSONAL_FOLLOW = "personal_follow.asp";
}