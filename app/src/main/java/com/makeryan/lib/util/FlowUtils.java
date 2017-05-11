package com.makeryan.lib.util;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.Model;

/**
 * Created by MakerYan on 2017/4/12 13:42.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : KongRongQi
 * package name : com.makeryan.lib.util
 */
public class FlowUtils {

	/**
	 * 从数据库中获取表对象
	 *
	 * @return
	 */
	public static <T extends Model> T querySingle(Class<T> model) {

		T t = SQLite.select()
					.from(model)
					.querySingle();
		try {
			if (t == null) {
				t = model.newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}
}
