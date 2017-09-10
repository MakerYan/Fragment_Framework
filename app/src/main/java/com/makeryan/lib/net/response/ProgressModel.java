package com.makeryan.lib.net.response;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.makeryan.lib.BR;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by MakerYan on 2017/8/19 00:27.
 * Modify by MakerYan on 2017/8/19 00:27.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.net.response
 */
public class ProgressModel
		extends BaseModel {

	public long progress;

	public long total;

	public boolean isDone;

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public ProgressModel() {

	}

	public ProgressModel(long progress, long total, boolean isDone) {

		this.progress = progress;
		this.total = total;
		this.isDone = isDone;
	}

	@Bindable
	public long getProgress() {

		return progress;
	}

	public void setProgress(long progress) {

		this.progress = progress;
		notifyChange(BR.progress);
	}

	@Bindable
	public long getTotal() {

		return total;
	}

	public void setTotal(long total) {

		this.total = total;
		notifyChange(BR.total);
	}

	@Bindable
	public boolean isDone() {

		return isDone;
	}

	public void setDone(boolean done) {

		isDone = done;
		notifyChange(BR.done);
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

		return "ProgressModel{" + "progress=" + progress + ", total=" + total + ", isDone=" + isDone + "} " + super.toString();
	}
}
