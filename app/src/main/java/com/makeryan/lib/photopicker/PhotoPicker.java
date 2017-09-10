package com.makeryan.lib.photopicker;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.makeryan.lib.activity.ContainerActivity;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.photopicker.fragment.PhotoPickerFragment;
import com.makeryan.lib.photopicker.utils.PermissionsUtils;

import java.util.ArrayList;


/**
 * Created by Donglua on 16/6/25.
 * Builder class to ease Intent setup.
 */
public class PhotoPicker {

	public static final int REQUEST_CODE = 233;

	public final static int DEFAULT_MAX_COUNT = 9;

	public final static int DEFAULT_COLUMN_NUMBER = 3;

	public final static String KEY_SELECTED_PHOTOS = "SELECTED_PHOTOS";

	public final static String EXTRA_MAX_COUNT = "MAX_COUNT";

	public final static String EXTRA_SHOW_CAMERA = "SHOW_CAMERA";

	public final static String EXTRA_SHOW_GIF = "SHOW_GIF";

	public final static String EXTRA_GRID_COLUMN = "column";

	public final static String EXTRA_ORIGINAL_PHOTOS = "ORIGINAL_PHOTOS";

	public final static String EXTRA_PREVIEW_ENABLED = "PREVIEW_ENABLED";

	public static PhotoPickerBuilder builder() {

		return new PhotoPickerBuilder();
	}

	public static class PhotoPickerBuilder {

		private Bundle mPickerOptionsBundle;
		//    private Intent mPickerIntent;

		public PhotoPickerBuilder() {

			mPickerOptionsBundle = new Bundle();
			//      mPickerIntent = new Intent();
		}

		/**
		 * Send the Intent from an Activity with a custom request code
		 *
		 * @param iSupport
		 * @param requestCode
		 * 		requestCode for result
		 */
		@RequiresApi(api = Build.VERSION_CODES.M)
		public void start(@NonNull ISupport iSupport, int requestCode) {

			if (iSupport == null) {
				return;
			}

			if (!iSupport.checkPermission(PermissionsUtils.READ_EXTERNAL_STORAGE)) {
				iSupport.requestPermission(
						PermissionsUtils.REQUEST_CODE_READ_EXTERNAL_STORAGE,
						PermissionsUtils.READ_EXTERNAL_STORAGE
										  );
				return;
			}
			iSupport.enqueueAction(() -> {

					/*iSupport.startForResult(
							PhotoPickerFragment.newInstance(getBundle()),
							requestCode
										   );*/
				ContainerActivity.start(
						iSupport.getActivity(),
						PhotoPickerFragment.newInstance(getBundle())
									   );
			});
		}

		//		/**
		//		 * Get Intent to start {@link PhotoPickerActivity}
		//		 *
		//		 * @return Intent for {@link PhotoPickerActivity}
		//		 */
		//		/*public Intent getIntent(@NonNull Context context) {
		//		  mPickerIntent.setClass(context, PhotoPickerActivity.class);
		//    	  mPickerIntent.putExtras(mPickerOptionsBundle);
		//    	  return mPickerIntent;
		//    	}*/
		public Bundle getBundle() {

			return mPickerOptionsBundle;
		}

		/**
		 * Send the crop Intent from an Activity
		 *
		 * @param iSupport
		 * 		ISupport to receive result
		 */
		public void start(@NonNull ISupport iSupport) {

			start(
					iSupport,
					REQUEST_CODE
				 );
		}

		public PhotoPickerBuilder setPhotoCount(int photoCount) {

			mPickerOptionsBundle.putInt(
					EXTRA_MAX_COUNT,
					photoCount
									   );
			return this;
		}

		public PhotoPickerBuilder setGridColumnCount(int columnCount) {

			mPickerOptionsBundle.putInt(
					EXTRA_GRID_COLUMN,
					columnCount
									   );
			return this;
		}

		public PhotoPickerBuilder setShowGif(boolean showGif) {

			mPickerOptionsBundle.putBoolean(
					EXTRA_SHOW_GIF,
					showGif
										   );
			return this;
		}

		public PhotoPickerBuilder setShowCamera(boolean showCamera) {

			mPickerOptionsBundle.putBoolean(
					EXTRA_SHOW_CAMERA,
					showCamera
										   );
			return this;
		}

		public PhotoPickerBuilder setSelected(ArrayList<String> imagesUri) {

			mPickerOptionsBundle.putStringArrayList(
					EXTRA_ORIGINAL_PHOTOS,
					imagesUri
												   );
			return this;
		}

		public PhotoPickerBuilder setPreviewEnabled(boolean previewEnabled) {

			mPickerOptionsBundle.putBoolean(
					EXTRA_PREVIEW_ENABLED,
					previewEnabled
										   );
			return this;
		}
	}
}
