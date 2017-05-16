package com.makeryan.lib.net;


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

	//	protected static OkHttpClient.Builder okHttpClient;

	//	protected static BasicParamsInterceptor.Builder interceptorBuilder;

	private HttpUtil() {

	}

	public static ApiService getApiService() {

		OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

		BasicParamsInterceptor.Builder interceptorBuilder = new BasicParamsInterceptor.Builder();

		interceptorBuilder.addHeaderParam(
				"token",
				""
										 );
		BasicParamsInterceptor build = interceptorBuilder.build();

		okHttpClient.addInterceptor(build);

		OkHttpClient client = okHttpClient.build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL)
												  .addConverterFactory(JsonConverterFactory.create()) // 自定义加密
												  //.addConverterFactory(GsonConverterFactory.create())
												  .addConverterFactory(new FileRequestBodyConverterFactory())
												  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
												  .client(client)
												  .build();

		return retrofit.create(ApiService.class);
	}

	public static <T> T getApiService(Class<T> clazz) {

		OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

		BasicParamsInterceptor.Builder interceptorBuilder = new BasicParamsInterceptor.Builder();
		//		interceptorBuilder.addHeaderParam("token","fdjkslafjdslk");
		//		interceptorBuilder.addHeaderParam("userid","dsafsad");

		BasicParamsInterceptor build = interceptorBuilder.build();

		okHttpClient.addInterceptor(build);

		OkHttpClient client = okHttpClient.build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL)
												  .addConverterFactory(JsonConverterFactory.create()) // 自定义加密
												  //												  .addConverterFactory(GsonConverterFactory.create())
												  .addConverterFactory(new FileRequestBodyConverterFactory())
												  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
												  .client(client)
												  .build();

		return retrofit.create(clazz);
	}

}
