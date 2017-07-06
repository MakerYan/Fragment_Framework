package com.makeryan.modules.vo.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MakerYan on 2017/6/4 13:56.
 * Modify by MakerYan on 2017/6/4 13:56.
 * Email : light.yan@qq.com
 * project name : shanghaixuankai_android
 * package name : com.makeryan.modules.vo.response
 */
public class TestResponse {

	/**
	 * formName : fevcfr3
	 * toName : 草根！疯子
	 * presentName : 玫瑰
	 */

	public String formName;

	public String toName;

	public String presentName;

	public static TestResponse objectFromData(String str) {

		return new Gson().fromJson(str,
								   TestResponse.class);
	}

	public static List<TestResponse> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<TestResponse>>() {

		}.getType();

		return new Gson().fromJson(str,
								   listType);
	}
}
