package com.makeryan.lib.net;


import com.makeryan.modules.vo.request.SuperRequest;
import com.makeryan.modules.vo.response.SuperResponse;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by MakerYan on 2017/4/13 14:08.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.kongrongqi.kongrongqi.net
 */

public interface ApiService {

	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<SuperResponse>> postApi(@Path("api") String api, @Body SuperRequest request);

	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<List<SuperResponse>>> postListApi(@Path("api") String api, @Body SuperRequest request);

	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<SuperResponse>> postApi(@Path("api") String api);

	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<List<SuperResponse>>> postListApi(@Path("api") String api);

	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<SuperResponse>> postApi(@Path("api") String api, @Body SuperResponse request);

	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<List<SuperResponse>>> postListApi(@Path("api") String api, @Body SuperResponse request);

	@Multipart
	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<SuperResponse>> uploadFile(@Path("api") String api, @Part("filename") File file);

	@Multipart
	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<SuperResponse>> uploadFile(@Path("api") String api, @Part("description") RequestBody description, @Part MultipartBody.Part file);

	@Multipart
	@POST(API.BASE_NEWURL + "{api}")
	Observable<Response<SuperResponse>> uploadFile(@Path("api") String api, @Part("description") RequestBody description, @Part("filename") File file);
}