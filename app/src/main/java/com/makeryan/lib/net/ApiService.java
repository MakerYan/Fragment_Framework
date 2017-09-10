package com.makeryan.lib.net;

import com.makeryan.lib.net.request.SuperRequest;
import com.makeryan.lib.net.response.SuperResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by MakerYan on 2017/4/13 14:08.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.kongrongqi.kongrongqi.net
 */

public interface ApiService {

	@POST()
	Observable<Response<SuperResponse>> postApi(@Url() String api, @Body SuperRequest request);

	@POST()
	Observable<Response<List<SuperResponse>>> postListApi(@Url() String api, @Body SuperRequest request);

	@POST()
	Observable<Response<SuperResponse>> postApi(@Url() String api);

	@POST()
	Observable<Response<List<SuperResponse>>> postListApi(@Url() String api);

	@POST()
	Observable<Response<SuperResponse>> postApi(@Url() String api, @Body SuperResponse request);

	@POST()
	Observable<Response<List<SuperResponse>>> postListApi(@Url() String api, @Body SuperResponse request);

	@Multipart
	@POST()
	Observable<Response<SuperResponse>> uploadListFile(@Url() String api, @Part List<MultipartBody.Part> partList);

	@Streaming
	@GET
	Call<ResponseBody> downloadFile(@Url String api);

	@Multipart
	@POST()
	Call<Response<SuperResponse>> uploadFileWithProgress(@Url() String api, @Part List<MultipartBody.Part> partList);


	@Streaming
	@GET
	Call<ResponseBody> downloadGetFileWithProgress(@Url String api);
}