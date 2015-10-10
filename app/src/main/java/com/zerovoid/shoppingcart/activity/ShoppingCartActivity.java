package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends Activity {
    @Bind(R.id.myRecyclerView)
    RecyclerView myRecyclerView;

    private ArrayList<String> mList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(new MyRecyclerViewAdapter(this, mList));
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            mList.add("我是item" + i);
        }
    }

}
