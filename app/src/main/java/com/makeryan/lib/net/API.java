package com.makeryan.lib.net;

/**
 * Created by MakerYan on 16/9/13 17:20.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 */
public interface API {

	String OSS_URL_TEST = "http://krq-app-test.oss-cn-shanghai.aliyuncs.com/";


	String OSS_URL = OSS_URL_TEST;

	String OSS_URL_SUFFIX = "_test";

	String endpoint = "oss-cn-shanghai.aliyuncs.com";

	String accessKeyId = "LTAIDN7dJuPVFHbd";

	String accessKeySecret = "fcj06LlJ3YfcfVlbkbrPkhMBzlxt2A";

	String bucketName = "krq-app-test";

	String objectKeyIcon = "icon_folder/icon";

	String uploadObject = "sampleObject";

	String downloadObject = "sampleObject";

	/**
	 * 联机
	 */
	String XHS = "http://192.168.8.68:8080/krq-api/";

	/**
	 * 外部测试
	 */
	String EXTERNAL_LINKS_TEST = "http://106.15.52.56:8080/krq-api/";

	/**
	 * URL
	 */
	String BASE_URL = XHS;

}
