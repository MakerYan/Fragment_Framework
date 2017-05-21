package com.makeryan.modules;

import com.makeryan.lib.net.Response;

/**
 * Created by MakerYan on 2017/5/19 10:42.
 * Modify by MakerYan on 2017/5/19 10:42.
 * Email : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules
 */
public class MyNdk {

	/**
	 * @return
	 */
	public static native String getStaticMessage();

	/**
	 * @return
	 */
	public native String getMessage();
}
