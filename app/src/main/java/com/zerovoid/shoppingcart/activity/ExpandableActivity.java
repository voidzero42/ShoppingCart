package com.zerovoid.shoppingcart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ExpandableListView;

import com.zerovoid.shoppingcart.R;
import com.zerovoid.shoppingcart.adapter.MyExpandableListAdapter;

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
    private ArrayList<ArrayList<String>> mListChild = new ArrayList<ArrayList<String>>();
//    private HashMap<String, ArrayList<String>> mChildMap = new HashMap<String, ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
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
        mListGroup.add("1");
        mListGroup.add("2");
        mListGroup.add("3");

        ArrayList<String> listChild1 = new ArrayList<String>();
        listChild1.add("1八月1号");
        listChild1.add("1八月2号");
        listChild1.add("1八月3号");

        ArrayList<String> listChild2 = new ArrayList<String>();
        listChild2.add("2八月1号");
        listChild2.add("2八月2号");
        listChild2.add("2八月3号");

        ArrayList<String> listChild3 = new ArrayList<String>();
        listChild3.add("3八月1号");
        listChild3.add("3八月2号");
        listChild3.add("3八月3号");

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
