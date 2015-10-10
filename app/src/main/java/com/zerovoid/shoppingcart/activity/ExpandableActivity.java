package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.zerovoid.shoppingcart.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author WuGefei
 */
public class ExpandableActivity extends Activity {

    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    private ArrayList<String> mListGroup = new ArrayList<String>();
    private ArrayList<String> mListChild = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        expandAllGroup();
    }

    private void initData() {

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
