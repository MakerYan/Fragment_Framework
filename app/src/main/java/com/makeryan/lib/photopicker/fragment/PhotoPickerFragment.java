package com.makeryan.lib.photopicker.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.mvp.presenter.PhotoPickerPresenter;
import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentPhotoPickerBinding;

import java.util.ArrayList;

import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_SHOW_GIF;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoPickerFragment
		extends BaseFragment {

	//目录弹出框的一次最多显示的目录数目
	public static int COUNT_MAX = 4;

	protected FragmentPhotoPickerBinding mBinding;

	protected PhotoPickerPresenter mPresenter;

	public static PhotoPickerFragment newInstance(boolean showCamera, boolean showGif, boolean previewEnable, int column, int maxCount, ArrayList<String> originalPhotos) {

		Bundle args = new Bundle();
		args.putBoolean(
				EXTRA_SHOW_CAMERA,
				showCamera
					   );
		args.putBoolean(
				EXTRA_SHOW_GIF,
				showGif
					   );
		args.putBoolean(
				EXTRA_PREVIEW_ENABLED,
				previewEnable
					   );
		args.putInt(
				EXTRA_GRID_COLUMN,
				column
				   );
		args.putInt(
				EXTRA_MAX_COUNT,
				maxCount
				   );
		args.putStringArrayList(
				EXTRA_ORIGINAL_PHOTOS,
				originalPhotos
							   );
		return newInstance(args);
	}

	public static PhotoPickerFragment newInstance(@NonNull Bundle args) {

		PhotoPickerFragment fragment = new PhotoPickerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	public BasePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new PhotoPickerPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_photo_picker;
	}

	/**
	 * 初始化DataBinding
	 *
	 * @param inflater
	 * @param parent
	 */
	@Override
	protected ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup parent) {

		return mBinding == null ?
				mBinding = FragmentPhotoPickerBinding.inflate(
						inflater,
						parent,
						false
															 ) :
				mBinding;
	}

	/**
	 * 开始处理
	 */
	@Override
	protected void doAction() {

		mPresenter.init(mBinding);
	}
}