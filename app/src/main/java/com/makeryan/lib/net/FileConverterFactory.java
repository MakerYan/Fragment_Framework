package com.makeryan.lib.net;


import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by MakerYan on 16/9/13 10:30.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 */
public class FileConverterFactory
		extends Converter.Factory {

	private FileConverterFactory() {

	}

	public static FileConverterFactory create() {

		return new FileConverterFactory();
	}

	@Override
	public Converter<File, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

		return new FileRequestBodyConverter();
	}

	/*
	@Override
	public Converter<ResponseBody, File> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

		return new FileResponseBodyConverter();
	}
	*/
}
