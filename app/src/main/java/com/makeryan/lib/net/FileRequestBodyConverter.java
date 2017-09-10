package com.makeryan.lib.net;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by MakerYan on 16/9/13 10:31.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 */
public class FileRequestBodyConverter
		implements Converter<File, RequestBody> {

	@Override
	public RequestBody convert(File file)
			throws IOException {

		return RequestBody.create(
				//				MediaType.parse("application/otcet-stream"),
				MediaType.parse("multipart/form-data"),
				file
								 );
	}
}
