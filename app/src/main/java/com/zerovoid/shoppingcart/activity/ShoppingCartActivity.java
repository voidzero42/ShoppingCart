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

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.adapter.MyExpandableListAdapter;
import com.zerovoid.shoppingcart.adapter.MyRecyclerViewAdapter;
import com.zerovoid.shoppingcart.biz.ShoppingCartBiz;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends Activity {
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.ivSelectAll)
    ImageView ivSelectAll;
    @Bind(R.id.tvEditAll)
    TextView tvEditAll;
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
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                return true;
            }
        });
        expandAllGroup();
        ivSelectAll.setOnClickListener(listener);
        tvEditAll.setOnClickListener(listener);
    }

    boolean isSelectAll = false;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivSelectAll:
                    isSelectAll = ShoppingCartBiz.checkItem(isSelectAll, ivSelectAll);
                    break;
                case R.id.tvEditAll:
                    Toast.makeText(ShoppingCartActivity.this, "编辑全部", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


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
