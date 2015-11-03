package com.zerovoid.common.util;


import java.util.HashMap;
import java.util.Map;

import com.zerovoid.common.config.SharePreferenceUtilNew;

import android.content.Context;

public class TokenUtils {
	// Volley请求类提供了一个 getHeaers（）的方法，重载这个方法可以自定义HTTP 的头信息。
	public static Map<String, String> getHeaders(Context context) {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json; charset=UTF-8");
//		headers.put("accessToken", SharePreferenceUtilNew.getInstance()
//				.getAccessToken());
//		headers.put("accessToken","94b9261a-b0d5-4456-beff-e17def1b12bb");
		return headers;
	}
}
