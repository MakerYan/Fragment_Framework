package com.makeryan.lib.photopicker.entity;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.makeryan.lib.pojo.BaseBean;
import com.makeryan.lib.BR;

/**
 * Created by donglua on 15/6/30.
 */
public class Photo
		extends BaseBean {

	private int id;

	private String path;

	private boolean isChecked;

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public Photo(int id, String path) {

		this.id = id;
		this.path = path;
	}

	public Photo() {

	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof Photo)) {
			return false;
		}

		Photo photo = (Photo) o;

		return id == photo.id;
	}

	@Override
	public int hashCode() {

		return id;
	}

	@Bindable
	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		this.path = path;
		notifyChange(BR.path);
	}

	@Bindable
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
		notifyChange(BR.id);
	}

	@Bindable
	public boolean isChecked() {

		return isChecked;
	}

	public void setChecked(boolean checked) {

		isChecked = checked;
		notifyChange(BR.checked);
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
	public String toString() {

		return "Photo{" + "id=" + id + ", path='" + path + '\'' + ", isChecked='" + isChecked + '\'' + ", propertyChangeRegistry=" + propertyChangeRegistry + "} " + super.toString();
	}
}
