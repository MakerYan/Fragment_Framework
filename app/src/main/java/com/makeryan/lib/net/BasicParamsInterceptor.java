package com.makeryan.lib.net;

import android.text.TextUtils;

import com.socks.library.KLog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 * Created by MakerYan on 16/9/13 11:21.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : ChengGua
 * package name : com.chenggua.cg.app.lib.net
 */
public class BasicParamsInterceptor
		implements Interceptor {

	Map<String, String> queryParamsMap = new HashMap<>();

	Map<String, String> paramsMap = new HashMap<>();

	Map<String, String> headerParamsMap = new HashMap<>();

	List<String> headerLinesList = new ArrayList<>();

	private BasicParamsInterceptor() {

	}

	private static String bodyToString(final RequestBody request) {

		try {
			final RequestBody copy   = request;
			final Buffer      buffer = new Buffer();
			if (copy != null) {
				copy.writeTo(buffer);
			} else {
				return "";
			}
			return buffer.readUtf8();
		} catch (final IOException e) {
			return "did not work";
		}
	}

	@Override
	public Response intercept(Chain chain)
			throws IOException {

		Request         request        = chain.request();
		Request.Builder requestBuilder = request.newBuilder();

		// process header params inject
		Headers.Builder headerBuilder = request.headers()
											   .newBuilder();
		if (headerParamsMap.size() > 0) {
			Iterator iterator = headerParamsMap.entrySet()
											   .iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String    value = String.valueOf(entry.getValue());
//				KLog.d("\nkey : " + entry.getKey() + "\nvalue : " + new String(
//						Base64.decode(value),
//						"UTF-8"
//				));
				if (!TextUtils.isEmpty(value)) {
					try {
						headerBuilder.add(
								(String) entry.getKey(),
								value
										 );
					} catch (Exception e) {
						KLog.d("\n" + e.getCause() + "\n" + e.getMessage());
					}
				}
			}
		}

		if (headerLinesList.size() > 0)

		{
			for (String line : headerLinesList) {
				headerBuilder.add(line);
			}
		}

		requestBuilder.headers(headerBuilder.build());
		// process header params end


		// process queryParams inject whatever it's GET or POST
		if (queryParamsMap.size() > 0)

		{
			injectParamsIntoUrl(
					request,
					requestBuilder,
					queryParamsMap
							   );
		}
		// process header params end


		// process post body inject
		if (

				canInjectIntoBody(request))

		{
			FormBody.Builder formBodyBuilder = new FormBody.Builder();
			if (paramsMap.size() > 0) {
				Iterator iterator = paramsMap.entrySet()
											 .iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					formBodyBuilder.add(
							(String) entry.getKey(),
							(String) entry.getValue()
									   );
				}
			}
			RequestBody formBody       = formBodyBuilder.build();
			String      postBodyString = bodyToString(request.body());
			postBodyString += ((postBodyString.length() > 0) ?
					"&" :
					"") + bodyToString(formBody);
			requestBuilder.post(RequestBody.create(
					MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
					postBodyString
												  ));
		} else

		{    // can't inject into body, then inject into url
			injectParamsIntoUrl(
					request,
					requestBuilder,
					paramsMap
							   );
		}
		KLog.d(
				"BaseHttp",
				"url : " + request.url()
								  .

										  toString()
			  );
		request = requestBuilder.build();
		return chain.proceed(request);
	}

	private boolean canInjectIntoBody(Request request) {

		if (request == null) {
			return false;
		}
		if (!TextUtils.equals(
				request.method(),
				"POST"
							 )) {
			return false;
		}
		RequestBody body = request.body();
		if (body == null) {
			return false;
		}
		MediaType mediaType = body.contentType();
		if (mediaType == null) {
			return false;
		}
		if (!TextUtils.equals(
				mediaType.subtype(),
				"x-www-form-urlencoded"
							 )) {
			return false;
		}
		return true;
	}

	// func to inject params into url
	private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {

		HttpUrl.Builder httpUrlBuilder = request.url()
												.newBuilder();
		if (paramsMap.size() > 0) {
			Iterator iterator = paramsMap.entrySet()
										 .iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				httpUrlBuilder.addQueryParameter(
						(String) entry.getKey(),
						(String) entry.getValue()
												);
			}
		}

		requestBuilder.url(httpUrlBuilder.build());
	}

	public static class Builder {

		BasicParamsInterceptor interceptor;

		public Builder() {

			interceptor = new BasicParamsInterceptor();
		}

		public Builder addParam(String key, String value) {

			interceptor.paramsMap.put(
					key,
					value
									 );
			return this;
		}

		public Builder addParamsMap(Map<String, String> paramsMap) {

			interceptor.paramsMap.putAll(paramsMap);
			return this;
		}

		public Builder addHeaderParam(String key, String value) {

			interceptor.headerParamsMap.put(
					key,
					value
										   );
			return this;
		}

		public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {

			interceptor.headerParamsMap.putAll(headerParamsMap);
			return this;
		}

		public Builder addHeaderLine(String headerLine) {

			int index = headerLine.indexOf(":");
			if (index == -1) {
				throw new IllegalArgumentException("Unexpected header: " + headerLine);
			}
			interceptor.headerLinesList.add(headerLine);
			return this;
		}

		public Builder addHeaderLinesList(List<String> headerLinesList) {

			for (String headerLine : headerLinesList) {
				int index = headerLine.indexOf(":");
				if (index == -1) {
					throw new IllegalArgumentException("Unexpected header: " + headerLine);
				}
				interceptor.headerLinesList.add(headerLine);
			}
			return this;
		}

		public Builder addQueryParam(String key, String value) {

			interceptor.queryParamsMap.put(
					key,
					value
										  );
			return this;
		}

		public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {

			interceptor.queryParamsMap.putAll(queryParamsMap);
			return this;
		}

		public BasicParamsInterceptor build() {

			return interceptor;
		}

	}

	//由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
	//会首先替换 \n ,然后使用 okhttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
	private static String getValueEncoded(String value)
			throws UnsupportedEncodingException {

		if (value == null) {
			return "null";
		}
		String newValue = value.replace(
				"\n",
				""
									   );
		for (int i = 0, length = newValue.length(); i < length; i++) {
			char c = newValue.charAt(i);
			if (c <= '\u001f' || c >= '\u007f') {
				return URLEncoder.encode(
						newValue,
						"UTF-8"
										);
			}
		}
		return newValue;
	}
}