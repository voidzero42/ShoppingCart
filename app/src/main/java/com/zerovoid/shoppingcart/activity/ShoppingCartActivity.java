package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zerovoid.common.util.ToastHelper;
import com.zerovoid.http.VollyHelperNew;
import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.adapter.MyExpandableListAdapter;
import com.zerovoid.shoppingcart.adapter.MyRecyclerViewAdapter;
import com.zerovoid.shoppingcart.biz.ShoppingCartBiz;
import com.zerovoid.shoppingcart.biz.ShoppingCartHttpBiz;
import com.zerovoid.shoppingcart.model.ShoppingCartBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends Activity {
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.ivSelectAll)
    ImageView ivSelectAll;
    @Bind(R.id.tvEditAll)
    TextView tvEditAll;
    private List<ShoppingCartBean> mListChildGoods = new ArrayList<>();
    private MyExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        request();
        initView();
        setAdapter();
    }

    private void setAdapter() {
        adapter = new MyExpandableListAdapter(this, mListChildGoods);
        expandableListView.setAdapter(adapter);
    }

    private void initView() {
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                return true;
            }
        });
        ivSelectAll.setOnClickListener(listener);
        tvEditAll.setOnClickListener(listener);
    }

    boolean isSelectAll = false;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivSelectAll:
                    isSelectAll = ShoppingCartBiz.selectAll(mListChildGoods, isSelectAll, (ImageView) view);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.tvEditAll:
                    Toast.makeText(ShoppingCartActivity.this, "编辑全部", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void request() {
        String s1 = "279457f3-4692-43bf-9676-fa9ab9155c38";
        String s2 = "95fbe11d-7303-4b9f-8ca4-537d06ce2f8a";
        ArrayList<String> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        ShoppingCartHttpBiz.requestOrderList(list, new VollyHelperNew.ResponseCallBack() {
            @Override
            public void handleResponse(JSONObject response, int errCode) {
                mListChildGoods = ShoppingCartHttpBiz.handleOrderList(response, errCode);
                updateListView();
            }
        });
    }

    private void updateListView() {
        adapter.setList(mListChildGoods);
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
