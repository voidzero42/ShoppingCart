package com.zerovoid.http;

import android.app.Dialog;

import com.google.gson.Gson;
import com.zerovoid.common.adapter.FatherAdapter;
import com.zerovoid.common.config.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON处理类，只是用来特殊处理自己服务端的数据，非通用的
 *
 * @author Administrator
 */
public class JsonReponseHandler {

    // FIXME 还是改成LIST MAP的形式比较好，比LIST 好

    /**
     * 服务端橙子标准JSON格式，返回{"errCode":0,"errInfo":"OK","entity":{{"KEY":"VALUE"},{}
     * ...}
     *
     * @param response 服务端返回的JSON
     * @param key      Entity对象中的键名列表
     * @return Entity对象中的键值列表
     */
    public static ArrayList<String> getValueListWithEntity(JSONObject response,
                                                           String... key) {
        ArrayList<String> list = null;
        try {
            JSONObject jo = response.getJSONObject(Constant.RESULT_KEY_ENTITY);
            list = new ArrayList<String>();
            for (int i = 0; i < key.length; i++) {
                String result = null;
                try {
                    result = jo.getString(key[i]);
                } catch (JSONException e) {
                    /* 如果某个KEY出问题，那么就直接保存NULL */
                    e.printStackTrace();
                }
                list.add(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * 服务端橙子标准JSON格式，返回{"errCode":0,"errInfo":"OK","entity":{{"KEY":"VALUE"},{}
     * ...} 其中Entity的内容，若Android有建立对应的Bean类，则可以使用此方法直接将JSON转为Bean类
     *
     * @param response 服务端返回的JSON
     * @param cls      JavaBean的类型
     * @return Bean类，需要强转
     */
    public static <T> Object getBeanWithEntity(JSONObject response, Class<T> cls) {
        JSONObject jo;
        Object obj = null;
        try {
            jo = response.getJSONObject(Constant.RESULT_KEY_ENTITY);
            obj = new Gson().fromJson(jo.toString(), cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /** listKey=pageEntity */
    public static <T> List<T> getListFromJsonWithPageEntity(Object response,
                                                            Type type, FatherAdapter<T> baseAdapter, Dialog dialog) {
        return getListFromJsonSingle(response, type,
                Constant.RESULT_KEY_PAGE_ENTITY, baseAdapter, dialog);
    }

    /** listKey=自定义，之前有个Entity */
    public static <T> List<T> getListFromJsonWithEntity(Object response,
                                                        String listKey, Type type, FatherAdapter<T> baseAdapter,
                                                        Dialog dialog) {
        return getListFromJsonDouble(response, type,
                Constant.RESULT_KEY_ENTITY, listKey, baseAdapter, dialog);
    }

    /** 直接listKey */
    public static <T> List<T> getListFromJsonSingle(Object response, Type type,
                                                    String listKey, FatherAdapter<T> baseAdapter, Dialog dialog) {
        return getListFromJson(response, type, null, listKey, baseAdapter,
                dialog);
    }

    /** listKey之前还有一层，ENTITY */
    public static <T> List<T> getListFromJsonDouble(Object response, Type type,
                                                    String beforeListKey, String listKey, FatherAdapter<T> baseAdapter,
                                                    Dialog dialog) {
        return getListFromJson(response, type, beforeListKey, listKey,
                baseAdapter, dialog);
    }

    /**
     * 将服务器返回的JSON转化为List，两层，
     *
     * @param response    服务器的HTTP响应，JSON数据，之所以用object，是希望将msg.obj直接传送过来
     * @param type        List的数据类型，如List<Comment>
     * @param baseAdapter 更新ListView的适配器
     * @param dialog      进度对话框
     * @author 陈福荣, 吴格非
     */
    public static <T> List<T> getListFromJson(Object response, Type type,
                                              String beforeListKey, String listKey, FatherAdapter<T> baseAdapter,
                                              Dialog dialog) {
        if (response == null) {
            return null;
        }
        JSONObject jo = null;
        if (response instanceof JSONObject) {
            jo = (JSONObject) response;
        } else if (response instanceof String) {
            try {
                jo = new JSONObject(response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        List<T> list = null;
        try {
            // 如果有对话框，则关闭对话框
            if (dialog != null) {
                dialog.dismiss();
            }
            // 如果在取list上层还有一层，则取出，否则直接取list
            if (beforeListKey != null && !beforeListKey.equals("")
                    && (jo != null && !jo.equals(""))) {
                jo = jo.getJSONObject(beforeListKey);
            }
            // 取list
            if (jo != null && !jo.equals("")) {
                String json = String.valueOf(jo.getJSONArray(listKey));
                Gson gson = new Gson();
                if (!"null".equals(json)) {// 服务端可能返回null
                    list = gson.fromJson(json, type);
                }
                if (list == null) {
                    list = new ArrayList<T>();
                }
            }
            if (baseAdapter != null) {// 如果有传入adapter，直接刷新界面
                baseAdapter.setList(list).notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
