package com.makeryan.modules.database;

import android.databinding.PropertyChangeRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeryan.lib.database.AppDatabase;
import com.makeryan.lib.pojo.BaseBean;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MakerYan on 2017/6/3 18:27.
 * Modify by MakerYan on 2017/6/3 18:27.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.database
 */
@Table(database = AppDatabase.class)
public class UserInfo
		extends BaseBean {

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public static UserInfo objectFromData(String str) {

		return new Gson().fromJson(
				str,
				UserInfo.class
								  );
	}

	public static List<UserInfo> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<UserInfo>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
	}

	@PrimaryKey
	@Column
	public long singleId = 666;

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
