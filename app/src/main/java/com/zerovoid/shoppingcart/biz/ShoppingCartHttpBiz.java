package com.zerovoid.shoppingcart.biz;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zerovoid.common.biz.CommonBiz;
import com.zerovoid.http.JsonReponseHandler;
import com.zerovoid.http.VollyHelperNew;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/14.
 */
public class ShoppingCartHttpBiz extends CommonBiz {


    /** 购物车确认订单 */
    public static final String URL_DISPATCH_CONFIRM_ORDER = "http://developer.17orange.com:29088/mpc/index.php/orangelife/service/orderInfo/firmOrder";

    public static void requestOrderList(ArrayList<String> listProductIDs, VollyHelperNew.ResponseCallBack callback) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsIDs", map);
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < listProductIDs.size(); i++) {
            try {
                jsonArray.put(i, listProductIDs.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jo.put("goodsIDs", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VollyHelperNew.getInstance().sendRequestWithCallback(URL_DISPATCH_CONFIRM_ORDER, Request.Method.POST, null, jo, callback, null, null, null);
    }

    public static List<ShoppingCartBean> handleOrderList(JSONObject response, int errCode) {
        List<ShoppingCartBean> list = null;
        if (isSuccess(response, errCode)) {
            list = JsonReponseHandler.getListFromJsonWithPageEntity(
                    response, new TypeToken<List<ShoppingCartBean>>() {
            }.getType(), null, null);
        }
        return list;
    }
}
