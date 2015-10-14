package com.zerovoid.common.biz;

import com.zerovoid.common.config.Constant;
import com.zerovoid.common.util.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class CommonBiz {

	// TODO 1、网络问题 2、服务器问题 3、
	// java.net.ConnectException: failed to connect to /10.168.7.1 (port 8080)
	// after 60000ms: isConnected failed: ECONNREFUSED (Connection refused)
	// TIMEOUT 连接超时

	// TODO 将VolleyHelper的内容移植到此之后，根本不需要errCode，需要作出整改；

	/** 使用非Volley，没有返回JSONObject，直接处理JSON */
	public static boolean isSuccess(String json) {
		if (json != null) {
			JSONObject response = null;
			try {
				response = new JSONObject(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			int errCode = response.optInt(Constant.RESULT_KEY, -1);
			return isSuccess(response, errCode);
		}
		return false;
	}

	public static boolean isSuccess(JSONObject response) {
		if (response != null) {
			int errCode = response.optInt(Constant.RESULT_KEY, -1);
			return isSuccess(response, errCode);
		}
		return false;
	}

	public static boolean isSuccess(JSONObject response, int errCode) {
		return isSuccess(response, errCode, true);
	}

	/**
	 * 是否成功
	 * 
	 * @param response
	 * @param errCode
	 * @param isShowErrorInfo
	 *            是否显示98型错误
	 * @return
	 */
	public static boolean isSuccess(JSONObject response, int errCode,
			boolean isShowErrorInfo) {
		if (errCode == Constant.RESULT_SUCCESS && response != null) {
			return true;
		} else if (String.valueOf(errCode).equals("98")) {
			if (!isShowErrorInfo) {
				return false;
			}
			/* 98开头的错误，需要弹出报错信息，否则自定义报错信息 */
			String message = "";
			try {
				message = response.getString(Constant.RESULT_MESSAGE_KEY);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (message == null || "".equals(message)) {
				message = "对不起，请求失败";
			}
			ToastHelper.getInstance()._toast(message);
		}

		return false;
	}

	/**
	 * 判断传入的字符串是否是NULL或者是空字符串，或者是否是NULL字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
}
