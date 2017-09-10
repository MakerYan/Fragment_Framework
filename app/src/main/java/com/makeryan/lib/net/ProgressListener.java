package com.makeryan.lib.net;

/**
 * Created by MakerYan on 2017/8/19 00:24.
 * Modify by MakerYan on 2017/8/19 00:24.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.net
 */
public interface ProgressListener {

	//已完成的 总的文件长度 是否完成
	void onProgress(long progress, long total, boolean done);
}
