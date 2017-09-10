package com.makeryan.lib.net;

import android.os.Environment;

import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by MakerYan on 16/9/13 10:31.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 */
public class FileResponseBodyConverter
		implements Converter<ResponseBody, File> {

	@Override
	public File convert(ResponseBody body)
			throws IOException {

		try {
			// todo change the file location/name according to your needs

			File futureStudioIconFile = new File(Environment.getExternalStorageDirectory()
															.getPath() + File.separator + "CNPC" + File.separator + "download" + formatCurrentDate());
			InputStream  inputStream  = null;
			OutputStream outputStream = null;

			try {
				byte[] fileReader = new byte[4096];

				long fileSize           = body.contentLength();
				long fileSizeDownloaded = 0;

				inputStream = body.byteStream();
				outputStream = new FileOutputStream(futureStudioIconFile);

				while (true) {
					int read = inputStream.read(fileReader);

					if (read == -1) {
						break;
					}

					outputStream.write(
							fileReader,
							0,
							read
									  );

					fileSizeDownloaded += read;

					KLog.d("file download: " + fileSizeDownloaded + " of " + fileSize);
				}

				outputStream.flush();

				return futureStudioIconFile;
			} catch (IOException e) {
				return null;
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			}
		} catch (IOException e) {
			return null;
		}
	}

	public static String formatCurrentDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
}
