package com.makeryan.lib.net;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.google.gson.annotations.SerializedName;
import com.makeryan.lib.BR;

import java.io.Serializable;

/**
 * Created by MakerYan on 16/9/13 17:29.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : ChengGua
 * package name : com.chenggua.cg.app.lib.net
 */
public class Response<T>
		extends BaseObservable
		implements Serializable {

	/**
	 * bizCode : 0
	 * code : 0
	 * data : string
	 * msg : string
	 */

	@SerializedName("bizCode")
	public int bizCode;

	@SerializedName("code")
	public int code;

	@SerializedName("data")
	public T data;

	@SerializedName("msg")
	public String msg;

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	@Bindable
	public int getBizCode() {

		return bizCode;
	}

	public void setBizCode(int bizCode) {

		this.bizCode = bizCode;
		notifyChange(BR.bizCode);
	}

	@Bindable
	public int getCode() {

		return code;
	}

	public void setCode(int code) {

		this.code = code;
		notifyChange(BR.code);
	}

	@Bindable
	public T getData() {

		return data;
	}

	public void setData(T data) {

		this.data = data;
		notifyChange(BR.data);
	}

	@Bindable
	public String getMsg() {

		return msg;
	}

	public void setMsg(String msg) {

		this.msg = msg;
		notifyChange(BR.msg);
	}

	private void notifyChange(int propertyId) {

		if (propertyChangeRegistry == null) {
			propertyChangeRegistry = new PropertyChangeRegistry();
		}
		propertyChangeRegistry.notifyChange(
				this,
				propertyId
										   );
	}

	@Override
	public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

		if (propertyChangeRegistry == null) {
			propertyChangeRegistry = new PropertyChangeRegistry();
		}
		propertyChangeRegistry.add(callback);

	}

	@Override
	public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

		if (propertyChangeRegistry != null) {
			propertyChangeRegistry.remove(callback);
		}
	}

	@Override
	public String toString() {

		return "Response{" + "bizCode=" + bizCode + ", code=" + code + ", data=" + data + ", msg='" + msg + '\'' + ", propertyChangeRegistry=" + propertyChangeRegistry + "} " + super.toString();
	}
}
