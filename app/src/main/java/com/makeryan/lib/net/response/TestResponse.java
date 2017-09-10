package com.makeryan.lib.net.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MakerYan on 2017/7/17 22:05.
 * Modify by MakerYan on 2017/7/17 22:05.
 * Email : light.yan@qq.com
 * project name : CNPCHuBei
 * package name : com.makeryan.lib.net.response
 */
public class TestResponse
		extends BaseModel
		implements Serializable {


	/**
	 * inspUnhandleNo : 0
	 * inspHandleingNo : 0
	 * inspCompleteNo : 0
	 * repaUnhandleNo : 0
	 * repaHandleingNo : 0
	 * repaCompleteNo : 0
	 */

	public int inspUnhandleNo;

	public int inspHandleingNo;

	public int inspCompleteNo;

	public int repaUnhandleNo;

	public int repaHandleingNo;

	public int repaCompleteNo;

	public static TestResponse objectFromData(String str) {

		return new Gson().fromJson(
				str,
				TestResponse.class
								  );
	}

	public static List<TestResponse> arrayTestResponseFromData(String str) {

		Type listType = new TypeToken<ArrayList<TestResponse>>() {

		}.getType();

		return new Gson().fromJson(
				str,
				listType
								  );
	}
}
