package com.makeryan.modules.vo;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeryan.lib.database.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.makeryan.lib.BR;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MakerYan on 2017/7/1 11:17.
 * Modify by MakerYan on 2017/7/1 11:17.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.vo
 */
@Table(database = AppDatabase.class)
public class LocalEaseUser
		extends BaseModel {

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	public static LocalEaseUser objectFromData(String str) {

		return new Gson().fromJson(
				str,
				LocalEaseUser.class
								  );
	}

	public static List<LocalEaseUser> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<LocalEaseUser>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
	}

	/**
	 * initial letter for nickname
	 */
	@Column
	public String initialLetter = "";

	/**
	 * avatar of the user
	 */
	@Column
	public String avatar = "";

	/**
	 * \~chinese
	 * 此用户的唯一标示名,即用户的环信id
	 * <p>
	 * \~english
	 * the user name assigned from app, which should be unique in the application
	 */
	@PrimaryKey
	@Column
	public String username = "";

	@Column
	public String nick = "";

	@Column
	public String nickname = "";

	public LocalEaseUser() {

	}

	public LocalEaseUser(String username) {

		this.username = username;
	}

	@Override
	public String toString() {

		return "LocalEaseUser{" + "initialLetter='" + initialLetter + '\'' + ", avatar='" + avatar + '\'' + ", username='" + username + '\'' + ", nick='" + nick + '\'' + ", nickname='" + nickname + '\'' + "} " + super.toString();
	}

	@Bindable
	public String getInitialLetter() {

		return initialLetter;
	}

	public void setInitialLetter(String initialLetter) {

		this.initialLetter = initialLetter;
		notifyChange(BR.initialLetter);
	}

	@Bindable
	public String getAvatar() {

		return avatar;
	}

	public void setAvatar(String avatar) {

		this.avatar = avatar;
		notifyChange(BR.avatar);
	}

	@Bindable
	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
		notifyChange(BR.username);
	}

	@Bindable
	public String getNick() {

		return nick;
	}

	public void setNick(String nick) {

		this.nick = nick;
		this.nickname = nick;
		notifyChange(BR.nick);
		notifyChange(BR.nickname);
	}

	@Bindable
	public String getNickname() {

		return nickname;
	}

	public void setNickname(String nickname) {

		this.nickname = nickname;
		this.nick = nickname;
		notifyChange(BR.nick);
		notifyChange(BR.nickname);
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
