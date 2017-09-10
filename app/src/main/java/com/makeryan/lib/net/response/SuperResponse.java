package com.makeryan.lib.net.response;

import android.databinding.PropertyChangeRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MakerYan on 2017/7/17 22:02.
 * Modify by MakerYan on 2017/7/17 22:02.
 * Email : light.yan@qq.com
 * project name : CNPCHuBei
 * package name : com.makeryan.lib.net.response
 */
public class SuperResponse
		extends BaseModel {

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public static SuperResponse objectFromData(String str) {

		return new Gson().fromJson(
				str,
				SuperResponse.class
								  );
	}

	public static List<SuperResponse> arrayResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<SuperResponse>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
	}

	/**
	 * Closes this stream and releases any system resources associated
	 * with it. If the stream is already closed then invoking this
	 * method has no effect.
	 *
	 * @throws IOException
	 * 		if an I/O error occurs
	 */
	@Override
	public void close()
			throws IOException {

		super.close();
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
