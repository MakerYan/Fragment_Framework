package com.makeryan.modules.message.listeners;

/**
 * Created by MakerYan on 2017/5/16 11:56.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message.listeners
 */
public interface MessageListener<T> {

	/**
	 * 启动一个同级Fragment
	 */
	void startSibling();

	/**
	 * 启动一个同级Fragment并实时刷新数据
	 *
	 * @param params
	 */
	void startSibling(T params);

	/**
	 * 显示一个消息推送
	 */
	void showNotification();
}
