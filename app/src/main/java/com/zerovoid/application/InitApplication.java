package com.zerovoid.application;


import android.app.Application;

import com.zerovoid.common.config.SharePreferenceUtilNew;
import com.zerovoid.common.util.ToastHelper;
import com.zerovoid.http.VollyHelperNew;


public class InitApplication extends Application {
    private static InitApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initVollyHelper();
        initSharePreferenceUtil();
        initToastHelper();
    }

    public static InitApplication getInstance() {
        if (mInstance == null) {
            mInstance = new InitApplication();
        }
        return mInstance;
    }

    private void initVollyHelper() {
        VollyHelperNew.getInstance().initVollyHelper(getApplicationContext());
    }

    private void initSharePreferenceUtil() {
        SharePreferenceUtilNew.getInstance().init(getApplicationContext());
    }

    private void initToastHelper() {
        ToastHelper.getInstance().init(getApplicationContext());
    }
}