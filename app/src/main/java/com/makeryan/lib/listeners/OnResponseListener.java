package com.makeryan.lib.listeners;

import com.makeryan.lib.net.Response;
import com.makeryan.modules.vo.response.SuperResponse;

import java.util.List;

/**
 * Created by MakerYan on 2017/6/18 22:55.
 * Modify by MakerYan on 2017/6/18 22:55.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.lib.listeners
 */
public interface OnResponseListener {

	void onStart();

	void onCompleted();

	void onError(Throwable e);

	void onNext(Response<SuperResponse> response);

	void onNext(SuperResponse response);

	void onNextList(Response<List<SuperResponse>> response);

	void onNextList(List<SuperResponse> response);

	void onListEmpty();
}
