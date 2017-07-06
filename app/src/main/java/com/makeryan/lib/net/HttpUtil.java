package com.makeryan.lib.net;


import com.makeryan.lib.util.FlowUtils;
import com.makeryan.lib.util.GlobUtils;
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

	//	protected static OkHttpClient.Builder okHttpClient;

	//	protected static BasicParamsInterceptor.Builder interceptorBuilder;

	private HttpUtil() {

	}

	public static ApiService getApiService() {

		OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

		okHttpClient.connectTimeout(60,	TimeUnit.SECONDS);
		okHttpClient.readTimeout(20, TimeUnit.SECONDS);
		okHttpClient.writeTimeout(20, TimeUnit.SECONDS);
		//错误重连
		okHttpClient.retryOnConnectionFailure(true);

		BasicParamsInterceptor.Builder interceptorBuilder = new BasicParamsInterceptor.Builder();

		UserInfo userInfo = FlowUtils.getUserInfo();

		interceptorBuilder.addHeaderParam("token", Base64.encodeBytes(userInfo.getToken().getBytes()));
		interceptorBuilder.addHeaderParam("ua", Base64.encodeBytes(GlobUtils.getUserAgent().getBytes()));
		interceptorBuilder.addHeaderParam("imei", Base64.encodeBytes(GlobUtils.getDeviceId().getBytes()));
		interceptorBuilder.addHeaderParam("province", Base64.encodeBytes(userInfo.getProvince().getBytes()));
		interceptorBuilder.addHeaderParam("city", Base64.encodeBytes(userInfo.getCity().getBytes()));
		interceptorBuilder.addHeaderParam("jd", Base64.encodeBytes(userInfo.getJd().getBytes()));
		interceptorBuilder.addHeaderParam("wd", Base64.encodeBytes(userInfo.getWd().getBytes()));
		interceptorBuilder.addHeaderParam("publishId", Base64.encodeBytes(API.XY_PUBLISHID.getBytes()));
		interceptorBuilder.addHeaderParam("macAddr", Base64.encodeBytes(GlobUtils.getMac().getBytes()));
		interceptorBuilder.addHeaderParam("cpuName", Base64.encodeBytes(GlobUtils.getCpuName().getBytes()));
		interceptorBuilder.addHeaderParam("cpuCore", Base64.encodeBytes(String.valueOf(GlobUtils.getNumCores()).getBytes()));
		interceptorBuilder.addHeaderParam("maxMemorySize", Base64.encodeBytes(String.valueOf(GlobUtils.getRamMemory()).getBytes()));
		interceptorBuilder.addHeaderParam("freeMemorySize", Base64.encodeBytes(GlobUtils.getAvailMemory().getBytes()));
		interceptorBuilder.addHeaderParam("screenSize", Base64.encodeBytes(GlobUtils.getScreenResolution().getBytes()));
		interceptorBuilder.addHeaderParam("minCpuFreq", Base64.encodeBytes(GlobUtils.getMinCpuFreq().getBytes()));

		BasicParamsInterceptor build = interceptorBuilder.build();

		okHttpClient.addInterceptor(build);

		OkHttpClient client = okHttpClient.build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL)
												  .addConverterFactory(JsonConverterFactory.create()) // 自定义加密
												  // .addConverterFactory(GsonConverterFactory.create())
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
