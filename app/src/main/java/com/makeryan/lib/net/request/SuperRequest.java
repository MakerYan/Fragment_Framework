package com.makeryan.lib.net.request;

import android.databinding.PropertyChangeRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MakerYan on 2017/7/17 22:02.
 * Modify by MakerYan on 2017/7/17 22:02.
 * Email : light.yan@qq.com
 * project name : CNPCHuBei
 * package name : com.makeryan.lib.net.request
 */
public class SuperRequest
		extends BaseModel
		implements Serializable {

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public static SuperRequest objectFromData(String str) {

		return new Gson().fromJson(
				str,
				SuperRequest.class
								  );
	}

	public static List<SuperRequest> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<SuperRequest>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
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
