package com.makeryan.lib.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by MakerYan on 16/9/13 19:05.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 */
public class JsonResponseBodyConverter<T>
		implements Converter<ResponseBody, T> {

	private final Gson mGson;//gson对象

	private final TypeAdapter<T> adapter;

	/**
	 * 构造器
	 */
	public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {

		this.mGson = gson;
		this.adapter = adapter;
	}

	/**
	 * 转换
	 *
	 * @param responseBody
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	@Override
	public T convert(ResponseBody responseBody)
			throws IOException {

		//		String response = responseBody.string();
		//		if (BuildConfig.DEBUG) {
		//			KLog.a("未解密的服务器数据：\n" + response);
		//		}
		//		String result = Authcode3.authcodeDecode(
		//				response,
		//				HttpConstant.DE_KEY
		//												);//解密
		//		if (BuildConfig.DEBUG) {
		//			KLog.a("解密的服务器数据：\n" + result);
		//		}
		//		ResponseBody body = ResponseBody.create(
		//				responseBody.contentType(),
		//				result
		//											   );
		//		JsonReader jsonReader = mGson.newJsonReader(body.charStream());
		//		try {
		//			return adapter.read(jsonReader);
		//		} finally {
		//			responseBody.close();
		//		}
		JsonReader jsonReader = mGson.newJsonReader(responseBody.charStream());
		try {
			T read = adapter.read(jsonReader);
			KLog.d("服务器返回的数据：\n" + mGson.toJson(read));
			return read;
		} finally {
			responseBody.close();
		}
	}


}