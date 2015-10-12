package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ExpandableListView;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.adapter.MyExpandableListAdapter;
import com.zerovoid.shoppingcart.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends Activity {
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    private ArrayList<String> mListGroup = new ArrayList<String>();
    private ArrayList<ArrayList<String>> mListChild = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new MyExpandableListAdapter(this, mListGroup, mListChild));
        expandAllGroup();
    }

    private void initData() {
        mListGroup.add("Alienware旗舰店");
        mListGroup.add("魅族官方旗舰店");
        mListGroup.add("小米官方旗舰店");

        ArrayList<String> listChild1 = new ArrayList<String>();
        listChild1.add("Razer雷蛇炼狱皇蛇鼠标");
        listChild1.add("1八月2号");
        listChild1.add("1八月3号");

        ArrayList<String> listChild2 = new ArrayList<String>();
        listChild2.add("魅蓝Note2");
        listChild2.add("魅族Pro5");
        listChild2.add("魅族MX4");

        ArrayList<String> listChild3 = new ArrayList<String>();
        listChild3.add("小米3");
        listChild3.add("小米4");
        listChild3.add("红米Note");

        mListChild.add(listChild1);
        mListChild.add(listChild2);
        mListChild.add(listChild3);
    }


    /**
     * 展开所有组
     */
    private void expandAllGroup() {
        for (int i = 0; i < mListGroup.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

}