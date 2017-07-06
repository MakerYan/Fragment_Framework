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

	@SerializedName("status")
	public int status;

	@SerializedName("result")
	public T result;

	@SerializedName("message")
	public String message;

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	@Override
	public String toString() {

		return "Response{" + "status=" + status + ", result=" + result + ", message='" + message + '\'' + "} " + super.toString();
	}

	@Bindable
	public int getStatus() {

		return status;
	}

	public void setStatus(int status) {

		this.status = status;
		notifyChange(BR.status);
	}

	@Bindable
	public T getResult() {

		return result;
	}

	public void setResult(T result) {

		this.result = result;
		notifyChange(BR.result);
	}

	@Bindable
	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
		notifyChange(BR.message);
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
}
