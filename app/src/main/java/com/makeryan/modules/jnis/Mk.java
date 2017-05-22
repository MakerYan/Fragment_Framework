package com.makeryan.modules.jnis;

/**
 * Created by MakerYan on 2017/5/22 21:06.
 * Modify by MakerYan on 2017/5/22 21:06.
 * Email : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.jnis
 */
public class Mk {

	static {
		System.loadLibrary("Mk");
	}

	/**
	 * @return
	 */
	public static native String getStaticMessage();

	/**
	 * @return
	 */
	public native String getMessage();

}
