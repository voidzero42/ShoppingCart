package com.zerovoid.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zerovoid.common.config.Constant;
import com.zerovoid.common.util.ToastHelper;
import com.zerovoid.common.util.TokenUtils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class VollyHelperNew {

    private static VollyHelperNew helper;
    private Context context;
    private RequestQueue mQueue;

    public void initVollyHelper(Context context) {
        this.context = context;
        this.mQueue = Volley.newRequestQueue(context);
    }

    public static VollyHelperNew getInstance() {
        if (helper == null) {
            helper = new VollyHelperNew();
        }
        return helper;
    }

    protected static final String TAG = VollyHelperNew.class.getSimpleName();

    public void cancelAll() {
        mQueue.cancelAll(CANCLE_TAG);
    }

    public void cancel(String tag) {
        mQueue.cancelAll(tag);
    }

    /**
     * 判断是否成功
     *
     * @param errCode 服务端返回码
     * @return 是否成功
     */
    public static boolean isSuccess(int errCode) {
        if (errCode == Constant.RESULT_SUCCESS) {
            return true;
        }
        return false;
    }

    /********************** Handler ************************/

    /**
     * @param url     请求地址
     * @param map     请求参数列表
     * @param handler
     * @param what
     * @author WuGefei
     */
    public RequestQueue sendRequestPost(String url,
                                        HashMap<String, String> map, final Handler handler, final int what) {
        return sendRequest(url, map, handler, what, Method.POST);
    }

    public RequestQueue sendRequestGet(String url, final Handler handler,
                                       final int what) {
        return sendRequest(url, null, handler, what, Method.GET);
    }

    public RequestQueue sendRequestGet(String url, final Handler handler,
                                       final int what, Map<String, String> map) {
        String params = convertMapToGetParams(map);
        url += params;
        return sendRequest(url, null, handler, what, Method.GET);
    }

    /**
     * @param url     请求地址
     * @param map     请求参数列表
     * @param handler 请求界面的Handler
     * @param what    请求界面的上下文
     * @param method  请求方式 POST | PUT | GET
     * @author WuGefei
     */
    public RequestQueue sendRequest(String url, HashMap<String, String> map,
                                    final Handler handler, final int what, int method) {
        JSONObject jo = null;
        if (method != Method.GET || map != null) {
            jo = new JSONObject(map);
        }

        JsonObjectRequest json = new JsonObjectRequest(method, url, jo,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        checkResponse(response, handler, what);
                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                sendError(handler, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return TokenUtils.getHeaders(context);
            }
        };
        /* 解决重复请求问题 */
        json.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 1f) {

            @Override
            public int getCurrentRetryCount() {
                /*
                 * Volley默认尝试两次，MAX=1,count=0;count<=MAX;count++;count=2时，
				 * 表示当前已经重复请求两次，就不会再第三次重复请求，从而屏蔽掉Volley的自动重复请求功能；
				 */
                return 2;
            }

        });
        mQueue.add(json);
        return mQueue;
    }

    /********************** WithCallbackAndInfo ************************/

    public RequestQueue sendRequestPostWithCallbackAndInfo(String url,
                                                           Context context, HashMap<String, String> map, String infoSuccess,
                                                           String infoFailure) {
        return sendRequestWithCallbackAndInfo(url, Method.POST, map,
                infoSuccess, infoFailure);
    }

    public RequestQueue sendRequestGetWithCallbackAndInfo(String url,
                                                          String infoSuccess, String infoFailure) {
        return sendRequestWithCallbackAndInfo(url, Method.GET, null,
                infoSuccess, infoFailure);
    }

    /**
     * 传入成功和失败信息，自动处理json
     *
     * @param url
     * @param method
     * @param map
     * @param infoSuccess 成功弹出信息
     * @param infoFailure 错误弹出信息
     * @return
     */
    public RequestQueue sendRequestWithCallbackAndInfo(String url, int method,
                                                       HashMap<String, String> map, String infoSuccess, String infoFailure) {
        return sendRequestWithCallback(url, method, map, null, infoSuccess,
                infoFailure);
    }

    /************************* WithCallback WithoutInfo *********************/

    public RequestQueue sendRequestGetWithCallback(String url,
                                                   final ResponseCallBack responseCallBack, Map<String, String> map) {
        String params = convertMapToGetParams(map);
        url += params;
        return sendRequestWithCallback(url, Method.GET, null, responseCallBack,
                null, null);
    }

    public RequestQueue sendRequestGetWithCallback(String url,
                                                   final ResponseCallBack responseCallBack, Map<String, String> map,
                                                   Object tag) {
        String params = convertMapToGetParams(map);
        url += params;
        return sendRequestWithCallback(url, Method.GET, null, responseCallBack,
                null, null, tag);
    }

    public RequestQueue sendRequestGetWithCallback(String url,
                                                   final ResponseCallBack responseCallBack) {
        return sendRequestWithCallback(url, Method.GET, null, responseCallBack,
                null, null);
    }

    public RequestQueue sendRequestGetWithCallback(String url,
                                                   final ResponseCallBack responseCallBack, Object tag) {
        return sendRequestWithCallback(url, Method.GET, null, responseCallBack,
                null, null, tag);
    }

    public RequestQueue sendRequestPostWithCallback(String url,
                                                    Map<String, String> map, final ResponseCallBack responseCallBack) {
        return sendRequestWithCallback(url, Method.POST, map, responseCallBack,
                null, null);
    }

    private boolean isShowSuccess = false;
    private boolean isShowError = false;

    public VollyHelperNew isShowToast(boolean isShowSuccess, boolean isShowError) {
        this.isShowSuccess = isShowSuccess;
        this.isShowError = isShowError;
        return VollyHelperNew.this;
    }

    public RequestQueue sendRequestWithCallback(String url, int method,
                                                Map<String, String> map, final ResponseCallBack responseCallBack,
                                                final String infoSuccess, final String infoFailure) {
        return sendRequestWithCallback(url, method, map, responseCallBack,
                infoSuccess, infoFailure, null);
    }


    public RequestQueue sendRequestWithCallback(String url, int method,
                                                Map<String, String> map, final ResponseCallBack responseCallBack,
                                                final String infoSuccess, final String infoFailure, Object tag) {
        return sendRequestWithCallback(url, method, map, null, responseCallBack, infoSuccess, infoFailure, tag);
    }

    /**
     * @param url    请求地址
     * @param method GET/POST
     * @param map    请求参数
     * @author ChenFurong
     */
    public RequestQueue sendRequestWithCallback(String url, int method,
                                                Map<String, String> map, JSONObject jsonObject, final ResponseCallBack responseCallBack,
                                                final String infoSuccess, final String infoFailure, Object tag) {

        JSONObject jo = null;
        if (method != Method.GET && method != Method.DELETE && map != null) {
            jo = new JSONObject(map);
        }
        if (jsonObject != null) {
            jo = jsonObject;
        }
        JsonObjectRequest json = new JsonObjectRequest(method, url, jo,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(VollyHelperNew.class.getName(), "onResponse="
                                + response.toString());
                        String info = checkResponse(response, null, 0);
                        int errCode = response.optInt(Constant.RESULT_KEY, -1);
                        if (errCode == Constant.RESULT_SUCCESS) {
                            /* 如果需要弹出且成功信息不为空 */
                            if (isShowSuccess) {
                                if (infoSuccess != null) {
                                    info = infoSuccess;
                                }
                                ToastHelper.getInstance()._toast(info);
                            }
                        } else {
                            /* 如果需要弹出且失败信息不为空 */
                            if (isShowError) {
                                if (infoFailure != null) {
                                    info = infoFailure;
                                }
                                ToastHelper.getInstance()._toast(info);
                            }
                        }
                        if (responseCallBack != null) {
                            responseCallBack.handleResponse(response, errCode);
                        }
						/* reset */
                        isShowSuccess = false;
                        isShowError = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(VollyHelperNew.class.getName(), "onErrorResponse");
                if (responseCallBack != null) {
                    Log.e(VollyHelperNew.class.getName(), "error="
                            + error.getMessage());
                    responseCallBack.handleResponse(null, -1);
                }
						/* ToastHelper.getInstance()._toast("对不起，网络请求问题..."); */
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return TokenUtils.getHeaders(context);
            }

            @Override
            public void setRetryPolicy(RetryPolicy retryPolicy) {
                super.setRetryPolicy(retryPolicy);
            }
        };

        json.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 1f) {

            @Override
            public int getCurrentRetryCount() {
				/*
				 * Volley默认尝试两次，MAX=1,count=0;count<=MAX;count++;count=2时，
				 * 表示当前已经重复请求两次，就不会再第三次重复请求，从而屏蔽掉Volley的自动重复请求功能；
				 */
                return 2;
            }

        });
        if (tag != null) {
            json.setTag(tag);
        } else {
            json.setTag(CANCLE_TAG);
        }
        mQueue.add(json);

        return mQueue;
    }

    public static final String CANCLE_TAG = "CLEAR_ALL";

    /**
     * 拼接Map为get请求的URL的尾部
     *
     * @param map Get请求中，URL尾部的参数的键值对
     * @return 拼装好的参数
     */
    private String convertMapToGetParams(Map<String, String> map) {
        if (map != null) {
            StringBuffer sb = new StringBuffer();
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            if ("".equals(sb.toString()))
                return "";
			/* 标准格式是URL ？ KEY1 = VALUE1 & KEY2 = VALUE2,循环后首字为“&”，可直接替换为“？” */
            return sb.replace(0, 1, "?").toString();
        } else {
            return "";
        }

    }

    /********************** 处理 ************************/

    private String checkResponse(JSONObject response, Handler handler, int what) {
        int result = -1;
        String info = "";
        try {
            result = response.optInt(Constant.RESULT_KEY, -1);
            if (result == -1) {/* 如果为空，则直接 */
                info = "对不起，服务器返回数据为空......";
                if (handler != null) {
                    handler.obtainMessage(Constant.WHAT_ERROR_HTTP, result)
                            .sendToTarget();
                }
            } else if (Constant.RESULT_LOGIN_OUT_OF_DATE.equals(result)
                    || Constant.WHAT_TOKEN_EXPIRED == result) {

            } else if (result == Constant.RESULT_SUCCESS) {
				/* 错误信息一定要提示，但是成功信息不一定要提示，即使提示，成功信息也各不相同 */
                if (handler != null) {
                    handler.obtainMessage(what, response).sendToTarget();
                }
            } else {/* 如是失败，则直接将失败信息传回 */
                info = response.get(Constant.RESULT_MESSAGE_KEY).toString();
                if (handler != null) {
                    if (result == Constant.WHAT_ERROR_HTTP) {
                        handler.obtainMessage(Constant.WHAT_ERROR_HTTP, result)
                                .sendToTarget();
                    } else {
                        handler.obtainMessage(result, response).sendToTarget();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            info = "对不起，JSON数据异常......";
            if (handler != null) {
                handler.obtainMessage(Constant.WHAT_ERROR_HTTP, result)
                        .sendToTarget();
            }
        }
        return info;
    }

    private void sendError(Handler handler, VolleyError error) {
        handler.obtainMessage(Constant.WHAT_ERROR_HTTP, "对不起，网络请求错误...")
                .sendToTarget();
    }

    /********************** ResponseCallBack ************************/

    public interface ResponseCallBack {

        /**
         * 整个JSON对象
         * 处理後的数据
         */
        public void handleResponse(JSONObject response, int errCode);
    }

    public RequestQueue sendRequestPostOfJSON(String url, JSONObject object,
                                              final Handler handler, final int what) {

        JsonObjectRequest json = new JsonObjectRequest(Method.POST, url,
                object, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                checkResponse(response, handler, what);
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                sendError(handler, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return TokenUtils.getHeaders(context);
            }
        };
        json.setRetryPolicy(new DefaultRetryPolicy() {

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

        });
        mQueue.add(json);
        return mQueue;
    }
}
