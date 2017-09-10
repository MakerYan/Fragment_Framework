package com.makeryan.lib.net;


import com.makeryan.lib.util.FlowUtils;
import com.makeryan.modules.database.UserInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by MakerYan on 16/9/13 10:26.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : ChengGua
 * package name : com.chenggua.cg.app.lib.net
 */
public class HttpUtil {

	private HttpUtil() {

	}

	public static ApiService getApiService() {

		return getApiService(
				API.BASE_URL,
				null,
				ApiService.class
							);
	}

	public static ApiService getApiService(ProgressListener listener) {

		ApiService service = (ApiService) getApiService(
				API.BASE_URL,
				listener,
				(Class) ApiService.class
													   );
		return service;
	}

	public static <T> T getApiService(String baseUrl, ProgressListener listener, Class<T> clazz) {

		OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
		okHttpClient.connectTimeout(
				60,
				TimeUnit.SECONDS
								   );
		okHttpClient.readTimeout(
				20,
				TimeUnit.SECONDS
								);
		okHttpClient.writeTimeout(
				20,
				TimeUnit.SECONDS
								 );
		//错误重连
		okHttpClient.retryOnConnectionFailure(true);

		BasicParamsInterceptor.Builder interceptorBuilder = null;
		if (listener != null) {
			interceptorBuilder = new BasicParamsInterceptor.Builder(listener);
		} else {
			interceptorBuilder = new BasicParamsInterceptor.Builder();
		}
		UserInfo userInfo = FlowUtils.getUserInfo();

		//		interceptorBuilder.addHeaderParam("token", Base64.encodeBytes(userInfo.getToken().getBytes()));

		BasicParamsInterceptor build = interceptorBuilder.build();

		okHttpClient.addInterceptor(build);

		OkHttpClient client = okHttpClient.build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
												  .addConverterFactory(JsonConverterFactory.create()) // 自定义加密
												  // .addConverterFactory(GsonConverterFactory.create())
												  .addConverterFactory(FileConverterFactory.create())
												  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
												  .client(client)
												  .build();

		return retrofit.create(clazz);
	}
}
