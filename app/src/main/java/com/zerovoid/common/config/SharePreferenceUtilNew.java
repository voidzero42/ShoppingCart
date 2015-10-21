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

    /**************************************************************************/

    /**
     * 获取shareList界面返回的shareID
     *
     * @return
     */
    public String getResponseShareID() {
        return this.spInfo.getString("shareID", "0");
    }

    /**
     * 存入shareID
     *
     * @param paramString
     */
    public void setResponseShareID(String paramString) {
        this.spInfo.edit().putString("shareID", paramString).commit();
    }

    public String getGoodsInfo() {
        return this.spInfo.getString("goodsInfo", "");
    }

    public void setGoodsInfo(String goodsInfo) {
        this.spInfo.edit().putString("goodsInfo", goodsInfo).commit();
    }

    public String getToken() {
        return this.spInfo.getString("token", "");
    }

    public void setToken(String token) {
        this.spInfo.edit().putString("token", token).commit();
    }

    /**************************************************************************/

    /**
     * @param paramString 设置是否打开手势密码 0关闭1打开
     */
    public void setLockPatternSwitch(int paramString) {
        this.spInfo.edit().putInt("lockPatternSwitch", paramString).commit();
    }

    /**
     * @return 设置是否打开手势密码 0关闭1打开
     */
    public int getLockPatternSwitch() {
        return this.spInfo.getInt("lockPatternSwitch", 0);
    }

    /** 是否第一次登陆 */
    public boolean getIsFirstLogin() {
        return this.spInfo.getBoolean("isFirstLogin", true);
    }

    /**
     * @param paramBoolean 是否第一次登录
     */
    public void setIsFirstLogin(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isFirstLogin", paramBoolean).commit();
    }

    /** 是否按home键退出 */
    public boolean getIsHomeBack() {
        return this.spInfo.getBoolean("isHomeBack", false);
    }

    /**
     * @param paramBoolean 是否按home键退出
     */
    public void setIsHomeBack(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isHomeBack", paramBoolean).commit();
    }

    /**
     * @return 手机号码
     */
    public String getMobile() {
        return this.spInfo.getString("mobile", "");
    }

    /**
     * @param paramString 手机号码
     */
    public void setMobile(String paramString) {
        this.spInfo.edit().putString("mobile", paramString).commit();
    }

    /**
     * @param paramString 登录账户
     */
    public void setAccount(String paramString) {
        this.spInfo.edit().putString("account", paramString).commit();
    }

    public String getAccount() {
        return this.spInfo.getString("account", "小橙子");
    }

    /**
     * @param paramSet 存储登录过的账号
     */
    @SuppressLint("NewApi")
    public void setLoginPhone(Set<String> paramSet) {
        this.spInfo.edit().putStringSet("loginphone", paramSet).commit();
    }

    /**
     * 获取登录过的账号
     */
    @SuppressLint("NewApi")
    public Set<String> getLoginPhone() {
        Set<String> set = new HashSet<String>();
        return this.spInfo.getStringSet("loginphone", set);
    }

    /**
     * @return 最近登录的账号
     */
    public String getLastTimeAccount() {
        return this.spInfo.getString("lastTimeAccount", "");
    }

    /**
     * @param paramString 最近登录的账号
     */
    public void setLastTimeAccount(String paramString) {
        this.spInfo.edit().putString("lastTimeAccount", paramString).commit();
    }

    /**
     * @return 真名
     */
    public String getTrueName() {
        return this.spInfo.getString("trueName", "");
    }

    /**
     * @param paramString 真名
     */
    public void setTrueName(String paramString) {
        this.spInfo.edit().putString("trueName", paramString).commit();
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return this.spInfo.getString("password", "");
    }

    /**
     * @param paramString 密码
     */
    public void setPassword(String paramString) {
        this.spInfo.edit().putString("password", paramString).commit();
    }

    /**
     * @return 性别
     */
    public String getSex() {
        return this.spInfo.getString("sex", "");
    }

    /**
     * @param paramString 性别
     */
    public void setSex(String paramString) {
        this.spInfo.edit().putString("sex", paramString).commit();
    }

    /**
     * @return 是否游客
     */
    public boolean getIsVisitor() {
        return this.spInfo.getBoolean("isVisitor", true);
    }

    /**
     * @param paramBoolean 是否是游客
     */
    public void setIsVisitor(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isVisitor", paramBoolean).commit();
    }

    /**
     * @return 登录类型
     */
    public int getLoginMode() {
        return this.spInfo.getInt("loginMode", 0);
    }

    public String getUserId() {
        return this.spInfo.getString("userId", "");
    }

    public void setUserId(String paramString) {
        this.spInfo.edit().putString("userId", paramString).commit();
    }

    /**
     * @param paramInt 登录模式
     */
    public void setLoginMode(int paramInt) {
        this.spInfo.edit().putInt("loginMode", paramInt).commit();
    }

    /**
     * @param paramString 是否自动登录，false不自动true自动
     */
    public void setIsAutoLogin(boolean paramString) {
        this.spInfo.edit().putBoolean("autoLogin", paramString).commit();
    }

    /**
     * @return 是否自动登录
     */
    public boolean isAutoLogin() {
        return this.spInfo.getBoolean("autoLogin", false);
    }

    /** 设置Token是否过期 */
    public void setIsAccessTokenExpired(boolean paramString) {
        this.spInfo.edit().putBoolean("isAccessTokenExpired", paramString)
                .commit();
    }

    /**
     * @return 是否Token过期
     */
    public boolean isAccessTokenExpired() {
        return this.spInfo.getBoolean("isAccessTokenExpired", true);
    }

    /**
     * @return 是否记住密码
     */
    public boolean isRemberPasW() {
        return this.spInfo.getBoolean("remberPasW", true);
    }

    /**
     * @param paramString 是否记住密码，false不自动true自动
     */
    public void setIsRemberPasW(boolean paramString) {
        this.spInfo.edit().putBoolean("remberPasW", paramString).commit();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getVerifyCode() {
        return this.spInfo.getString("verifyCode", "");
    }

    /**
     * 储存验证码
     *
     * @param paramString
     */
    public void setVerifyCode(String paramString) {
        this.spInfo.edit().putString("verifyCode", paramString).commit();
    }

    public void setFirstLogin(boolean isFirst) {
        this.spInfo.edit().putBoolean("first", isFirst).commit();
    }

    /**
     * 获得邮箱信息
     *
     * @return
     */
    public String getEmail() {
        return this.spInfo.getString("email", "");
    }

    /**
     * 储存邮箱信息
     */
    public void setEmail(String paramString) {
        this.spInfo.edit().putString("email", paramString).commit();
    }

    /**
     * 获得appSecret
     *
     * @return
     */
    public String getAppSecret() {
        return this.spInfo.getString("appSecret", "");
    }

    /**
     * 储存appSecret
     */
    public void setAppSecret(String paramString) {
        this.spInfo.edit().putString("appSecret", paramString).commit();
    }

    /**
     * @param paramString 上传的文件名称
     */
    public void setFileName(String paramString) {
        this.spInfo.edit().putString("fileName", paramString).commit();
    }

    public void setCommentNum(int paramInt) {
        this.spInfo.edit().putInt("commentNum", paramInt).commit();
    }

    public void setDealTimeDate(String paramString) {
        this.spInfo.edit().putString("DealTimeDate", paramString).commit();
    }

    public void setEndDate(String paramString) {
        this.spInfo.edit().putString("endDate", paramString).commit();
    }

    public void setExpressNum(int paramInt) {
        this.spInfo.edit().putInt("expressNum", paramInt).commit();
    }

    public void setHeadPicName(String paramString) {
        this.spInfo.edit().putString("headPicName", paramString).commit();
    }

    public void setHeadPicUrl(String paramString) {
        this.spInfo.edit().putString("headPicUrl", paramString).commit();
    }

    public void setImageUrl(String paramString) {
        this.spInfo.edit().putString("imageUrl", paramString).commit();
    }

    /**
     * @param paramBoolean 是否支持蓝牙
     */
    public void setIsBluetoothSupport(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("bluetooth", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否第一次加载应用
     */
    public void setIsFirstLaunch(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isFirstLaunch", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否首次点击导航
     */
    public void setIsGuestCodeFirstClick(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isFirstClick", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否需要导航
     */
    public void setIsNeedGuide(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isNeedGuide", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否新的业务支持
     */
    public void setIsNewBusinessSupport(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("newBusiness", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否有通知
     */
    public void setIsNotifyOn(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("isNotifyOn", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否支持一卡通
     */
    public void setIsOneCardSupport(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("oneCard", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 是否支持预付
     */
    public void setIsPrePaySupported(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("propertyBillPrePay", paramBoolean)
                .commit();
    }

    /**
     * @param paramBoolean 是否物业账单支付
     */
    public void setIsPropertyBillPaymentSupport(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("propertyBillPayment", paramBoolean)
                .commit();
    }

    public void setIsPropertyBillSupport(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("propertybill", paramBoolean).commit();
    }

    public void setIsProtection(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("protection", paramBoolean).commit();
    }

    /**
     * @param paramBoolean 工作人员的支持
     */
    public void setIsStaffLikeSupport(boolean paramBoolean) {
        this.spInfo.edit().putBoolean("staffLike", paramBoolean).commit();
    }

	/*---------------------------- 登录返回信息 -------------------------------*/

    /**
     * @param paramString 设置授权令牌
     */
    public void setAccessToken(String paramString) {
        this.spInfo.edit().putString("accessToken", paramString).commit();
    }

    /**
     * @return 授权令牌
     */
    public String getAccessToken() {
        return this.spInfo.getString("accessToken", "");
    }

    /**
     * @return 用户ID
     */
    public String getMemberID() {
        return this.spInfo.getString("memberID", "");
    }

    /**
     * @param paramString 设置用户ID
     */
    public void setMemberID(String paramString) {
        this.spInfo.edit().putString("memberID", paramString).commit();
    }

    /**
     * @return 登录类型
     */
    public String getLoginType() {
        return this.spInfo.getString("loginType", "");
    }

    /**
     * @param paramString 设置登录类型
     */
    public void setLoginType(String paramString) {
        this.spInfo.edit().putString("loginType", paramString).commit();
    }

    /**
     * @param paramString 设置账号
     */

    public void setLoginName(String paramString) {
        this.spInfo.edit().putString("loginName", paramString).commit();
    }

    /**
     * @return 账号
     */
    public String getLoginName() {
        return this.spInfo.getString("loginName", "");
    }

    /**
     * @param paramString 设置昵称
     */

    public void setNickName(String paramString) {
        this.spInfo.edit().putString("loginName", paramString).commit();
    }

    /**
     * @return 昵称
     */
    public String getNickName() {
        return this.spInfo.getString("loginName", "");
    }

    /**
     * @param paramString 设置手机号
     */

    public void setPhoneNum(String paramString) {
        this.spInfo.edit().putString("phone", paramString).commit();
    }

    /**
     * @return 手机号
     */
    public String getPhoneNum() {
        return this.spInfo.getString("phone", "");
    }

    /**
     * @param paramString 设置积分
     */
    public void setScore(String paramString) {
        this.spInfo.edit().putString("score", paramString).commit();
    }

    /**
     * @return 积分
     */
    public String getScore() {
        return this.spInfo.getString("score", "");
    }

	/*---------------------------- 最后一次登录信息 -------------------------------*/

    /**
     * @param paramString 设置上一次登录时间
     */
    public void setLastLoginTime(String paramString) {
        this.spInfo.edit().putString("lastLoginTime", paramString).commit();
    }

    /**
     * @return 上一次登录时间
     */
    public String getLastLoginTime() {
        return this.spInfo.getString("lastLoginTime", "");
    }

    /**
     * @param paramString 身份证号
     */
    public void setIdCard(String paramString) {
        this.spInfo.edit().putString("iDCard", paramString).commit();
    }

    /**
     * @return 身份证号
     */
    public String getIdCard() {
        return this.spInfo.getString("iDCard", "");
    }

    /**
     * @param paramString 是否认证
     */
    public void setCerStatus(String paramString) {
        this.spInfo.edit().putString("cerStatus", paramString).commit();
    }

    /**
     * @return 是否认证
     */
    public String getCerStatus() {
        return this.spInfo.getString("cerStatus", "0");
    }

    /**
     * @param paramString 是否认证
     */
    public void setTotalMoney(String paramString) {
        this.spInfo.edit().putString("totalMoney", paramString).commit();
    }

    /**
     * @return 是否认证
     */
    public String getTotalMoney() {
        return this.spInfo.getString("totalMoney", "0");
    }

    /**
     * @param paramString 余额
     */
    public void setBalance(String paramString) {
        this.spInfo.edit().putString("balance", paramString).commit();
    }

    /**
     * @return 余额
     */
    public String getBalance() {
        return this.spInfo.getString("balance", "0");
    }

    /**
     * @param paramBoolean
     *            是否第一次买入
     */
    // public void setIsFirstTimeBuy(boolean paramBoolean) {
    // this.spInfo.edit().putBoolean("firstTimeBuy", paramBoolean).commit();
    // }
    //
    // /**
    // * @return 是否第一次买入
    // */
    // public boolean getisFirstTimeBuy() {
    // return this.spInfo.getBoolean("firstTimeBuy", true);
    // }

    /**
     * @param paramString 设置头像
     */
    public void setProtraitImg(String paramString) {
        this.spInfo.edit().putString("protraitImg", paramString).commit();
    }

    /**
     * @return 头像
     */
    public String getProtraitImg() {
        return this.spInfo.getString("protraitImg", "");
    }

    public void setTopicTags(String paramString) {
        this.spInfo.edit().putString("topicTags", paramString).commit();
    }

    public void setTouristResidentialName(String paramString) {
        this.spInfo.edit().putString("touristResidentialName", paramString)
                .commit();
    }

    public void setPhotoNum(int paramString) {
        this.spInfo.edit().putInt("photoNum", paramString).commit();
    }

    public int getPhotoNum() {
        return this.spInfo.getInt("photoNum", 3);
    }

    /** 获取服务器地址 */
    public String getUrl() {
        String url = this.spInfo.getString("url", "");
        if (url.equals("")) {
            url = ConstantPrivate.SERVER_URL;
        }
        return url;
    }

    public void setUrl(String paramString) {
        this.spInfo.edit().putString("url", paramString).commit();
    }

    public String getRootPath() {
        return this.spInfo.getString("rootPath", "");
    }

    public void setRootPath(String paramString) {
        this.spInfo.edit().putString("rootPath", paramString).commit();
    }

    /**
     * 头像
     *
     * @param paramString
     */
    public void setPortraitUrl(String paramString) {
        this.spInfo.edit().putString("portraitUrl", paramString).commit();
    }

    public String getPortraitUrl() {
        return this.spInfo.getString("portraitUrl", "");
    }

    /**
     * @return 城市ID
     */
    public String getCityId() {
        return this.spInfo.getString("cityId", "");
    }

    /**
     * @return 城市名称
     */
    public String getCityName() {
        return this.spInfo.getString("cityName", "");
    }

    /**
     * @return 小区名称
     */

    public String getComName() {
        return this.spInfo.getString("comName", "");
    }

    /**
     * 上传的文件名称
     */

    public String getFileName() {
        return this.spInfo.getString("fileName", "");
    }

    /**
     * 上传的图片路径
     */

    public String getQiniuDomain() {
        return this.spInfo.getString("imageUrl2", "");
    }

    /**
     * @param paramString 上传的图片路径
     */
    public void setQiniuDomain(String paramString) {
        this.spInfo.edit().putString("imageUrl2", paramString).commit();
    }

    /**
     * @return 获取转UTF-8码的城市名称
     */
    public String getCityNameUtf8() {
        String cityName = this.spInfo.getString("cityName", "");
        try {
            cityName = URLEncoder.encode(cityName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        return cityName;
    }

    public int getCommentNum() {
        return this.spInfo.getInt("commentNum", 0);
    }

    public String getCustCode() {
        return this.spInfo.getString("custCode", "");
    }

    public String getCustName() {
        return this.spInfo.getString("CustName", "");
    }

    public String getDealTimeDate() {
        return this.spInfo.getString("DealTimeDate", "");
    }

    public String getEndDate() {
        return this.spInfo.getString("endDate", "");
    }

    public String getHeadPicName() {
        return this.spInfo.getString("headPicName", "");
    }

    public String getHeadPicUrl() {
        return this.spInfo.getString("headPicUrl", "");
    }

    /**
     * @return 小区名称
     */
    public String getCommunity() {
        return this.spInfo.getString("community", "");
    }

    /**
     * @return 小区ID
     */
    public String getCommId() {
        return this.spInfo.getString("commId", "");
    }

    /**
     * @return 楼栋名称
     */
    public String getBuildName() {
        return this.spInfo.getString("buildName", "");

    }

    /**
     * @return 楼栋ID
     */
    public String getBuildId() {
        return this.spInfo.getString("buildId", "");
    }

    /**
     * @return 房屋ID
     */
    public String getHouseId() {
        return this.spInfo.getString("houseId", "");
    }

    /**
     * @return 房屋名称
     */
    public String getHouseName() {
        return this.spInfo.getString("houseName", "");
    }

    public String getImageUrl() {
        return this.spInfo.getString("imageUrl", "");
    }

    public boolean getIsBluetoothSupport() {
        return this.spInfo.getBoolean("bluetooth", false);
    }

    /**
     * @return 是否首次进入应用
     */
    public boolean getIsFirstLaunch() {
        return this.spInfo.getBoolean("isFirstLaunch", true);
    }

    public boolean getIsGuestCodeFirstClick() {
        return this.spInfo.getBoolean("isFirstClick", true);
    }

    public boolean getIsNeedGuide() {
        return this.spInfo.getBoolean("isNeedGuide", true);
    }

    public boolean getIsNewBusinessSupport() {
        return this.spInfo.getBoolean("newBusiness", false);
    }

    public boolean getIsNotifyOn() {
        return this.spInfo.getBoolean("isNotifyOn", true);
    }

    public boolean getIsOneCardSupportSupport() {
        return this.spInfo.getBoolean("oneCard", false);
    }

    public boolean getIsPrePaySupported() {
        return this.spInfo.getBoolean("propertyBillPrePay", false);
    }

    public boolean getIsPropertyBillPaymentSupport() {
        return this.spInfo.getBoolean("propertyBillPayment", false);
    }

    public boolean getIsPropertyBillSupport() {
        return this.spInfo.getBoolean("propertybill", false);
    }

    public boolean getIsProtection() {
        return this.spInfo.getBoolean("protection", false);
    }

    public boolean getIsStaffLikeSupport() {
        return this.spInfo.getBoolean("staffLike", false);
    }

    /**
     * 设置股票代码更新时间
     */
    public void setStockCodeUpdateTime(String time) {
        this.spInfo.edit().putString("stockCodeUpdateTime", time).commit();
    }

    /**
     * 获取股票代码更新时间
     */
    public String getStockCodeUpdateTime() {
        return this.spInfo.getString("stockCodeUpdateTime", "");
    }

    /**
     * 设置是否已经阅读股票规则
     */
    public void setHasReadStockRule(boolean hasRead) {
        this.spInfo.edit().putBoolean("hasReadStockRule", hasRead).commit();
    }

    /**
     * 是否已经阅读股票规则
     */
    public boolean hasReadStockRule() {
        return this.spInfo.getBoolean("hasReadStockRule", false);
    }

    /**
     * 设置是否第一次设置手势密码
     */
    public void setIsFirstGesture(boolean isFirst) {
        this.spInfo.edit().putBoolean("isFirstGesture", isFirst).commit();
    }

    /**
     * 是否第一次设置手势密码
     */
    public boolean isFirstGesture() {
        return this.spInfo.getBoolean("isFirstGesture", true);
    }

}
