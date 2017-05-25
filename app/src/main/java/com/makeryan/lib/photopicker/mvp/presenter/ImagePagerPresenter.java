package com.makeryan.lib.photopicker.mvp.presenter;

import android.graphics.ColorMatrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.makeryan.lib.databinding.FragmentImagePagerBinding;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.lib.photopicker.adapter.PhotoPagerAdapter;
import com.makeryan.lib.util.ImageUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

import com.makeryan.lib.fragment.fragmentation.ISupport;

import static com.makeryan.lib.photopicker.PhotoPreview.ARG_CURRENT_ITEM;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_HAS_ANIM;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_PATH;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_HEIGHT;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_LEFT;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_TOP;
import static com.makeryan.lib.photopicker.PhotoPreview.ARG_THUMBNAIL_WIDTH;

/**
 * Created by MakerYan on 2017/5/12 14:13.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : kongrongqi_android
 * package name : com.makeryan.lib.photopicker.mvp.presenter
 */

public class ImagePagerPresenter
		extends BasePresenter {

	private ArrayList<String> paths = new ArrayList<>();

	public final static long ANIM_DURATION = 200L;

	private int thumbnailTop = 0;

	private int thumbnailLeft = 0;

	private int thumbnailWidth = 0;

	private int thumbnailHeight = 0;

	private boolean hasAnim = false;

	private final ColorMatrix colorizerMatrix = new ColorMatrix();

	private int currentItem = 0;

	protected FragmentImagePagerBinding mBinding;

	protected PhotoPagerAdapter mPhotoPagerAdapter;

	public ImagePagerPresenter(ISupport iSupport) {

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

	@Override
	public void processonCreateViewSavedInstanceState(Bundle savedInstanceState) {


	}

	public void init(FragmentImagePagerBinding binding) {

		mBinding = binding;

		String[] pathArr = mExtras.getStringArray(ARG_PATH);
		if (pathArr != null) {
			for (String s : pathArr) {
				paths.add(s);
			}
		}

		hasAnim = mExtras.getBoolean(ARG_HAS_ANIM);
		currentItem = mExtras.getInt(ARG_CURRENT_ITEM);
		thumbnailTop = mExtras.getInt(ARG_THUMBNAIL_TOP);
		thumbnailLeft = mExtras.getInt(ARG_THUMBNAIL_LEFT);
		thumbnailWidth = mExtras.getInt(ARG_THUMBNAIL_WIDTH);
		thumbnailHeight = mExtras.getInt(ARG_THUMBNAIL_HEIGHT);

		mPhotoPagerAdapter = new PhotoPagerAdapter(
				ImageUtil.getRequestManager(),
				paths
		);
		binding.VPPhoto.setAdapter(mPhotoPagerAdapter);
		binding.VPPhoto.setCurrentItem(currentItem);
		binding.VPPhoto.setOffscreenPageLimit(5);
		binding.VPPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

				hasAnim = currentItem == position;
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		binding.VPPhoto.setCurrentItem(currentItem);
	}

	@Override
	public boolean onBackPressedSupport() {

		runExitAnimation(() -> {
			ISupport view = getView();
			if (view != null) {
				view.pop();
			}
		});
		return super.onBackPressedSupport();
	}

	/**
	 * The enter animation scales the picture in from its previous thumbnail
	 * size/location, colorizing it in parallel. In parallel, the background of the
	 * activity is fading in. When the pictue is in place, the text description
	 * drops down.
	 */
	public void runEnterAnimation() {

		final long duration = ANIM_DURATION;

		// Set starting values for properties we're going to animate. These
		// values scale and position the full size version down to the thumbnail
		// size/location, from which we'll animate it back up
		ViewHelper.setPivotX(
				mBinding.VPPhoto,
				0
							);
		ViewHelper.setPivotY(
				mBinding.VPPhoto,
				0
							);
		ViewHelper.setScaleX(
				mBinding.VPPhoto,
				(float) thumbnailWidth / mBinding.VPPhoto.getWidth()
							);
		ViewHelper.setScaleY(
				mBinding.VPPhoto,
				(float) thumbnailHeight / mBinding.VPPhoto.getHeight()
							);
		ViewHelper.setTranslationX(
				mBinding.VPPhoto,
				thumbnailLeft
								  );
		ViewHelper.setTranslationY(
				mBinding.VPPhoto,
				thumbnailTop
								  );

		// Animate scale and translation to go from thumbnail to full size
		ViewPropertyAnimator.animate(mBinding.VPPhoto)
							.setDuration(duration)
							.scaleX(1)
							.scaleY(1)
							.translationX(0)
							.translationY(0)
							.setInterpolator(new DecelerateInterpolator());

		// Fade in the black background
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(
				mBinding.VPPhoto.getBackground(),
				"alpha",
				0,
				255
													);
		bgAnim.setDuration(duration);
		bgAnim.start();

		// Animate a color filter to take the image from grayscale to full color.
		// This happens in parallel with the image scaling and moving into place.
		ObjectAnimator colorizer = ObjectAnimator.ofFloat(
				getView().getTopFragment(),
				"saturation",
				0,
				1
														 );
		colorizer.setDuration(duration);
		colorizer.start();

	}

	/**
	 * The exit animation is basically a reverse of the enter animation, except that if
	 * the orientation has changed we simply scale the picture back into the center of
	 * the screen.
	 *
	 * @param endAction
	 * 		This action gets run after the animation completes (this is
	 * 		when we actually switch activities)
	 */
	public void runExitAnimation(final Runnable endAction) {

		if (!hasAnim) {
			endAction.run();
		} else {
			final long duration = ANIM_DURATION;

			// Animate image back to thumbnail size/location
			ViewPropertyAnimator.animate(mBinding.VPPhoto)
								.setDuration(duration)
								.setInterpolator(new AccelerateInterpolator())
								.scaleX((float) thumbnailWidth / mBinding.VPPhoto.getWidth())
								.scaleY((float) thumbnailHeight / mBinding.VPPhoto.getHeight())
								.translationX(thumbnailLeft)
								.translationY(thumbnailTop)
								.setListener(new Animator.AnimatorListener() {

									@Override
									public void onAnimationStart(Animator animation) {

									}

									@Override
									public void onAnimationEnd(Animator animation) {

										endAction.run();
									}

									@Override
									public void onAnimationCancel(Animator animation) {

									}

									@Override
									public void onAnimationRepeat(Animator animation) {

									}
								});

			// Fade out background
			ObjectAnimator bgAnim = ObjectAnimator.ofInt(
					mBinding.VPPhoto.getBackground(),
					"alpha",
					0
														);
			bgAnim.setDuration(duration);
			bgAnim.start();

			// Animate a color filter to take the image back to grayscale,
			// in parallel with the image scaling and moving into place.
			ObjectAnimator colorizer = ObjectAnimator.ofFloat(
					getView().getTopFragment(),
					"saturation",
					1,
					0
															 );
			colorizer.setDuration(duration);
			colorizer.start();
		}

	}

	public int getThumbnailTop() {

		return thumbnailTop;
	}

	public int getThumbnailLeft() {

		return thumbnailLeft;
	}

	public int getThumbnailWidth() {

		return thumbnailWidth;
	}

	public int getThumbnailHeight() {

		return thumbnailHeight;
	}

	public boolean isHasAnim() {

		return hasAnim;
	}

	public int getCurrentItem() {

		return currentItem;
	}

	public void setThumbnailTop(int thumbnailTop) {

		this.thumbnailTop = thumbnailTop;
	}

	public void setThumbnailLeft(int thumbnailLeft) {

		this.thumbnailLeft = thumbnailLeft;
	}

	public void setThumbnailWidth(int thumbnailWidth) {

		this.thumbnailWidth = thumbnailWidth;
	}

	public void setThumbnailHeight(int thumbnailHeight) {

		this.thumbnailHeight = thumbnailHeight;
	}

	public void setHasAnim(boolean hasAnim) {

		this.hasAnim = hasAnim;
	}

	public void setCurrentItem(int currentItem) {

		this.currentItem = currentItem;
	}
}
