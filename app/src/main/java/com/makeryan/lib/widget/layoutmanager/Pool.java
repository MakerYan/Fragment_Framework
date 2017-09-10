package com.makeryan.lib.widget.layoutmanager;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by MakerYan on 2017/7/12 22:28.
 * Modify by MakerYan on 2017/7/12 22:28.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.lib.widget.layoutmanager
 */
public class Pool<T> {

	private SparseArrayCompat<T> mPool;

	private New<T> mNewInstance;

	public Pool(New<T> newInstance) {

		mPool = new SparseArrayCompat<>();
		mNewInstance = newInstance;
	}

	public T get(int key) {

		T res = mPool.get(key);
		if (res == null) {
			res = mNewInstance.get();
			mPool.put(
					key,
					res
					 );
		}
		return res;
	}

	public interface New<T> {

		T get();
	}
}