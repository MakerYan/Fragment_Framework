package com.makeryan.lib.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.makeryan.lib.BuildConfig;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by MakerYan on 16/9/13 19:04.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 */
public class JsonRequestBodyConverter<T>
		implements Converter<T, RequestBody> {

	//	private static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream; charset=UTF-8"); // 二进制流
	private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8"); // POST JSON
	//	private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"); //表单

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private final Gson gson;

	private final TypeAdapter<T> adapter;

	/**
	 * 构造器
	 */

	public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {

		this.gson = gson;
		this.adapter = adapter;
	}


	@Override
	public RequestBody convert(T value)
			throws IOException {
		//		//加密
		//		APIBodyData data = new APIBodyData();
		//		if (BuildConfig.DEBUG) {
		//			KLog.a("request中传递的json数据：\n" + value.toString());
		//		}
		//		data.setData(Authcode3.authcodeEncode(
		//				value.toString(),
		//				HttpConstant.EN_KEY,
		//				60
		//											 ));
		//		String postBody = gson.toJson(data); //对象转化成json
		//		if (BuildConfig.DEBUG) {
		//			KLog.a("转化后的数据：\n" + postBody);
		//		}
		//		return RequestBody.create(
		//				MEDIA_TYPE,
		//				postBody
		//								 );
		if (BuildConfig.DEBUG) {
			KLog.d("request中传递的json数据：\n" + gson.toJson(value));
			KLog.json(gson.toJson(value));
		}
		Buffer buffer = new Buffer();
		Writer writer = new OutputStreamWriter(
				buffer.outputStream(),
				UTF_8
		);
		JsonWriter jsonWriter = gson.newJsonWriter(writer);
		adapter.write(
				jsonWriter,
				value
					 );
		jsonWriter.close();
		return RequestBody.create(
				MEDIA_TYPE,
				buffer.readByteString()
								 );
	}
}