package com.makeryan.lib.net;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by MakerYan on 2017/8/16 22:25.
 * Modify by MakerYan on 2017/8/16 22:25.
 * Email : light.yan@qq.com
 * project name : CNPC_Android
 * package name : com.makeryan.lib.net
 */
public abstract class RetrofitCallback<T>
		implements Callback<T> {

	/**
	 * Invoked for a received HTTP response.
	 * <p>
	 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
	 * Call {@link retrofit2.Response#isSuccessful()} to determine if the response indicates success.
	 *
	 * @param call
	 * @param response
	 */
	@Override
	public void onResponse(Call<T> call, retrofit2.Response<T> response) {

		if (response.isSuccessful()) {
			onSuccess(
					call,
					response
					 );
		} else {
			onFailure(
					call,
					new Throwable(response.message())
					 );
		}
	}

	/**
	 * Invoked when a network exception occurred talking to the server or when an unexpected
	 * exception occurred creating the request or processing the response.
	 *
	 * @param call
	 * @param t
	 */
	@Override
	public void onFailure(Call<T> call, Throwable t) {

	}

	public abstract void onSuccess(Call<T> call, retrofit2.Response<T> response);

	public void onLoading(long total, long progress) {

	}
}
