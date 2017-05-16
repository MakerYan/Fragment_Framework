package com.makeryan.lib.photopicker.entity;

import android.databinding.Bindable;
import android.databinding.PropertyChangeRegistry;
import android.text.TextUtils;

import com.makeryan.lib.BR;
import com.makeryan.lib.photopicker.utils.FileUtils;
import com.makeryan.lib.pojo.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donglua on 15/6/28.
 */
public class PhotoDirectory
		extends BaseBean {

	private String id;

	private String coverPath;

	private String name;

	private long dateAdded;

	private List<Photo> photos = new ArrayList<>();

	private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

	@Override
	public String toString() {

		return "PhotoDirectory{" + "id='" + id + '\'' + ", coverPath='" + coverPath + '\'' + ", name='" + name + '\'' + ", dateAdded=" + dateAdded + ", photos=" + photos + ", propertyChangeRegistry=" + propertyChangeRegistry + "} " + super.toString();
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
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof PhotoDirectory)) {
			return false;
		}

		PhotoDirectory directory = (PhotoDirectory) o;

		boolean hasId      = !TextUtils.isEmpty(id);
		boolean otherHasId = !TextUtils.isEmpty(directory.id);

		if (hasId && otherHasId) {
			if (!TextUtils.equals(
					id,
					directory.id
								 )) {
				return false;
			}

			return TextUtils.equals(
					name,
					directory.name
								   );
		}

		return false;
	}

	@Override
	public int hashCode() {

		if (TextUtils.isEmpty(id)) {
			if (TextUtils.isEmpty(name)) {
				return 0;
			}

			return name.hashCode();
		}

		int result = id.hashCode();

		if (TextUtils.isEmpty(name)) {
			return result;
		}

		result = 31 * result + name.hashCode();
		return result;
	}

	@Bindable
	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
		notifyChange(BR.id);
	}

	@Bindable
	public String getCoverPath() {

		return coverPath;
	}

	public void setCoverPath(String coverPath) {

		this.coverPath = coverPath;
		notifyChange(BR.coverPath);
	}

	@Bindable
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
		notifyChange(BR.name);
	}

	@Bindable
	public long getDateAdded() {

		return dateAdded;
	}

	public void setDateAdded(long dateAdded) {

		this.dateAdded = dateAdded;
		notifyChange(BR.dateAdded);
	}

	@Bindable
	public List<Photo> getPhotos() {

		return photos;
	}

	public void setPhotos(List<Photo> photos) {

		if (photos == null) {
			return;
		}
		for (int i = 0, j = 0, num = photos.size(); i < num; i++) {
			Photo p = photos.get(j);
			if (p == null || !FileUtils.fileIsExists(p.getPath())) {
				photos.remove(j);
			} else {
				j++;
			}
		}
		this.photos = photos;
		notifyChange(BR.photos);
	}

	@Bindable
	public List<String> getPhotoPaths() {

		List<String> paths = new ArrayList<>(photos.size());
		for (Photo photo : photos) {
			paths.add(photo.getPath());
		}
		return paths;
	}

	public void addPhoto(int id, String path) {

		if (FileUtils.fileIsExists(path)) {
			photos.add(new Photo(
					id,
					path
			));
		}
		notifyChange(BR.photos);
	}
}
