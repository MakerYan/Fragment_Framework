package com.makeryan.lib.photopicker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.makeryan.lib.photopicker.entity.PhotoDirectory;

import java.util.ArrayList;
import java.util.List;

import com.makeryan.lib.R;

/**
 * Created by donglua on 15/6/28.
 */
public class PopupDirectoryListAdapter
		extends BaseAdapter {


	private List<PhotoDirectory> directories = new ArrayList<>();

	private RequestManager glide;

	public PopupDirectoryListAdapter(RequestManager glide, List<PhotoDirectory> directories) {

		this.directories = directories;
		this.glide = glide;
	}

	public void setDirectories(List<PhotoDirectory> directories) {

		this.directories = directories;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return directories.size();
	}


	@Override
	public PhotoDirectory getItem(int position) {

		return directories.get(position);
	}


	@Override
	public long getItemId(int position) {

		return directories.get(position)
						  .hashCode();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
			convertView = mLayoutInflater.inflate(
					R.layout.__picker_item_directory,
					parent,
					false
												 );
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.bindData(directories.get(position));

		return convertView;
	}

	private class ViewHolder {

		public ImageView ivCover;

		public TextView tvName;

		public TextView tvCount;

		public ViewHolder(View rootView) {

			ivCover = (ImageView) rootView.findViewById(R.id.iv_dir_cover);
			tvName = (TextView) rootView.findViewById(R.id.tv_dir_name);
			tvCount = (TextView) rootView.findViewById(R.id.tv_dir_count);
		}

		public void bindData(PhotoDirectory directory) {

			glide.load(directory.getCoverPath())
				 .dontAnimate()
				 .thumbnail(0.1f)
				 .into(ivCover);
			tvName.setText(directory.getName());
			tvCount.setText(tvCount.getContext()
								   .getString(
										   R.string.__picker_image_count,
										   directory.getPhotos()
													.size()
											 ));
		}
	}

}
