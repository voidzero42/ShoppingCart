package com.zerovoid.shoppingcart.biz;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.zerovoid.common.biz.CommonBiz;
import com.zerovoid.http.JsonReponseHandler;
import com.zerovoid.http.VollyHelperNew;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/14.
 */
public class ShoppingCartHttpBiz extends CommonBiz {

    //从给定位置读取Json文件
    public static String readJson(InputStream is) {
        //从给定位置获取文件
//        File file = new File(path);
        BufferedReader reader = null;
        //返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            //每次读取文件的缓存
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    //给定路径与Json文件，存储到硬盘
    public static void writeJson(String path, Object json, String fileName) {
        BufferedWriter writer = null;
        File file = new File(path + fileName + ".json");
        //如果文件不存在，则新建一个
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("文件写入成功！");
    }


    /**
     * 购物车确认订单
     */
    public static final String URL_DISPATCH_CONFIRM_ORDER = "http://developer.17orange.com:29088/mpc/index.php/orangelife/service/orderInfo/firmOrder";

    public static void requestOrderList(Context context, VollyHelperNew.ResponseCallBack callback) {
        try {
            InputStream is = context.getAssets().open("firm_order.json");
            String s = ShoppingCartHttpBiz.readJson(is);
            callback.handleResponse(new JSONObject(s), 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void requestOrderList(List<String> listProductIDs, VollyHelperNew.ResponseCallBack callback) {
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
