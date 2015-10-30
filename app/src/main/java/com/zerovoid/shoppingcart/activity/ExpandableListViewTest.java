package com.zerovoid.shoppingcart.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.zerovoid.shoppingcart.R;

public class ExpandableListViewTest extends Activity {
    ExpandableListView expandList;
    InfoDetailsAdapter adapter;
    Activity activity;
    List<String> group;
    List<List<String>> child;

    public void initialData() {
        group = new ArrayList<>();
        child = new ArrayList<>();
        addInfo("组1", new String[]{"组1子1", "组1子2", "组1子3"});
        addInfo("组2", new String[]{"组2子1", "组2子2", "组2子3"});
        addInfo("组3", new String[]{"组3子1", "组3子2", "组3子3"});
    }

    public void addInfo(String p, String[] c) {
        group.add(p);
        List<String> item = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            item.add(c[i]);
        }
        child.add(item);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        activity = this;
        expandList = (ExpandableListView) findViewById(R.id.expandableListView);
        initialData();
        adapter = new InfoDetailsAdapter(this, group, child);
        expandList.setAdapter(adapter);
        expandList.expandGroup(0);
        expandList.expandGroup(1);
        expandList.expandGroup(2);
    }


}
