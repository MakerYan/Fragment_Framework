package com.makeryan.lib.net;

/**
 * Created by MakerYan on 2017/5/15 下午8:49.
 *
 * @author MakerYan
 * @email light.yan@qq.com
 * @time 2017/5/15 下午8:49
 */
public interface API {


	/**
	 * 通知与俟
	 */
	String QUICK_START = "http://10.206.129.209:2017/ZSY_JWX/business/pages/operationManual/operationManual.jsp";

	String TEST_URL = "http://10.206.129.209:2017/ZSY_JWX/";

	String WEI_CHU = "http://10.206.180.69:8080/ZSY_JWX/";

	String EXTERNAL_URL = "http://119.97.219.136:2017/ZSY_JWX/";

	String BASE_URL = EXTERNAL_URL;

	String SUFFIX = ".action";

	/**
	 * 登录接口
	 */
	String LOGIN = "bsuser/login" + SUFFIX;

	/**
	 * 巡检接口
	 */
	String INSPECTION = "insp/findList" + SUFFIX;

	/**
	 * 巡检ITEM详情
	 */
	String INSP_DETAILS = "insp/findDetails" + SUFFIX;

	/**
	 * 完成巡检
	 */
	String INSP_UPDATE_INSP = "insp/updateInsp" + SUFFIX;

	/**
	 * 获取故障类型
	 */
	String DEVICE_FINDDIC = "device/findDic" + SUFFIX;

	/**
	 * 获取设备类型
	 */
	String DEVICE_FIND_LIST = "device/findList" + SUFFIX;


	/**
	 * 获取设备名称
	 */
	String DEVICE_FIND_DEVICE_NAME = "device/findLeader" + SUFFIX;

	/**
	 * 获取故障部位
	 */
	String DEVICE_FIND_FAULT_POSITION = "device/findPart" + SUFFIX;

	/**
	 * 获取配件厂商
	 */
	String DEVICE_FIND_FAC_LIST = "device/findFacList" + SUFFIX;

	/**
	 * 获取配件名称
	 */
	String DEVICE_FIND_BASE_LIST = "device/findBaseList" + SUFFIX;

	/**
	 * 获取品牌型号
	 */
	String DEVICE_FIND_MODEL_LIST = "device/findModelList" + SUFFIX;

	/**
	 * 站经理
	 * 获取报修工单
	 */
	String REPA_FIND_LIST = "repa/findList" + SUFFIX;

	/**
	 * 维修工
	 * 报修任务,加油站列表
	 */
	String REPA_FIND_STA_LIST = "repa/findStaList" + SUFFIX;

	/**
	 * 维修签到
	 */
	String REPA_SIGN = "repa/sign" + SUFFIX;

	/**
	 * 巡检签到
	 */
	String INSP_SIGN = "insp/sign" + SUFFIX;

	/**
	 * 立即报修
	 */
	String IMMEDIATE_REPAIR = "repa/addRepa" + SUFFIX;

	/**
	 * 报修-已完成
	 */
	String REPA_FIND_END_LIST = "repa/findEndList" + SUFFIX;

	/**
	 * 上传文件
	 */
	String FILE_UPLOAD = "file/upload" + SUFFIX;

	/**
	 * 上传文件
	 */
	String FILE_UPLOAD2 = "file/upload2" + SUFFIX;

	/**
	 * 故障描述快捷关键字
	 */
	String FIND_DESC_LIST = "device/findDescList" + SUFFIX;

	/**
	 * 故障详情-完成检修
	 */
	String REPA_UPDATE_REPA = "repa/updateRepa" + SUFFIX;

	/**
	 * 申请重新派工
	 */
	String REPA_AGAIN_REPA = "repa/againRepa" + SUFFIX;

	/**
	 * 更改到站日期
	 */
	String INSP_AGAIN_INSP = "insp/againInsp" + SUFFIX;

	/**
	 * 新增设备
	 */
	String DEVICE_ADD_TEMP = "device/addTemp" + SUFFIX;

	/**
	 * 已报修列表
	 */
	String REPA_FIND_OWN_LIST = "repa/findOwnList" + SUFFIX;

	/**
	 * 个人中心
	 */
	String BSUSER_FIND_BASE = "bsuser/findBase" + SUFFIX;

	/**
	 * 修改密码
	 */
	String BSUSER_UPDATE_PASSWORD = "bsuser/updatePsd" + SUFFIX;

	/**
	 * 公告查询
	 */
	String BSUSER_FIND_NOTICE = "bsuser/findNotice" + SUFFIX;

	/**
	 * 获取当前APP版本
	 */
	String BSUSER_FIND_APP_VERSION = "bsuser/findAppVersion" + SUFFIX;

	/**
	 * 本地存储数据的版本
	 */
	String BSUSER_FIND_DIC_VERSION = "bsuser/findDicVersion" + SUFFIX;

	/**
	 * 风险作业
	 */
	String REPA_UPDATE_RISK = "repa/updateRisk" + SUFFIX;
}