package com.makeryan.lib.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import com.makeryan.lib.fragment.fragmentation.SupportFragment;

/**
 * Created by MakerYan on 16/9/20 14:08.
 * Personal e-mail : light.yan@qq.com
 */
public class EventBean<T>
		implements Serializable {

	@SerializedName("tag")
	public int tag;

	@SerializedName("data")
	public T data;

	@SerializedName("targetFragment")
	public SupportFragment targetFragment;

	public EventBean() {

	}

	public EventBean(int tag, SupportFragment targetFragment) {

		this.tag = tag;
		this.targetFragment = targetFragment;
	}

	public EventBean(int tag) {

		this.tag = tag;
	}

	public EventBean(int tag, T data) {

		this.tag = tag;
		this.data = data;
	}


	public int getTag() {

		return tag;
	}

	public void setTag(int tag) {

		this.tag = tag;
	}

	public T getData() {

		return data;
	}

	public void setData(T data) {

		this.data = data;
	}

	public SupportFragment getTargetFragment() {

		return targetFragment;
	}

	public void setTargetFragment(SupportFragment targetFragment) {

		this.targetFragment = targetFragment;
	}

	@Override
	public String toString() {

		return "EventBusBean{" + "tag=" + tag + ", data=" + data + "} " + super.toString();
	}
}
