package com.zerovoid.common.config;

/**
 * 各自测试用参数，勿上传
 * 
 * @author WuGefei
 * 
 */
public class ConstantPrivate {
	public static final String PATH_ROOT = "/fattail_api/app/fattail/service/";
	/** 老王服务器 */
	// public static final String SERVER_URL = "http://10.168.7.64:8080";
	/** 统哥服务器 */
	public static final String SERVER_URL = "http://10.168.7.1:8080";
	/** 统哥服务器 */
	// public static final String SERVER_URL = "http://192.168.1.103";

	/** 231服务器 */
	// public static String SERVER_URL = "http://developer.17orange.com:10002";

	// static {
	// if (AppConfig.APP_MODE == AppConfig.MODE_PUBLISH) {
	// SERVER_URL = "http://developer.17orange.com:10002";
	// } else if (AppConfig.APP_MODE == AppConfig.MODE_TEST) {
	// SERVER_URL = "http://developer.17orange.com:10002";
	// } else if (AppConfig.APP_MODE == AppConfig.MODE_TONG) {
	// SERVER_URL = "http://10.168.7.1:8080";
	// }
	// }
	/** 是否直接登录 */
	public static boolean isLoginWithoutValidate = false;
}
