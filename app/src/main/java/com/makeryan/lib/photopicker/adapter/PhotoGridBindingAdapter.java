package com.makeryan.lib.photopicker.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.SimpleViewHolder;
import com.makeryan.lib.BR;
import com.makeryan.lib.R;
import com.makeryan.lib.databinding.PickerItemPhotoBinding;
import com.makeryan.lib.photopicker.entity.Photo;
import com.makeryan.lib.photopicker.event.Selectable;
import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridBindingAdapter
		extends CommonRecyclerViewAdapter<Photo, ViewDataBinding>
		implements Selectable {

	protected final View.OnClickListener mFunctionListener;

	protected ArrayList<String> selectedPhotos = new ArrayList<>();

	private boolean previewEnable = true;

	public PhotoGridBindingAdapter(OnRecyclerViewItemClickListener itemClickListener, View.OnClickListener functionListener) {

		super(itemClickListener);
		mFunctionListener = functionListener;
	}

	/**
	 * @return item layout resource id
	 */
	@Override
	public int getLayoutResID() {

		return R.layout.picker_item_photo;
	}

	@Override
	public int getFunctionLayoutResId() {

		return R.layout.picker_item_camera;
	}

	/**
	 * 绑定数据
	 *
	 * @param position
	 * @param holder
	 * @param binding
	 * @param data
	 */
	@Override
	public void bindData(int position, SimpleViewHolder holder, ViewDataBinding binding, Photo data) {

		data.setChecked(isSelected(data));
		holder.itemView.setTag(data);
		binding.setVariable(
				BR.item,
				data
						   );
		binding.setVariable(
				BR.adapter,
				this
						   );
		binding.setVariable(
				BR.holder,
				holder
						   );
		binding.setVariable(
				BR.position,
				position
						   );
		binding.setVariable(
				BR.listener,
				mItemClickListener
						   );
	}

	@Override
	public void bindFunctionData(int position, SimpleViewHolder holder, ViewDataBinding binding) {

		binding.setVariable(
				BR.listener,
				mFunctionListener
						   );
	}

	/**
	 * Indicates if the item at position where is selected
	 *
	 * @param photo
	 * 		Photo of the item to check
	 *
	 * @return true if the item is selected, false otherwise
	 */
	@Override
	public boolean isSelected(Photo photo) {

		return selectedPhotos.contains(photo.getPath());
	}

	/**
	 * Toggle the selection status of the item at a given position
	 *
	 * @param photo
	 * 		Photo of the item to toggle the selection status for
	 */
	@Override
	public void toggleSelection(Photo photo) {

		if (selectedPhotos.contains(photo.getPath())) {
			selectedPhotos.remove(photo.getPath());
		} else {
			selectedPhotos.add(photo.getPath());
		}
	}


	/**
	 * Toggle the selection status of the item at a given position
	 *
	 * @param path
	 * 		Photo of the item to toggle the selection status for
	 */
	public void toggleSelection(String path) {

		if (selectedPhotos.contains(path)) {
			selectedPhotos.remove(path);
		} else {
			selectedPhotos.add(path);
		}
	}


	/**
	 * Clear the selection status for all items
	 */
	@Override
	public void clearSelection() {

		selectedPhotos.clear();
	}


	/**
	 * Count the selected items
	 *
	 * @return Selected items count
	 */
	@Override
	public int getSelectedItemCount() {

		return selectedPhotos.size();
	}

	public ArrayList<String> getCurrentPhotoPaths() {

		ArrayList<String> currentPhotoPaths = new ArrayList<>(mDataList.size());
		for (Photo photo : mDataList) {
			currentPhotoPaths.add(photo.getPath());
		}
		return currentPhotoPaths;
	}


	public List<String> getSelectedPhotos() {

		return selectedPhotos;
	}

	public boolean isPreviewEnable() {

		return previewEnable;
	}

	public void setPreviewEnable(boolean previewEnable) {

		this.previewEnable = previewEnable;
	}


	@Override
	public void onViewRecycled(SimpleViewHolder holder) {

		ViewDataBinding binding = holder.getBinding();
		if (binding instanceof PickerItemPhotoBinding) {
			Glide.clear(binding.getRoot()
							   .findViewById(R.id.ivPhoto));
		}
		super.onViewRecycled(holder);
	}
}
