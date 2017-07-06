package com.makeryan.lib.photopicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.makeryan.lib.activity.ContainerActivity;
import com.makeryan.lib.photopicker.fragment.ImagePagerFragment;

import java.util.ArrayList;

import com.makeryan.lib.fragment.fragmentation.ISupport;

/**
 * Created by Donglua on 16/6/25.
 * Builder class to ease Intent setup.
 */
public class PhotoPreview {

	public final static int REQUEST_CODE = 666;

	//	public final static String EXTRA_CURRENT_ITEM = "current_item";
	//
	//	public final static String EXTRA_PHOTOS = "photos";
	//
	//	public final static String EXTRA_SHOW_DELETE = "show_delete";

	public final static String ARG_PATH = "PATHS";

	public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";

	public final static String ARG_THUMBNAIL_TOP = "THUMBNAIL_TOP";

	public final static String ARG_THUMBNAIL_LEFT = "THUMBNAIL_LEFT";

	public final static String ARG_THUMBNAIL_WIDTH = "THUMBNAIL_WIDTH";

	public final static String ARG_THUMBNAIL_HEIGHT = "THUMBNAIL_HEIGHT";

	public final static String ARG_HAS_ANIM = "HAS_ANIM";

	public final static String ARG_SHOW_DELETE = "show_delete";

	public static PhotoPreviewBuilder builder() {

		return new PhotoPreviewBuilder();
	}


	public static class PhotoPreviewBuilder {

		private Bundle mPreviewOptionsBundle;

		private Intent mPreviewIntent;

		public PhotoPreviewBuilder() {

			mPreviewOptionsBundle = new Bundle();
			mPreviewIntent = new Intent();
		}

		/**
		 * Send the Intent from an Activity with a custom request code
		 *
		 * @param iSupport
		 * @param requestCode
		 * 		requestCode for result
		 */
		public void start(@NonNull ISupport iSupport, int requestCode) {
			// activity.startActivityForResult(getIntent(activity), requestCode);
			/*iSupport.startForResult(
					ImagePagerFragment.newInstance(getBundle()),
					requestCode
								   );*/
			Bundle bundle = getBundle();
			ContainerActivity.start(
					iSupport.getSupportActivity(),
					ImagePagerFragment.newInstance(bundle)
								   );
		}

		//    /**
		//     * Send the Intent with a custom request code
		//     *
		//     * @param fragment    Fragment to receive result
		//     * @param requestCode requestCode for result
		//     */
		//    public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment, int requestCode) {
		//      fragment.startActivityForResult(getIntent(context), requestCode);
		//    }
		//
		//    /**
		//     * Send the Intent with a custom request code
		//     *
		//     * @param fragment    Fragment to receive result
		//     */
		//    public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment) {
		//      fragment.startActivityForResult(getIntent(context), REQUEST_CODE);
		//    }

		/**
		 * Send the crop Intent from an Activity
		 *
		 * @param iSupport
		 */
		public void start(@NonNull ISupport iSupport) {

			start(
					iSupport,
					REQUEST_CODE
				 );
		}

		//		/**
		//		 * Get Intent to start {@link PhotoPickerActivity}
		//		 *
		//		 * @return Intent for {@link PhotoPickerActivity}
		//		 */
		//		/*public Intent getIntent(@NonNull Context context) {
		//		  mPreviewIntent.setClass(context, PhotoPagerActivity.class);
		//    	  mPreviewIntent.putExtras(mPreviewOptionsBundle);
		//    	  return mPreviewIntent;
		//    	}*/
		public Bundle getBundle() {

			return mPreviewOptionsBundle;
		}

		public PhotoPreviewBuilder setPhotos(ArrayList<String> photoPaths) {

			mPreviewOptionsBundle.putStringArray(
					ARG_PATH,
					photoPaths.toArray(new String[photoPaths.size()])
												);
			return this;
		}

		public PhotoPreviewBuilder setCurrentItem(int currentItem) {

			mPreviewOptionsBundle.putInt(
					ARG_CURRENT_ITEM,
					currentItem
										);
			return this;
		}

		public PhotoPreviewBuilder setShowDeleteButton(boolean showDeleteButton) {

			mPreviewOptionsBundle.putBoolean(
					ARG_SHOW_DELETE,
					showDeleteButton
											);
			return this;
		}

	}
}
