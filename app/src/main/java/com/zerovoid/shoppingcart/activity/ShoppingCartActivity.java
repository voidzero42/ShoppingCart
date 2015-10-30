package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerovoid.common.config.SharePreferenceUtilNew;
import com.zerovoid.http.VollyHelperNew;
import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.adapter.MyExpandableListAdapter;
import com.zerovoid.shoppingcart.biz.ShoppingCartBiz;
import com.zerovoid.shoppingcart.biz.ShoppingCartHttpBiz;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends Activity {
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.ivSelectAll)
    ImageView ivSelectAll;
    @Bind(R.id.btnSettle)
    TextView btnSettle;
    @Bind(R.id.tvCountMoney)
    TextView tvCountMoney;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    private List<ShoppingCartBean> mListChildGoods = new ArrayList<>();
        private MyExpandableListAdapter adapter;
//    InfoDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        setAdapter();
        request();
        initView();
    }

    private void testAddGood() {
//        SharePreferenceUtilNew.getInstance().setGoodsInfo("");
        ShoppingCartBiz.saveGoodNum("279457f3-4692-43bf-9676-fa9ab9155c38", "6");
        ShoppingCartBiz.saveGoodNum("95fbe11d-7303-4b9f-8ca4-537d06ce2f8a", "8");
        ShoppingCartBiz.saveGoodNum("8c6e52fb-d57c-45ee-8f05-50905138801b", "9");
        ShoppingCartBiz.saveGoodNum("7d6e52fb-d57c-45ee-8f05-50905138801d", "3");
    }


    private void setAdapter() {
        adapter = new MyExpandableListAdapter(this, listGroup, listChild);
//        adapter = new InfoDetailsAdapter(this, listGroup, listChild);
        expandableListView.setAdapter(adapter);
//        adapter.setImageViewSelectAll(ivSelectAll, btnSettle, tvCountMoney, tvTitle);
    }

    private List<String> listGroup = new ArrayList<>();
    private List<List<String>> listChild = new ArrayList<>();

    private void depart(List<ShoppingCartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            String mname = list.get(i).getMerchantName();
            listGroup.add(mname);
            List<String> ls = new ArrayList<>();
            for (int j = 0; j < list.get(i).getGoods().size(); j++) {
                ls.add(list.get(i).getGoods().get(j).getGoodsName());

            }
            listChild.add(ls);
        }
    }

    private void initView() {
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                return true;
            }
        });
    }

    private void request() {
        testAddGood();
//        List<String> list = ShoppingCartBiz.getAllProductID();
        ShoppingCartHttpBiz.requestOrderList(this, new VollyHelperNew.ResponseCallBack() {
            @Override
            public void handleResponse(JSONObject response, int errCode) {
                mListChildGoods = ShoppingCartHttpBiz.handleOrderList(response, errCode);
                ShoppingCartBiz.updateShopList(mListChildGoods);
                updateListView();
            }
        });
//        ShoppingCartHttpBiz.requestOrderList(list, new VollyHelperNew.ResponseCallBack() {
//            @Override
//            public void handleResponse(JSONObject response, int errCode) {
//                mListChildGoods = ShoppingCartHttpBiz.handleOrderList(response, errCode);
//                ShoppingCartBiz.updateShopList(mListChildGoods);
//                updateListView();
//            }
//        });
    }

    private void updateListView() {
        depart(mListChildGoods);
//        InfoDetailsAdapter ida = new InfoDetailsAdapter(this, listGroup, listChild);
//        expandableListView.setAdapter(ida);
        adapter.setGroupList(listGroup);
        adapter.setChildList(listChild);
//        adapter.setList(mListChildGoods);
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }

    /**
     * 展开所有组
     */
    private void expandAllGroup() {
        for (int i = 0; i < mListChildGoods.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

}
