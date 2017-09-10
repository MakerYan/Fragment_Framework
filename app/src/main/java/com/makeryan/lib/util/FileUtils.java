package com.makeryan.lib.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class FileUtils {

	private static final String SD_PATH = Environment.getExternalStorageDirectory()
													 .getPath();

	public static final String NAME = "videorecord";

	public static String getAppPath() {

		StringBuilder sb = new StringBuilder();
		sb.append(SD_PATH);
		sb.append(File.separator);
		sb.append(NAME);
		sb.append(File.separator);
		return sb.toString();
	}

	public static void deleteFile(String filePath) {

		if (TextUtils.isEmpty(filePath)) {
			return;
		}
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else {
				String[] filePaths = file.list();
				for (String path : filePaths) {
					deleteFile(filePath + File.separator + path);
				}
				file.delete();
			}
		}
	}

	public static boolean fileIsExists(String path) {

		if (path == null || path.trim()
								.length() <= 0) {
			return false;
		}
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
