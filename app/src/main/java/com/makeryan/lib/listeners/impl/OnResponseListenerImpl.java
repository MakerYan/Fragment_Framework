package com.makeryan.lib.listeners.impl;

import com.makeryan.lib.listeners.OnResponseListener;
import com.makeryan.lib.net.Response;
import com.makeryan.lib.net.response.SuperResponse;

import java.io.File;
import java.util.List;


/**
 * Created by MakerYan on 2017/6/18 22:58.
 * Modify by MakerYan on 2017/6/18 22:58.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.lib.listeners.impl
 */
public class OnResponseListenerImpl
		implements OnResponseListener {


	@Override
	public void onStart() {

	}

	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {

	}

	@Override
	public void onNext(Response<SuperResponse> response) {

	}

	@Override
	public void onNext(SuperResponse response) {

	}

	@Override
	public void onNextList(Response<List<SuperResponse>> response) {

	}

	@Override
	public void onNextList(List<SuperResponse> responses) {

	}

	@Override
	public void onListEmpty() {

	}

	@Override
	public void downloadFile(File file, String url) {

	}

	@Override
	public void downloadProgress(long total, long progress, boolean isDone, String url) {

	}

	@Override
	public void downloadComplete() {

	}

	@Override
	public void downloadFileEmpty(String url) {

	}
}
