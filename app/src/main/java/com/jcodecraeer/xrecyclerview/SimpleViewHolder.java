package com.jcodecraeer.xrecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;


/**
 * Created by MakerYan on 2017/4/22 16:00.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.jcodecraeer.xrecyclerview
 */

public class SimpleViewHolder<T, D extends ViewDataBinding>
		extends RecyclerView.ViewHolder {

	protected D binding;

	protected T mDataObj;

	public SimpleViewHolder(View itemView) {

		super(itemView);
		AutoUtils.autoSize(itemView);
	}

	public SimpleViewHolder(View itemView, D binding) {

		super(itemView);
		AutoUtils.autoSize(itemView);
		this.binding = binding;
	}

	public T getDataObj() {

		return mDataObj;
	}

	public void setDataObj(T dataObj) {

		mDataObj = dataObj;
	}

	public ViewDataBinding getBinding() {

		return this.binding;
	}

	public void setBinding(D binding) {

		this.binding = binding;
	}
}
