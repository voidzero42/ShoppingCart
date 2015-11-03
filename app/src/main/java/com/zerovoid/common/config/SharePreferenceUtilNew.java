package com.zerovoid.common.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtilNew {

    private static SharePreferenceUtilNew sharePreferenceUtil;

    private SharedPreferences spInfo;

    public SharePreferenceUtilNew() {

    }

    public static SharePreferenceUtilNew getInstance() {
        if (sharePreferenceUtil == null) {
            sharePreferenceUtil = new SharePreferenceUtilNew();
        }
        return sharePreferenceUtil;
    }

    /** 必须初始化 */
    public void init(Context context) {
        this.spInfo = context.getSharedPreferences("user_info", 0);
    }

    /**
     * 单例模式中获取唯一的AppContext实例1
     *
     * @return
     */

    public static SharePreferenceUtilNew getSharePreference(Context context) {
        sharePreferenceUtil = new SharePreferenceUtilNew(context);
        return sharePreferenceUtil;
    }

    public Editor getEditor() {
        return this.spInfo.edit();
    }

    public SharedPreferences getSP() {
        return this.spInfo;
    }

    public SharePreferenceUtilNew(Context paramContext) {
        this.spInfo = paramContext.getSharedPreferences("user_info", 0);
    }
}
