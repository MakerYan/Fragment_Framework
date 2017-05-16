package com.makeryan.lib.photopicker.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.makeryan.lib.R;
import com.makeryan.lib.databinding.FragmentImagePagerBinding;
import com.makeryan.lib.fragment.BaseFragment;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.mvp.presenter.ImagePagerPresenter;

import java.util.ArrayList;

import static com.makeryan.lib.photopicker.PhotoPreview.ARG_CURRENT_ITEM;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_HAS_ANIM;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_PATH;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_HEIGHT;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_LEFT;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_TOP;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_WIDTH;

/**
 * Created by donglua on 15/6/21.
 */
public class ImagePagerFragment
		extends BaseFragment {

	protected ImagePagerPresenter mPresenter;

	protected FragmentImagePagerBinding mBinding;

	public static ImagePagerFragment newInstance(@NonNull Bundle args) {

		ImagePagerFragment fragment = new ImagePagerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static ImagePagerFragment newInstance(ArrayList<String> paths, int currentItem) {


		Bundle args = new Bundle();
		args.putStringArray(
				ARG_PATH,
				paths.toArray(new String[paths.size()])
						   );
		args.putInt(
				ARG_CURRENT_ITEM,
				currentItem
				   );
		args.putBoolean(
				ARG_HAS_ANIM,
				false
					   );

		return newInstance(args);
	}


	public static ImagePagerFragment newInstance(ArrayList<String> paths, int currentItem, int[] screenLocation, int thumbnailWidth, int thumbnailHeight) {

		Bundle args = new Bundle();
		args.putStringArray(
				ARG_PATH,
				paths.toArray(new String[paths.size()])
						   );
		args.putInt(
				ARG_CURRENT_ITEM,
				currentItem
				   );
		args.putBoolean(
				ARG_HAS_ANIM,
				false
					   );
		args.putInt(
				ARG_THUMBNAIL_LEFT,
				screenLocation[0]
				   );
		args.putInt(
				ARG_THUMBNAIL_TOP,
				screenLocation[1]
				   );
		args.putInt(
				ARG_THUMBNAIL_WIDTH,
				thumbnailWidth
				   );
		args.putInt(
				ARG_THUMBNAIL_HEIGHT,
				thumbnailHeight
				   );
		args.putBoolean(
				ARG_HAS_ANIM,
				true
					   );

		return newInstance(args);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(
				view,
				savedInstanceState
						   );

		if (savedInstanceState == null && mPresenter.isHasAnim()) {

			ViewTreeObserver observer = mBinding.VPPhoto.getViewTreeObserver();
			observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

				@Override
				public boolean onPreDraw() {

					mBinding.VPPhoto.getViewTreeObserver()
									.removeOnPreDrawListener(this);

					// Figure out where the thumbnail and full size versions are, relative
					// to the screen and each other
					int[] screenLocation = new int[2];
					mBinding.VPPhoto.getLocationOnScreen(screenLocation);
					mPresenter.setThumbnailLeft(mPresenter.getThumbnailLeft() - screenLocation[0]);
					mPresenter.setThumbnailTop(mPresenter.getThumbnailTop() - screenLocation[1]);

					mPresenter.runEnterAnimation();

					return true;
				}
			});
		}
	}

	/**
	 * @return 初始化并返回当前Presenter
	 */
	@Override
	protected BasePresenter getPresenter() {

		return mPresenter == null ?
				mPresenter = new ImagePagerPresenter(this) :
				mPresenter;
	}

	/**
	 * @return 布局Id
	 */
	@Override
	protected int getLayoutResID() {

		return R.layout.fragment_image_pager;
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
				mBinding = FragmentImagePagerBinding.inflate(
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
