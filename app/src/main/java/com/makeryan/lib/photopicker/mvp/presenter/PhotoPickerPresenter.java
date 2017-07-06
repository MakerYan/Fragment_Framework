package com.makeryan.lib.photopicker.mvp.presenter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.jcodecraeer.xrecyclerview.SimpleViewHolder;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.fragment.fragmentation.SupportActivity;
import com.makeryan.lib.fragment.fragmentation.SupportFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.adapter.PhotoGridBindingAdapter;
import com.makeryan.lib.photopicker.adapter.PopupDirectoryListAdapter;
import com.makeryan.lib.photopicker.entity.Photo;
import com.makeryan.lib.photopicker.entity.PhotoDirectory;
import com.makeryan.lib.photopicker.fragment.ImagePagerFragment;
import com.makeryan.lib.photopicker.utils.ImageCaptureManager;
import com.makeryan.lib.photopicker.utils.MediaStoreHelper;
import com.makeryan.lib.photopicker.utils.PermissionsConstant;
import com.makeryan.lib.photopicker.utils.PermissionsUtils;
import com.makeryan.lib.util.ImageUtil;
import com.makeryan.lib.util.ToastUtil;
import com.makeryan.lib.util.adapter.CommonRecyclerViewAdapter;
import com.socks.library.KLog;
import com.makeryan.lib.BR;
import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentPhotoPickerBinding;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.speech.RecognizerIntent.EXTRA_ORIGIN;
import static com.makeryan.lib.photopicker.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static com.makeryan.lib.photopicker.PhotoPicker.EXTRA_SHOW_GIF;
import static com.makeryan.lib.photopicker.fragment.PhotoPickerFragment.COUNT_MAX;
import static com.makeryan.lib.photopicker.utils.MediaStoreHelper.INDEX_ALL_PHOTOS;


/**
 * Created by MakerYan on 2017/5/12 10:16.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : kongrongqi_android
 * package name : com.makeryan.lib.photopicker.mvp.presenter
 */

public class PhotoPickerPresenter
		extends BasePresenter
		implements CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener<Photo>,
				   AdapterView.OnItemSelectedListener,
				   AdapterView.OnItemClickListener {

	protected FragmentPhotoPickerBinding mBinding;

	//所有photos的路径
	private List<PhotoDirectory> mDirectories = new ArrayList<>();

	//传入的已选照片
	private ArrayList<String> mOriginalPhotos = new ArrayList<>();

	protected int mColumn;

	protected boolean mShowCamera;

	protected boolean mPreviewEnable;

	protected boolean mShowGif;

	protected int mMaxCount;

	protected PhotoGridBindingAdapter mAdapter;

	protected PopupDirectoryListAdapter mPopupAdapter;

	protected ListPopupWindow mListPopupWindow;

	protected ImageCaptureManager mImageCaptureManager;

	public PhotoPickerPresenter(ISupport iSupport) {

		super(iSupport);
	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {

	}

	public void init(FragmentPhotoPickerBinding binding) {

		mBinding = binding;
		getExtrasData();
		initRecyclerView();
		initPopupWindow();
		SupportActivity activity = getSupportActivity();


		binding.setVariable(
				BR.maxCount,
				mMaxCount
						   );
		binding.setVariable(
				BR.selectCount,
				mOriginalPhotos == null ?
						0 :
						mOriginalPhotos.size()
						   );
		binding.setVariable(
				BR.listener,
				this
						   );
		Bundle mediaStoreArgs = new Bundle();
		mediaStoreArgs.putBoolean(
				EXTRA_SHOW_GIF,
				mShowGif
								 );
		MediaStoreHelper.getPhotoDirs(
				activity,
				mediaStoreArgs,
				(List<PhotoDirectory> dirs) -> {
					mDirectories.clear();
					mDirectories.addAll(dirs);
					mPopupAdapter.setDirectories(dirs);
					mAdapter.setDataList(dirs.get(0)
											 .getPhotos());
					if (mOriginalPhotos != null) {
						for (String selectedPath : mOriginalPhotos) {
							mAdapter.toggleSelection(selectedPath);
						}
					}
					adjustHeight();
				}
									 );
		mImageCaptureManager = new ImageCaptureManager(getActivity());
	}

	/**
	 * 获取上个页面传过来的数据
	 */
	private void getExtrasData() {

		mOriginalPhotos = mExtras.getStringArrayList(EXTRA_ORIGIN);

		mColumn = mExtras.getInt(
				EXTRA_GRID_COLUMN,
				DEFAULT_COLUMN_NUMBER
								);
		mMaxCount = mExtras.getInt(
				EXTRA_MAX_COUNT,
				Integer.MAX_VALUE
								  );
		mShowCamera = mExtras.getBoolean(
				EXTRA_SHOW_CAMERA,
				true
										);
		mPreviewEnable = mExtras.getBoolean(
				EXTRA_PREVIEW_ENABLED,
				true
										   );
		mShowGif = mExtras.getBoolean(EXTRA_SHOW_GIF);
		mOriginalPhotos = mExtras.getStringArrayList(EXTRA_ORIGINAL_PHOTOS);
	}

	private void initRecyclerView() {

		mBinding.RVPhotos.setLayoutManager(new StaggeredGridLayoutManager(
				mColumn,
				OrientationHelper.VERTICAL
		));
		mAdapter = new PhotoGridBindingAdapter(
				this,
				this
		);
		mAdapter.setFcButtonStart(true);
		mAdapter.setHasFcButton(mShowCamera);
		mBinding.RVPhotos.setAdapter(mAdapter);

	}

	private void initPopupWindow() {

		SupportActivity activity = getSupportActivity();

		mPopupAdapter = new PopupDirectoryListAdapter(
				ImageUtil.getRequestManager(),
				mDirectories
		);
		mListPopupWindow = new ListPopupWindow(activity);
		mListPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);
		mListPopupWindow.setAnchorView(mBinding.button);
		mListPopupWindow.setAdapter(mPopupAdapter);
		mListPopupWindow.setModal(true);
		mListPopupWindow.setOnItemSelectedListener(this);
		mListPopupWindow.setDropDownGravity(Gravity.BOTTOM);
		mListPopupWindow.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		KLog.d();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		mListPopupWindow.dismiss();

		PhotoDirectory directory = mDirectories.get(position);

		mBinding.button.setText(directory.getName());

		mAdapter.setDataList(directory.getPhotos());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void adjustHeight() {

		if (mPopupAdapter == null) {
			return;
		}
		int count = mPopupAdapter.getCount();
		count = count < COUNT_MAX ?
				count :
				COUNT_MAX;
		if (mListPopupWindow != null) {
			mListPopupWindow.setHeight(count * getActivity().getResources()
															.getDimensionPixelOffset(R.dimen.__picker_item_directory_height));
		}
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		if (id == R.id.ivCamera) {
			if (!PermissionsUtils.checkCameraPermission(getView().getTopFragment())) {
				return;
			}
			if (!PermissionsUtils.checkWriteStoragePermission(getView().getTopFragment())) {
				return;
			}
			openCamera();
		} else if (id == R.id.tvDone) {
			v.setEnabled(false);
			ArrayList<String> selectedPhotos = (ArrayList<String>) mAdapter.getSelectedPhotos();
			EventBus.getDefault()
					.post(new EventBean<>(
							EventType.PHOTO_PICKER,
							selectedPhotos
					));
			getSupportActivity().finish();
		} else if (id == R.id.button) {
			if (mListPopupWindow == null) {
				return;
			}
			mListPopupWindow.show();
		}
	}

	private void openCamera() {

		try {
			Intent intent = mImageCaptureManager.dispatchTakePictureIntent();
			getView().getSupportActivity()
					 .startActivityForResult(
							 intent,
							 ImageCaptureManager.REQUEST_TAKE_PHOTO
											);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ActivityNotFoundException e) {
			// TODO No Activity Found to handle Intent
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

			if (mImageCaptureManager == null) {
				SupportActivity activity = getSupportActivity();
				mImageCaptureManager = new ImageCaptureManager(activity);
			}

			mImageCaptureManager.galleryAddPic();
			if (mDirectories.size() > 0) {
				String         path      = mImageCaptureManager.getCurrentPhotoPath();
				PhotoDirectory directory = mDirectories.get(INDEX_ALL_PHOTOS);
				directory.getPhotos()
						 .add(
								 INDEX_ALL_PHOTOS,
								 new Photo(
										 path.hashCode(),
										 path
								 )
							 );
				directory.setCoverPath(path);
				mAdapter.notifyDataSetChanged();
				if (mOriginalPhotos != null) {
					for (String selectedPath : mOriginalPhotos) {
						mAdapter.toggleSelection(selectedPath);
					}
				}
			}
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			SupportFragment fragment = getView().getTopFragment();
			switch (requestCode) {
				case PermissionsConstant.REQUEST_CAMERA:
				case PermissionsConstant.REQUEST_EXTERNAL_WRITE:
					if (PermissionsUtils.checkWriteStoragePermission(fragment) && PermissionsUtils.checkCameraPermission(fragment)) {
						openCamera();
					}
					break;
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		mImageCaptureManager.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}


	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {

		mImageCaptureManager.onRestoreInstanceState(savedInstanceState);
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public boolean onBackPressedSupport() {

		getSupportActivity().finish();
		return super.onBackPressedSupport();
	}

	/**
	 * item 点击事件
	 *
	 * @param view
	 * 		点击的哪个View
	 * @param adapter
	 * 		这个Item属于哪个Adapter
	 * @param holder
	 * 		{@link SimpleViewHolder ( View )}
	 * @param position
	 * 		点击的哪个位置
	 * @param data
	 */
	@Override
	public void onItemClick(View view, CommonRecyclerViewAdapter adapter, SimpleViewHolder holder, int position, Photo data) {

		if (adapter instanceof PhotoGridBindingAdapter) {

			PhotoGridBindingAdapter inAdapter = (PhotoGridBindingAdapter) adapter;

			int id = view.getId();
			if (id == R.id.ivPhoto) {
				onPhotoViewClick(
						view,
						holder,
						position,
						inAdapter,
						data
								);
			} else if (id == R.id.ivSelected) {
				onCheckViewClick(
						holder,
						position,
						inAdapter,
						data
								);
			} else if (id == R.id.ivCamera) {

			}
		}
	}

	/**
	 * item 长按点击事件
	 *
	 * @param view
	 * 		点击的哪个View
	 * @param adapter
	 * 		这个Item属于哪个Adapter
	 * @param holder
	 * 		{@link SimpleViewHolder ( View )}
	 * @param position
	 * 		点击的哪个位置
	 * @param data
	 */
	@Override
	public void onItemLongClick(View view, CommonRecyclerViewAdapter adapter, SimpleViewHolder holder, int position, Photo data) {

	}

	private void onCheckViewClick(SimpleViewHolder holder, int position, PhotoGridBindingAdapter inAdapter, Photo photo) {

		int     selectedItemCount = inAdapter.getSelectedItemCount();
		boolean canSelect         = selectedItemCount < mMaxCount;
		if (canSelect || inAdapter.isSelected(photo)) {
			inAdapter.toggleSelection(photo);
			photo.setChecked(!photo.isChecked());
			inAdapter.notifyItemChanged(position);
		} else {
			ToastUtil.showMessage(
					getActivity(),
					"最多只选择" + mMaxCount + "张"
								 );
		}
		mBinding.setVariable(
				BR.selectCount,
				inAdapter.getSelectedItemCount()
							);
		if (mMaxCount == 1 && inAdapter.getSelectedItemCount() != 0) {
			mBinding.tvDone.performClick();
		}
	}

	private void onPhotoViewClick(View view, SimpleViewHolder holder, int position, PhotoGridBindingAdapter inAdapter, Photo photo) {

		boolean previewEnable = inAdapter.isPreviewEnable();
		if (previewEnable) {

			final int index = inAdapter.isHasFcButton() ?
					position - 1 :
					position;

			ArrayList<String> photos = inAdapter.getCurrentPhotoPaths();

			int[] screenLocation = new int[2];
			view.getLocationOnScreen(screenLocation);
			getView().start(ImagePagerFragment.newInstance(
					photos,
					index,
					screenLocation,
					view.getWidth(),
					view.getHeight()
														  ));
		} else {
			onCheckViewClick(
					holder,
					position,
					inAdapter,
					photo
							);
		}
	}
}
