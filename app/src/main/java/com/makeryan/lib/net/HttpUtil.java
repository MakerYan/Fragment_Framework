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

	protected static OkHttpClient.Builder mOkHttpBuilder;

	protected static BasicParamsInterceptor.Builder mInterceptorBuilder;

	private HttpUtil() {

	}

	public static ApiService getApiService() {

		if (mOkHttpBuilder == null) {
			mOkHttpBuilder = new OkHttpClient.Builder();
		}

		if (mInterceptorBuilder == null) {
			mInterceptorBuilder = new BasicParamsInterceptor.Builder();
		}

		mInterceptorBuilder.addHeaderParam(
				"token",
				""
										  );
		BasicParamsInterceptor build = mInterceptorBuilder.build();

		mOkHttpBuilder.addInterceptor(build);

		OkHttpClient client = mOkHttpBuilder.build();

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

		if (mOkHttpBuilder == null) {
			mOkHttpBuilder = new OkHttpClient.Builder();
		}

		if (mInterceptorBuilder == null) {
			mInterceptorBuilder = new BasicParamsInterceptor.Builder();
		}

		//		mInterceptorBuilder.addHeaderParam("token","fdjkslafjdslk");
		//		mInterceptorBuilder.addHeaderParam("userid","dsafsad");

		BasicParamsInterceptor build = mInterceptorBuilder.build();

		mOkHttpBuilder.addInterceptor(build);

		OkHttpClient client = mOkHttpBuilder.build();

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
