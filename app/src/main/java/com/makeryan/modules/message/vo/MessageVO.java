package com.makeryan.modules.message.vo;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.makeryan.lib.BR;
import com.makeryan.lib.pojo.BaseBean;


/**
 * Created by MakerYan on 2017/5/16 12:02.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message
 */
public class MessageVO
		extends BaseBean {

	public String content1;

	public String content2;

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	@Override
	public String toString() {

		return "MessageVO{" + "content1='" + content1 + '\'' + ", content2='" + content2 + '\'' + "} " + super.toString();
	}

	@Bindable
	public String getContent1() {

		return content1;
	}

	public void setContent1(String content1) {

		this.content1 = content1;
		notifyChange(BR.content1);
	}

	@Bindable
	public String getContent2() {

		return content2;
	}

	public void setContent2(String content2) {

		this.content2 = content2;
		notifyChange(BR.content2);
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
